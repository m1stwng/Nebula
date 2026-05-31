package dev.m1stwng.nebula.service;

import dev.m1stwng.nebula.dto.request.LoginRequest;
import dev.m1stwng.nebula.dto.response.LoginResponse;
import dev.m1stwng.nebula.entity.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        authentication = authenticationManager.authenticate(authentication);

        final SecurityUser user = (SecurityUser) authentication.getPrincipal();

        final String token = tokenService.generateToken(user);

        return new LoginResponse(token);
    }
}
