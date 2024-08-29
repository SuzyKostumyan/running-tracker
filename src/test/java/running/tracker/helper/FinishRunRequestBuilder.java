package running.tracker.helper;

import running.tracker.dto.FinishRunRequest;
import running.tracker.unit.util.RandomValueGenerator;

import java.time.LocalDateTime;

public class FinishRunRequestBuilder {

    private String userId = RandomValueGenerator.generateRandomString();
    private Double finishLatitude = RandomValueGenerator.generateRandomDouble();
    private Double finishLongitude = RandomValueGenerator.generateRandomDouble();
    private LocalDateTime finishDateTime = LocalDateTime.now();
    private Double distance = RandomValueGenerator.generateRandomDouble();

    public FinishRunRequest build() {
        return new FinishRunRequest(userId, finishLatitude, finishLongitude, finishDateTime, distance);
    }

    public FinishRunRequestBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public FinishRunRequestBuilder setFinishLatitude(Double finishLatitude) {
        this.finishLatitude = finishLatitude;
        return this;
    }

    public FinishRunRequestBuilder setFinishLongitude(Double finishLongitude) {
        this.finishLongitude = finishLongitude;
        return this;
    }

    public FinishRunRequestBuilder setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
        return this;
    }

    public FinishRunRequestBuilder setDistance(Double distance) {
        this.distance = distance;
        return this;
    }
}