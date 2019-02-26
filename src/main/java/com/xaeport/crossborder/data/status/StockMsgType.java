package com.xaeport.crossborder.data.status;

//跨境，保税入库报文解析类型
public class StockMsgType {

    //跨境订单报文
    public final static String CB_DD = "CEB311";
    //跨境支付单报文
    public final static String CB_ZFD = "CEB411";
    //跨境运单报文
    public final static String CB_YD = "CEB511";
    //跨境运单状态报文
    public final static String CB_YDZT = "CEB513";
    //跨境清单报文
    public final static String CB_QD = "CEB621";
    //跨境入库明细单报文
    public final static String CB_RKMXD = "CEB711";
    //跨境核放单报文
    public final static String CB_MF = "CEBMANIFEST";

    //保税核注清单报文
    public final static String BD_HZQD = "INV101";
    //保税核放单报文
    public final static String BD_HFD = "SAS121";

}
