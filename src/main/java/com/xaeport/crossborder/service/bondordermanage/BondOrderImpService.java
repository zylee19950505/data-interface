package com.xaeport.crossborder.service.bondordermanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BondOrderImpMapper;
import com.xaeport.crossborder.data.mapper.EnterpriseMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BondOrderImpService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    BondOrderImpMapper bondOrderImpMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /*
     * 订单导入
     * @param importTime 申报时间
     */
    @Transactional
    public int createOrderForm(Map<String, Object> excelMap, String importTime, Users user, String billNo) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpOrderHead(excelMap, importTime, user, billNo);
        } catch (Exception e) {
            flag = 2;
            this.log.error("导入失败", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /*
     * 创建ImpOrderHead信息
     */
    private int createImpOrderHead(Map<String, Object> excelMap, String importTime, Users user, String billNo) throws Exception {
        int flag = 0;
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        List<ImpOrderHead> impOrderHeadList = (List<ImpOrderHead>) excelMap.get("ImpOrderHead");
        for (ImpOrderHead anImpOrderHeadList : impOrderHeadList) {
            ImpOrderHead impOrderHead = this.impOrderHeadData(importTime, anImpOrderHeadList, user, enterprise);
            impOrderHead.setBill_No(billNo);
            flag = this.bondOrderImpMapper.isRepeatOrderNo(impOrderHead);
            if (flag > 0) {
                return 0;
            }
            this.bondOrderImpMapper.insertImpOrderHead(impOrderHead);//查询无订单号则插入ImpOrderHead数据
            this.createImpOrderList(excelMap, impOrderHead, user);//插入ImpOrderGoodsList
            this.insertOrderNo(impOrderHead);
        }
        return flag;
    }

    private void insertOrderNo(ImpOrderHead impOrderHead) {
        String billNo = impOrderHead.getBill_No();
        String brevityCode = billNo.substring(0, 2);
        Integer sum = this.bondOrderImpMapper.queryEntInfoByBrevityCode(brevityCode);
        if (sum > 0) {
            OrderNo orderNo = new OrderNo();
            orderNo.setId(IdUtils.getUUId());
            orderNo.setOrder_no(impOrderHead.getOrder_No());
            orderNo.setCrt_tm(new Date());
            orderNo.setUsed("0");
            this.bondOrderImpMapper.insertOrderNo(orderNo);
        }
    }

    /*
     * 创建ImpOrderList信息
     */
    private void createImpOrderList(Map<String, Object> excelMap, ImpOrderHead impOrderHead, Users user) throws Exception {
        List<ImpOrderBody> list = (List<ImpOrderBody>) excelMap.get("ImpOrderBody");
        int count = 1;
        String headOrder_no = impOrderHead.getOrder_No();//ImpOrderHead中的订单号
        String headGuid = impOrderHead.getGuid();//ImpOrderHead的主键
        for (int i = 0, len = list.size(); i < len; i++) {
            ImpOrderBody impOrderBody = list.get(i);
            String listOrder_no = impOrderBody.getOrder_No();
            if (headOrder_no.equals(listOrder_no)) {
                impOrderBody = this.impOrderGoodsListData(impOrderBody, headGuid, user);
                impOrderBody.setG_num(count);//商品序号
                count++;
                this.bondOrderImpMapper.insertImpOrderBody(impOrderBody);//插入EntryList数据
            }
        }
    }

    /*
     * 查询有无重复订单号
     */
    public String getOrderNoCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpOrderHead> list = (List<ImpOrderHead>) excelMap.get("ImpOrderHead");
        for (int i = 0; i < list.size(); i++) {
            ImpOrderHead impOrderHead = list.get(i);
            flag = this.bondOrderImpMapper.isRepeatOrderNo(impOrderHead);
            if (flag > 0) {
                return impOrderHead.getOrder_No();
            }
        }
        return "0";
    }

    /**
     * 表头自生成信息
     */
    private ImpOrderHead impOrderHeadData(String declareTime, ImpOrderHead impOrderHead, Users user, Enterprise enterprise) throws Exception {
        impOrderHead.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impOrderHead.setApp_Type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impOrderHead.setApp_Status("2");//业务状态:1-暂存,2-申报,默认为2。
        impOrderHead.setOrder_Type("I");//电子订单类型：I进口
        impOrderHead.setBuyer_Id_Type("1");//订购人证件类型
        impOrderHead.setCurrency("142");//币制
        impOrderHead.setData_status(StatusCode.BSDDDSB);//数据状态
        impOrderHead.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impOrderHead.setCrt_tm(new Date());//创建时间
        impOrderHead.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impOrderHead.setUpd_tm(new Date());//更新时间
        impOrderHead.setEnt_id(enterprise.getId());
        impOrderHead.setEnt_name(enterprise.getEnt_name());
        impOrderHead.setEnt_customs_code(enterprise.getCustoms_code());
        impOrderHead.setBusiness_type(SystemConstants.T_IMP_BOND_ORDER);
        return impOrderHead;
    }


    /**
     * 表体自生成信息
     */
    private ImpOrderBody impOrderGoodsListData(ImpOrderBody impOrderBody, String headGuid, Users user) throws Exception {
        String brevity_code = user.getBrevity_code();
        String gds_seqno = this.bondOrderImpMapper.queryGdsSeqnoByItemNo(impOrderBody.getItem_No(), brevity_code);

        impOrderBody.setHead_guid(headGuid);//
        impOrderBody.setGds_seqno(StringUtils.isEmpty(gds_seqno) ? "无" : gds_seqno);
        impOrderBody.setCurrency("142");//币制
        impOrderBody.setBar_Code("无");//非必填项，没有必须写“无”
        return impOrderBody;
    }


}
