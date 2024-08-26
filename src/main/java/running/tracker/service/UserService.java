package running.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import running.tracker.dto.UserRequest;
import running.tracker.dto.UserResponse;

public interface UserService {

    /**
     * Creates a new user.
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Updates an existing user.
     */
    UserResponse updateUser(String id, UserRequest userRequest);

    /**
     * Retrieves a user by their ID.
     */
    UserResponse getUserById(String id);

    /**
     * Retrieves a list of all users.
     */
    Page<UserResponse> getAllUsers(Pageable pageable);

    /**
     * Deletes a user by their ID.
     */
    UserResponse deleteUser(String id);
}