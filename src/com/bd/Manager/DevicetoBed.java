package com.bd.Manager;

import com.bd.Control.BedPatientBound;
import com.bd.Control.DeviceBedBound;
import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;
import com.bd.objects.PatientInfo;

public class DevicetoBed {
	// *通过终端号查询病人信息,为绑定返回null
	public static PatientInfo getPatientFromDevice(String deviceID) {
		String bedNum = DeviceBedBound.getBedNum(deviceID);
		if (bedNum != null) {
			if (BedPatientBound.contain(bedNum))
				return BedPatientBound.getPatientByBedNo(bedNum);
		}
		return null;
	}

	// *通过终端号查询病人信息,为绑定返回null
	public static String getPatientIDFromDevice(String deviceID) {
		String bedNum = DeviceBedBound.getBedNum(deviceID);
		if (bedNum != null) {
			if (BedPatientBound.contain(bedNum))
				return BedPatientBound.getPatientByBedNo(bedNum).getPatientId();
		}
		return null;
	}

	// *通过床位号获得设备号
	public static EDeviceStatusColorEnum getDeviceStatusColorbyBednum(String bednum) {
		return DeviceBedBound.getDeviceInfoByBedNo(bednum).getStatusColor();
	}

}
