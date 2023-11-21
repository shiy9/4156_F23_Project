package com.ims.security;

import com.ims.constants.ClientConstants;
import com.ims.entity.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Token utility class. Responsible for generating and validating JWT token for authentication
 * purpose.
 */
@Component
public class TokenUtil {
  private final long validDuration = 3600000; // in ms, expire in 1 hr after generating
  private final Key signKey;

  public TokenUtil(Environment env) {
    String secretKey = env.getProperty("tokenSecretKey");
    this.signKey = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  /**
   * Generating the JWT token.
   * Subject is the client email. Token will expire in an hour. SHA256 encrypted.
   * Return: the token as a String
   */
  public String createToken(Client client) {
    Date now = new Date();
    Date expireTime = new Date(now.getTime() + validDuration);

    return Jwts.builder()
            .setSubject(client.getEmail())
            .claim(ClientConstants.CLAIM_KEY_CLIENT_TYPE, client.getClientType())
            .claim(ClientConstants.CLAIM_KEY_CLIENT_ID, client.getClientId())
            .setIssuedAt(now)
            .setExpiration(expireTime)
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * Validate the incoming token with signKey, make sure it has not expired, and the encoded
   * clientType matches with the expectedType parameter.
   * Used for ease of testing.
   */
  public boolean validateToken(String token, String expectedType) {
    try {
      if (token == null || token.isEmpty()) {
        return false;
      }
      Jws<Claims> claims = Jwts.parserBuilder()
              .setSigningKey(signKey)
              .build()
              .parseClaimsJws(token);
      if (claims.getBody().getExpiration().before(new Date())) {
        return false;
      }
      String clientType = claims.getBody().get(ClientConstants.CLAIM_KEY_CLIENT_TYPE, String.class);

      return clientType != null && clientType.equals(expectedType);
    } catch (JwtException | IllegalArgumentException e) {
      // TODO: Log exception in the future?
      return false;
    }
  }

  /**
   * Validate the incoming token with signKey, and make sure it has not expired.
   * Note: the function does not offer clientType check.
   */
  public boolean validateToken(String token) {
    try {
      if (token == null || token.isEmpty()) {
        return false;
      }
      Jws<Claims> claims = Jwts.parserBuilder()
              .setSigningKey(signKey)
              .build()
              .parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      // TODO: Log exception in the future?
      return false;
    }
  }

  /**
   * Get the encoded client email within the token.
   */
  public String getEmail(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(signKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  /**
   * For retrieving clientType field from inside the token.
   */
  public String getClientType(String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(signKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

    return claims.get(ClientConstants.CLAIM_KEY_CLIENT_TYPE, String.class);
  }

  /**
   * For retrieving clientId field from inside the token.
   */
  public int getClientId(String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(signKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

    return claims.get(ClientConstants.CLAIM_KEY_CLIENT_ID, Integer.class);
  }

  /**
   * Helper function to extract the token from the request header.
   */
  public String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
