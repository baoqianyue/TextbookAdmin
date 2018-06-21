package main.java.dao;


import main.java.bean.Teacher;

import java.sql.SQLException;
import java.util.List;

/**
 * 定义对Teacher表的增删改查操作
 */
public interface TeacherDao {

    //增加一个教师
    public void addTeacher(Teacher teacher) throws SQLException;

    //更新一个教师信息
    public void updateTeacher(Teacher teacher) throws SQLException;

    //删除一个教师信息
    public void deleteTeacher(Teacher teacher) throws SQLException;

    //教师或管理员登录查询操作
    public boolean validateLogin(String username, String password, String type) throws SQLException;

    //查询所有教师信息
    public List<Teacher> queryAllTeacher() throws SQLException;

    //通过id查询教师信息
    public Teacher queryTeacherById(String tno) throws SQLException;


}
