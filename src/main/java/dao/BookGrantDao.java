package main.java.dao;

import main.java.bean.BookGrant;
import main.java.bean.ClassGetBook;

import java.sql.SQLException;
import java.util.List;

public interface BookGrantDao {
    //发放一本教材
    public void addGrantBook(BookGrant bookGrant) throws SQLException;

    //更新一本教材的发放信息
    public void updateGrantBook(BookGrant bookGrant) throws SQLException;

    //删除一本发放教材
    public void deleteGrantBook(BookGrant bookGrant) throws SQLException;

    //多表查询
    //根据班级号连接查询
    //获取到该班级发放教材的记录以及对应班级的信息
    public List<ClassGetBook> queryAllGetBook(String bno) throws SQLException;

    //查询所有发放教材信息
    public List<BookGrant> queryAllGrantBook() throws SQLException;
}
