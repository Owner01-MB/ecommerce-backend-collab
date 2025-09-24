package com.ecommerce.backend.Security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  @Value("${jwt.secret}")
  private String secret;

  private Key signingKey;

  private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

  @PostConstruct
  public void init() {
    try {
      byte[] keyBytes = Base64.getDecoder().decode(secret);
      this.signingKey = Keys.hmacShaKeyFor(keyBytes);
      logger.info("JWT signing key initialized successfully");
    } catch (Exception e) {
      logger.error("Failed to initialize JWT signing key", e);
      throw e;
    }
  }

  public String generateToken(String username) {
    logger.info("Generating JWT token for user: {}", username);
    String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(signingKey)
        .compact();
    logger.debug("Token generated for user: {}", username);
    return token;
  }

  public String extractUsername(String token) {
    try {
      String username = extractClaim(token, Claims::getSubject);
      logger.debug("Extracted username '{}' from JWT token", username);
      return username;
    } catch (Exception e) {
      logger.error("Failed to extract username from token", e);
      throw e;
    }
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parserBuilder()
        .setSigningKey(signingKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claimsResolver.apply(claims);
  }

  public boolean isTokenValid(String token, String username) {
    try {
      boolean valid = (extractUsername(token).equals(username)) && !isTokenExpired(token);
      if (valid) {
        logger.info("JWT token is valid for user: {}", username);
      } else {
        logger.warn("JWT token is invalid or expired for user: {}", username);
      }
      return valid;
    } catch (Exception e) {
      logger.error("Error validating JWT token for user: {}", username, e);
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    boolean expired = extractExpiration(token).before(new Date());
    if (expired) {
      logger.warn("JWT token has expired");
    }
    return expired;
  }
}
