package main.java.db;

import main.java.utils.Statics;

import java.sql.*;


/**
 * 初始化jdbc
 */
public class JDBCHelper {

    public static String url;
    public static String username;
    public static String password;


    private static volatile JDBCHelper sInstance;

    private JDBCHelper() {

    }

    //单例模式
    public static JDBCHelper getsInstance() {
        if (null == sInstance) {
            synchronized (JDBCHelper.class) {
                if (null == sInstance) {
                    sInstance = new JDBCHelper();
                }
            }
        }
        //保证单例后，初始化jdbc
        sInstance.initJDBC();
        return sInstance;
    }

    /**
     * 初始化jdbc，设置相关属性
     */
    private void initJDBC() {
        try {
            url = Statics.DBURL;
            username = Statics.USERNAME;
            password = Statics.PASSWORD;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库连接(单例)
     *
     * @return
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取数据库连接失败");
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @param res
     * @param stat
     * @param conn
     */
    public static void closeConnection(ResultSet res, Statement stat, Connection conn) {
        try {
            if (res != null) res.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
