package com.example.schoolmangementsystem1.LeaveRequest;

public class LeaveReq {
	String date1 ;
	String date2;
	String Timespan;
	String ReqReason;
	String ReqRemarks;
	String ReqBy;
	String ReqStatus;
	String ReqApprovedBy;
	String ReqApproveTimeSpan;
	String ReqApprovalRemarks;
	String stdUid;



	public LeaveReq() {
	}

	public String getStdUid() {
		return stdUid;
	}

	public void setStdUid(String stdUid) {
		this.stdUid = stdUid;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getTimespan() {
		return Timespan;
	}

	public void setTimespan(String timespan) {
		Timespan = timespan;
	}

	public String getReqReason() {
		return ReqReason;
	}

	public void setReqReason(String reqReason) {
		ReqReason = reqReason;
	}

	public String getReqRemarks() {
		return ReqRemarks;
	}

	public void setReqRemarks(String reqRemarks) {
		ReqRemarks = reqRemarks;
	}

	public String getReqBy() {
		return ReqBy;
	}

	public void setReqBy(String reqBy) {
		ReqBy = reqBy;
	}

	public String getReqStatus() {
		return ReqStatus;
	}

	public void setReqStatus(String reqStatus) {
		ReqStatus = reqStatus;
	}

	public String getReqApprovedBy() {
		return ReqApprovedBy;
	}

	public void setReqApprovedBy(String reqApprovedBy) {
		ReqApprovedBy = reqApprovedBy;
	}

	public String getReqApproveTimeSpan() {
		return ReqApproveTimeSpan;
	}

	public void setReqApproveTimeSpan(String reqApproveTimeSpan) {
		ReqApproveTimeSpan = reqApproveTimeSpan;
	}

	public String getReqApprovalRemarks() {
		return ReqApprovalRemarks;
	}

	public void setReqApprovalRemarks(String reqApprovalRemarks) {
		ReqApprovalRemarks = reqApprovalRemarks;
	}
}
