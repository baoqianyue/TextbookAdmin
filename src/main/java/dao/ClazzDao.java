package main.java.dao;

import main.java.bean.Clazz;

import java.sql.SQLException;
import java.util.List;

public interface ClazzDao {

    //增加一个班级
    public void addClass(Clazz clazz) throws SQLException;

    //更新一个班级信息
    public void updateClass(String classId) throws SQLException;

    //删除一个班级
    public void deleteClass(String classId) throws SQLException;

    //根据id查询一个班级记录
    public Clazz queryClassById(String cno) throws SQLException;

    //查询表中所有记录
    public List<Clazz> queryALLClass() throws SQLException;


}
