package com.xaeport.crossborder.data.entity;

import java.util.Date;

public class OrderSum {

	private String bill_no;//提运单号
	private Date appTime;//申报时间(取最大)
	private int totalCount;//提运单号下的订单总数
	private String data_status;//订单状态
	private int count;//订单状态数量
	private String no;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public Date getAppTime() {
		return appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getData_status() {
		return data_status;
	}

	public void setData_status(String data_status) {
		this.data_status = data_status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
