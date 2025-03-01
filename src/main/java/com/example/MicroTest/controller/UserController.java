package com.example.MicroTest.controller;

import com.example.MicroTest.dto.BaseResponse;
import com.example.MicroTest.dto.UserDto;
import com.example.MicroTest.dto.UserResponseDto;
import com.example.MicroTest.entity.User;
import com.example.MicroTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<BaseResponse<UserResponseDto>> createUser(@RequestBody UserDto userDto) {
        try {
            UserResponseDto user = userService.createUser(userDto);
            BaseResponse<UserResponseDto> response = new BaseResponse<>("success", "Пользователь успешно создан", user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            BaseResponse<UserResponseDto> response = new BaseResponse<>("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            BaseResponse<UserResponseDto> response = new BaseResponse<>("error", "Internal Server Error: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<BaseResponse<UserResponseDto>> getUserById(@RequestParam Long id) {
        try {
            UserResponseDto user = userService.getUserById(id);
            BaseResponse<UserResponseDto> response = new BaseResponse<>("success", "Пользователь успешно найден", user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            BaseResponse<UserResponseDto> response = new BaseResponse<>("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            BaseResponse<UserResponseDto> response = new BaseResponse<>("error", "Internal Server Error: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<BaseResponse<UserResponseDto>> updateUser(@RequestParam Long id, @RequestBody UserDto userDto) {
        try {
            UserResponseDto user = userService.updateUser(id, userDto);
            BaseResponse<UserResponseDto> response = new BaseResponse<>("success", "Пользователь успешно обновлен", user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            BaseResponse<UserResponseDto> response = new BaseResponse<>("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            BaseResponse<UserResponseDto> response = new BaseResponse<>("error", "Internal Server Error: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUser(id);
            BaseResponse<Void> response = new BaseResponse<>("success", "Пользователь успешно удален", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            BaseResponse<Void> response = new BaseResponse<>("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            BaseResponse<Void> response = new BaseResponse<>("error", "Internal Server Error: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
