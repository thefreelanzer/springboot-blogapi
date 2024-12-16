package com.blog.blog.controller.auth;

import com.blog.blog.dtos.AppResponse;
import com.blog.blog.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AppResponse<AuthenticationResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(
                new AppResponse<>(response, true, "User registered successfully")
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AppResponse<AuthenticationResponse>> authenticate(@Valid @RequestBody AuthenticateRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(
                new AppResponse<>(response, true, "Authenticated!")
        );
    }
}
