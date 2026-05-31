package dev.m1stwng.nebula.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.m1stwng.nebula.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    private final String ISSUER = "nebula-api";

    public TokenService(@Value("${JWT_SECRET}") String secretKey) {
        algorithm = Algorithm.HMAC256(secretKey);
        verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    }

    public String generateToken(SecurityUser user) throws JWTCreationException {
        final Instant issuedAt = Instant.now();

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(ISSUER)
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plusSeconds(900))
                .sign(algorithm);
    }

    public String verifyToken(String token) throws JWTVerificationException {
        return verifier.verify(token).getSubject();
    }
}
