package main.java.daoimpl;

import jdk.nashorn.internal.scripts.JD;
import main.java.bean.Teacher;
import main.java.dao.TeacherDao;
import main.java.db.JDBCHelper;

import java.sql.*;
import java.util.List;

/**
 * 操作Teacher表实现类
 * 具体的
 */
public class TeacherImpl implements TeacherDao {


    @Override
    public void addTeacher(Teacher teacher) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO TEACHER VALUES(?,?,?,?,?,?,?)";
        try {
            connection = JDBCHelper.getsInstance().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, teacher.getId());
            ps.setString(2, teacher.getName());
            ps.setString(3, teacher.getSex());
            ps.setString(4, teacher.getRight());
            ps.setString(5, teacher.getTel());
            ps.setString(6, teacher.getEmail());
            ps.setString(7, teacher.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("添加数据失败");
        } finally {
            JDBCHelper.closeConnection(null, ps, connection);
        }

    }

    @Override
    public void updateTeacher(Teacher teacher) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE Teacher SET Tname=?,Tsex=?,Ttel=?,Temail=?,Tpassword=? WHERE Tno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, teacher.getName());
            ps.setString(2, teacher.getSex());
            ps.setString(3, teacher.getTel());
            ps.setString(4, teacher.getEmail());
            ps.setString(5, teacher.getPassword());
            ps.setString(6, teacher.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCHelper.closeConnection(null, ps, conn);
        }
    }

    @Override
    public void deleteTeacher(Teacher teacher) throws SQLException {

    }


    /**
     * 教师登录,有两种权限，管理员和普通教师
     *
     * @param username
     * @param password
     * @param type     登录权限
     * @return
     * @throws SQLException
     */
    @Override
    public boolean validateLogin(String username, String password, String type) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        boolean isLogin;
        String resPassword = null;
        String sql = "SELECT Tpassword From Teacher WHERE Tno=? AND Tright=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, type);
            res = ps.executeQuery();
            if (res.next()) {
                System.out.println(res.getString(1));
                resPassword = res.getString(1).trim();
            }
            isLogin = resPassword.equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("查询失败");
        } finally {
            JDBCHelper.closeConnection(res, ps, conn);
        }
        return isLogin;
    }

    @Override
    public List<Teacher> queryAllTeacher() throws SQLException {
        return null;
    }

    @Override
    public Teacher queryTeacherById(String tno) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Teacher teacher = null;
        String sql = "SELECT * FROM Teacher WHERE Tno=?";
        try {
            conn = JDBCHelper.getsInstance().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tno);
            res = ps.executeQuery();
            if (res.next()) {
                teacher = new Teacher(tno, res.getString(2), res.getString(3),
                        res.getString(4), res.getString(5), res.getString(6),
                        res.getString(7));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCHelper.closeConnection(res, ps, conn);
        }
        return teacher;
    }

}
