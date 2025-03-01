package com.example.MicroTest.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponseDto {

    private Long id;
    private String serviceName;
    private Long userId;
}
