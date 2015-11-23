package com.chen.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.chen.exception.DataAccessException;



public class DBManager {
	
	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	
 	private static BasicDataSource dataSource;
	
	static{
	    
		dataSource = new BasicDataSource();		
		
		Properties prop = new Properties();
		try {
			prop.load(DBManager.class.getClassLoader().getResourceAsStream("config.properties"));
			driverName = prop.getProperty("jdbc.driver","com.mysql.jdbc.Driver");
			url = prop.getProperty("jdbc.url", "jdbc:mysql://localhost:3306/db_crm");
			username =  prop.getProperty("jdbc.username", "root");
			password = prop.getProperty("jdbc.password", "windmill");
			dataSource.setDriverClassName(driverName);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setInitialSize(5); //初始化连接池大小
			dataSource.setMaxActive(30);   //连接池最大活动大小
			dataSource.setMaxWait(5000);//等待时间
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new DataAccessException("数据库连接异常", e);
		}
	}
	
	
	/*
	static{
		try {
			Class.forName(DriverName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		try {
			return  DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	*/
	public static PreparedStatement getPreparedStatement(Connection conn, String sql){
		if(conn != null){
			try {
				return conn.prepareStatement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}else 
			return null;
		
	}
	
	public static Statement getStatement(Connection conn){
		if(conn != null){
			try {
				return conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}else 
			return null;
	}
	
	public static void closeStatement(Statement statement){
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	public static void closePrepareStatement(PreparedStatement ps){
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeConnection(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
