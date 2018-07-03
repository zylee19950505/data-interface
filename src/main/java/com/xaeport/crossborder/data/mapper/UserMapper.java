package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.Menu;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.UserSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by lzy on 2018/4/27.
 */
@Mapper
public interface UserMapper {

    @Select("select * from cpo_userInfo")
    List<Users> getUserInfoList();

    //获取登录用户认证信息
//    @Select("select t1.id,t1.loginName,t1.password,t3.r_name,t3.remark,t1.createTime,t1.updateTime from cpo_userinfo t1,Cpo_User_Role t2,cpo_role t3 where t1.id=#{id} and t1.state='1' and t1.id = t2.userinfoid and t2.roleId = t3.r_id ")
    @SelectProvider(type= UserSQLProvider.class,method = "getUserById")
    Users getUserById(String id);

    //获取父级菜单列表
//    @Select("select t3.* from cpo_user_role t1,cpo_role_menu t2,cpo_menu t3 where t1.userinfoid = #{id} and t1.roleId = t2.r_id and t2.m_id = t3.m_id and t3.pid='0' and t3.m_status='1' order by t3.m_sort")
    @SelectProvider(type= UserSQLProvider.class,method = "getRoleParentMenu")
    List<Menu> getRoleParentMenu(String id);

    //获取子级菜单列表
//    @Select("select t3.* from cpo_user_role t1,cpo_role_menu t2,cpo_menu t3 where t1.userinfoid = #{id} and t1.roleId = t2.r_id and t2.m_id = t3.m_id and t3.pid!='0' and t3.m_status='1' order by t3.m_sort")
    @SelectProvider(type= UserSQLProvider.class,method = "getRoleChildMenu")
    List<Menu> getRoleChildMenu(String id);

//    @Select("select t1.id,t1.loginName,t1.password,t1.createTime,t1.updateTime from cpo_userinfo t1 where t1.id=#{id} ")
    @SelectProvider(type= UserSQLProvider.class,method = "authUserLogin")
    Users authUserLogin(@Param("id") String id, @Param("loginName") String loginName);

//    @Update("update cpo_userinfo set password=#{newPassword},update_time=#{updateTime} where ID=#{userId}")
    @SelectProvider(type= UserSQLProvider.class,method = "changePassword")
    Integer changePassword(@Param("newPassword") String newPassword, @Param("updateTime") Date updateTime, @Param("userId") String userId);

}
