package com.ims.security;

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
   * TODO: add clientType encoding once DB support is added
   * Return: the token as a String
   */
  public String createToken(Client client) {
    Date now = new Date();
    Date expireTime = new Date(now.getTime() + validDuration);

    return Jwts.builder()
            .setSubject(client.getEmail())
            .setIssuedAt(now)
            .setExpiration(expireTime)
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact();
    // Add a .claim("clientType", client.getClientType()) above to accommodate client type
  }

  /**
   * Validate the incoming token with signKey and make sure it has not expired.
   * TODO: this function is NOT responsible for validating clientType. It can be later refactored
   * to do so. Or additional functions can be added to validate specific clientTypes.
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

  //  For retrieving clientType field from inside the token
  //  public String getClientType(String token) {
  //    Claims claims = Jwts.parserBuilder()
  //            .setSigningKey(signKey)
  //            .build()
  //            .parseClaimsJws(token)
  //            .getBody();
  //
  //    return claims.get("clientType", String.class);
  //  }
}
