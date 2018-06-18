package main.java.daoimpl;

import main.java.bean.BookGrant;
import main.java.dao.BookGrantDao;
import main.java.db.JDBCHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 操作表BookGrant实现类
 */
public class GrantBookImpl implements BookGrantDao {


    @Override
    public void addGrantBook(BookGrant bookGrant) throws SQLException {

    }

    @Override
    public void updateGrantBook(BookGrant bookGrant) throws SQLException {

    }

    @Override
    public void deleteGrantBook(BookGrant bookGrant) throws SQLException {

    }

    @Override
    public List<BookGrant> queryAllGrantBook() throws SQLException {
        Connection connection = JDBCHelper.getsInstance().getConnection();
//        Statement stat = connection.prepareStatement("SELECT * ")
        return null;
    }
}
