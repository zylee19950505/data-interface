package com.xaeport.crossborder.data.entity;


import java.util.Date;

/*
* 对应接收运单回执表
* */
public class ImpRecLogistics {
	private String id;
	private String guid;
	private String logistics_Code;
	private String logistics_No;
	private String return_Status;
	private String return_Info;
	private String return_Time;
	private Date crtTm;
	private Date updTm;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getLogistics_Code() {
		return logistics_Code;
	}

	public void setLogistics_Code(String logistics_Code) {
		this.logistics_Code = logistics_Code;
	}

	public String getLogistics_No() {
		return logistics_No;
	}

	public void setLogistics_No(String logistics_No) {
		this.logistics_No = logistics_No;
	}

	public String getReturn_Status() {
		return return_Status;
	}

	public void setReturn_Status(String return_Status) {
		this.return_Status = return_Status;
	}

	public String getReturn_Info() {
		return return_Info;
	}

	public void setReturn_Info(String return_Info) {
		this.return_Info = return_Info;
	}

	public String getReturn_Time() {
		return return_Time;
	}

	public void setReturn_Time(String return_Time) {
		this.return_Time = return_Time;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
}
