package com.example.MicroTest.service;

import com.example.MicroTest.constants.Constant;
import com.example.MicroTest.dto.SubscriptionDto;
import com.example.MicroTest.dto.SubscriptionResponseDto;
import com.example.MicroTest.entity.Subscription;
import com.example.MicroTest.entity.User;
import com.example.MicroTest.repository.SubscriptionRepository;
import com.example.MicroTest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    private SubscriptionRepository repository;
    private UserRepository userRepository;

    public SubscriptionServiceImpl(SubscriptionRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public SubscriptionResponseDto addSubscription(Long userId, SubscriptionDto subscriptionDto) {
        if (userId == null) {
            log.error(String.valueOf(Constant.USER_ID_CANNOT_BE_NULL));
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }
        if (subscriptionDto == null) {
            log.error(String.valueOf(Constant.SUBSCRIPTIONS_CANNOT_BE_NULL));
            throw new IllegalArgumentException("Данные подписки не могут быть null");
        }
        if (subscriptionDto.getServiceName() == null || subscriptionDto.getServiceName().isBlank()) {
            log.error(String.valueOf(Constant.SERVICE_NAME_CANNOT_BE_NULL));
            throw new IllegalArgumentException("Название сервиса не может быть пустым");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error(String.valueOf(Constant.USER_NOT_FOUND), userId);
                    return new IllegalArgumentException("Пользователь с ID " + userId + " не найден");
                });

        Subscription subscription = new Subscription();
        subscription.setServiceName(subscriptionDto.getServiceName());
        subscription.setUser(user);

        Subscription savedSubscription = repository.save(subscription);

        return new SubscriptionResponseDto(
                savedSubscription.getId(),
                savedSubscription.getServiceName(),
                savedSubscription.getUser().getId()
        );
    }

    @Override
    public Optional<SubscriptionResponseDto> getSubscriptionById(Long id) {
        if (id == null) {
            log.error(String.valueOf(Constant.ID_SUBSCRIPTION_NOT_NULL));
            throw new IllegalArgumentException("ID подписки не может быть null");
        }
        return repository.findById(id)
                .map(subscription -> new SubscriptionResponseDto(
                        subscription.getId(),
                        subscription.getServiceName(),
                        subscription.getUser().getId()
                ));
    }

    @Override
    public List<SubscriptionResponseDto> getSubscriptionsByUserId(Long userId) {
        if (userId == null) {
            log.error(String.valueOf(Constant.USER_ID_NOT_NULL));
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }
        if (!userRepository.existsById(userId)) {
            log.error(String.valueOf(Constant.USER_ID_NOT_FOUND), userId);
            throw new IllegalArgumentException("Пользователь с ID " + userId + " не найден");
        }
        return repository.findByUserId(userId).stream()
                .map(subscription -> new SubscriptionResponseDto(
                        subscription.getId(),
                        subscription.getServiceName(),
                        subscription.getUser().getId()
                ))
                .toList();
    }

    @Override
    public void deleteSubscription(Long id) {
        if (id == null) {
            log.error(String.valueOf(Constant.SUBSCRIPTION_ID_NOT_NULL));
            throw new IllegalArgumentException("ID подписки не может быть null");
        }
        if (!repository.existsById(id)) {
            log.error(String.valueOf(Constant.SUBSCRIPTION_ID_NOT_FOUND));
            throw new IllegalArgumentException("Подписка с ID " + id + " не найдена");
        }
        repository.deleteById(id);
    }

    @Override
    public List<SubscriptionResponseDto> getTop3Subscriptions() {
        return repository.findTop3PopularSubscriptions().stream()
                .map(subscription -> new SubscriptionResponseDto(
                        subscription.getId(),
                        subscription.getServiceName(),
                        subscription.getUser().getId()
                ))
                .toList();
    }
}
