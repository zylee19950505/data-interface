package com.xaeport.crossborder.verification.dataload.impl;

import com.xaeport.crossborder.data.mapper.VerificationMapper;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.dataload.CrossBorderLoader;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PaymentDataloader implements CrossBorderLoader {

    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public List<ImpCBHeadVer> loadingData() {
        // 加载至多500条 状态为已导入且未校验过的数据
        List<ImpCBHeadVer> impCBHeadVers = this.verificationMapper.unverifiedByPayment();

        if (CollectionUtils.isEmpty(impCBHeadVers)) {
            return new ArrayList<ImpCBHeadVer>();
        }

        return impCBHeadVers;
    }

}
