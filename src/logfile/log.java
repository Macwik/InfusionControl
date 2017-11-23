package logfile;

import org.apache.log4j.Logger;

public class log {
	static Logger logger = Logger.getLogger(log.class.getName());

	public static void SqlInsertException(String e) {
		logger.error("SqlInsertException:" + e);
	}

	public static void SqlAddException(String e) {
		logger.error("SqlAddException:" + e);
	}

	public static void SqlGetException(String e) {
		logger.error("SqlGetException:" + e);
	}

	public static void SqlSetException(String e) {
		logger.error("SqlSetException:" + e);
	}

	public static void FileException(String e) {
		logger.error("SqlSetException:" + e);
	}
}
