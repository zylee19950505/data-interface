package com.xaeport.crossborder.service;


import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Menu;
import com.xaeport.crossborder.data.entity.TiedCard;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.UserMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zwj on 2017/07/18.
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<Users> getUserInfoList() {
        return userMapper.getUserInfoList();
    }

    public Users getUserById(String id) {
        return this.userMapper.getUserById(id);
    }

    public Users authUserLogin(String id,String loginName) {
        return this.userMapper.authUserLogin(id,loginName);
    }

    public Enterprise getEnterpriseDetail(String enterpriseId) {
        return this.userMapper.getEnterpriseDetail(enterpriseId);
    }

    public Integer changePassword(String newPassword, Date updateTime, String userId) {
        return this.userMapper.changePassword(newPassword, updateTime, userId);
    }

    public List<Menu> getRoleParentMenu(String id) {
        return this.userMapper.getRoleParentMenu(id);
    }

    public List<Menu> getRoleChildMenu(String id) {
        return this.userMapper.getRoleChildMenu(id);
    }


    /**
     * 是否绑定IC卡
     *
     * @param securityUsers
     * @return
     */
    public boolean checkUserIc(Users securityUsers) {
        String userId = securityUsers.getId();
        int userCount = this.userMapper.checkUserHasIcCard(userId);
        if (userCount < 1) {
            return false;
        }
        return true;
    }

    /**
     * 绑定IC卡卡号到当前用户
     *
     * @param securityUsers
     * @param icCardNo
     * @return
     */
    public boolean saveBandIc(Users securityUsers, String icCardNo, String icCardPwd) {
        String userId = securityUsers.getId();
        String oldIcCard = this.userMapper.getIcByUsers(userId);
        oldIcCard = StringUtils.isEmpty(oldIcCard) ? "无" : oldIcCard;
        int updCount = this.userMapper.updateIcCardByUserId(userId, icCardNo, icCardPwd);
        if (updCount > 0) {
            // 增加绑卡记录
            TiedCard tiedCard = new TiedCard();
            tiedCard.setTcId(IdUtils.getUUId());
            tiedCard.setCreateTime(new Date());
            tiedCard.setCardNo(icCardNo);
            tiedCard.setuId(userId);
            tiedCard.setRemark(String.format("由 %s 变更为 %s ", oldIcCard, icCardNo));
            this.userMapper.insertTiedCard(tiedCard);
            return true;
        }
        return false;
    }

    /**
     * 获取当前用户绑定的IC卡号
     *
     * @param securityUsers
     * @return
     */
    public String getUserIcCardNo(Users securityUsers) {
        String userId = securityUsers.getId();
        String icCardNo = this.userMapper.getIcByUsers(userId);
        return icCardNo;
    }

    /**
     * 获取用户绑卡操作历史记录
     *
     * @param paramMap
     * @return
     */
    public List<Map<String, String>> getBandOprHis(Map<String, String> paramMap) {
        return this.userMapper.getBandOprHis(paramMap);
    }

}
