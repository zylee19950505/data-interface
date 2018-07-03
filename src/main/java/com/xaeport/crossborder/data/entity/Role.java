package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * Created by wx on 2018/3/15.
 */
public class Role {
    private String r_Id;//角色Id
    private String e_Id;
    private String r_Name;//角色名称
    private String remark;//角色描述
    private String creatorId;
    private Date createTime;
    private String updatorId;
    private Date updateTime;

    public String getR_Id() {
        return r_Id;
    }

    public void setR_Id(String r_Id) {
        this.r_Id = r_Id;
    }

    public String getE_Id() {
        return e_Id;
    }

    public void setE_Id(String e_Id) {
        this.e_Id = e_Id;
    }

    public String getR_Name() {
        return r_Name;
    }

    public void setR_Name(String r_Name) {
        this.r_Name = r_Name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
