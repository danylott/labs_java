package com.example.Servlet_OOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
	private static ConnectionPool cp = new ConnectionPool();
	
	private final String url = "jdbc:postgresql://balarama.db.elephantsql.com:5432/tjnttvbp";
	private final String user = "tjnttvbp";
	private final String password = "qaUTEcBGLLftne9QMvRYySo1CJKUVNxR";
	private final int MAX_CONNECTIONS = 5;
	
	private BlockingQueue<Connection> connections;
	
	private ConnectionPool() {
		try {
			 Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			 System.out.println("JDBC Driver not found");
			 e.printStackTrace();
		}
		
		connections = new LinkedBlockingQueue<Connection>(MAX_CONNECTIONS);		
		
		try {
			for(int i = 0; i < MAX_CONNECTIONS; ++i) {
				connections.put(DriverManager.getConnection(url,user,password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ConnectionPool getConnectionPool() {
		return cp;
	}
	
	public Connection getConnection() throws InterruptedException, SQLException {
		Connection c = connections.take();
		if (c.isClosed()) {
			c = DriverManager.getConnection(url,user,password);
		}
		return c;
	}
	
	public void releaseConnection(Connection c) throws InterruptedException {
		connections.put(c);
	}
}
