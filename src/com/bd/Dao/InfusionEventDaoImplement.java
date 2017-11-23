package com.bd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bd.JDBCUtil.JDBCUtils;
import com.bd.objects.InfusionEvent;

import logfile.log;

public class InfusionEventDaoImplement implements IInfusionEventDao {

	@Override
	public void addInfusionEvent(InfusionEvent infusionEvent) {
		String sql = "insert into InfusionEventTable values (null,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, infusionEvent.getPatientID());
			ps.setString(2, infusionEvent.getDate());
			ps.setString(3, infusionEvent.getTime());
			ps.setString(4, infusionEvent.getEvent());
			ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlAddException("addInfusionEvent");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
	}

	@Override
	public ArrayList<InfusionEvent> getAllData(String patientId) {

		ArrayList<InfusionEvent> arrayList = new ArrayList<InfusionEvent>();

		String sql = "select * from InfusionEventTable where patientID =?";
		Connection conn = null;
		Statement stat = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientId);
			rs = ps.executeQuery();

			while (rs.next()) {
				InfusionEvent ie = new InfusionEvent(patientId);
				ie.setDate(rs.getString("date"));
				ie.setTime(rs.getString("time"));
				ie.setEvent(rs.getString("event"));
				arrayList.add(ie);
			}
		} catch (SQLException e) {
			log.SqlGetException("getAllData");
			return null;
		} finally {
			JDBCUtils.close(rs, stat, conn);
		}
		return arrayList;
	}

	@Override
	public ArrayList<InfusionEvent> getAllData(String patientId, String date) {

		ArrayList<InfusionEvent> arrayList = new ArrayList<InfusionEvent>();

		String sql = "select * from InfusionEventTable where patientID =? and date =?";
		Connection conn = null;
		Statement stat = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientId);
			ps.setString(2, date);
			rs = ps.executeQuery();

			while (rs.next()) {
				InfusionEvent ie = new InfusionEvent(patientId);
				ie.setDate(rs.getString("date"));
				ie.setTime(rs.getString("time"));
				ie.setEvent(rs.getString("event"));
				arrayList.add(ie);
			}
		} catch (SQLException e) {
			log.SqlGetException(" getAllData(String patientId, String date)");
			return null;
		} finally {
			JDBCUtils.close(rs, stat, conn);
		}
		return arrayList;
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
