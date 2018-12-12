package com.xaeport.crossborder.service.booksAndRecords;

import com.xaeport.crossborder.data.entity.BwlHeadType;
import com.xaeport.crossborder.data.mapper.AccountRecordMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountRecordService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    AccountRecordMapper accountRecordMapper;

    public String crtAccountRecord(BwlHeadType bwlHeadType) {
        boolean flag;
        String id = IdUtils.getUUId();
        bwlHeadType.setId(id);
        bwlHeadType.setCrt_time(new Date());
        bwlHeadType.setUpd_time(new Date());
        try {
            flag = this.accountRecordMapper.crtAccountRecord(bwlHeadType);
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("账册备案信息时发生异常[ent_name: %s]", bwlHeadType.getAppend_typecd()), e);
        }
        if (flag) {
            return id;
        }
        return "";
    }

}
