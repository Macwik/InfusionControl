package com.bd.Dao;

public interface IMessageDao {
	/**
	 * 添加一条记录
	 */
	public void addMessage(String patientID, String date, String time, String message);

	/**
	 * 删除表
	 */
	public boolean DropTable(String table);

	/**
	 * 创建一张表
	 */
	public boolean CreateTable(String sql);
}
