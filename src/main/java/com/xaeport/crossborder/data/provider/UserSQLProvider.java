package com.xaeport.crossborder.data.provider;

import com.xaeport.crossborder.data.entity.TiedCard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
public class UserSQLProvider extends BaseSQLProvider {

    //获取登录用户认证信息
    public String getUserById(final String id) throws Exception {
        return new SQL() {
            {
                SELECT("t1.id");
                SELECT("t1.loginName");
                SELECT("t1.password");
                SELECT("t1.createTime");
                SELECT("t1.updateTime");
                SELECT("t1.ent_Id");
                SELECT("t1.ent_Name");
                SELECT("t1.ic_Card");
                SELECT("t1.ic_Pwd");
                SELECT("t1.phone");
                SELECT("t1.email");
                SELECT("t1.userType");
                SELECT("t3.r_name");
                SELECT("t3.remark");
                FROM("T_USERS t1");
                FROM("T_USER_ROLE t2");
                FROM("T_ROLE t3");
                WHERE("t1.id = #{id}");
                WHERE("t1.state = '1'");
                WHERE("t1.id = t2.userInfoId");
                WHERE("t2.roleId = t3.r_id AND " +
                        "EXISTS(SELECT t.id FROM T_ENTERPRISE t where t.id = t1.ent_Id and t.status = '1')");
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

    public String getBandOprHis(Map<String, String> paramMap) {
        final String userId = paramMap.get("userId");
        return new SQL() {
            {
                SELECT("t.TC_ID");
                SELECT("t.U_ID");
                SELECT("t.CARD_NO");
                SELECT("to_char(t.CREATE_TIME,'yyyy-MM-dd HH24:mi:ss') as CREATE_TIME");
                SELECT("t.REMARK");
                SELECT("u.LOGINNAME");
                FROM("T_TIED_CARD t");
                LEFT_OUTER_JOIN("T_USERS u ON t.U_ID = u.ID");
                WHERE("t.U_ID = #{userId}");
                ORDER_BY("t.CREATE_TIME desc");
            }
        }.toString();
    }

    public String updateIcCardByUserId(
            @Param("userId") String userId,
            @Param("icCardNo") String icCardNo,
            @Param("icCardPwd") String icCardPwd
    ) {
        return new SQL() {
            {
                UPDATE("T_USERS");
                WHERE("ID = #{userId}");
                SET("IC_CARD = #{icCardNo}");
                SET("IC_PWD = #{icCardPwd}");
                SET("UPDATETIME = sysdate");
            }
        }.toString();
    }

    public String insertTiedCard(@Param("tiedCard") TiedCard tiedCard) {
        return new SQL() {
            {
                INSERT_INTO("T_TIED_CARD");
                VALUES("TC_ID", "#{tiedCard.tcId}");
                VALUES("U_ID", "#{tiedCard.uId}");
                VALUES("CARD_NO", "#{tiedCard.cardNo}");
                VALUES("CREATE_TIME", "#{tiedCard.createTime}");
                VALUES("REMARK", "#{tiedCard.remark}");
            }
        }.toString();
    }

    public String checkUserHasIcCard(@Param("userId") String userId) {
        return new SQL() {
            {
                SELECT("COUNT(1)");
                FROM("T_USERS u");
                WHERE("id = #{userId}");
                WHERE("(" +
                        "u.IC_CARD IS NOT NULL " +
                        "OR " +
                        "((select ROLEID from t_user_role t2 where t2.USERINFOID = #{userId}) = 'admin')" +
                        ")");
            }
        }.toString();
    }

}
