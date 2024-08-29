package running.tracker.helper;

import running.tracker.dto.UserRequest;
import running.tracker.unit.util.RandomValueGenerator;

import java.time.LocalDate;

public class UserRequestBuilder {

    private final String firstName = RandomValueGenerator.generateRandomString();
    private final String lastName = RandomValueGenerator.generateRandomString();
    private final LocalDate birthDate = LocalDate.now();
    private final String sex = RandomValueGenerator.generateRandomString();

    public UserRequest build() {
        return new UserRequest(firstName, lastName, birthDate, sex);
    }
}