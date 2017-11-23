/**
 * 封装病人信息
 */
package com.bd.objects;

public class PatientInfo {
	private String patientId;// 住院号
	private String patientName; // 病人姓名
	private String patientGender; // 病人性别
	private String patientAge; // 病人年龄
	private String patientSymptom; // 疾病名称

	public PatientInfo(String patientId, String PatientName, String patientGender, String patientAge,
			String patientSymptom) {

		this.setPatientId(patientId);
		this.setPatientName(PatientName);
		this.setPatientGender(patientGender);
		this.setPatientAge(patientAge);
		this.setPatientSymptom(patientSymptom);
	}

	public PatientInfo(String patientID) {
		patientId = patientID;// 住院号
		patientName = null; // 病人姓名
		patientGender = null; // 病人性别
		patientAge = null; // 病人年龄
		patientSymptom = null; // 疾病名称
	}

	@Override
	public String toString() {
		return patientName.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PatientInfo) {
			return this.patientId.equals(((PatientInfo) obj).getPatientId());
		} else if (obj instanceof String) {
			return this.patientId.equals(obj);
		}
		return false;
		// return this.patientId.equals(((PatientInfo) obj).patientId);
	}

	@Override
	public int hashCode() {
		return patientId.hashCode();
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientSymptom() {
		return patientSymptom;
	}

	public void setPatientSymptom(String patientSymptom) {
		this.patientSymptom = patientSymptom;
	}
}
