package main.java.utils;

import main.java.controller.LoginController;
import main.java.controller.PersonController;
import main.java.controller.TextbookLibraryController;
import main.java.controller.teacher.TeacherAdminClassController;
import main.java.controller.teacher.TextbookSubController;

/**
 * 存放一些常量
 */
public class Statics {
    //连接数据库用到的常量
    public static final String DBURL = "jdbc:sqlserver://localhost;database=TextbookAdmin";
    public static final String USERNAME = "bao";
    public static final String PASSWORD = "123456";

    //教师身份标记
    public static final String SIMPLE_TEACHER = "0";
    public static final String ADMIN_TEACHER = "1";
    //当前用户的用户名,在查询每个老师所管理的班级时会用到
    public static String CURR_USERNAME;

    //用户身份标记
    public static final String TYPE_TEACHRE = "teacher";
    public static final String TYPE_ADMIN = "admin";
    public static final String TYPE_CLASS = "class";
    public static String TYPE_CURR;


    //各用户侧边栏内容
    public static String[] teacherSlideTitle = new String[]{
            "教材征订与管理", "教材库", "所管理班级并发放", "个人信息管理", "退出登录"
    };

    //各用户侧边栏对应Controller
    public static Class[] teacherSlideController = new Class[]{
            TextbookSubController.class,
            TextbookLibraryController.class,
            TeacherAdminClassController.class,
            PersonController.class,
            LoginController.class,

    };


}
