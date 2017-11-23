package com.bd.Dao;

import java.util.HashMap;

public interface IBedPatientBoundDao {

	/**
	 * 病床病人绑定表
	 * 
	 * @param bedNo
	 * @param patientID
	 * @return是否绑定成功
	 */
	public boolean addBedPatientBound(String patientID, String bedNo);

	/**
	 * 设置病人出院
	 * 
	 * @param patientID
	 * @return 设置病人出院是否成功
	 */
	public boolean setPatientLeaveHospital(String patientID);

	/**
	 * 获取病人病床绑定的map 病床号、住院号
	 */
	public HashMap<String, String> getBedPatientInfoMap();

	/**
	 * 判断能够添加一条记录
	 */
	public boolean canInsert(String patientID);

	/**
	 * 删除一条输液记录
	 * 
	 * @param patientID
	 * @return
	 */
	public boolean deleteBedPatientInfo(String patientID);

	/**
	 * 删除表
	 */
	public boolean DropTable(String table);

	/**
	 * 创建一张表
	 */
	public boolean CreateTable(String sql);
}
