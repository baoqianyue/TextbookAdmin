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
    public void updateClass(String classId) throws SQLException {

    }

    @Override
    public void deleteClass(String classId) throws SQLException {

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
