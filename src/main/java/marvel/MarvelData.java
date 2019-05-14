package marvel;
import com.opencsv.bean.CsvBindByName;

/**
 * This represents a marvel character
 */
public class MarvelData {

    /**
     * the name of the character
     */
    @CsvBindByName
    private String hero;

    /**
     * the book they show up in
     */
    @CsvBindByName
    private String book;

    /**
     * get the name of the character
     *
     * @return the name of the character
     */
    public String getHero() {
        return hero;
    }

    /**
     * get the book of the character appears in
     *
     * @return the book of the character appears in
     */
    public String getBook() {
        return book;
    }

    /**
     * set the name of the character
     * @param hero the name of the character
     */
    public void setName(String hero) {
        this.hero = hero;
    }

    /**
     * set the book of the character appears in
     * @param book the book of the character appears in
     */
    public void setBook(String book) {
        this.book = book;
    }
}

