package com.xaeport.crossborder.controller.api.infomanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.service.UserService;
import com.xaeport.crossborder.tools.DownloadUtils;
import com.xaeport.crossborder.tools.Transcoding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2019-01-10.
 * 用户Api
 */
@RestController
public class UserApi extends BaseApi {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    protected UserService userService;

    @Autowired
    AppConfiguration appConfiguration;

    /**
     * 检查用户是否已绑定IC卡
     */
    @RequestMapping(value = "/checkUserIc", method = RequestMethod.GET)
    public ResponseData checkUserIc() {
        Users currentUsers = this.getCurrentUsers();
        if (currentUsers == null) {
            this.log.debug("检查用户是否已绑定IC卡时,获取当前登录用户失败");
            return rtnResponse("false", "获取当前登录用户失败");
        }
        boolean isBand = this.userService.checkUserIc(currentUsers);
        if (isBand) {
            return rtnResponse("true", "");
        }
        this.log.debug(String.format("检查用户是否已绑定IC卡时,用户[%s]未绑定IC卡", currentUsers.getId()));
        return rtnResponse("false", "未绑定IC卡");
    }

    /**
     * 绑定IC卡卡号到当前用户
     */
    @RequestMapping(value = "/saveBandIc", method = RequestMethod.PUT)
    public ResponseData saveBandIc(@RequestParam(required = false) String icCardNo,
                                   @RequestParam(required = false) String icCardPwd) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (StringUtils.isEmpty(icCardNo)) {
            return rtnResponse("false", "IC卡号不能为空");
        }
        if (StringUtils.isEmpty(icCardPwd)) {
            return rtnResponse("false", "IC卡密码不能为空");
        }
        Users currentUsers = this.getCurrentUsers();
        if (currentUsers == null) {
            this.log.debug("检查用户是否已绑定IC卡时,获取当前登录用户失败");
            return rtnResponse("false", "获取当前登录用户失败");
        }
        boolean isSaved = this.userService.saveBandIc(currentUsers, icCardNo, icCardPwd);
        if (isSaved) {
            return rtnResponse("true", "绑定IC卡成功");
        }
        this.log.debug(String.format("绑定IC卡卡号到当前用户时,用户[%s]绑定IC卡失败", currentUsers.getId()));
        return rtnResponse("false", "绑定IC卡失败");

    }

    /**
     * 转换IC卡号
     *
     * @param icCardNo IC卡号
     */
    @RequestMapping(value = "/convertIcNo", method = RequestMethod.GET)
    public ResponseData convertIcNo(@RequestParam(required = false) String icCardNo) {
        if (StringUtils.isEmpty(icCardNo)) {
            return rtnResponse("false", "IC卡号不能为空");
        }
        try {
            String converageAfter = Transcoding.Crypto(icCardNo, true);
            return rtnResponse("true", converageAfter);
        } catch (Exception e) {
            this.log.error(String.format("转化IC卡号%s时发生异常", icCardNo), e);
        }
        this.log.debug(String.format("IC卡号[%s]转换失败", icCardNo));
        return rtnResponse("false", "IC卡号转换失败");
    }

    /**
     * 转换IC卡密码
     *
     * @param pwd IC卡密码
     */
    @RequestMapping(value = "/convertPwd", method = RequestMethod.GET)
    public ResponseData convertPwd(@RequestParam(required = false) String pwd) {
        if (StringUtils.isEmpty(pwd)) {
            return rtnResponse("false", "IC卡密码不能为空");
        }
        try {
            String converageAfter = Transcoding.Crypto(pwd);
            return rtnResponse("true", converageAfter);
        } catch (Exception e) {
            this.log.error(String.format("转化IC卡密码[%s]时发生异常", pwd), e);
        }
        this.log.debug(String.format("IC卡密码[%s]转换失败", pwd));
        return rtnResponse("false", "IC卡密码转换失败");
    }

    /**
     * IC卡绑卡操作记录查询
     */
    @RequestMapping(value = "/bandOprHis", method = RequestMethod.GET)
    public ResponseData getBandOprHis() {
        // 获取当前用户
        Users currentUsers = this.getCurrentUsers();
        if (currentUsers == null) {
            this.log.debug("IC卡绑卡操作记录查询时,获取当前登录用户失败");
            return rtnResponse("false", "获取当前登录用户失败");
        }
        String userId = currentUsers.getId();
        Map<String, String> paramMap = new HashMap<String, String>();
        // 查询参数
        paramMap.put("userId", userId);
        List<Map<String, String>> bandOprHisList = this.userService.getBandOprHis(paramMap);
        return new ResponseData(bandOprHisList);
    }

    /**
     * 获取用户IC卡信息
     */
    @RequestMapping(value = "/userIc", method = RequestMethod.GET)
    public ResponseData getUserIcCardNo() {
        Users securityUsers = this.getCurrentUsers();
        if (securityUsers == null) {
            this.log.debug("获取用户IC卡信息时,获取当前登录用户失败");
            return rtnResponse("false", "获取当前登录用户失败");
        }
        String icCardNo = this.userService.getUserIcCardNo(securityUsers);
        if (!StringUtils.isEmpty(icCardNo)) {
            return rtnResponse("true", icCardNo);
        }
        this.log.debug(String.format("获取用户[%s]IC卡信息失败", securityUsers.getLoginName()));
        return rtnResponse("false", "获取IC卡信息失败");
    }


    /**
     * 驱动程序下载
     */
    @RequestMapping(value = "/downloadDrivers")
    public void excelModelDownload(HttpServletRequest request, HttpServletResponse response) {
        String filePath = this.appConfiguration.getAgentExeFolder();
        this.log.debug(String.format("开始下载文件：%s", filePath));
        File file = new File(filePath);
        DownloadUtils.download(response, file, SystemConstants.HTTP_CONTENT_TYPE_EXE);
        this.log.debug(String.format("完成下载文件：%s", filePath));
    }
}

