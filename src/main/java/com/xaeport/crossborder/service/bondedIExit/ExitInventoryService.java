package com.xaeport.crossborder.service.bondedIExit;

import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.mapper.ExitInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExitInventoryService {

    @Autowired
    ExitInventoryMapper exitInventoryMapper;

    //查询跨境清单数据
    public List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception {
        return this.exitInventoryMapper.queryEInventoryList(paramMap);
    }

    //查询跨境清单总数
    public Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception {
        return this.exitInventoryMapper.queryEInventoryCount(paramMap);
    }



}
