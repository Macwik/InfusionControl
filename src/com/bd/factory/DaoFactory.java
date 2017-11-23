package com.bd.factory;

import com.bd.Control.SystemConfig;
import com.bd.Dao.IBedPatientBoundDao;
import com.bd.Dao.IInfusionEventDao;
import com.bd.Dao.IMessageDao;
import com.bd.Dao.IPatientDao;
import com.bd.Dao.IPhoneBedBoundDao;
import com.bd.Dao.IUserDao;

import logfile.log;

public class DaoFactory {
	private static DaoFactory factory = new DaoFactory();

	private DaoFactory() {
	}

	public static DaoFactory getFactory() {
		return factory;
	}

	public IUserDao getUserDao() {
		try {
			return (IUserDao) Class.forName(SystemConfig.dbProp.getProperty("UserDao")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.SqlGetException("getUserDao");
			return null;
		}

	}

	public IPatientDao getPatientDao() {
		try {
			return (IPatientDao) Class.forName(SystemConfig.dbProp.getProperty("PatientDao")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.SqlGetException("getPatientDao");
			return null;
		}

	}

	public IBedPatientBoundDao getBedPatientDao() {
		try {
			return (IBedPatientBoundDao) Class.forName(SystemConfig.dbProp.getProperty("BedPatientBoundDao"))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.SqlGetException("getBedPatientDao");
			return null;
		}

	}

	public IMessageDao getMessageDao() {
		try {
			return (IMessageDao) Class.forName(SystemConfig.dbProp.getProperty("MessageDao")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.SqlGetException("getMessageDao");
			return null;
		}
	}

	public IInfusionEventDao getInfusionEventDao() {
		try {
			return (IInfusionEventDao) Class.forName(SystemConfig.dbProp.getProperty("InfusionEventDao")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.SqlGetException("getInfusionEventDao");
			return null;
		}
	}

	public IPhoneBedBoundDao getPhoneBedBoundDao() {
		try {
			return (IPhoneBedBoundDao) Class.forName(SystemConfig.dbProp.getProperty("IPhoneBedBoundDao"))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.SqlGetException("getPhoneBedBoundDao");
			return null;
		}
	}
}
