package com.xaeport.crossborder.service.sysmanage;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.mapper.BooksManageMapper;
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
public class BooksManageSerivce {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    BooksManageMapper booksManageMapper;

    //企业管理——企业所有信息数据
    public List<BwlHeadType> queryAllBooksInfo(Map<String, String> paramMap) throws Exception {
        return this.booksManageMapper.queryAllBooksInfo(paramMap);
    }

//    //企业管理——企业状态更变1
//    public Map<String, String> changeEntStatus(String ent_Id) {
//        Map<String, String> rtnMap = new HashMap<String, String>();
//
//        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(ent_Id);
//        if (enterprise == null) {
//            rtnMap.put("result", "false");
//            rtnMap.put("msg", "企业信息不存在无法变更状态！");
//            return rtnMap;
//        }
//
//        if ("1".equals(enterprise.getStatus())) {
//            enterprise.setStatus("0");
//        } else {
//            enterprise.setStatus("1");
//        }
//
//        boolean updateFlag = this.changeEntStatus(enterprise);
//        if (!updateFlag) {
//            rtnMap.put("result", "false");
//            rtnMap.put("msg", "企业信息变更状态失败！");
//            return rtnMap;
//        }
//
//        rtnMap.put("result", "true");
//        rtnMap.put("msg", "企业信息变更状态成功");
//        return rtnMap;
//    }

    //    //企业管理——企业状态更变2
//    public boolean changeEntStatus(Enterprise enterprise) {
//        boolean flag;
//        int count = this.enterpriseMapper.getEnterpriseCount(enterprise.getId());
//        if (count < 1) {
//            return false;
//        }
//        try {
//            flag = this.enterpriseMapper.updateEnterprise(enterprise);
//        } catch (Exception e) {
//            flag = false;
//            this.logger.error(String.format("更新企业信息时发生异常[ID:%s]", enterprise.getId()), e);
//        }
//        return flag;
//    }
//
//
//    /**
//     * 获取下拉菜单选项
//     *
//     * @param codeType 代码类型
//     * @return 下拉菜单选项Map集合
//     */
//    public List<Map<String, String>> getCodeSelectOption(String codeType) {
//        List<Map<String, String>> selectOptionList = null;
//        switch (codeType) {
//            case SystemConstants.CODE_TYPE_AGENT_CODE: {
//                selectOptionList = enterpriseMapper.getAgentCode();
//            }
//            ;
//            break;
//            case SystemConstants.CODE_TYPE_CUSTOMS_CODE: {
//                selectOptionList = enterpriseMapper.getCustomsCode();
//            }
//            ;
//            break;
//            case SystemConstants.CODE_TYPE_AGENT_TYPE: {
//                selectOptionList = enterpriseMapper.getAgentType();
//            }
//            ;
//            break;
//            case SystemConstants.CODE_TYPE_AGENT_NATURE: {
//                selectOptionList = enterpriseMapper.getAgentNature();
//            }
//            ;
//            break;
//            default:
//                selectOptionList = new ArrayList<Map<String, String>>();
//        }
//        return selectOptionList;
//    }
//
    public String crtBooksInfo(BwlHeadType bwlHeadType) {
        boolean flag;
        String id = IdUtils.getUUId();
        bwlHeadType.setId(id);
        bwlHeadType.setCrt_time(new Date());
        bwlHeadType.setUpd_time(new Date());
        bwlHeadType.setInput_date(new Date());
        try {
            flag = this.booksManageMapper.crtBooksInfo(bwlHeadType);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("创建账册信息时发生异常[Dcl_etps_nm: %s]", bwlHeadType.getDcl_etps_nm()), e);
        }
        if (flag) {
            return id;
        }
        return "";
    }

    public boolean updateBooks(BwlHeadType bwlHeadType) {
        boolean flag;
        int count = this.booksManageMapper.getBooksCount(bwlHeadType.getId());
        if (count < 1) {
            return false;
        }
        try {
            flag = this.booksManageMapper.updateBooks(bwlHeadType);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("更新账册信息时发生异常[id:%s]", bwlHeadType.getId()), e);
        }
        return flag;
    }

    public BwlHeadType getBooksById(String id) {
        return this.booksManageMapper.getBooksById(id);
    }


}
