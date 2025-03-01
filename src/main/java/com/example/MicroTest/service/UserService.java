package com.example.MicroTest.service;

import com.example.MicroTest.dto.UserDto;
import com.example.MicroTest.dto.UserResponseDto;
import com.example.MicroTest.entity.User;

import java.util.Optional;

/**
 * Сервис для работы с пользователем
 */

public interface UserService {

    /**
     * Метод создания нового пользователя
     *
     * @param userDto данные пользователя
     * @return созданный пользователь
     */
    UserResponseDto createUser(UserDto userDto);

    /**
     * Метод получения пользователя по id
     *
     * @param id пользователя
     * @return пользователь
     */
    UserResponseDto getUserById(Long id);

    /**
     * Метод обновления пользователя
     *
     * @param id пользователя
     * @param userDto данные пользователя
     * @return пользователь
     */
    UserResponseDto updateUser(Long id, UserDto userDto);

    /**
     * Метод удаления пользователя
     *
     * @param id пользователя
     */
    void deleteUser(Long id);
}
