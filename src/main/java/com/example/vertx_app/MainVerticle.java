package com.example.vertx_app;

import io.jsonwebtoken.SignatureException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.*;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.impl.jose.JWT;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;

import java.util.*;

public class MainVerticle extends AbstractVerticle {
  private static final String LIST_ALL_POSTS_ADDR ="vertx.list_all_posts";

  private static final String LIST_ALL_POSTS = "SELECT * FROM social_forum_db.post";

  private final String publicKey = KeyStore.getPublicKey();

  private User user = new User();

  @Override
  public void start() throws Exception {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setPort(3306)
      .setHost("localhost")
      .setDatabase("assignment_submission_db")//"social_forum_db"
      .setUser("root")
      .setPassword("rootroot");

// Pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

// Create the pooled client
    MySQLPool client = MySQLPool.pool(vertx, connectOptions, poolOptions);

    Router router = Router.router(vertx);
    SessionStore store = LocalSessionStore.create(vertx);
    //Route handler1 =
    router.route().handler(SessionHandler.create(store));
    router
      .route()
      .handler(CorsHandler.create("http://localhost:3000")//"\"((http://)|(https://))localhost\\:\\d+\""
        .allowedMethod(HttpMethod.GET)
        .allowedMethod(HttpMethod.OPTIONS)
        .allowCredentials(true)
        .allowedHeader("Authorization")
        .allowedHeader("Access-Control-Allow-Method")
        .allowedHeader("Access-Control-Allow-Credentials")
        .allowedHeader("Access-Control-Allow-Origin")
        .allowedHeader("Access-Control-Allow-Headers")
        .allowedHeader("origin")
        .allowedHeader("Content-Type"));

    router.get("/hello")
      .handler(routingContext -> {
        // Check if the Bear and JWT exist in the request header.
        // The header: Bear "JWT"
        String auth = routingContext.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
          HttpServerResponse response = routingContext.response();
          System.out.println(routingContext.request().getHeader("Authorization"));
          response.setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
            .setStatusMessage("Unauthorized your idiot, caused by: " + HttpResponseStatus.UNAUTHORIZED)
            .end();
          return;
        }

        // The JWT
        String jwt = auth.split(" ")[1].trim().replaceAll("^\"|\"$", "");
        String[] parts = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        JsonObject decodedJwt = new JsonObject(new String(decoder.decode(parts[1])));
        String name = decodedJwt.getString("sub");
        // Instead: get e.g. the username from the token
        String test = JWT.parse(jwt).getJsonObject("payload").getString("sub");
        loadUser(client, name);
        System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getFull_name());

        JwtUtil jwtUtil = new JwtUtil();
        try {
          jwtUtil.validateToken(jwt, user);
          System.out.println("From try!! validated");
        } catch (SignatureException ex) {
          HttpServerResponse response = routingContext.response();
          response.setStatusCode(HttpResponseStatus.UNAUTHORIZED.code())
            .setStatusMessage("Fake signature your idiot! " + HttpResponseStatus.UNAUTHORIZED)
            .end();
          return;
        }

        JWTAuth jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
          .addPubSecKey(new PubSecKeyOptions()
            .setAlgorithm("HS512")
            .setBuffer(publicKey)));
        /**
         * Trying to generate a JWT according to those promises
         * that vertx has!
         * */
        /*
        JsonObject notUsedObject = new JsonObject().put("token", jwt);
        System.out.println("Not used object: " + notUsedObject);
        System.out.println("The authorities: " + JWT.parse(jwt).getJsonObject("payload").getString("authorities"));
        System.out.println("The authorities: " + JWT.parse(jwt).getJsonObject("payload"));
        String notUsedToken = jwtAuth.generateToken(
          new JsonObject()
            .put("authorities", JWT.parse(jwt).getJsonObject("payload").getString("authorities")),
          new JWTOptions().setAlgorithm("HS512")
            .setHeader(JWT.parse(jwt).getJsonObject("header"))
            .setSubject(JWT.parse(jwt).getJsonObject("payload").getString("sub"))
            .setExpiresInSeconds(JWT.parse(jwt).getJsonObject("payload").getInteger("exp")));
        //System.out.println("Not used token: " + notUsedToken);
        TokenCredentials notUsed = new TokenCredentials();
        //System.out.println(notUsed);
        TokenCredentials tokenCredentials = new TokenCredentials(jwt);
        Future<User> authenticated = jwtAuth.authenticate(tokenCredentials);
        System.out.println(tokenCredentials);

          //jwtAuth.authenticate(JsonObject.mapFrom(jwt));
        authenticated.onSuccess(s -> System.out.println("The authentication process succeeded"))
            .onFailure(s -> System.out.println("Failed: " + s.getMessage()));

        com.example.vertx_app.User user = new com.example.vertx_app.User();
        user.setUsername(name); user.setPassword("$2a$10$nPAbIPNSCcJDBp1.n2u5oeORQwpHRiXahcmGGFVUoxsE8fJ8igzg2"); user.setAge(28);
        boolean valid = jwtUtil.validateToken(jwt, user);
        System.out.println("\nValid user? : " + valid);*/

        client
          .query("SELECT created_date FROM post WHERE creator_username= '" + name + "'")
          .execute(ar -> {
            if (ar.succeeded()) {
              RowSet<Row> result = ar.result();
              System.out.println("Got " + result.size() + " rows ");

              JsonArray dateList = new JsonArray();
              for (Row row : result) {
                dateList.add(row.getLocalDate(0).toString());
              }

              HttpServerResponse response = routingContext.response();
              response.setChunked(true);
                JsonObject jsonObject = new JsonObject();
                jsonObject.put("amount_users", result.size());//.getValue("username")
                response//.setStatusCode(200).setStatusMessage("OK")
                  .putHeader("content-type", "application/json")
                  .putHeader("Access-Control-Allow-Origin", "*")
                  .putHeader("Access-Control-Allow-Methods", "GET")
                  .end(dateList.encode());
            } else {
              System.out.println("Failure: " + ar.cause().getMessage());
            }
          });
      });

    httpServer
      .requestHandler(router)
      .listen(8888);
  }

  private void loadUser(MySQLPool client, String username) {
    client.query(
      "SELECT * FROM users WHERE username = '" + username + "'"
    ).execute(re -> {
      if (re.succeeded()) {
        RowSet<Row> result = re.result();
        result.forEach(r -> {
          user.setUsername(r.getString("username"));
          user.setPassword(r.getString("password"));
          user.setAge(r.getInteger("age"));
          user.setFull_name(r.getString("full_name"));
          user.setProfile(r.getString("profile"));
          user.setImage_url(r.getString("image_url"));
        });
      } else {
        user = null;
      }
    });
  }
}
