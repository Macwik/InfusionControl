/**
 * 封装终端信息
 */
package com.bd.objects;

import com.bd.Control.InterfaceAndEnum.EDeviceStatusColorEnum;

public class DeviceInfo {
	private String deviceID; // 终端ID
	private boolean regiFlag;// 是否已注册
	private String workStatus;// 工作状态
	private EDeviceStatusColorEnum statusColor;
	private String baseSpd;// 基准滴速
	private String currentSpd;// 当前滴速
	private String currentVlm;// 当前容量
	private String remainTime;

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	private String IP; // 设备IP

	public DeviceInfo(String deviceID) {
		this.deviceID = deviceID;
		this.regiFlag = false;
		this.workStatus = null;
		this.setStatusColor(EDeviceStatusColorEnum.WAIT);
		this.baseSpd = null;
		this.currentSpd = null;
		this.currentVlm = null;
		this.IP = null;
		this.remainTime = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DeviceInfo) {
			return this.deviceID.equals(((DeviceInfo) obj).getDeviceID());
		} else if (obj instanceof String) {
			return this.deviceID.equals(obj);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.deviceID.hashCode();
	}

	@Override
	public String toString() {
		return this.deviceID.toString();
	}

	public String getDeviceID() {
		return deviceID;
	}

	public boolean isRegiFlag() {
		return regiFlag;
	}

	public void setRegiFlag(boolean regiFlag) {
		this.regiFlag = regiFlag;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getBaseSpd() {
		return baseSpd;
	}

	public void setBaseSpd(String baseSpd) {
		this.baseSpd = baseSpd;
	}

	public String getCurrentSpd() {
		return currentSpd;
	}

	public void setCurrentSpd(String currentSpd) {
		this.currentSpd = currentSpd;
	}

	public String getCurrentVlm() {
		return currentVlm;
	}

	public void setCurrentVlm(String currentVlm) {
		this.currentVlm = currentVlm;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public EDeviceStatusColorEnum getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(EDeviceStatusColorEnum statusColor) {
		this.statusColor = statusColor;
	}

}
