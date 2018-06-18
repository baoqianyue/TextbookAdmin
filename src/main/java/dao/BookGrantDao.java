package main.java.dao;

import main.java.bean.BookGrant;

import java.sql.SQLException;
import java.util.List;

public interface BookGrantDao {
    //增加一本征订教材
    public void addGrantBook(BookGrant bookGrant) throws SQLException;

    //更新一本征订教材
    public void updateGrantBook(BookGrant bookGrant) throws SQLException;

    //删除一本征订教材
    public void deleteGrantBook(BookGrant bookGrant) throws SQLException;

    //查询所有征订教材信息
    public List<BookGrant> queryAllGrantBook() throws SQLException;
}
