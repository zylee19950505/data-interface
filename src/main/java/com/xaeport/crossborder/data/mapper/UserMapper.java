package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Menu;
import com.xaeport.crossborder.data.entity.TiedCard;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.provider.UserSQLProvider;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */
@Mapper
public interface UserMapper {

    @Select("select * from t_users")
    List<Users> getUserInfoList();

    //获取登录用户认证信息
    @SelectProvider(type= UserSQLProvider.class,method = "getUserById")
    Users getUserById(String id);

    //获取用户企业信息
    @Select("SELECT * FROM T_ENTERPRISE t where t.ID = #{enterpriseId}")
    Enterprise getEnterpriseDetail(@Param("enterpriseId") String enterpriseId);

    //获取父级菜单列表
    @SelectProvider(type= UserSQLProvider.class,method = "getRoleParentMenu")
    List<Menu> getRoleParentMenu(String id);

    //获取子级菜单列表
    @SelectProvider(type= UserSQLProvider.class,method = "getRoleChildMenu")
    List<Menu> getRoleChildMenu(String id);

    @SelectProvider(type= UserSQLProvider.class,method = "authUserLogin")
    Users authUserLogin(@Param("id") String id, @Param("loginName") String loginName);

    @SelectProvider(type= UserSQLProvider.class,method = "changePassword")
    Integer changePassword(@Param("newPassword") String newPassword, @Param("updateTime") Date updateTime, @Param("userId") String userId);

    //根据用户id查询企业id号码
    @Select("SELECT t.ENT_ID FROM T_USERS t WHERE t.ID = #{id}")
    String getEnterpriseId(@Param("id") String id);

    //查询用户是否绑定IC卡信息
    @SelectProvider(type = UserSQLProvider.class,method = "checkUserHasIcCard")
    int checkUserHasIcCard(@Param("userId") String userId);

    //根据用户id查询用户的IC卡信息
    @Select("SELECT IC_CARD FROM T_USERS WHERE ID = #{userId}")
    String getIcByUsers(@Param("userId") String userId);

    //查询IC卡绑卡操作记录
    @SelectProvider(type = UserSQLProvider.class,method = "getBandOprHis")
    List<Map<String,String>> getBandOprHis(Map<String, String> paramMap);

    //变更IC信息
    @UpdateProvider(type = UserSQLProvider.class,method = "updateIcCardByUserId")
    int updateIcCardByUserId(@Param("userId") String userId,@Param("icCardNo")  String icCardNo,@Param("icCardPwd") String icCardPwd);

    //插入IC卡绑定记录
    @InsertProvider(type = UserSQLProvider.class,method = "insertTiedCard")
    void insertTiedCard(@Param("tiedCard") TiedCard tiedCard);
}
