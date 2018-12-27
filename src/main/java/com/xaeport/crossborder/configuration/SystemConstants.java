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

    String T_IMP_ORDER = "ORDER";
    String T_IMP_PAYMENT = "PAYMENT";
    String T_IMP_LOGISTICS = "LOGISTICS";
    String T_IMP_INVENTORY = "INVENTORY";
    String T_IMP_BOND_INVEN = "BONDINVEN";

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

    String RESULT = "result";

    /** 字段名 */
    String ID = "ID";
    String DELETE = "1";
    String UPDATE = "2";
    String SELECT = "3";

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
    String HTTP_CONTENT_TYPE_ZIP = "application/x-zip-compressed";


    /*
    * 区别订单和支付单运单的
    * */
    String entryType_DD = "DD";//订单
    String entryType_QD = "QD";//清单
    String entryType_YD = "YD";//运单


}
