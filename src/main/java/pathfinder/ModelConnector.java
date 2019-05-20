/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import pathfinder.datastructures.*;
import pathfinder.parser.CampusBuilding;
import graph.*;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/*
In the pathfinder homework, the text user interface calls these methods to talk
to your model. In the campuspaths homework, your graphical user interface
will ultimately make class to these methods (through a web server) to
talk to your model the same way.

This is the power of the Model-View-Controller pattern, two completely different
user interfaces can use the same model to display and interact with data in
different ways, without requiring a lot of work to change things over.
*/

/**
 * This class represents the connection between the view and controller and the model
 * for the pathfinder and campus paths applications.
 */
public class ModelConnector {

  // Rep Invariant: graph != null
  //                && every edge label in grpah >= 0
  //                && buildingCoordinates != null
  //                && shortToLong != null
  //                && every child is a node of the graph
  //
  // AF(this) = each node in graph holds a coordinates on the campus, and connected to other coordinates with distance as label
  //            Some coordinates are campus buildings which are stored in buildingCoordinates

  private Graph<Point, Double> graph;
  private Map<String, Point> buildingCoordinates; // map the short name of the buildings to its coordinates
  private Map<String, String> shortToLong; // map all the short name to its long name

  /**
   * Creates a new {@link ModelConnector} and initializes it to contain data about
   * pathways and buildings or locations of interest on the campus of the University
   * of Washington, Seattle. When this constructor completes, the dataset is loaded
   * and prepared, and any method may be called on this object to query the data.
   */
  public ModelConnector() {
    // Remember the tenets of design that you've learned. You shouldn't necessarily do everything
    // you need for the model in this one constructor, factor code out to helper methods or
    // classes to work with your design best. The only thing that needs to remain the
    // same is the name of this class and the four method signatures below, because the
    // Pathfinder application calls these methods in order to talk to your model.
    // Change and add anything else as you'd like.
    buildingCoordinates = new HashMap<>();
    shortToLong = new HashMap<>();
    List<CampusBuilding> buildingsInfo = CampusPathsParser.parseCampusBuildings();

    for (CampusBuilding campusBuilding : buildingsInfo) {
      if (!buildingCoordinates.containsKey(campusBuilding.getShortName())) {
        buildingCoordinates.put(campusBuilding.getShortName(), new Point(campusBuilding.getX(), campusBuilding.getY()));
      }
      if (!shortToLong.containsKey(campusBuilding.getShortName())) {
        shortToLong.put(campusBuilding.getShortName(), campusBuilding.getLongName());
      }
    }

    graph = buildGraph(CampusPathsParser.parseCampusPaths());
    checkRep();
  }

  private static Graph<Point, Double> buildGraph(List<CampusPath> coordinates) {
    Graph<Point, Double> graph = new Graph<>();

    // {inv: n from 0 to (i-1), coordinates.get(n) is a node of graph
    for (CampusPath campusPath : coordinates) {
      Point start = new Point(campusPath.getX1(), campusPath.getY1());
      Point end = new Point(campusPath.getX2(), campusPath.getY2());

      if (!graph.hasNode(start)) {
        graph.addNode(start);
      }

      if (!graph.hasNode(end)) {
        graph.addNode(end);
      }

      if (!graph.hasLabel(start, end, campusPath.getDistance())) {
        graph.addChild(start, end, campusPath.getDistance());
      }
    }
    return graph;
  }

  /**
   * @param shortName The short name of a building to query.
   * @return {@literal true} iff the short name provided exists in this campus map.
   */
  public boolean shortNameExists(String shortName) {
    checkRep();
    boolean a = shortToLong.containsKey(shortName);
    checkRep();
    return a;
  }

  /**
   * @param shortName The short name of a building to look up.
   * @return The long name of the building corresponding to the provided short name.
   * @throws IllegalArgumentException if the short name provided does not exist.
   */
  public String longNameForShort(String shortName) {
    checkRep();
    String longName = shortToLong.get(shortName);
    if (longName == null) {
      throw new IllegalArgumentException("building does not exists");
    }
    checkRep();
    return longName;
  }

  /**
   * get the coordinate of the building
   *
   * @param shortName the short name of building to look up
   * @return the coordinate of the building in (x, y)
   * @throws IllegalArgumentException if the short name does not exist
   */
  public Point getCoordinate(String shortName) {
    checkRep();
    if (!buildingCoordinates.containsKey(shortName)) {
      throw new IllegalArgumentException("building does not exist");
    }
    Point p = buildingCoordinates.get(shortName);
    return p;
  }

  /**
   * @return The mapping from all the buildings' short names to their long names in this campus map.
   */
  public Map<String, String> buildingNames() {
    checkRep();
    Map<String, String> map = new HashMap<>(shortToLong);
    checkRep();
    return map;
  }

  /**
   * Finds the shortest path, by distance, between the two provided buildings.
   *
   * @param startShortName The short name of the building at the beginning of this path.
   * @param endShortName   The short name of the building at the end of this path.
   * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
   * if none exists.
   * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
   *                                  {@literal null}, or not valid short names of buildings in
   *                                  this campus map.
   */
  public Path<Point> findShortestPath(String startShortName, String endShortName) {
    checkRep();
    if (!buildingCoordinates.containsKey(startShortName) || !buildingCoordinates.containsKey(endShortName)) {
      throw new IllegalArgumentException("building does not exists");
    }
    Point start = buildingCoordinates.get(startShortName);
    Point end = buildingCoordinates.get(endShortName);
    Path<Point> result = SearchPath.findShortestPath(start, end, this.graph);
    checkRep();
    return result;
  }


  /**
   * exception will be thrown if rep invariant is violated
   */
  private void checkRep() {
    assert graph != null;
    assert buildingCoordinates != null;
    assert shortToLong != null;
    for (Point p : graph.getNodes()) {
      assert p != null;
      for (Edge<Point, Double> edge : graph.getEdges(p)) {
        assert graph.hasNode(edge.getChild());
        assert edge.getLabel() >= 0;
      }
    }
  }


}
