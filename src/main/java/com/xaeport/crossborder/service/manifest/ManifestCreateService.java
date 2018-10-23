package com.xaeport.crossborder.service.manifest;

import com.xaeport.crossborder.data.entity.CheckGoodsInfo;
import com.xaeport.crossborder.data.entity.ManifestData;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.mapper.ManifestCreateMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ManifestCreateService {

    @Autowired
    ManifestCreateMapper manifestCreateMapper;

    public List<CheckGoodsInfo> queryCheckGoodsInfoList(Map<String, String> paramMap) throws Exception {
        return this.manifestCreateMapper.queryCheckGoodsInfoList(paramMap);
    }


    public ManifestData queryManifestData(Map<String, String> paramMap) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

        ManifestHead manifestHead = new ManifestHead();
        ManifestData manifestData = new ManifestData();
        String totalLogisticsNo = paramMap.get("totalLogisticsNo");

        CheckGoodsInfo checkGoodsInfo = null;
        List<CheckGoodsInfo> checkGoodsInfoList = null;
        if (!StringUtils.isEmpty(totalLogisticsNo)) {
            checkGoodsInfo = this.manifestCreateMapper.queryManifestSum(paramMap);
            checkGoodsInfoList = this.manifestCreateMapper.queryCheckGoodsList(paramMap);
            manifestHead.setGoods_wt(checkGoodsInfo.getGrossWtSum());
            manifestHead.setFact_weight(checkGoodsInfo.getNetWtSum());
            manifestHead.setPack_no(checkGoodsInfo.getReleasePackSum());
            manifestHead.setSum_goods_value(checkGoodsInfo.getGoodsValueSum());
        }

        manifestHead.setAuto_id(IdUtils.getUUId());
        manifestHead.setManifest_no("H" + paramMap.get("ent_customs_code") + sdf.format(new Date()) + IdUtils.getShortUUId().substring(0, 3));
        manifestHead.setCustoms_code("9007");
        manifestHead.setBiz_type("KA12");
        manifestHead.setBiz_mode("KA27");
        manifestHead.setI_e_flag("I");
        manifestHead.setI_e_mark("E");
        manifestHead.setTrade_mode("直购进口");
        manifestHead.setDelivery_way("普通业务");
        manifestHead.setStart_land("XK16");
        manifestHead.setGoal_land("XK01");

        manifestHead.setBill_nos(paramMap.get("totalLogisticsNo"));

        manifestHead.setM_status("K01");
        manifestHead.setB_status("B01");
        manifestHead.setStatus("K01");
        manifestHead.setPort_status("K03");

        manifestHead.setInput_name(paramMap.get("input_name"));
        manifestHead.setInput_code(paramMap.get("input_code"));
        manifestHead.setTrade_name(paramMap.get("trade_name"));
        manifestHead.setTrade_code(paramMap.get("trade_code"));

        manifestHead.setApp_person(paramMap.get("app_person"));

        manifestHead.setRegion_code("RE24");
        manifestHead.setExtend_field_3("KJWTBLC");
        manifestHead.setPlat_from("XAKJE");
        manifestHead.setNote("");

        manifestData.setManifestHead(manifestHead);
        if (!StringUtils.isEmpty(checkGoodsInfoList)) {
            manifestData.setCheckGoodsInfoList(checkGoodsInfoList);
        }

        return manifestData;
    }

    @Transactional
    public Map<String, String> saveManifestInfo(LinkedHashMap<String, String> entryHead) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        String manifestNo = entryHead.get("manifest_no");
        String bill_nos = entryHead.get("bill_nos");
        if (!StringUtils.isEmpty(manifestNo) && !StringUtils.isEmpty(bill_nos)) {
            this.manifestCreateMapper.updateCheckGoodsData(manifestNo, bill_nos);
        }
        this.manifestCreateMapper.saveManifest(entryHead);
        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑成功，请到“核放单管理”处进行后续操作");
        return rtnMap;

    }


}
