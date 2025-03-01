package com.example.MicroTest.service;

import com.example.MicroTest.dto.SubscriptionDto;
import com.example.MicroTest.dto.SubscriptionResponseDto;
import com.example.MicroTest.entity.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с подписками
 */

public interface SubscriptionService {

    /**
     * Метод добавления подписки пользователю
     *
     * @param userId          ID пользователя
     * @param subscriptionDto данные подписки
     * @return созданная подписка
     */
    SubscriptionResponseDto addSubscription(Long userId, SubscriptionDto subscriptionDto);

    /**
     * Метод получения подписки по ID
     *
     * @param id ID подписки
     * @return подписка
     */
    Optional<SubscriptionResponseDto> getSubscriptionById(Long id);

    /**
     * Метод получения всех подписок пользователя
     *
     * @param userId ID пользователя
     * @return список подписок
     */
    List<SubscriptionResponseDto> getSubscriptionsByUserId(Long userId);

    /**
     * Метод удаления подписки
     *
     * @param id ID подписки
     */
    void deleteSubscription(Long id);

    /**
     * Метод получения ТОП-3 популярных подписок
     *
     * @return список подписок
     */
    List<SubscriptionResponseDto> getTop3Subscriptions();
}
