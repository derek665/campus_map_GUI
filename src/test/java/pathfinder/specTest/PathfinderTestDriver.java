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

package pathfinder.specTest;

import graph.Graph;

import java.io.*;
import java.util.*;
import graph.*;
import pathfinder.ModelConnector;
import pathfinder.SearchPath;
import pathfinder.datastructures.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

  public static void main(String args[]) {
    try {
      if (args.length > 1) {
        printUsage();
        return;
      }

      PathfinderTestDriver td;

      if (args.length == 0) {
        td = new PathfinderTestDriver(new InputStreamReader(System.in),
                new OutputStreamWriter(System.out));
      } else {

        String fileName = args[0];
        File tests = new File (fileName);

        if (tests.exists() || tests.canRead()) {
          td = new PathfinderTestDriver(new FileReader(tests),
                  new OutputStreamWriter(System.out));
        } else {
          System.err.println("Cannot read from " + tests.toString());
          printUsage();
          return;
        }
      }

      td.runTests();

    } catch (IOException e) {
      System.err.println(e.toString());
      e.printStackTrace(System.err);
    }
  }

  private static void printUsage() {
    System.err.println("Usage:");
    System.err.println("to read from a file: java pathfinder.specTest.PathfinderTestDriver <name of input script>");
    System.err.println("to read from standard in: java pathfinder.specTest.PathfinderTestDriver");
  }

  /** String -> Graph: maps the names of graphs to the actual graph **/
  private final Map<String, Graph<String, Double>> graphs = new HashMap<>();
  private final PrintWriter output;
  private final BufferedReader input;

  /**
   * @spec.requires r != null && w != null
   *
   * @spec.effects Creates a new GraphTestDriver which reads command from
   * <tt>r</tt> and writes results to <tt>w</tt>.
   **/
  public PathfinderTestDriver(Reader r, Writer w) {
    input = new BufferedReader(r);
    output = new PrintWriter(w);
  }

  public void runTests() throws IOException {
    String inputLine;
    while ((inputLine = input.readLine()) != null) {
      if ((inputLine.trim().length() == 0) ||
              (inputLine.charAt(0) == '#')) {
        // echo blank and comment lines
        output.println(inputLine);
      }
      else
      {
        // separate the input line on white space
        StringTokenizer st = new StringTokenizer(inputLine);
        if (st.hasMoreTokens()) {
          String command = st.nextToken();

          List<String> arguments = new ArrayList<String>();
          while (st.hasMoreTokens()) {
            arguments.add(st.nextToken());
          }

          executeCommand(command, arguments);
        }
      }
      output.flush();
    }
  }

  private void executeCommand(String command, List<String> arguments) {
    try {
      if (command.equals("FindPath")) {
        findPath(arguments);
      } else if (command.equals("CreateGraph")) {
        createGraph(arguments);
      } else if (command.equals("AddNode")) {
        addNode(arguments);
      } else if (command.equals("AddEdge")) {
        addEdge(arguments);
      } else if (command.equals("ListNodes")) {
        listNodes(arguments);
      } else if (command.equals("ListChildren")) {
        listChildren(arguments);
      } else {
        output.println("Unrecognized command: " + command);
      }
    } catch (Exception e) {
      output.println("Exception: " + e.toString());
    }
  }

  private void createGraph(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new CommandException("Bad arguments to CreateGraph: " + arguments);
    }

    String graphName = arguments.get(0);
    createGraph(graphName);
  }

  private void createGraph(String graphName) {
    graphs.put(graphName, new Graph<>());
    output.println("created graph " + graphName);
  }

  private void addNode(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new CommandException("Bad arguments to addNode: " + arguments);
    }

    String graphName = arguments.get(0);
    String nodeName = arguments.get(1);

    addNode(graphName, nodeName);
  }

  private void addNode(String graphName, String nodeName) {
    graphs.get(graphName).addNode(nodeName);
    output.println("added node " + nodeName + " to " + graphName);
  }

  private void addEdge(List<String> arguments) {
    if (arguments.size() != 4) {
      throw new CommandException("Bad arguments to addEdge: " + arguments);
    }

    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    String childName = arguments.get(2);
    String edgeLabel = arguments.get(3);

    addEdge(graphName, parentName, childName, Double.parseDouble(edgeLabel));
  }

  private void addEdge(String graphName, String parentName, String childName, double edgeLabel) {
    graphs.get(graphName).addChild(parentName, childName, edgeLabel);
    output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + childName + " in " + graphName);
  }

  private void listNodes(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new CommandException("Bad arguments to listNodes: " + arguments);
    }

    String graphName = arguments.get(0);
    listNodes(graphName);
  }

  private void listNodes(String graphName) {
    Set<String> node = new TreeSet<>(graphs.get(graphName).getNodes());
    StringBuilder result = new StringBuilder();
    result.append(graphName + " contains:");
    for (String s : node) {
      result.append(" " + s);
    }
    output.println(result);
  }

  private void listChildren(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new CommandException("Bad arguments to listChildren: " + arguments);
    }

    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    listChildren(graphName, parentName);
  }

  private void listChildren(String graphName, String parentName) {
    Set<Edge<String, Double>> unsorted = graphs.get(graphName).getEdges(parentName);
    List<Edge<String, Double>> sorted = new ArrayList<>(unsorted);
    sorted.sort(new EdgeSorter());
    StringBuilder result = new StringBuilder();
    result.append("the children of " + parentName + " in " + graphName + " are:");
    for (Edge<String, Double> edge : sorted) {
      result.append(" " + edge.getChild() + "(" + String.format("%.3f", edge.getLabel()) + ")");
    }
    output.println(result);
  }

  private void findPath(List<String> arguments) {
    if (arguments.size() != 3) {
      throw new CommandException("Bad arguments to findPath: " + arguments);
    }
    Graph<String, Double> graphName = graphs.get(arguments.get(0));
    String start = arguments.get(1).replaceAll("_", " ");
    String end = arguments.get(2).replaceAll("_", " ");
    if (!graphName.hasNode(start)) {
      output.println("unknown node " + start);
    }
    if (!graphName.hasNode(end)) {
      output.println("unknown node " + end);
    }
    if (graphName.hasNode(start) && graphName.hasNode((end))) {
      Path<String> result = SearchPath.findShortestPath(start, end, graphName);
      output.println("path from " + start + " to " + end + ":");
      for (Path<String>.Segment seg : result) {
        output.println(seg.getStart() + " to " + seg.getEnd() + " with weight " + String.format("%.3f", seg.getCost()));
      }
      output.println("total cost: " + String.format("%.3f", result.getCost()));
    }
  }

  /**
   * This exception results when the input file cannot be parsed properly
   **/
  static class CommandException extends RuntimeException {

    public CommandException() {
      super();
    }
    public CommandException(String s) {
      super(s);
    }

    public static final long serialVersionUID = 3495;
  }

  /**
   * This class is for the Edge to appear in sorted order
   */
  private class EdgeSorter implements Comparator<Edge<String, Double>> {
    @Override
    public int compare(Edge<String, Double> e1, Edge<String, Double> e2) {
      if (e1.getChild().compareTo(e2.getChild()) > 0) {
        return 1;
      } else if (e1.getChild().compareTo(e2.getChild()) < 0) {
        return -1;
      } else {
        return e1.getLabel().compareTo(e2.getLabel());
      }
    }
  }
}
