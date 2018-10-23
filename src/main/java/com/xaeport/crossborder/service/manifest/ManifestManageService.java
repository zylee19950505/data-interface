package com.xaeport.crossborder.service.manifest;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.CheckGoodsInfo;
import com.xaeport.crossborder.data.entity.ManifestHead;
import com.xaeport.crossborder.data.mapper.ManifestManageMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManifestManageService {

    @Autowired
    ManifestManageMapper manifestManageMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    //查询核放单数据
    public List<ManifestHead> queryManifestManageList(Map<String, String> paramMap) throws Exception {
        return manifestManageMapper.queryManifestManageList(paramMap);
    }

    //查询核放单总数
    public Integer queryManifestManageCount(Map<String, String> paramMap) {
        return manifestManageMapper.queryManifestManageCount(paramMap);
    }

    //更新预订数据状态
    public List<CheckGoodsInfo> queryCheckGoodsInfo(String manifest_no) {
        return manifestManageMapper.queryCheckGoodsInfo(manifest_no);
    }

    //更新预订数据状态
    public void updateCheckGoodsInfo(String manifest_no) {
        manifestManageMapper.updateCheckGoodsInfo(manifest_no);
    }

    //删除核放单数据
    public void manifestDelete(String manifest_no) {
        manifestManageMapper.manifestDelete(manifest_no);
    }

    //查询核放单表头打印数据
    public ManifestHead queryManifestHead(Map<String, String> paramMap) throws Exception {
        return manifestManageMapper.queryManifestHead(paramMap);
    }

    //查询核放单表体打印数据
    public List<CheckGoodsInfo> queryCheckGoodsInfoList(Map<String, String> paramMap) throws Exception {
        return manifestManageMapper.queryCheckGoodsInfoList(paramMap);
    }


    /*
    * 核放单申报
    * */
    public boolean manifestDeclare(String manifestNo) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("manifestNo", manifestNo);
        paramMap.put("dataStatus", StatusCode.HFDSBZ);//核放单申报中
        try {
            manifestManageMapper.manifestDeclare(paramMap);
            return true;
        } catch (Exception e) {
            String exceptionMsg = String.format("处理核放单[manifestNo: %s]时发生异常", paramMap.get("manifestNo"));
            logger.error(exceptionMsg, e);
            return false;
        }

    }
}
