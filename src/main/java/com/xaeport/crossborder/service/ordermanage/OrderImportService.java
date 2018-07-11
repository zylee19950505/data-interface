package com.xaeport.crossborder.service.ordermanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.OrderImportMapper;
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
public class OrderImportService {
    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    AppConfiguration appConfiguration;
    @Autowired
    OrderImportMapper orderImportMapper;

    /*
     * 订单导入
     * @param importTime 申报时间
     */
    @Transactional
    public int createOrderForm(Map<String, Object> excelMap, String importTime, Users user) {
        int flag;
        try {
            String id = user.getId();
            flag = this.createImpOrderHead(excelMap, importTime, user);
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
    private int createImpOrderHead(Map<String, Object> excelMap, String importTime, Users user) throws Exception {
        int flag = 0;
        List<ImpOrderHead> impOrderHeadList = (List<ImpOrderHead>) excelMap.get("ImpOrderHead");
        for (ImpOrderHead anImpOrderHeadList : impOrderHeadList) {
            ImpOrderHead impOrderHead = this.impOrderHeadData(importTime, anImpOrderHeadList, user);
            flag = this.orderImportMapper.isRepeatOrderNo(impOrderHead);
            if (flag > 0) {
                return 0;
            }
            this.orderImportMapper.insertImpOrderHead(impOrderHead);//查询无订单号则插入ImpOrderHead数据
            this.createImpOrderList(excelMap, impOrderHead, user);//插入ImpOrderGoodsList
        }
        return flag;
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
                this.orderImportMapper.insertImpOrderBody(impOrderBody);//插入EntryList数据
//                Integer gNum = orderImportMapper.queryMaxG_num(listOrder_no);
//                int g_num = StringUtils.isEmpty(gNum) ? 0 : gNum;
//                if (g_num == 0) {
//                    impOrderBody.setG_num(count);//商品序号
//                } else if (g_num != 0) {
//                    impOrderBody.setG_num(g_num + 1);//商品序号
//                }
//                String HeadGuid = this.orderImportMapper.queryHeadGuidByOrderNo(listOrder_no);

            }
        }
    }

    /*
     * 查询有无重复订单号
     */
    public int getOrderNoCount(Map<String, Object> excelMap) throws Exception{
        int flag = 0;
        List<ImpOrderHead> list = (List<ImpOrderHead>) excelMap.get("ImpOrderHead");
        for(int i=0;i<list.size();i++){
            ImpOrderHead impOrderHead = list.get(i);
            flag = this.orderImportMapper.isRepeatOrderNo(impOrderHead);
            if (flag > 0) {
                return 1;
            }
        }
        return flag;
    }

    /**
     * 表头自生成信息
     */
    private ImpOrderHead impOrderHeadData(String declareTime, ImpOrderHead impOrderHead, Users user) throws Exception {
        impOrderHead.setGuid(IdUtils.getUUId());//企业系统生成36 位唯一序号（英文字母大写）
        impOrderHead.setApp_Type("1");//企业报送类型。1-新增2-变更3-删除。默认为1。
        impOrderHead.setApp_Time("");//企业报送时间。格式:YYYYMMDDhhmmss。
        impOrderHead.setApp_Status("2");//业务状态:1-暂存,2-申报,默认为2。
        impOrderHead.setOrder_Type("I");//电子订单类型：I进口
        impOrderHead.setBuyer_Id_Type("1");//订购人证件类型
        impOrderHead.setCurrency("142");//币制
        impOrderHead.setCrt_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//创建人
        impOrderHead.setCrt_tm(new Date());//创建时间
        impOrderHead.setUpd_id(StringUtils.isEmpty(user.getId()) ? "" : user.getId());//更新人
        impOrderHead.setUpd_tm(new Date());//更新时间
        return impOrderHead;
    }


    /**
     * 表体自生成信息
     */
    private ImpOrderBody impOrderGoodsListData(ImpOrderBody impOrderBody, String headGuid, Users user) throws Exception {
        impOrderBody.setHead_guid(headGuid);//
        impOrderBody.setCurrency("142");//币制
        return impOrderBody;
    }


}
