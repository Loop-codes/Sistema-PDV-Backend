package com.ciadainformatica.vendas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.ciadainformatica.vendas.domain.Usuario;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtService {

    // chave com mais de 32 caracteres
    private static final String SECRET_KEY =
            "chave-super-secreta-bem-grande-para-jwt-vendas-2026";
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24h

    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(Usuario usuario) {
        String nome = (usuario.getPessoa() != null && usuario.getPessoa().getNome() != null)
                ? usuario.getPessoa().getNome()
                : "Usuario";

        return Jwts.builder()
                .setSubject(nome)             // subject = nome
                .claim("nome", nome)          // custom claim
                .claim("tipo", "USUARIO")     // você pode ajustar depois
                .claim("papeis", "USER")      // idem
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractNome(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return null;
            }
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("nome", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String extractTipo(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return null;
            }
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("tipo", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String extractPapeis(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return null;
            }
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("papeis", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String extrairUsername(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return null;
            }
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}