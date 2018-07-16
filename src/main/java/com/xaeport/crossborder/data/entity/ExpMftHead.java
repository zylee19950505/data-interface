package com.xaeport.crossborder.data.entity;

/**
 * 订单回执头信息
 * Created by zwj on 2017/07/18.
 */
public class ExpMftHead {
    private String optype;// 操作类型 ADD
    private String bill_no;// 主单号
    private String voyage_no;// 航班号
    private String i_e_flag;// 进出口标志
    private String trafCn_Name;// 运输工具中文名
    private String trafEn_Name;// 运输工具英文名
    private String grossWt;// 毛重
    private String packNo;// 件数
    private String billNum;// 分运单总数
    private String trafMode;// 运输方式代码
    private String i_e_data;// 进出口日期(YYYYMMDD)
    private String destinationPort;// 指运港(抵运港)
    private String i_e_port;// 进出口岸代码
    private String trade_co;// 收发货人编号
    private String tradeName;// 收发货人名称
    private String inputNo;// 录入人卡号
    private String inputOpName;// 录入人姓名
    private String inputCompanyCode;// 录入单位代码
    private String getInputCompanyName;// 录入单位名称

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getVoyage_no() {
        return voyage_no;
    }

    public void setVoyage_no(String voyage_no) {
        this.voyage_no = voyage_no;
    }

    public String getI_e_flag() {
        return i_e_flag;
    }

    public void setI_e_flag(String i_e_flag) {
        this.i_e_flag = i_e_flag;
    }

    public String getTrafCn_Name() {
        return trafCn_Name;
    }

    public void setTrafCn_Name(String trafCn_Name) {
        this.trafCn_Name = trafCn_Name;
    }

    public String getTrafEn_Name() {
        return trafEn_Name;
    }

    public void setTrafEn_Name(String trafEn_Name) {
        this.trafEn_Name = trafEn_Name;
    }

    public String getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(String grossWt) {
        this.grossWt = grossWt;
    }

    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getTrafMode() {
        return trafMode;
    }

    public void setTrafMode(String trafMode) {
        this.trafMode = trafMode;
    }

    public String getI_e_data() {
        return i_e_data;
    }

    public void setI_e_data(String i_e_data) {
        this.i_e_data = i_e_data;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getI_e_port() {
        return i_e_port;
    }

    public void setI_e_port(String i_e_port) {
        this.i_e_port = i_e_port;
    }

    public String getTrade_co() {
        return trade_co;
    }

    public void setTrade_co(String trade_co) {
        this.trade_co = trade_co;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getInputNo() {
        return inputNo;
    }

    public void setInputNo(String inputNo) {
        this.inputNo = inputNo;
    }

    public String getInputOpName() {
        return inputOpName;
    }

    public void setInputOpName(String inputOpName) {
        this.inputOpName = inputOpName;
    }

    public String getInputCompanyCode() {
        return inputCompanyCode;
    }

    public void setInputCompanyCode(String inputCompanyCode) {
        this.inputCompanyCode = inputCompanyCode;
    }

    public String getGetInputCompanyName() {
        return getInputCompanyName;
    }

    public void setGetInputCompanyName(String getInputCompanyName) {
        this.getInputCompanyName = getInputCompanyName;
    }

    @Override
    public String toString() {
        return "ExpMftHead{" +
                "optype='" + optype + '\'' +
                ", bill_no='" + bill_no + '\'' +
                ", voyage_no='" + voyage_no + '\'' +
                ", i_e_flag='" + i_e_flag + '\'' +
                ", trafCn_Name='" + trafCn_Name + '\'' +
                ", trafEn_Name='" + trafEn_Name + '\'' +
                ", grossWt='" + grossWt + '\'' +
                ", packNo='" + packNo + '\'' +
                ", billNum='" + billNum + '\'' +
                ", trafMode='" + trafMode + '\'' +
                ", i_e_data='" + i_e_data + '\'' +
                ", destinationPort='" + destinationPort + '\'' +
                ", i_e_port='" + i_e_port + '\'' +
                ", trade_co='" + trade_co + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", inputNo='" + inputNo + '\'' +
                ", inputOpName='" + inputOpName + '\'' +
                ", inputCompanyCode='" + inputCompanyCode + '\'' +
                ", getInputCompanyName='" + getInputCompanyName + '\'' +
                '}';
    }
}
