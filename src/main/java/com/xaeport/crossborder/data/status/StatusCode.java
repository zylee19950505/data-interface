package com.xaeport.crossborder.data.status;

/*
* 数据状态表
* create by mamba on 2018/7/12
* */
public class StatusCode {

	//通用数据状态（涉及导入及校验）
	public final static String EXPORT = "CBDS1";//已导入
	public final static String VALIDATE_NO_PASS = "CBDS11";// 数据校验未通过
	public final static String verifyState = "CBDS12";//身份校验已提交校验（身份证校验中）
	public final static String verifyIng = "CBDS13";//身份校验中

	//订单数据状态
	public final static String DDDSB = "CBDS2";//订单待申报,导入当做待申报
	public final static String DDSBZ = "CBDS20";//订单申报中
	public final static String DDYSB = "CBDS21";//订单已申报
	public final static String DDCB = "CBDS22";//订单重报

	//支付单数据状态
	public final static String ZFDDSB = "CBDS3";//支付单待申报
	public final static String ZFDSBZ = "CBDS30";//支付单申报中
	public final static String ZFDYSB = "CBDS31";//支付单已申报
	public final static String ZFDSBCG = "CBDS32";//支付单申报成功
	public final static String ZFDCB = "CBDS33";//支付单重报
	public final static String ZFDSBSB = "CBDS34";//支付单申报失败

	//运单数据状态
	public final static String YDDSB = "CBDS4";//运单待申报
	public final static String YDSBZ = "CBDS40";//运单申报中
	public final static String YDYSB = "CBDS41";//运单已申报
	public final static String YDSBCG = "CBDS42";//运单申报成功
	public final static String YDCB = "CBDS43";//运单重报
	public final static String YDSBSB = "CBDS44";//运单申报失败

	//运单状态数据状态
	public final static String YDZTDSB = "CBDS5";//运单状态待申报
	public final static String YDZTSBZ = "CBDS50";//运单状态申报中
	public final static String YDZTYSB = "CBDS51";//运单状态已申报
	public final static String YDZTSBCG = "CBDS52";//运单状态申报成功
	public final static String YDZTCB = "CBDS53";//运单状态重报
	public final static String YDZTSBSB = "CBDS54";//运单状态申报失败

	//清单数据状态
	public final static String QDDSB = "CBDS6";//清单待申报
	public final static String QDSBZ = "CBDS60";//清单申报中
	public final static String QDYSB = "CBDS61";//清单已申报
	public final static String QDSBCG = "CBDS62";//清单申报成功
	public final static String QDCB = "CBDS63";//清单重报
	public final static String QDSBSB = "CBDS64";//清单申报失败

	public final static String DZKAYZC = "1";//电子口岸已暂存
	public final static String DZKASBZ = "2";//电子口岸申报中
	public final static String FWHGCG = "3";//发送海关成功
	public final static String FWHGSB = "4";//发送海关失败
	public final static String HGTD = "100";//海关退单
	public final static String HGRK = "120";//海关入库
	public final static String RGSH = "300";//人工审核
	public final static String HGSJ = "399";//海关审结
	public final static String FX = "800";//放行
	public final static String JG = "899";//结关
	public final static String CY = "500";//查验
	public final static String KLYSTG = "501";//扣留移送通关
	public final static String KLYSJS = "502";//扣留移送缉私
	public final static String KLYSFG = "503";//扣留移送法规
	public final static String QTKL = "599";//其他扣留
	public final static String TY = "700";//退运


}
