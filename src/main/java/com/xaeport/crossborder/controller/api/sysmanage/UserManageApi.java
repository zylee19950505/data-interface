package com.xaeport.crossborder.controller.api.sysmanage;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.UserRole;
import com.xaeport.crossborder.data.entity.Users;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wx on 2018/4/27.
 */

@RestController
@RequestMapping("/api/sysmanage")
public class UserManageApi extends BaseApi{
    private Log logger = LogFactory.getLog(this.getClass());

   /* @Autowired
    private SaveLog saveLog;*/

    /**
     * 用户管理—用户列表查询及条件检索
     **/
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseData userList(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String ic,
            @RequestParam(required = false) String loginName,
            @RequestParam(required = false) String userType,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email

    ) {
        this.logger.debug(String.format("用户列表查询及条件检索条件[id: %s, ic: %s, loginName: %s, userType: %s, state: %s, phone: %s, email: %s]", id, ic, loginName, userType, state, phone, email));
        Map<String,String> paramMap = new HashMap<String,String>();
        List<Map<String, String>> userList = null;
        try {
            paramMap.put("id",id);
            paramMap.put("ic",ic);
            paramMap.put("loginName",loginName);
            paramMap.put("userType",userType);
            paramMap.put("state",state);
            paramMap.put("phone",phone);
            paramMap.put("email",email);
            userList = this.userMaService.userList(paramMap);
        } catch (Exception e) {
            this.logger.error(e,e);
            return new ResponseData("请求错误", HttpStatus.BAD_REQUEST);
        } finally {
           /* saveLog.saveSystemLog(SystemLogEnum.YHGL.getMenuCode(), SystemLogEnum.METHOD_SELECT);*/
        }
        return new ResponseData(userList);
    }


