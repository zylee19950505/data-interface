package com.xaeport.crossborder.data.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by wx on 2018/3/14.
 */
public class Users {
    private String id;//用户账号
    private String ic;//*ic卡
    private String loginName;//用户昵称
    private String phone;//电话
    private String email;//邮箱
    private String password;//密码
    private int state;//数据状态（0：删除状态，1：正常状态，2锁定状态）
    private String creatorId;//创建人
    private Date createTime;//创建时间
    private String updatorId;//修改人
    private Date updateTime;//修改时间
    private String userType;//用户类型（1：普通用户 2：设备编码）
    private String roleId;//角色ID
    private String roleName;//角色名称
    private String ent_Id;//企业ID
    private String ent_Name;//企业名称
    private String ent_Code;//企业代码
    private String ent_Customs_Code;//海关十位码
    private String ic_Card;//IC卡号
    private String ic_Pwd;//IC卡密码
    private String area_code;//区内企业编号
    private String credit_code;//企业统一社会信用代码
    private String ent_business_type;//跨境企业类型
    private String port;//主管海关
    private String brevity_code;//简码
    private List<Menu> subMenuList;//菜单表
    private List<Menu> childMenuList;//子菜单表
    private Enterprise enterprise;//企业信息

    public String getBrevity_code() {
        return brevity_code;
    }

    public void setBrevity_code(String brevity_code) {
        this.brevity_code = brevity_code;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code;
    }

    public String getEnt_business_type() {
        return ent_business_type;
    }

    public void setEnt_business_type(String ent_business_type) {
        this.ent_business_type = ent_business_type;
    }

    public String getEnt_Customs_Code() {
        return ent_Customs_Code;
    }

    public void setEnt_Customs_Code(String ent_Customs_Code) {
        this.ent_Customs_Code = ent_Customs_Code;
    }

    public String getEnt_Code() {
        return ent_Code;
    }

    public void setEnt_Code(String ent_Code) {
        this.ent_Code = ent_Code;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<Menu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<Menu> subMenuList) {
        this.subMenuList = subMenuList;
    }

    public List<Menu> getChildMenuList() {
        return childMenuList;
    }

    public void setChildMenuList(List<Menu> childMenuList) {
        this.childMenuList = childMenuList;
    }

    public String getEnt_Id() {
        return ent_Id;
    }

    public void setEnt_Id(String ent_Id) {
        this.ent_Id = ent_Id;
    }

    public String getEnt_Name() {
        return ent_Name;
    }

    public void setEnt_Name(String ent_Name) {
        this.ent_Name = ent_Name;
    }

    public String getIc_Card() {
        return ic_Card;
    }

    public void setIc_Card(String ic_Card) {
        this.ic_Card = ic_Card;
    }

    public String getIc_Pwd() {
        return ic_Pwd;
    }

    public void setIc_Pwd(String ic_Pwd) {
        this.ic_Pwd = ic_Pwd;
    }
}
