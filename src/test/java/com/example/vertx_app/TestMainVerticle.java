package com.example.vertx_app;

import io.netty.handler.codec.base64.Base64Encoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.ChainAuth;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.auth.impl.ChainAuthImpl;
import io.vertx.ext.auth.impl.jose.JWT;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.charset.StandardCharsets;
import java.security.AuthProvider;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }

  @Test
  void jwt_test() {

    JwtUtil jwtUtil = new JwtUtil();
    final String secret = KeyStore.getPublicKey();

    User user = new User();
    user.setPassword("$10$nPAbIPNSCcJDBp1.n2u5oeORQwpHRiXahcmGGFVUoxsE8fJ8igzg2");
    user.setUsername("ahmad"); user.setAge(25); user.setAuthorities(new HashSet<>());

    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", user.getAuthorities()
      .stream()
      .map(auth -> auth.getAuthority())
      .collect(Collectors.toList()));
/*
    JWTAuth provider = JWTAuth.create(Vertx.vertx(), new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS512")
        .setBuffer(secret)));


    String builder = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJhaG1hZCIsImV4cCI6MTY3NDc3NzQzOSwiaWF0IjoxNjcyMTg1NDM5LCJhdXRob3JpdGllcyI6W119.eMZ4xwqZH6YPT3Qb03o7mL_REFsan64kcIrCq9hufcqgHfatQ2-HXUNFHgcz6x90yuIR-EJfTGxj4A4NW9xVcg";
      //jwtUtil.generateToken(user);

    Credentials credentials = new TokenCredentials(builder);
    Credentials userpass = new UsernamePasswordCredentials(user.getUsername(), user.getPassword());
    System.out.println("User credentials: " + userpass);
    System.out.println("The jwt credentials are: " + credentials);
    System.out.println(jwtUtil.validateToken(builder, user));
    provider.authenticate(credentials)
      .map(claims)
      .onSuccess(s -> System.out.println("The token credential succeeded: " + s.values()))
      .onFailure(err -> System.out.println("Failure: " + err.getMessage()));

    Future<io.vertx.ext.auth.User> checkSum = provider.authenticate(userpass);
    checkSum.onSuccess(s -> System.out.println("Succeeded to validate the user cred: " + s))
      .onFailure(err -> System.out.println("Failure: " + err));

    String token = provider.generateToken(
      new JsonObject()
        .put("authorities", new JsonObject()),
      new JWTOptions().setAlgorithm("HS512")
        .setHeader(new JsonObject())
        .setSubject(user.getUsername())
        .setExpiresInSeconds(1000));


    // Decodes the jwt
    String[] part = token.split("\\.");
    Base64.Decoder decoder = Base64.getUrlDecoder();
    String header = new String(decoder.decode(part[0]));
    String payload = new String(decoder.decode(part[1]));

    JsonObject object = new JsonObject();
    int key = payload.indexOf("sub");
    int value = payload.indexOf("ahmad");
    object.put(payload.substring(key, key + "sub".length()), payload.substring(value, value + "ahmad".length()));


    Arrays.stream(part).forEach(p -> System.out.println("The part " + p.indexOf(0) + " is: " + p));
    System.out.println("The whole token is: " + token);
    System.out.println("The decoded header is: " + header);
    System.out.println("The decoded payload is: " + payload);
    System.out.println("The username is: " + object.getString("sub"));
    System.out.println("The secret: " + secret);
    String jwt = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJhaG1hZCIsImV4cCI6MTY3NDc3NTkzOCwiaWF0IjoxNjcyMTgzOTM4LCJhdXRob3JpdGllcyI6W119.71wyRrZxQ7g_eGtVk4RL6x0HW101LNHpCfyi4F2hL3-CNOvtmXANGcaPM8l1pF4zEsNLkEowx85O97oTn4QKEw";
    String notUsedToken = provider.generateToken(
      new JsonObject()
        .put("authorities", JWT.parse(jwt).getJsonObject("payload").getJsonArray("authorities")),//.trim().replaceAll("^\"|\"$", "")
      new JWTOptions().setAlgorithm("HS512")
        .setSubject(JWT.parse(jwt).getJsonObject("payload").getString("sub"))
        .setExpiresInSeconds(JWT.parse(jwt).getJsonObject("payload").getInteger("exp"))
        );
    System.out.println("Not used token: " + notUsedToken);
    Credentials endCredential = new TokenCredentials(jwt);
    provider.authenticate(endCredential)
      .onSuccess(s -> System.out.println("End succeeded: " + s))
      .onFailure(err -> System.out.println("End Failed:" + err));
*/

    System.out.println(
      /*
      jwtUtil.validateToken(
        "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhaG1hZCIsImF1dGhvcml0aWVzIjpbXSwiaWF0IjoxNjcyMjk1ODYxLCJleHAiOjE2NzIzODIyNjF9.AOlQLQPVs0CNu8GIOJRj4EMixv7r9BR_UzvLxvgjTKisIEPreY2KV_xyf8vdpoo8yf2V_NfC9Vd1j8yCVJ_Idw",
        user
      )*/
      secret.equals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzY9brM8bjd3RVc+pNCdlIhT8PduqzUKUVyn0mFaJqSusxSB5KBevmLmCeclISjZ2+2pSx7JHyYmgGLhvpLTNV9kj7yuyaRJ8gmRtNBpy5rLzQrEHtAO801voiUelh69BSZoBV7iRaytQ7oIsCqg8KLIyD8x1vOVw06p0PUQv+N5kADtYM5IfAljNt3EKl9P8Pj4/j4XbLGq4rz1T12llkx/cxFdlfdUy/26axoroXKxayQfASJid5o4o4O9FZY4nXRzbILU5pAl1ulG8fi046P28dFv6/m5GU/DOWhxQhHlUPIvzNKaU+fANVyVEFuHxGU6xsUiW20Nv6GUC0RwWQIDAQAB")
    );
  }
}
