package com.xaeport.crossborder.service.ordermanage;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.data.entity.ImpOrderGoodsList;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Users;
import com.xaeport.crossborder.data.mapper.OrderImportMapper;
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
    public int createOrderForm(Map<String, Object> excelMap , String importTime , Users user) {
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

    private int createImpOrderHead(Map<String, Object> excelMap, String declareTime, Users user) throws Exception {
        int flag = 0;
        List<ImpOrderHead> eIntlMailList = (List<ImpOrderHead>) excelMap.get("ImpOrderHead");
        for (ImpOrderHead anEIntlMailList : eIntlMailList) {
            ImpOrderHead impOrderHead = this.impOrderHeadData(declareTime, anEIntlMailList, user);
            this.orderImportMapper.insertImpOrderHead(impOrderHead);//插入EntryHead数据
            this.createImpOrderList(excelMap, impOrderHead, user);
        }
        return flag;
    }

    private void createImpOrderList(Map<String, Object> excelMap, ImpOrderHead impOrderHead, Users user) throws Exception {
        List<ImpOrderGoodsList> list = (List<ImpOrderGoodsList>) excelMap.get("ImpOrderList");
        int count = 1;
        String headOrder_no = impOrderHead.getOrder_No();//EntryHead中的分单号
        String headGuid = impOrderHead.getGuid();//EntryHead的主键
        for (int i = 0, len = list.size(); i < len; i++) {
            ImpOrderGoodsList impOrderGoodsList = list.get(i);
            String listOrder_no = impOrderGoodsList.getOrder_No();
            if (headOrder_no.equals(listOrder_no)) {
                impOrderGoodsList = this.entryListData(impOrderGoodsList, headGuid, user);
                impOrderGoodsList.setG_num(count);//商品序号
                count++;
                this.orderImportMapper.insertImpOrderGoodsList(impOrderGoodsList);//插入EntryList数据
                //this.createEntryDocu(excelMap, entryList, i);
            }
        }
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
        impOrderHead.setCurrency("142");
        impOrderHead.setCrt_id(StringUtils.isEmpty(user.getId())?"":user.getId());//创建人
        impOrderHead.setCrt_tm(new Date());//创建时间
        impOrderHead.setUpd_id(StringUtils.isEmpty(user.getId())?"":user.getId());//更新人
        impOrderHead.setUpd_tm(new Date());//更新时间

        return impOrderHead;
    }


    /**
     * 表体自生成信息
     */
    private ImpOrderGoodsList entryListData(ImpOrderGoodsList impOrderGoodsList, String headGuid, Users currentUsers) throws Exception {
        impOrderGoodsList.setHead_guid(headGuid);//分单头信息ID
        impOrderGoodsList.setOrder_No(IdUtils.getUUId());//主键ID
        impOrderGoodsList.setCurrency("142");//币制
        return impOrderGoodsList;
    }






}
