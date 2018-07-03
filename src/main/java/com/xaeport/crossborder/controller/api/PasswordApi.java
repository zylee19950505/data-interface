package com.xaeport.crossborder.controller.api;

import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 密码API
 * Created by xcp on 2018/5/14.
 */
@RestController
public class PasswordApi extends BaseApi {

	private Log log = LogFactory.getLog(this.getClass());
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
	@Autowired
	UserService userService;

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public ResponseData modifyPassword(@RequestParam(value = "userId") String userId,
									   @RequestParam(name = "old_password") String oldPassword,
									   @RequestParam(name = "new_password") String newPassword) {
		boolean result;
		String msg;
		Map<String, Object> map = new HashMap<>();
		if ("visitor".equals(userId)){
			map.put("result", false);
			map.put("msg", "体验账号不允许修改密码");
			return new ResponseData(map);
		}
		Users currentUsers;
		String loginName = null;
		try {
			currentUsers = this.getCurrentUsers();
			loginName = currentUsers.getLoginName();
			String rawId = currentUsers.getId();
			this.log.debug("修改密码：loginName=" + loginName + ";rawId=" + rawId);
			Users users = this.userService.authUserLogin(String.valueOf(rawId),loginName);
			if (null != users) {
				String historyPassword = users.getPassword();
				boolean match = passwordEncoder.matches(oldPassword, historyPassword);
				String bCryptNewPassword = passwordEncoder.encode(newPassword);//新密码 BCrypt
				if (match) {
					this.log.debug("userId：" + userId + " 帐号：" + loginName + "密码正确，可以修改密码");
					//修改密码
					this.userService.changePassword(bCryptNewPassword, new Date(), userId);
					result = true;
					msg = "修改密码成功";
					this.log.debug("userId：" + userId + " 帐号：" + loginName + "修改密码成功");
				} else {
					// 原始密码错误
					result = false;
					msg = "原始密码错误";
					this.log.debug("userId：" + userId + " 帐号：" + loginName + "原始密码:" + oldPassword + " 错误，不可以修改密码");
				}
			} else {
				// 原始密码错误
				result = false;
				msg = "用户身份错误";
				this.log.debug("userId：" + userId + " 帐号：" + loginName + "用户身份错误，不可以修改密码");
			}
			map.put("result", result);
			map.put("msg", msg);
			return new ResponseData(map);
		} catch (Exception e) {
			this.log.error("userId：" + userId + " 帐号：" + loginName + "修改密码异常", e);
			map.put("result", false);
			map.put("msg", "修改密码异常错误");
			return new ResponseData(map);
		}
	}


}
