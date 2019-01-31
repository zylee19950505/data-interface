package com.xaeport.crossborder.data.status;

//跨境，保税回执报文类型表
public class RecriptType {
    //跨境订单回执
    public final static String KJDD = "CEB312";
    //跨境支付单回执
    public final static String KJZFD = "CEB412";
    //跨境运单回执
    public final static String KJYD = "CEB512";
    //跨境运单状态回执
    public final static String KJYDZT = "CEB514";
    //跨境清单回执
    public final static String KJQD = "CEB622";
    //跨境入库明细单回执
    public final static String KJRKMXD = "CEB712";
    //跨境预定数据回执
    public final static String KJYDSJ = "CheckGoodsInfo";
    //跨境电子税单回执
    public final static String KJSD = "TAX";

    //保税数据中心通用回执
    public final static String BSSJZX = "COMMON";
    //保税核注清单审核回执
    public final static String BSHZQDSH = "INV201";
    //保税核注清单报关单回执
    public final static String BSHZQDBGD = "INV202";
    //保税核放单审核回执
    public final static String BSHFDSH = "SAS221";
    //保税核放单过卡回执
    public final static String BSHFDGK = "SAS223";
}
