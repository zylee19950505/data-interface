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
    String T_IMP_BOND_ORDER = "BONDORDER";
    String T_IMP_BOND_INVEN = "BONDINVEN";

    String T_BOND_INVT = "BONDINVT";
    String T_PASS_PORT = "PASSPORT";

    //保税入区
    String BSRQ = "ENTER";
    //保税出区
    String BSCQ = "EXIT";

    /** 字段名 */
    String ID = "ID";
    String DELETE = "1";
    String UPDATE = "2";
    String SELECT = "3";

    String HTTP_CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
    String HTTP_CONTENT_TYPE_EXE = "application/x-msdownload";
    String HTTP_CONTENT_TYPE_XML = "application/xml";
    String HTTP_CONTENT_TYPE_ZIP = "application/x-zip-compressed";

    String DJ_DD = "CEB311";
    String DJ_HZQD = "CEBINV101";

}
