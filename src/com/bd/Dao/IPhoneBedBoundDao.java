package com.bd.Dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPhoneBedBoundDao {
	/**
	 * 手机病床绑定表
	 * 
	 * @param phoneID
	 * @param bedNoList
	 * @return是否绑定成功
	 */
	public boolean addBedPatientBound(String phoneID, String imei, String bedNoList);

	/*
	 * 更新绑定
	 */
	public void updatePhoneBedBound(String phoneID, String bedNo);

	/*
	 * 更新绑定
	 */
	public void updatePhoneImei(String phoneID, String imei);

	/*
	 * 获取手机和IMEI绑定
	 */
	public HashMap<String, String> getPhoneImeiBoundMap();

	/**
	 * 设置手机状态（是否在线）
	 * 
	 * @param phoneID
	 * @return 设置手机状态（是否在线）
	 */
	public boolean setPhoneOffline(String phoneID);

	public boolean setPhoneOnline(String phoneID);

	/**
	 * 获取病人病床绑定的map 病床号、住院号
	 */
	public HashMap<String, ArrayList<String>> getPhoneBedBoundMap();

	// /**
	// * 判断能够添加一条记录
	// */
	// public boolean canInsert(String phoneID);

	/**
	 * 删除表
	 */
	public boolean DropTable(String table);

	/**
	 * 创建一张表
	 */
	public boolean CreateTable(String sql);
}
