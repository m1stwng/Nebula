package dev.m1stwng.nebula.controller;

import dev.m1stwng.nebula.dto.response.UserResponse;
import dev.m1stwng.nebula.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> findAuthenticatedUser() {
        return ResponseEntity.ok(userService.findAuthenticatedUser());
    }
}
