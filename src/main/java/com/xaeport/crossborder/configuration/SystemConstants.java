package com.xaeport.crossborder.configuration;

/**
 * 系统常量
 */
public interface SystemConstants {

    /** 系统 超级管理员ID */
    String SYSTEM_ADMIN_USER_ID = "admin";
    /** 系统 默认密码 */
    String SYSTEM_DEFAULT_PWD = "888888";
    /** 数据字典 邮寄国代码父级节点ID */
    String SYSTEM_CODE_POST_COUNTRY_PARENT = "000001";

    String CODE_TYPE_AGENT_CODE = "AGENT_CODE";
    String CODE_TYPE_CUSTOMS_CODE = "CUSTOMS_CODE";
    String CODE_TYPE_AGENT_TYPE = "AGENT_TYPE";
    String CODE_TYPE_AGENT_NATURE = "AGENT_NATURE";
    String CODE_TYPE_TRANS_MODE = "TRANS_MODE";
    String CODE_TYPE_TRADE_MODE = "TRADE_MODE";

    /** 系统用户状态 已删除 */
    int SYSTEM_USER_STATUS_DEL = 0;
    /** 系统用户状态 正常 */
    int SYSTEM_USER_STATUS_NORMAL = 1;
    /** 系统用户状态 锁定 */
    int SYSTEM_USER_STATUS_LOCK = 2;
    /** 系统用户类型 普通用户 */
    String SYSTEM_USER_TYPE_NORMAL = "1";
    /** 系统用户类型 设备编码 */
    String SYSTEM_USER_TYPE_APP = "2";
    String RESULT_FALSE = "false";
    String RESULT_TRUE = "true";
    String RESULT = "result";
    String RESULT_MSG = "errorMsg";
    String RESULT_DATA = "resultData";
    String TOTAL_NUM = "totalNum";
    String USER = "user";
    String ROLE = "role";
    String RECIPIENT = "recipient";
    String ENTERMAIL = "enterMail";
    String MAILMANAGES = "mailManages";
    String MAILTRACK = "mailTrack";
    String MENU = "manu";
    /** 任务接口生成接口文件参数数据对象key常量 */
    String MESSAGE_HEAD_KEY = "MessageHead";
    String TABLE_DATA_KEY = "TableData";
    String ROW_DATA_LIST_KEY = "RowDataList";
    String ATTACHMENT_LIST_KEY = "AttachmentList";
    String MESSAGE_BODY = "messageBody";
    String OPERATE_TYPE = "OperateType";
    String TABEL_NAME = "TabelName";
    String PKFIELD = "PKField";
    String FIELDNAME = "FieldName";
    String NAME = "Name";
    String TYPE = "Type";
    String LENGTH = "Length";
    String PATH = "Path";
    String DATE = "Date";
    /** 表名 */
    String CPO_ENTERMAIL = "CPO_ENTERMAIL";
    String CPO_MAILMANAGE = "CPO_MAILMANAGE";
    String CPO_MAILTRACK = "CPO_MAILTRACK";
    /** 字段名 */
    String ID = "ID";
    String STATE = "state";
    String CREATORID = "creatorId";
    String CREATETIME = "createTime";
    String UPDATORID = "updatorId";
    String UPDATETIME = "updateTime";
    String MAIL_NO = "mailNo";
    String MAIL_TYPE = "mailType";
    String MAIL_URL_GOODS = "mailUrl_goods";
    String MAIL_URL_PANLE = "mailUrl_panel";
    String ENTRY_STATE = "entryState";
    String DETAIN_NO = "detainNo";
    String MODIFY_FLAG = "modifyFlag";
    String RECIPIENTNAME = "recipientName";
    String RECIPIENTPHONE = "recipientPhone";
    String RECIPIENTPADDRESS = "recipientAddress";
    String CARRYDATEID = "CarryDataID";
    String CARRYDATE = "CarryData";
    String PROCESSRES = "processRes";
    String PROCESSREMA = "processrema";
    String REPROCESS = "ReProcess";
    String RECEIPTORIDCARD = "receiptorIdCard";
    String AGENTIDCARD = "agentIdCard";
    String MIALSTATE = "mailState";
    String ISPRINT = "isPrint";
    String CUSTOMNO = "customNo";
    String EVENT = "event";
    String INSERT = "0";
    String DELETE = "1";
    String UPDATE = "2";
    String SELECT = "3";
    int ENTRY_STATE_NOT_RECORDED = 0;
    /** 入库邮件状态 已记录 */
    int ENTRY_STATE_RECORDED = 1;
    /** 入库邮件状态 重新采集 */
    int ENTRY_STATE_REGET = 2;
    /** 通用状态 删除 */
    int COMMON_STATE_DEL = 0;
    /** 通用状态 正常 */
    int COMMON_STATE_NORMAL = 1;
    /** 通用状态 锁定 */
    int COMMON_STATE_LOCK = 2;
    /** 打印状态 未打印 */
    int PRINT_STATE_NO = 0;
    /** 打印状态 已打印 */
    int PRINT_STATE_YES = 1;

    String HTTP_CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
    String HTTP_CONTENT_TYPE_EXE = "application/x-msdownload";
    String HTTP_CONTENT_TYPE_XML = "application/xml";


    /*
    * 区别订单和支付单运单的
    * */
    String entryType_DD = "DD";//订单
    String entryType_QD = "QD";//清单
    String entryType_YD = "YD";//运单


}
