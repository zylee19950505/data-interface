package com.xaeport.crossborder.service.deliverymanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

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
    public int updateSubmitCustom(Map<String, String> paramMap) {
        int flag = 0;
        try {
            String voyage = null;
            String voyageNo = null;
            String str = paramMap.get("submitKeys");
            List<String> billNos = Arrays.asList(str.split(","));
            for (String billNo : billNos) {
                voyage = deliveryDeclareMapper.queryLigisticsInfo(billNo);
                voyageNo = deliveryDeclareMapper.queryDeliveryInfo(billNo);
                if (StringUtils.isEmpty(voyage) && StringUtils.isEmpty(voyageNo)) {
                    flag = 2;
                }
            }
            if (flag == 2) {
                return flag;
            } else {
                List<String> billNoData = this.deliveryDeclareMapper.queryDeliveryByEmptyVoyage(str);
                for (String billNo : billNoData) {
                    voyage = deliveryDeclareMapper.queryLigisticsInfo(billNo);
                    voyageNo = deliveryDeclareMapper.queryDeliveryInfo(billNo);
                    if (!StringUtils.isEmpty(voyage)) {
                        deliveryDeclareMapper.updateDeliveryByLogistics(billNo, voyage);
                    } else if (StringUtils.isEmpty(voyage)) {
                        deliveryDeclareMapper.updateDeliveryByLogistics(billNo, voyageNo);
                    }
                }
                deliveryDeclareMapper.updateSubmitCustom(paramMap);
                flag = 1;
            }
        } catch (Exception e) {
            String exceptionMsg = String.format("置为入库明细单[submitKeys: %s]申报中时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    public void updateDeliveryInfoByLogistic(List<String> billNoList, Map<String, String> paramMap) {
        String voyage = null;
        String voyageNo = null;
        for (String billNo : billNoList) {
            voyage = deliveryDeclareMapper.queryLigisticsInfo(billNo);
            voyageNo = deliveryDeclareMapper.queryDeliveryInfo(billNo);
            if (!StringUtils.isEmpty(voyage)) {
                deliveryDeclareMapper.updateDeliveryByLogistics(billNo, voyage);
            } else if (StringUtils.isEmpty(voyage)) {
                deliveryDeclareMapper.updateDeliveryByLogistics(billNo, voyageNo);
            }
        }
        deliveryDeclareMapper.updateSubmitCustom(paramMap);
    }

    //数据申报进行
    public void setDeliveryData(String entId, String submitKeys) {
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(entId);
        deliveryDeclareMapper.setDeliveryData(enterprise, submitKeys);
    }

    /*
     * 查询入库明细单待填写数据
     */
    public List<ImpDelivery> querydeliverytofill(Map<String, String> paramMap) throws Exception {
        String voyage = null;
        String voyageNo = null;
        String str = paramMap.get("billNo");
        List<String> billNos = Arrays.asList(str.split(","));
        List<String> billNoData = new ArrayList<>();
        for (String billNo : billNos) {
            voyage = deliveryDeclareMapper.queryLigisticsInfo(billNo);
            voyageNo = deliveryDeclareMapper.queryDeliveryInfo(billNo);
            if (StringUtils.isEmpty(voyage) && StringUtils.isEmpty(voyageNo)) {
                billNoData.add(billNo);
            }
        }
        String billNodata = org.apache.commons.lang.StringUtils.join(billNoData.toArray(), ",");
        return this.deliveryDeclareMapper.querydeliverytofill(billNodata);
    }

    //填充入库明细单信息
    public Map<String, String> fillDeliveryInfo(ArrayList<LinkedHashMap<String, String>> deliveryHeadLists, Users userInfo) {
        Map<String, String> map = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(deliveryHeadLists)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> deliveryHead : deliveryHeadLists) {
                if (!CollectionUtils.isEmpty(deliveryHead)) {
                    this.deliveryDeclareMapper.fillDeliveryInfo(deliveryHead, userInfo);
                }
            }
        }
        map.put("result", "true");
        map.put("msg", "编辑成功，已填充入库明细单信息");
        return map;
    }

    //查询航班航次号为空的入库明细单信息
    public List<String> queryDeliveryByEmptyVoyage(String billNos) throws Exception {
        return this.deliveryDeclareMapper.queryDeliveryByEmptyVoyage(billNos);
    }

}
