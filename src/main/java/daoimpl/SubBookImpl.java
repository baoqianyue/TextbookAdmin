package main.java.daoimpl;

import main.java.bean.BookSub;
import main.java.dao.BookSubDao;
import main.java.db.JDBCHelper;
import main.java.utils.Toast;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 操作BookSub表具体实现类
 */
public class SubBookImpl implements BookSubDao {


    @Override
    public int querySubBookNumById(String bno) throws SQLException {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        int bsubnum = 0;
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT Bsub FROM BookSub WHERE Bno=" + bno);
            if (res.next()) {
                bsubnum = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询数据失败");
        } finally {
            JDBCHelper.closeConnection(res, stat, conn);
        }
        return bsubnum;
    }

    @Override
    public void addSubBook(BookSub sub) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO BookSub VALUES(?,?,?,?)";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sub.getBookId());
            ps.setString(2, sub.getBookName());
            ps.setString(3, sub.getTeacherId());
            ps.setInt(4, sub.getSubBookNum());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("数据插入失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public void updateSubBook(BookSub sub) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE BookSub SET Bsub=? WHERE Bno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sub.getSubBookNum());
            ps.setString(2, sub.getBookId().trim());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("更新失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public void deleteSubBook(String bookId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM BookSub WHERE Bno=?";
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
    public List<BookSub> queryAllSubBook() throws SQLException {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        BookSub sub;
        List<BookSub> subList = new ArrayList<>();
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM BookSub");
            while (res.next()) {
                sub = new BookSub();
                sub.setBookId(res.getString(1));
                sub.setBookName(res.getString(2));
                sub.setTeacherId(res.getString(3));
                sub.setSubBookNum(res.getInt(4));
                subList.add(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询数据失败");
        } finally {
            JDBCHelper.closeConnection(res, stat, conn);
        }
        return subList;
    }
}
