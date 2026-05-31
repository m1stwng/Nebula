package dev.m1stwng.nebula.filter;

import dev.m1stwng.nebula.entity.SecurityUser;
import dev.m1stwng.nebula.service.SecurityUserDetailsService;
import dev.m1stwng.nebula.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityUserDetailsService userDetailsService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return;
        }

        final String token = authHeader.substring(7);
        final String email = tokenService.verifyToken(token);

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(email);

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }
}
