package com.example.MicroTest.repository;

import com.example.MicroTest.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Найти все подписки пользователя по ID.
     *
     * @param userId ID пользователя
     * @return список подписок
     */
    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId")
    List<Subscription> findByUserId(@Param("userId") Long userId);

    /**
     * Получить топ-3 самых популярных подписок.
     *
     * @return список из 3 самых популярных подписок
     */
    @Query("SELECT s.serviceName, COUNT(s.id) AS subscriptionCount FROM Subscription s GROUP BY s.serviceName ORDER BY subscriptionCount DESC LIMIT 3")
    List<Subscription> findTop3PopularSubscriptions();
}
