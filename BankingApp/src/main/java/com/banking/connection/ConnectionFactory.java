package com.banking.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static final String URL = System.getenv("DB_URL");/// * Insert URL Here
																/// */"jdbc:postgresql://database-1.cu31px5trjq3.us-east-1.rds.amazonaws.com:5432/postgres";
	public static final String USER = System.getenv("DB_USER");/// * Insert Username Here */ "postgres";
	public static final String PASS = System.getenv("DB_PASS");/// * Insert password Here */ "Revature19$";

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			return con;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void closeConnection(Connection c) {
		if (null != c) {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
