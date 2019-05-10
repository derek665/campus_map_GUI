package marvel;

import java.util.*;

public class MarvelPath {

    public static void main(String args[]) {
        Map<String, Set<String>> bookData = MarvelParser.parseData("src/main/resources/marvel.data/marvel.tsv");
    }
}
