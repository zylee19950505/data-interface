package com.xaeport.crossborder.service.bondedienter;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.PassPortHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.CrtEnterEmptyMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CrtEnterEmptyService {

    @Autowired
    CrtEnterEmptyMapper crtEnterEMptyMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    public List<PassPortHead> queryCrtEnterEmptyList(Map<String, String> paramMap) {
        return crtEnterEMptyMapper.queryCrtEnterEmptyList(paramMap);
    }

    public Integer queryCrtEnterEmptyCount(Map<String, String> paramMap) {
        return crtEnterEMptyMapper.queryCrtEnterEmptyCount(paramMap);
    }


    @Transactional
    public Map<String, String> saveEntryEmptyInfo(LinkedHashMap<String, Object> object, Users users) {

        Map<String, String> rtnMap = new HashMap<>();
        PassPortHead passPortHead = new PassPortHead();
        Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(users.getEnt_Id());
        //账册企业信息的企业信息
//        Enterprise enterprise = this.builderDetailMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        //String emsNo = this.builderDetailMapper.queryBwlHeadType(enterpriseDetail.getId());
        //创建核放单企业内部编号;HFD+海关十位+进出口标志（I/E）+年月日+四位流水号
        //海关十位
        String customs_code = enterpriseDetail.getCustoms_code();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yMd = sdf.format(new Date());
        String etps_preent_no = "HFD" + customs_code + "I" + yMd + (IdUtils.getShortUUId()).substring(0, 4);


        passPortHead.setId(IdUtils.getUUId());
        passPortHead.setBusiness_type(SystemConstants.T_PASS_PORT);
        passPortHead.setEtps_preent_no(etps_preent_no);
        passPortHead.setPassport_typecd("6");
        passPortHead.setRlt_tb_typecd("1");
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
        //DecimalFormat df = new DecimalFormat("######0.00000");
        passPortHead.setTotal_wt(object.get("total_wt").toString());
        passPortHead.setDcl_etpsno(object.get("dcl_etpsno").toString());
        passPortHead.setDcl_etps_nm(object.get("dcl_etps_nm").toString());
        passPortHead.setInput_code(object.get("dcl_etpsno").toString());
        passPortHead.setInput_name(object.get("dcl_etps_nm").toString());
        passPortHead.setAreain_etpsno(object.get("areain_etpsno").toString());
        passPortHead.setAreain_etps_nm(object.get("areain_etps_nm").toString());
        passPortHead.setDcl_typecd("1");
        passPortHead.setIo_typecd("I");
        passPortHead.setFlag("ENTER");
        passPortHead.setStatus(StatusCode.RQKCHFDDSB);//入区核放单待申报
        passPortHead.setCrt_user(users.getId());
        passPortHead.setCrt_ent_id(users.getEnt_Id());
        passPortHead.setCrt_ent_name(users.getEnt_Name());
        crtEnterEMptyMapper.saveEntryEmptyInfo(passPortHead);
        rtnMap.put("result", "true");
        rtnMap.put("msg", "新建入区空车核放单基本信息成功");
        return rtnMap;
    }

    public void deleteEnterEmpty(String id) {
        this.crtEnterEMptyMapper.deleteEnterEmpty(id);
    }

    public PassPortHead queryEnterEmptyDetails(Map<String, String> paramMap) {
        return crtEnterEMptyMapper.queryEnterEmptyDetails(paramMap);
    }

    /*
    * 编辑入区空车核放单
    * */
    @Transactional
    public Map<String, String> updateEntryEmptyInfo(LinkedHashMap<String, Object> object, Users users) {
        Map<String, String> rtnMap = new HashMap<>();
        PassPortHead passPortHead = new PassPortHead();

        passPortHead.setEtps_preent_no(object.get("etps_preent_no").toString());
        passPortHead.setVehicle_no(object.get("vehicle_no").toString());
        passPortHead.setVehicle_ic_no(object.get("vehicle_ic_no").toString());
        passPortHead.setVehicle_frame_wt(object.get("vehicle_frame_wt").toString());
        passPortHead.setVehicle_wt(object.get("vehicle_wt").toString());

        passPortHead.setContainer_type(object.get("container_type").toString());
        passPortHead.setContainer_wt(object.get("container_wt").toString());
        passPortHead.setTotal_gross_wt(object.get("total_gross_wt").toString());
        passPortHead.setDcl_er_conc(object.get("dcl_er_conc").toString());


        passPortHead.setMaster_cuscd(object.get("master_cuscd").toString());
        passPortHead.setTotal_wt(object.get("total_wt").toString());
        passPortHead.setDcl_etpsno(object.get("dcl_etpsno").toString());
        passPortHead.setDcl_etps_nm(object.get("dcl_etps_nm").toString());
        passPortHead.setInput_code(object.get("dcl_etpsno").toString());
        passPortHead.setInput_name(object.get("dcl_etps_nm").toString());
        passPortHead.setAreain_etpsno(object.get("areain_etpsno").toString());
        passPortHead.setAreain_etps_nm(object.get("areain_etps_nm").toString());
        passPortHead.setStatus(StatusCode.RQKCHFDDSB);//入区核放单待申报
        passPortHead.setUpd_user(users.getId());
        crtEnterEMptyMapper.updateEntryEmptyInfo(passPortHead);
        rtnMap.put("result", "true");
        rtnMap.put("msg", "保存入区空车核放单基本信息成功");
        return rtnMap;
    }

    public boolean submitEmptyCustom(Map<String, String> paramMap) {
        return crtEnterEMptyMapper.submitEmptyCustom(paramMap);
    }
}
