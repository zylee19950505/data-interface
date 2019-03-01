package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.WaybillQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class WaybillQueryService {

    @Autowired
    WaybillQueryMapper waybillMapper;

    /*
     * 查询运单查询数据
     */
    public List<ImpLogisticsData> queryWaybillQueryDataList(Map<String, String> paramMap) throws Exception {
        return this.waybillMapper.queryWaybillQueryDataList(paramMap);
    }

    /*
     * 查询运单查询总数
     */
    public Integer queryWaybillQueryCount(Map<String, String> paramMap) throws Exception {
        return this.waybillMapper.queryWaybillQueryCount(paramMap);
    }

    /*
     * 查询运单详情总数
     */
    public ImpLogisticsDetail seeWaybillDetail(Map<String, String> paramMap) {
        LogisticsHead logisticsHead = waybillMapper.seeWaybillDetail(paramMap);
        Verify verify = waybillMapper.queryVerifyDetail(paramMap);
        ImpLogisticsDetail impLogisticsDetail = new ImpLogisticsDetail();
        impLogisticsDetail.setLogisticsHead(logisticsHead);
        impLogisticsDetail.setVerify(verify);
        return impLogisticsDetail;
    }

    /*
     * 查询编辑运单详情
     **/
    public Map<String, String> saveBillDetail(
            LinkedHashMap<String, String> entryHead,
            ArrayList<LinkedHashMap<String, String>> entryLists
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveBillDetail(entryHead, entryLists, rtnMap, "运单查询-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“运单申报”处重新进行申报！");
        return rtnMap;
    }

    public boolean saveBillDetail(
            LinkedHashMap<String, String> entryHead,
            List<LinkedHashMap<String, String>> entryLists,
            Map<String, String> rtnMap,
            String notes
    ) {
        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.waybillMapper.updateLogistics(entryHead);
        }
        return false;
    }

    /*
    * 运单状态回执详情
    * */
    public ImpLogistics queryReturnDetail(Map<String, String> paramMap) {
        return waybillMapper.queryReturnDetail(paramMap);
    }

    /*
     * 查询编辑运单详情
     **/
    public Map<String, String> saveLogicalDetail(
            LinkedHashMap<String, String> entryHead
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveLogisticsLogicalDetail(entryHead, rtnMap, "运单查询-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“运单申报”处确认是否校验通过！");
        return rtnMap;
    }

    public boolean saveLogisticsLogicalDetail(
            LinkedHashMap<String, String> entryHead,
            Map<String, String> rtnMap,
            String notes
    ) {
        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String guid = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.waybillMapper.updateLogisticsByLogic(entryHead);
        }
        this.waybillMapper.deleteVerifyStatus(guid);
        return false;
    }

}
