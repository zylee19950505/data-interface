package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.ImpInventory;
import com.xaeport.crossborder.data.mapper.DetailDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * 更新清单状态
     *
     * @return
     */
    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.detailDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("处理清单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }


}
