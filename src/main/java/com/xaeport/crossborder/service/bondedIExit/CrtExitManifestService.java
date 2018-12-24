package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BondInvtBsc;
import com.xaeport.crossborder.data.mapper.CrtExitManifestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CrtExitManifestService {

    @Autowired
    CrtExitManifestMapper crtExitManifestMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    //查询跨境清单数据
    public List<BondInvtBsc> queryEInventoryList(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryEInventoryList(paramMap);
    }

    //查询跨境清单总数
    public Integer queryEInventoryCount(Map<String, String> paramMap) throws Exception {
        return this.crtExitManifestMapper.queryEInventoryCount(paramMap);
    }

}
