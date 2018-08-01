package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryDetail;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.mapper.DetailQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DetailQueryService {

    @Autowired
    DetailQueryMapper detailQueryMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询清单数据
     */
    public List<ImpInventory> queryInventoryQueryList(Map<String, String> paramMap) throws Exception {
        return this.detailQueryMapper.queryInventoryQueryList(paramMap);
    }

    /*
     * 查询清单总数
     */
    public Integer queryInventoryQueryCount(Map<String, String> paramMap) throws Exception {
        return this.detailQueryMapper.queryInventoryQueryCount(paramMap);
    }

    //根据唯一 Id 码查询清单详情
    public ImpInventoryDetail getImpInventoryDetail(String guid) {
        Map<String,String> paramMap =  new HashMap<>();
        paramMap.put("id",guid);
        ImpInventoryHead impInventoryHead =  detailQueryMapper.queryImpInventoryHead(paramMap);
        List<ImpInventoryBody> impInventoryBodies = detailQueryMapper.queryImpInventoryBodies(paramMap);
        ImpInventoryDetail impInventoryDetail = new ImpInventoryDetail();
        impInventoryDetail.setImpInventoryHead(impInventoryHead);
        impInventoryDetail.setImpInventoryBodies(impInventoryBodies);
        return impInventoryDetail;
    }

    @Transactional
    public Map<String,String> saveInventoryDetail(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveOrderDetail(entryHead, entryLists, rtnMap,"清单查询-编辑-重报")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功，请到“清单查询-清单申报”处重新进行订单申报！");
        return rtnMap;

    }
    public boolean saveOrderDetail(LinkedHashMap<String, String> entryHead,
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
            this.detailQueryMapper.updateImpInventoryHead(entryHead);
        }
        if (!CollectionUtils.isEmpty(entryLists)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> entryList : entryLists) {
                if (!CollectionUtils.isEmpty(entryList) && entryList.size() > 2) {
                    detailQueryMapper.updateImpInventoryBodies(entryList);
                }
            }
        }
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













}
