package com.demo.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	private static String url;
	private static String user;
	private static String password;
	private static String driver;

	static {
		try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
			Properties p = new Properties();
			p.load(in);
			url = p.getProperty("db.url");
			user = p.getProperty("db.user");
			password = p.getProperty("db.password");
			driver = p.getProperty("db.driver", "oracle.jdbc.OracleDriver");

			Class.forName(driver);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load DB config", e);
		}
	}

	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection("jdbc:oracle:thin:@//10.16.115.139/NSK23083", "RASDBT", "RASDBT");
	}
}
