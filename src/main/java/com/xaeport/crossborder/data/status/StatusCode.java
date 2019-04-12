package com.xaeport.crossborder.data.status;

/*
* 数据状态表
* create by mamba on 2018/7/12
* */
public class StatusCode {

	//解析入库报文标记
	public final static String RKBW = "STOCK";//入库报文

	//通用数据状态（涉及导入及校验）
	public final static String EXPORT = "CBDS1";//已导入
	public final static String VALIDATE_NO_PASS = "CBDS11";// 数据校验未通过
	public final static String verifyState = "CBDS12";//身份校验已提交校验（身份证校验中）
	public final static String verifyIng = "CBDS13";//身份校验中

	//订单数据状态
	public final static String DDDSB = "CBDS2";//订单待申报,导入当做待申报
	public final static String DDSBZ = "CBDS20";//订单申报中
	public final static String DDYSB = "CBDS21";//订单已申报
	public final static String DDSBCG = "CBDS22";//订单申报成功
	public final static String DDCB = "CBDS23";//订单重报
	public final static String DDSBSB = "CBDS24";//订单申报失败

	public final static String DDBWSCZ = "OrderDoing";//订单报文生成中
	public final static String DDBWXZWC = "OrderOver";//订单报文下载完成

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
	public final static String QDZC = "CBDS65";
	public final static String QDDSB = "CBDS6";//清单待申报
	public final static String QDSBZ = "CBDS60";//清单申报中
	public final static String QDYSB = "CBDS61";//清单已申报
	public final static String QDSBCG = "CBDS62";//清单申报成功
	public final static String QDCB = "CBDS63";//清单重报
	public final static String QDSBSB = "CBDS64";//清单申报失败

	public final static String QDBWSCZ = "InvenDoing";//清单报文生成中
	public final static String QDBWXZWC = "InvenOver";//清单报文下载完成

	//入库明细单数据状态
	public final static String RKMXDDSB = "CBDS7";//入库明细单待申报
	public final static String RKMXDSBZ = "CBDS70";//入库明细单申报中
	public final static String RKMXDYSB = "CBDS71";//入库明细单已申报
	public final static String RKMXDSBCG = "CBDS72";//入库明细单申报成功
	public final static String RKMXDCB = "CBDS73";//入库明细单重报
	public final static String RKMXDSBSB = "CBDS74";//入库明细单申报失败

	//核放单数据状态
	public final static String HFDDSB = "CBDS8";//核放单待申报
	public final static String HFDSBZ = "CBDS80";//核放单申报中
	public final static String HFDYSB = "CBDS81";//核放单已申报
	public final static String HFDSBCG = "CBDS82";//核放单申报成功
	public final static String HFDCB = "CBDS83";//核放单重报
	public final static String HFDSBSB = "CBDS84";//核放单申报失败

	public final static String DZKAYZC = "1";//电子口岸已暂存
	public final static String DZKASBZ = "2";//电子口岸申报中
	public final static String FWHGCG = "3";//发送海关成功
	public final static String FWHGSB = "4";//发送海关失败
	public final static String HGTD = "100";//海关退单
	public final static String HGRK = "120";//海关入库
	public final static String RGSH = "300";//人工审核
	public final static String HGSJ = "399";//海关审结（审核通过）
	public final static String DYD = "400";//待运抵
	public final static String CY = "500";//查验
	public final static String KLYSTG = "501";//扣留移送通关
	public final static String KLYSJS = "502";//扣留移送缉私
	public final static String KLYSFG = "503";//扣留移送法规
	public final static String QTKL = "599";//其他扣留
	public final static String GQ = "600";//挂起
	public final static String TY = "700";//退运
	public final static String FX = "800";//放行
	public final static String JG = "899";//结关

	public final static String RQHZQDDSB = "BDDS1";//入区核注清单待申报
	public final static String RQHZQDSBZ = "BDDS10";//入区核注清单申报中
	public final static String RQHZQDYSB = "BDDS11";//入区核注清单已申报
	public final static String RQHZQDSBCG = "BDDS12";//入区核注清单申报成功

	public final static String RQHFDDSB = "BDDS3";//入区核放单待申报
	public final static String RQHFDSBZ = "BDDS30";//入区核放单申报中
	public final static String RQHFDYSB = "BDDS31";//入区核放单已申报
	public final static String RQHFDSBCG = "BDDS32";//入区核放单申报成功
	public final static String RQHFDDZC = "BDDS33";//入区核放单暂存

	public final static String CQHZQDDSB = "BDDS2";//出区核注清单待申报
	public final static String CQHZQDSBZ = "BDDS20";//出区核注清单申报中
	public final static String CQHZQDYSB = "BDDS21";//出区核注清单已申报
	public final static String CQHZQDSBCG = "BDDS22";//出区核注清单申报成功

	public final static String CQHFDDSB = "BDDS4";//出区核放单待申报
	public final static String CQHFDSBZ = "BDDS40";//出区核放单申报中
	public final static String CQHFDYSB = "BDDS41";//出区核放单已申报
	public final static String CQHFDSBCG = "BDDS42";//出区核放单申报成功

	public final static String BSQDDSB = "BDDS5";//保税清单待申报
	public final static String BSQDSBZ = "BDDS50";//保税清单申报中
	public final static String BSQDYSB = "BDDS51";//保税清单已申报
	public final static String BSQDSBCG = "BDDS52";//保税清单申报成功
	public final static String BSQDCB = "BDDS53";//保税清单重报
	public final static String BSQDSBSB = "BDDS54";//保税清单申报失败

	public final static String BSDDDSB = "BDDS6";//保税订单待申报
	public final static String BSDDSBZ = "BDDS60";//保税订单申报中
	public final static String BSDDYSB = "BDDS61";//保税订单已申报
	public final static String BSDDSBCG = "BDDS62";//保税订单申报成功

	public final static String CQKCHFDDSB = "BDDS8";//出区空车核放单待申报
	public final static String CQKCHFDSBZ = "BDDS80";//出区空车核放单申报中
	public final static String CQKCHFDYSB = "BDDS81";//出区空车核放单已申报
	public final static String CQKCHFDSBCG = "BDDS82";//出区空车核放单申报成功

}
