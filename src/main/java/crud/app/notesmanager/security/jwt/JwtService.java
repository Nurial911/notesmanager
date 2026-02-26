package crud.app.notesmanager.security.jwt;

import crud.app.notesmanager.config.JwtConfig;
import crud.app.notesmanager.security.user.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateToken(User user) {
        var token = Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getTokenExpiration()))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
        return parse(token);
    }

    public Jwt parse(String token){
        try {
            var key = jwtConfig.getSecretKey();
            var claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new Jwt(claims, key);
        } catch (JwtException e) {
            return null;
        }
    }
}
