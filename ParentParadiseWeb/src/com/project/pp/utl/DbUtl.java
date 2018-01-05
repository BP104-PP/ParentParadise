package com.project.pp.utl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtl {
	public final static String URL = "jdbc:mysql://localhost:3306/PPDB?useUnicode=true&characterEncoding=UTF-8";
	public final static String USER = "root";
	public final static String PASSWORD = "root";
	
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver"); 
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		conn.setAutoCommit(false);//
		return conn;
	}
}
