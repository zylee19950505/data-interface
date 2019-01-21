package com.xaeport.crossborder.service.deliverymanage;

import com.xaeport.crossborder.data.entity.ImpDelivery;
import com.xaeport.crossborder.data.mapper.DeliveryQueryMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryQueryService {

    @Autowired
    DeliveryQueryMapper deliveryQueryMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    public List<ImpDelivery> queryDeliveryQueryList(Map<String, String> paramMap) throws Exception {
        return this.deliveryQueryMapper.queryDeliveryQueryList(paramMap);
    }
}
