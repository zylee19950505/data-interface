package com.xaeport.crossborder.data.entity;

/*
* ceb311 里的节点
* */
public class BaseTransfer {
	private String copCode;
	private String copName;
	private String dxpMode;
	private String dxpId;
	private String note;

	public String getCopCode() {
		return copCode;
	}

	public void setCopCode(String copCode) {
		this.copCode = copCode;
	}

	public String getCopName() {
		return copName;
	}

	public void setCopName(String copName) {
		this.copName = copName;
	}

	public String getDxpMode() {
		return dxpMode;
	}

	public void setDxpMode(String dxpMode) {
		this.dxpMode = dxpMode;
	}

	public String getDxpId() {
		return dxpId;
	}

	public void setDxpId(String dxpId) {
		this.dxpId = dxpId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
