package main.java.daoimpl;

import main.java.bean.BookGrant;
import main.java.dao.BookGrantDao;
import main.java.db.JDBCHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 操作表BookGrant实现类
 */
public class GrantBookImpl implements BookGrantDao {


    @Override
    public void addGrantBook(BookGrant bookGrant) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO BookGrant VALUES(?,?,?,?)";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookGrant.getBookId());
            ps.setString(2, bookGrant.getBookName());
            ps.setString(3, bookGrant.getClassId());
            ps.setInt(4, bookGrant.getBookNum());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("插入数据失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
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
