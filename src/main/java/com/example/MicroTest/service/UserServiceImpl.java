package com.example.MicroTest.service;

import com.example.MicroTest.constants.Constant;
import com.example.MicroTest.dto.SubscriptionResponseDto;
import com.example.MicroTest.dto.UserDto;
import com.example.MicroTest.dto.UserResponseDto;
import com.example.MicroTest.entity.User;
import com.example.MicroTest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDto createUser(UserDto userDto) {
        if (userDto == null) {
            log.error(String.valueOf(Constant.USER_NOT_NULL));
            throw new IllegalArgumentException("Пользователь не может быть null");
        }
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            log.error(String.valueOf(Constant.USERNAME_NOT_NULL));
            throw new IllegalArgumentException("Имя пользователя не может быть null");
        }
        if (userDto.getLastName() == null || userDto.getLastName().isBlank()) {
            log.error(String.valueOf(Constant.LASTNAME_NOT_NULL));
            throw new IllegalArgumentException("Фамилия пользователя не может быть null");
        }
        User user = new User();
        user.setUserName(userDto.getName());
        user.setLastName(userDto.getLastName());
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            user.setEmail("email");
        } else {
            user.setEmail(userDto.getEmail());
        }
        User savedUser = repository.save(user);

        List<SubscriptionResponseDto> subscriptionResponses = savedUser.getSubscriptions().stream()
                .map(subscription -> new SubscriptionResponseDto(
                        subscription.getId(),
                        subscription.getServiceName(),
                        subscription.getUser().getId()
                ))
                .toList();

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                subscriptionResponses
        );
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        if (id == null) {
            log.error(String.valueOf(Constant.USER_ID_NOT_NULL));
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }
        User user = repository.findById(id)
                .orElseThrow(() -> {
                    log.error(String.valueOf(Constant.USER_NOT_FOUND), id);
                    return new IllegalArgumentException("Пользователь с ID " + id + " не найден");
                });
        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getLastName(),
                user.getEmail(),
                user.getSubscriptions().stream()
                        .map(subscription -> new SubscriptionResponseDto(
                                subscription.getId(),
                                subscription.getServiceName(),
                                subscription.getUser().getId()
                        ))
                        .toList()
        );
    }


    @Override
    public UserResponseDto updateUser(Long id, UserDto userDto) {
        if (id == null) {
            log.error(String.valueOf(Constant.USER_ID_NOT_NULL));
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }
        if (userDto == null) {
            log.error(String.valueOf(Constant.CLIENT_DATA_NOT_NULL));
            throw new IllegalArgumentException("Данные пользователя не могут быть null");
        }
        User existingUser = repository.findById(id)
                .orElseThrow(() -> {
                    log.error(String.valueOf(Constant.USER_NOT_FOUND), id);
                    return new IllegalArgumentException("Пользователь с ID " + id + " не найден");
                });
        if (userDto.getName() != null && !userDto.getName().isBlank()) {
            existingUser.setUserName(userDto.getName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().isBlank()) {
            existingUser.setLastName(userDto.getLastName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            existingUser.setEmail(userDto.getEmail());
        }
        User updatedUser = repository.save(existingUser);

        return new UserResponseDto(
                updatedUser.getId(),
                updatedUser.getUserName(),
                updatedUser.getLastName(),
                updatedUser.getEmail(),
                updatedUser.getSubscriptions().stream()
                        .map(subscription -> new SubscriptionResponseDto(
                                subscription.getId(),
                                subscription.getServiceName(),
                                subscription.getUser().getId()
                        ))
                        .toList()
        );
    }

    @Override
    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            log.error(String.valueOf(Constant.USER_NOT_FOUND), id);
            throw new IllegalArgumentException("Пользователь с ID " + id + " не найден");
        }
        repository.deleteById(id);
    }
}
