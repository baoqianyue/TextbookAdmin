package main.java.bean;



/**
 * 对应教材征订表
 */
public class BookSub {
    //教材编号
    private String bookId;
    //教材名
    private String bookName;
    //班主任教师号
    private String teacherId;
    //征订数量
    private int subBookNum;

    public BookSub() {

    }

    public BookSub(String bookId, String bookName, String teacherId, int subBookNum) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.teacherId = teacherId;
        this.subBookNum = subBookNum;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getSubBookNum() {
        return subBookNum;
    }

    public void setSubBookNum(int subBookNum) {
        this.subBookNum = subBookNum;
    }

    @Override
    public String toString() {
        return bookId;
    }
}
