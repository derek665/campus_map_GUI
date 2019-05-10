package marvel;

import java.util.*;

public class MarvelPath {

    public static void main(String args[]) {
        Map<String, Set<String>> bookData =
                MarvelParser.parseData("/Users/derekchan/cse331-19sp-derek665/src/main/resources/marvel/data/staffSuperheroes.tsv");
    }
}
