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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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















}
