package running.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "user")
@Data
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String sex;
}