package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.CrtExitInventoryMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CrtExitInventoryService {

    @Autowired
    CrtExitInventoryMapper crtExitInventoryMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    //查询跨境清单数据
    public List<ImpInventory> queryCrtEInventoryList(Map<String, String> paramMap) throws Exception {
        return this.crtExitInventoryMapper.queryCrtEInventoryList(paramMap);
    }

    //查询跨境清单总数
    public Integer queryCrtEInventoryCount(Map<String, String> paramMap) throws Exception {
        return this.crtExitInventoryMapper.queryCrtEInventoryCount(paramMap);
    }

    //查表头
    public BondInvtBsc queryBondInvtBsc(Map<String, String> paramMap) throws Exception {
        BondInvtBsc bondInvtBsc = new BondInvtBsc();
        bondInvtBsc.setBizop_etpsno(paramMap.get("bizop_etpsno"));
        bondInvtBsc.setBizop_etps_nm(paramMap.get("bizop_etps_nm"));
        bondInvtBsc.setDcl_etpsno(paramMap.get("dcl_etpsno"));
        bondInvtBsc.setDcl_etps_nm(paramMap.get("dcl_etps_nm"));
        bondInvtBsc.setPutrec_no(this.crtExitInventoryMapper.queryBws_no(paramMap.get("ent_id")));
        return bondInvtBsc;
    }

    //查表体
    public List<BondInvtDt> queryBondInvtDtList(Map<String, String> paramMap) throws Exception {
        List<String> list = this.crtExitInventoryMapper.queryGuids(paramMap.get("invtNo"));
        String dataList = list.stream().collect(Collectors.joining(","));
        List<ImpInventoryBody> impInventoryBodyList = this.crtExitInventoryMapper.queryImpInventoryBodyList(dataList);
        List<BondInvtDt> bondInvtDtList = new ArrayList<>();
        BondInvtDt bondInvtDt;
        for (int i = 0; i < impInventoryBodyList.size(); i++) {
            bondInvtDt = new BondInvtDt();
            bondInvtDt.setId(IdUtils.getUUId());
            bondInvtDt.setGds_seqno(i + 1);
            bondInvtDt.setPutrec_seqno(i + 1);
            bondInvtDt.setGds_mtno("456");
            bondInvtDt.setGdecd(impInventoryBodyList.get(i).getG_code());
            bondInvtDt.setGds_nm(impInventoryBodyList.get(i).getG_name());
            bondInvtDt.setDcl_unitcd(impInventoryBodyList.get(i).getUnit());
            bondInvtDt.setDcl_qty(impInventoryBodyList.get(i).getQty());
            bondInvtDt.setDcl_uprc_amt(impInventoryBodyList.get(i).getPrice());
            bondInvtDt.setDcl_total_amt(impInventoryBodyList.get(i).getTotal_price());
            bondInvtDt.setDcl_currcd(impInventoryBodyList.get(i).getCurrency());
            bondInvtDt.setGds_spcf_model_desc(impInventoryBodyList.get(i).getG_model());
            bondInvtDt.setUsd_stat_total_amt("6.8962");
            bondInvtDtList.add(bondInvtDt);
        }
        return bondInvtDtList;
    }

    public Map<String, String> saveBondInvt(LinkedHashMap<String, String> BondInvtBsc,ArrayList<LinkedHashMap<String, String>> BondInvtDtList){
        Map<String, String> map = new HashMap<String, String>();
        this.crtExitInventoryMapper.saveBondInvtBsc(BondInvtBsc);

        if (!CollectionUtils.isEmpty(BondInvtDtList)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> BondInvtDt : BondInvtDtList) {
                if (!CollectionUtils.isEmpty(BondInvtDt)) {
                    this.crtExitInventoryMapper.saveBondInvtDt(BondInvtDt);
                }
            }
        }
        map.put("result", "true");
        map.put("msg", "编辑成功，请到“出区核注清单”处进行后续操作");
        return map;
    }


}
