package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.Enterprise;
import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.DetailImportMapper;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DetailImportService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    DetailImportMapper detailImportMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    /*
     * 清单导入
     * @param importTime 申报时间
     */
    @Transactional
    public int createDetailForm(Map<String, Object> excelMap, String importTime, Users user) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpInventoryHead(excelMap, importTime, user);
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
    private int createImpInventoryHead(Map<String, Object> excelMap, String importTime, Users user) throws Exception {
        int flag = 0;
        Enterprise enterprise = enterpriseMapper.getEnterpriseDetail(user.getEnt_Id());
        List<ImpInventoryHead> impInventoryHeadList = (List<ImpInventoryHead>) excelMap.get("ImpInventoryHead");
        for (ImpInventoryHead anImpInventoryHeadList : impInventoryHeadList) {
            ImpInventoryHead impInventoryHead = this.impInventoryHeadData(importTime, anImpInventoryHeadList, user,enterprise);
            flag = this.detailImportMapper.isRepeatOrderNo(impInventoryHead);
            if (flag > 0) {
                return 0;
            }
            this.detailImportMapper.insertImpInventoryHead(impInventoryHead);//查询无订单号则插入ImpInventoryHead数据
            this.createImpInventoryList(excelMap, impInventoryHead, user);//插入ImpInventoryList
        }
        return flag;
    }


    /*
     * 创建ImpOrderList信息
     */
    private void createImpInventoryList(Map<String, Object> excelMap, ImpInventoryHead impInventoryHead, Users user) throws Exception {
        List<ImpInventoryBody> list = (List<ImpInventoryBody>) excelMap.get("ImpInventoryBody");
        int count = 1;
        String headOrder_no = impInventoryHead.getOrder_no();//ImpOrderHead中的订单号
        String headGuid = impInventoryHead.getGuid();//ImpOrderHead的主键
        for (int i = 0, len = list.size(); i < len; i++) {
            ImpInventoryBody impInventoryBody = list.get(i);
            String listOrder_no = impInventoryBody.getOrder_no();
            if (headOrder_no.equals(listOrder_no)) {
                impInventoryBody = this.impInventoryBodyData(impInventoryBody, headGuid, user);
                impInventoryBody.setG_num(count);//商品序号
                count++;
                this.detailImportMapper.insertImpInventoryBody(impInventoryBody);//插入EntryList数据

            }
        }
    }

    /*
     * 查询有无重复订单号
     */
    public int getOrderNoCount(Map<String, Object> excelMap) throws Exception {
        int flag = 0;
        List<ImpInventoryHead> list = (List<ImpInventoryHead>) excelMap.get("ImpInventoryHead");
        for (int i = 0; i < list.size(); i++) {
            ImpInventoryHead impInventoryHead = list.get(i);
            flag = this.detailImportMapper.isRepeatOrderNo(impInventoryHead);
            if (flag > 0) {
                return 1;
            }
        }
        return flag;
    }

    /**
     * 表头自生成信息
     */
    private ImpInventoryHead impInventoryHeadData(String importTime, ImpInventoryHead impInventoryHead, Users user,Enterprise enterprise) throws Exception {
        impInventoryHead.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impInventoryHead.setCop_no(enterprise.getCustoms_code() + IdUtils.getShortUUId().substring(0,10));
        impInventoryHead.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impInventoryHead.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2。
        impInventoryHead.setIe_flag("I");//电子订单类型：I进口
        impInventoryHead.setBuyer_id_type("1");//订购人证件类型
        impInventoryHead.setTrade_mode("9610");//贸易方式
        impInventoryHead.setCurrency("142");//币制
        impInventoryHead.setPack_no("1");//件数
        impInventoryHead.setIe_date(DateTools.shortDateTimeString(importTime));
        impInventoryHead.setData_status(StatusCode.EXPORT);//数据状态
        impInventoryHead.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impInventoryHead.setCrt_tm(new Date());//创建时间
        impInventoryHead.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impInventoryHead.setUpd_tm(new Date());//更新时间
        impInventoryHead.setEnt_id(enterprise.getId());
        impInventoryHead.setEnt_name(enterprise.getEnt_name());
        impInventoryHead.setEnt_customs_code(enterprise.getCustoms_code());
        return impInventoryHead;
    }


    /**
     * 表体自生成信息
     */
    private ImpInventoryBody impInventoryBodyData(ImpInventoryBody impInventoryBody, String headGuid, Users user) throws Exception {
        impInventoryBody.setHead_guid(headGuid);//
        impInventoryBody.setCurrency("142");//币制
        impInventoryBody.setBar_code("无");//非必填项，没有必须写“无”
        return impInventoryBody;
    }


}
