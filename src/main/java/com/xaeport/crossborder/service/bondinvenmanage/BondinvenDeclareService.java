package com.xaeport.crossborder.service.bondinvenmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.inventory.BaseDetailDeclareXML;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BondinvenDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class BondinvenDeclareService {

    @Autowired
    BondinvenDeclareMapper bondinvenDeclareMapper;
    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BaseDetailDeclareXML baseDetailDeclareXML;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询保税清单数据
     */
    public List<ImpInventory> queryBondInvenDeclareList(Map<String, String> paramMap) throws Exception {
        return this.bondinvenDeclareMapper.queryBondInvenDeclareList(paramMap);
    }

    /*
     * 查询保税清单总数
     */
    public Integer queryBondInvenDeclareCount(Map<String, String> paramMap) throws Exception {
        return this.bondinvenDeclareMapper.queryBondInvenDeclareCount(paramMap);
    }

    /**
     * 更新保税清单状态
     */
    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.bondinvenDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理保税清单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    //根据唯一 Id 码查询清单详情
    public ImpInventoryDetail seeBondInvenDetail(String guid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", guid);
        ImpInventoryHead impInventoryHead = bondinvenDeclareMapper.queryImpBondInvenHead(paramMap);
        List<ImpInventoryBody> impInventoryBodies = bondinvenDeclareMapper.queryImpBondInvenBodies(paramMap);
//        Verify verify = bondinvenDeclareMapper.queryVerifyDetail(paramMap);
        ImpInventoryDetail impInventoryDetail = new ImpInventoryDetail();
        impInventoryDetail.setImpInventoryHead(impInventoryHead);
        impInventoryDetail.setImpInventoryBodies(impInventoryBodies);
//        impInventoryDetail.setVerify(verify);
        return impInventoryDetail;
    }


    //修改申报前保税清单数据
    @Transactional
    public Map<String, String> saveBondInvenBefore(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveBondInvenBeforeDetail(entryHead, entryLists, rtnMap, "保税清单申报-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“保税清单申报”处重新进行申报！");
        return rtnMap;

    }

    //更新修改申报前保税清单数据
    public boolean saveBondInvenBeforeDetail(
            LinkedHashMap<String, String> entryHead,
            List<LinkedHashMap<String, String>> entryLists,
            Map<String, String> rtnMap, String notes
    ) {
        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.bondinvenDeclareMapper.updateImpBondInvenHead(entryHead);
        }
        if (!CollectionUtils.isEmpty(entryLists)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> entryList : entryLists) {
                if (!CollectionUtils.isEmpty(entryList) && entryList.size() > 2) {
                    bondinvenDeclareMapper.updateImpBondInvenBodies(entryList);
                }
            }
            this.bondinvenDeclareMapper.updateImpBondInvenHeadByList(entryHead);
        }
        return false;
    }

}
