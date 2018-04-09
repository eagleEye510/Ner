package com.zkdp.nlp.hadoopner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectmysql {
	static String dbName = "historyurl";
	static String password = "duba0406.";
	static String userName = "root";
	static String url = "jdbc:mysql://192.168.1.193:3306/historyurl?characterEncoding=UTF-8";

	static String sql = "select * from historyurl";
	public static Connection getConnection() {
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
