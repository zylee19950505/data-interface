package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DetailDeclareService {

    @Autowired
    DetailDeclareMapper detailDeclareMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询清单申报数据
     */
    public List<ImpInventory> queryInventoryDeclareList(Map<String, String> paramMap) throws Exception {
        return this.detailDeclareMapper.queryInventoryDeclareList(paramMap);
    }

    /*
     * 查询清单申报总数
     */
    public Integer queryInventoryDeclareCount(Map<String, String> paramMap) throws Exception {
        return this.detailDeclareMapper.queryInventoryDeclareCount(paramMap);
    }


}
