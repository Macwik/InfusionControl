package com.bd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bd.JDBCUtil.JDBCUtils;
import com.bd.objects.PatientInfo;

import logfile.log;

public class PatientDaoImplement implements IPatientDao {

	@Override
	public boolean addPatient(String patientID, String patientName, String PatientSex, String PatientAge,
			String PatientDisease) {
		String sql = "insert into PatientTable values (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);
			ps.setString(2, patientName);
			ps.setString(3, PatientSex);
			ps.setString(4, PatientAge);
			ps.setString(5, PatientDisease);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlAddException("addPatient");
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
	public PatientInfo getPatientInfo(String patientID) {
		String sql = "select * from PatientTable where patientID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientID);
			rs = ps.executeQuery();
			if (rs.next()) {
				PatientInfo pi = new PatientInfo(patientID);
				pi.setPatientName(rs.getString("patientName"));
				pi.setPatientGender(rs.getString("patientSex"));
				pi.setPatientAge(rs.getString("patientAge"));
				pi.setPatientSymptom(rs.getString("patientDisease"));
				return pi;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.SqlGetException("getPatientInfo");
			return null;
		} finally {
			JDBCUtils.close(rs, ps, conn);
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

	@Override
	public boolean canInsert(String patientID) {
		String sql = "select * from PatientTable where patientID =?";
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
	public boolean deletePatient(String patientID) {
		String sql = "delete * from PatientTable where patientID=?";
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
			log.SqlGetException("deletePatientInfo");
			return false;
		} finally {
			JDBCUtils.close(null, ps, conn);
		}
	}

}
