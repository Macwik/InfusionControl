package com.bd.objects;

public class InfusionEvent {

	private String patientID;
	private String date;
	private String time;
	private String event;

	public InfusionEvent(String patientID) {
		this.setPatientID(patientID);
		setDate(null);
		setTime(null);
		setEvent(null);
	}

	public InfusionEvent(String patientID, String date, String time, String event) {
		this.setPatientID(patientID);
		setDate(date);
		setTime(time);
		setEvent(event);
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
}
