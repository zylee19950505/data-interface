package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.entity.PassPortAcmp;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
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

    //查询跨境清单数据
    public List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryEInventoryList(paramMap);
    }

    //查询跨境清单总数
    public Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryEInventoryCount(paramMap);
    }

    //查表头
    public PassPortHead queryPassPortHead(Map<String, String> paramMap) throws Exception {
        PassPortHead passPortHead = new PassPortHead();

        BondInvtBsc bondInvtBsc = this.crtExitManifestMapper.queryBondInvtBsc(paramMap.get("bond_invt_no"));
        passPortHead.setId(IdUtils.getUUId());
        passPortHead.setAreain_oriact_no(bondInvtBsc.getPutrec_no());
        passPortHead.setDcl_etpsno(bondInvtBsc.getDcl_etpsno());
        passPortHead.setDcl_etps_nm(bondInvtBsc.getDcl_etps_nm());
        passPortHead.setMaster_cuscd(bondInvtBsc.getDcl_plc_cuscd());
        passPortHead.setInput_code(paramMap.get("input_code"));
        passPortHead.setInput_name(paramMap.get("input_name"));
        passPortHead.setEtps_preent_no(paramMap.get("etps_preent_no"));
        passPortHead.setBond_invt_no(paramMap.get("bond_invt_no"));
        passPortHead.setRlt_no(paramMap.get("bond_invt_no"));
        return passPortHead;
    }

    //查表体
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
        map.put("msg", "编辑成功，请到“出区核注清单”处进行后续操作");
        return map;
    }

}
