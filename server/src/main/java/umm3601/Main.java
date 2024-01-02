package umm3601;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import umm3601.user.UserController;

public class Main {

  public static void main(String[] args) {
    // Get the MongoDB address and database name from environment variables and
    // if they aren't set, use the defaults of "localhost" and "dev".
    String mongoAddr = Main.getEnvOrDefault("MONGO_ADDR", "localhost");
    String databaseName = Main.getEnvOrDefault("MONGO_DB", "dev");

    // Set up the MongoDB client
    MongoClient mongoClient = Server.configureDatabase(mongoAddr);
    // Get the database
    MongoDatabase database = mongoClient.getDatabase(databaseName);

    // Get the controllers for the server; you'll add your own controllers
    // in `getControllers` as you create them.
    final Controller[] controllers = Main.getControllers(database);

    // Construct the server
    Server server = new Server(mongoClient, controllers);

    // Start the server
    server.startServer();
  }

  /**
   * Get the value of an environment variable, or return a default value if it's not set.
   *
   * @param envName The name of the environment variable to get
   * @param defaultValue The default value to use if the environment variable isn't set
   *
   * @return The value of the environment variable, or the default value if it's not set
   */
  static String getEnvOrDefault(String envName, String defaultValue) {
    return System.getenv().getOrDefault(envName, defaultValue);
  }

  /**
   * Get the controllers for the server. You'll add your own controllers
   * here as you create them.
   *
   * @param database The MongoDB database object used by the controllers
   *               to access the database.
   * @return An array of controllers for the server.
   */
  static Controller[] getControllers(MongoDatabase database) {
    Controller[] controllers = new Controller[] {
      // You would add additional controllers here, as you create them.
      // You can also remove this UserController once you don't need it.
      new UserController(database)
    };
    return controllers;
  }

}
