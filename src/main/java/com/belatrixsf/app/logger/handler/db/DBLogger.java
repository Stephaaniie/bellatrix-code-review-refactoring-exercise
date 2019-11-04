package com.belatrixsf.app.logger.handler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.logging.StreamHandler;

/**
 * DB Logger Handler
 * @author emilio.watemberg
 */
public class DBLogger extends StreamHandler {

	private static Map dbParams;
	private static Connection connection;
	private static Properties connectionProps;
	private static Statement statament;

	public static void executeUpdate(Map dbParamsMap, String updateQuery) throws SQLException {
		try {
			dbParams = dbParamsMap;
			connectionProps = new Properties();
			connectionProps.put("user", dbParams.get("userName"));
			connectionProps.put("password", dbParams.get("password"));
			connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
					+ ":" + dbParams.get("portNumber") + "/", connectionProps);
			statament = connection.createStatement();
			statament.executeUpdate(updateQuery);
		} finally {
			connection.close();
			statament.close();
		}
	}

}
