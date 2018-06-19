package main.java.bean;


/**
 * 对应TextBook表
 */
public class TextBook {

    private String bookId;
    private String bookName;
    private String bookSource;
    private int bookNum;
    private String bookDricetion;

    public TextBook(String bookId, String bookName, String bookSource, int bookNum, String bookDricetion) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookSource = bookSource;
        this.bookNum = bookNum;
        this.bookDricetion = bookDricetion;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookSource() {
        return bookSource;
    }

    public void setBookSource(String bookSource) {
        this.bookSource = bookSource;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public String getBookDricetion() {
        return bookDricetion;
    }

    public void setBookDricetion(String bookDricetion) {
        this.bookDricetion = bookDricetion;
    }
}
