package running.tracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import running.tracker.dto.*;
import running.tracker.service.RunService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/runs")
public class RunController {

    private final RunService runService;

    @PostMapping("/start")
    public ResponseEntity<RunResponse> startRun(@Valid @RequestBody StartRunRequest startRunRequest) {
        RunResponse startRunResponse = runService.startRun(startRunRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(startRunResponse);
    }

    @PostMapping("/finish")
    public ResponseEntity<RunResponse> finishRun(@Valid @RequestBody FinishRunRequest finishRunRequest) {
        RunResponse finishRunResponse = runService.finishRun(finishRunRequest);
        return ResponseEntity.ok(finishRunResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RunResponse>> getAllRunsForUser(
            @PathVariable String userId,
            @RequestParam(required = false) LocalDateTime fromDateTime,
            @RequestParam(required = false) LocalDateTime toDateTime
    ) {
        RunRequest runRequest = new RunRequest(userId, fromDateTime, toDateTime);
        List<RunResponse> runs = runService.getAllRunsForUser(runRequest);
        return ResponseEntity.ok(runs);
    }

    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics(
            @PathVariable String userId,
            @RequestParam(required = false) LocalDateTime fromDatetime,
            @RequestParam(required = false) LocalDateTime toDatetime
    ) {
        RunRequest request = new RunRequest(userId, fromDatetime, toDatetime);
        UserStatisticsResponse statistics = runService.getUserStatistics(request);
        return ResponseEntity.ok(statistics);
    }
}