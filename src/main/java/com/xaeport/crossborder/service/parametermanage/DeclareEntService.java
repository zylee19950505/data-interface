package com.xaeport.crossborder.service.parametermanage;

import com.xaeport.crossborder.data.entity.DclEtps;
import com.xaeport.crossborder.data.mapper.DeclareEntMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeclareEntService {

    @Autowired
    DeclareEntMapper declareEntMapper;

    public List<DclEtps> queryDclEtpsList(Map<String, String> map) throws Exception {
        return this.declareEntMapper.queryDclEtpsList(map);
    }

    public void deleteDcletps(String id) throws Exception {
        this.declareEntMapper.deleteDcletps(id);
    }

    public void createDcletps(@Param("dclEtps") DclEtps dclEtps) throws Exception {
        this.declareEntMapper.createDcletps(dclEtps);
    }


}
