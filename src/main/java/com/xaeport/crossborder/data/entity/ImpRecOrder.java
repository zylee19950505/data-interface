package com.xaeport.crossborder.data.entity;

import java.util.Date;

/*
* 对应接收订单回执表
* */
public class ImpRecOrder {
	private String id;//订单回执的id
	private String guid;//系统生成36 位唯一序号（英文字母大写）
	private String ebpCode;//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
	private String ebcCode;//电商企业的海关注册登记编号。
	private String orderNo;//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
	private String returnStatus;//操作结果（2电子口岸申报中/3发送海关成功/4发送海关失败/100海关退单/120海关入库）,若小于0 数字表示处理异常回执
	private String returnTime;//操作时间(格式：yyyyMMddHHmmssfff)
	private String returnInfo;//备注（如:退单原因）
	private Date crtTm;//创建时间
	private Date updTm;//更新时间

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

	public String getEbpCode() {
		return ebpCode;
	}

	public void setEbpCode(String ebpCode) {
		this.ebpCode = ebpCode;
	}

	public String getEbcCode() {
		return ebcCode;
	}

	public void setEbcCode(String ebcCode) {
		this.ebcCode = ebcCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public Date getCrtTm() {
		return crtTm;
	}

	public Date getUpdTm() {
		return updTm;
	}

	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}

	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}



}
