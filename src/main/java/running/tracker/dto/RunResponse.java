package running.tracker.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RunResponse {

    private String id;

    private String userId;

    private Double startLatitude;

    private Double startLongitude;

    private LocalDateTime startDateTime;

    private Double finishLatitude;

    private Double finishLongitude;

    private LocalDateTime finishDateTime;

    private Double distance;

    private Double averageSpeed;
}