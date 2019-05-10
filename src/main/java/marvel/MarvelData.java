package marvel;
import com.opencsv.bean.CsvBindByName;

public class MarvelData {

    /**
     * the name of the character
     */
    @CsvBindByName
    private String name;

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
    public String getName() {
        return name;
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
     * @param name the name of the character
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the book of the character appears in
     * @param book the book of the character appears in
     */
    public void setBook(String book) {
        this.book = book;
    }
}

