package dev.m1stwng.nebula.service;

import dev.m1stwng.nebula.dto.response.UserResponse;
import dev.m1stwng.nebula.entity.SecurityUser;
import dev.m1stwng.nebula.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserResponse findAuthenticatedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final User user = ((SecurityUser) auth.getPrincipal()).user();

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
