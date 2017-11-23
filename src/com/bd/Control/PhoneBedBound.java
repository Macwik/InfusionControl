package com.bd.Control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.bd.Control.InterfaceAndEnum.IPhoneBedBoundListener;
import com.bd.objects.PhoneInfo;

public class PhoneBedBound {
	private static ConcurrentHashMap<String, String> bedPhongAlarm = new ConcurrentHashMap<String, String>();
	private static ConcurrentHashMap<PhoneInfo, ArrayList<String>> phoneBedMap = new ConcurrentHashMap<PhoneInfo, ArrayList<String>>();

	private static HashSet<IPhoneBedBoundListener> pbListeners = new HashSet<IPhoneBedBoundListener>();

	public static void addPhoneBedBoundListener(IPhoneBedBoundListener ipb) {
		pbListeners.add(ipb);
	}

	public static void addBedAlarm(String bedNo, String phoneID) {
		if (!bedPhongAlarm.containsKey(bedNo)) {
			bedPhongAlarm.put(bedNo, phoneID);
		}
	}

	public static void removeBedAlarm(String bedNo) {
		if (bedPhongAlarm.containsKey(bedNo))
			bedPhongAlarm.remove(bedNo);
	}

	public static String getConfirmPhone(String bedNo) {
		if (bedPhongAlarm.containsKey(bedNo))
			return bedPhongAlarm.get(bedNo);
		return null;
	}

	public static void removePhoneBedBound(String phoneNum) {
		PhoneInfo pi = new PhoneInfo(phoneNum);
		if (phoneBedMap.containsKey(pi))
			phoneBedMap.remove(pi);
	}

	// * 验证手机未被添加
	public static boolean notContains(String pi) {
		return !phoneBedMap.containsKey(pi);
	}

	public static ConcurrentHashMap<PhoneInfo, ArrayList<String>> getPhoneBedMap() {
		return phoneBedMap;
	}

	// *向手机病床绑定表里添加一条数据，手机ID和所绑定病床编号。
	public static boolean addPhoneBedBound(String phoneID, ArrayList<String> bed) {
		if (notContains(phoneID)) {
			phoneBedMap.put(new PhoneInfo(phoneID), bed);
			return true;
		}
		return false;
	}

	// *获取ID为phoneID的手机所绑定的病床List。
	public static ArrayList<String> getBoundBed(String phoneID) {
		return phoneBedMap.get(new PhoneInfo(phoneID));
	}

	// *从病床号找到所监控的手机ID返回ArrayList
	public static ArrayList<PhoneInfo> getBoundPhone(String bedNum) {
		ArrayList<PhoneInfo> boundPhone = new ArrayList<PhoneInfo>();
		Iterator<PhoneInfo> it = phoneBedMap.keySet().iterator();
		while (it.hasNext()) {
			PhoneInfo phoneNo = it.next();
			if (getBoundBed(phoneNo.getPhoneId()).contains(bedNum))
				boundPhone.add(phoneNo);
		}
		return boundPhone;
	}

	// *从病床号找到所监控的手机ID返回String
	public static String getBoundPhoneString(String bedNum) {
		String bound = "";
		String i = "";
		Iterator<PhoneInfo> it = phoneBedMap.keySet().iterator();
		while (it.hasNext()) {
			i = it.next().getPhoneId();
			if (getBoundBed(i).contains(bedNum))
				bound += (i + "  ");
		}
		return bound;
	}

	// *通过手机编号获取映射
	public static Entry<PhoneInfo, ArrayList<String>> getPhoneEntry(String phoneNo) {
		Iterator<Entry<PhoneInfo, ArrayList<String>>> iter = phoneBedMap.entrySet().iterator();
		Entry<PhoneInfo, ArrayList<String>> entry;
		PhoneInfo pi = new PhoneInfo(phoneNo);
		while (iter.hasNext()) {
			entry = iter.next();
			if (pi.equals(entry.getKey()))
				return entry;
		}
		return null;
	}

	// *修改编号为phoneNo的手机的信息
	public static boolean setPhoneRegi(String phoneNo, boolean regi) {
		Entry<PhoneInfo, ArrayList<String>> entry = getPhoneEntry(phoneNo);
		if (entry != null) {
			// if(entry.getKey().isRegiFlag()!=regi){
			entry.getKey().setRegiFlag(regi);
			notifyUpdate(phoneNo);
			return true;
			// }
		}
		return false;
	}

	// *修改编号为phoneNo的手机的信息
	public static boolean setPhoneOff(String phoneNo) {
		Entry<PhoneInfo, ArrayList<String>> entry = getPhoneEntry(phoneNo);
		if (entry != null) {
			entry.getKey().setRegiFlag(false);
			return true;
		}
		return false;
	}

	// *查询编号为phoneNo的手机的状态
	public static boolean getPhoneRegi(String phoneNo) {
		Entry<PhoneInfo, ArrayList<String>> entry = getPhoneEntry(phoneNo);
		if (entry != null) {
			return entry.getKey().isRegiFlag();
		}
		return false;
	}

	private static void notifyUpdate(String phoneNo) {
		for (IPhoneBedBoundListener bound : pbListeners) {
			bound.update(phoneNo);
		}
	}
}
