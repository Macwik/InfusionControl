package com.bd.Manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.bd.Control.PhoneBedBound;
import com.bd.Control.InterfaceAndEnum.EBedPhoneStatus;
import com.bd.objects.PhoneInfo;

public class AboutPhone {

	public static HashMap<String, EBedPhoneStatus> phoneBedStatus = new HashMap<String, EBedPhoneStatus>();

	// *获取ID为phoneID的手机所绑定的病床List。
	public static ArrayList<String> getBedByPhone(String phoneID) {
		return PhoneBedBound.getBoundBed(phoneID);
	}

	// *从病床号找到所监控的手机ID返回ArrayList
	public static ArrayList<PhoneInfo> getPhoneByBed(String bedNum) {

		return PhoneBedBound.getBoundPhone(bedNum);
	}

	// *设置手机颜色
	public static boolean setPhoneColor(String phoneNum, boolean regi) {
		return PhoneBedBound.setPhoneRegi(phoneNum, regi);
	}

	// *设置手机状态，是否在线（不变色）
	public static boolean setPhoneOff(String phoneNum) {
		return PhoneBedBound.setPhoneOff(phoneNum);
	}

	public static boolean isPhoneReg(String phoneNum) {
		return PhoneBedBound.getPhoneRegi(phoneNum);
	}

	public static void setBedPhoneStatus(String BedNo, EBedPhoneStatus ebd) {
		phoneBedStatus.put(BedNo, ebd);
	}

	public static EBedPhoneStatus getBedPhoneStatus(String BedNo) {
		return phoneBedStatus.get(BedNo);
	}

	public static boolean isConfirm(String bedNo) {
		if (!phoneBedStatus.containsKey(bedNo))
			return false;
		return phoneBedStatus.get(bedNo).equals(EBedPhoneStatus.CONFIRM);
	}

}
