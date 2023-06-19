package com.laowang.mymall.mallmbg.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: my-mall
 * @description: 连接mymall数据库并操作的工具类
 * @author: Laowang
 * @create: 2023-06-16 10:18
 */
public class MymallDBHelper {

    private static String driverClass = "com.mysql.cj.jdbc.Driver";
    private static String connectionURL = "jdbc:mysql://192.168.3.128:33801/tlmallswarmmymall?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private static String userId = "tlmalladmin";
    private static String password = "Tlmall_123456";
    private static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL,userId,password);
        } catch (ClassNotFoundException | SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static List<String> getAllTableNamesFromMymall() {
        List<String> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = MymallDBHelper.getConnection();
            if (connection == null){
                throw new Exception("无法连接数据库");
            }
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = metaData.getTables(connection.getCatalog(), null, "%", types);
            while (resultSet.next()){
                result.add(resultSet.getString(3));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return result;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
