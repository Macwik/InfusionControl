package com.bd.JDBCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bd.Control.SystemConfig;

import logfile.log;

public class JDBCUtils {

	public static Connection getConn() throws SQLException {
		try {
			Class.forName(SystemConfig.dbProp.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			log.SqlGetException("getConn");
		}
		return DriverManager.getConnection("jdbc:sqlite:Infusion.db3");

	}

	public static void close(ResultSet rs, Statement stat, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			} finally {
				rs = null;
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
			} finally {
				stat = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			} finally {
				conn = null;
			}
		}
	}
}
