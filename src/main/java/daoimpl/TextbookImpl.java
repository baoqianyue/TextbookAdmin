package main.java.daoimpl;

import main.java.bean.TextBook;
import main.java.dao.TextBookDao;
import main.java.db.JDBCHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Textbook表操作具体实现类
 */
public class TextbookImpl implements TextBookDao {
    @Override
    public void addTextBook(TextBook book) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO Textbook VALUES(?,?,?,?,?)";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, book.getBookId());
            ps.setString(2, book.getBookName());
            ps.setString(3, book.getBookSource());
            ps.setInt(4, book.getBookNum());
            ps.setString(5, book.getBookDricetion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("插入数据失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public void updateTextBook(String bno, int bnum) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE Textbook SET Bnum=? WHERE Bno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bnum);
            ps.setString(2, bno);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("数据更新失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public void deleteTextBook(String bookId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM Textbook WHERE Bno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("数据删除失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public List<TextBook> queryAllTextbook() throws SQLException {
        return null;
    }
}