    /**
     * 用户管理—根据ID获取用户详细信息
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseData userDetail(@PathVariable(value = "id") String id) {
        if (StringUtils.isEmpty(id)) {
            return new ResponseData("用户ID不可为空", HttpStatus.FORBIDDEN);
        }
        this.logger.debug(String.format("根据ID[Id: %s]获取用户详细信息", id));
        Map<String, String> userDetail = this.userMaService.userDetail(id);
        return new ResponseData(userDetail);
    }

    /**
     * 用户管理—用户密码重置
     */
    @RequestMapping(value = "/reset", method = RequestMethod.PUT)
    public ResponseData userReset(
            @RequestParam String id,
            @RequestParam String password
    ) throws NoSuchAlgorithmException {
        if (StringUtils.isEmpty(id)) {
            return new ResponseData("用户ID获取失败", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(password)) {
            return new ResponseData("用户重置密码获取失败", HttpStatus.FORBIDDEN);
        }
        Users currentUsers = this.getCurrentUsers();
        boolean isChg = false;
        try {
            isChg = this.userMaService.userReset(currentUsers, id, password);
        } catch (Exception e) {
            this.logger.debug(String.format("用户密码重置失败[id: %s, password: %s]", id, password));
        } finally {
          /*  //保存日志
            saveLog.saveSystemLog(SystemLogEnum.YHGL.getMenuCode(), SystemLogEnum.METHOD_UPDATE);*/
        }
        if (isChg) {
            return rtnResponse("true", "密码重置成功");
        }
        return rtnResponse("false", "密码重置失败");
    }

    /**
     * 用户管理—用户删除
     **/
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseData userDelete(@PathVariable(value = "id") String id) {
        if (StringUtils.isEmpty(id)) {
            return new ResponseData("用户ID不可为空", HttpStatus.FORBIDDEN);
        }
        this.userMaService.userDelete(id);
       /* //保存日志
        saveLog.saveSystemLog(SystemLogEnum.YHGL.getMenuCode(), SystemLogEnum.METHOD_DELETE);*/
        return new ResponseData(1);
    }

    /**
     * 用户管理—用户新增
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseData userCreate(
            @RequestParam String id,
            @RequestParam String loginName,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam String ic,
            @RequestParam String userType,
            @RequestParam int state,
            @RequestParam String phone,
            @RequestParam String email
    ) throws NoSuchAlgorithmException, SQLException {
        if (StringUtils.isEmpty(id)) {
            return new ResponseData("登陆帐号不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(loginName)) {
            return new ResponseData("用户姓名不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(password)) {
            return new ResponseData("登录密码不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(state)) {
            return new ResponseData("用户状态不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(userType)) {
            return new ResponseData("用户类型不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(role)) {
            return new ResponseData("授予角色不可为空", HttpStatus.FORBIDDEN);
        }

        if (this.idRepeat(id)) {
            this.logger.debug(String.format("用户新增时发现用户信息已存在[id: %s]", id));
            return new ResponseData(String.format("帐号:%s 已存在", id), HttpStatus.FORBIDDEN);
        }

        Users currentUsers = this.getCurrentUsers();

        Users users = new Users();
        users.setId(id);
        users.setLoginName(loginName);
        users.setPassword(password);
        users.setIc(ic);
        users.setUserType(userType);
        users.setState(state);
        users.setPhone(phone);
        users.setEmail(email);
        users.setRoleId(role);

        UserRole userRole = new UserRole();
        userRole.setUserInfoId(id);
        userRole.setRoleId(role);


        boolean isCreate = false;
        try {
            isCreate = this.userMaService.userCreate(currentUsers, users,userRole);
        } catch (Exception e) {
            this.logger.debug(String.format("用户新增失败[id: %s]", id));
        }finally {
          /*  saveLog.saveSystemLog(SystemLogEnum.YHGL.getMenuCode(), SystemLogEnum.METHOD_SAVE);*/
        }
        if (isCreate) {
            return rtnResponse("true", "用户新增成功");
        }
        return rtnResponse("false", "用户新增失败");
    }

    /*
     * 用户管理—用户信息修改
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseData userEdit(
            @PathVariable(value = "id") String id,
            @RequestParam String loginName,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam String ic,
            @RequestParam String userType,
            @RequestParam int state,
            @RequestParam String phone,
            @RequestParam String email
    ) throws SQLException {
        if (StringUtils.isEmpty(id)) {
            return new ResponseData("登陆帐号不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(loginName)) {
            return new ResponseData("用户姓名不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(state)) {
            return new ResponseData("用户状态不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(userType)) {
            return new ResponseData("用户类型不可为空", HttpStatus.FORBIDDEN);
        }
        if (StringUtils.isEmpty(role)) {
            return new ResponseData("授予角色不可为空", HttpStatus.FORBIDDEN);
        }

        Users currentUsers = this.getCurrentUsers();

        Users users = new Users();
        users.setId(id);
        users.setLoginName(loginName);
        users.setPassword(password);
        users.setRoleId(role);
        users.setIc(ic);
        users.setUserType(userType);
        users.setState(state);
        users.setPhone(phone);
        users.setEmail(email);

        UserRole userRole = new UserRole();
        userRole.setUserInfoId(id);
        userRole.setRoleId(role);

        boolean isEdit = false;
        try {
            isEdit = this.userMaService.userEdit(currentUsers, users,userRole);
        } catch (Exception e) {
            this.logger.debug(String.format("用户修改失败[id: %s]", id));
        } finally {
       /*     saveLog.saveSystemLog(SystemLogEnum.YHGL.getMenuCode(), SystemLogEnum.METHOD_UPDATE);*/
        }
        if (isEdit) {
            return rtnResponse("true", "用户信息修改成功");
        }
        return rtnResponse("false", "用户信息修改失败");
    }

    /*
     * 用户管理—用户账号重复校验
     */
    private boolean idRepeat(String id) throws SQLException {
        boolean isRepeat = this.userMaService.idRepeat(id);
        return isRepeat;
    }

    /**
     * 用户管理—角色权限组select下拉框显示
     */
    @RequestMapping(value = "/roleSelect", method = RequestMethod.GET)
    public ResponseData roleSelectList() {
        List<Map<String, String>> roleList = this.userMaService.roleSelectList();
        return new ResponseData(roleList);
    }


}
