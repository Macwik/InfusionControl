package com.bd.Manager;

import com.bd.Control.DeviceBedBound;

public class AboutBed {
	private AboutBed() {
	}

	// *添加病床和终端绑定
	public static boolean addBedDeviceBound(String bedNum, String deviceID) {
		return DeviceBedBound.addDeviceBedBound(bedNum, deviceID);
	}

	// *判断能否添加病床终端绑定
	public static boolean canBedBound(String bedNum) {
		return !DeviceBedBound.containsBed(bedNum);
	}
}
