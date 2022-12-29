package com.example.vertx_app;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.platform.commons.util.StringUtils;

import javax.swing.text.Utilities;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JwtUtil implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final long JWT_TOKEN_VALIDITY = 30 * 24 * 60 * 60;

    private final String secret = KeyStore.getPublicKey();


  public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
      String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
      return Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(User userDetails) {
        System.out.println("generateToken()");
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList()));
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

      String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
      System.out.println(
                "Claims: " + claims +
                ", Subject: " + subject +
                ", JWT_TOKEN_VALIDITY: " + JWT_TOKEN_VALIDITY +
                ", secret: " + secret
        );
        return Jwts.builder()
                .setClaims(claims)
          .setHeaderParam("type", "JWT")
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, encodedSecret).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, User userDetails) {
        if (!(token != null && !token.isEmpty() && containsText(token)))
            return false;

        final String username = getUsernameFromToken(token);
      System.out.println("From validateToken: " + token + ", user: " + userDetails);
        return (userDetails != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

  private static boolean containsText(CharSequence str) {
    int strLen = str.length();

    for(int i = 0; i < strLen; ++i) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }

    return false;
  }
}
