/**
 *输液终端绑定表相关
 */
package com.bd.Control;

import java.util.HashSet;
import java.util.Iterator;

public class DeviceBound {

	public static HashSet<String> getDeviceSet() {
		HashSet<String> hs = new HashSet<String>();
		Iterator<Object> iter = SystemConfig.deviceProp.keySet().iterator();
		while (iter.hasNext()) {
			hs.add(SystemConfig.deviceProp.getProperty((String) iter.next()));
		}
		return hs;
	}

	// * 终端注册列表
	private static HashSet<String> deviceBoundSet = getDeviceSet();

	// * 获取Set对象
	public static HashSet<String> getDeviceBoundSet() {
		return deviceBoundSet;
	}

	// * 判断设备编号是否已经注册过
	public static boolean containDevice(String deviceNum) {
		return deviceBoundSet.contains(deviceNum);
	}

	// *终端注册
	public static boolean addDevice(String deviceNum) {
		return deviceBoundSet.add(deviceNum);
	}

	// *获取总共注册过的终端数量
	public static int getNumofDevice() {
		return deviceBoundSet.size();
	}

	// *删除编号为deviceNum 的终端
	public static void deleteDevice(String deviceNum) {
		deviceBoundSet.remove(deviceNum.trim());
	}

	// * 删除全部注册过的终端
	public static void deleteAllDevice() {
		deviceBoundSet.clear();
	}

}
