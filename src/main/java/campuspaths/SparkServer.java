package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.JsonArray;
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
        Gson gson = new Gson();
        return gson.toJson(mc.buildingNames());
      }
    });

    // find the shortest path
    // "findPath?start=BAG&end=CSE"
    Spark.get("/findPath", new Route() {
      @Override
      public Object handle(Request request, Response response) throws Exception {
        String start = request.queryParams("start");
        String end = request.queryParams("end");
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
