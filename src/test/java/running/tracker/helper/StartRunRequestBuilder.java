package running.tracker.helper;

import running.tracker.dto.StartRunRequest;
import running.tracker.unit.util.RandomValueGenerator;

import java.time.LocalDateTime;

public class StartRunRequestBuilder {

    private String userId = RandomValueGenerator.generateRandomString();
    private Double startLatitude = RandomValueGenerator.generateRandomDouble();
    private Double startLongitude = RandomValueGenerator.generateRandomDouble();
    private LocalDateTime startDateTime = LocalDateTime.now();

    public StartRunRequest build() {
        return new StartRunRequest(userId, startLatitude, startLongitude, startDateTime);
    }

    public StartRunRequestBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public StartRunRequestBuilder setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
        return this;
    }

    public StartRunRequestBuilder setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
        return this;
    }

    public StartRunRequestBuilder setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }
}