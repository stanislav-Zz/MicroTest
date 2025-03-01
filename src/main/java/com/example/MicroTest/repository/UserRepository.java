package com.example.MicroTest.repository;

import com.example.MicroTest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Найти пользователя по email.
     *
     * @param email Email пользователя
     * @return Опциональный объект User
     */
    Optional<User> findByEmail(String email);

    /**
     * Проверить, существует ли пользователь с данным email.
     *
     * @param email Email пользователя
     * @return true, если существует, иначе false
     */
    boolean existsByEmail(String email);
}
