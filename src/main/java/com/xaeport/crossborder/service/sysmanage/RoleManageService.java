package com.xaeport.crossborder.service.sysmanage;

import com.xaeport.crossborder.data.entity.Role;
import com.xaeport.crossborder.data.entity.RoleMenu;
import com.xaeport.crossborder.data.mapper.RoleManageMapper;
import com.xaeport.crossborder.data.provider.BaseSQLProvider;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lzy on 2018/7/4.
 */
@Service
public class RoleManageService extends BaseSQLProvider{

    private Log log = LogFactory.getLog(this.getClass());

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

    @Autowired
    RoleManageMapper roleMaMapper;
    
    /*
     * 角色管理—获取全部角色信息
     */
    public List<Map<String,String>> roleList(Map<String,String> paramMap){
        return this.roleMaMapper.roleList(paramMap);
    }

    /*
     * 角色管理—根据角色Id获取详情信息
     */
    public Map<String,String> roleDetail(String r_Id){
        return this.roleMaMapper.roleDetail(r_Id);
    }

    /*
     * 角色管理—创建角色
     */
    public boolean roleCreate(Role role, String mIds){
        String r_Id = IdUtils.getUUId();
        role.setR_Id(r_Id);
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());

        int roleCount = this.roleMaMapper.insertRole(role);
        if(roleCount > 0){
            this.log.debug(String.format("创建角色信息[r_Name: %s]成功", role.getR_Name()));
            insertRoleMenu(r_Id, mIds);
            this.log.debug(String.format("添加角色[r_Name: %s]拥有权限信息[mIds: %s]成功", role.getR_Name(), mIds));
            return true;
        }

        this.log.debug(String.format("创建角色信息[r_Name: %s]失败",role.getR_Name()));
        return false;
    }

    /**
     * 角色管理—角色信息编辑
     */
    public boolean roleEdit(Role role, String mIds) {
        String r_Id = role.getR_Id();
        role.setUpdateTime(new Date());

        int roleCount = this.roleMaMapper.updateRole(role);
        if (roleCount > 0) {
            this.log.debug(String.format("修改角色信息[r_Id: %s]成功", r_Id));
            this.roleMaMapper.roleMenuDelete(r_Id);
            this.log.debug(String.format("删除角色[r_Id: %s]下权限组成功", r_Id));
            insertRoleMenu(r_Id, mIds);
            this.log.debug(String.format("添加角色[r_Id: %s]拥有权限信息[mIds: %s]成功", r_Id, mIds));
            return true;
        }
        return false;
    }

    /**
     * 角色管理—根据角色ID新增权限组配置信息
     */
    private void insertRoleMenu(String r_Id, String mIds) {
        List<String> midList = Arrays.asList(mIds.split(","));
        this.log.debug(String.format("根据角色ID新增权限组配置信息,拆分权限组Ids[mIds: %s]", mIds));

        for (String mid : midList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setId(IdUtils.getUUId());
            roleMenu.setR_Id(r_Id);
            roleMenu.setM_Id(mid);
            roleMenu.setCreateTime(new Date());
            roleMenu.setUpdateTime(new Date());
            this.log.debug(String.format("添加权限信息[mid: %s]到角色[rId: %s]", mid, r_Id));
            this.roleMaMapper.insertRoleMenu(roleMenu);
        }
    }

    /*
     * 角色管理—根据角色Id 删除角色
     */
    public void roleDelete(String r_Id){
        this.log.debug(String.format("根据角色ID[r_Id: %s]删除用户", r_Id));
        this.roleMaMapper.roleDelete(r_Id);
        this.log.debug(String.format("删除角色相关权限配置[r_Id: %s]", r_Id));
        this.roleMaMapper.roleMenuDelete(r_Id);
    }

    /*
     * 角色管理—根据PId获取权限树
     */
    public List<Map<String,Object>> queryMenuBypId(String pId){
        return this.roleMaMapper.queryMenuBypId(pId);
    }

    /*
     * 角色管理—根据角色Id获取权限树
     */
    public List<Map<String,Object>> queryMenuBypIdrId(String pId,String r_Id){
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("pId",pId);
        paramMap.put("r_Id",r_Id);
        return this.roleMaMapper.queryMenuBypIdrId(paramMap);
    }

    /*
     * 角色管理—验证角色名称重复
     */
    public boolean isRoleRepeat(String r_Name,boolean hasAdmin){
        String isAdmin = hasAdmin ? "1":"0";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("r_Name",r_Name);
        paramMap.put("hasAdmin",isAdmin);

        int count = this.roleMaMapper.isRoleRepeat(paramMap);
        this.log.debug(String.format("验证角色是否重复[r_Name: %s, isAdmin: %s]结果为：%s ", r_Name, hasAdmin, (count > 0)));
        return count > 0;
    }
    
}
