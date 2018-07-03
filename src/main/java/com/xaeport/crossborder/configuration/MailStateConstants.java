package com.xaeport.crossborder.configuration;

public interface MailStateConstants {

    public static String getMailState(String type) {
        String str;
        if (type.equals("G")) {
            str = new StringBuilder().append(MailStateConstants.MAIL_PROCESS_STATE_DUTIABLE_CUSTOMS_DECLARATION_WAIT).append(",")//"H">货物待报关
                    .append(MailStateConstants.MAIL_PROCESS_STATE_DUTIABLE_GOODS).toString();//"G">货物待报关
        }else {
            str = new StringBuilder().append(MailStateConstants.MAIL_PROCESS_STATE_WAIT_OUTDEPOT).append(",")//"Q">已运抵
                    .append(MailStateConstants.MAIL_PROCESS_STATE_DUTIABLE_RES).append(",")//"J">应税物品
                    .append(MailStateConstants.MAIL_PROCESS_STATE_NEGOTIATE).append(",")//"M">现场面洽
                    .append(MailStateConstants.MAIL_PROCESS_STATE_PERSONAL).append(",")//"T">待申报
                    .append(MailStateConstants.MAIL_PROCESS_STATE_TAXES_WAIT).append(",")//"K">待缴税
                    .append(MailStateConstants.MAIL_PROCESS_STATE_DUTIABLE_CUSTOMS_DECLARATION_WAIT).append(",")//"H">货物待报关
                    .append(MailStateConstants.MAIL_PROCESS_STATE_DUTIABLE_GOODS).append(",")//"G">货物待报关
                    .append(MailStateConstants.MAIL_PROCESS_STATE_TRANSFER_WAIT).append(",")//"F">移交
                    .append(MailStateConstants.MAIL_PROCESS_STATE_RETURNED).append(",")//"E">退运
                    .append(MailStateConstants.MAIL_PROCESS_STATE_RELEASE).toString();//"B">放行
        }
        return str;
    }

    /**
     * 未录入
     */
    int MAIL_STATE_NOT_ENTER = 0;

    /**
     * 已录入
     */
    int MAIL_STATE_HAS_ENTER = 1;

    /**
     * 重新采集
     */
    int ENTRY_STATE_REGET = 2;

    /**
     * 货物
     */
    int MAIL_TYPE_GOODS = 1;
    /**
     * 邮局代办品（物品）
     */
    int MAIL_TYPE_RES = 2;
    /**
     * 移交品
     */
    int MAIL_TYPE_DEVOLVE = 3;
    /**
     * 现场处置品
     */
    int MAIL_TYPE_SITE = 4;

    /**
     * 待录入
     */
    String MAIL_STATE_WAIT_ENTER = "A";

    /**
     * 已录入 V
     */
    String MAIL_STATE_WAIT_CHECK = "B";

    /**
     * 已入库
     */
    String MAIL_STATE_INDEPOT = "C";

    /**
     * 已备案
     */
    int MAIL_STATE_KEEPON = 3;

    /**
     * 已报关
     */
    String MAIL_STATE_ONCUSTOMS = "E";

    /**
     * 待处理
     */
    int MAIL_STATE_WAIT_PROCESS = 5;

    /**
     * 待出库
     */
    int MAIL_STATE_WAIT_OUTDEPOT = 6;

    /**
     * 已出库
     */
    String MAIL_STATE_OUTDEPOT = "H";

    /**
     * 已妥投
     */
    String MAIL_STATE_SENDPOST = "I";

    /**
     * 待处置
     */
    String MAIL_STATE_WAITDISPOSED = "F";

    /**
     * 已放弃
     */
    int MAIL_STATE_RENOUNCE = 9;

    /**
     * 已处置
     */
    String MAIL_STATE_HASDISPOSED = "L";


    /**
     * 已放弃
     */
    int MAIL_STATE_SENDBACK = 10;

    /**
     * 重新采集
     */
    String MAIL_STATE_AGAIN_COLLECTION = "X";

    /**
     * 已缴税
     */
    String MAIL_STATE_HASTAX = "M";

    /*** 支付方式 现金 0 ；微信 1 ***/
    /**
     * 现金支付方式
     */
    int PAYTYPE_MONEY = 0;
    /**
     * 微信支付方式
     */
    int PAYTYPE_WECHAT = 1;

    /*** 处理方式PROCESSRES ***/
    /**
     * 自用放行品 1
     */
    int PROCESSRES_SELFRELEASE = 1;
    /**
     * 现场征税品 2
     */
    int PROCESSRES_TAX = 2;
    /**
     * 移交品 3
     */
    int PROCESSRES_TRANSFER = 3;
    /**
     * 货物 4
     */
    int PROCESSRES_GOODS = 4;
    /**
     * 邮局代办 5
     */
    int PROCESSRES_POSTOFFICE = 5;
    /**
     * 放弃品 6
     */
    int PROCESSRES_GIVEUP = 6;
    /**
     * 退运品 7
     */
    int PROCESSRES_RETURNED = 7;


