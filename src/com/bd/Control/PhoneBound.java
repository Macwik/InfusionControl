/**
 * 手机绑定
 */
package com.bd.Control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import com.bd.Control.InterfaceAndEnum.IPhoneBound;

public class PhoneBound {
	// *手机编号和手机IMEI映射map
	private static HashMap<String, String> phoneIMEIMap = new HashMap<String, String>();

	private static HashSet<IPhoneBound> piListeners = new HashSet<IPhoneBound>();

	public static void addPhoneBoundListener(IPhoneBound ibp) {
		piListeners.add(ibp);
	}

	// *验证手机ID和手机IMEI都未被添加
	public static boolean notContains(String phoneNo, String imei) {
		return !phoneIMEIMap.containsKey(phoneNo) && !phoneIMEIMap.containsValue(imei);
	}

	// *增加手机和手机IMEI的绑定
	public static boolean addPhoneBound(String phoneNo, String imei) {
		if (!notContains(phoneNo, imei)) {
			return false;
		}
		synchronized (phoneIMEIMap) {
			phoneIMEIMap.put(phoneNo, imei);
		}
		notifyUpdate(phoneNo);
		return true;
	}

	// 移除手机IMEI绑定
	public static boolean removePhoneBound(String phoneNo, String imei) {
		boolean result = false;
		synchronized (phoneIMEIMap) {
			result = phoneIMEIMap.remove(phoneNo) != null;
		}

		if (result) {
			notifyRemove(phoneNo);
		}
		return result;
	}

	// *通过手机编号获取手机IMEI
	public static String getPhoneIMEI(String phoneNo) {
		synchronized (phoneIMEIMap) {
			return phoneIMEIMap.get(phoneNo);
		}
	}

	private static void notifyUpdate(String phoneNo) {
		for (IPhoneBound bound : piListeners) {
			bound.update(phoneNo);
		}
	}

	private static void notifyRemove(String phoneNo) {
		for (IPhoneBound bound : piListeners) {
			bound.remove(phoneNo);
		}
	}

	// 获取手机ID和手机IMEI对应Map
	public static HashMap<String, String> getPhoneBound() {
		return phoneIMEIMap;
	}

	public static synchronized Iterator<Entry<String, String>> getAllBound() {
		return phoneIMEIMap.entrySet().iterator();
	}

}
