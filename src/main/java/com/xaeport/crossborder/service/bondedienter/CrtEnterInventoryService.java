package com.xaeport.crossborder.service.bondedienter;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.CrtEnterInventoryMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.mapper.UserMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CrtEnterInventoryService {

    @Autowired
    CrtEnterInventoryMapper crtEnterInventoryMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;
    @Autowired
    UserMapper userMapper;

    public String createEnterInventory(Map<String, Object> excelMap, Users user) {
        List<BondInvtDt> list = (List<BondInvtDt>) excelMap.get("bondInvtDtList");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(date);
        String etpsInnerInvtNo = "HZQD" + user.getEnt_Customs_Code() + "I" + dateNowStr + (IdUtils.getShortUUId()).substring(0, 4);
        int original_nm = 0;
        //将企业信息暂时保存在表头里
        String bscId = IdUtils.getUUId();
        //根据当前用户查找企业id
        String entId = userMapper.getEnterpriseId(user.getId());
        //根据企业id查找企业信息
        Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(entId);

        //查找当前登录企业的区内企业信息
        Enterprise enterprise = enterpriseMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        for (int i = 0; i < list.size(); i++) {
            String dtId = IdUtils.getUUId();
            BondInvtDt bondInvtDt = list.get(i);
            //设置表头原有数量
            original_nm += Double.parseDouble(bondInvtDt.getDcl_qty());
            bondInvtDt.setId(dtId);

            bondInvtDt.setGds_seqno(i + 1);
            bondInvtDt.setGdecd(bondInvtDt.getGdecd());
            bondInvtDt.setHead_etps_inner_invt_no(etpsInnerInvtNo);
            bondInvtDt.setDestination_natcd("142");//最终目的国
            bondInvtDt.setModf_markcd("3");//修改标志位
            bondInvtDt.setEntry_gds_seqno(i + 1);
            this.crtEnterInventoryMapper.insertEnterInventoryDt(bondInvtDt);
        }


        //将企业信息和表头基本信息预存在bond_invt_bsc里
        BondInvtBsc bondInvtBsc = new BondInvtBsc();
        bondInvtBsc.setId(bscId);
        bondInvtBsc.setBusiness_type(SystemConstants.T_BOND_INVT);
        bondInvtBsc.setEtps_inner_invt_no(etpsInnerInvtNo);
        bondInvtBsc.setCrt_ent_id(enterpriseDetail.getId());
        bondInvtBsc.setCrt_ent_name(enterpriseDetail.getEnt_name());
        bondInvtBsc.setBizop_etpsno("");//经营企业编号:用户自行填写
        bondInvtBsc.setBizop_etps_nm("");//经营企业名称:用户自行填写
//        bondInvtBsc.setDcl_etpsno(enterpriseDetail.getDeclare_ent_code());//获取信息的申报企业编号
//        bondInvtBsc.setDcl_etps_nm(enterpriseDetail.getDeclare_ent_name());//获取信息的申报企业名称
        bondInvtBsc.setRcvgd_etpsno(enterpriseDetail.getCustoms_code());//获取企业的海关十位
        bondInvtBsc.setRcvgd_etps_nm(enterpriseDetail.getEnt_name());//获取企业的名称

        bondInvtBsc.setDcl_plc_cuscd(enterpriseDetail.getPort());//主管海关
        bondInvtBsc.setEc_customs_code(list.get(0).getEc_customs_code());//电商海关编码
        bondInvtBsc.setCrt_user(user.getId());
        bondInvtBsc.setPutrec_no(this.crtEnterInventoryMapper.queryBws_no(entId));

        //设置核注清单原有数量,可绑定数量,绑定数量
        bondInvtBsc.setOriginal_nm(original_nm);
        bondInvtBsc.setUsable_nm(original_nm);
        bondInvtBsc.setBound_nm(original_nm);

        bondInvtBsc.setImpexp_markcd("I");
        bondInvtBsc.setMtpck_endprd_markcd("I");
        bondInvtBsc.setSupv_modecd("1210");
        bondInvtBsc.setDclcus_flag("1");
//        bondInvtBsc.setdclcus("3");
        bondInvtBsc.setBond_invt_typecd("0");
        bondInvtBsc.setDclcus_typecd("2");
        bondInvtBsc.setDcl_typecd("1");

        this.crtEnterInventoryMapper.insertEnterInventoryBsc(bondInvtBsc);
        //是否需要返回保税清单编号,以供保存和取消时使用
        return etpsInnerInvtNo;
    }

    public BondInvt seeEnterInventoryDetail(Map<String, String> paramMap) throws Exception {
        BondInvtBsc bondInvtBsc = this.crtEnterInventoryMapper.queryEnterInventoryBsc(paramMap);
        List<BondInvtDt> bondInvtDtList = this.crtEnterInventoryMapper.queryEnterInventoryDt(paramMap);
        Verify verify = this.crtEnterInventoryMapper.queryLogicVerify(paramMap);
        BondInvt bondInvt = new BondInvt();
        bondInvt.setBondInvtBsc(bondInvtBsc);
        bondInvt.setBondInvtDtList(bondInvtDtList);
        bondInvt.setVerify(verify);
        return bondInvt;
    }

    /*
     * 保存入库核放单的表头信息
     * */
    @Transactional
    public Map<String, String> updateEnterInventoryDetail(
            LinkedHashMap<String, String> bondInvtHead,
            ArrayList<LinkedHashMap<String, String>> bondInvtDts,
            Users users
    ) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        if (updateEnterInvtHead(bondInvtHead, bondInvtDts, rtnMap, users)) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "保存信息成功");
        return rtnMap;
    }

    private boolean updateEnterInvtHead(
            LinkedHashMap<String, String> bondInvtHead,
            ArrayList<LinkedHashMap<String, String>> bondInvtDts,
            Map<String, String> rtnMap,
            Users users
    ) {
        if ((CollectionUtils.isEmpty(bondInvtHead) && bondInvtHead.size() < 1) && CollectionUtils.isEmpty(bondInvtDts)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        //封装数据
        BondInvtBsc bondInvtBsc = new BondInvtBsc();
        bondInvtBsc.setEtps_inner_invt_no(bondInvtHead.get("etps_inner_invt_no"));
        bondInvtBsc.setChg_tms_cnt(0);
        bondInvtBsc.setUpd_user(users.getId());
        bondInvtBsc.setStatus(StatusCode.BSYDR);//数据状态
        bondInvtBsc.setFlag(SystemConstants.BSRQ);//进出区数据标识
        if (!StringUtils.isEmpty(bondInvtHead.get("bizop_etpsno"))) {
            bondInvtBsc.setBizop_etpsno(bondInvtHead.get("bizop_etpsno"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("bizop_etps_nm"))) {
            bondInvtBsc.setBizop_etps_nm(bondInvtHead.get("bizop_etps_nm"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dcl_etpsno"))) {
            bondInvtBsc.setDcl_etpsno(bondInvtHead.get("dcl_etpsno"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dcl_etps_nm"))) {
            bondInvtBsc.setDcl_etps_nm(bondInvtHead.get("dcl_etps_nm"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("putrec_no"))) {
            bondInvtBsc.setPutrec_no(bondInvtHead.get("putrec_no"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("rcvgd_etpsno"))) {
            bondInvtBsc.setRcvgd_etpsno(bondInvtHead.get("rcvgd_etpsno"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("rcvgd_etps_nm"))) {
            bondInvtBsc.setRcvgd_etps_nm(bondInvtHead.get("rcvgd_etps_nm"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("impexp_portcd"))) {
            bondInvtBsc.setImpexp_portcd(bondInvtHead.get("impexp_portcd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dcl_plc_cuscd"))) {
            bondInvtBsc.setDcl_plc_cuscd(bondInvtHead.get("dcl_plc_cuscd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("trsp_modecd"))) {
            bondInvtBsc.setTrsp_modecd(bondInvtHead.get("trsp_modecd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("supv_modecd"))) {
            bondInvtBsc.setSupv_modecd(bondInvtHead.get("supv_modecd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("impexp_markcd"))) {
            bondInvtBsc.setImpexp_markcd(bondInvtHead.get("impexp_markcd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("mtpck_endprd_markcd"))) {
            bondInvtBsc.setMtpck_endprd_markcd(bondInvtHead.get("mtpck_endprd_markcd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dclcus_flag"))) {
            bondInvtBsc.setDclcus_flag(bondInvtHead.get("dclcus_flag"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dclcus_typecd"))) {
            bondInvtBsc.setDclcus_typecd(bondInvtHead.get("dclcus_typecd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("bond_invt_typecd"))) {
            bondInvtBsc.setBond_invt_typecd(bondInvtHead.get("bond_invt_typecd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dcl_typecd"))) {
            bondInvtBsc.setDcl_typecd(bondInvtHead.get("dcl_typecd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("stship_trsarv_natcd"))) {
            bondInvtBsc.setStship_trsarv_natcd(bondInvtHead.get("stship_trsarv_natcd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("corr_entry_dcl_etps_sccd"))) {
            bondInvtBsc.setCorr_entry_dcl_etps_sccd(bondInvtHead.get("corr_entry_dcl_etps_sccd"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("corr_entry_dcl_etps_no"))) {
            bondInvtBsc.setCorr_entry_dcl_etps_no(bondInvtHead.get("corr_entry_dcl_etps_no"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("corr_entry_dcl_etps_nm"))) {
            bondInvtBsc.setCorr_entry_dcl_etps_nm(bondInvtHead.get("corr_entry_dcl_etps_nm"));
        }
        if (!StringUtils.isEmpty(bondInvtHead.get("dec_type"))) {
            bondInvtBsc.setDec_type(bondInvtHead.get("dec_type"));
        }
        this.crtEnterInventoryMapper.updateEnterInvtHead(bondInvtBsc);
        if (!CollectionUtils.isEmpty(bondInvtDts)) {
            //更新表体数据
            for (LinkedHashMap<String, String> bondInvtDt : bondInvtDts) {
                if (!CollectionUtils.isEmpty(bondInvtDt) && bondInvtDt.size() > 2) {
                    crtEnterInventoryMapper.updateEnterInvtBody(bondInvtDt, users);
                }
            }
        }
        return false;
    }

    public void deleteEnterInven(String etps_inner_invt_no) {
        this.crtEnterInventoryMapper.deleteEnterInvenBsc(etps_inner_invt_no);
        this.crtEnterInventoryMapper.deleteEnterInvenDt(etps_inner_invt_no);
    }
}
