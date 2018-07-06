package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.Role;
import com.xaeport.crossborder.data.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
public class RoleManageSQLProvider extends BaseSQLProvider {


    //角色管理—检索查询
    public String roleList(final Map<String,String> paramMap)throws Exception{
        final String r_id = paramMap.get("r_id");
        final String r_name = paramMap.get("r_name");
        final String remark = paramMap.get("remark");
        return new SQL(){
            {
                SELECT("r_id,r_name,remark");
                FROM("t_role");
                if(!StringUtils.isEmpty(r_name)){
                    WHERE("r_name = #{r_name}");
                }
            }
        }.toString();
    }

    //角色管理—根据角色Id获取权限树
    public String queryMenuBypIdrId(Map<String,String> paramMap)throws Exception{
        return new SQL(){
            {
                SELECT("t.m_Id");
                SELECT("t.pId");
                SELECT("t.m_Name");
                SELECT("t.m_Url");
                SELECT("t.m_Grade");
                SELECT("t.m_Status");
                SELECT("t.m_Sort");
                SELECT("to_char(t.createTime,'yyyy-MM-dd hh24:mi:ss') as createTime");
                SELECT("(select case when count(1) > 0 then 'true' else  'false' end from t_menu tt where tt.pId = t.m_Id) as isparent");
                SELECT("(select case when count(1) > 0 then 'true' else  'false' end from t_role_menu tt where tt.m_Id = t.m_Id and tt.r_Id = #{r_Id}) as ischecked");
                FROM("t_menu t");
                WHERE("t.m_Status = '1'");
                WHERE("t.pId = #{pId}");
                ORDER_BY("t.pId asc,t.m_Sort asc");
            }
        }.toString();
    }

    //角色管理—根据角色Id获取权限树
    public String queryMenuBypId(String pId)throws Exception{
        return new SQL(){
            {
                SELECT("t.m_Id");
                SELECT("t.pId");
                SELECT("t.m_Name");
                SELECT("t.m_Url");
                SELECT("t.m_Grade");
                SELECT("t.m_Status");
                SELECT("t.m_Sort");
                SELECT("to_char(t.createTime,'yyyy-MM-dd hh24:mi:ss') as createTime");
                SELECT("(select case when count(1) > 0 then 'true' else  'false' end from t_menu tt where tt.pId = t.m_Id) as isparent");
                FROM("t_menu t");
                WHERE("t.m_Status = '1'");
                WHERE("t.pId = #{pId}");
                ORDER_BY("t.pId asc,t.m_Sort asc");
            }
        }.toString();
    }

    //角色管理—查询角色名称是否重复
    public String isRoleRepeat(final Map<String, String> paramMap)throws Exception {
        final String hasAdmin = paramMap.get("hasAdmin");
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("t_role t");
                WHERE("t.r_Name = #{r_Name}");
                if ("0".equals(hasAdmin)) {
                    WHERE("t.r_id != 'admin' ");
                }
            }
        }.toString();
    }

    //角色管理—新增角色表
    public String insertRole(@Param("role") Role role)throws Exception{
        return new SQL(){
            {

                INSERT_INTO("t_role");
                if(!StringUtils.isEmpty(role.getR_Id())){
                    VALUES("r_Id","#{role.r_Id}");
                }
                if(!StringUtils.isEmpty(role.getR_Name())){
                    VALUES("r_Name","#{role.r_Name}");
                }
                if(!StringUtils.isEmpty(role.getRemark())){
                    VALUES("remark","#{role.remark}");
                }
                if(!StringUtils.isEmpty(role.getCreateTime())){
                    VALUES("createTime","#{role.createTime}");
                }
                if(!StringUtils.isEmpty(role.getUpdateTime())){
                    VALUES("updateTime","#{role.updateTime}");
                }

            }
        }.toString();
    }

    //角色管理—新增角色菜单表
    public String insertRoleMenu(@Param("roleMenu") RoleMenu roleMenu)throws Exception{
        return new SQL(){
            {
                INSERT_INTO("t_role_menu");
                if(!StringUtils.isEmpty(roleMenu.getId())){
                    VALUES("id","#{roleMenu.id}");
                }
                if(!StringUtils.isEmpty(roleMenu.getId())){
                    VALUES("r_Id","#{roleMenu.r_Id}");
                }
                if(!StringUtils.isEmpty(roleMenu.getId())){
                    VALUES("m_Id","#{roleMenu.m_Id}");
                }
                if(!StringUtils.isEmpty(roleMenu.getId())){
                    VALUES("createTime","#{roleMenu.createTime}");
                }
                if(!StringUtils.isEmpty(roleMenu.getId())){
                    VALUES("updateTime","#{roleMenu.updateTime}");
                }

            }
        }.toString();
    }

    //角色管理—更改角色数据
    public String updateRole(@Param("role") Role role)throws Exception{
        return new SQL(){
            {
                UPDATE("t_role");
                WHERE("r_Id = #{role.r_Id}");
                SET("r_name = #{role.r_Name}");
                SET("remark = #{role.remark}");
                SET("updateTime = #{role.updateTime}");
            }
        }.toString();
    }

    //角色管理—查询角色信息
    public String roleDetail(@Param("r_Id") String r_Id)throws Exception{
        return new SQL(){
            {
                SELECT("r_Id");
                SELECT("r_Name");
                SELECT("remark");
                FROM("t_role");
                WHERE("r_Id = #{r_Id}");
            }
        }.toString();
    }

    //角色管理—删除角色信息
    public String roleDelete(@Param("r_Id") String r_Id)throws Exception{
        return new SQL(){
            {
                DELETE_FROM("t_role");
                WHERE("r_Id = #{r_Id}");
                WHERE("r_id != 'admin'");
            }
        }.toString();
    }

    //角色管理—删除角色菜单信息
    public String roleMenuDelete(@Param("r_Id") String r_Id)throws Exception{
        return new SQL(){
            {
                DELETE_FROM("t_role_menu");
                WHERE("r_Id = #{r_Id}");
            }
        }.toString();
    }



}
