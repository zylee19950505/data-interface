package com.xaeport.crossborder.verification.dataload.impl;

import com.xaeport.crossborder.data.mapper.VerificationMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.dataload.BondLoader;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PassPortDataLoader implements BondLoader {

    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public List<ImpBDHeadVer> loadingData() {
        // 加载至多500条 状态为已导入且未校验过的数据
        List<ImpBDHeadVer> impBDHeadVers = this.verificationMapper.unverifiedByPassPort(StatusCode.BSYDR);

        if (CollectionUtils.isEmpty(impBDHeadVers)) {
            return new ArrayList<ImpBDHeadVer>();
        }
        return impBDHeadVers;
    }

}
