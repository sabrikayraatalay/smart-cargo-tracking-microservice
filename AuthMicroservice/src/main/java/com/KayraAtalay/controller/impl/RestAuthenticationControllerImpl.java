package com.KayraAtalay.controller.impl;

import com.KayraAtalay.dto.request.AuthRequest;
import com.KayraAtalay.dto.request.CourierRegisterRequest;
import com.KayraAtalay.dto.request.RefreshTokenRequest;
import com.KayraAtalay.dto.response.AuthResponse;
import com.KayraAtalay.dto.response.CourierResponse;
import com.KayraAtalay.dto.response.DtoUser;
import com.KayraAtalay.shared.response.RestBaseController;
import com.KayraAtalay.shared.response.RootEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.KayraAtalay.controller.IRestAuthenticationController;

import com.KayraAtalay.service.IAuthenticationService;

import jakarta.validation.Valid;

import java.security.Principal;

import static com.KayraAtalay.config.RestApis.*;

@RestController
@RequestMapping(AUTH)
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping(REGISTER)
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest request) {
        return ok(authenticationService.register(request));
    }

    @PostMapping(REGISTER_COURIER)
    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public RootEntity<CourierResponse> registerCourier(@Valid @RequestBody CourierRegisterRequest request) {
        return ok(authenticationService.registerCourier(request));
    }

    @PostMapping(AUTHENTICATE)
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ok(authenticationService.authenticate(request));
    }

    @PostMapping(REFRESH_TOKEN)
    @Override
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ok(authenticationService.refreshToken(request));
    }

    @GetMapping(FIND_USER_ID_BY_USERNAME)
    @Override
    @PreAuthorize("isAuthenticated()")
    @Hidden
    public RootEntity<Long> findUserIdByUsername(Principal principal) {
        String username = principal.getName();
        return ok(authenticationService.findUserIdByUsername(username));
    }

    @GetMapping(FIND_USER_EMAIL_BY_USERNAME)
    @Override
    @PreAuthorize("isAuthenticated()")
    @Hidden
    public RootEntity<String> findUserEmailByUsername(Principal principal) {
        String username = principal.getName();
        return ok(authenticationService.findUserEmailByUsername(username));
    }


}
