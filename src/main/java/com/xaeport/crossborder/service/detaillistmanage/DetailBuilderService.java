package com.xaeport.crossborder.service.detaillistmanage;

import com.xaeport.crossborder.data.entity.BuilderDetail;
import com.xaeport.crossborder.data.mapper.DetailBuilderMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DetailBuilderService {


    @Autowired
    DetailBuilderMapper detailBuilderMapper;

    public List<BuilderDetail> queryBuilderDetailList(Map<String, String> paramMap) {
        return detailBuilderMapper.queryBuilderDetailList(paramMap);
    }

    public Integer queryBuilderDetailCount(Map<String, String> paramMap) {
        return detailBuilderMapper.queryBuilderDetailCount(paramMap);
    }

    public boolean builderDetail(Map<String, String> paramMap) {
        String submitKeys = paramMap.get("submitKeys");
        String[] orders = submitKeys.split(",");

        //建一张缓存数据表,往表里插入数据,再启动一个单线程去扫描这个表;
        Map<String,String> map = new HashMap<>();
        try {
            for (String orderNo:orders){
                String id = IdUtils.getShortUUId();
                map.put("id",id);
                map.put("orderNo",orderNo);
                map.put("dataStatus","QDSCZ");
                this.detailBuilderMapper.insertBuilderCache(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
