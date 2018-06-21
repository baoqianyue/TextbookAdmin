package main.java.bean;


/**
 * 用于每个班级查询本班需要领取的教材
 * 对应发放表和班级表的自然连接
 */
public class ClassGetBook {

    private String bookId;
    private String bookName;
    private String classLeaderId;
    private String classLeaderName;
    private int bookGetNum;

    public ClassGetBook(String bookId, String bookName, String classLeaderId, String classLeaderName, int bookGetNum) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.classLeaderId = classLeaderId;
        this.classLeaderName = classLeaderName;
        this.bookGetNum = bookGetNum;
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

    public String getClassLeaderId() {
        return classLeaderId;
    }

    public void setClassLeaderId(String classLeaderId) {
        this.classLeaderId = classLeaderId;
    }

    public String getClassLeaderName() {
        return classLeaderName;
    }

    public void setClassLeaderName(String classLeaderName) {
        this.classLeaderName = classLeaderName;
    }

    public int getBookGetNum() {
        return bookGetNum;
    }

    public void setBookGetNum(int bookGetNum) {
        this.bookGetNum = bookGetNum;
    }
}

