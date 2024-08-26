package running.tracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserStatisticsRequest {

    @NotNull(message = "User ID is mandatory")
    private String userId;

    private LocalDateTime fromDateTime;

    private LocalDateTime toDateTime;
}