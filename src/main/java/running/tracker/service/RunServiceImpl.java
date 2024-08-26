package running.tracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import running.tracker.dto.*;
import running.tracker.exception.NotFoundException;
import running.tracker.model.Run;
import running.tracker.repository.RunRepository;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;

    private final ModelMapper modelMapper;

    @Override
    public RunResponse startRun(StartRunRequest startRunRequest) {
        log.info("Starting a new run for user: {}", startRunRequest.getUserId());

        Run run = modelMapper.map(startRunRequest, Run.class);
        Run savedRun = runRepository.save(run);

        log.debug("Run started and saved with ID: {}", savedRun.getId());

        return modelMapper.map(savedRun, RunResponse.class);
    }

    @Override
    public RunResponse finishRun(FinishRunRequest finishRunRequest) {
        log.info("Finishing run for user: {}", finishRunRequest.getUserId());

        Run run = getOngoingRun(finishRunRequest.getUserId());
        calculateRunDistance(run, finishRunRequest);
        updateRunFinishDetails(run, finishRunRequest);

        log.debug("Run details updated for run ID: {}", run.getId());

        return saveAndMapRun(run);
    }

    private Run getOngoingRun(String userId) {
        log.debug("Retrieving ongoing run for user ID: {}", userId);

        return runRepository.findByUserIdAndFinishDateTimeIsNull(userId)
                .orElseThrow(() -> {
                    log.error("No ongoing run found for user ID: {}", userId);
                    return new NotFoundException("Run for user with id " + userId + " not found to finish.");
                });
    }

    private void calculateRunDistance(Run run, FinishRunRequest finishRunRequest) {
        log.debug("Calculating distance for run ID: {}", run.getId());

        double distance;
        if (finishRunRequest.getDistance() == null) {
            distance = calculateDistance(run.getStartLatitude(), run.getStartLongitude(),
                    finishRunRequest.getFinishLatitude(), finishRunRequest.getFinishLongitude());
            log.debug("Calculated distance: {} meters", distance);
        } else {
            distance = finishRunRequest.getDistance();
            log.debug("Using provided distance: {} meters", distance);
        }
        run.setDistance(distance);
    }

    private void updateRunFinishDetails(Run run, FinishRunRequest finishRunRequest) {
        log.debug("Updating finish details for run ID: {}", run.getId());

        run.setFinishLatitude(finishRunRequest.getFinishLatitude());
        run.setFinishLongitude(finishRunRequest.getFinishLongitude());
        run.setFinishDateTime(finishRunRequest.getFinishDateTime());
    }

    private RunResponse saveAndMapRun(Run run) {
        log.info("Saving updated run with ID: {}", run.getId());

        Run updatedRun = runRepository.save(run);
        return modelMapper.map(updatedRun, RunResponse.class);
    }

    private double calculateDistance(Double startLat, Double startLon, Double endLat, Double endLon) {
        log.debug("Calculating straight-line distance between coordinates.");

        double latDistance = Math.toRadians(endLat - startLat);
        double lonDistance = Math.toRadians(endLon - startLon);

        double x = lonDistance * Math.cos(Math.toRadians((startLat + endLat) / 2));
        double y = latDistance;

        final int EARTH_RADIUS = 6371;
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        log.debug("Calculated distance in meters.");

        return distance * 1000;
    }

    @Override
    public List<RunResponse> getAllRunsForUser(RunRequest runRequest) {
        log.info("Retrieving all runs for user: {}", runRequest.getUserId());

        List<Run> runs = retrieveRuns(runRequest);

        log.debug("Found {} runs for user ID: {}", runs.size(), runRequest.getUserId());

        return mapRunsToRunResponses(runs);
    }

    private List<Run> retrieveRuns(RunRequest runRequest) {
        if (runRequest.getFromDateTime() != null && runRequest.getToDateTime() != null) {
            log.debug("Filtering runs between {} and {}", runRequest.getFromDateTime(), runRequest.getToDateTime());
            return runRepository.findAllByUserIdAndStartDateTimeBetween(
                    runRequest.getUserId(),
                    runRequest.getFromDateTime(),
                    runRequest.getToDateTime()
            );
        } else {
            log.debug("Retrieving all runs for user ID: {}", runRequest.getUserId());
            return runRepository.findAllByUserId(runRequest.getUserId());
        }
    }

    private List<RunResponse> mapRunsToRunResponses(List<Run> runs) {
        return runs.stream().map(run -> {
            RunResponse runResponse = modelMapper.map(run, RunResponse.class);

            if (run.getFinishDateTime() != null && run.getDistance() != null) {
                double averageSpeed = calculateAndSetAverageSpeed(run);
                runResponse.setAverageSpeed(averageSpeed);
            } else {
                runResponse.setAverageSpeed(null);
            }

            return runResponse;
        }).collect(Collectors.toList());
    }

    private double calculateAndSetAverageSpeed(Run run) {
        log.debug("Calculating average speed for run ID: {}", run.getId());

        Duration duration = Duration.between(run.getStartDateTime(), run.getFinishDateTime());
        double averageSpeed = run.getDistance() / duration.getSeconds();

        log.debug("Calculated average speed: {} m/s", averageSpeed);

        run.setAverageSpeed(averageSpeed);
        runRepository.save(run);

        return averageSpeed;
    }

    @Override
    public UserStatisticsResponse getUserStatistics(RunRequest request) {
        log.info("Calculating statistics for user: {}", request.getUserId());

        List<Run> runs = retrieveRuns(request);

        long numberOfRuns = calculateNumberOfRuns(runs);
        double totalDistance = calculateTotalDistance(runs);
        double averageSpeed = calculateAverageSpeed(runs, totalDistance);

        log.debug("User ID: {} - Number of runs: {}, Total distance: {}, Average speed: {}",
                request.getUserId(), numberOfRuns, totalDistance, averageSpeed);

        return buildUserStatisticsResponse(numberOfRuns, totalDistance, averageSpeed);
    }

    private long calculateNumberOfRuns(List<Run> runs) {
        return runs.size();
    }

    private double calculateTotalDistance(List<Run> runs) {
        return runs.stream().mapToDouble(Run::getDistance).sum();
    }

    private double calculateAverageSpeed(List<Run> runs, double totalDistance) {
        double totalDurationSeconds = runs.stream()
                .filter(run -> run.getFinishDateTime() != null)
                .mapToDouble(run -> Duration.between(run.getStartDateTime(), run.getFinishDateTime()).getSeconds())
                .sum();
        return totalDurationSeconds > 0 ? totalDistance / totalDurationSeconds : 0;
    }

    private UserStatisticsResponse buildUserStatisticsResponse(long numberOfRuns, double totalDistance, double averageSpeed) {
        UserStatisticsResponse response = new UserStatisticsResponse();
        response.setNumberOfRuns(numberOfRuns);
        response.setTotalDistance(totalDistance);
        response.setAverageSpeed(averageSpeed);
        return response;
    }
}