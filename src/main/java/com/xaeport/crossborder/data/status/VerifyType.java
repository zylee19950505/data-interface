package com.xaeport.crossborder.data.status;

public class VerifyType {

    public final static String IDCARD = "IDCARD";//身份证校验
    public final static String LOGIC = "LOGIC";//逻辑校验
    public final static String UCHECK="UCHECK";//未校验
    public final static String OTHER = "OTHER";//其它校验

    public final static String PASS = "Y";//校验通过
    public final static String NOTPASS = "N";//校验未通过
    public final static String VERIFYING = "ING";//校验中
    public final static String CONVERT="P";//转为未校验
    public final static String VERIFY="1002";//公安库中无此号码
    public final static String ISPASS = "";//待校验
    public final static String DIFFERENT="1001"; //身份证信息不一致

    public final static String REAL_NAME_CODE = "SW1100";// 实名制库中存在的 身份校验通过状态
    public final static String CONVERT_CODE = "UC1100";// 转化为未校验状态

}
