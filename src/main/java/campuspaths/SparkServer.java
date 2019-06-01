package campuspaths;

import campuspaths.utils.CORSFilter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.google.gson.Gson;

import pathfinder.*;
import pathfinder.datastructures.*;

public class SparkServer {

  public static void main(String[] args) {
    CORSFilter corsFilter = new CORSFilter();
    corsFilter.apply();
    ModelConnector mc = new ModelConnector();

    // Get the list of the building names
    Spark.get("/buildings", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        return mc.buildingNames();
      }
    });

    // get the long name corresponded the short name
    // "shortToLong?short=BAG
    Spark.get("/shortToLong", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        String shortName = request.queryParams("short");
        if (!mc.shortNameExists(shortName)) {
          Spark.halt(400, "Must be a valid short name");
        }
        return mc.longNameForShort(shortName);
      }
    });

    // find the shortest path
    // "findPath?from=BAG&to=CSE"
    Spark.get("/findPath", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        String start = request.queryParams("from");
        String end = request.queryParams("to");
        if (!(mc.shortNameExists(start) && mc.shortNameExists(end))) {
          Spark.halt(400, "Must be valid short names");
        }
        Path<Point> path = mc.findShortestPath(start, end);
        Gson gson = new Gson();
        return gson.toJson(path);
      }
    });


    // TODO: Create all the Spark Java routes you need here:
  }

}
