package main.java.dao;

import main.java.bean.TextBook;

import java.sql.SQLException;
import java.util.List;

public interface TextBookDao {

    //增加一种教材
    public void addTextBook(TextBook book) throws SQLException;

    //更新一种教材
    public void updateTextBook(String bno, int bnum) throws SQLException;

    //领取教材后，在教材库中更新
    public void getTextbookUpdate(String bno, int bnum) throws SQLException;

    //删除一种教材
    public void deleteTextBook(String bookId) throws SQLException;

    //查询教材库中所有教材信息
    public List<TextBook> queryAllTextbook() throws SQLException;
}
