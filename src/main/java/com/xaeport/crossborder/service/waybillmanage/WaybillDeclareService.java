package com.xaeport.crossborder.service.waybillmanage;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.mapper.WaybillDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WaybillDeclareService {

    @Autowired
    WaybillDeclareMapper waybillMapper;

    /*
     * 查询运单申报数据
     */
    public List<ImpLogistics> queryWaybillDeclareDataList(Map<String, String> paramMap) throws Exception {
        return this.waybillMapper.queryWaybillDeclareDataList(paramMap);
    }

    /*
     * 查询运单申报总数
     */
    public Integer queryWaybillDeclareCount(Map<String, String> paramMap) throws Exception {
        return this.waybillMapper.queryWaybillDeclareCount(paramMap);
    }

}
