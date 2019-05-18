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

package marvel.specTest;

import graph.*;
import marvel.MarvelParser;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from files for testing Graph, the
 * Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

  public static void main(String args[]) {
    try {
      if (args.length > 1) {
        printUsage();
        return;
      }

      MarvelTestDriver td;

      if (args.length == 0) {
        td = new MarvelTestDriver(new InputStreamReader(System.in),
                new OutputStreamWriter(System.out));
      } else {

        String fileName = args[0];
        File tests = new File (fileName);

        if (tests.exists() || tests.canRead()) {
          td = new MarvelTestDriver(new FileReader(tests),
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
    System.err.println("to read from a file: java graph.specTest.GraphTestDriver <name of input script>");
    System.err.println("to read from standard in: java graph.specTest.GraphTestDriver");
  }

  /** String -> Graph: maps the names of graphs to the actual graph **/
  private final Map<String, Graph<String, String>> graphs = new HashMap<>();
  private final PrintWriter output;
  private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new GraphTestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public MarvelTestDriver(Reader r, Writer w) {
      input = new BufferedReader(r);
      output = new PrintWriter(w);
    }

  /**
   * @effects Executes the commands read from the input and writes results to the output
   * @throws IOException if the input or output sources encounter an IOException
   **/
  public void runTests()
            throws IOException
    {
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
      if (command.equals("LoadGraph")) {
        loadGraph(arguments);
      } else if (command.equals("FindPath")) {
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

  private void loadGraph(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new MarvelTestDriver.CommandException("Bad arguments to LoadGraph: " + arguments);
    }
    String graphName = arguments.get(0);
    String fileName = "src/test/resources/marvel/data/" + arguments.get(1);
    graphs.put(graphName, MarvelParser.parseData(fileName));
    output.println("loaded graph " + graphName);
  }

  private void findPath(List<String> arguments) {
    if (arguments.size() != 3) {
      throw new MarvelTestDriver.CommandException("Bad arguments to findPath: " + arguments);
    }
    Graph<String, String> graphName = graphs.get(arguments.get(0));
    String start = arguments.get(1).replaceAll("_", " ");
    String end = arguments.get(2).replaceAll("_", " ");
    if (!graphName.hasNode(start)) {
      output.println("unknown character " + start);
    }
    if (!graphName.hasNode(end)) {
      output.println("unknown character " + end);
    }
    if (graphName.hasNode(start) && graphName.hasNode((end))) {
      List<Edge<String, String>> result = MarvelPaths.findPath(start, end, graphName);
      output.println("path from " + start + " to " + end + ":");
      if (result.isEmpty() && !start.equals(end)) {
        output.println("no path found");
      } else {
        for (Edge<String, String> e : result) {
          output.println(start + " to " + e.getChild() + " via " + e.getLabel());
          start = e.getChild();
        }
      }
    }
  }

  private void createGraph(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new MarvelTestDriver.CommandException("Bad arguments to CreateGraph: " + arguments);
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
      throw new MarvelTestDriver.CommandException("Bad arguments to addNode: " + arguments);
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
      throw new MarvelTestDriver.CommandException("Bad arguments to addEdge: " + arguments);
    }

    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    String childName = arguments.get(2);
    String edgeLabel = arguments.get(3);

    addEdge(graphName, parentName, childName, edgeLabel);
  }

  private void addEdge(String graphName, String parentName, String childName, String edgeLabel) {
    graphs.get(graphName).addChild(parentName, childName, edgeLabel);
    output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
  }

  private void listNodes(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new MarvelTestDriver.CommandException("Bad arguments to listNodes: " + arguments);
    }

    String graphName = arguments.get(0);
    listNodes(graphName);
  }

  private void listNodes(String graphName) {
    Set<String> node = new TreeSet<>();
    node.addAll(graphs.get(graphName).getNodes());
    StringBuilder result = new StringBuilder();
    result.append(graphName + " contains:");
    for (String s : node) {
      result.append(" " + s);
    }
    output.println(result);
  }

  private void listChildren(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new MarvelTestDriver.CommandException("Bad arguments to listChildren: " + arguments);
    }

    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    listChildren(graphName, parentName);
  }

  private void listChildren(String graphName, String parentName) {
    Set<Edge<String, String>> unsorted = graphs.get(graphName).getEdges(parentName);
    List<Edge<String, String>> child = new ArrayList<>(unsorted);
    child.sort(new EdgeSorter());
    StringBuilder result = new StringBuilder();
    result.append("the children of " + parentName + " in " + graphName + " are:");
    for (Edge edge : child) {
      result.append(" " + edge.getChild() + "(" + edge.getLabel() + ")");
    }
    output.println(result);
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

  private class EdgeSorter implements Comparator<Edge<String, String>> {
    @Override
    public int compare(Edge<String, String> e1, Edge<String, String> e2) {
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
