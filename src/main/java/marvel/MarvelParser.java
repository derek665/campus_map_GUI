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

package marvel;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import graph.Graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/** Parser utility to load the Marvel Comics dataset. */
public class MarvelParser {
  /**
   * Reads the Marvel Universe dataset. Each line of the input file contains a character name and a
   * comic book the character appeared in, separated by a tab character
   *
   * @spec.requires filename is a valid file path
   * @param filename the file that will be read
   * @return a graph of characters' linked to the characters in the same books
   */
  public static Graph parseData(String filename) {
    // Hint: You might want to create a new class to use with the CSV Parser
    Graph graph = new Graph();
    try {
      Reader reader = Files.newBufferedReader(Paths.get(filename));

      CsvToBean<MarvelData> csvToBean = new CsvToBeanBuilder<MarvelData>(reader)
              .withType(MarvelData.class)
              .withSeparator('\t')
              .withIgnoreLeadingWhiteSpace(true)
              .build();

      Iterator<MarvelData> csvMarvelIterator = csvToBean.iterator();

      Map<String, Set<String>> booksAndCharacters = new HashMap<>();
      while (csvMarvelIterator.hasNext()) {
        MarvelData csvMarvel = csvMarvelIterator.next();
        if (!booksAndCharacters.containsKey(csvMarvel.getBook())) {
          booksAndCharacters.put(csvMarvel.getBook(), new HashSet<>());
        }
        booksAndCharacters.get(csvMarvel.getBook()).add(csvMarvel.getHero());
      }

      // {inv: graph.getNode = graph.getNode_pre + parent }
      for (String book : booksAndCharacters.keySet()) {
        for (String parent : booksAndCharacters.get(book)) {
          if (!graph.hasNode(parent)) {
            graph.addNode(parent);
          }
          // {inv : graph.getEdges(parent) = graph.getEdges(parent)_pre + Edge(booksAndCharacters.get(book)_(i-1), book)}
          for (String child : booksAndCharacters.get(book)) {
            if (!graph.hasNode(child)) {
              graph.addNode(child);
            }
            if (!child.equals(parent) && !graph.hasLabel(parent, child, book)) {
              graph.addChild(parent, child, book);
            }
          }
        }
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println(filename + ": file not found");
      System.exit(1);
    }
    catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return graph;
  }
}
