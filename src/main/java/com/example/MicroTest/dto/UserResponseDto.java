package com.example.MicroTest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String userName;
    private String lastName;
    private String email;
    private List<SubscriptionResponseDto> subscriptions;
}
