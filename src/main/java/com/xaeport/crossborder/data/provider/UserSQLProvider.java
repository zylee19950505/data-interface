package com.xaeport.crossborder.data.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * Created by lzy on 2018/7/4.
 */
public class UserSQLProvider extends BaseSQLProvider {

    //获取登录用户认证信息
    public String getUserById(final String id) throws Exception {
        return new SQL() {
            {
                SELECT("t1.id,t1.loginName,t1.password,t1.createTime,t1.updateTime");
                SELECT("t1.phone,t1.email,t1.userType");
                SELECT("t3.r_name,t3.remark");
                FROM("t_users t1");
                FROM("t_user_role t2");
                FROM("t_role t3");
                WHERE("t1.id = #{id}");
                WHERE("t1.state = '1'");
                WHERE("t1.id = t2.userInfoId");
                WHERE("t2.roleId = t3.r_id");
            }
        }.toString();
    }

    //获取父级菜单列表
    public String getRoleParentMenu(final String id) throws Exception {
        return new SQL() {
            {
                SELECT("t3.*");
                FROM("t_user_role t1");
                FROM("t_role_menu t2");
                FROM("t_menu t3");
                WHERE("t1.userInfoId = #{id}");
                WHERE("t1.roleId = t2.r_id");
                WHERE("t2.m_id = t3.m_id");
                WHERE("t3.pid = '0'");
                WHERE("t3.m_status ='1'");
                ORDER_BY("t3.m_sort");
            }
        }.toString();
    }

    //获取子级菜单列表
    public String getRoleChildMenu(final String id) throws Exception {
        return new SQL() {
            {
                SELECT("t3.*");
                FROM("t_user_role t1");
                FROM("t_role_menu t2");
                FROM("t_menu t3");
                WHERE("t1.userInfoId = #{id}");
                WHERE("t1.roleId = t2.r_id");
                WHERE("t2.m_id = t3.m_id");
                WHERE("t3.pid != '0'");
                WHERE("t3.m_status ='1'");
                ORDER_BY("t3.m_sort");
            }
        }.toString();
    }

    public String authUserLogin(final @Param("id") String id, @Param("loginName") String loginName) throws Exception {
        return new SQL() {
            {
                SELECT("t1.id");
                SELECT("t1.password");
                SELECT("t1.createTime");
                SELECT("t1.updateTime");
                FROM("t_users t1");
                WHERE("t1.id = #{id} and t1.loginName = #{loginName}");
            }
        }.toString();
    }

    public String changePassword(final @Param("newPassword") String newPassword, @Param("updateTime") Date updateTime, @Param("userId") String userId) throws Exception {
        return new SQL() {
            {
                UPDATE("t_users");
                SET("password = #{newPassword}");
                SET("updatetime = #{updateTime}");
                WHERE("id = #{userId}");
            }
        }.toString();
    }

}
