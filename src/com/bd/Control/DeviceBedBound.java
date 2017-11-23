/**
 * 终端和病床绑定表
 */
package com.bd.Control;

import java.util.HashSet;

import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;
import com.bd.Control.InterfaceAndEnum.IDeviceBedBoundListener;
import com.bd.Control.Util.ArrayMap;
import com.bd.Control.Util.StringUtil;
import com.bd.objects.DeviceInfo;

public class DeviceBedBound {

	private static ArrayMap<DeviceInfo, String> bedDeviceMap = new ArrayMap<DeviceInfo, String>();

	private static HashSet<IDeviceBedBoundListener> dbListeners = new HashSet<IDeviceBedBoundListener>();

	public static void addDeviceBedBoundListener(IDeviceBedBoundListener idb) {
		dbListeners.add(idb);
	}

	public static EDeviceStatusColorEnum getEdeviceStatusColorEnum(String device) {
		return bedDeviceMap.getKey(new DeviceInfo(device)).getStatusColor();
	}

	// *判断Map中有没有此绑定
	public static boolean containsBed(String bedNum) {
		return bedDeviceMap.containsValue(bedNum);
	}

	// * 增加床位和病人信息的绑定
	public static boolean addDeviceBedBound(String bedNo, String deviceID) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			if (bedDeviceMap.get(di) != null) {
				return false;
			} else {
				bedDeviceMap.put(di, bedNo);
				notifyUpdate(bedNo);
				return true;
			}
		} else {
			bedDeviceMap.put(new DeviceInfo(deviceID), bedNo);
			notifyUpdate(bedNo);
			return true;
		}
	}

	// *设置终端工作状态
	public static boolean setDeviceStatus(String deviceID, String workStatus) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null && (di.getWorkStatus() == null || !di.getWorkStatus().equals(workStatus))) {
			di.setWorkStatus(workStatus);
			notifyUpdate(bedDeviceMap.get(deviceID));
			return true;
		}
		return false;
	}

	// *设置容量
	public static boolean setDeviceCurrentVol(String deviceID, String vol) {

		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			di.setCurrentVlm(vol);
			return true;
		}
		return false;
	}

	// *设置当前滴速
	public static boolean setDeviceCurrentSpd(String deviceID, String currentSpd) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null && (di.getCurrentSpd() == null || !di.getCurrentSpd().equals(currentSpd))) {
			di.setCurrentSpd(currentSpd);
			notifyUpdate(bedDeviceMap.get(deviceID));
			return true;
		}
		return false;
	}

	// *设置剩余时间
	public static boolean setDeviceRemainTime(String deviceID, String remainTime) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			di.setRemainTime(remainTime);
			return true;
		}
		return false;
	}

	// *设置当前基准
	public static boolean setDeviceCurrentBase(String deviceID, String currentBase) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			di.setBaseSpd(currentBase);
			return true;
		}
		return false;
	}

	// *设置IP地址
	public static boolean setDeviceIP(String deviceID, String ip) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			di.setIP(ip);
			return true;
		}
		return false;
	}

	// *注册
	public static boolean registerDevice(String deviceID) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di == null) {
			di = new DeviceInfo(deviceID);
			di.setRegiFlag(true);
			bedDeviceMap.put(di, null);
			return true;
		} else if (!di.isRegiFlag()) {
			di.setRegiFlag(true);
			return true;
		} else {
			return false;
		}
	}

	public static void unRegisterDevice(String deviceID) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null)
			di.setRegiFlag(false);

	}

	// *判断终端注册状态
	public static boolean isDeviceregi(String deviceID) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			return di.isRegiFlag();
		}
		return false;
	}

	// *设置当前颜色状态
	public static boolean setDeviceStatusColor(String deviceID, EDeviceStatusColorEnum statusColor) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null && (di.getStatusColor() == null || !di.getStatusColor().equals(statusColor))) {
			di.setStatusColor(statusColor);
			notifyUpdate(bedDeviceMap.get(deviceID));
			return true;
		}
		return false;
	}

	// *通过设备号清除绑定病床和终端绑定
	public static boolean RemoveDeviceInfo(String deviceID) {
		if (bedDeviceMap.containsKey(deviceID)) {
			String bedNo = bedDeviceMap.get(deviceID);
			if (bedNo != null && bedDeviceMap.remove(deviceID) != null) {
				notifyRemove(bedNo);
				return true;
			}
		}
		return false;
	}

	// *移除病床上终端信息的显示，不删除绑定
	public static void removedDeviceBedDisplay(String deviceID) {
		if (bedDeviceMap.containsKey(deviceID)) {
			notifyRemove(bedDeviceMap.get(deviceID));
		}
	}

	// *通过病床号获取终端信息
	public static DeviceInfo getDeviceInfoByBedNo(String bedNo) {
		return bedDeviceMap.getKeyForValue(bedNo);
	}

	private static void notifyUpdate(String bedNo) {
		for (IDeviceBedBoundListener bound : dbListeners) {
			bound.update(bedNo);
		}
	}

	private static void notifyRemove(String bedNo) {
		for (IDeviceBedBoundListener bound : dbListeners) {
			bound.remove(bedNo);
		}
	}

	// *通过终端号查询所绑定的床位号
	public static String getBedNum(String deviceID) {
		return bedDeviceMap.get(deviceID);
	}

	// * 验证终端未被添加
	public static boolean notContainBD(String deviceID) {
		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di == null) {
			return true;
		} else {
			return bedDeviceMap.get(di) == null;
		}
	}

	// *通过ID找到设备容量信息
	public static int getVol(String deviceID) {

		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			String currSpd = di.getCurrentSpd();
			String time = di.getRemainTime();
			if (currSpd != null && time != null) {
				int t = StringUtil.getNum(time);
				int c = StringUtil.getNum(currSpd);
				int base = StringUtil.getNum(DeviceBedBound.getBaseSpd(deviceID));
				if (c == 0) {
					return base * t;
				}
				return t * c;
			}
		}
		return -1;
	}

	// *通过ID找到设备基准滴速
	public static String getBaseSpd(String deviceID) {

		DeviceInfo di = bedDeviceMap.getKey(deviceID);
		if (di != null) {
			return di.getBaseSpd();
		}
		return null;
	}

}
