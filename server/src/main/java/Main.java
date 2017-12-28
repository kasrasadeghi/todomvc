import static spark.Spark.*;

public class Main {
  public static void main(String[] args) {
    TodosKt.load();
    port(5000);
    enableCORS("*", "GET,POST",
        "Access-Control-Allow-Origin, Content-Type, Access-Control-Allow-Headers");

    get("/hello", (req, res) -> "Hello World");

    get("/todos", (req, res) -> {
      res.type("application/json");
      return TodosKt.json();
    });

    post("/todo", (req, res) -> {
      TodosKt.add(req.body());
      return TodosKt.json();
    });

    post("/remove", (req, res) -> {
      TodosKt.remove(req.body());
      return TodosKt.json();
    });

    Runtime.getRuntime().addShutdownHook(new Thread(TodosKt::save));
  }

  private static void enableCORS(final String origin, final String methods, final String headers) {

    options("/*", (request, response) -> {

      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    before((request, response) -> {
      response.header("Access-Control-Allow-Origin", origin);
      response.header("Access-Control-Request-Method", methods);
      response.header("Access-Control-Allow-Headers", headers);
      // Note: this may or may not be necessary in your particular application
      response.type("application/json");
    });
  }
}


