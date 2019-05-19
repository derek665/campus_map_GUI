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
  private Graph<Point, Double> graph;
  private List<CampusBuilding> buildingsInfo;

  /**
   * Creates a new {@link ModelConnector} and initializes it to contain data about
   * pathways and buildings or locations of interest on the campus of the University
   * of Washington, Seattle. When this constructor completes, the dataset is loaded
   * and prepared, and any method may be called on this object to query the data.
   */
  public ModelConnector() {
    // TODO: You'll want to do things like read in the campus data and assemble your graph.
    // Remember the tenets of design that you've learned. You shouldn't necessarily do everything
    // you need for the model in this one constructor, factor code out to helper methods or
    // classes to work with your design best. The only thing that needs to remain the
    // same is the name of this class and the four method signatures below, because the
    // Pathfinder application calls these methods in order to talk to your model.
    // Change and add anything else as you'd like.
    buildingsInfo = CampusPathsParser.parseCampusBuildings();
  }

  /**
   * @param shortName The short name of a building to query.
   * @return {@literal true} iff the short name provided exists in this campus map.
   */
  public boolean shortNameExists(String shortName) {
    // TODO: Implement this method to talk to your model, then remove the exception below.

    throw new RuntimeException("shortNameExists not implemented yet.");
  }

  /**
   * @param shortName The short name of a building to look up.
   * @return The long name of the building corresponding to the provided short name.
   * @throws IllegalArgumentException if the short name provided does not exist.
   */
  public String longNameForShort(String shortName) {
    // TODO: Implement this method to talk to your model, then remove the exception below.

    throw new RuntimeException("longNameForShort not implemented yet.");
  }

  /**
   * get the coordinate of the building
   *
   * @param shortName the short name of building to look up
   * @return the coordinate of the building in (x, y)
   * @throws IllegalArgumentException if the short name does not exist
   */
  public Point getCoordinate(String shortName) {
    if (!shortNameExists(shortName)) {
      throw new IllegalArgumentException("short name does not exist");
    }
    for (CampusBuilding cb : buildingsInfo) {
      if (cb.getShortName().equals(shortName)) {
        return new Point(cb.getX(), cb.getY());
      }
    }
    return null;
  }

  /**
   * @return The mapping from all the buildings' short names to their long names in this campus map.
   */
  public Map<String, String> buildingNames() {
    // TODO: Implement this method to talk to your model, then remove the exception below.

    throw new RuntimeException("buildingNames not implemented yet.");
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
    // TODO: Implement this method to talk to your model, then remove the exception below.
    throw new RuntimeException();
  }

  /**
   * find the shortest path by distance from start to end
   *
   * @param start the start of the search
   * @param end the end of the search
   * @param graph the graph we are searching the path in
   * @return a new shortest distance path from the start to the end
   */
  public static <E> Path<E> findShortestPath(E start, E end, Graph<E, Double> graph) {
    Queue<Path<E>> active = new PriorityQueue<>(new PathSorter<>());
    Set<E> finished = new HashSet<>();
    active.add(new Path<>(start));
    while (!active.isEmpty()) {
      Path<E> minPath = active.remove();
      E minDest = minPath.getEnd();
      if (minDest.equals(end)) {
        return minPath;
      } else if (!finished.contains(minDest)) {
        for (Edge<E, Double> edge: graph.getEdges(minDest)) {
          E p = edge.getChild();
          if (!finished.contains(p)) {
            Path<E> newPath = minPath.extend(p, edge.getLabel());
            active.add(newPath);
          }
          finished.add(minDest);
        }
      }
    }
    return null;
  }

  private static class PathSorter<E> implements Comparator<Path<E>> {
    @Override
    public int compare(Path<E> p1, Path<E> p2) {
      double p1C = p1.getCost();
      double p2C = p2.getCost();
      if (p1C > p2C) {
        return 1;
      } else if (p1C < p2C) {
        return -1;
      } else {
        return 0;
      }
    }
  }

}
