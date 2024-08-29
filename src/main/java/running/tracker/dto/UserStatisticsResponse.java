package running.tracker.dto;

import lombok.Data;

@Data
public class UserStatisticsResponse {

    private String userId;

    private long numberOfRuns;

    private Double totalDistance;

    private Double averageSpeed;
}