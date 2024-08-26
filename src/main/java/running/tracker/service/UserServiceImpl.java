package running.tracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import running.tracker.dto.UserRequest;
import running.tracker.dto.UserResponse;
import running.tracker.exception.NotFoundException;
import running.tracker.model.User;
import running.tracker.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating a new user with details: {}", userRequest);

        User user = modelMapper.map(userRequest, User.class);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);

        log.debug("Mapped saved User entity to UserResponse: {}", userResponse);
        return userResponse;
    }

    @Override
    public UserResponse updateUser(String id, UserRequest userRequest) {
        log.info("Updating user with ID: {}", id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            modelMapper.map(userRequest, user);
            User updatedUser = userRepository.save(user);

            log.info("User updated successfully with ID: {}", updatedUser.getId());

            return modelMapper.map(updatedUser, UserResponse.class);
        } else {
            log.error("User with ID: {} not found for update", id);

            throw new NotFoundException("User with id " + id + " not found to update.");
        }
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Override
    public UserResponse deleteUser(String id) {
        log.info("Attempting to delete user with ID: {}", id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User userToDelete = optionalUser.get();
            userRepository.deleteById(id);

            log.info("User with ID: {} deleted successfully", id);

            return modelMapper.map(userToDelete, UserResponse.class);
        } else {
            log.error("User with ID: {} not found for deletion", id);

            throw new NotFoundException("User with id " + id + " not found to delete.");
        }
    }
}