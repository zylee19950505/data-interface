package com.xaeport.crossborder.service.sysmanage;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.SysLog;
import com.xaeport.crossborder.data.mapper.EntManageMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wx on 2018/4/27.
 */
@Service
public class EntManageSerivce {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    EntManageMapper entManageMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    //企业管理——企业所有信息数据
    public List<Enterprise> queryAllEntInfo(Map<String, String> paramMap) {
        return this.entManageMapper.queryAllEntInfo(paramMap);
    }

    //企业管理——企业状态更变1
    public Map<String, String> changeEntStatus(String ent_Id) {
        Map<String, String> rtnMap = new HashMap<String, String>();

        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(ent_Id);
        if (enterprise == null) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "企业信息不存在无法变更状态！");
            return rtnMap;
        }

        if ("1".equals(enterprise.getStatus())) {
            enterprise.setStatus("0");
        } else {
            enterprise.setStatus("1");
        }

        boolean updateFlag = this.changeEntStatus(enterprise);
        if (!updateFlag) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "企业信息变更状态失败！");
            return rtnMap;
        }

        rtnMap.put("result", "true");
        rtnMap.put("msg", "企业信息变更状态成功");
        return rtnMap;
    }

    //企业管理——企业状态更变2
    public boolean changeEntStatus(Enterprise enterprise) {
        boolean flag;
        int count = this.entManageMapper.getEnterpriseCount(enterprise.getId());
        if (count < 1) {
            return false;
        }
        try {
            flag = this.entManageMapper.updateEnterprise(enterprise);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("更新企业信息时发生异常[ID:%s]", enterprise.getId()), e);
        }
        return flag;
    }


    /**
     * 获取下拉菜单选项
     *
     * @param codeType 代码类型
     * @return 下拉菜单选项Map集合
     */
    public List<Map<String, String>> getCodeSelectOption(String codeType) {
        List<Map<String, String>> selectOptionList = null;
        switch (codeType) {
            case SystemConstants.CODE_TYPE_AGENT_CODE: {
                selectOptionList = entManageMapper.getAgentCode();
            }
            ;
            break;
            case SystemConstants.CODE_TYPE_CUSTOMS_CODE: {
                selectOptionList = entManageMapper.getCustomsCode();
            }
            ;
            break;
            case SystemConstants.CODE_TYPE_AGENT_TYPE: {
                selectOptionList = entManageMapper.getAgentType();
            }
            ;
            break;
            case SystemConstants.CODE_TYPE_AGENT_NATURE: {
                selectOptionList = entManageMapper.getAgentNature();
            }
            ;
            break;
            default:
                selectOptionList = new ArrayList<Map<String, String>>();
        }
        return selectOptionList;
    }

    public String createEnterprise(Enterprise enterprise) {
        boolean flag;
        String id = IdUtils.getUUId();
        enterprise.setId(id);
        enterprise.setCrt_tm(new Date());
        enterprise.setUpd_tm(new Date());
        enterprise.setStatus("1");
        try {
            flag = this.entManageMapper.createEntInfo(enterprise);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("创建企业信息时发生异常[ent_name: %s]", enterprise.getEnt_name()), e);
        }
        if (flag) {
            return id;
        }
        return "";
    }

    public boolean updateEnterprise(Enterprise enterprise) {
        boolean flag;
        int count = this.entManageMapper.getEnterpriseCount(enterprise.getId());
        if (count < 1) {
            return false;
        }
        try {
            flag = this.entManageMapper.updateEnterprise(enterprise);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("更新企业信息时发生异常[id:%s]", enterprise.getId()), e);
        }
        return flag;
    }

    public Enterprise getEnterpriseDetail(String entId){
        return this.entManageMapper.getEnterpriseDetail(entId);
    }






}
