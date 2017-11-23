/**
 * 封装病床信息
 */
package com.bd.objects;

public class BedInfo {
	private String BedNum;
	private PatientInfo patientInfo;
	private PhoneInfo[] phoneID;
	private DeviceInfo deviceInfo;

	public BedInfo(String BedNum, PatientInfo patientInfo, PhoneInfo[] phoneID, DeviceInfo deviceInfo) {
		this.BedNum = BedNum;
		this.patientInfo = patientInfo;
		this.phoneID = new PhoneInfo[10];
		this.deviceInfo = deviceInfo;
	}

	public String getBedNum() {
		return BedNum;
	}

	public void setBedNum(String bedNum) {
		BedNum = bedNum;
	}

	public PhoneInfo[] getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(PhoneInfo[] phoneID) {
		this.phoneID = phoneID;
	}

	public PatientInfo getPatientInfo() {
		return patientInfo;
	}

	public void setPatientInfo(PatientInfo patientInfo) {
		this.patientInfo = patientInfo;
	}

	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
