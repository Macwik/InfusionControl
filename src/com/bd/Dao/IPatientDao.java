package com.bd.Dao;

import com.bd.objects.PatientInfo;

public interface IPatientDao {

	/**
	 * 添加一条病人记录
	 * 
	 * @param patientID
	 * @param patientName
	 * @param PatientSex
	 * @param PatientAge
	 * @param PatientDisease
	 * @return 添加是否成功
	 */
	public boolean addPatient(String patientID, String patientName, String PatientSex, String PatientAge,
			String PatientDisease);

	/**
	 * 通过住院号查询病人信息
	 * 
	 * @param PatientID
	 * @return 病人信息
	 */
	public PatientInfo getPatientInfo(String PatientID);

	/**
	 * 判断能否插入一条记录
	 * 
	 * @param patientID
	 * @return
	 */
	public boolean canInsert(String patientID);

	/**
	 * 删除一条病人记录
	 * 
	 * @param patientID
	 * @return
	 */
	public boolean deletePatient(String patientID);

	/**
	 * 删除表
	 */
	public boolean DropTable(String table);

	/**
	 * 创建一张表
	 */
	public boolean CreateTable(String sql);
}
