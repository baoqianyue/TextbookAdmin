package main.java.bean;


/**
 * 对应教材发放表
 */
public class BookGrant {
    private String bookId;
    private String classId;
    private String bookName;
    //教材发放量
    private int bookNum;

    public BookGrant(String bookId, String classId, String bookName, int bookNum) {
        this.bookId = bookId;
        this.classId = classId;
        this.bookName = bookName;
        this.bookNum = bookNum;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }
}
