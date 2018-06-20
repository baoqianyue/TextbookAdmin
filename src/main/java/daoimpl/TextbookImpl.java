package main.java.daoimpl;

import main.java.bean.TextBook;
import main.java.dao.TextBookDao;
import main.java.db.JDBCHelper;

import java.sql.*;
import java.util.ArrayList;
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
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        ArrayList<TextBook> list = new ArrayList<>();
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM Textbook");
            while (res.next()) {
                TextBook book = new TextBook(
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getInt(4),
                        res.getString(5)
                );
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询失败");
        } finally {
            JDBCHelper.closeConnection(res, stat, conn);
        }
        return list;
    }
}
