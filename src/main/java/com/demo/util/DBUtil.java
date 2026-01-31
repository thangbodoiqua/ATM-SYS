package com.demo.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class DBUtil {
	private static String url;
	private static String user;
	private static String password;
	private static final Logger log = Logger.getLogger(DBUtil.class);

	static {
		// Chỉ đọc properties, không đăng ký driver ở đây nữa
		Properties p = new Properties();
		try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (in != null) {
				p.load(in);
				log.info("[DB] Loaded DB settings from db.properties");
			} else {
				log.warn("[DB] db.properties not found, will use hardcoded defaults.");
			}
		} catch (Exception e) {
			log.error("[DB] Failed to load db.properties, using defaults", e);
		}
		// Lấy giá trị từ properties, hoặc dùng default nếu không có
		url = p.getProperty("db.url", "jdbc:mysql://localhost:3306/atm_training");
		user = p.getProperty("db.user", "root");
		password = p.getProperty("db.password", "");
	}

	public static Connection getConnection() throws SQLException {
		if (url == null) {
			throw new IllegalStateException("DB properties (db.url) not loaded.");
		}
		log.info("[DB] Attempting to get connection for URL: " + url);
		return DriverManager.getConnection(url, user, password);
	}
}