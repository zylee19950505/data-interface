package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.UserRole;
import com.xaeport.crossborder.data.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
public class UserManageSQLProvider extends BaseSQLProvider {

    //用户管理—检索查询
    public String userList(final Map<String,String> paramMap) throws Exception{
        final String id = paramMap.get("id");
        final String ic = paramMap.get("ic");
        final String loginName = paramMap.get("loginName");
        final String userType = paramMap.get("userType");
        final String state = paramMap.get("state");
        final String phone = paramMap.get("phone");
        final String email = paramMap.get("email");
        return new SQL() {
            {
                SELECT("id,ic,loginName,userType,state,phone,email");
                FROM("t_users");
                if(!StringUtils.isEmpty(id)){
                    WHERE("id = #{id}");
                }
                if(!StringUtils.isEmpty(userType)){
                    WHERE("userType = #{userType}");
                }
            }
        }.toString();

    }

    //用户管理—查询用户信息，角色信息
    public String findUsersById(final String id)throws Exception{
        return new SQL(){
            {
                SELECT("t1.id");
                SELECT("t1.ic");
                SELECT("t1.loginName");
                SELECT("t1.phone");
                SELECT("t1.email");
                SELECT("t1.password");
                SELECT("t1.userType");
                SELECT("t2.roleid");
                FROM("t_users t1");
                FROM("t_user_role t2");
                WHERE("t1.id = t2.userinfoid");
                WHERE("t1.id = #{id}");
            }
        }.toString();
    }

    //用户管理—查询用户信息
    public String userDetail(@Param("id") String id)throws Exception{
        return new SQL(){
            {
                SELECT("t1.id");
                SELECT("t1.ic");
                SELECT("t1.loginName");
                SELECT("t1.userType");
                SELECT("t1.state");
                SELECT("t1.phone");
                SELECT("t1.email");
                SELECT("t1.password");
                SELECT("t2.roleId");
                SELECT("t3.r_name");
                FROM("t_users t1");
                FROM("t_user_role t2");
                FROM("t_role t3");
                WHERE("t1.id = #{id}");
                WHERE("t1.id = t2.userinfoid");
                WHERE("t2.roleId = t3.r_id");
            }
        }.toString();
    }

    //用户管理—更改用户密码
    public String updatePwById(@Param("id") String id,@Param("password") String password)throws Exception{
        return new SQL(){
            {
                UPDATE("t_users t");
                WHERE("t.id = #{id}");
                SET("t.password = #{password}");
                SET("t.updatetime = sysdate");
            }
        }.toString();
    }

    //用户管理—增添用户信息
    public String insertUser(@Param("users") Users users)throws Exception{
        return new SQL(){
            {
                INSERT_INTO("t_users");
                if(!StringUtils.isEmpty(users.getId())){
                    VALUES("id","#{users.id}");
                }
                if(!StringUtils.isEmpty(users.getIc())){
                    VALUES("ic","#{users.ic}");
                }
                if(!StringUtils.isEmpty(users.getLoginName())){
                    VALUES("loginName","#{users.loginName}");
                }
                if(!StringUtils.isEmpty(users.getPhone())){
                    VALUES("phone","#{users.phone}");
                }
                if(!StringUtils.isEmpty(users.getEmail())){
                    VALUES("email","#{users.email}");
                }
                if(!StringUtils.isEmpty(users.getPassword())){
                    VALUES("password","#{users.password}");
                }
                if(!StringUtils.isEmpty(users.getState())){
                    VALUES("state","#{users.state}");
                }
                if(!StringUtils.isEmpty(users.getCreatorId())){
                    VALUES("creatorId","#{users.creatorId}");
                }
                if(!StringUtils.isEmpty(users.getCreateTime())){
                    VALUES("createTime","#{users.createTime}");
                }
                if(!StringUtils.isEmpty(users.getUpdatorId())){
                    VALUES("updatorId","#{users.updatorId}");
                }
                if(!StringUtils.isEmpty(users.getUpdateTime())){
                    VALUES("updateTime","#{users.updateTime}");
                }
                if(!StringUtils.isEmpty(users.getUserType())){
                    VALUES("userType","#{users.userType}");
                }


            }
        }.toString();
    }

    //用户管理—增添用户角色信息表
    public String insertUserRole(@Param("userRole") UserRole userRole)throws Exception{
        return new SQL(){
            {
                INSERT_INTO("t_user_role");
                if(!StringUtils.isEmpty(userRole.getId())){
                    VALUES("id","#{userRole.id}");
                }
                if(!StringUtils.isEmpty(userRole.getUserInfoId())){
                    VALUES("userInfoId","#{userRole.userInfoId}");
                }
                if(!StringUtils.isEmpty(userRole.getRoleId())){
                    VALUES("roleId","#{userRole.roleId}");
                }
                if(!StringUtils.isEmpty(userRole.getState())){
                    VALUES("state","#{userRole.state}");
                }
                if(!StringUtils.isEmpty(userRole.getCreatorId())){
                    VALUES("creatorId","#{userRole.creatorId}");
                }
                if(!StringUtils.isEmpty(userRole.getCreateTime())){
                    VALUES("createTime","#{userRole.createTime}");
                }
                if(!StringUtils.isEmpty(userRole.getUpdatorId())){
                    VALUES("updatorId","#{userRole.updatorId}");
                }
                if(!StringUtils.isEmpty(userRole.getUpdateTime())){
                    VALUES("updateTime","#{userRole.updateTime}");
                }

            }
        }.toString();
    }

    //用户管理—更改用户信息
    public String updateUser(@Param("users") Users users)throws Exception{
        return new SQL(){
            {
                UPDATE("t_users t");
                WHERE("t.id = #{users.id}");
                SET("t.ic = #{users.ic}");
                SET("t.loginName = #{users.loginName}");
                SET("t.phone = #{users.phone}");
                SET("t.email = #{users.email}");
                SET("t.state = #{users.state}");
                SET("t.updateTime = #{users.updateTime}");
                SET("t.userType = #{users.userType}");
            }
        }.toString();
    }

    //用户管理—更改用户角色信息表
    public String updateUserRole(@Param("userRole") UserRole userRole)throws Exception{
        return new SQL(){
            {
                UPDATE("t_user_role t");
                WHERE("t.userInfoId = #{userRole.userInfoId}");
                SET("t.roleId = #{userRole.roleId}");
                SET("t.updateTime = #{userRole.updateTime}");
            }
        }.toString();
    }

    //用户管理—获取角色下拉菜单
    public String roleSelectList()throws Exception{
        return new SQL(){
            {
                SELECT("r.r_id");
                SELECT("r.r_name");
                FROM("t_role r");
                ORDER_BY("r.createTime ASC");
            }
        }.toString();
    }




}
