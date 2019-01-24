package com.xaeport.crossborder.service.bondedIEnter;


import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.PassPortList;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.EnterManifestMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnterManifestService {

    @Autowired
    EnterManifestMapper enterManifestMapper;


    public List<PassPortHead> queryEnterManifest(Map<String, String> paramMap) {
        return enterManifestMapper.queryEnterManifest(paramMap);
    }

    public Integer queryEnterManifestCount(Map<String, String> paramMap) {
        return enterManifestMapper.queryEnterManifestCount(paramMap);
    }

    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        return enterManifestMapper.updateSubmitCustom(paramMap);
    }

    public void deleteEnterManifest(String submitKeys, Users users) {
        //根据核放单号去查找(当前为核放单的ETPS_PREENT_NO,之后要改为PASSPORT_NO)
        String[] etps_preent_nos = submitKeys.split(",");
        Map<String,String> paramMap = new HashMap<String,String>();
        for (String etps_preent_no:etps_preent_nos) {
            paramMap.put("status", StatusCode.RQHFDDZC);
            paramMap.put("etps_preent_no",etps_preent_no);
            paramMap.put("userId",users.getId());
            PassPortHead passPortHead = new PassPortHead();
            passPortHead = this.enterManifestMapper.queryEnterManifestBindType(paramMap);
            paramMap.put("passport_no",passPortHead.getPassport_no());
            if (!"3".equals(passPortHead.getBind_typecd())){
                //一车一票和一车多票
                //恢复关联单里的核注清单的表体信息和可绑定信息

                //查找关联单里的核注清单信息
                String rtl_nos = this.enterManifestMapper.queryEnterPassportAcmp(paramMap);
                String rtl_no = rtl_nos.replaceAll("/", ",");
                //先恢复核注清单表头
                paramMap.put("rtl_no",rtl_no);
                this.enterManifestMapper.updateEnterBondInvtBsc(paramMap);
                //再恢复核注清单表体
                String[] etps_invt_nos = this.enterManifestMapper.queryEnterBondInvtDtID(paramMap);
                for (String etps_invt_no:etps_invt_nos) {
                    this.enterManifestMapper.updateEnterBondInvtDt(etps_invt_no);
                }
                //删除关联单信息
                this.enterManifestMapper.deleteEnterPassportAcmp(paramMap);

                //删除核放单
                this.enterManifestMapper.deleteEnterPassportHead(paramMap);

            }else{
            //一票多车
                //恢复表体信息里的商品信息里核注清单商品信息
                List<PassPortList> passPortLists  = new ArrayList<PassPortList>();
                passPortLists = this.enterManifestMapper.queryEnterPassportList(paramMap);
                int dcl_qty = 0;
                for (PassPortList passPortList:passPortLists) {
                    dcl_qty += passPortList.getDcl_qty();
                    //按每一个核注清单的商品类型,商品名称去恢复商品可绑定数目
                    this.enterManifestMapper.updateEnterBondInvtDtMoreCar(passPortList);
                }
                //恢复核注清单表头的可绑定数目
                    //查找该核放单的核注清单信息
                String bond_invt_no = this.enterManifestMapper.queryEnterBondInvtBsc(paramMap);

                paramMap.put("dcl_qty", String.valueOf(dcl_qty));
                paramMap.put("bond_invt_no",bond_invt_no);
                this.enterManifestMapper.updateEnterBondInvtBscMoreCar(paramMap);
                //删除核放单表体
                this.enterManifestMapper.deleteEnterPassportList(paramMap);

                //删除核放单
                this.enterManifestMapper.deleteEnterPassportHead(paramMap);

            }
        }
    }
}
