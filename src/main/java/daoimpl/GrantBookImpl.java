package main.java.daoimpl;

import main.java.bean.BookGrant;
import main.java.bean.ClassGetBook;
import main.java.dao.BookGrantDao;
import main.java.db.JDBCHelper;

import java.sql.*;
import java.util.ArrayList;
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
    public List<ClassGetBook> queryAllGetBook(String cno) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        ArrayList<ClassGetBook> data = new ArrayList<>();
        String sql = "SELECT Bno,Bname,Sno,Sname,Bissue FROM BookGrant,Class WHERE BookGrant.Cno = Class.Cno AND" +
                "BookGrant.Cno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, cno);
            res = ps.executeQuery();
            while (res.next()) {
                ClassGetBook getBook = new ClassGetBook(
                        res.getString(1), res.getString(2),
                        res.getString(3), res.getString(4),
                        res.getInt(5)
                );
                data.add(getBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询失败");
        } finally {
            JDBCHelper.closeConnection(res, ps, conn);
        }
        return data;

    }

    @Override
    public List<BookGrant> queryAllGrantBook() throws SQLException {
        Connection connection = JDBCHelper.getsInstance().getConnection();
//        Statement stat = connection.prepareStatement("SELECT * ")
        return null;
    }
}
