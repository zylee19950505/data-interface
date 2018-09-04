package com.xaeport.crossborder.service.deliverymanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryDeclareService {

    @Autowired
    DeliveryDeclareMapper deliveryDeclareMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    private Log logger = LogFactory.getLog(this.getClass());

    /*
     * 查询入库明细单申报数据
     */
    public List<ImpDelivery> queryDeliveryDeclareList(Map<String, String> paramMap) throws Exception {
        return this.deliveryDeclareMapper.queryDeliveryDeclareList(paramMap);
    }

    /*
     * 查询入库明细单申报总数
     */
    public Integer queryDeliveryDeclareCount(Map<String, String> paramMap) throws Exception {
        return this.deliveryDeclareMapper.queryDeliveryDeclareCount(paramMap);
    }

    /*
     * 更新状态为入库明细单申报中
     */
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            deliveryDeclareMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("置为入库明细单[orderNo: %s]申报中时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    public void setDeliveryData(String entId,String submitKeys) {

        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(entId);
        deliveryDeclareMapper.setDeliveryData(enterprise,submitKeys);
        ;
    }

}
