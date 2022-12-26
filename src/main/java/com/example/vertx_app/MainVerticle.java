package com.example.vertx_app;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;

import java.time.LocalDate;

public class MainVerticle extends AbstractVerticle {
  private static final String LIST_ALL_POSTS_ADDR ="vertx.list_all_posts";

  private static final String LIST_ALL_POSTS = "SELECT * FROM social_forum_db.post";

  SqlClient sqlClient;

  @Override
  public void start() throws Exception {
    Vertx vertx = Vertx.vertx();

    HttpServer httpServer = vertx.createHttpServer();

    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setPort(3306)
      .setHost("localhost")
      .setDatabase("social_forum_db")
      .setUser("root")
      .setPassword("rootroot");

// Pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

// Create the pooled client
    MySQLPool client = MySQLPool.pool(vertx, connectOptions, poolOptions);

    Router router = Router.router(vertx);
    Route handler1 = router
      .get("/hello/:name")
      .handler(CorsHandler.create("*")
        .allowedMethod(HttpMethod.GET)
        .allowedMethod(HttpMethod.OPTIONS)
        .allowCredentials(true)
        .allowedHeader("Access-Control-Allow-Method")
        .allowedHeader("Access-Control-Allow-Origin")
        .allowedHeader("Access-Control-Allow-Credentials")
        .allowedHeader("Access-Control-Allow-Headers")
        .allowedHeader("Content-Type"))
      .handler(routingContext -> {
        MultiMap queryParams = routingContext.queryParams();
        String name = queryParams.contains("name") ? queryParams.get("name"): "unknown";
        System.out.println("The name parameter is::: " + name);

        client
          .query("SELECT created_date FROM post WHERE creator= '" + name + "'")
          .execute(ar -> {
            if (ar.succeeded()) {
              RowSet<Row> result = ar.result();
              System.out.println("Got " + result.size() + " rows ");

              JsonArray dateList = new JsonArray();
              for (Row row : result) {
                LocalDate date = row.getLocalDate(0);
                dateList.add(row.getLocalDate(0).toString());
                //dateList.add(date);
              }

              dateList.forEach(s -> System.out.println(s));
              HttpServerResponse response = routingContext.response();
              response.setChunked(true);
              //response.write("Number of users " + result.size() + "\n");
              //for (Row row : result){
                JsonObject jsonObject = new JsonObject();
                jsonObject.put("amount_users", result.size());//.getValue("username")
                response//.setStatusCode(200).setStatusMessage("OK")
                  .putHeader("content-type", "application/json")
                  .putHeader("Access-Control-Allow-Origin", "*")
                  .putHeader("Access-Control-Allow-Methods", "GET")
                  .end(dateList.encode());
                  //.end(jsonObject.getString("amount_users"));//.encode()
                  //.write(jsonObject.encode());
              //}
              /*
              response//.setStatusCode(200).setStatusMessage("OK")
                .putHeader("content-type", "application/json")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET")
                .end();
               */
            } else {
              System.out.println("Failure: " + ar.cause().getMessage());
            }
          });
      });


    httpServer
      .requestHandler(router)
      .listen(8888);
  }
}
