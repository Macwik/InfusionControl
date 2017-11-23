/**
 * 封装手机信息
 */
package com.bd.objects;

public class PhoneInfo {
	private String phoneId; // 手机编号
	private boolean regiFlag;// 是否在线false为离线，true为在线
	private String workStatus;// 当前状态
	private String alarmType;// 报警类型
	private String ip; // 手机IP

	public PhoneInfo(String phoneId) {
		this.setPhoneId(phoneId);
		this.setRegiFlag(false);
		this.setWorkStatus(null);
		this.setAlarmType(null);
		this.setIp(null);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhoneInfo) {
			return this.phoneId.equals(((PhoneInfo) obj).getPhoneId());
		}
		// else if (obj instanceof String) {
		// return this.phoneId.equals(obj);
		// }
		return false;
	}

	@Override
	public int hashCode() {
		return this.phoneId.hashCode();
	}

	@Override
	public String toString() {
		return this.phoneId.toString();
	}

	// * phoneId的get、set
	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	// * regiFlag的get、set
	public boolean isRegiFlag() {
		return regiFlag;
	}

	public void setRegiFlag(boolean regiFlag) {
		this.regiFlag = regiFlag;
	}

	// * workStatus的get、set
	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	// * alarmType的get、set
	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	// * Ip的get、set
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
