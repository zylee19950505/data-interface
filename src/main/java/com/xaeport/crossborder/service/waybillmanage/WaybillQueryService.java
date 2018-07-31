package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.Logistics;
import com.xaeport.crossborder.data.entity.LogisticsHead;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import com.xaeport.crossborder.data.mapper.WaybillQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WaybillQueryService {

    @Autowired
    WaybillQueryMapper waybillMapper;

    /*
     * 查询运单查询数据
     */
    public List<ImpLogistics> queryWaybillQueryDataList(Map<String, String> paramMap) throws Exception {
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

}
