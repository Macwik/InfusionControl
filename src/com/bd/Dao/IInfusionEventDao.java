package com.bd.Dao;

import java.util.ArrayList;

import com.bd.objects.InfusionEvent;

public interface IInfusionEventDao {

	// * 添加一条记录
	public void addInfusionEvent(InfusionEvent infusionEvent);

	// *获取某一个住院号的所有记录信息
	public ArrayList<InfusionEvent> getAllData(String patientID);

	// *获取某一病人在某一天的所有记录
	public ArrayList<InfusionEvent> getAllData(String patientID, String date);

	// * 删除表
	public boolean DropTable(String table);

	// * 创建一张表
	public boolean CreateTable(String sql);
}
