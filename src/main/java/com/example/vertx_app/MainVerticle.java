package com.example.vertx_app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

public class MainVerticle extends AbstractVerticle {
  private static final String LIST_ALL_POSTS_ADDR ="vertx.list_all_posts";

  private static final String LIST_ALL_POSTS = "SELECT * FROM social_forum_db.post";

  SqlClient sqlClient;

  @Override
  public void start() throws Exception {

    // Create a Router
    Router router = Router.router(vertx);

    // Mount the handler for all incoming requests at every path and HTTP method
    router.route().handler(context -> {
      // Get the address of the request
      String address = context.request().connection().remoteAddress().toString();
      // Get the query parameter "name"
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
      // Write a json response
      context.json(
        new JsonObject()
          .put("name", name)
          .put("address", address)
          .put("message", "Hello " + name + " connected from " + address)
      );
    });

    // Create the HTTP server
    vertx.createHttpServer()
      // Handle every request using the router
      .requestHandler(router)
      // Start listening
      .listen(8888)
      // Print the port
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );

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

    client
      .query("SELECT * FROM user WHERE username='ahmad'")
      .execute(ar -> {
        if (ar.succeeded()) {
          RowSet<Row> result = ar.result();
          System.out.println("Got " + result.size() + " rows ");
          router.get("/:db").handler(re -> re.response().end(String.valueOf(result.size())));
          router.route().handler(context ->
            context.json(new JsonObject().put("the size", result.size()))
            );
          vertx.createHttpServer().requestHandler(router).listen(8888)
            .onSuccess(server ->
              System.out.println(
                "The new db endpoint " + "db"
              ));
          for (Row row : result) {

            System.out.println("User " + row.getString(0) + " " + row.getString(1) + " " + row.getString(2) + " " + row.getInteger(3));
          }
        } else {
          System.out.println("Failure: " + ar.cause().getMessage());
        }
      });
  }
}
