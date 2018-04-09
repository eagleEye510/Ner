package com.zkdp.nlp.hadoopner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class connect_mysql {
	int bufferSize = 20 * 1024 * 1024;//���ȡ�ļ��Ļ���Ϊ20MB
	ArrayList<String> urlstring = new ArrayList<String>();
	ArrayList<String> ner_idstring = new ArrayList<String>();
	

	static String dbName = "ner";
	static String password = "duba0406.";
	static String userName = "root";
	static String url = "jdbc:mysql://192.168.1.193:3306/ner";
	static String sql = "select * from url_title";
	Connection conn = null;

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

	public void readFile(String filename) throws SQLException, FileNotFoundException {
		File file = new File(filename);
		if (file.isFile() && file.exists()) {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			InputStreamReader isr = new InputStreamReader(bis);
			BufferedReader br = new BufferedReader(isr, bufferSize);
			int count = 0;// ������
			String lineTXT = null;
			PreparedStatement pstmt = null;
			String[] temp = null;
			Connection conn = getConnection();
			conn.setAutoCommit(false);// ���������ֶ��ύ���Լ���������
			String sql = "insert into title_url(url, ner_id) values (?, ?)";
			pstmt = conn.prepareStatement(sql);
			try {
				while ((lineTXT = br.readLine()) != null) {
					temp = lineTXT.split("	");
					pstmt.setString(1, temp[1]);
					pstmt.setInt(2,count);
					pstmt.addBatch();// ��PreparedStatement����������
					if (count!= 0) {// ��������500���������ʱ�����ύ
						pstmt.executeBatch();// ִ��������
						conn.commit();
						pstmt.clearBatch();
						//��ӡ���������
						//System.out.println("count: " + count);
					}
					count++;
				}
				pstmt.executeBatch();// ִ��������
				conn.commit();
				pstmt.close();
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void show() {
		System.out.println("This is string:");
		for (int i = 0; i < urlstring.size(); i++) {
			System.out.println(urlstring.get(i));
		}
		System.out.println("This is integer:");
		for (int i = 0; i < ner_idstring.size(); i++) {
			System.out.println(ner_idstring.get(i));
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		connect_mysql test = new connect_mysql();
		//test.show();
		long timeTestStart = System.currentTimeMillis();
		try {
			test.readFile("D://workspace//Eclipse//MapRed2//mysql1.txt");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("succeed");
		long timeTestEnd = System.currentTimeMillis();// ��¼����ʱ��
		long time = timeTestEnd - timeTestStart;
		long secondTime = time / 1000;
		System.out.println("Time:" + secondTime + " seconds");
	}
}
