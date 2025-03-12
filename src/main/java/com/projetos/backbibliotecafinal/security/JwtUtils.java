package com.projetos.backbibliotecafinal.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

public class JwtUtils {
    private static int EXPIRATION_TIME = 1000 * 60 * 5;
    private static String TOKEN_SECRET = "b24eb75f2193f91b8831abc57e662249c139b0f6b8180d3e968fbc9d1dd04369";
    public static String AUTH_HEADER = "Authorization";
    public static String AUTH_BEARER = "Bearer ";

    public static String generateToken(String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    public static String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static List<String> getRoles(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }
}
