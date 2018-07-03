
package com.xaeport.crossborder.data.entity;

/**
 * @ClassName: MenuEnum
 * @Description: 系统操作日志相关枚举类
 */
public enum SystemLogEnum {
	XTDL("系统登录", "XTDL"), CDGL("菜单管理", "CDGL"), JSGL("角色管理", "JSGL"), YHGL("用户管理", "YHGL"), XTCSPZ("系统参数配置", "XTCSPZ");
	/**
	 * 
	 * 操作业务日志常量
	 * 
	 */
	/** 日志操作类型添加 */
	public static final String METHOD_SAVE = "保存";
	/** 日志操作类型删除 */
	public static final String METHOD_DELETE = "删除";
	/** 日志操作类型修改 */
	public static final String METHOD_UPDATE = "修改";
	/** 日志操作类型查询 */
	public static final String METHOD_SELECT = "查询";
	/** 日志操作类型登录 */
	public static final String METHOD_LOGIN = "登录";
	/** 日志操作结果成功 */
	public static final String RESULT_SUCCESS = "操作成功";
	/** 日志操作结果失败 */
	public static final String RESULT_FAILURE = "操作失败";

	private String menuName;

	private String menuCode;

	private SystemLogEnum(String menuName, String menuCode) {
		this.menuName = menuName;
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
