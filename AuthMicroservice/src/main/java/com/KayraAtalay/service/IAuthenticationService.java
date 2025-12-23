package com.KayraAtalay.service;


import com.KayraAtalay.dto.request.AuthRequest;
import com.KayraAtalay.dto.request.CourierRegisterRequest;
import com.KayraAtalay.dto.request.RefreshTokenRequest;
import com.KayraAtalay.dto.response.AuthResponse;
import com.KayraAtalay.dto.response.CourierResponse;
import com.KayraAtalay.dto.response.DtoUser;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest request);

    public CourierResponse registerCourier(CourierRegisterRequest request);

    public AuthResponse authenticate(AuthRequest request);

    public AuthResponse refreshToken(RefreshTokenRequest request);

    public Long findUserIdByUsername(String username);

    public String findUserEmailByUsername(String email);

}
