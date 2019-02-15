package com.xaeport.crossborder.service.bondinvenmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BondinvenQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class BondinvenQueryService {

    @Autowired
    BondinvenQueryMapper bondinvenQueryMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询保税清单数据
     */
    public List<ImpInventory> queryBondInvenQueryData(Map<String, String> paramMap) throws Exception {
        return this.bondinvenQueryMapper.queryBondInvenQueryData(paramMap);
    }

    /*
     * 查询保税清单总数
     */
    public Integer queryBondInvenQueryCount(Map<String, String> paramMap) throws Exception {
        return this.bondinvenQueryMapper.queryBondInvenQueryCount(paramMap);
    }

//    //根据唯一 Id 码查询清单详情
//    public ImpInventoryDetail getImpInventoryDetail(String guid) {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("id", guid);
//        ImpInventoryHead impInventoryHead = bondinvenQueryMapper.queryImpInventoryHead(paramMap);
//        List<ImpInventoryBody> impInventoryBodies = bondinvenQueryMapper.queryImpInventoryBodies(paramMap);
//        Verify verify = bondinvenQueryMapper.queryVerifyDetail(paramMap);
//        ImpInventoryDetail impInventoryDetail = new ImpInventoryDetail();
//        impInventoryDetail.setImpInventoryHead(impInventoryHead);
//        impInventoryDetail.setImpInventoryBodies(impInventoryBodies);
//        impInventoryDetail.setVerify(verify);
//        return impInventoryDetail;
//    }

    //查询保税清单回执数据
    public ImpInventoryHead getImpBondInvenRec(String guid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", guid);
        ImpInventoryHead impInventoryHead = bondinvenQueryMapper.getImpBondInvenRec(paramMap);
        return impInventoryHead;
    }

    //修改报税清单数据
    @Transactional
    public Map<String, String> saveBondInvenAfter(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveBondInvenAfterDetail(entryHead, entryLists, rtnMap, "保税清单查询-编辑-重报")) return rtnMap;
        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“保税清单申报”处重新进行申报！");
        return rtnMap;

    }

    //更新修改保税清单数据
    public boolean saveBondInvenAfterDetail(LinkedHashMap<String, String> entryHead,
                                            List<LinkedHashMap<String, String>> entryLists,
                                            Map<String, String> rtnMap, String notes) {
        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.bondinvenQueryMapper.updateImpBondInvenHeadAfter(entryHead);
        }
        if (!CollectionUtils.isEmpty(entryLists)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> entryList : entryLists) {
                if (!CollectionUtils.isEmpty(entryList) && entryList.size() > 2) {
                    bondinvenQueryMapper.updateImpBondInvenBodyAfter(entryList);
                }
            }
            this.bondinvenQueryMapper.updateImpBondInvenHeadByList(entryHead);
        }
        return false;
    }

}
