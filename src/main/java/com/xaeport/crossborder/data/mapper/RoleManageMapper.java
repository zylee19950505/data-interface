package com.xaeport.crossborder.data.mapper;

import com.xaeport.crossborder.data.entity.Role;
import com.xaeport.crossborder.data.entity.RoleMenu;
import com.xaeport.crossborder.data.provider.RoleManageSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/4/27.
 */
@Mapper
public interface RoleManageMapper {

    //角色管理—查询角色
    @SelectProvider(type = RoleManageSQLProvider.class, method = "roleList")
    List<Map<String,String>> roleList(Map<String, String> paramMap);

    //角色管理—查询当前角色权限表
    @SelectProvider(type = RoleManageSQLProvider.class, method = "queryMenuBypIdrId")
    List<Map<String,Object>> queryMenuBypIdrId(Map<String, String> paramMap);

    //角色管理—查询所有角色权限表
    @SelectProvider(type = RoleManageSQLProvider.class, method = "queryMenuBypId")
    List<Map<String,Object>> queryMenuBypId(String pId);

    //角色管理—角色名称重复查询
    @SelectProvider(type = RoleManageSQLProvider.class, method = "isRoleRepeat")
    int isRoleRepeat(Map<String, String> paramMap);

    //角色管理—新增角色表
//    @Insert("insert into cpo_role (r_Id, r_Name, remark, createTime, updateTime) " +
//            "values (#{role.r_Id}, #{role.r_Name}, #{role.remark}, #{role.createTime}, #{role.updateTime})")
    @InsertProvider(type = RoleManageSQLProvider.class, method = "insertRole")
    int insertRole(@Param("role") Role role);

    //角色管理—新增角色菜单表
//    @Insert("insert into cpo_role_menu (id, r_Id, m_Id, createTime, updateTime) " +
//            "values (#{roleMenu.id}, #{roleMenu.r_Id}, #{roleMenu.m_Id}, #{roleMenu.createTime}, #{roleMenu.updateTime})")
    @InsertProvider(type = RoleManageSQLProvider.class, method = "insertRoleMenu")
    int insertRoleMenu(@Param("roleMenu") RoleMenu roleMenu);

    //角色管理—更改角色数据
//    @Update("update cpo_role set r_name = #{role.r_Name}, remark = #{role.remark}, updateTime = #{role.updateTime} where r_Id = #{role.r_Id} ")
    @UpdateProvider(type = RoleManageSQLProvider.class, method = "updateRole")
    int updateRole(@Param("role") Role role);

    //角色管理—查询角色信息
//    @Select("select r_Id,r_Name,remark from cpo_role t where t.r_Id = #{r_Id} ")
    @SelectProvider(type = RoleManageSQLProvider.class, method = "roleDetail")
    Map<String, String> roleDetail(@Param("r_Id") String r_Id);

    //角色管理—删除角色信息
//    @Delete("delete from cpo_role where r_Id = #{r_Id} and r_id != 'admin'")
    @DeleteProvider(type = RoleManageSQLProvider.class, method = "roleDelete")
    void roleDelete(@Param("r_Id") String r_Id);

    //角色管理—删除角色菜单信息
//    @Delete("delete from cpo_role_menu where r_id = #{r_Id}")
    @DeleteProvider(type = RoleManageSQLProvider.class, method = "roleMenuDelete")
    void roleMenuDelete(@Param("r_Id") String r_Id);




}
