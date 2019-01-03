package com.xaeport.crossborder.service.booksandrecords;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BwlListType;
import com.xaeport.crossborder.data.mapper.AccountQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountQueryService {

    @Autowired
    AccountQueryMapper accountQueryMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    public List<BwlListType> queryBwlListTypeData(Map<String, String> paramMap) {
        return this.accountQueryMapper.queryBwlListTypeData(paramMap);
    }

    public Integer queryBwlListTypeCount(Map<String, String> paramMap) {
        return this.accountQueryMapper.queryBwlListTypeCount(paramMap);
    }

}
