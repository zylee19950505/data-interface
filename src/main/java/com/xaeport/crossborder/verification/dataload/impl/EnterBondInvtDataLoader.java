package com.xaeport.crossborder.verification.dataload.impl;

import com.xaeport.crossborder.configuration.SystemConstants;
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

public class EnterBondInvtDataLoader implements BondLoader{
    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public List<ImpBDHeadVer> loadingData() {
        // 加载至多500条 状态为已导入且未校验过的数据
        List<ImpBDHeadVer> impBDHeadVers = this.verificationMapper.unverifiedByBondInvtHead(StatusCode.BSYDR, SystemConstants.BSRQ);

        if (CollectionUtils.isEmpty(impBDHeadVers)) {
            return new ArrayList<ImpBDHeadVer>();
        }
        String etps_inner_invt_no;
        String etpsInnerInvtNos = "";
        Map<String, ImpBDHeadVer> impBDHeadVerMap = new HashMap<String, ImpBDHeadVer>();// 便于处理将etps_inner_invt_no与对象绑定

        for (ImpBDHeadVer BDHeadVer : impBDHeadVers) {
            etps_inner_invt_no = BDHeadVer.getEtps_inner_invt_no();
            etpsInnerInvtNos += ("," + etps_inner_invt_no);
            impBDHeadVerMap.put(etps_inner_invt_no, BDHeadVer);
        }
        // etps_inner_invt_no 使用逗号间隔字符串，便于批量查询
        etpsInnerInvtNos = etpsInnerInvtNos.substring(1);
        // 参数Map
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("etpsInnerInvtNos",etpsInnerInvtNos);
        // 根据etps_inner_invt_no获取所有分单信息
        List<ImpBDBodyVer> impBDBodyVers = verificationMapper.unverifiedByBondInvtBody(paramMap);
        if (CollectionUtils.isEmpty(impBDBodyVers)) {
            return impBDHeadVers;
        }
        List<ImpBDBodyVer> impBDBodyVerList = null;
        // etps_inner_invt_no 将表体与表头做关联
        for (ImpBDBodyVer impBDBodyVer : impBDBodyVers) {
            etps_inner_invt_no = impBDBodyVer.getHead_etps_inner_invt_no();
            impBDBodyVerList = impBDHeadVerMap.get(etps_inner_invt_no).getImpBDBodyVerList();
            if(CollectionUtils.isEmpty(impBDBodyVerList)){
                impBDBodyVerList = new ArrayList<ImpBDBodyVer>();
                impBDHeadVerMap.get(etps_inner_invt_no).setImpBDBodyVerList(impBDBodyVerList);
            }
            impBDBodyVerList.add(impBDBodyVer);
        }
        return impBDHeadVers;
    }

}
