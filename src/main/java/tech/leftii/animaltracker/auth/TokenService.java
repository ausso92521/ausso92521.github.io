package tech.leftii.animaltracker.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/*
 *
 * */
@Service
public class TokenService {

    // Logger for debugging
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Generates jwt token using the provided authorization object
     *
     * @param auth Authentication
     * @return a string representation of the generated jwt token
     */

    public String generateToken(Authentication auth) {
        Instant now = Instant.now();
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")     // Self-signing JWT (no auth server)
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))    // Set expiration of token to 30 minutes
                .subject(auth.getName())
                .claim("scope", scope)
                .build();

        LOGGER.debug(claims.getClaims().toString());

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
