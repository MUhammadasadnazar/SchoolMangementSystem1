package com.example.schoolmangementsystem1.meeting;

public class Meeting {

	public String meetingdate;
	public  String meetingTime ;
	public  String meetingAgenda;
	public  String meetingStatus;
	public  String meetingtimeStamp;
	public String meetingid;

	public Meeting() {
	}

	public String getMeetingid() {
		return meetingid;
	}

	public void setMeetingid(String meetingid) {
		this.meetingid = meetingid;
	}



	public String getMeetingdate() {
		return meetingdate;
	}

	public void setMeetingdate(String meetingdate) {
		this.meetingdate = meetingdate;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getMeetingAgenda() {
		return meetingAgenda;
	}

	public void setMeetingAgenda(String meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
	}

	public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getMeetingtimeStamp() {
		return meetingtimeStamp;
	}

	public void setMeetingtimeStamp(String meetingtimeStamp) {
		this.meetingtimeStamp = meetingtimeStamp;
	}
}
