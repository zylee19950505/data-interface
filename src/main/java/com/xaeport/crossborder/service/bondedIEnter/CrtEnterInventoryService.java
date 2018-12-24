package com.xaeport.crossborder.service.bondedIEnter;

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
        //目前先设置uuid(用来关联表体和表头的信息)
        String uuId = IdUtils.getUUId();
        int count = 1;
        for (int i = 0;i < list.size();i++){
            String dtId = IdUtils.getUUId();
            BondInvtDt bondInvtDt = list.get(i);
            bondInvtDt.setId(dtId);
            bondInvtDt.setPutrec_seqno(count);
            bondInvtDt.setGdecd(String.valueOf(count));
            bondInvtDt.setHead_etps_inner_invt_no(uuId);
            this.crtEnterInventoryMapper.insertEnterInventoryDt(bondInvtDt);
            count++;
        }
        //将企业信息暂时保存在表头里
        String bscId = IdUtils.getUUId();
        //根据当前用户查找企业id
        String entId = userMapper.getEnterpriseId(user.getId());
        //根据企业id查找企业信息
        Enterprise enterpriseDetail = enterpriseMapper.getEnterpriseDetail(entId);

        //将企业信息和表头基本信息预存在bond_invt_bsc里
        BondInvtBsc bondInvtBsc =  new BondInvtBsc();
        bondInvtBsc.setId(bscId);
        bondInvtBsc.setEtps_inner_invt_no(uuId);
        bondInvtBsc.setCrt_ent_id(enterpriseDetail.getId());
        bondInvtBsc.setCrt_ent_name(enterpriseDetail.getEnt_name());
        bondInvtBsc.setBizop_etpsno(enterpriseDetail.getCustoms_code());//经营企业编号
        bondInvtBsc.setBizop_etps_nm(enterpriseDetail.getEnt_name());//经营企业名称
        bondInvtBsc.setDcl_etpsno(enterpriseDetail.getCustoms_code());//申报企业编号
        bondInvtBsc.setDcl_etps_nm(enterpriseDetail.getEnt_name());//申报企业名称
        bondInvtBsc.setDcl_plc_cuscd(enterpriseDetail.getPort());//主管海关
        bondInvtBsc.setCrt_user(user.getId());
        this.crtEnterInventoryMapper.insertEnterInventoryBsc(bondInvtBsc);

        //是否需要返回保税清单编号,以供保存和取消时使用

        return uuId;
    }

    public BondInvt seeEnterInventoryDetail(Map<String, String> paramMap) throws Exception{
        BondInvtBsc bondInvtBsc = new BondInvtBsc();
        bondInvtBsc = this.crtEnterInventoryMapper.queryEnterInventoryBsc(paramMap);
        List<BondInvtDt> bondInvtDtList = new ArrayList<>();
        bondInvtDtList = this.crtEnterInventoryMapper.queryEnterInventoryDt(paramMap);
        BondInvt bondInvt = new BondInvt();
        bondInvt.setBondInvtBsc(bondInvtBsc);
        bondInvt.setBondInvtDtList(bondInvtDtList);
        return bondInvt;
    }

    /*
    * 保存入库核放单的表头信息
    * */
    @Transactional
    public Map<String, String> updateEnterInventoryDetail(LinkedHashMap<String, String> entryHead,Users users) {
        Map<String,String> rtnMap = new HashMap<String,String>();
        if (updateEnterInventoryDetail(entryHead,rtnMap,users)) return rtnMap;

        rtnMap.put("result", "true");
        rtnMap.put("msg", "编辑信息成功");
        return rtnMap;
    }

    private boolean updateEnterInventoryDetail(LinkedHashMap<String, String> entryHead, Map<String, String> rtnMap,Users users) {
        if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1)) {
            rtnMap.put("result", "false");
            rtnMap.put("msg", "未发现需要修改数据！");
            return true;
        }
        String invt_no = entryHead.get("invt_no");
        //封装数据
        BondInvtBsc bondInvtBsc = new BondInvtBsc();
        bondInvtBsc.setEtps_inner_invt_no(invt_no);
        bondInvtBsc.setChg_tms_cnt(0);
        bondInvtBsc.setUpd_user(users.getId());
        bondInvtBsc.setStatus("BDDS1");
        bondInvtBsc.setFlag("Enter");//进出区数据标识
        if (!StringUtils.isEmpty(entryHead.get("bizop_etpsno"))){
            bondInvtBsc.setBizop_etpsno(entryHead.get("bizop_etpsno"));
        }
        if (!StringUtils.isEmpty(entryHead.get("bizop_etps_nm"))){
            bondInvtBsc.setBizop_etps_nm(entryHead.get("bizop_etps_nm"));
        }
        if (!StringUtils.isEmpty(entryHead.get("dcl_etpsno"))){
            bondInvtBsc.setDcl_etpsno(entryHead.get("dcl_etpsno"));
        }
        if (!StringUtils.isEmpty(entryHead.get("dcl_etps_nm"))){
            bondInvtBsc.setDcl_etps_nm(entryHead.get("dcl_etps_nm"));
        }
        if (!StringUtils.isEmpty(entryHead.get("putrec_no"))){
            bondInvtBsc.setPutrec_no(entryHead.get("putrec_no"));
        }

        if (!StringUtils.isEmpty(entryHead.get("rcvgd_etpsno"))){
            bondInvtBsc.setRcvgd_etpsno(entryHead.get("rcvgd_etpsno"));
        }
        if (!StringUtils.isEmpty(entryHead.get("rcvgd_etps_nm"))){
            bondInvtBsc.setRcvgd_etps_nm(entryHead.get("rcvgd_etps_nm"));
        }
        if (!StringUtils.isEmpty(entryHead.get("impexp_portcd"))){
            bondInvtBsc.setImpexp_portcd(entryHead.get("impexp_portcd"));
        }
        if (!StringUtils.isEmpty(entryHead.get("dcl_plc_cuscd"))){
            bondInvtBsc.setDcl_plc_cuscd(entryHead.get("dcl_plc_cuscd"));
        }
        bondInvtBsc.setImpexp_markcd("I");
        bondInvtBsc.setMtpck_endprd_markcd("1");

        if (!StringUtils.isEmpty(entryHead.get("supv_modecd"))){
            bondInvtBsc.setSupv_modecd(entryHead.get("supv_modecd"));
        }
        if (!StringUtils.isEmpty(entryHead.get("trsp_modecd"))){
            bondInvtBsc.setTrsp_modecd(entryHead.get("trsp_modecd"));
        }
        bondInvtBsc.setDclcus_flag("1");
        bondInvtBsc.setBond_invt_typecd("8");
        bondInvtBsc.setDclcus_typecd("1");
        bondInvtBsc.setDcl_typecd("1");
        if (!StringUtils.isEmpty(entryHead.get("stship_trsarv_natcd"))){
            bondInvtBsc.setStship_trsarv_natcd(entryHead.get("stship_trsarv_natcd"));
        }
        this.crtEnterInventoryMapper.updateEnterInventoryDetail(bondInvtBsc);
        return false;
    }


    public void deleteEnterInven(String invt_no) {
        this.crtEnterInventoryMapper.deleteEnterInvenBsc(invt_no);
        this.crtEnterInventoryMapper.deleteEnterInvenDt(invt_no);
    }
}
