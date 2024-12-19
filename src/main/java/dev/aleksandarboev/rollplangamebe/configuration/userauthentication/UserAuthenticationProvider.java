package dev.aleksandarboev.rollplangamebe.configuration.userauthentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {
    //@Value("$security.jwt.token.secret-key:secret-key}") //TODO fetch secret key from vault instead of hardcoding it
    private static final String SECRET_KEY = "mySecretKey12345";
    private String secretKeyEncoded;

    @PostConstruct
    private void init() {
        secretKeyEncoded = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createToken(UserJwtDto userJwtDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKeyEncoded);
        return JWT.create()
                .withSubject(userJwtDto.id())
                .withIssuedAt(now)
                .withExpiresAt(validity)
//                .withClaim("firstName", userJwtDto.getFirstName()) //put here data, which FE client could use
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKeyEncoded);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserJwtDto user = new UserJwtDto(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
