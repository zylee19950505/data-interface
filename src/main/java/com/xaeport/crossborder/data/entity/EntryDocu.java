package com.xaeport.crossborder.data.entity;

/**
 * entrydocu   分单（随附单据）
 * Created by zwj on 2017/07/18.
 */
public class EntryDocu {
    private String entrydocu_id;
    private String entrylist_id;
    private String ass_bill_no;//分单号
    private String optype;//操作类型
    private String order_no;//序号
    private String docu_code;//随附单证类型
    private String cert_code;//随附单证号码


    public String getEntrydocu_id() {
        return entrydocu_id;
    }

    public void setEntrydocu_id(String entrydocu_id) {
        this.entrydocu_id = entrydocu_id;
    }

    public String getEntrylist_id() {
        return entrylist_id;
    }

    public void setEntrylist_id(String entrylist_id) {
        this.entrylist_id = entrylist_id;
    }

    public   String getAss_bill_no() {
        return ass_bill_no;
    }

    public void setAss_bill_no(String ass_bill_no) {
        this.ass_bill_no = ass_bill_no;
    }

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDocu_code() {
        return docu_code;
    }

    public void setDocu_code(String docu_code) {
        this.docu_code = docu_code;
    }

    public String getCert_code() {
        return cert_code;
    }

    public void setCert_code(String cert_code) {
        this.cert_code = cert_code;
    }

    @Override
    public String toString() {
        return "EntryDocu{" +
                "entrydocu_id='" + entrydocu_id + '\'' +
                ", entrylist_id='" + entrylist_id + '\'' +
                ", ass_bill_no='" + ass_bill_no + '\'' +
                ", optype='" + optype + '\'' +
                ", order_no='" + order_no + '\'' +
                ", docu_code='" + docu_code + '\'' +
                ", cert_code='" + cert_code + '\'' +
                '}';
    }
}
