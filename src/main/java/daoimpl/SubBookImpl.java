package main.java.daoimpl;

import com.sun.deploy.util.StringUtils;
import main.java.bean.BookSub;
import main.java.dao.BookSubDao;
import main.java.db.JDBCHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 操作BookSub表具体实现类
 */
public class SubBookImpl implements BookSubDao {
    @Override
    public void addSubBook(BookSub sub) throws SQLException {

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
    public void deleteSubBook(BookSub sub) throws SQLException {

    }

    @Override
    public List<BookSub> queryAllSubBook() throws SQLException {
        Connection conn = null;
        Statement stat = null;
        ResultSet res = null;
        BookSub sub = null;
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
