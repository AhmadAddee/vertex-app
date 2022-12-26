package com.example.vertx_app;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Base64;

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
    JWTAuth provider = JWTAuth.create(Vertx.vertx(), new JWTAuthOptions()
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS512")
        .setBuffer(
          "-----BEGIN PUBLIC KEY-----\n" +
          "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzY9brM8bjd3RVc+pNCd\n" +
          "lIhT8PduqzUKUVyn0mFaJqSusxSB5KBevmLmCeclISjZ2+2pSx7JHyYmgGLhvpLT\n" +
          "NV9kj7yuyaRJ8gmRtNBpy5rLzQrEHtAO801voiUelh69BSZoBV7iRaytQ7oIsCqg\n" +
          "8KLIyD8x1vOVw06p0PUQv+N5kADtYM5IfAljNt3EKl9P8Pj4/j4XbLGq4rz1T12l\n" +
          "lkx/cxFdlfdUy/26axoroXKxayQfASJid5o4o4O9FZY4nXRzbILU5pAl1ulG8fi0\n" +
          "46P28dFv6/m5GU/DOWhxQhHlUPIvzNKaU+fANVyVEFuHxGU6xsUiW20Nv6GUC0Rw\n" +
          "WQIDAQAB\n" +
          "-----END PUBLIC KEY-----"))
      .addPubSecKey(new PubSecKeyOptions()
        .setAlgorithm("HS512")
        .setBuffer(
          "-----BEGIN PRIVATE KEY-----\n" +
          "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnNj1uszxuN3dF\n" +
          "Vz6k0J2UiFPw926rNQpRXKfSYVompK6zFIHkoF6+YuYJ5yUhKNnb7alLHskfJiaA\n" +
          "YuG+ktM1X2SPvK7JpEnyCZG00GnLmsvNCsQe0A7zTW+iJR6WHr0FJmgFXuJFrK1D\n" +
          "ugiwKqDwosjIPzHW85XDTqnQ9RC/43mQAO1gzkh8CWM23cQqX0/w+Pj+Phdssari\n" +
          "vPVPXaWWTH9zEV2V91TL/bprGiuhcrFrJB8BImJ3mjijg70VljiddHNsgtTmkCXW\n" +
          "6Ubx+LTjo/bx0W/r+bkZT8M5aHFCEeVQ8i/M0ppT58A1XJUQW4fEZTrGxSJbbQ2/\n" +
          "oZQLRHBZAgMBAAECggEAchgShuyJ+XKjUY8cUX1/aJA1JqDOxMemSKn+YRDIYMHR\n" +
          "6qO9lfDPdP0qletAbIXhUa2y7G0PMs/nSYcvHcg1kZ+LkIBgi3oscpiQ8V2cMq9n\n" +
          "DHuiIpw4908JMww8+JX0yATSyYdawcv3VdUkhIAx3MBWXDyyl0cSE3gja08spKFK\n" +
          "QdX6fVptYd4/Byuwy8Z0NWTKx9blGVqp0ZtxPl9vHDyEWtULiJd4rFu2gyVOmKpC\n" +
          "NDP+RWbcuZjVogZ9IBKRy2Gea35P3mA9fTyrNyAPDIco6QlMFt8sRY+pzxfrkIIK\n" +
          "vgL24Jw4/f4mKS9bcqEN2AdqMpNarCsvQD9L4V144QKBgQDPM3OJ3/B+whmWaSEh\n" +
          "HVU8Izt7y5HY/Oxv62SpAWlFqaKKt4snlm6n5S+OgQ0Ss1LaVmRP1KhbPh8+x6az\n" +
          "33QoKQf3t8frU01jf3boFUuLDqIi92ysH6hsl3us2sK6QbSzTaS2c75OnLP5PzC5\n" +
          "NJ/CIiXjG3j7/NsXk3am1I8UZQKBgQDOl8SVwlo3ngCC3a6TvAnKjOsu4Spd1Gyy\n" +
          "JI1IF5aeEwt6zHwEzTgKY1EXh3jFLVIk+DRXAMGBxCXr//9X+DRN29+kyr5OHA8i\n" +
          "TZZ60qx2x+46yzKNXbgnpZvOG39VD0j35RDsf5D40d+E7K6zTd73E/3FC+lAzyGc\n" +
          "V8C+MrpK5QKBgDZYM1SVNLm6ZQWrOzI5RFLKlt5oTS+RXt4X6kXdhePskyI487mF\n" +
          "Xi6REQEaXfhqqi9z+0lo6GNODtnjeZix4gBqiplxTXEuXxzaoLBMbYziYKo1JP6c\n" +
          "KwuYS6rRkcNSWnewxKPKBb8OoLGd3kSlRcWgOTmwyfGlpz0uwy8Mjx51AoGBAJwq\n" +
          "VUvvcksAgNprwH+kcLJP+6egRWpQHSo8Px/z1MMBGnhlAoqOpoITuUTT/xLtw152\n" +
          "0PnDdIMnRpMzHE3fLYp9Xn0vVNOumjIP/GQzxHK1hvuihkAeTAqMQU53srUVrM7W\n" +
          "tQhRFcFAxHmygAzCHH0g+39sFZIMARgxrV0Y84GRAoGAVD/zCggaM6mlRC5wrDlb\n" +
          "I3QmlXJejRlKjRf/EWF+ivvqCLALbo/RcNNcVOfrpujkqmkoDttqrUomxL2iGgaV\n" +
          "lvQKMUDPr4izt4ZyIoRduYX+iEvyJW2jLXgEJlTF9Yil30Qyd2owv8jLd6m54/z4\n" +
          "ZeocNtoaiyk7YNQIhhLjeF0=\n" +
          "-----END PRIVATE KEY-----"
        )));

    String token = provider.generateToken(
      new JsonObject().put("username", "ahmad")
        .put("certificated", "true"),
      new JWTOptions().setAlgorithm("HS512"));

    // Decodes the jwt
    String[] part = token.split("\\.");
    Base64.Decoder decoder = Base64.getUrlDecoder();
    String header = new String(decoder.decode(part[0]));
    String payload = new String(decoder.decode(part[1]));

    JsonObject object = new JsonObject();
    int key = payload.indexOf("username");
    int value = payload.indexOf("ahmad");
    object.put(payload.substring(key, key + "username".length()), payload.substring(value, value + "ahmad".length()));


    Arrays.stream(part).forEach(p -> System.out.println("The part " + p.indexOf(0) + " is: " + p));
    System.out.println("The whole token is: " + token);
    System.out.println("The decoded header is: " + header);
    System.out.println("The decoded payload is: " + payload);
    System.out.println("The username is: " + object.getString("username"));
  }
}
