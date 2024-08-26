package running.tracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {

    private String id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String sex;
}