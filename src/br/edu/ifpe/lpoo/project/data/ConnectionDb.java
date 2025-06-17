package br.edu.ifpe.lpoo.project.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.edu.ifpe.lpoo.project.exceptions.ExceptionLivroDb;

public class ConnectionDb {
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testelivro", "root", "root123");
			}catch(SQLException e) {
				throw new ExceptionLivroDb(e.getMessage());
			}
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				throw new ExceptionLivroDb(e.getMessage());
			}
		}
	}
}
