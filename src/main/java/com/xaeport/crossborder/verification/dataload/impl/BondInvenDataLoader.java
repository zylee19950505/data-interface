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

public class BondInvenDataLoader implements BondLoader {


    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public List<ImpBDHeadVer> loadingData() {
        // 加载至多500条 状态为已导入且未校验过的数据
        List<ImpBDHeadVer> ImpBDHeadVers = this.verificationMapper.unverifiedByBondInvenHead(StatusCode.BSYDR);

        if (CollectionUtils.isEmpty(ImpBDHeadVers)) {
            return new ArrayList<ImpBDHeadVer>();
        }

        String headGuid;
        String headGuids = "";
        Map<String, ImpBDHeadVer> ImpBDHeadVerMap = new HashMap<String, ImpBDHeadVer>();// 便于处理将headGuid与对象绑定

        for (ImpBDHeadVer entryHeadVer : ImpBDHeadVers) {
            headGuid = entryHeadVer.getGuid();
            headGuids += ("," + headGuid);
            ImpBDHeadVerMap.put(headGuid, entryHeadVer);
        }
        // headGuid 使用逗号间隔字符串，便于批量查询
        headGuids = headGuids.substring(1);

        // 参数Map
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("headGuids",headGuids);

        // 根据headGuid获取所有分单信息
        List<ImpBDBodyVer> ImpBDBodyVers = verificationMapper.unverifiedByBondInvenBody(paramMap);

        if (CollectionUtils.isEmpty(ImpBDBodyVers)) {
            return ImpBDHeadVers;
        }

        List<ImpBDBodyVer> ImpBDBodyVerList = null;

        // 使用headGuid 将表体与表头做关联
        for (ImpBDBodyVer impBDBodyVer : ImpBDBodyVers) {

            headGuid = impBDBodyVer.getHead_guid();

            ImpBDBodyVerList = ImpBDHeadVerMap.get(headGuid).getImpBDBodyVerList();
            if(CollectionUtils.isEmpty(ImpBDBodyVerList)){
                ImpBDBodyVerList = new ArrayList<ImpBDBodyVer>();
                ImpBDHeadVerMap.get(headGuid).setImpBDBodyVerList(ImpBDBodyVerList);
            }

            ImpBDBodyVerList.add(impBDBodyVer);
        }
        return ImpBDHeadVers;
    }


}
