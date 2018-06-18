package main.java.dao;

import main.java.bean.BookSub;

import java.sql.SQLException;
import java.util.List;

public interface BookSubDao {

    //增加征订一本教材
    public void addSubBook(BookSub sub) throws SQLException;

    //更新已征订的教材
    public void updateSubBook(BookSub sub) throws SQLException;

    //删除已征订的教材
    public void deleteSubBook(BookSub sub) throws SQLException;

    //查询所有已征订的教材
    public List<BookSub> queryAllSubBook() throws SQLException;


}
