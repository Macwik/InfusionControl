package com.bd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.bd.JDBCUtil.JDBCUtils;

import logfile.log;

public class MessageDaoImplement implements IMessageDao {

	@Override
	public void addMessage(String patientID, String date, String time, String message) {
		String sql = "insert into MessageTable values (null,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);
			ps.setString(2, date);
			ps.setString(3, time);
			ps.setString(4, message);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlAddException("addMessage");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
	}

	@Override
	public boolean DropTable(String table) {
		String sql = "drop table if exists " + table;
		Connection conn = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			status = stat.executeUpdate(sql);
		} catch (SQLException e) {
			log.SqlSetException("DropTable");
			return false;
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		if (status > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean CreateTable(String sql) {
		Connection conn = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			status = stat.executeUpdate(sql);
		} catch (SQLException e) {
			log.SqlSetException("CreateTable");
			return false;
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		if (status > 0)
			return true;
		else
			return false;
	}

}
