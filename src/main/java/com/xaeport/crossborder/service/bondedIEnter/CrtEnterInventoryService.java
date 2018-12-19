package com.xaeport.crossborder.service.bondedIEnter;

import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.CrtEnterInventoryMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CrtEnterInventoryService {

    @Autowired
    CrtEnterInventoryMapper crtEnterInventoryMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;


    public String createEnterInventory(Map<String, Object> excelMap, Users user) {
        List<BondInvtDt> list = (List<BondInvtDt>) excelMap.get("bondInvtDtList");
        String flag = "";
        //存公司id吗

        //设置一个保税清单编号 QD+4位主管海关+2位年份+1位进出口标志+9位流水号,首次备案填写清单预录入编号
            //查询找到企业的主管海关编号
               /* Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
                    //主管海关
                String port = enterpriseDetail.getPort();
                    //两位年
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                String year = sdf.format(date).toString();
                String lastTwoYear = year.substring(year.length() - 2, year.length());
                    //进出口标志*/
        //目前先设置uuid
        String uuId = IdUtils.getUUId();
        int count = 1;
        for (int i = 0;i < list.size();i++){
            String dtId = IdUtils.getUUId();
            BondInvtDt bondInvtDt = list.get(i);
            bondInvtDt.setId(dtId);
            bondInvtDt.setPutrec_seqno(String.valueOf(count));
            bondInvtDt.setGdecd(String.valueOf(count));
            bondInvtDt.setBond_invt_no(uuId);
            this.crtEnterInventoryMapper.insertEnterInventory(bondInvtDt);
            count++;

        }



        //是否需要返回保税清单编号,以供保存和取消时使用

        return uuId;
    }
}