    /** 新梳理状态 */
    /**
     * 已录入
     */
    String MAIL_PROCESS_STATE_WAIT_ENTER = "A";
    /**
     * 放行
     */
    String MAIL_PROCESS_STATE_RELEASE = "B";
    /**
     * 货物
     */
    String MAIL_PROCESS_STATE_GOODS = "C";
    /**
     * 放弃
     */
    String MAIL_PROCESS_STATE_GIVE_UP = "D";
    /**
     * 退运
     */
    String MAIL_PROCESS_STATE_RETURNED = "E";
    /**
     * 移交-待处置
     */
    String MAIL_PROCESS_STATE_TRANSFER_WAIT = "F";
    /**
     * 应税货物
     */
    String MAIL_PROCESS_STATE_DUTIABLE_GOODS = "G";
    /**
     * 待报关
     */
    String MAIL_PROCESS_STATE_DUTIABLE_CUSTOMS_DECLARATION_WAIT = "H";
    /**
     * 已报关
     */
    String MAIL_PROCESS_STATE_DUTIABLE_CUSTOMS_DECLARATION_OK = "I";
    /**
     * 应税物品
     */
    String MAIL_PROCESS_STATE_DUTIABLE_RES = "J";
    /**
     * 待缴税
     */
    String MAIL_PROCESS_STATE_TAXES_WAIT = "K";
    /**
     * 已交税
     */
    String MAIL_PROCESS_STATE_TAXES_OK = "L";
    /**
     * 现场面洽
     */
    String MAIL_PROCESS_STATE_NEGOTIATE = "M";
    /**
     * 需个人申报 （二次申报）
     */
    String MAIL_PROCESS_STATE_DECLARE_WAIT = "N";
    /**
     * 待审核
     */
    String MAIL_PROCESS_STATE_EXAMINE_WAIT = "O";
    /**
     * 未通过
     */
    String MAIL_PROCESS_STATE_ADOPT_NO = "P";
    /**
     * 已入库
     */
    String MAIL_PROCESS_STATE_WAIT_OUTDEPOT = "Q";

    /**
     * 已出库
     */
    String MAIL_PROCESS_STATE_OUTDEPOT = "R";

    /**
     * 待处置
     */
    String MAIL_PROCESS_STATE_WAITDISPOSED = "S";

    /**
     * 需个人申报
     */
    String MAIL_PROCESS_STATE_PERSONAL = "T";

    /**
     * 移交-已处置
     */
    String MAIL_PROCESS_STATE_TRANSFER_OK = "U";

    /**
     * 直接征税
     */
    String MAIL_PROCESS_STATE_PROCESSRES_TAX = "V";
    /**
     * 邮局申报状态-已申报
     */
    String DECLARE_STATUS_DECLARED = "1";
    /**
     * 邮局申报状态-未申报
     */
    String DECLARE_STATUS_UNDECLARED = "0";

    /**
     * 邮件申报审核状态-待审核
     */
    String DECLARE_AUDIT_PENDING = "0";

    /**
     * 邮件类型-应税物品
     */
    String MAIL_TYPE_DUTIABLE_ITEMS = "7";
    /**
     * 邮件类型-应税货物
     */
    String MAIL_TYPE_DUTIABLE_GOODS = "6";
    /**
     * 邮件类型-移交品
     */
    String MAIL_TYPE_TRANSFER = "5";
    /**
     * 邮件类型-正常
     */
    String MAIL_TYPE_NORMALLY = "4";

    /**
     * 邮件申报审核状态-待审核
     */
    String DECLARE_AUDIT_WAITED = "0";

    /**
     * 邮件申报审核状态-审核通过
     */
    String DECLARE_AUDIT_PASS = "1";

    /**
     * 邮件申报审核状态-审核不通过
     */
    String DECLARE_AUDIT_NOT_PASS = "2";

    /**
     * 邮件申报审核状态-不缴税直接放行
     */
    String DECLARE_AUDIT_DIRECT_PASS = "3";

    /**
     * 邮件申报审核状态-现场面洽
     */
    String DECLARE_AUDIT_CONSULT_PERSONALLY = "4";

    /**
     * 短信模版类型-面洽通知
     */
    String SMS_TEMPLATE_TYPE_MQTZ = "MQTZ";
    /**
     * 短信模版类型-征税通知
     */
    String SMS_TEMPLATE_TYPE_ZSTZ = "ZSTZ";
    /**
     * 短信模版类型-申报通知
     */
    String SMS_TEMPLATE_TYPE_SBTZ = "SBTZ";

    /***TaxItem表 缴税标识 taxstatus***/
    /**
     * 已缴税
     */
    String TAX_STATUS_ISPAY = "1";
    /**
     * 未缴税
     */
    String TAX_STATUS_NOTPAY = "0";

}
