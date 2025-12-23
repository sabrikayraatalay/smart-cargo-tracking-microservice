package com.KayraAtalay.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class CourierRegisterRequest {
    private String fullName;
    @Email
    private String email;
    private String phoneNumber;
    private String password;
}
