package crud.app.notesmanager.security.jwt;

import crud.app.notesmanager.security.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;

@AllArgsConstructor
public class Jwt {
    private Claims claims;
    private SecretKey secretKey;

    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }

    public String getEmail(){
        return claims.getSubject();
    }

    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }

    public String toString(){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
