package com.xaeport.crossborder.data.entity;


import java.util.Date;

/*
* create by yth 2018-8-9
*
* 运单申报查询统计实体类
* */
public class LogisticsSum {

	private String bill_no;//提运单号
	private Date appTime;//申报时间(取最大)
	private int totalCount;//提运单号下的运单总数
	private String data_status;//运单状态
	private int count1;//运单状态数量
	private String sta_data_status;//运单状态的状态
	private int count2;//运单状态的状态的数量

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

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public String getSta_data_status() {
		return sta_data_status;
	}

	public void setSta_data_status(String sta_data_status) {
		this.sta_data_status = sta_data_status;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}
}
