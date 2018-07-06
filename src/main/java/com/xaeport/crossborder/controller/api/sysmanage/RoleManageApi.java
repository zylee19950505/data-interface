package com.xaeport.crossborder.controller.api.sysmanage;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/7/4.
 */

@RestController
@RequestMapping("/api/sysmanage")
public class RoleManageApi extends BaseApi{
    
    private Log logger = LogFactory.getLog(this.getClass());
    /*@Autowired
    private SaveLog saveLog;*/

    /**
     * 角色管理—角色列表查询及条件检索
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ResponseData roleList(
            @RequestParam(value = "r_Name",required = false) String r_Name
    ) {
        this.logger.debug(String.format("角色权限组列表查询条件[r_Name: %s]", r_Name));
        Map<String, String> paramMap = new HashMap<String, String>();
        // 查询参数
        paramMap.put("r_name", r_Name);
        List<Map<String, String>> roleList = null;
        try {
            roleList = this.roleMaService.roleList(paramMap);
        } catch (Exception e) {
            this.logger.debug(String.format("角色列表查询失败[r_Name: %s]",r_Name));
        } finally {
         /*   saveLog.saveSystemLog(SystemLogEnum.JSGL.getMenuCode(), SystemLogEnum.METHOD_SELECT);*/
        }
        return new ResponseData(roleList);
    }

    /**
     * 角色管理—根据角色ID获取详细信息
     */
    @RequestMapping(value = "/role/{r_Id}", method = RequestMethod.GET)
    public ResponseData roleDetail(
            @PathVariable(value = "r_Id") String r_Id
    ) {
        if (StringUtils.isEmpty(r_Id)) {
            return new ResponseData("角色ID不可为空", HttpStatus.FORBIDDEN);
        }
        Map<String, String> roleDetail = this.roleMaService.roleDetail(r_Id);
        return new ResponseData(roleDetail);
    }

    /**
     * 角色管理—角色删除
     */
    @RequestMapping(value = "/role/{r_Id}", method = RequestMethod.DELETE)
    public ResponseData roleDelete(@PathVariable(value = "r_Id") String r_Id) {
        if (SystemConstants.SYSTEM_ADMIN_USER_ID.equals(r_Id)) {
            return new ResponseData("超级管理员角色禁止删除", HttpStatus.FORBIDDEN);
        }
        this.logger.debug(String.format("角色权限删除[r_Id: %s]", r_Id));
        this.roleMaService.roleDelete(r_Id);
       /* saveLog.saveSystemLog(SystemLogEnum.JSGL.getMenuCode(), SystemLogEnum.METHOD_DELETE);*/
        return new ResponseData(1);
    }

    /**
     * 角色管理—角色权限新增
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ResponseData roleCreate(
            @RequestParam(required = false) String r_name,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String mIds
    ) {
        if (StringUtils.isEmpty(r_name)) {
            return new ResponseData("角色名称不能为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(mIds)) {
            return new ResponseData("请勾选角色的权限", HttpStatus.FORBIDDEN);
        }

        try {
            // 验证角色名称是否重复
            if (this.isRoleRepeat(r_name, false)) {
                return new ResponseData(String.format("角色名称:%s 已存在", r_name), HttpStatus.FORBIDDEN);
            }
        } catch (SQLException e) {
            this.logger.error(String.format("角色权限新增时发生异常[r_Name: %s, remark: %s, mIds: %s]", r_name, remark, mIds), e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        }
        // 拼装角色信息
        Role role = new Role();
        role.setR_Name(r_name);
        role.setRemark(remark);

        boolean isCreate = false;
        try {
            isCreate = this.roleMaService.roleCreate(role, mIds);
        } catch (Exception e) {
            this.logger.debug(String.format("角色权限新增失败[role: %s, mIds: %s]", role, mIds));
        } finally {
           /* saveLog.saveSystemLog(SystemLogEnum.JSGL.getMenuCode(), SystemLogEnum.METHOD_SAVE);*/
        }
        if (isCreate) {
            return rtnResponse("true", "新增角色成功");
        }
        return rtnResponse("false", "新增角色失败");
    }

    /**
     * 角色管理—角色权限修改
     */
    @RequestMapping(value = "/role/{r_Id}", method = RequestMethod.PUT)
    public ResponseData roleEdit(
            @PathVariable(value = "r_Id") String r_Id,
            @RequestParam(required = false) String r_name,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String mIds
    ) throws SQLException {
        if (StringUtils.isEmpty(r_Id)) {
            return new ResponseData("角色ID不能为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(r_name)) {
            return new ResponseData("角色名称不能为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(mIds)) {
            return new ResponseData("请勾选角色的权限", HttpStatus.FORBIDDEN);
        }

        // 拼装角色信息
        Role role = new Role();
        role.setR_Id(r_Id);
        role.setR_Name(r_name);
        role.setRemark(remark);

        boolean isEdit = false;
        try {
            isEdit = this.roleMaService.roleEdit(role, mIds);
        } catch (Exception e) {
            this.logger.debug(String.format("修改角色信息失败[r_Id: %s]", r_Id));
        } finally {
            //saveLog.saveSystemLog(SystemLogEnum.JSGL.getMenuCode(), SystemLogEnum.METHOD_UPDATE);
        }
        if (isEdit) {
            return rtnResponse("true", "修改角色成功");
        }
        return rtnResponse("false", "修改角色失败");
    }

    /**
     * 角色管理—通过父级ID获取权限树
     */
    @RequestMapping(value = "/menulist", method = RequestMethod.POST)
    public ResponseData menuMessage(@RequestParam(required = false) String pId) {
        if (StringUtils.isEmpty(pId)) {
            pId = "0";
        }
        this.logger.debug(String.format("通过父级ID[pId: %s]获取权限树", pId));
        List<Map<String, Object>> treeMap = this.roleMaService.queryMenuBypId(pId);
        return new ResponseData(treeMap);
    }

    /**
     * 角色管理—通过父级ID及角色ID获取权限树
     */
    @RequestMapping(value = "/menulistByRid/{r_Id}", method = RequestMethod.POST)
    public ResponseData menuMessage(@RequestParam(required = false) String pId, @PathVariable(value = "r_Id") String r_Id) {
        if (StringUtils.isEmpty(r_Id)) {
            return rtnResponse("false", "角色ID不能为空");
        }
        if (StringUtils.isEmpty(pId)) {
            pId = "0";
        }
        this.logger.debug(String.format("通过父级ID及角色ID获取权限树[pId: %s, r_Id: %s]获取权限树", pId, r_Id));
        List<Map<String, Object>> treeMap = this.roleMaService.queryMenuBypIdrId(pId, r_Id);
        return new ResponseData(treeMap);
    }

    /**
     * 角色管理—角色权限名称重复校验
     */
    private boolean isRoleRepeat(String r_Name, boolean hasAdmin) throws SQLException {
        boolean isRepeat = this.roleMaService.isRoleRepeat(r_Name, hasAdmin);
        return isRepeat;
    }
    
}
