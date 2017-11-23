package com.bd.Control;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.bd.Control.InterfaceAndEnum.EErrorStatus;
import com.bd.Dao.IBedPatientBoundDao;
import com.bd.Dao.IInfusionEventDao;
import com.bd.Dao.IPatientDao;
import com.bd.Dao.IPhoneBedBoundDao;
import com.bd.Dao.IUserDao;
import com.bd.factory.DaoFactory;

import DBSource.ConnectionPool;

public class SystemConfig {

	public static Properties dbProp = null;
	public static Properties phoneIMEIprop = null;
	public static Properties deviceProp = null;

	public static File deviceFile = null;

	public static ConnectionPool connPool = null;

	public static IUserDao userDao;
	public static IPatientDao patientDao;
	public static IBedPatientBoundDao bedPatientBoundDao;
	public static IInfusionEventDao infusionEventDao;
	public static IPhoneBedBoundDao phoneBedBoundDao;

	private static boolean isFileNormal = true;
	private static boolean isDBNormal = true;

	static {
		dbProp = new Properties();
		phoneIMEIprop = new Properties();
		deviceProp = new Properties();

		File dbFile = new File(new File("").getAbsoluteFile(), "config" + File.separator + "database.properties");

		File phoneImeiFile = new File(new File("").getAbsoluteFile(),
				"config" + File.separator + "phoneIMEI.properties");
		deviceFile = new File(new File("").getAbsoluteFile(), "config" + File.separator + "device.properties");

		FileReader dbFr;
		try {
			dbFr = new FileReader(dbFile);
			dbProp.load(dbFr);
		} catch (IOException e) {
			isFileNormal = false;
		}
		FileReader phoneIMEIFr;
		try {
			phoneIMEIFr = new FileReader(phoneImeiFile);
			phoneIMEIprop.load(phoneIMEIFr);
		} catch (IOException e) {
			isFileNormal = false;
		}
		FileReader deviceFr;
		try {
			deviceFr = new FileReader(deviceFile);
			deviceProp.load(deviceFr);
		} catch (IOException e) {
			isFileNormal = false;
		}

		// connPool = new ConnectionPool("org.sqlite.JDBC",
		// "jdbc:sqlite:Infusion.db3");
		//
		// try {
		// connPool.createPool();
		// } catch (SQLException e) {
		// isDBNormal = false;
		// }

		userDao = DaoFactory.getFactory().getUserDao();
		patientDao = DaoFactory.getFactory().getPatientDao();
		bedPatientBoundDao = DaoFactory.getFactory().getBedPatientDao();
		infusionEventDao = DaoFactory.getFactory().getInfusionEventDao();
		phoneBedBoundDao = DaoFactory.getFactory().getPhoneBedBoundDao();
	}

	public static EErrorStatus init() {
		if (!isFileNormal)
			return EErrorStatus.FILE_NOT_FOUND;
		if (!isDBNormal || userDao == null || patientDao == null || bedPatientBoundDao == null
				|| infusionEventDao == null || phoneBedBoundDao == null)
			return EErrorStatus.DATABASE_ERROR;
		return null;
	}

	public static String errorStatus(EErrorStatus e) {
		String str = null;
		if (e.equals(EErrorStatus.FILE_NOT_FOUND))
			str = "文件加载出错，系统退出！！";
		else if (e.equals(EErrorStatus.DATABASE_ERROR))
			str = "数据库加载出错，系统退出！！";
		else
			str = "系统异常，请重新打开！！！";
		return str;
	}
}
