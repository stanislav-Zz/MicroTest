package com.example.MicroTest.controller;

import com.example.MicroTest.dto.BaseResponse;
import com.example.MicroTest.dto.SubscriptionDto;
import com.example.MicroTest.dto.SubscriptionResponseDto;
import com.example.MicroTest.entity.Subscription;
import com.example.MicroTest.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/addSubscription")
    public ResponseEntity<BaseResponse<SubscriptionResponseDto>> addSubscription(@RequestParam Long userId,
                                                                                 @RequestBody SubscriptionDto subscriptionDto) {
        try {
            SubscriptionResponseDto subscription = subscriptionService.addSubscription(userId, subscriptionDto);
            BaseResponse<SubscriptionResponseDto> response = new BaseResponse<>("success", "Подписка успешно добавлена", subscription);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            BaseResponse<SubscriptionResponseDto> response = new BaseResponse<>("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            BaseResponse<SubscriptionResponseDto> response = new BaseResponse<>("error", "Internal Server Error: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getSubscriptionsByUserId")
    public ResponseEntity<BaseResponse<List<SubscriptionResponseDto>>> getSubscriptionsByUserId(@RequestParam Long userId) {
        try {
            List<SubscriptionResponseDto> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
            BaseResponse<List<SubscriptionResponseDto>> response = new BaseResponse<>("success", "Подписки успешно получены", subscriptions);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            BaseResponse<List<SubscriptionResponseDto>> response = new BaseResponse<>("error", ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            BaseResponse<List<SubscriptionResponseDto>> response = new BaseResponse<>("error", "Internal Server Error: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/deleteSubscription")
    public ResponseEntity<BaseResponse<Void>> deleteSubscription(@RequestParam Long subscriptionId) {
        try {
            subscriptionService.deleteSubscription(subscriptionId);
            BaseResponse<Void> response = new BaseResponse<>("success", "Подписка успешно удалена", null);
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
