package com.bd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.bd.Control.Util.StringUtil;
import com.bd.JDBCUtil.JDBCUtils;

import logfile.log;

public class PhoneBedBoundImplement implements IPhoneBedBoundDao {

	@Override
	public boolean addBedPatientBound(String phoneID, String imei, String bedNoList) {
		String sql = "insert into PhoneBedBoundTable values (?,?,?,0)";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, phoneID);
			ps.setString(2, imei);
			ps.setString(3, bedNoList);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlAddException("addBedPatientBound");
			return false;
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		if (status == 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean setPhoneOffline(String phoneID) {
		String sql = "update PhoneBedBoundTable set isOnline=0,imei=?,bedNo=? where phoneID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, "");
			ps.setString(3, phoneID);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlSetException("setPhoneOffline");
			return false;
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		if (status == 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean setPhoneOnline(String phoneID) {
		String sql = "update PhoneBedBoundTable set isOnline =1 where phoneID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, phoneID);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlGetException("setPhoneOnline");
			return false;
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		if (status == 0)
			return false;
		else
			return true;
	}

	@Override
	public HashMap<String, ArrayList<String>> getPhoneBedBoundMap() {
		HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

		String sql = "select * from PhoneBedBoundTable where isOnline =1";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		String phoneID = null;
		String bedNo = null;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				phoneID = rs.getString("phoneID");
				bedNo = rs.getString("bedNo");
				if (!phoneID.equals(null) && !bedNo.equals(null))
					hm.put(phoneID, StringUtil.getArrayList(bedNo));
			}
		} catch (SQLException e) {
			log.SqlGetException("getPhoneBedBoundMap");
			return null;
		} finally {
			JDBCUtils.close(rs, stat, conn);
		}
		return hm;
	}

	@Override
	public HashMap<String, String> getPhoneImeiBoundMap() {
		HashMap<String, String> hm = new HashMap<String, String>();

		String sql = "select * from PhoneBedBoundTable where isOnline =1";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		String phoneID = null;
		String imei = null;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				phoneID = rs.getString("phoneID");
				imei = rs.getString("imei");
				if (!phoneID.equals(null) && !imei.equals(null))
					hm.put(phoneID, imei);
			}
		} catch (SQLException e) {
			log.SqlGetException("getPhoneImeiBoundMap");
			return null;
		} finally {
			JDBCUtils.close(rs, stat, conn);
		}
		return hm;
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

	@Override
	public void updatePhoneBedBound(String phoneID, String bedNo) {
		String sql = "update PhoneBedBoundTable set bedNo =? where phoneID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, bedNo);
			ps.setString(2, phoneID);
			ps.executeUpdate();
			ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlSetException("updatePhoneBedBound");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}

	}

	@Override
	public void updatePhoneImei(String phoneID, String imei) {
		String sql = "update PhoneBedBoundTable set imei =? where phoneID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, imei);
			ps.setString(2, phoneID);
			ps.executeUpdate();
			ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlSetException("updatePhoneImei");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
	}

}
