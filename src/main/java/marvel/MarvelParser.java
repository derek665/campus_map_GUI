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

import java.io.*;
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
   * @return a map of book to all characters in the book
   */
  // TODO: Pick your return type and document it
  public static Map<String, Set<String>> parseData(String filename) {
    // Hint: You might want to create a new class to use with the CSV Parser
    Map<String, Set<String>> booksAndCharacters = new TreeMap<>();
    try {
      Reader reader = Files.newBufferedReader(Paths.get(filename));

      CsvToBean<MarvelData> csvToBean = new CsvToBeanBuilder(reader)
              .withType(MarvelData.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build();

      Iterator<MarvelData> csvMarvelIterator = csvToBean.iterator();

      while (csvMarvelIterator.hasNext()) {
        MarvelData csvMarvel = csvMarvelIterator.next();
        if (!booksAndCharacters.containsKey(csvMarvel.getBook())) {
          booksAndCharacters.put(csvMarvel.getBook(), new TreeSet<>());
        }
        booksAndCharacters.get(csvMarvel.getBook()).add(csvMarvel.getName());
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
    return booksAndCharacters;
  }
}
