package com.bd.Manager;

import com.bd.Control.DeviceBedBound;
import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;

public class SetDevice {
	// * 修改终端号为deviceID的终端所绑定的病床的颜色显示
	public static boolean setDeviceStatusColor(String deviceID, EDeviceStatusColorEnum statusColor) {
		return DeviceBedBound.setDeviceStatusColor(deviceID, statusColor);
	}

	// * 修改终端号为deviceID的终端所绑定的病床的滴速显示
	public static boolean setDeviceCurrentSpd(String deviceID, String currentSpd) {
		return DeviceBedBound.setDeviceCurrentSpd(deviceID, currentSpd);
	}

	// * 修改终端号为deviceID的终端所绑定的病床的当前基准
	public static boolean setDeviceCurrentBase(String deviceID, String currentBase) {
		return DeviceBedBound.setDeviceCurrentBase(deviceID, currentBase);
	}

	// *修改终端号为deviceID的终端所绑定的病床的当前容量
	public static boolean setDeviceCurrentVol(String deviceID, String currentVol) {
		return DeviceBedBound.setDeviceCurrentVol(deviceID, currentVol);
	}

	// * 修改终端号为deviceID的终端所绑定的病床的工作状态
	public static boolean setDeviceStatus(String deviceID, String workStatus) {
		return DeviceBedBound.setDeviceStatus(deviceID, workStatus);
	}

	// *修改终端IP
	public static boolean setDeviceIp(String deviceID, String ip) {
		return DeviceBedBound.setDeviceIP(deviceID, ip);
	}

	// *修改剩余时间
	public static boolean setDeviceRemainTime(String deviceID, String remainTime) {
		return DeviceBedBound.setDeviceRemainTime(deviceID, remainTime);
	}

	// *注册
	public static boolean registerDevice(String deviceID) {
		return DeviceBedBound.registerDevice(deviceID);
	}

	// * 临时解除注册
	public static void unRegisterDevice(String deviceID) {
		DeviceBedBound.unRegisterDevice(deviceID);
	}

	// *判断是否已注册
	public static boolean isDeviceRegi(String deviceID) {
		return DeviceBedBound.isDeviceregi(deviceID);
	}

	public static boolean removeDeviceInfo(String deviceID) {
		return DeviceBedBound.RemoveDeviceInfo(deviceID);
	}

	// *清空病床上设备信息，不删除绑定
	public static void removeDevInfoDisplay(String device) {
		DeviceBedBound.removedDeviceBedDisplay(device);
	}

	// *判断床位现在的颜色状态
	public static boolean isWarning(String bedNo) {
		if (!DeviceBedBound.containsBed(bedNo))
			return false;
		return DevicetoBed.getDeviceStatusColorbyBednum(bedNo).equals(EDeviceStatusColorEnum.WARNING);
	}
}
