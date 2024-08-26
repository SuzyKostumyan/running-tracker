package running.tracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "run")
@Data
public class Run {
    @Id
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