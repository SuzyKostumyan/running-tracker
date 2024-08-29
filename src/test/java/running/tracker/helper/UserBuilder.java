package running.tracker.helper;

import running.tracker.model.User;
import running.tracker.unit.util.RandomValueGenerator;

import java.time.LocalDate;

public class UserBuilder {

    private String id = RandomValueGenerator.generateRandomString();
    private final String firstName = RandomValueGenerator.generateRandomString();
    private final String lastName = RandomValueGenerator.generateRandomString();
    private final LocalDate birthDate = LocalDate.now();
    private final String sex = RandomValueGenerator.generateRandomString();

    public User build() {
        return new User(id, firstName, lastName, birthDate, sex);
    }

    public UserBuilder setId(String id) {
        this.id = id;
        return this;
    }
}