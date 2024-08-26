package running.tracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FinishRunRequest {

    @NotNull(message = "User ID is mandatory")
    private String userId;

    @NotNull(message = "Finish latitude is mandatory")
    private Double finishLatitude;

    @NotNull(message = "Finish longitude is mandatory")
    private Double finishLongitude;

    @NotNull(message = "Finish datetime is mandatory")
    private LocalDateTime finishDateTime;

    private Double distance;
}