package com.xaeport.crossborder.service.bondedIExit;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BuilderDetailMapper;
import com.xaeport.crossborder.data.mapper.CrtEnterManifestMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CrtEnterManifestService {

    @Autowired
    CrtEnterManifestMapper crtEnterManifestMapper;

    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Autowired
    BuilderDetailMapper builderDetailMapper;

    public List<BondInvtBsc> queryCrtEnterManifestList(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryCrtEnterManifestList(paramMap);
    }

    public Integer queryCrtEnterManifestCount(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryCrtEnterManifestCount(paramMap);
    }

    //创建核放单
    @Transactional
    public Map<String, String> createEnterManifest(Map<String, String> paramMap, Users user, Map<String, String> rtnMap) throws Exception {
        PassPortHead passPortHead = new PassPortHead();

        Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        //账册企业信息的企业信息
//        Enterprise enterprise = this.builderDetailMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        String emsNo = this.builderDetailMapper.queryBwlHeadType(enterpriseDetail.getId());
        //创建核放单企业内部编号;HFD+海关十位+进出口标志（I/E）+年月日+四位流水号
        //海关十位
        String customs_code = enterpriseDetail.getCustoms_code();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yMd = sdf.format(new Date());
        String etps_preent_no = "HFD" + customs_code + "I" + yMd + (IdUtils.getShortUUId()).substring(0, 4);

        passPortHead.setId(IdUtils.getUUId());
        passPortHead.setEtps_preent_no(etps_preent_no);
        passPortHead.setBond_invt_no(paramMap.get("invtNo"));
        passPortHead.setMaster_cuscd(enterpriseDetail.getPort());
        passPortHead.setRlt_tb_typecd("1");
        passPortHead.setPassport_typecd("1");
        passPortHead.setRlt_no(paramMap.get("invtNo"));


        passPortHead.setInput_code(enterpriseDetail.getCustoms_code());
        passPortHead.setInput_name(enterpriseDetail.getEnt_name());
        passPortHead.setDcl_etpsno(enterpriseDetail.getCustoms_code());
        passPortHead.setDcl_etps_nm(enterpriseDetail.getEnt_name());
        passPortHead.setCrt_user(user.getId());
        passPortHead.setCrt_ent_id(user.getEnt_Id());
        passPortHead.setCrt_ent_name(user.getEnt_Name());
        passPortHead.setAreain_oriact_no(emsNo);

        passPortHead.setAreain_etpsno(enterpriseDetail.getArea_code());//区内企业编码
        passPortHead.setAreain_etps_nm(enterpriseDetail.getArea_name());//区内企业名称

        //绑定类型代码
        switch (paramMap.get("bind_typecd")) {
            case "YCYP":
                passPortHead.setBind_typecd("2");
                break;
            case "YCDP":
                passPortHead.setBind_typecd("1");
                break;
            case "YPDC":
                passPortHead.setBind_typecd("3");
        }

        //设置总毛重和总净重
        String[] invtNos = paramMap.get("invtNo").split("/");
        //取第一个核注清单的申报企业信息
        BondInvtBsc bondInvtBsc = this.crtEnterManifestMapper.queryDclEtpsMsg(invtNos[0]);

//        passPortHead.setDcl_etpsno(bondInvtBsc.getDcl_etpsno());//申报企业编码
//        passPortHead.setDcl_etps_nm(bondInvtBsc.getDcl_etps_nm());//申报企业名称

        List<BondInvtDt> bondInvtDtListAll = new ArrayList<>();
        //总毛重
        double gross_wts = 0;
        double net_wts = 0;
        for (String invtNo : invtNos) {
            List<BondInvtDt> bondInvtDtList = this.crtEnterManifestMapper.queryEnterInvtory(invtNo);
            bondInvtDtListAll.addAll(bondInvtDtList);
        }
        if ("YPDC".equals(paramMap.get("bind_typecd"))) {
            for (BondInvtDt bondInvtDt : bondInvtDtListAll) {
                gross_wts += Double.parseDouble(bondInvtDt.getGross_wt()) * Double.parseDouble(bondInvtDt.getDcl_qty()) / Double.parseDouble(paramMap.get("editBoundNm"));
                net_wts += Double.parseDouble(bondInvtDt.getNet_wt()) * Double.parseDouble(bondInvtDt.getDcl_qty()) / Double.parseDouble(paramMap.get("editBoundNm"));
            }
        }else {
            for (BondInvtDt bondInvtDt : bondInvtDtListAll) {
                gross_wts += Double.parseDouble(bondInvtDt.getGross_wt());
                net_wts += Double.parseDouble(bondInvtDt.getNet_wt());
            }
        }

        /*if (!"YPDC".equals(paramMap.get("bind_typecd"))) {
            for (BondInvtDt bondInvtDt : bondInvtDtListAll) {
                gross_wts += Double.parseDouble(bondInvtDt.getGross_wt()) * Double.parseDouble(bondInvtDt.getDcl_qty());
                net_wts += Double.parseDouble(bondInvtDt.getNet_wt()) * Double.parseDouble(bondInvtDt.getDcl_qty());
            }
        }*/
        DecimalFormat df = new DecimalFormat("######0.00000");
        passPortHead.setTotal_gross_wt(df.format(gross_wts));
        passPortHead.setTotal_net_wt(df.format(net_wts));
        passPortHead.setDcl_typecd("1");
        passPortHead.setIo_typecd("I");
        passPortHead.setFlag("ENTER");
        passPortHead.setStatus(StatusCode.RQHFDDSB);
        passPortHead.setTotal_wt(df.format(gross_wts));
        crtEnterManifestMapper.createEnterManifest(passPortHead);

        //一车一单和一车多单 创建核放单关联单证 核放单编号和关联单证编号
        if (!"YPDC".equals(paramMap.get("bind_typecd"))) {
            PassPortAcmp passPortAcmp = new PassPortAcmp();
            //查询核放单编号
            passPortAcmp.setRtl_tb_typecd("1");
            passPortAcmp.setRtl_no(paramMap.get("invtNo"));
            passPortAcmp.setHead_etps_preent_no(etps_preent_no);
            passPortAcmp.setId(IdUtils.getUUId());
            passPortAcmp.setCrt_user(user.getId());
            //创建关联单号
            this.createPassPortAcmp(passPortAcmp);
        }

        //将可绑定数量化为0(不是一票多车的时候)
        if (!"YPDC".equals(paramMap.get("bind_typecd"))) {
            paramMap.put("invtNo", passPortHead.getBond_invt_no());
            //把每个保税清单可申报数量都变为0
            this.updateEnterInventory(paramMap, user);
        } else {
            //把当前的保税清单编号的可申报数量减去申报的数量
            paramMap.put("invtNo", passPortHead.getBond_invt_no());
            paramMap.put("upd_user", user.getId());
            this.crtEnterManifestMapper.updateEnterInventoryMoreCar(paramMap);
        }

        //后面还要将状态置为冻结状态
        rtnMap.put("hfd", etps_preent_no);
        return rtnMap;
    }

    @Transactional
    public void updateEnterInventory(Map<String, String> paramMap, Users user) {
        paramMap.put("upd_user", user.getId());
        String[] invtNos = paramMap.get("invtNo").split("/");

        for (String invtNo : invtNos) {
            paramMap.put("invtNo", invtNo);
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

    /**
     * 查询表头信息
     */
    public PassPortHead queryEnterManifestOneCar(Map<String, String> paramMap) {
        return crtEnterManifestMapper.queryEnterManifestOneCar(paramMap);
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
        passPortHead.setContainer_type(object.get("container_type").toString());
        passPortHead.setTotal_wt(object.get("total_wt").toString());
        passPortHead.setTotal_gross_wt(object.get("total_gross_wt").toString());
        passPortHead.setTotal_net_wt(object.get("total_net_wt").toString());
        passPortHead.setDcl_er_conc(object.get("dcl_er_conc").toString());
        passPortHead.setDcl_etps_nm(object.get("dcl_etps_nm").toString());
        passPortHead.setDcl_etpsno(object.get("dcl_etpsno").toString());
        passPortHead.setVehicle_ic_no(object.get("vehicle_ic_no").toString());
        //passPortHead.setInput_code(object.get("input_code").toString());
        //passPortHead.setInput_name(object.get("input_name").toString());
        passPortHead.setInput_code(object.get("dcl_etpsno").toString());
        passPortHead.setInput_name(object.get("dcl_etps_nm").toString());
        passPortHead.setStatus(StatusCode.RQHFDDSB);
        passPortHead.setUpd_user(users.getId());
        crtEnterManifestMapper.updateEnterManifestDetailOneCar(passPortHead);
        rtnMap.put("result", "true");
        rtnMap.put("msg", "保存入区核放单表头信息成功");
        return rtnMap;
    }

    /**
     * 保存核放单信息
     */
    public Map<String, String> createEnterManifestDetailOneCar(LinkedHashMap<String, Object> object, Users users) throws Exception {
        PassPortHead passPortHead = new PassPortHead();
        passPortHead.setId(IdUtils.getUUId());

        //模拟核放单编号
        String passPortNo = IdUtils.getShortUUId();
        passPortHead.setPassport_no(passPortNo);

        Map<String, String> rtnMap = new HashMap<>();
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
        passPortHead.setDcl_etpsno(object.get("dcl_etpsno").toString());
        passPortHead.setDcl_etps_nm(object.get("dcl_etps_nm").toString());
        passPortHead.setVehicle_ic_no(object.get("vehicle_ic_no").toString());
        passPortHead.setInput_code(object.get("input_code").toString());
        passPortHead.setInput_name(object.get("input_name").toString());
        passPortHead.setUpd_user(users.getId());
        //关联单证类型
        passPortHead.setRlt_tb_typecd("1");
        passPortHead.setRlt_no(object.get("bond_invt_no").toString());
        //创建人
        passPortHead.setCrt_user(users.getId());
        passPortHead.setCrt_ent_id(users.getEnt_Id());
        passPortHead.setCrt_ent_name(users.getEnt_Name());
        this.crtEnterManifestMapper.createEnterManifestDetailOneCar(passPortHead);

        PassPortAcmp passPortAcmp = new PassPortAcmp();
        //查询核放单编号
        passPortAcmp.setRtl_tb_typecd("1");
        passPortAcmp.setPassport_no(passPortNo);
        passPortAcmp.setRtl_no(object.get("bond_invt_no").toString());
        passPortAcmp.setId(IdUtils.getUUId());
        passPortAcmp.setHead_etps_preent_no(object.get("etps_preent_no").toString());
        passPortAcmp.setCrt_user(users.getId());
        //创建关联单号
        this.crtEnterManifestMapper.createPassPortAcmp(passPortAcmp);

        //将可绑定数量化为0
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("invtNo", passPortHead.getBond_invt_no());
        this.updateEnterInventory(paramMap, users);

        rtnMap.put("result", "true");
        rtnMap.put("msg", "保存入区核放单信息成功");
        return rtnMap;
    }


    /**
     * 商品料号和商品名称来模糊查询
     */
    public List<BondInvtDt> querySelectBondDtList(Map<String, String> paramMap) throws Exception {
        return this.crtEnterManifestMapper.querySelectBondDtList(paramMap);
    }

    public Map<String, String> saveManifestDetailMoreCar(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists, Users users) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (saveMoreCar(entryHead, entryLists, users, rtnMap, "核放单编辑成功")) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "核放单保存成功!");
        return rtnMap;

    }

    private boolean saveMoreCar(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists, Users users, Map<String, String> rtnMap, String msg) {
        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改的核放单数据！");
            return true;
        }
        if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
            // 更新表头数据
            entryHead.put("upd_user", users.getId());
            this.crtEnterManifestMapper.updatePassPortHead(entryHead);
        }
        if (!CollectionUtils.isEmpty(entryLists)) {
            // 更新表体数据
            for (LinkedHashMap<String, String> entryList : entryLists) {
                //创建表体信息
                entryList.put("head_id", entryHead.get("head_id"));
                entryList.put("bond_invt_no", entryHead.get("bond_invt_no"));
                entryList.put("etps_preent_no", entryHead.get("etps_preent_no"));
                entryList.put("passPortListId", IdUtils.getUUId());
                entryList.put("crt_user", users.getId());
                //创建核放单表体
                crtEnterManifestMapper.crtPassPortList(entryList);
                //根据表体id去修改每个核放单的表体数据
                crtEnterManifestMapper.updateEnterBondInvtDt(entryList);
            }
        }
        return false;
    }


    public List<PassPortList> querypassPortListNm(String bond_invt_no) {
        return this.crtEnterManifestMapper.querypassPortListNm(bond_invt_no);
    }

    public int queryBondBscNm(String bond_invt_no) {
        return this.crtEnterManifestMapper.queryBondBscNm(bond_invt_no);
    }

    public List<BondInvtDt> queryBondDtList(String bond_invt_no) {
        return this.crtEnterManifestMapper.queryBondDtList(bond_invt_no);
    }

    public Map<String, String> canelEnterManifestDetail(Map<String, String> paramMap) throws Exception {
        Map<String, String> rtnMap = new HashMap<String, String>();
        this.crtEnterManifestMapper.canelEnterManifestDetail(paramMap);
        rtnMap.put("result", "true");
        rtnMap.put("msg", "取消核放单成功!");
        return rtnMap;
    }
}
