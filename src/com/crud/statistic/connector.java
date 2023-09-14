package com.crud.statistic;

import java.sql.Connection;
import java.sql.DriverManager;

public class connector {


	static Connection con ;
	static String driver = "com.mysql.jdbc.Driver";
	static String url ="jdbc:mysql://localhost:3308/CrudJava";
	static String uname = "root";
	static String pass = "";

	public static Connection ConnectDB() throws Exception{

		if (con == null) {
			Class.forName(driver);
			con = DriverManager.getConnection(url,uname,pass);
		}
		return con;
	}
}
