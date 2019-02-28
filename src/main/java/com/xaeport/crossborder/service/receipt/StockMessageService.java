package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.data.entity.*;
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

import java.text.ParseException;
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

            if (type.equals(StockMsgType.CB_DD) || type.equals(StockMsgType.CB_QD) || type.equals(StockMsgType.CB_RKMXD)) {
                stockMsg = (Map<String, List<Map<String, List<Map<String, String>>>>>) map.get("Receipt");
            } else {
                msg = (Map<String, List<List<Map<String, String>>>>) map.get("Receipt");
            }

            switch (type) {
                case StockMsgType.CB_DD://跨境订单报文数据
                    this.insertImpOrderData(stockMsg, refileName);
                    break;
                case StockMsgType.CB_QD://跨境清单报文数据
                    this.insertImpInventoryData(stockMsg, refileName);
                    break;
                case StockMsgType.CB_RKMXD://跨境入库明细单报文数据
                    this.insertImpDeliveryData(stockMsg, refileName);
                    break;

                case StockMsgType.CB_ZFD://跨境支付单报文数据
                    this.insertImpPaymentData(msg, refileName);
                    break;
                case StockMsgType.CB_MF://跨境核放单报文数据
                    this.insertManifestData(msg, refileName);
                    break;
                /*case StockMsgType.BD_HZQD://保税核注清单报文数据
                    this.insertBondinvtData(msg, refileName);
                    break;*/
                case StockMsgType.CB_YD://跨境运单报文数据
                    this.insertImpLogistics(msg, refileName);
                    break;

                case StockMsgType.CB_YDZT://跨境运单状态报文数据
                    this.insertImpLogisticsStatus(msg, refileName);
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
     * 插入保税核注清单报文数据(暂时不做)
     * */
   /* private void insertBondinvtData(Map<String, List<List<Map<String, String>>>> msg, String refileName) {


    }*/


    /**
     * 插入跨境核放单报文数据
     * */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertManifestData(Map<String, List<List<Map<String, String>>>> msg, String refileName) throws Exception {
        List<List<Map<String, String>>> manifestHeadlist = msg.get("MANIFEST_HEAD");
        if (!StringUtils.isEmpty(manifestHeadlist)){
            ManifestHead manifestHead;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            for (int i = 0; i < manifestHeadlist.size(); i++) {
                //List<Map<String, String>> manifestHeads = manifestHeadlist.get(i).get("MANIFEST_HEAD");
                List<Map<String, String>> manifestHeads = manifestHeadlist.get(i);
                manifestHead = new ManifestHead();
                for (Map<String, String> manifestHeadMap:manifestHeads) {
                    if (manifestHeadMap.containsKey("AUTO_ID")){
                        manifestHead.setAuto_id(manifestHeadMap.get("AUTO_ID"));
                    }
                    if (manifestHeadMap.containsKey("MANIFEST_NO")){
                        manifestHead.setManifest_no(manifestHeadMap.get("MANIFEST_NO"));
                    }
                    if (manifestHeadMap.containsKey("BIZ_TYPE")){
                        manifestHead.setBiz_type(manifestHeadMap.get("BIZ_TYPE"));
                    }
                    if (manifestHeadMap.containsKey("BIZ_MODE")){
                        manifestHead.setBiz_mode(manifestHeadMap.get("BIZ_MODE"));
                    }
                    if (manifestHeadMap.containsKey("I_E_FLAG")){
                        manifestHead.setI_e_flag(manifestHeadMap.get("I_E_FLAG"));
                    }
                    if (manifestHeadMap.containsKey("I_E_MARK")){
                        manifestHead.setI_e_mark(manifestHeadMap.get("I_E_MARK"));
                    }
                    if (manifestHeadMap.containsKey("START_LAND")){
                        manifestHead.setStart_land(manifestHeadMap.get("START_LAND"));
                    }
                    if (manifestHeadMap.containsKey("GOAL_LAND")){
                        manifestHead.setGoal_land(manifestHeadMap.get("GOAL_LAND"));
                    }
                    if (manifestHeadMap.containsKey("CAR_NO")){
                        manifestHead.setCar_no(manifestHeadMap.get("CAR_NO"));
                    }
                    if (manifestHeadMap.containsKey("CAR_WT")){
                        manifestHead.setCar_wt(manifestHeadMap.get("CAR_WT"));
                    }
                    if (manifestHeadMap.containsKey("IC_CODE")){
                        manifestHead.setIc_code(manifestHeadMap.get("IC_CODE"));
                    }
                    if (manifestHeadMap.containsKey("GOODS_WT")){
                        manifestHead.setGoods_wt(manifestHeadMap.get("GOODS_WT"));
                    }
                    if (manifestHeadMap.containsKey("FACT_WEIGHT")){
                        manifestHead.setFact_weight(manifestHeadMap.get("FACT_WEIGHT"));
                    }
                    if (manifestHeadMap.containsKey("PACK_NO")){
                        manifestHead.setPack_no(manifestHeadMap.get("PACK_NO"));
                    }
                    if (manifestHeadMap.containsKey("M_STATUS")){
                        manifestHead.setM_status(manifestHeadMap.get("M_STATUS"));
                    }
                    if (manifestHeadMap.containsKey("B_STATUS")){
                        manifestHead.setB_status(manifestHeadMap.get("B_STATUS"));
                    }
                    if (manifestHeadMap.containsKey("STATUS")){
                        manifestHead.setStatus(manifestHeadMap.get("STATUS"));
                    }
                    if (manifestHeadMap.containsKey("PORT_STATUS")){
                        manifestHead.setPort_status(manifestHeadMap.get("PORT_STATUS"));
                    }
                    if (manifestHeadMap.containsKey("APP_PERSON")){
                        manifestHead.setApp_person(manifestHeadMap.get("APP_PERSON"));
                    }
                    if (manifestHeadMap.containsKey("APP_DATE")){
                        manifestHead.setApp_date(sdf.parse(manifestHeadMap.get("APP_DATE")));
                    }
                    if (manifestHeadMap.containsKey("INPUT_CODE")){
                        manifestHead.setInput_code(manifestHeadMap.get("INPUT_CODE"));
                    }
                    if (manifestHeadMap.containsKey("INPUT_NAME")){
                        manifestHead.setInput_name(manifestHeadMap.get("INPUT_NAME"));
                    }
                    if (manifestHeadMap.containsKey("TRADE_CODE")){
                        manifestHead.setTrade_code(manifestHeadMap.get("TRADE_CODE"));
                    }
                    if (manifestHeadMap.containsKey("TRADE_NAME")){
                        manifestHead.setTrade_name(manifestHeadMap.get("TRADE_NAME"));
                    }
                    if (manifestHeadMap.containsKey("REGION_CODE")){
                        manifestHead.setRegion_code(manifestHeadMap.get("REGION_CODE"));
                    }
                    if (manifestHeadMap.containsKey("CUSTOMS_CODE")){
                        manifestHead.setCustoms_code(manifestHeadMap.get("CUSTOMS_CODE"));
                    }
                    if (manifestHeadMap.containsKey("NOTE")){
                        manifestHead.setNote(manifestHeadMap.get("NOTE"));
                    }
                    if (manifestHeadMap.containsKey("EXTEND_FIELD_3")){
                        manifestHead.setExtend_field_3(manifestHeadMap.get("EXTEND_FIELD_3"));
                    }
                    if (manifestHeadMap.containsKey("PLATFROM")){
                        manifestHead.setPlat_from(manifestHeadMap.get("PLATFROM"));
                    }
                }
                //查询数据库里是否存在此单
                int count = this.stockMessageMapper.queryManifestData(manifestHead);
                if (count>0){
                    //修改数据
                    this.stockMessageMapper.updateManifestData(manifestHead);
                }else{
                    //插入数据
                    manifestHead.setWriting_mode("stock");
                    this.stockMessageMapper.insertManifestData(manifestHead); //插入跨境直购核放单表头
                }
            }
        }
    }




    /**
     * 插入订单报文数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpOrderData(Map<String, List<Map<String, List<Map<String, String>>>>> stockMsg, String refileName) throws Exception {
        List<Map<String, List<Map<String, String>>>> list = stockMsg.get("Order");
        String guid = null;
        if (!StringUtils.isEmpty(list)) {
            ImpOrderHead impOrderHead;
            ImpOrderBody impOrderBody;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            for (int i = 0; i < list.size(); i++) {
                List<Map<String, String>> OrderHeads = list.get(i).get("OrderHead");
                List<Map<String, String>> OrderLists = list.get(i).get("OrderList");
                impOrderHead = new ImpOrderHead();
                impOrderBody = new ImpOrderBody();

                if (!StringUtils.isEmpty(OrderHeads)) {
                    guid = OrderHeads.get(0).get("guid");
                }

                if (!StringUtils.isEmpty(OrderHeads)) {
                    for (Map<String, String> orderHead : OrderHeads) {
                        if (orderHead.containsKey("guid")) {
                            impOrderHead.setGuid(orderHead.get("guid"));
                        }
                        if (orderHead.containsKey("appType")) {
                            impOrderHead.setApp_Type(orderHead.get("appType"));
                        }
                        if (orderHead.containsKey("appTime")) {
                            impOrderHead.setApp_Time(sdf.parse(orderHead.get("appTime")));
                        }
                        if (orderHead.containsKey("appStatus")) {
                            impOrderHead.setApp_Status(orderHead.get("appStatus"));
                        }
                        if (orderHead.containsKey("orderType")) {
                            impOrderHead.setOrder_Type(orderHead.get("orderType"));
                        }
                        if (orderHead.containsKey("orderNo")) {
                            impOrderHead.setOrder_No(orderHead.get("orderNo"));
                        }
                        if (orderHead.containsKey("ebpCode")) {
                            impOrderHead.setEbp_Code(orderHead.get("ebpCode"));
                        }
                        if (orderHead.containsKey("ebpName")) {
                            impOrderHead.setEbp_Name(orderHead.get("ebpName"));
                        }
                        if (orderHead.containsKey("ebcCode")) {
                            impOrderHead.setEbc_Code(orderHead.get("ebcCode"));
                        }
                        if (orderHead.containsKey("ebcName")) {
                            impOrderHead.setEbc_Name(orderHead.get("ebcName"));
                        }
                        if (orderHead.containsKey("goodsValue")) {
                            impOrderHead.setGoods_Value(orderHead.get("goodsValue"));
                        }
                        if (orderHead.containsKey("freight")) {
                            impOrderHead.setFreight(orderHead.get("freight"));
                        }
                        if (orderHead.containsKey("discount")) {
                            impOrderHead.setDiscount(orderHead.get("discount"));
                        }
                        if (orderHead.containsKey("taxTotal")) {
                            impOrderHead.setTax_Total(orderHead.get("taxTotal"));
                        }
                        if (orderHead.containsKey("acturalPaid")) {
                            impOrderHead.setActural_Paid(orderHead.get("acturalPaid"));
                        }
                        if (orderHead.containsKey("currency")) {
                            impOrderHead.setCurrency(orderHead.get("currency"));
                        }
                        if (orderHead.containsKey("buyerRegNo")) {
                            impOrderHead.setBuyer_Reg_No(orderHead.get("buyerRegNo"));
                        }
                        if (orderHead.containsKey("buyerName")) {
                            impOrderHead.setBuyer_Name(orderHead.get("buyerName"));
                        }
                        if (orderHead.containsKey("buyerIdType")) {
                            impOrderHead.setBuyer_Id_Type(orderHead.get("buyerIdType"));
                        }
                        if (orderHead.containsKey("buyerIdNumber")) {
                            impOrderHead.setBuyer_Id_Number(orderHead.get("buyerIdNumber"));
                        }
                        if (orderHead.containsKey("payCode")) {
                            impOrderHead.setPay_Code(orderHead.get("payCode"));
                        }
                        if (orderHead.containsKey("payName")) {
                            impOrderHead.setPayName(orderHead.get("payName"));
                        }
                        if (orderHead.containsKey("payTransactionId")) {
                            impOrderHead.setPay_Transaction_Id(orderHead.get("payTransactionId"));
                        }
                        if (orderHead.containsKey("batchNumbers")) {
                            impOrderHead.setBatch_Numbers(orderHead.get("batchNumbers"));
                        }
                        if (orderHead.containsKey("consignee")) {
                            impOrderHead.setConsignee(orderHead.get("consignee"));
                        }
                        if (orderHead.containsKey("consigneeTelephone")) {
                            impOrderHead.setConsignee_Telephone(orderHead.get("consigneeTelephone"));
                        }
                        if (orderHead.containsKey("consigneeAddress")) {
                            impOrderHead.setConsignee_Address(orderHead.get("consigneeAddress"));
                        }
                        if (orderHead.containsKey("consigneeDistrict")) {
                            impOrderHead.setConsignee_Ditrict(orderHead.get("consigneeDistrict"));
                        }
                        if (orderHead.containsKey("note")) {
                            impOrderHead.setNote(orderHead.get("note"));
                        }
                    }
                    this.stockMessageMapper.insertImpOrderHead(impOrderHead); //插入订单表头数据
                }

                if (!StringUtils.isEmpty(OrderLists)) {
                    for (Map<String, String> orderList : OrderLists) {
                        impOrderBody.setHead_guid(guid);
                        if (orderList.containsKey("gnum")) {
                            impOrderBody.setG_num(Integer.valueOf(orderList.get("gnum")));
                        }
                        if (orderList.containsKey("itemNo")) {
                            impOrderBody.setItem_No(orderList.get("itemNo"));
                        }
                        if (orderList.containsKey("itemName")) {
                            impOrderBody.setItem_Name(orderList.get("itemName"));
                        }
                        if (orderList.containsKey("itemDescribe")) {
                            impOrderBody.setItem_Describe(orderList.get("itemDescribe"));
                        }
                        if (orderList.containsKey("barCode")) {
                            impOrderBody.setBar_Code(orderList.get("barCode"));
                        }
                        if (orderList.containsKey("unit")) {
                            impOrderBody.setUnit(orderList.get("unit"));
                        }
                        if (orderList.containsKey("qty")) {
                            impOrderBody.setQty(orderList.get("qty"));
                        }
                        if (orderList.containsKey("price")) {
                            impOrderBody.setPrice(orderList.get("price"));
                        }
                        if (orderList.containsKey("totalPrice")) {
                            impOrderBody.setTotal_Price(orderList.get("totalPrice"));
                        }
                        if (orderList.containsKey("currency")) {
                            impOrderBody.setCurrency(orderList.get("currency"));
                        }
                        if (orderList.containsKey("country")) {
                            impOrderBody.setCountry(orderList.get("country"));
                        }
                        if (orderList.containsKey("note")) {
                            impOrderBody.setNote(orderList.get("note"));
                        }
                    }
                    this.stockMessageMapper.insertImpOrderBody(impOrderBody); //插入订单表体数据
                }
            }
        }
    }

    /**
     * 插入支付单报文数据（进口跨境直购）
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

    /**
     * 插入运单报文数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpLogistics(Map<String, List<List<Map<String, String>>>> msg, String refileName) throws Exception {
        List<List<Map<String, String>>> list = msg.get("LogisticsHead");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        if (!StringUtils.isEmpty(list)) {
            ImpLogistics impLogistics;
            for (int i = 0; i < list.size(); i++) {
                impLogistics = new ImpLogistics();
                impLogistics.setCrt_tm(new Date());
                impLogistics.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impLogistics.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("appType")) {
                        impLogistics.setApp_type(map.get("appType"));
                    }
                    if (map.containsKey("appTime")) {
                        impLogistics.setApp_time(sdf.parse(map.get("appTime")));
                    }
                    if (map.containsKey("appStatus")) {
                        impLogistics.setApp_status(map.get("appStatus"));
                    }
                    if (map.containsKey("logisticsCode")) {
                        impLogistics.setLogistics_code(map.get("logisticsCode"));
                    }
                    if (map.containsKey("logisticsName")) {
                        impLogistics.setLogistics_name(map.get("logisticsName"));
                    }
                    if (map.containsKey("logisticsNo")) {
                        impLogistics.setLogistics_code(map.get("logisticsNo"));
                    }
                    if (map.containsKey("billNo")) {
                        impLogistics.setBill_no(map.get("billNo"));
                    }
                    if (map.containsKey("freight")) {
                        impLogistics.setFreight(map.get("freight"));
                    }
                    if (map.containsKey("insuredFee")) {
                        impLogistics.setInsured_fee(map.get("insuredFee"));
                    }
                    if (map.containsKey("currency")) {
                        impLogistics.setCurrency(map.get("currency"));
                    }
                    if (map.containsKey("weight")) {
                        impLogistics.setWeight(map.get("weight"));
                    }
                    if (map.containsKey("packNo")) {
                        impLogistics.setPack_no(map.get("packNo"));
                    }
                    if (map.containsKey("goodsInfo")) {
                        impLogistics.setGoods_info(map.get("goodsInfo"));
                    }
                    if (map.containsKey("consignee")) {
                        impLogistics.setConsingee(map.get("consignee"));
                    }
                    if (map.containsKey("consigneeAddress")) {
                        impLogistics.setConsignee_address(map.get("consigneeAddress"));
                    }
                    if (map.containsKey("consigneeTelephone")) {
                        impLogistics.setConsignee_telephone(map.get("consigneeTelephone"));
                    }
                    if (map.containsKey("note")) {
                        impLogistics.setNote(map.get("note"));
                    }
                }
                this.stockMessageMapper.insertImpLogistics(impLogistics); //插入订单状态表数据
            }
        }
    }

    /**
     * 插入运单状态数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpLogisticsStatus(Map<String, List<List<Map<String, String>>>> msg, String refileName) throws Exception {
        List<List<Map<String, String>>> list = msg.get("LogisticsStatus");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        if (!StringUtils.isEmpty(list)) {
            ImpLogisticsStatus impLogisticsStatus;
            for (int i = 0; i < list.size(); i++) {

                impLogisticsStatus = new ImpLogisticsStatus();
                impLogisticsStatus.setCrt_tm(new Date());
                impLogisticsStatus.setUpd_tm(new Date());
                List<Map<String, String>> mapList = list.get(i);

                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impLogisticsStatus.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("appType")) {
                        impLogisticsStatus.setApp_type(map.get("appType"));
                    }
                    if (map.containsKey("appTime")) {
                        impLogisticsStatus.setApp_time(sdf.parse(map.get("appTime")));
                    }
                    if (map.containsKey("appStatus")) {
                        impLogisticsStatus.setApp_status(map.get("appStatus"));
                    }
                    if (map.containsKey("logisticsCode")) {
                        impLogisticsStatus.setLogistics_code(map.get("logisticsCode"));
                    }
                    if (map.containsKey("logisticsName")) {
                        impLogisticsStatus.setLogistics_name(map.get("logisticsName"));
                    }
                    if (map.containsKey("logisticsNo")) {
                        impLogisticsStatus.setLogistics_no(map.get("logisticsNo"));
                    }
                    if (map.containsKey("logisticsStatus")) {
                        impLogisticsStatus.setLogistics_status(map.get("logisticsStatus"));
                    }
                    if (map.containsKey("logisticsTime")) {
                        impLogisticsStatus.setLogistics_time(sdf.parse(map.get("logisticsTime")));
                    }
                    if (map.containsKey("note")) {
                        impLogisticsStatus.setNote(map.get("note"));
                    }
                }
                this.stockMessageMapper.insertImpLogisticsStatus(impLogisticsStatus); //插入订单状态表数据
            }
        }
    }

    /**
     * 插入清单报文数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpInventoryData(Map<String, List<Map<String, List<Map<String, String>>>>> stockMsg, String refileName) throws Exception {
        List<Map<String, List<Map<String, String>>>> list = stockMsg.get("Inventory");
        String guid = null;
        if (!StringUtils.isEmpty(list)) {

            ImpInventoryHead impInventoryHead;
            ImpInventoryBody impInventoryBody;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat shortSdf = new SimpleDateFormat("yyyyMMdd");

            for (int i = 0; i < list.size(); i++) {
                List<Map<String, String>> InventoryHeads = list.get(i).get("InventoryHead");
                List<Map<String, String>> InventoryLists = list.get(i).get("InventoryList");
                impInventoryHead = new ImpInventoryHead();
                impInventoryBody = new ImpInventoryBody();
                impInventoryHead.setCrt_tm(new Date());
                impInventoryHead.setUpd_tm(new Date());

                if (!StringUtils.isEmpty(InventoryHeads)) {
                    guid = InventoryHeads.get(0).get("guid");
                }

                if (!StringUtils.isEmpty(InventoryHeads)) {
                    for (Map<String, String> inventoryHead : InventoryHeads) {
                        if (inventoryHead.containsKey("guid")) {
                            impInventoryHead.setGuid(inventoryHead.get("guid"));
                        }
                        if (inventoryHead.containsKey("appType")) {
                            impInventoryHead.setApp_type(inventoryHead.get("appType"));
                        }
                        if (inventoryHead.containsKey("appTime")) {
                            impInventoryHead.setApp_time(sdf.parse(inventoryHead.get("appTime")));
                        }
                        if (inventoryHead.containsKey("appStatus")) {
                            impInventoryHead.setApp_status(inventoryHead.get("appStatus"));
                        }
                        if (inventoryHead.containsKey("orderNo")) {
                            impInventoryHead.setOrder_no(inventoryHead.get("orderNo"));
                        }
                        if (inventoryHead.containsKey("ebpCode")) {
                            impInventoryHead.setEbp_code(inventoryHead.get("ebpCode"));
                        }
                        if (inventoryHead.containsKey("ebpName")) {
                            impInventoryHead.setEbp_name(inventoryHead.get("ebpName"));
                        }
                        if (inventoryHead.containsKey("ebcCode")) {
                            impInventoryHead.setEbc_code(inventoryHead.get("ebcCode"));
                        }
                        if (inventoryHead.containsKey("ebcName")) {
                            impInventoryHead.setEbc_name(inventoryHead.get("ebcName"));
                        }
                        if (inventoryHead.containsKey("logisticsNo")) {
                            impInventoryHead.setLogistics_no(inventoryHead.get("logisticsNo"));
                        }
                        if (inventoryHead.containsKey("logisticsCode")) {
                            impInventoryHead.setLogistics_code(inventoryHead.get("logisticsCode"));
                        }
                        if (inventoryHead.containsKey("logisticsName")) {
                            impInventoryHead.setLogistics_name(inventoryHead.get("logisticsName"));
                        }
                        if (inventoryHead.containsKey("copNo")) {
                            impInventoryHead.setCop_no(inventoryHead.get("copNo"));
                        }
                        if (inventoryHead.containsKey("preNo")) {
                            impInventoryHead.setPre_no(inventoryHead.get("preNo"));
                        }
                        if (inventoryHead.containsKey("assureCode")) {
                            impInventoryHead.setAssure_code(inventoryHead.get("assureCode"));
                        }
                        if (inventoryHead.containsKey("emsNo")) {
                            impInventoryHead.setEms_no(inventoryHead.get("emsNo"));
                        }
                        if (inventoryHead.containsKey("invtNo")) {
                            impInventoryHead.setInvt_no(inventoryHead.get("invtNo"));
                        }
                        if (inventoryHead.containsKey("ieFlag")) {
                            impInventoryHead.setIe_flag(inventoryHead.get("ieFlag"));
                        }
                        if (inventoryHead.containsKey("declTime")) {
                            impInventoryHead.setDecl_time(shortSdf.parse(inventoryHead.get("declTime")));
                        }
                        if (inventoryHead.containsKey("customsCode")) {
                            impInventoryHead.setCustoms_code(inventoryHead.get("customsCode"));
                        }
                        if (inventoryHead.containsKey("portCode")) {
                            impInventoryHead.setPort_code(inventoryHead.get("portCode"));
                        }
                        if (inventoryHead.containsKey("ieDate")) {
                            impInventoryHead.setIe_date(shortSdf.parse(inventoryHead.get("ieDate")));
                        }
                        if (inventoryHead.containsKey("buyerIdType")) {
                            impInventoryHead.setBuyer_id_type(inventoryHead.get("buyerIdType"));
                        }
                        if (inventoryHead.containsKey("buyerIdNumber")) {
                            impInventoryHead.setBuyer_id_number(inventoryHead.get("buyerIdNumber"));
                        }
                        if (inventoryHead.containsKey("buyerName")) {
                            impInventoryHead.setBuyer_name(inventoryHead.get("buyerName"));
                        }
                        if (inventoryHead.containsKey("buyerTelephone")) {
                            impInventoryHead.setBuyer_telephone(inventoryHead.get("buyerTelephone"));
                        }
                        if (inventoryHead.containsKey("consigneeAddress")) {
                            impInventoryHead.setConsignee_address(inventoryHead.get("consigneeAddress"));
                        }
                        if (inventoryHead.containsKey("agentCode")) {
                            impInventoryHead.setAgent_code(inventoryHead.get("agentCode"));
                        }
                        if (inventoryHead.containsKey("agentName")) {
                            impInventoryHead.setAgent_name(inventoryHead.get("agentName"));
                        }
                        if (inventoryHead.containsKey("areaCode")) {
                            impInventoryHead.setArea_code(inventoryHead.get("areaCode"));
                        }
                        if (inventoryHead.containsKey("areaName")) {
                            impInventoryHead.setArea_name(inventoryHead.get("areaName"));
                        }
                        if (inventoryHead.containsKey("tradeMode")) {
                            impInventoryHead.setTrade_mode(inventoryHead.get("tradeMode"));
                        }
                        if (inventoryHead.containsKey("trafMode")) {
                            impInventoryHead.setTraf_mode(inventoryHead.get("trafMode"));
                        }
                        if (inventoryHead.containsKey("trafNo")) {
                            impInventoryHead.setTraf_no(inventoryHead.get("trafNo"));
                        }
                        if (inventoryHead.containsKey("voyageNo")) {
                            impInventoryHead.setVoyage_no(inventoryHead.get("voyageNo"));
                        }
                        if (inventoryHead.containsKey("billNo")) {
                            impInventoryHead.setBill_no(inventoryHead.get("billNo"));
                        }
                        if (inventoryHead.containsKey("loctNo")) {
                            impInventoryHead.setLoct_no(inventoryHead.get("loctNo"));
                        }
                        if (inventoryHead.containsKey("licenseNo")) {
                            impInventoryHead.setLicense_no(inventoryHead.get("licenseNo"));
                        }
                        if (inventoryHead.containsKey("country")) {
                            impInventoryHead.setCountry(inventoryHead.get("country"));
                        }
                        if (inventoryHead.containsKey("freight")) {
                            impInventoryHead.setFreight(inventoryHead.get("freight"));
                        }
                        if (inventoryHead.containsKey("insuredFee")) {
                            impInventoryHead.setInsured_fee(inventoryHead.get("insuredFee"));
                        }
                        if (inventoryHead.containsKey("currency")) {
                            impInventoryHead.setCurrency(inventoryHead.get("currency"));
                        }
                        if (inventoryHead.containsKey("wrapType")) {
                            impInventoryHead.setWrap_type(inventoryHead.get("wrapType"));
                        }
                        if (inventoryHead.containsKey("packNo")) {
                            impInventoryHead.setPack_no(inventoryHead.get("packNo"));
                        }
                        if (inventoryHead.containsKey("grossWeight")) {
                            impInventoryHead.setGross_weight(inventoryHead.get("grossWeight"));
                        }
                        if (inventoryHead.containsKey("netWeight")) {
                            impInventoryHead.setNet_weight(inventoryHead.get("netWeight"));
                        }
                        if (inventoryHead.containsKey("note")) {
                            impInventoryHead.setNote(inventoryHead.get("note"));
                        }
                    }
                    this.stockMessageMapper.insertImpInventoryHead(impInventoryHead); //插入清单表头数据
                }

                if (!StringUtils.isEmpty(InventoryLists)) {
                    for (Map<String, String> inventoryList : InventoryLists) {
                        impInventoryBody.setHead_guid(guid);
                        if (inventoryList.containsKey("gnum")) {
                            impInventoryBody.setG_num(Integer.valueOf(inventoryList.get("gnum")));
                        }
                        if (inventoryList.containsKey("itemRecordNo")) {
                            impInventoryBody.setItem_record_no(inventoryList.get("itemRecordNo"));
                        }
                        if (inventoryList.containsKey("itemNo")) {
                            impInventoryBody.setItem_no(inventoryList.get("itemNo"));
                        }
                        if (inventoryList.containsKey("itemName")) {
                            impInventoryBody.setItem_name(inventoryList.get("itemName"));
                        }
                        if (inventoryList.containsKey("gcode")) {
                            impInventoryBody.setG_code(inventoryList.get("gcode"));
                        }
                        if (inventoryList.containsKey("gname")) {
                            impInventoryBody.setG_name(inventoryList.get("gname"));
                        }
                        if (inventoryList.containsKey("gmodel")) {
                            impInventoryBody.setG_model(inventoryList.get("gmodel"));
                        }
                        if (inventoryList.containsKey("barCode")) {
                            impInventoryBody.setBar_code(inventoryList.get("barCode"));
                        }
                        if (inventoryList.containsKey("country")) {
                            impInventoryBody.setCountry(inventoryList.get("country"));
                        }
                        if (inventoryList.containsKey("currency")) {
                            impInventoryBody.setCurrency(inventoryList.get("currency"));
                        }
                        if (inventoryList.containsKey("qty")) {
                            impInventoryBody.setQty(inventoryList.get("qty"));
                        }
                        if (inventoryList.containsKey("unit")) {
                            impInventoryBody.setUnit(inventoryList.get("unit"));
                        }
                        if (inventoryList.containsKey("qty1")) {
                            impInventoryBody.setQty1(inventoryList.get("qty1"));
                        }
                        if (inventoryList.containsKey("unit1")) {
                            impInventoryBody.setUnit1(inventoryList.get("unit1"));
                        }
                        if (inventoryList.containsKey("qty2")) {
                            impInventoryBody.setQty2(inventoryList.get("qty2"));
                        }
                        if (inventoryList.containsKey("unit2")) {
                            impInventoryBody.setUnit2(inventoryList.get("unit2"));
                        }
                        if (inventoryList.containsKey("price")) {
                            impInventoryBody.setPrice(inventoryList.get("price"));
                        }
                        if (inventoryList.containsKey("totalPrice")) {
                            impInventoryBody.setTotal_price(inventoryList.get("totalPrice"));
                        }
                        if (inventoryList.containsKey("note")) {
                            impInventoryBody.setNote(inventoryList.get("note"));
                        }
                    }
                    this.stockMessageMapper.insertImpInventoryBody(impInventoryBody); //插入清单表体数据
                }
            }
        }
    }

    /**
     * 插入入库明细单报文数据（进口跨境直购）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void insertImpDeliveryData(Map<String, List<Map<String, List<Map<String, String>>>>> stockMsg, String refileName) throws Exception {
        List<Map<String, List<Map<String, String>>>> list = stockMsg.get("Delivery");
        String guid = null;
        if (!StringUtils.isEmpty(list)) {
            ImpDeliveryHead impDeliveryHead;
            ImpDeliveryBody impDeliveryBody;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            for (int i = 0; i < list.size(); i++) {
                List<Map<String, String>> DeliveryHeads = list.get(i).get("DeliveryHead");
                List<Map<String, String>> DeliveryLists = list.get(i).get("DeliveryList");
                impDeliveryHead = new ImpDeliveryHead();
                impDeliveryBody = new ImpDeliveryBody();
                impDeliveryHead.setCrt_tm(new Date());
                impDeliveryHead.setUpd_tm(new Date());

                if (!StringUtils.isEmpty(DeliveryHeads)) {
                    guid = DeliveryHeads.get(0).get("guid");
                }

                if (!StringUtils.isEmpty(DeliveryHeads)) {
                    for (Map<String, String> deliveryHead : DeliveryHeads) {
                        if (deliveryHead.containsKey("guid")) {
                            impDeliveryHead.setGuid(deliveryHead.get("guid"));
                        }
                        if (deliveryHead.containsKey("appType")) {
                            impDeliveryHead.setApp_type(deliveryHead.get("appType"));
                        }
                        if (deliveryHead.containsKey("appTime")) {
                            impDeliveryHead.setApp_time(sdf.parse(deliveryHead.get("appTime")));
                        }
                        if (deliveryHead.containsKey("appStatus")) {
                            impDeliveryHead.setApp_status(deliveryHead.get("appStatus"));
                        }
                        if (deliveryHead.containsKey("customsCode")) {
                            impDeliveryHead.setCustoms_code(deliveryHead.get("customsCode"));
                        }
                        if (deliveryHead.containsKey("copNo")) {
                            impDeliveryHead.setCop_no(deliveryHead.get("copNo"));
                        }
                        if (deliveryHead.containsKey("preNo")) {
                            impDeliveryHead.setPre_no(deliveryHead.get("preNo"));
                        }
                        if (deliveryHead.containsKey("rkdNo")) {
                            impDeliveryHead.setRkd_no(deliveryHead.get("rkdNo"));
                        }
                        if (deliveryHead.containsKey("operatorCode")) {
                            impDeliveryHead.setOperator_code(deliveryHead.get("operatorCode"));
                        }
                        if (deliveryHead.containsKey("operatorName")) {
                            impDeliveryHead.setOperator_name(deliveryHead.get("operatorName"));
                        }
                        if (deliveryHead.containsKey("ieFlag")) {
                            impDeliveryHead.setIe_flag(deliveryHead.get("ieFlag"));
                        }
                        if (deliveryHead.containsKey("trafMode")) {
                            impDeliveryHead.setTraf_mode(deliveryHead.get("trafMode"));
                        }
                        if (deliveryHead.containsKey("trafNo")) {
                            impDeliveryHead.setTraf_no(deliveryHead.get("trafNo"));
                        }
                        if (deliveryHead.containsKey("voyageNo")) {
                            impDeliveryHead.setVoyage_no(deliveryHead.get("voyageNo"));
                        }
                        if (deliveryHead.containsKey("billNo")) {
                            impDeliveryHead.setBill_no(deliveryHead.get("billNo"));
                        }
                        if (deliveryHead.containsKey("logisticsCode")) {
                            impDeliveryHead.setLogistics_code(deliveryHead.get("logisticsCode"));
                        }
                        if (deliveryHead.containsKey("logisticsName")) {
                            impDeliveryHead.setLogistics_name(deliveryHead.get("logisticsName"));
                        }
                        if (deliveryHead.containsKey("unloadLocation")) {
                            impDeliveryHead.setUnload_location(deliveryHead.get("unloadLocation"));
                        }
                        if (deliveryHead.containsKey("note")) {
                            impDeliveryHead.setNote(deliveryHead.get("note"));
                        }
                    }
                    this.stockMessageMapper.insertImpDeliveryHead(impDeliveryHead); //插入清单表头数据
                }

                if (!StringUtils.isEmpty(DeliveryLists)) {
                    for (Map<String, String> deliveryList : DeliveryLists) {
                        impDeliveryBody.setHead_guid(guid);
                        if (deliveryList.containsKey("gnum")) {
                            impDeliveryBody.setG_num(Integer.valueOf(deliveryList.get("gnum")));
                        }
                        if (deliveryList.containsKey("logisticsNo")) {
                            impDeliveryBody.setLogistics_no(deliveryList.get("logisticsNo"));
                        }
                        if (deliveryList.containsKey("note")) {
                            impDeliveryBody.setNote(deliveryList.get("note"));
                        }
                    }
                    this.stockMessageMapper.insertImpDeliveryBody(impDeliveryBody); //插入清单表体数据
                }
            }
        }
    }

}
