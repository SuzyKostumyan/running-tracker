package running.tracker.helper;

import running.tracker.dto.UserResponse;
import running.tracker.unit.util.RandomValueGenerator;

import java.time.LocalDate;

public class UserResponseBuilder {

    private String id = RandomValueGenerator.generateRandomString();
    private final String firstName = RandomValueGenerator.generateRandomString();
    private final String lastName = RandomValueGenerator.generateRandomString();
    private final LocalDate birthDate = LocalDate.now();
    private final String sex = RandomValueGenerator.generateRandomString();

    public UserResponse build() {
        return new UserResponse(id, firstName, lastName, birthDate, sex);
    }

    public UserResponseBuilder setId(String id) {
        this.id = id;
        return this;
    }
}