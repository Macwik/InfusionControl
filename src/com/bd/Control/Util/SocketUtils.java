package com.bd.Control.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @ClassName: SocketUtils
 * @Description: 用于解决通过端口号查看监听的PID和线程名
 * @author zhangc
 * @date 2017年3月11日 下午3:26:10
 *
 */
public class SocketUtils {

	public static boolean isrunning() {
		String str = getPID();
		return !(str == null || str.isEmpty());

	}

	/**
	 * 获取PID，通过端口号
	 * 
	 * @return 进程的PID
	 */
	public static String getPID() {
		InputStream is = null;
		BufferedReader br = null;
		String pid = null;
		try {
			String[] args = new String[] { "cmd.exe", "/c", "netstat -aon|findstr", "7000" };
			is = Runtime.getRuntime().exec(args).getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String temp = br.readLine();
			if (temp != null) {
				String[] strs = temp.split("\\s");
				pid = strs[strs.length - 1];
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pid;
	}

	// 根据进程ID得到映像名称
	public static String getProgramName(String pid) {
		InputStream is = null;
		BufferedReader br = null;
		String programName = null;
		try {
			String[] args = new String[] { "cmd.exe", "/c", "tasklist|findstr", pid };
			is = Runtime.getRuntime().exec(args).getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String temp = br.readLine();
			if (temp != null) {
				String[] strs = temp.split("\\s");
				programName = strs[0];
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return programName;
	}
}
