package running.tracker.service;

import running.tracker.dto.*;

import java.util.List;

public interface RunService {

    /**
     * Starts a new run for a user.
     */
    RunResponse startRun(StartRunRequest startRunRequest);

    /**
     * Finishes an existing run for a user.
     * If distance is not provided, it will be calculated.
     */
    RunResponse finishRun(FinishRunRequest finishRunRequest);

    /**
     * Retrieves all runs for a specific user, optionally filtering by start date.
     * Returns the average speed for each run.
     */
    List<RunResponse> getAllRunsForUser(RunRequest runRequest);

    /**
     * Retrieves statistics for a specific user, optionally filtering by start date.
     * Returns the number of runs, total distance, and average speed for all runs in the selected period.
     */
    UserStatisticsResponse getUserStatistics(RunRequest request);
}