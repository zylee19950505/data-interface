package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.mapper.DetailQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DetailQueryService {

    @Autowired
    DetailQueryMapper detailQueryMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询清单申报数据
     */
    public List<ImpInventory> queryInventoryQueryList(Map<String, String> paramMap) throws Exception {
        return this.detailQueryMapper.queryInventoryQueryList(paramMap);
    }

    /*
     * 查询清单申报总数
     */
    public Integer queryInventoryQueryCount(Map<String, String> paramMap) throws Exception {
        return this.detailQueryMapper.queryInventoryQueryCount(paramMap);
    }

}
