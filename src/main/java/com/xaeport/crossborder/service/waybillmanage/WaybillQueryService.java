package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsData;
import com.xaeport.crossborder.data.entity.Logistics;
import com.xaeport.crossborder.data.entity.LogisticsHead;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
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
    public Logistics waybillQueryById(Map<String, String> paramMap) {
        LogisticsHead logisticsHead = waybillMapper.waybillQueryById(paramMap);
        Logistics logistics = new Logistics();
        logistics.setLogisticsHead(logisticsHead);
        return logistics;
    }
/*
* 查询编辑运单详情
* */
	public Map<String,String> saveBillDetail(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveBillDetail(entryHead, entryLists, rtnMap,"运单查询-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“运单查询-运单申报”处重新进行运单申报！");
        return rtnMap;
	}

    public boolean saveBillDetail(LinkedHashMap<String, String> entryHead,
                                   List<LinkedHashMap<String, String>> entryLists,
                                   Map<String, String> rtnMap,String notes){

        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String entryHeadId = entryHead.get("entryhead_guid");
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            this.waybillMapper.updateBillHead(entryHead);
        }
       /* if (!CollectionUtils.isEmpty(entryLists)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> entryList : entryLists) {
                if (!CollectionUtils.isEmpty(entryList) && entryList.size() > 2) {
                    orderQueryMapper.updateOrderList(entryList);
                }
            }
        }*/
		/*// 修改entry_head的 操作状态SWOP3 和 申报状态（置空）、申报结果（置空）
		this.entryMapper.updateEntryHeadStatus(StatusCode.BDSB, "", "", entryHeadId);
		// 插入状态改变记录表
		StatusRecord statusRecord = new StatusRecord();
		statusRecord.setSr_id(IdUtils.getUUId());
		statusRecord.setCreate_time(new Date());
		statusRecord.setOdd_no(entryHeadId);
		statusRecord.setNotes(notes);
		statusRecord.setBelong("Entryhead");
		statusRecord.setStatus_code(StatusCode.BDSB);
		this.entryMapper.insertStatusRecord(statusRecord);*/
        return false;
    }

    /*
    * 运单状态回执详情
    * */
	public ImpLogistics queryReturnDetail(Map<String, String> paramMap) {
	    return waybillMapper.queryReturnDetail(paramMap);
	}
}
