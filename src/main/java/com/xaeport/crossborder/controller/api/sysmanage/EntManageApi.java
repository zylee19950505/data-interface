package com.xaeport.crossborder.controller.api.sysmanage;

import com.xaeport.crossborder.controller.api.BaseApi;
import com.xaeport.crossborder.data.ResponseData;
import com.xaeport.crossborder.data.entity.DataList;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.mapper.EntManageMapper;
import com.xaeport.crossborder.service.sysmanage.EntManageSerivce;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wx on 2018/4/27.
 */

@RestController
@RequestMapping(value = "/entManage")
public class EntManageApi extends BaseApi {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    EntManageSerivce entManageSerivce;

    //企业所有信息数据
    @RequestMapping(value = "/allEntInfo", method = RequestMethod.GET)
    public ResponseData queryAllEntInfo(
            @RequestParam(required = false) String entInfo
    ) {
        this.logger.debug(String.format("企业列表查询及条件检索条件[entInfo: %s]", entInfo));

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("entInfo", entInfo);

        List<Enterprise> allEntInfo = this.entManageSerivce.queryAllEntInfo(paramMap);
        return new ResponseData(allEntInfo);

    }

    /**
     * 企业信息状态变更
     *
     * @param ent_Id 企业ID
     */
    @RequestMapping(value = "/editEnterprise/{ent_Id}", method = RequestMethod.GET)
    public ResponseData changeEntStatus(@PathVariable(value = "ent_Id") String ent_Id) {
        if (StringUtils.isEmpty(ent_Id)) {
            return new ResponseData("企业ID不可为空", HttpStatus.FORBIDDEN);
        }
        Map<String, String> patramMap = this.entManageSerivce.changeEntStatus(ent_Id);
        if ("true".equals(patramMap.get("result"))) {
            return rtnResponse("true", "企业信息状态变更成功");
        }
        return rtnResponse("false", "企业信息状态变更失败");
    }

    /**
     * 获取下拉菜单选项
     *
     * @param codeType 代码类型
     * @return 下拉菜单选项Map集合
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public ResponseData getCodeSelectOption(@RequestParam String codeType) {
        List<Map<String, String>> selectOptionList = this.entManageSerivce.getCodeSelectOption(codeType);
        if (CollectionUtils.isEmpty(selectOptionList)) {
            return rtnResponse("false", "获取下拉菜单选项失败");
        }
        return new ResponseData(selectOptionList);
    }

    /**
     * 企业新增
     *
     * @param enterprise 企业信息
     */
    @RequestMapping(value = "/createEntInfo", method = RequestMethod.POST)
    public ResponseData entCreate(
            @ModelAttribute Enterprise enterprise
    ) {
        ResponseData responseData = checkEnterpriseData(enterprise, false);
        if (responseData != null) {
            return responseData;
        }

        String entId = entManageSerivce.createEnterprise(enterprise);
        if (!StringUtils.isEmpty(entId)) {
            return rtnResponse("true", "企业新增成功");
        }

        return rtnResponse("false", "企业新增失败");
    }

    private ResponseData checkEnterpriseData(Enterprise enterprise, boolean isUpdate) {
        try {
            if (StringUtils.isEmpty(enterprise.getEnt_name())) {
                return rtnResponse("false", "企业名称不能为空！");
            }
            if (StringUtils.isEmpty(enterprise.getDxp_id())) {
                return rtnResponse("false", "企业DXPID不能为空！");
            }

            if (StringUtils.isEmpty(enterprise.getCustoms_code())) {
                return rtnResponse("false", "企业海关代码不能为空！");
            }

            if (StringUtils.isEmpty(enterprise.getBusiness_code())) {
                return rtnResponse("false", "工商营业执照号不能为空！");
            }

            if (StringUtils.isEmpty(enterprise.getOrg_code())) {
                return rtnResponse("false", "组织机构代码不能为空！");
            }

            if (StringUtils.isEmpty(enterprise.getTax_code())) {
                return rtnResponse("false", "税务登记代码不能为空！");
            }

            if (StringUtils.isEmpty(enterprise.getPort())) {
                return rtnResponse("false", "主管海关不能为空！");
            }

        } catch (Exception e) {
            logger.error(String.format("企业新增校验时发生异常导致失败[enterprise_name: %s]", enterprise.getEnt_name()), e);
            return rtnResponse("false", "企业新增校验时发生异常导致失败");
        }
        return null;
    }

    /**
     * 企业信息修改
     *
     * @param enterprise 企业信息
     */
    @RequestMapping(value = "/enterprise/{uId}", method = RequestMethod.PUT)
    public ResponseData entEdit(
            @ModelAttribute Enterprise enterprise
    ) {

        String id = enterprise.getId();
        if (StringUtils.isEmpty(id)) {
            return rtnResponse("false", "修改企业信息时企业信息ID不能为空");
        }

        ResponseData responseData = checkEnterpriseData(enterprise, true);
        if (responseData != null) {
            return responseData;
        }

        boolean updateFlag = entManageSerivce.updateEnterprise(enterprise);
        if (updateFlag) {
            return rtnResponse("true", "企业信息修改成功");
        }

        return rtnResponse("false", "企业信息修改失败");
    }

    /*
     * 企业信息获取
     */
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    public ResponseData loadEnterprise(@PathVariable(name = "id") String enterpriseId) {
        Enterprise enterprise = this.entManageSerivce.getEnterpriseDetail(enterpriseId);
        return new ResponseData(enterprise);
    }


}
