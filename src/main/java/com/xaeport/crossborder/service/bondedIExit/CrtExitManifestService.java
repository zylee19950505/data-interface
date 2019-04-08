package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.CrtExitManifestMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class CrtExitManifestService {

    @Autowired
    CrtExitManifestMapper crtExitManifestMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    //查询出区核注清单数据
    public List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryEInventoryList(paramMap);
    }

    //查询出区核注清单数据总数
    public Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryEInventoryCount(paramMap);
    }

    //查询出区核注清单数据数据是否重复
    public Integer queryBondinvtIsRepeat(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryBondinvtIsRepeat(paramMap);
    }

    //获取出区核放单表头数据
    public PassPortHead queryPassPortHead(Map<String, String> paramMap) throws Exception {
        PassPortHead passPortHead = new PassPortHead();

        BondInvtBsc bondInvtBsc = this.crtExitManifestMapper.queryBondInvtBsc(paramMap.get("bond_invt_no"));
        passPortHead.setId(IdUtils.getUUId());
        passPortHead.setAreain_oriact_no(bondInvtBsc.getPutrec_no());
//        passPortHead.setDcl_etpsno(bondInvtBsc.getDcl_etpsno());
//        passPortHead.setDcl_etps_nm(bondInvtBsc.getDcl_etps_nm());
        passPortHead.setMaster_cuscd(bondInvtBsc.getDcl_plc_cuscd());
//        passPortHead.setInput_code(paramMap.get("input_code"));
//        passPortHead.setInput_name(paramMap.get("input_name"));
        passPortHead.setEtps_preent_no(paramMap.get("etps_preent_no"));
        passPortHead.setBond_invt_no(paramMap.get("bond_invt_no"));
        passPortHead.setRlt_no(paramMap.get("bond_invt_no"));
        return passPortHead;
    }

    //获取出区核放单表体数据
    public List<PassPortAcmp> queryPassPortAcmpList(Map<String, String> paramMap) throws Exception {
        List<PassPortAcmp> passPortAcmpList = new ArrayList<>();
        PassPortAcmp passPortAcmp;
        passPortAcmp = new PassPortAcmp();
        passPortAcmp.setId(IdUtils.getUUId());
        passPortAcmp.setNo(1);
        passPortAcmp.setRtl_tb_typecd("");
        passPortAcmp.setRtl_no("");
        passPortAcmp.setHead_etps_preent_no(paramMap.get("etps_preent_no"));
        passPortAcmpList.add(passPortAcmp);
        return passPortAcmpList;
    }

    //创建并预插入出区核放单数据（用户未保存确认前）
    public void insertPassHeadData(PassPortHead passPortHead, List<PassPortAcmp> passPortAcmpList, Users userInfo) {
        this.crtExitManifestMapper.updateBondInvt(passPortHead);
        this.crtExitManifestMapper.insertPassPortHead(passPortHead, userInfo);

        for (PassPortAcmp passPortAcmp : passPortAcmpList) {
            this.crtExitManifestMapper.insertPassPortAcmp(passPortAcmp, userInfo);
        }
    }

    //保存更新出区核放单数据（用户保存确认后）
    public Map<String, String> saveExitManifest(LinkedHashMap<String, String> passPortHead, ArrayList<LinkedHashMap<String, String>> passPortAcmpList, Users userInfo) {
        Map<String, String> map = new HashMap<String, String>();

        this.crtExitManifestMapper.updateBondInvtStatus(passPortHead);
        this.crtExitManifestMapper.savePassPortHead(passPortHead, userInfo);

        if (!CollectionUtils.isEmpty(passPortAcmpList)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> passPortAcmp : passPortAcmpList) {
                if (!CollectionUtils.isEmpty(passPortAcmp)) {
                    this.crtExitManifestMapper.savePassPortAcmpList(passPortHead, passPortAcmp, userInfo);
                }
            }
        }
        map.put("result", "true");
        map.put("msg", "编辑成功，请到“出区核放单”处进行后续操作");
        return map;
    }

}
