package com.xaeport.crossborder.verification.dataLoad.impl;

import com.xaeport.crossborder.data.mapper.VerificationMapper;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.dataLoad.OrderLoader;
import com.xaeport.crossborder.verification.entity.ImpCBBodyVer;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDataLoader implements OrderLoader {

    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public List<ImpCBHeadVer> loadingData() {
        // 加载至多1000条 状态为已导入且未校验过的数据
        List<ImpCBHeadVer> impCBHeadVers = this.verificationMapper.unverifiedByOrderHead();

        if (CollectionUtils.isEmpty(impCBHeadVers)) {
            return new ArrayList<ImpCBHeadVer>();
        }

        String headGuid;
        String headGuids = "";
        Map<String, ImpCBHeadVer> impCBHeadVerMap = new HashMap<String, ImpCBHeadVer>();// 便于处理将headGuid与对象绑定

        for (ImpCBHeadVer entryHeadVer : impCBHeadVers) {
            headGuid = entryHeadVer.getGuid();
            headGuids += ("," + headGuid);
            impCBHeadVerMap.put(headGuid, entryHeadVer);
        }
        // headGuid 使用逗号间隔字符串，便于批量查询
        headGuids = headGuids.substring(1);

        // 参数Map
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("headGuids",headGuids);

        // 根据headGuid获取所有分单信息
        List<ImpCBBodyVer> impCBBodyVers = verificationMapper.unverifiedByOrderBody(paramMap);

        if (CollectionUtils.isEmpty(impCBBodyVers)) {
            return impCBHeadVers;
        }

        List<ImpCBBodyVer> impCBBodyVerList = null;

        // 使用headGuid 将表体与表头做关联
        for (ImpCBBodyVer impCBBodyVer : impCBBodyVers) {

            headGuid = impCBBodyVer.getHead_guid();

            impCBBodyVerList = impCBHeadVerMap.get(headGuid).getImpCBBodyVerList();
            if(CollectionUtils.isEmpty(impCBBodyVerList)){
                impCBBodyVerList = new ArrayList<ImpCBBodyVer>();
                impCBHeadVerMap.get(headGuid).setImpCBBodyVerList(impCBBodyVerList);
            }

            impCBBodyVerList.add(impCBBodyVer);
        }
        return impCBHeadVers;
    }

}
