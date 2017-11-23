package com.bd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.bd.JDBCUtil.JDBCUtils;

import logfile.log;

public class BedPatientBoundDaoImplement implements IBedPatientBoundDao {

	@Override
	public boolean addBedPatientBound(String patientID, String bedNo) {
		String sql = "insert into BedPatientBoundTable values (?,?,1)";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);
			ps.setString(2, bedNo);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlAddException("BedPatientBoundDaoImplement");
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
	public boolean setPatientLeaveHospital(String patientID) {
		String sql = "update BedPatientBoundTable set isInHospital =0 where patientID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlSetException("setPatientLeaveHospital");
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
	public HashMap<String, String> getBedPatientInfoMap() {

		HashMap<String, String> hm = new HashMap<String, String>();

		String sql = "select * from BedPatientBoundTable where isInHospital =1";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		String patientID = null;
		String BedNum = null;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				patientID = rs.getString("patientID");
				BedNum = rs.getString("BedNo");
				if (!patientID.equals(null) && !BedNum.equals(null))
					hm.put(patientID, BedNum);
			}
		} catch (SQLException e) {
			log.SqlGetException("getBedPatientInfoMap");
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
	public boolean canInsert(String patientID) {
		String sql = "select * from BedPatientBoundTable where patientID =?";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();

			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);

			rs = ps.executeQuery();

			if (rs.next())
				return false;
			else
				return true;
		} catch (SQLException e) {
			log.SqlInsertException("canInsert(String patientID)");
			return false;
		} finally {
			JDBCUtils.close(rs, stat, conn);
		}
	}

	@Override
	public boolean deleteBedPatientInfo(String patientID) {
		String sql = "delete * from BedPatientBoundTable where patientID =?";
		Connection conn = null;
		PreparedStatement ps = null;
		int i = -1;
		try {
			conn = JDBCUtils.getConn();

			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);

			i = ps.executeUpdate();

			return i > 0;
		} catch (SQLException e) {
			log.SqlInsertException("deleteBedPatientInfo");
			return false;
		} finally {
			JDBCUtils.close(null, null, conn);
		}
	}

}
