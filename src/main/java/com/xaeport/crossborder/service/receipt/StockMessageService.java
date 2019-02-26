package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.ImpPayment;
import com.xaeport.crossborder.data.entity.ImpRecOrder;
import com.xaeport.crossborder.data.mapper.StockMessageMapper;
import com.xaeport.crossborder.data.status.ReceiptType;
import com.xaeport.crossborder.data.status.StockMsgType;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockMessageService {
    private final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    StockMessageMapper stockMessageMapper;

    @Transactional(rollbackForClassName = "Exception")
    public boolean createStockData(Map map, String refileName) {
        boolean flag = true;
        try {
            String type = (String) map.get("type");
            Map<String, List<List<Map<String, String>>>> msg = new HashMap<>();
            Map<String, List<Map<String, List<Map<String, String>>>>> stockMsg = new HashMap<>();

            if (type.equals(StockMsgType.CB_DD) || type.equals(StockMsgType.CB_QD)) {
                stockMsg = (Map<String, List<Map<String, List<Map<String, String>>>>>) map.get("Receipt");
            } else {
                msg = (Map<String, List<List<Map<String, String>>>>) map.get("Receipt");
            }

            switch (type) {
                case StockMsgType.CB_DD://跨境订单报文数据
                    this.insertImpOrderData(stockMsg, refileName);
                    break;
                case StockMsgType.CB_ZFD://跨境订单报文数据
                    this.insertImpPaymentData(msg, refileName);
                    break;
            }
        } catch (Exception e) {
            flag = false;
            this.log.error(String.format("入库报文写入失败,文件名为:%s", refileName), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /**
     * 插入订单回执报文数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpOrderData(Map<String, List<Map<String, List<Map<String, String>>>>> stockMsg, String refileName) throws Exception {
        List<Map<String, List<Map<String, String>>>> list = stockMsg.get("ceb:Order");
        if (!StringUtils.isEmpty(list)) {
            ImpOrderHead impOrderHead;
            ImpOrderBody impOrderBody;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            for (int i = 0; i < list.size(); i++) {
                List<Map<String, String>> OrderHeads = list.get(i).get("ceb:OrderHead");
                List<Map<String, String>> OrderLists = list.get(i).get("ceb:OrderList");
                String guid = null;
                for (Map<String, String> orderHead : OrderHeads) {
                    impOrderHead = new ImpOrderHead();
                    guid = orderHead.get("ceb:guid");
                    if (orderHead.containsKey("ceb:guid")) {
                        impOrderHead.setGuid(orderHead.get("ceb:guid"));
                    }
                    if (orderHead.containsKey("ceb:appType")) {
                        impOrderHead.setApp_Type(orderHead.get("ceb:appType"));
                    }
                    if (orderHead.containsKey("ceb:appTime")) {
                        impOrderHead.setApp_Time(sdf.parse(orderHead.get("ceb:appTime)")));
                    }
                    if (orderHead.containsKey("ceb:appStatus")) {
                        impOrderHead.setApp_Status(orderHead.get("ceb:appStatus"));
                    }
                    if (orderHead.containsKey("ceb:orderType")) {
                        impOrderHead.setOrder_Type(orderHead.get("ceb:orderType"));
                    }
                    if (orderHead.containsKey("ceb:orderNo")) {
                        impOrderHead.setOrder_No(orderHead.get("ceb:orderNo"));
                    }
                    if (orderHead.containsKey("ceb:ebpCode")) {
                        impOrderHead.setEbp_Code(orderHead.get("ceb:ebpCode"));
                    }
                    if (orderHead.containsKey("ceb:ebpName")) {
                        impOrderHead.setEbp_Name(orderHead.get("ceb:ebpName"));
                    }
                    if (orderHead.containsKey("ceb:ebcCode")) {
                        impOrderHead.setEbc_Code(orderHead.get("ceb:ebcCode"));
                    }
                    if (orderHead.containsKey("ceb:ebcName")) {
                        impOrderHead.setEbc_Name(orderHead.get("ceb:ebcName"));
                    }
                    if (orderHead.containsKey("ceb:goodsValue")) {
                        impOrderHead.setGoods_Value(orderHead.get("ceb:goodsValue"));
                    }
                    if (orderHead.containsKey("ceb:freight")) {
                        impOrderHead.setFreight(orderHead.get("ceb:freight"));
                    }
                    if (orderHead.containsKey("ceb:discount")) {
                        impOrderHead.setDiscount(orderHead.get("ceb:discount"));
                    }
                    if (orderHead.containsKey("ceb:taxTotal")) {
                        impOrderHead.setTax_Total(orderHead.get("ceb:taxTotal"));
                    }
                    if (orderHead.containsKey("ceb:acturalPaid")) {
                        impOrderHead.setActural_Paid(orderHead.get("ceb:acturalPaid"));
                    }
                    if (orderHead.containsKey("ceb:currency")) {
                        impOrderHead.setCurrency(orderHead.get("ceb:currency"));
                    }
                    if (orderHead.containsKey("ceb:buyerRegNo")) {
                        impOrderHead.setBuyer_Reg_No(orderHead.get("ceb:buyerRegNo"));
                    }
                    if (orderHead.containsKey("ceb:buyerName")) {
                        impOrderHead.setBuyer_Name(orderHead.get("ceb:buyerName"));
                    }
                    if (orderHead.containsKey("ceb:buyerIdType")) {
                        impOrderHead.setBuyer_Id_Type(orderHead.get("ceb:buyerIdType"));
                    }
                    if (orderHead.containsKey("ceb:buyerIdNumber")) {
                        impOrderHead.setBuyer_Id_Number(orderHead.get("ceb:buyerIdNumber"));
                    }
                    if (orderHead.containsKey("ceb:payCode")) {
                        impOrderHead.setPay_Code(orderHead.get("ceb:payCode"));
                    }
                    if (orderHead.containsKey("ceb:payName")) {
                        impOrderHead.setPayName(orderHead.get("ceb:payName"));
                    }
                    if (orderHead.containsKey("ceb:payTransactionId")) {
                        impOrderHead.setPay_Transaction_Id(orderHead.get("ceb:payTransactionId"));
                    }
                    if (orderHead.containsKey("ceb:batchNumbers")) {
                        impOrderHead.setBatch_Numbers(orderHead.get("ceb:batchNumbers"));
                    }
                    if (orderHead.containsKey("ceb:consignee")) {
                        impOrderHead.setConsignee(orderHead.get("ceb:consignee"));
                    }
                    if (orderHead.containsKey("ceb:consigneeTelephone")) {
                        impOrderHead.setConsignee_Telephone(orderHead.get("ceb:consigneeTelephone"));
                    }
                    if (orderHead.containsKey("ceb:consigneeAddress")) {
                        impOrderHead.setConsignee_Address(orderHead.get("ceb:consigneeAddress"));
                    }
                    if (orderHead.containsKey("ceb:consigneeDistrict")) {
                        impOrderHead.setConsignee_Ditrict(orderHead.get("ceb:consigneeDistrict"));
                    }
                    if (orderHead.containsKey("ceb:note")) {
                        impOrderHead.setNote(orderHead.get("ceb:note"));
                    }
                    this.stockMessageMapper.insertImpOrderHead(impOrderHead); //插入订单表头数据
                }
                for (Map<String, String> orderList : OrderLists) {
                    impOrderBody = new ImpOrderBody();
                    impOrderBody.setHead_guid(guid);
                    if (orderList.containsKey("ceb:gnum")) {
                        impOrderBody.setG_num(Integer.valueOf(orderList.get("ceb:gnum")));
                    }
                    if (orderList.containsKey("ceb:itemNo")) {
                        impOrderBody.setItem_No(orderList.get("ceb:itemNo"));
                    }
                    if (orderList.containsKey("ceb:itemName")) {
                        impOrderBody.setItem_Name(orderList.get("ceb:itemName"));
                    }
                    if (orderList.containsKey("ceb:itemDescribe")) {
                        impOrderBody.setItem_Describe(orderList.get("ceb:itemDescribe"));
                    }
                    if (orderList.containsKey("ceb:barCode")) {
                        impOrderBody.setBar_Code(orderList.get("ceb:barCode"));
                    }
                    if (orderList.containsKey("ceb:unit")) {
                        impOrderBody.setUnit(orderList.get("ceb:unit"));
                    }
                    if (orderList.containsKey("ceb:qty")) {
                        impOrderBody.setQty(orderList.get("ceb:qty"));
                    }
                    if (orderList.containsKey("ceb:price")) {
                        impOrderBody.setPrice(orderList.get("ceb:price"));
                    }
                    if (orderList.containsKey("ceb:totalPrice")) {
                        impOrderBody.setTotal_Price(orderList.get("ceb:totalPrice"));
                    }
                    if (orderList.containsKey("ceb:currency")) {
                        impOrderBody.setCurrency(orderList.get("ceb:currency"));
                    }
                    if (orderList.containsKey("ceb:country")) {
                        impOrderBody.setCountry(orderList.get("ceb:country"));
                    }
                    if (orderList.containsKey("ceb:note")) {
                        impOrderBody.setNote(orderList.get("ceb:note"));
                    }
                    this.stockMessageMapper.insertImpOrderBody(impOrderBody); //插入订单表体数据
                }
            }
        }
    }

    /**
     * 插入订单回执报文数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpPaymentData(Map<String, List<List<Map<String, String>>>> msg, String refileName) throws Exception {
        List<List<Map<String, String>>> list = msg.get("PaymentHead");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        if (!StringUtils.isEmpty(list)) {
            ImpPayment impPayment;
            for (int i = 0; i < list.size(); i++) {
                impPayment = new ImpPayment();
                impPayment.setCrt_tm(new Date());
                impPayment.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impPayment.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("appType")) {
                        impPayment.setApp_type(map.get("appType"));
                    }
                    if (map.containsKey("appTime")) {
                        impPayment.setApp_time(sdf.parse(map.get("appTime")));
                    }
                    if (map.containsKey("appStatus")) {
                        impPayment.setApp_status(map.get("appStatus"));
                    }
                    if (map.containsKey("payCode")) {
                        impPayment.setPay_code(map.get("payCode"));
                    }
                    if (map.containsKey("payName")) {
                        impPayment.setPay_name(map.get("payName"));
                    }
                    if (map.containsKey("payTransactionId")) {
                        impPayment.setPay_transaction_id(map.get("payTransactionId"));
                    }
                    if (map.containsKey("orderNo")) {
                        impPayment.setOrder_no(map.get("orderNo"));
                    }
                    if (map.containsKey("ebpCode")) {
                        impPayment.setEbp_code(map.get("ebpCode"));
                    }
                    if (map.containsKey("ebpName")) {
                        impPayment.setEbp_name(map.get("ebpName"));
                    }
                    if (map.containsKey("payerIdType")) {
                        impPayment.setPayer_id_type(map.get("payerIdType"));
                    }
                    if (map.containsKey("payerIdNumber")) {
                        impPayment.setPayer_id_number(map.get("payerIdNumber"));
                    }
                    if (map.containsKey("payerName")) {
                        impPayment.setPayer_name(map.get("payerName"));
                    }
                    if (map.containsKey("telephone")) {
                        impPayment.setTelephone(map.get("telephone"));
                    }
                    if (map.containsKey("amountPaid")) {
                        impPayment.setAmount_paid(map.get("amountPaid"));
                    }
                    if (map.containsKey("currency")) {
                        impPayment.setCurrency(map.get("currency"));
                    }
                    if (map.containsKey("payTime")) {
                        impPayment.setPay_time(sdf.parse(map.get("payTime")));
                    }
                    if (map.containsKey("note")) {
                        impPayment.setNote(map.get("note"));
                    }
                }
                this.stockMessageMapper.insertImpPayment(impPayment); //插入订单状态表数据
            }
        }
    }

}
