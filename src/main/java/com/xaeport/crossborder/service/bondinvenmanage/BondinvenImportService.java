package com.xaeport.crossborder.service.bondinvenmanage;

import com.xaeport.crossborder.bondstock.CountLoader;
import com.xaeport.crossborder.bondstock.impl.CountPreReduce;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BondinvenImportMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BondinvenImportService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BondinvenImportMapper bondinvenImportMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /*
     * 保税清单清单导入
     * @param importTime 申报时间
     */
    @Transactional
    public int createBondInvenForm(Map<String, Object> excelMap, String importTime, Users user, String emsNo, String billNo) {
        int flag;
        try {
            CountLoader countLoader = new CountPreReduce();
            flag = countLoader.count(excelMap, user, emsNo);
            if (flag != 3) {
                flag = this.createImpBondInvenHead(excelMap, importTime, user, emsNo, billNo);
            } else {
                return flag;
            }
        } catch (Exception e) {
            flag = 2;
            this.log.error("导入失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /*
     * 创建保税ImpOrderHead信息
     */
    private int createImpBondInvenHead(Map<String, Object> excelMap, String importTime, Users user, String emsNo, String billNo) throws Exception {
        int flag = 0;
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        List<ImpInventoryHead> impInventoryHeadList = (List<ImpInventoryHead>) excelMap.get("ImpInventoryHead");
        for (ImpInventoryHead anImpInventoryHeadList : impInventoryHeadList) {
            ImpInventoryHead impInventoryHead = this.impBondInvenHeadData(importTime, anImpInventoryHeadList, user, enterprise, emsNo, billNo);
            flag = this.bondinvenImportMapper.isRepeatOrderNo(impInventoryHead);
            if (flag > 0) {
                return 0;
            }
            this.bondinvenImportMapper.insertImpBondInvenHead(impInventoryHead);//查询无订单号则插入ImpInventoryHead数据
            this.createImpBondInvenList(excelMap, impInventoryHead, user);//插入ImpInventoryList
        }
        return flag;
    }

    /*
     * 创建保税ImpOrderList信息
     */
    private void createImpBondInvenList(Map<String, Object> excelMap, ImpInventoryHead impInventoryHead, Users user) throws Exception {
        List<ImpInventoryBody> list = (List<ImpInventoryBody>) excelMap.get("ImpInventoryBody");
        int count = 1;
        String headOrder_no = impInventoryHead.getOrder_no();//ImpOrderHead中的订单号
        String headGuid = impInventoryHead.getGuid();//ImpOrderHead的主键
        for (int i = 0, len = list.size(); i < len; i++) {
            ImpInventoryBody impInventoryBody = list.get(i);
            String listOrder_no = impInventoryBody.getOrder_no();
            if (headOrder_no.equals(listOrder_no)) {
                impInventoryBody = this.impBondInvenBodyData(impInventoryBody, headGuid, user);
                impInventoryBody.setG_num(count);//商品序号
                count++;
                this.bondinvenImportMapper.insertImpBondInvenBody(impInventoryBody);//插入EntryList数据

            }
        }
    }

    /*
     * 查询有无重复订单号
     */
    public String getOrderNoCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpInventoryHead> list = (List<ImpInventoryHead>) excelMap.get("ImpInventoryHead");
        for (int i = 0; i < list.size(); i++) {
            ImpInventoryHead impInventoryHead = list.get(i);
            flag = this.bondinvenImportMapper.isRepeatOrderNo(impInventoryHead);
            if (flag > 0) {
                return impInventoryHead.getOrder_no();
            }
        }
        return "0";
    }

    /**
     * 表头自生成信息
     */
    private ImpInventoryHead impBondInvenHeadData(String importTime, ImpInventoryHead impInventoryHead, Users user, Enterprise enterprise, String emsNo, String billNo) throws Exception {

        impInventoryHead.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impInventoryHead.setCop_no(enterprise.getCustoms_code() + IdUtils.getShortUUId().substring(0, 10));//保税清单企业内部编码
        impInventoryHead.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impInventoryHead.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impInventoryHead.setIe_flag("I");//电子订单类型：I进口
        impInventoryHead.setBuyer_id_type("1");//订购人证件类型
        impInventoryHead.setPack_no("1");//件数
        impInventoryHead.setTrade_mode("1210");//贸易方式
        impInventoryHead.setCurrency("142");//币制
        impInventoryHead.setCountry("142");

        impInventoryHead.setEms_no(emsNo);
        impInventoryHead.setIe_date(DateTools.shortDateTimeString(importTime));
        impInventoryHead.setBill_no(billNo.trim());
        impInventoryHead.setEbc_code(user.getEnt_Customs_Code());
        impInventoryHead.setEbc_name(user.getEnt_Name());
        impInventoryHead.setAgent_code(user.getEnt_Customs_Code());
        impInventoryHead.setAgent_name(user.getEnt_Name());
        impInventoryHead.setArea_code(enterprise.getArea_code());
        impInventoryHead.setArea_name(enterprise.getArea_name());
        impInventoryHead.setAssure_code(enterprise.getAssure_ent_code());

        impInventoryHead.setData_status(StatusCode.BSQDDSB);//数据状态
        impInventoryHead.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impInventoryHead.setCrt_tm(new Date());//创建时间
        impInventoryHead.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impInventoryHead.setUpd_tm(new Date());//更新时间
        impInventoryHead.setEnt_id(enterprise.getId());
        impInventoryHead.setEnt_name(enterprise.getEnt_name());
        impInventoryHead.setEnt_customs_code(enterprise.getCustoms_code());
        impInventoryHead.setBusiness_type(SystemConstants.T_IMP_BOND_INVEN);
        return impInventoryHead;
    }

    /**
     * 表体自生成信息
     */
    private ImpInventoryBody impBondInvenBodyData(ImpInventoryBody impInventoryBody, String headGuid, Users user) throws Exception {
        impInventoryBody.setHead_guid(headGuid);//
        impInventoryBody.setCurrency("142");//币制
        impInventoryBody.setBar_code("无");//非必填项，没有必须写“无”
        return impInventoryBody;
    }

}
