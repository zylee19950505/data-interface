package com.xaeport.crossborder.verification.dataload.impl;

import com.xaeport.crossborder.data.mapper.VerificationMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.dataload.BondLoader;
import com.xaeport.crossborder.verification.entity.ImpBDBodyVer;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BondOrderDataLoader implements BondLoader {
    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public List<ImpBDHeadVer> loadingData() {
        // 加载至多500条 状态为已导入且未校验过的数据
        List<ImpBDHeadVer> impBDHeadVers = this.verificationMapper.unverifiedByBondOrderHead(StatusCode.BSYDR);

        if (CollectionUtils.isEmpty(impBDHeadVers)) {
            return new ArrayList<ImpBDHeadVer>();
        }
        String headGuid;
        String headGuids = "";
        Map<String, ImpBDHeadVer> impBDHeadVerMap = new HashMap<String, ImpBDHeadVer>();// 便于处理将headGuid与对象绑定

        for (ImpBDHeadVer BDHeadVer : impBDHeadVers) {
            headGuid = BDHeadVer.getGuid();
            headGuids += ("," + headGuid);
            impBDHeadVerMap.put(headGuid, BDHeadVer);
        }
        // headGuid 使用逗号间隔字符串，便于批量查询
        headGuids = headGuids.substring(1);
        // 参数Map
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("headGuids",headGuids);
        // 根据headGuid获取所有分单信息
        List<ImpBDBodyVer> impBDBodyVers = verificationMapper.unverifiedByBondOrderBody(paramMap);
        if (CollectionUtils.isEmpty(impBDBodyVers)) {
            return impBDHeadVers;
        }
        List<ImpBDBodyVer> impBDBodyVerList = null;
        // 使用headGuid 将表体与表头做关联
        for (ImpBDBodyVer impBDBodyVer : impBDBodyVers) {
            headGuid = impBDBodyVer.getHead_guid();
            impBDBodyVerList = impBDHeadVerMap.get(headGuid).getImpBDBodyVerList();
            if(CollectionUtils.isEmpty(impBDBodyVerList)){
                impBDBodyVerList = new ArrayList<ImpBDBodyVer>();
                impBDHeadVerMap.get(headGuid).setImpBDBodyVerList(impBDBodyVerList);
            }
            impBDBodyVerList.add(impBDBodyVer);
        }
        return impBDHeadVers;
    }

}
