package com.bd.Dao;

import com.bd.JDBCUtil.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientAddDao {

    public boolean addPatientAndBedPatient(String patientID, String bedNo, String patientName, String PatientSex, String PatientAge,
                                           String PatientDisease) {
        String sql = "insert into BedPatientBoundTable values (?,?,1)";
        String sql2 = "insert into PatientTable values (?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            conn = JDBCUtils.getConn();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps2 = conn.prepareStatement(sql2);
            ps.setString(1, patientID);
            ps.setString(2, bedNo);
            ps2.setString(1, patientID);
            ps2.setString(2, patientName);
            ps2.setString(3, PatientSex);
            ps2.setString(4, PatientAge);
            ps2.setString(5, PatientDisease);
            ps.execute();
            ps2.execute();
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
                return false;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JDBCUtils.close(null, ps, conn);
        }
        return false;
    }

}
