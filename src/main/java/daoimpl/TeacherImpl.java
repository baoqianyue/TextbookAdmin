package main.java.daoimpl;

import main.java.bean.Teacher;
import main.java.dao.TeacherDao;
import main.java.db.JDBCHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 操作Teacher表实现类
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

    }

    @Override
    public void deleteTeacher(Teacher teacher) throws SQLException {

    }

    @Override
    public int validateLogin(String username, String password, String type) throws SQLException {
        return 0;
    }

    @Override
    public List<Teacher> queryAllTeacher() throws SQLException {
        return null;
    }
}
