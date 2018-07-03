package com.xaeport.crossborder.data.entity;

import java.util.Date;

/**
 * Created by wx on 2018/3/14.
 */
public class Menu {
    private String m_Id;
    private String pId;
    private String m_Name;
    private String m_Url;
    private String i_Url;
    private String m_Grade;
    private String m_Status;
    private String m_Owner;
    private String remark;
    private String m_Sort;
    private String creatorId;
    private Date createTime;
    private String updatorId;
    private Date updateTime;


    public String getM_Id() {
        return m_Id;
    }

    public void setM_Id(String m_Id) {
        this.m_Id = m_Id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getM_Name() {
        return m_Name;
    }

    public void setM_Name(String m_Name) {
        this.m_Name = m_Name;
    }

    public String getM_Url() {
        return m_Url;
    }

    public void setM_Url(String m_Url) {
        this.m_Url = m_Url;
    }

    public String getI_Url() {
        return i_Url;
    }

    public void setI_Url(String i_Url) {
        this.i_Url = i_Url;
    }

    public String getM_Grade() {
        return m_Grade;
    }

    public void setM_Grade(String m_Grade) {
        this.m_Grade = m_Grade;
    }

    public String getM_Status() {
        return m_Status;
    }

    public void setM_Status(String m_Status) {
        this.m_Status = m_Status;
    }

    public String getM_Owner() {
        return m_Owner;
    }

    public void setM_Owner(String m_Owner) {
        this.m_Owner = m_Owner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getM_Sort() {
        return m_Sort;
    }

    public void setM_Sort(String m_Sort) {
        this.m_Sort = m_Sort;
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
