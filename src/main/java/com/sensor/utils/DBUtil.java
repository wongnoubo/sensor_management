package com.sensor.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
//解决连接
public class DBUtil {
    private static ComboPooledDataSource cpds=null;

    static{
        cpds=new ComboPooledDataSource("mysql");
    }

    public static Connection getConnection(){
        Connection connection=null;
        try {
            connection = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
