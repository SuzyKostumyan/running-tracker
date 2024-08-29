package running.tracker.unit.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import running.tracker.service.UserServiceImpl;
import running.tracker.dto.UserRequest;
import running.tracker.dto.UserResponse;
import running.tracker.helper.UserRequestBuilder;
import running.tracker.helper.UserResponseBuilder;
import running.tracker.helper.UserBuilder;
import running.tracker.model.User;
import running.tracker.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import running.tracker.exception.NotFoundException;
import running.tracker.unit.util.RandomValueGenerator;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @AfterEach
    void finalizeTest() {
        verifyNoMoreInteractions(userRepository, modelMapper);
    }

    @Test
    void createUser_ShouldReturnUserResponse() {
        // Given
        UserRequest userRequest = new UserRequestBuilder().build();
        User user = new UserBuilder().build();
        User savedUser = new UserBuilder().build();
        UserResponse userResponse = new UserResponseBuilder().build();

        // When
        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userServiceImpl.createUser(userRequest);

        // Then
        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(modelMapper).map(userRequest, User.class);
        verify(userRepository).save(user);
        verify(modelMapper).map(savedUser, UserResponse.class);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserResponse() {
        // Given
        String userId = RandomValueGenerator.generateRandomString();
        UserRequest userRequest = new UserRequestBuilder().build();
        User user = new UserBuilder().setId(userId).build();
        User updatedUser = new UserBuilder().setId(userId).build();
        UserResponse userResponse = new UserResponseBuilder().setId(userId).build();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(modelMapper).map(userRequest, user);
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(modelMapper.map(updatedUser, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userServiceImpl.updateUser(userId, userRequest);

        // Then
        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository).findById(userId);
        verify(modelMapper).map(userRequest, user);
        verify(userRepository).save(user);
        verify(modelMapper).map(updatedUser, UserResponse.class);
    }

    @Test
    void updateUser_ShouldThrowNotFoundExceptionWhenUserNotFound() {
        // Given
        String userId = RandomValueGenerator.generateRandomString();
        UserRequest userRequest = new UserRequestBuilder().build();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> userServiceImpl.updateUser(userId, userRequest));
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_ShouldReturnUserResponse() {
        // Given
        String userId = RandomValueGenerator.generateRandomString();
        User user = new UserBuilder().setId(userId).build();
        UserResponse userResponse = new UserResponseBuilder().build();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userServiceImpl.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository).findById(userId);
        verify(modelMapper).map(user, UserResponse.class);
    }

    @Test
    void getUserById_ShouldThrowNotFoundExceptionWhenUserNotFound() {
        // Given
        String userId = RandomValueGenerator.generateRandomString();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> userServiceImpl.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void getAllUsers_ShouldReturnPagedUserResponse() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        User user = new UserBuilder().build();
        Page<User> users = new PageImpl<>(Collections.singletonList(user));
        UserResponse userResponse = new UserResponseBuilder().build();

        // When
        when(userRepository.findAll(pageable)).thenReturn(users);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        Page<UserResponse> result = userServiceImpl.getAllUsers(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(userResponse, result.getContent().getFirst());
        verify(userRepository).findAll(pageable);
        verify(modelMapper).map(user, UserResponse.class);
    }

    @Test
    void deleteUser_ShouldReturnDeletedUserResponse() {
        // Given
        String userId = RandomValueGenerator.generateRandomString();
        User user = new UserBuilder().setId(userId).build();
        UserResponse userResponse = new UserResponseBuilder().build();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);

        UserResponse result = userServiceImpl.deleteUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(userResponse, result);
        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
        verify(modelMapper).map(user, UserResponse.class);
    }

    @Test
    void deleteUser_ShouldThrowNotFoundExceptionWhenUserNotFound() {
        // Given
        String userId = RandomValueGenerator.generateRandomString();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> userServiceImpl.deleteUser(userId));
        verify(userRepository).findById(userId);
    }
}