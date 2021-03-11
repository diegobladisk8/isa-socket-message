package com.isa.socket.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static DatabaseConnection instance;

	private Connection connection;

	private DatabaseConnection(String host, String puerto, String database, String username, String password)
			throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			this.connection = DriverManager.getConnection(
					"jdbc:postgresql://" + host.trim() + ":" + puerto + "/" + database.trim(), username, password);
		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static DatabaseConnection getInstance(String host, String puerto, String database, String username,
			String password) throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection(host, puerto, database, username, password);
		} else if (instance.getConnection().isClosed()) {
			instance = new DatabaseConnection(host, puerto, database, username, password);
		}

		return instance;
	}

}
