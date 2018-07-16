package com.xaeport.crossborder.data.entity;

/**
 * 订单回执表体信息
 * Created by zwj on 2017/07/18.
 */
public class ExpMftList {
    private String bill_no;// 主单号
    private String ass_bill_no;// 分单号
    private String voyageno;// 航班号
    private String main_gn_name;// 主要商品名称
    private String pack_no;// 件数
    private String gross_wt;// 毛重
    private String trade_total;// 贸易额
    private String trade_curr;// 贸易币值

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getAss_bill_no() {
        return ass_bill_no;
    }

    public void setAss_bill_no(String ass_bill_no) {
        this.ass_bill_no = ass_bill_no;
    }

    public String getVoyageno() {
        return voyageno;
    }

    public void setVoyageno(String voyageno) {
        this.voyageno = voyageno;
    }

    public String getMain_gn_name() {
        return main_gn_name;
    }

    public void setMain_gn_name(String main_gn_name) {
        this.main_gn_name = main_gn_name;
    }

    public String getPack_no() {
        return pack_no;
    }

    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }

    public String getGross_wt() {
        return gross_wt;
    }

    public void setGross_wt(String gross_wt) {
        this.gross_wt = gross_wt;
    }

    public String getTrade_total() {
        return trade_total;
    }

    public void setTrade_total(String trade_total) {
        this.trade_total = trade_total;
    }

    public String getTrade_curr() {
        return trade_curr;
    }

    public void setTrade_curr(String trade_curr) {
        this.trade_curr = trade_curr;
    }

    @Override
    public String toString() {
        return "ExpMftList{" +
                "bill_no='" + bill_no + '\'' +
                ", ass_bill_no='" + ass_bill_no + '\'' +
                ", voyageno='" + voyageno + '\'' +
                ", main_gn_name='" + main_gn_name + '\'' +
                ", pack_no='" + pack_no + '\'' +
                ", gross_wt='" + gross_wt + '\'' +
                ", trade_total='" + trade_total + '\'' +
                ", trade_curr='" + trade_curr + '\'' +
                '}';
    }
}
