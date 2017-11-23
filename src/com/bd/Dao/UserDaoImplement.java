package com.bd.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bd.JDBCUtil.JDBCUtils;

import logfile.log;

public class UserDaoImplement implements IUserDao {

	@Override
	public String findPassWordByName(String name) {
		String sql = "select * from User where userName=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String password = "";
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			password = rs.getString("password");
		} catch (SQLException e) {
			log.SqlGetException("findPassWordByName");
			return null;
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return password;
	}

	@Override
	public String findPswByUserID(int id) {
		String sql = "select * from User where userID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String password = "";
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			password = rs.getString("password");
		} catch (SQLException e) {
			log.SqlGetException("findPswByUserID");
			return null;
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return password;
	}

	@Override
	public String findUserNameByUserID(int id) {
		String sql = "select * from User where userID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userName = "";
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			userName = rs.getString("userName");
		} catch (SQLException e) {
			log.SqlGetException("findUserNameByUserID");
			return null;
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return userName;
	}

	@Override
	public void SelectAll() {
		String sql = "select * from User";
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) { // 将查询到的数据打印出来
				System.out.println("name =" + rs.getString("userName") + "," + "password=" + rs.getString("password"));
			}
		} catch (SQLException e) {
			log.SqlGetException("SelectAll");
		} finally {
			JDBCUtils.close(rs, stat, conn);
		}
	}

	@Override
	public void addUser(int id, String name, String password) {
		String sql = "insert into User values (?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlGetException("addUser");
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

	@Override
	public void updateUser(String userName, String password) {

		String sql = "update User set userName =?,password =? where userID=2";
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.SqlSetException("updateUser");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
	}

	@Override
	public boolean findIslogin() {
		String sql = "select * from User where userID=2";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int isLogin = 0;
		try {
			conn = JDBCUtils.getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			isLogin = rs.getInt("isLogin");
		} catch (SQLException e) {
			log.SqlGetException("findIslogin");
			return false;
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return isLogin > 0;
	}

	@Override
	public boolean setLogin() {
		String sql = "update User set isLogin =1 where userID=2";
		Connection conn = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			status = stat.executeUpdate(sql);
		} catch (SQLException e) {
			log.SqlSetException("setLogin");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		return status > 0;
	}

	@Override
	public boolean setExit() {
		String sql = "update User set isLogin =0 where userID=2";
		Connection conn = null;
		Statement stat = null;
		int status = 0;
		try {
			conn = JDBCUtils.getConn();
			stat = conn.createStatement();
			status = stat.executeUpdate(sql);
		} catch (SQLException e) {
			log.SqlSetException("setExit");
		} finally {
			JDBCUtils.close(null, stat, conn);
		}
		return status > 0;
	}

}
