package com.xaeport.crossborder.service.bondedIExit;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.CrtExitEmptyMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CrtExitEmptyService {
    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    CrtExitEmptyMapper crtExitEmptyMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    public List<PassPortHead> querExitEmptyPassportList(Map<String, String> paramMap) {
        return crtExitEmptyMapper.querExitEmptyPassportList(paramMap);
    }

    public Integer queryExitEmptyPassportCount(Map<String, String> paramMap) {
        return crtExitEmptyMapper.queryExitEmptyPassportCount(paramMap);
    }

    //更新修改出区核放单数据为申报中状态（提交海关）
    @Transactional
    public boolean updateSubmitCustom(Map<String, String> paramMap) {
        boolean flag;
        try {
            this.crtExitEmptyMapper.updateSubmitCustom(paramMap);
            flag = true;
        } catch (Exception e) {
            flag = false;
            String exceptionMsg = String.format("申报出区空车核放单[etps_preent_no: %s]时发生异常", paramMap.get("submitKeys"));
            logger.error(exceptionMsg, e);
        }
        return flag;
    }

    @Transactional
    public Map<String, String> saveExitEmptyInfo(LinkedHashMap<String, Object> object, Users users) {

        Map<String, String> rtnMap = new HashMap<>();
        PassPortHead passPortHead = new PassPortHead();
        Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(users.getEnt_Id());

        String customs_code = enterpriseDetail.getCustoms_code();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yMd = sdf.format(new Date());
        String etps_preent_no = "HFD" + customs_code + "E" + yMd + (IdUtils.getShortUUId()).substring(0, 4);

        passPortHead.setId(IdUtils.getUUId());
        passPortHead.setEtps_preent_no(etps_preent_no);
        passPortHead.setPassport_typecd("6");
        passPortHead.setRlt_tb_typecd("1");
        passPortHead.setDcl_typecd("1");
        passPortHead.setIo_typecd("E");
        passPortHead.setFlag("EXIT");
        passPortHead.setStatus(StatusCode.CQKCHFDDSB);
        passPortHead.setCrt_user(users.getId());
        passPortHead.setCrt_ent_id(users.getEnt_Id());
        passPortHead.setCrt_ent_name(users.getEnt_Name());

        passPortHead.setVehicle_no(object.get("vehicle_no").toString());
        passPortHead.setVehicle_ic_no(object.get("vehicle_ic_no").toString());
        passPortHead.setVehicle_frame_wt(object.get("vehicle_frame_wt").toString());
        passPortHead.setVehicle_wt(object.get("vehicle_wt").toString());
        passPortHead.setContainer_type(object.get("container_type").toString());
        passPortHead.setContainer_wt(object.get("container_wt").toString());
        passPortHead.setTotal_gross_wt(object.get("total_gross_wt").toString());
        passPortHead.setTotal_net_wt("0");
        passPortHead.setDcl_er_conc(object.get("dcl_er_conc").toString());
        passPortHead.setMaster_cuscd(object.get("master_cuscd").toString());
        passPortHead.setTotal_wt(object.get("total_wt").toString());
        passPortHead.setDcl_etpsno(object.get("dcl_etpsno").toString());
        passPortHead.setDcl_etps_nm(object.get("dcl_etps_nm").toString());
        passPortHead.setInput_code(object.get("dcl_etpsno").toString());
        passPortHead.setInput_name(object.get("dcl_etps_nm").toString());
        passPortHead.setAreain_etpsno(object.get("areain_etpsno").toString());
        passPortHead.setAreain_etps_nm(object.get("areain_etps_nm").toString());

        this.crtExitEmptyMapper.saveExitEmptyInfo(passPortHead);
        rtnMap.put("result", "true");
        rtnMap.put("msg", "保存出区空车核放单成功");
        return rtnMap;
    }

    public void deleteExitEmpty(String submitKeys) {
        this.crtExitEmptyMapper.deleteExitEmpty(submitKeys);
    }


}
