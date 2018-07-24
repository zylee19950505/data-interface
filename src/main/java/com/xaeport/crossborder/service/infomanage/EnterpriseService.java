package com.xaeport.crossborder.service.infomanage;

import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseMapper enterpriseMapper;

    public Enterprise getEnterpriseDetail(String entId){
        return this.enterpriseMapper.getEnterpriseDetail(entId);
    }




}
