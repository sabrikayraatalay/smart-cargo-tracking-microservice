package com.KayraAtalay.controller;

import com.KayraAtalay.dto.request.AuthRequest;
import com.KayraAtalay.dto.request.RefreshTokenRequest;
import com.KayraAtalay.dto.response.AuthResponse;
import com.KayraAtalay.dto.response.DtoUser;
import com.KayraAtalay.shared.response.RootEntity;

import java.security.Principal;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthRequest request);

    public RootEntity<DtoUser> registerCourier(AuthRequest request);

    public RootEntity<AuthResponse> authenticate(AuthRequest request);

    public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest request);

    public RootEntity<Long> findUserIdByUsername(Principal principal);

}
