package com.xaeport.crossborder.service.bondedIExit;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.CrtEnterManifestMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CrtEnterManifestService {

    @Autowired
    CrtEnterManifestMapper crtEnterManifestMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    public List<BondInvtBsc> queryCrtEnterManifestList(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryCrtEnterManifestList(paramMap);
    }

    public Integer queryCrtEnterManifestCount(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryCrtEnterManifestCount(paramMap);
    }

    //创建核放单
    @Transactional
    public void createEnterManifest(Map<String, String> paramMap, Users user) throws Exception{
        PassPortHead passPortHead =  new PassPortHead();
        passPortHead.setId(IdUtils.getUUId());

        //模拟核放单编号
        passPortHead.setPassport_no(IdUtils.getShortUUId());

        Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        //创建核放单企业内部编号;HFD+海关十位+进出口标志（I/E）+年月日+四位流水号
        String nextVal = crtEnterManifestMapper.querySeqPassPortHeadNextval();
        //海关十位
        String customs_code = enterpriseDetail.getCustoms_code();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yMd = sdf.format(new Date());
        String etps_preent_no = "HFD"+customs_code+"I"+yMd+nextVal;
        passPortHead.setEtps_preent_no(etps_preent_no);

        //核注清单编号
        passPortHead.setBond_invt_no(paramMap.get("invtNo"));

        //主管关区代码
        passPortHead.setMaster_cuscd(enterpriseDetail.getPort());

        //绑定类型代码
        switch (paramMap.get("bind_typecd")){
            case "YCYP":
                passPortHead.setBind_typecd("2");
                break;
            case "YCDP":
                passPortHead.setBind_typecd("1");
                break;
            case "YPDC":
                passPortHead.setBind_typecd("3");
        }
        //关联单证类型
        passPortHead.setRlt_tb_typecd("1");
        passPortHead.setRlt_no(paramMap.get("invtNo"));

        //申报企业编号
        passPortHead.setDcl_etpsno(enterpriseDetail.getCustoms_code());
        //申报企业名称
        passPortHead.setDcl_etps_nm(enterpriseDetail.getEnt_name());
        //申报企业编号
        passPortHead.setInput_code(enterpriseDetail.getCustoms_code());
        //申报企业名称
        passPortHead.setInput_name(enterpriseDetail.getEnt_name());

        //绑定数量

        //创建人
        passPortHead.setCrt_user(user.getId());
        passPortHead.setCrt_ent_id(user.getEnt_Id());
        passPortHead.setCrt_ent_name(user.getEnt_Name());


        //设置总毛重和总净重
        String[] invtNos = paramMap.get("invtNo").split("/");

        List<BondInvtDt> bondInvtDtList = new ArrayList<>();
        //总毛重
        double gross_wts = 0;
        double net_wts = 0;
        for (String invtNo: invtNos){
            bondInvtDtList = this.crtEnterManifestMapper.queryEnterInvtory(invtNo);
        }
        for (BondInvtDt bondInvtDt:bondInvtDtList) {
            gross_wts += Double.parseDouble(bondInvtDt.getGross_wt())*Double.parseDouble(bondInvtDt.getDcl_qty());
            net_wts += Double.parseDouble(bondInvtDt.getNet_wt())*Double.parseDouble(bondInvtDt.getDcl_qty());
        }
        DecimalFormat df = new DecimalFormat("######0.00000");
        passPortHead.setTotal_gross_wt(df.format(gross_wts));
        passPortHead.setTotal_net_wt(df.format(net_wts));

        crtEnterManifestMapper.createEnterManifest(passPortHead);


    }


    @Transactional
    public void updateEnterInventory(Map<String, String> paramMap, Users user) {
        paramMap.put("upd_user",user.getId());
        String[] invtNos = paramMap.get("invtNo").split("/");

        for (String invtNo: invtNos) {
            paramMap.put("invtNo",invtNo);
            crtEnterManifestMapper.updateEnterInventory(paramMap);
        }


    }

    public List<BondInvtDt> queryInventoryList(String invtNo) {
        return crtEnterManifestMapper.queryInventoryList(invtNo);
    }
    //查询核放单编号
    public String queryEnterManifestPassPortNo(String invtNo) {
        return crtEnterManifestMapper.queryEnterManifestPassPortNo(invtNo);
    }

    //创建关联单
    @Transactional
    public void createPassPortAcmp(PassPortAcmp passPortAcmp) {
        crtEnterManifestMapper.createPassPortAcmp(passPortAcmp);
    }

    public PassPortHead queryManifestOneCar(String bond_invt_no) {
       return crtEnterManifestMapper.queryManifestOneCar(bond_invt_no);
    }

    @Transactional
    public Map<String, String> updateEnterManifestDetailOneCar(LinkedHashMap<String, Object> object, Users users) throws Exception {
        Map<String, String> rtnMap = new HashMap<>();
       PassPortHead passPortHead = new PassPortHead();
        passPortHead.setEtps_preent_no(object.get("etps_preent_no").toString());
        passPortHead.setBond_invt_no(object.get("bond_invt_no").toString());
        passPortHead.setMaster_cuscd(object.get("master_cuscd").toString());
        passPortHead.setBind_typecd(object.get("bind_typecd").toString());
        passPortHead.setAreain_etpsno(object.get("areain_etpsno").toString());
        passPortHead.setAreain_etps_nm(object.get("areain_etps_nm").toString());
        passPortHead.setVehicle_no(object.get("vehicle_no").toString());
        passPortHead.setVehicle_wt(object.get("vehicle_wt").toString());
        passPortHead.setVehicle_frame_wt(object.get("vehicle_frame_wt").toString());
        passPortHead.setContainer_wt(object.get("container_wt").toString());
        passPortHead.setTotal_wt(object.get("total_wt").toString());
        passPortHead.setTotal_gross_wt(object.get("total_gross_wt").toString());
        passPortHead.setTotal_net_wt(object.get("total_net_wt").toString());
        passPortHead.setDcl_er_conc(object.get("dcl_er_conc").toString());
        passPortHead.setDcl_etps_nm(object.get("dcl_etps_nm").toString());
        passPortHead.setInput_code(object.get("input_code").toString());
        passPortHead.setInput_name(object.get("input_name").toString());
        passPortHead.setUpd_user(users.getId());

        crtEnterManifestMapper.updateEnterManifestDetailOneCar(passPortHead);

        //将可绑定数量化为0
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("invtNo",passPortHead.getBond_invt_no());
        this.updateEnterInventory(paramMap,users);
        //后面还要将状态置为冻结状态

       rtnMap.put("result", "true");
       rtnMap.put("msg", "保存入区核放单表头信息成功");
       return rtnMap;
    }
}
