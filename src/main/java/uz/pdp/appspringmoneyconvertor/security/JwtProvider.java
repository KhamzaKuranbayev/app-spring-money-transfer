package uz.pdp.appspringmoneyconvertor.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    public static final String KEY = "ThisIsTheSecretKeyOfToken";
    public static final long EXPIRE_TIME = 36_000_000;

    public String generateToken(String username) {

        String token = Jwts
                .builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .compact();

        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public String getUsernameFromToken(String token) {

        String username = Jwts
                .parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return username;
    }


}
