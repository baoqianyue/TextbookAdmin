package main.java.daoimpl;

import main.java.bean.Clazz;
import main.java.dao.ClazzDao;
import main.java.db.JDBCHelper;
import main.java.utils.Statics;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 操作表Class实现类
 */
public class ClassImpl implements ClazzDao {
    @Override
    public void addClass(Clazz clazz) throws SQLException {

    }

    @Override
    public void updateClass(Clazz clazz) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE Class SET Tno=?,Cnum=?,Stel=?,Sno=?,Sname=?,Spassword=? WHERE Cno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, clazz.getTeacherId());
            ps.setInt(2, clazz.getClassNum());
            ps.setString(3, clazz.getClassTel());
            ps.setString(4, clazz.getLeaderId());
            ps.setString(5, clazz.getLeaderName());
            ps.setString(6, clazz.getClassPassword());
            ps.setString(7, clazz.getClassId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public void deleteClass(String classId) throws SQLException {

    }

    @Override
    public boolean validateClassLogin(String username, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        String resPassword = null;
        boolean isLogin;
        String sql = "SELECT Spassword FROM Class WHERE Cno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            res = ps.executeQuery();
            if (res.next()) {
                resPassword = res.getString(1).trim();
                isLogin = resPassword.equals(password);
            } else {
                isLogin = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询失败");
        } finally {
            JDBCHelper.closeConnection(res, ps, conn);
        }
        return isLogin;
    }

    @Override
    public Clazz queryClassById(String cno) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Clazz clazz = null;
        String sql = "SELECT * FROM Class WHERE Cno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, cno);
            res = ps.executeQuery();
            if (res.next()) {
                clazz = new Clazz(cno, res.getString(2), res.getInt(3),
                        res.getString(4), res.getString(5), res.getString(6),
                        res.getString(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCHelper.closeConnection(res, ps, conn);
        }
        return clazz;
    }

    /**
     * 根据教师号来查询所管理的班级
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Clazz> queryALLClass() throws SQLException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        Clazz clazz;
        List<Clazz> classList = new ArrayList<>();
        String sql = "SELECT * FROM Class WHERE Tno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            stat = conn.prepareStatement(sql);
            stat.setString(1, Statics.CURR_USERNAME);
            res = stat.executeQuery();
            while (res.next()) {
                clazz = new Clazz();
                clazz.setClassId(res.getString(1));
                clazz.setTeacherId(res.getString(2));
                clazz.setClassNum(res.getInt(3));
                clazz.setClassTel(res.getString(4));
                clazz.setLeaderId(res.getString(5));
                clazz.setLeaderName(res.getString(6));
                clazz.setClassPassword(res.getString(7));
                classList.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询数据失败");
        } finally {
            JDBCHelper.closeConnection(res, stat, conn);
        }
        return classList;
    }
}
