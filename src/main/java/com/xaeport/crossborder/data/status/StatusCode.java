package com.xaeport.crossborder.data.status;

/*
* 数据状态表
* create by mamba on 2018/7/12
* */
public class StatusCode {
	public final static String EXPORT = "CBDS1";//已导入
	public final static String VALIDATE_NO_PASS = "CBDS11";// 数据校验未通过
	public final static String verifyState = "CBDS12";//身份校验已提交校验
	public final static String verifyIng = "CBDS13";//身份校验中

	public final static String DDDSB = "CBDS2";//订单待申报,导入当做待申报
	public final static String DDSBZ = "CBDS20";//订单申报中
	public final static String DDYSB = "CBDS21";//订单已申报
	public final static String DDCB = "CBDS22";//订单重报

	//运单或者支付单,用的时候需要再改数据
	public final static String BDSB = "CBDS3";//订单待申报
	public final static String BDSBZ = "CBDS30";//订单申报中
	public final static String BDYSB = "CBDS31";//订单已申报
	public final static String BDSBCG = "CBDS32";//订单申报成功
	public final static String BDSBSB = "CBDS33";//订单申报失败
	public final static String BDYTJSB="CBDS34";//订单已提交申报

	public final static String SJZXRKCG = "DY";//数据中心入库成功(改操作状态CBDS3)
	public final static String SJZXRKSB = "DN";//数据中心入库失败(改操作状态CBDS22)
	public final static String HGRKCG = "NY";//海关入库成功
	public final static String HGRKSB = "NN";//海关入库失败

	public final static String ZXRKCG = "D0";//中心入库成功
	public final static String ZXRKSB = "D1";//中心入库失败
	public final static String FWHGCG = "D2";//发往海关成功
	public final static String FWHGSB = "D3";//发往海关失败
	public final static String XZXGZJCG = "Y";//新增、修改、追加成功(改操作状态CBDS32)
	public final static String XZXGZJSB = "N";//新增、修改、追加失败(改操作状态CBDS33)
	public final static String YXZJ = "A";//允许追加
	public final static String BYXZJ = "R";//不允许追加
	public final static String TD = "00";//退单
	public final static String ZPHBG = "02";//转普货报关
	public final static String ZRGSH = "03";//转人工审核
	public final static String TJZMDJ = "04";//提交纸面单据
	public final static String CY = "05";//查验
	public final static String KL = "06";//扣留
	public final static String MS = "07";//没收
	public final static String XD = "08";//改单
	public final static String SD = "D";//删单
	public final static String SHTG = "10";//审核通过
	public final static String FX = "11";//放行
	public final static String CYHBSFX = "12";//查验后补税放行
	public final static String ZYHBZFX = "13";//查验后补证放行
	public final static String ZYHBSBZFX = "14";//查验后补税补证放行
	public final static String ZYHCFHFX = "15";//查验后处罚后放
	public final static String ZYHGDFX = "16";//查验后改单放行
	public final static String ZYHDBFX = "17";//查验后担保放行
	public final static String ZYHZCHWFX = "18";//查验正常货物放行
	public final static String ZYHKL = "19";//查验后扣留
	public final static String ZYHTD = "20";//查验后退单
	public final static String ZYHZPHBG = "21";//查验后转普货报关
	public final static String CYHZBG = "22";//查验后转A、C类报关
	public final static String CYHZGRWPBG = "23";//查验后转个人物品报关
	public final static String CYHGD = "24";//查验后改单
}
