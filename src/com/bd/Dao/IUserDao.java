package com.bd.Dao;

public interface IUserDao {
	/**
	 * 通过用户名查询密码
	 * 
	 * @param name
	 *            姓名
	 * @return 返回用户密码，没有用户名的话返回null
	 */
	public String findPassWordByName(String name);

	/**
	 * 通过用户ID查找用户名
	 */
	public String findUserNameByUserID(int id);

	/**
	 * 通过用户ID来查询用户密码
	 */
	public String findPswByUserID(int id);

	/**
	 * 查询用户是否已经登录
	 */
	public boolean findIslogin();

	/**
	 * 设置用户登录
	 */
	public boolean setLogin();

	/**
	 * 退出登录
	 */
	public boolean setExit();

	/**
	 * 添加用户
	 * 
	 * @param name
	 *            姓名
	 *            工资
	 */
	public void addUser(int id, String name, String passWord);

	/**
	 * 修改用户
	 */
	public void updateUser(String userName, String password);

	/**
	 * 查询表
	 * 
	 * @return
	 */
	public void SelectAll();

	/**
	 * 删除表
	 */
	public boolean DropTable(String table);

	/**
	 * 创建一张表
	 */
	public boolean CreateTable(String sql);
}
