package com.isa.socket.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.isa.socket.main.DatabaseConnection;

@Service
public class MensajeriaSocketService {

	@Value("${api.postgres.socket.username}")
	private String username;
	@Value("${api.postgres.socket.password}")
	private String password;
	@Value("${api.postgres.socket.database}")
	private String database;
	@Value("${api.postgres.socket.host}")
	private String host;
	@Value("${api.postgres.socket.port}")
	private String puerto;

	@Async
	public void enviarMensajeSocket(String channel, String mensaje) throws SQLException {

		Connection databaseConnection = DatabaseConnection.getInstance(host, puerto, database, username, password)
				.getConnection();

		try {
			CallableStatement properCase = databaseConnection
					.prepareCall("{ call public.notify_minimo_inventario_new_1( ?, ? ) }");
			properCase.setString(1, channel);
			properCase.setString(2, mensaje);
			properCase.execute();
			databaseConnection.close();
		} catch (SQLException e) {
			databaseConnection.close();
		} finally {
			databaseConnection.close();
		}

	}

}
