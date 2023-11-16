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
import org.springframework.stereotype.Component;


/**
 * Token utility class. Responsible for generating and validating JWT token for authentication
 * purpose.
 */
@Component
public class TokenUtil {
  private final String secretKey = "TDtq7U2k8d6YXjxFoNjznbUUaxY8VW6aG.FfeaAs7AyXrGgEVyYxQ_jNRTZ6";
  private final long validDuration = 3600000; // in ms, expire in 1 hr after generating
  private final Key signKey = Keys.hmacShaKeyFor(secretKey.getBytes());

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
            .claim(ClientConstants.CLIENT_TYPE_CLAIM_KEY, client.getClientType())
            .setIssuedAt(now)
            .setExpiration(expireTime)
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * Validate the incoming token with signKey, make sure it has not expired, and the encoded
   * clientType matches with the expectedType parameter.
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
      String clientType = claims.getBody().get(ClientConstants.CLIENT_TYPE_CLAIM_KEY, String.class);

      return clientType != null && clientType.equals(expectedType);
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

    return claims.get(ClientConstants.CLIENT_TYPE_CLAIM_KEY, String.class);
  }
}
