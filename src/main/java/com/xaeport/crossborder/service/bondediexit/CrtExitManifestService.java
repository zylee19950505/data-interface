package com.xaeport.crossborder.service.bondediexit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.configuration.SystemConstants;
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
    public List<BondInvtBsc> queryBondinvtIsRepeat(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryBondinvtIsRepeat(paramMap);
    }

    //获取出区核放单表头数据
    public PassPortHead queryPassPortHead(Map<String, String> paramMap) throws Exception {
        PassPortHead passPortHead = new PassPortHead();

        List<BondInvtBsc> bondInvtBscList = this.crtExitManifestMapper.queryBondInvtBscList(paramMap.get("bond_invt_no"));
        String BondInvtNo = paramMap.get("bond_invt_no").replaceAll(",", "/");
        passPortHead.setId(IdUtils.getUUId());
        passPortHead.setBusiness_type(SystemConstants.T_PASS_PORT);
        passPortHead.setAreain_oriact_no(bondInvtBscList.get(0).getPutrec_no());
        passPortHead.setMaster_cuscd(bondInvtBscList.get(0).getDcl_plc_cuscd());
        passPortHead.setEtps_preent_no(paramMap.get("etps_preent_no"));
        passPortHead.setBond_invt_no(BondInvtNo);
        passPortHead.setRlt_no(BondInvtNo);
        return passPortHead;
    }

    //获取出区核放单表体数据
    public List<PassPortAcmp> queryPassPortAcmpList(Map<String, String> paramMap) throws Exception {
        List<PassPortAcmp> passPortAcmpList = new ArrayList<>();
        PassPortAcmp passPortAcmp;
        String etpsPreentNo = paramMap.get("etps_preent_no");
        String bondInvtNo = paramMap.get("bond_invt_no");
        List<String> bondInvtNos = Arrays.asList(bondInvtNo.split(","));

        for (int i = 0; i < bondInvtNos.size(); i++) {
            passPortAcmp = new PassPortAcmp();
            passPortAcmp.setId(IdUtils.getUUId());
            passPortAcmp.setNo(i + 1);
            passPortAcmp.setRtl_tb_typecd("1");
            passPortAcmp.setRtl_no(bondInvtNos.get(i));
            passPortAcmp.setHead_etps_preent_no(etpsPreentNo);
            passPortAcmpList.add(passPortAcmp);
        }
        return passPortAcmpList;
    }

    //创建并预插入出区核放单数据（用户未保存确认前）
    public void insertPassHeadData(PassPortHead passPortHead, List<PassPortAcmp> passPortAcmpList, Users userInfo, String bond_invt_no) {
        this.crtExitManifestMapper.updateBondInvt(bond_invt_no);
        this.crtExitManifestMapper.insertPassPortHead(passPortHead, userInfo);

        for (PassPortAcmp passPortAcmp : passPortAcmpList) {
            this.crtExitManifestMapper.insertPassPortAcmp(passPortAcmp, userInfo);
        }
    }

    //保存更新出区核放单数据（用户保存确认后）
    public Map<String, String> saveExitManifest(LinkedHashMap<String, String> passPortHead, ArrayList<LinkedHashMap<String, String>> passPortAcmpList, Users userInfo) {
        Map<String, String> map = new HashMap<String, String>();

//        this.crtExitManifestMapper.updateBondInvtStatus(passPortHead);
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
