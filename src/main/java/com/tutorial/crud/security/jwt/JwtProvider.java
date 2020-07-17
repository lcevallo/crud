package com.tutorial.crud.security.jwt;

import com.tutorial.crud.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();



        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }
    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
      try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
      }catch (MalformedJwtException malformedJwtException){
          logger.error("Token mal formado");
      }catch (UnsupportedJwtException unsupportedJwtException){
          logger.error("Token no soportado");
      }catch (ExpiredJwtException expiredJwtException){
          logger.error("Token expirado");
      }catch (IllegalArgumentException illegalArgumentException){
          logger.error("Token vacio");
      }catch (SignatureException signatureException){
          logger.error("Fallo con la firma");
      }
      return false;
    }

}
