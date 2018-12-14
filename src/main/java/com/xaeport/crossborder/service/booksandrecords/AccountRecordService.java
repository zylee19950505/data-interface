package com.xaeport.crossborder.service.booksandrecords;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.mapper.AccountRecordMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccountRecordService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AccountRecordMapper accountRecordMapper;

    //企业管理——企业所有信息数据
    public List<BwlHeadType> queryAllAccountsInfo(Map<String, String> paramMap) throws Exception {
        return this.accountRecordMapper.queryAllAccountsInfo(paramMap);
    }

    public String crtAccountInfo(BwlHeadType bwlHeadType) {
        boolean flag;
        String id = IdUtils.getUUId();
        bwlHeadType.setId(id);
        bwlHeadType.setCrt_time(new Date());
        bwlHeadType.setUpd_time(new Date());
        try {
            flag = this.accountRecordMapper.crtAccountInfo(bwlHeadType);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("创建账册信息时发生异常[Dcl_etps_nm: %s]", bwlHeadType.getDcl_etps_nm()), e);
        }
        if (flag) {
            return id;
        }
        return "";
    }

    public boolean accountUpdate(BwlHeadType bwlHeadType) {
        boolean flag;
        int count = this.accountRecordMapper.getAccountCount(bwlHeadType.getId());
        if (count < 1) {
            return false;
        }
        try {
            flag = this.accountRecordMapper.accountUpdate(bwlHeadType);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("更新账册信息时发生异常[id:%s]", bwlHeadType.getId()), e);
        }
        return flag;
    }

    public BwlHeadType getAccountById(String id) {
        return this.accountRecordMapper.getAccountById(id);
    }



}
