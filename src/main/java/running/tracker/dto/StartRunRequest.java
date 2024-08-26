package running.tracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StartRunRequest {

    @NotNull(message = "User ID is mandatory")
    private String userId;

    @NotNull(message = "Start latitude is mandatory")
    private Double startLatitude;

    @NotNull(message = "Start longitude is mandatory")
    private Double startLongitude;

    @NotNull(message = "Start datetime is mandatory")
    private LocalDateTime startDateTime;
}