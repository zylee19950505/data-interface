package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.ReceiptMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 跨境报文回值解析（订单，支付单，运单，运单状态，清单回执报文）
 * Created by Administrator on 2017/7/25.
 */
@Service
public class ReceiptService {
    private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    ReceiptMapper receiptMapper;

    @Transactional(rollbackForClassName = "Exception")
    public boolean createReceipt(Map map, String refileName) {
        boolean flag = true;
        try {
            String type = (String) map.get("type");
            Map<String, List<List<Map<String, String>>>> receipt = (Map<String, List<List<Map<String, String>>>>) map.get("Receipt");

            switch (type) {
                case "CEB312"://订单回执代码
                    this.createImpRecorder(receipt, refileName);
                    break;
                case "CEB412"://支付单回执代码
                    this.createImpRecPayment(receipt, refileName);
                    break;
                case "CEB512"://运单回执代码
                    this.createImpRecLogistics(receipt, refileName);
                    break;
                case "CEB514"://运单状态回执代码
                    this.createImpRecLogisticsStatus(receipt, refileName);
                    break;
                case "CEB622"://清单回执代码
                    this.createImpRecInventory(receipt, refileName);
                    break;
                case "CEB712"://入库明细单回执
                    this.createImpRecDelivery(receipt, refileName);
                    break;
                case "CheckGoodsInfo"://核放单预订数据
                    this.createCheckGoodsInfo(receipt, refileName);
                    break;
                case "TAX"://核放单预订数据
                    this.createTax(receipt, refileName);
                    break;
            }
        } catch (Exception e) {
            flag = false;
            this.logger.error(String.format("报文回执入库失败,文件名为:%s", refileName), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    /**
     * 插入电子税单回执报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createTax(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> taxList = receipt.get("Tax");
        List<List<Map<String, String>>> taxHeads = receipt.get("TaxHeadRd");
        List<List<Map<String, String>>> taxLists = receipt.get("TaxListRd");

        if (!StringUtils.isEmpty(taxList)) {
            TaxHeadRd taxHeadRd;
            TaxListRd taxListRd;
            for (int i = 0; i < taxHeads.size(); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                taxHeadRd = new TaxHeadRd();
                String guid = IdUtils.getUUId();
                taxHeadRd.setGuid(guid);
                taxHeadRd.setCrt_tm(new Date());
                taxHeadRd.setUpd_tm(new Date());

                List<Map<String, String>> taxHead = taxHeads.get(i);
                for (Map<String, String> map : taxHead) {
                    if (map.containsKey("guid")) {
                        taxHeadRd.setPrivate_guid(map.get("guid"));
                    }
                    if (map.containsKey("returnTime")) {
                        taxHeadRd.setReturn_time(map.get("returnTime"));
                    }
                    if (map.containsKey("invtNo")) {
                        taxHeadRd.setInvt_no(map.get("invtNo"));
                    }
                    if (map.containsKey("taxNo")) {
                        taxHeadRd.setTax_no(map.get("taxNo"));
                    }
                    if (map.containsKey("customsTax")) {
                        taxHeadRd.setCustoms_tax(map.get("customsTax"));
                    }
                    if (map.containsKey("valueAddedTax")) {
                        taxHeadRd.setValue_added_tax(map.get("valueAddedTax"));
                    }
                    if (map.containsKey("consumptionTax")) {
                        taxHeadRd.setConsumption_tax(map.get("consumptionTax"));
                    }
                    if (map.containsKey("status")) {
                        taxHeadRd.setStatus(map.get("status"));
                    }
                    if (map.containsKey("entDutyNo")) {
                        taxHeadRd.setEntduty_no(map.get("entDutyNo"));
                    }
                    if (map.containsKey("note")) {
                        taxHeadRd.setNote(map.get("note"));
                    }
                    if (map.containsKey("assureCode")) {
                        taxHeadRd.setAssure_code(map.get("assureCode"));
                    }
                    if (map.containsKey("ebcCode")) {
                        taxHeadRd.setEbc_code(map.get("ebcCode"));
                    }
                    if (map.containsKey("logisticsCode")) {
                        taxHeadRd.setLogistics_code(map.get("logisticsCode"));
                    }
                    if (map.containsKey("agentCode")) {
                        taxHeadRd.setAgent_code(map.get("agentCode"));
                    }
                    if (map.containsKey("customsCode")) {
                        taxHeadRd.setCustoms_code(map.get("customsCode"));
                    }
                    if (map.containsKey("orderNo")) {
                        taxHeadRd.setOrder_no(map.get("orderNo"));
                    }
                    if (map.containsKey("logisticsNo")) {
                        taxHeadRd.setLogistics_no(map.get("logisticsNo"));
                    }
                }
                this.receiptMapper.InsertTaxHeadRd(taxHeadRd);

                long returnTime = Long.parseLong(taxHeadRd.getReturn_time());
                String invtNo = taxHeadRd.getInvt_no();
                ImpInventoryHead impInventoryHead = this.receiptMapper.findByInvtNo(invtNo);
                if (!StringUtils.isEmpty(impInventoryHead)) {
                    long taxPreTime = StringUtils.isEmpty(impInventoryHead.getTax_return_time()) ? 0 : Long.parseLong(impInventoryHead.getTax_return_time());
                    if (returnTime >= taxPreTime) {
                        this.receiptMapper.updateInventoryHeadTax(taxHeadRd);
                        for (int j = 0; j < taxLists.size(); j++) {
                            taxListRd = new TaxListRd();
                            taxListRd.setHead_guid(guid);
                            List<Map<String, String>> taxlist = taxLists.get(j);
                            for (Map<String, String> map : taxlist) {
                                if (map.containsKey("gnum")) {
                                    taxListRd.setG_num(map.get("gnum"));
                                }
                                if (map.containsKey("gcode")) {
                                    taxListRd.setG_code(map.get("gcode"));
                                }
                                if (map.containsKey("taxPrice")) {
                                    taxListRd.setTax_price(map.get("taxPrice"));
                                }
                                if (map.containsKey("customsTax")) {
                                    taxListRd.setCustoms_tax(map.get("customsTax"));
                                }
                                if (map.containsKey("valueAddedTax")) {
                                    taxListRd.setValue_added_tax(map.get("valueAddedTax"));
                                }
                                if (map.containsKey("consumptionTax")) {
                                    taxListRd.setConsumption_tax(map.get("consumptionTax"));
                                }
                            }
                            this.receiptMapper.InsertTaxListRd(taxListRd);
                            this.receiptMapper.updateInventoryListTax(taxHeadRd, taxListRd);
                        }
                    } else {
                        for (int j = 0; j < taxLists.size(); j++) {
                            taxListRd = new TaxListRd();
                            taxListRd.setHead_guid(guid);
                            List<Map<String, String>> taxlist = taxLists.get(j);
                            for (Map<String, String> map : taxlist) {
                                if (map.containsKey("gnum")) {
                                    taxListRd.setG_num(map.get("gnum"));
                                }
                                if (map.containsKey("gcode")) {
                                    taxListRd.setG_code(map.get("gcode"));
                                }
                                if (map.containsKey("taxPrice")) {
                                    taxListRd.setTax_price(map.get("taxPrice"));
                                }
                                if (map.containsKey("customsTax")) {
                                    taxListRd.setCustoms_tax(map.get("customsTax"));
                                }
                                if (map.containsKey("valueAddedTax")) {
                                    taxListRd.setValue_added_tax(map.get("valueAddedTax"));
                                }
                                if (map.containsKey("consumptionTax")) {
                                    taxListRd.setConsumption_tax(map.get("consumptionTax"));
                                }
                            }
                            this.receiptMapper.InsertTaxListRd(taxListRd);
                        }
                    }
                } else {
                    continue;
                }

            }
        }
    }

    /**
     * 插入预定数据回执报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createCheckGoodsInfo(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("CheckGoodsInfoHead");
        if (!StringUtils.isEmpty(list)) {
            CheckGoodsInfo checkGoodsInfo;
            for (int i = 0; i < list.size(); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

                checkGoodsInfo = new CheckGoodsInfo();
                checkGoodsInfo.setGuid(IdUtils.getUUId());
                checkGoodsInfo.setCrt_id("系统自生成");
                checkGoodsInfo.setCrt_tm(new Date());
                checkGoodsInfo.setUpd_id("系统自生成");
                checkGoodsInfo.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("entryid")) {
                        checkGoodsInfo.setEntry_id(map.get("entryid"));
                    }
                    if (map.containsKey("ieFlage")) {
                        checkGoodsInfo.setI_e_flag(map.get("ieFlage"));
                    }
                    if (map.containsKey("orderNo")) {
                        checkGoodsInfo.setOrder_no(map.get("orderNo"));
                    }
                    if (map.containsKey("logisticsNo")) {
                        checkGoodsInfo.setLogistics_no(map.get("logisticsNo"));
                    }
                    if (map.containsKey("logisticsCode")) {
                        checkGoodsInfo.setLogistics_code(map.get("logisticsCode"));
                    }
                    if (map.containsKey("logisticsName")) {
                        checkGoodsInfo.setLogistics_name(map.get("logisticsName"));
                    }
                    if (map.containsKey("packNum")) {
                        checkGoodsInfo.setPack_num(map.get("packNum"));
                    }
                    if (map.containsKey("grossWt")) {
                        checkGoodsInfo.setGross_wt(map.get("grossWt"));
                    }
                    if (map.containsKey("netWt")) {
                        checkGoodsInfo.setNet_wt(map.get("netWt"));
                    }
                    if (map.containsKey("goodsValue")) {
                        checkGoodsInfo.setGoods_value(map.get("goodsValue"));
                    }
                    if (map.containsKey("controlledStatus")) {
                        checkGoodsInfo.setControlled_status(map.get("controlledStatus"));
                    }
                    if (map.containsKey("messageTime")) {
                        checkGoodsInfo.setMessage_time(sdf.parse(map.get("messageTime")));
                    }
                    if (map.containsKey("customsCode")) {
                        checkGoodsInfo.setCustoms_code(map.get("customsCode"));
                    }
                    if (map.containsKey("status")) {
                        checkGoodsInfo.setStatus(map.get("status"));
                    }
                    if (map.containsKey("totalLogisticsNo")) {
                        checkGoodsInfo.setTotal_logistics_no(map.get("totalLogisticsNo"));
                    }
                }
                this.receiptMapper.createCheckGoodsInfoHis(checkGoodsInfo); //插入核放单预订数据

                long newTime = (checkGoodsInfo.getMessage_time()).getTime();
                String orderNo = checkGoodsInfo.getOrder_no();
                CheckGoodsInfo checkGoodsInfoData = this.receiptMapper.findByOrderNo(orderNo);
                if (!StringUtils.isEmpty(checkGoodsInfoData)) {
                    long systemTime = StringUtils.isEmpty(checkGoodsInfoData.getMessage_time()) ? 0 : (checkGoodsInfoData.getMessage_time()).getTime();
                    if (newTime >= systemTime) {
                        this.receiptMapper.updateCheckGoodsInfo(checkGoodsInfo);
                    } else {
                        continue;
                    }
                } else {
                    this.receiptMapper.createCheckGoodsInfo(checkGoodsInfo);
                }
            }
        }
    }

    /**
     * 插入订单回执报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createImpRecorder(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("OrderReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecOrder impRecOrder;
            for (int i = 0; i < list.size(); i++) {
                impRecOrder = new ImpRecOrder();
                impRecOrder.setId(IdUtils.getUUId());
                impRecOrder.setCrtTm(new Date());
                impRecOrder.setUpdTm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecOrder.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("ebpCode")) {
                        impRecOrder.setEbpCode(map.get("ebpCode"));
                    }
                    if (map.containsKey("ebcCode")) {
                        impRecOrder.setEbcCode(map.get("ebcCode"));
                    }
                    if (map.containsKey("orderNo")) {
                        impRecOrder.setOrderNo(map.get("orderNo"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecOrder.setReturnStatus(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecOrder.setReturnTime(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecOrder.setReturnInfo(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecOrder(impRecOrder); //插入订单状态表数据
                this.updateImpOrderStatus(impRecOrder);    //更新订单表状态
            }
        }
    }

    /**
     * 根据订单回执更新订单状态
     */
    private void updateImpOrderStatus(ImpRecOrder impRecOrder) throws Exception {
        ImpOrderHead impOrderHead = new ImpOrderHead();
        impOrderHead.setGuid(impRecOrder.getGuid());
        impOrderHead.setOrder_No(impRecOrder.getOrderNo());
        impOrderHead.setEbc_Code(impRecOrder.getEbcCode());
        impOrderHead.setEbp_Code(impRecOrder.getEbpCode());
        impOrderHead.setReturn_status(impRecOrder.getReturnStatus());
        impOrderHead.setReturn_time(impRecOrder.getReturnTime());
        impOrderHead.setReturn_info(impRecOrder.getReturnInfo());
        //订单申报成功
        impOrderHead.setData_status(StatusCode.DDSBCG);
        impOrderHead.setUpd_tm(new Date());
        this.receiptMapper.updateImpOrder(impOrderHead);
    }


    /**
     * 插入支付单回执报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createImpRecPayment(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("PaymentReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecPayment impRecPayment;
            for (int i = 0; i < list.size(); i++) {
                impRecPayment = new ImpRecPayment();
                impRecPayment.setId(IdUtils.getUUId());
                impRecPayment.setCrt_tm(new Date());
                impRecPayment.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecPayment.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("payCode")) {
                        impRecPayment.setPay_code(map.get("payCode"));
                    }
                    if (map.containsKey("payTransactionId")) {
                        impRecPayment.setPay_transaction_id(map.get("payTransactionId"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecPayment.setReturn_status(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecPayment.setReturn_time(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecPayment.setReturn_info(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecPayment(impRecPayment); //插入支付单状态表数据
                this.updateImpPaymentStatus(impRecPayment);    //更新支付单表状态
            }
        }
    }

    /**
     * 根据支付单回执更新支付单表状态
     */
    private void updateImpPaymentStatus(ImpRecPayment impRecPayment) throws Exception {
        ImpPayment impPayment = new ImpPayment();
        impPayment.setPay_code(impRecPayment.getPay_code());//支付企业的海关注册登记编号
        impPayment.setPay_transaction_id(impRecPayment.getPay_transaction_id());//支付企业唯一的支付流水号
        impPayment.setReturn_status(impRecPayment.getReturn_status());//操作结果（2电子口岸申报中/3发送海关成功/4发送海关失败/100海关退单/120海关入库）,若小于0 数字表示处理异常回执
        impPayment.setReturn_info(impRecPayment.getReturn_info());//备注（如:退单原因）
        impPayment.setReturn_time(impRecPayment.getReturn_time());//操作时间(格式：yyyyMMddHHmmssfff)
        //支付单状态申报成功
        impPayment.setData_status(StatusCode.ZFDSBCG);
        impPayment.setUpd_tm(new Date());
        this.receiptMapper.updateImpPayment(impPayment);  //更新支付单表中的回执状态
    }

    /**
     * 插入运单回执报文数据
     */
    private void createImpRecLogistics(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("LogisticsReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecLogistics impRecLogistics;
            for (int i = 0; i < list.size(); i++) {
                impRecLogistics = new ImpRecLogistics();
                impRecLogistics.setId(IdUtils.getUUId());
                impRecLogistics.setCrtTm(new Date());
                impRecLogistics.setUpdTm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecLogistics.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("logisticsCode")) {
                        impRecLogistics.setLogistics_Code(map.get("logisticsCode"));
                    }
                    if (map.containsKey("logisticsNo")) {
                        impRecLogistics.setLogistics_No(map.get("logisticsNo"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecLogistics.setReturn_Status(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecLogistics.setReturn_Time(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecLogistics.setReturn_Info(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecLogistics(impRecLogistics); //插入运单状态表数据
                this.updateImpLogistics(impRecLogistics);    //更新运单表状态
            }
        }
    }

    /**
     * 根据运单回执更新运单
     */
    private void updateImpLogistics(ImpRecLogistics impRecLogistics) throws Exception {
        ImpLogistics impLogistics = new ImpLogistics();
        impLogistics.setGuid(impRecLogistics.getGuid());
        impLogistics.setLogistics_no(impRecLogistics.getLogistics_No());
        impLogistics.setLogistics_code(impRecLogistics.getLogistics_Code());
        impLogistics.setReturn_status(impRecLogistics.getReturn_Status());
        impLogistics.setReturn_time(impRecLogistics.getReturn_Time());
        impLogistics.setReturn_info(impRecLogistics.getReturn_Info());
        impLogistics.setUpd_tm(new Date());
        //收到回执后,把数据状态改为运单申报成功
        impLogistics.setData_status(StatusCode.YDSBCG);
        this.receiptMapper.updateImpLogistics(impLogistics);
    }


    /**
     * 插入运单状态回执报文数据
     */
    private void createImpRecLogisticsStatus(Map<String, List<List<Map<String, String>>>> receipt, String refileName) {
        List<List<Map<String, String>>> list = receipt.get("LogisticsStatusReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecLogisticsStatus impRecLogisticsStatus;
            for (int i = 0; i < list.size(); i++) {
                impRecLogisticsStatus = new ImpRecLogisticsStatus();
                impRecLogisticsStatus.setId(IdUtils.getUUId());
                impRecLogisticsStatus.setCrtTm(new Date());
                impRecLogisticsStatus.setUpdTm(new Date());
                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecLogisticsStatus.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("logisticsCode")) {
                        impRecLogisticsStatus.setLogistics_Code(map.get("logisticsCode"));
                    }
                    if (map.containsKey("logisticsNo")) {
                        impRecLogisticsStatus.setLogistics_No(map.get("logisticsNo"));
                    }
                    if (map.containsKey("logisticsStatus")) {
                        impRecLogisticsStatus.setLogistics_Status(map.get("logisticsStatus"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecLogisticsStatus.setReturn_Status(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecLogisticsStatus.setReturn_Time(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecLogisticsStatus.setReturn_Info(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecLogisticsStatus(impRecLogisticsStatus); //插入运单状态表数据
                //运单状态收到回执后，更新运单表状态
                this.updateImpLogisticsDataStatus(impRecLogisticsStatus, StatusCode.YDZTSBCG);    //更新运单表状态
            }
        }
    }

    /**
     * 根据运单状态回执更新运单状态表
     */
    private void updateImpLogisticsStatus(ImpRecLogisticsStatus impRecLogisticsStatus) {
        ImpLogisticsStatus impLogisticsStatus = new ImpLogisticsStatus();
        impLogisticsStatus.setGuid(impRecLogisticsStatus.getGuid());
        impLogisticsStatus.setLogistics_no(impRecLogisticsStatus.getLogistics_No());
        impLogisticsStatus.setLogistics_status(impRecLogisticsStatus.getLogistics_Status());
        impLogisticsStatus.setLogistics_code(impRecLogisticsStatus.getLogistics_Code());
        impLogisticsStatus.setReturn_status(impRecLogisticsStatus.getReturn_Status());
        impLogisticsStatus.setReturn_time(impRecLogisticsStatus.getReturn_Time());
        impLogisticsStatus.setReturn_info(impRecLogisticsStatus.getReturn_Info());
        //运单状态申报成功
        impLogisticsStatus.setData_status(StatusCode.YDZTSBCG);
        impLogisticsStatus.setUpd_tm(new Date());
        this.receiptMapper.updateImpLogisticsStatus(impLogisticsStatus);
    }

    /**
     * 将运单表置为“CBDS52”状态（运单申报成功）
     */
    private void updateImpLogisticsDataStatus(ImpRecLogisticsStatus impRecLogisticsStatus, String ydztsbcg) {
        this.receiptMapper.updateImpLogisticsDataStatus(impRecLogisticsStatus, ydztsbcg);
    }

    /**
     * 插入清单回执报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createImpRecInventory(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("InventoryReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecInventory impRecInventory;
            for (int i = 0; i < list.size(); i++) {
                impRecInventory = new ImpRecInventory();
                impRecInventory.setId(IdUtils.getUUId());
                impRecInventory.setCrt_tm(new Date());
                impRecInventory.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecInventory.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("customsCode")) {
                        impRecInventory.setCustoms_code(map.get("customsCode"));
                    }
                    if (map.containsKey("ebpCode")) {
                        impRecInventory.setEbp_code(map.get("ebpCode"));
                    }
                    if (map.containsKey("ebcCode")) {
                        impRecInventory.setEbc_code(map.get("ebcCode"));
                    }
                    if (map.containsKey("agentCode")) {
                        impRecInventory.setAgent_code(map.get("agentCode"));
                    }
                    if (map.containsKey("copNo")) {
                        impRecInventory.setCop_no(map.get("copNo"));
                    }
                    if (map.containsKey("preNo")) {
                        impRecInventory.setPre_no(map.get("preNo"));
                    }
                    if (map.containsKey("invtNo")) {
                        impRecInventory.setInvt_no(map.get("invtNo"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecInventory.setReturn_status(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecInventory.setReturn_time(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecInventory.setReturn_info(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecInventory(impRecInventory); //插入清单状态表数据
                long returnTime = Long.parseLong(impRecInventory.getReturn_time());
                String copNo = impRecInventory.getCop_no();
                ImpInventoryHead impInventoryHead = this.receiptMapper.findByCopNo(copNo);
                if (!StringUtils.isEmpty(impInventoryHead)) {
                    long systemTime = StringUtils.isEmpty(impInventoryHead.getReturn_time()) ? 0 : Long.parseLong(impInventoryHead.getReturn_time());
                    if (returnTime >= systemTime) {
                        this.updateImpInventoryStatus(impRecInventory);    //更新清单表状态
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
    }


    /**
     * 根据清单回执更新清单表
     */
    private void updateImpInventoryStatus(ImpRecInventory impRecInventory) throws Exception {
        ImpInventoryHead impInventoryHead = new ImpInventoryHead();
        impInventoryHead.setCustoms_code(impRecInventory.getCustoms_code());//接受申报的海关关区代码。
        impInventoryHead.setEbp_code(impRecInventory.getEbp_code());//电商平台的海关注册登记编号
        impInventoryHead.setEbc_code(impRecInventory.getEbc_code());//电商企业的海关注册登记编号。
        impInventoryHead.setAgent_code(impRecInventory.getAgent_code());//申报单位的海关注册登记编号。
        impInventoryHead.setCop_no(impRecInventory.getCop_no());//企业内部标识单证的编号
        impInventoryHead.setPre_no(impRecInventory.getPre_no());//电子口岸标识单证的编号
        impInventoryHead.setInvt_no(impRecInventory.getInvt_no());//海关接受申报生成的清单编号。
        impInventoryHead.setReturn_status(impRecInventory.getReturn_status());//操作结果（2电子口岸申报中/3发送海关成功/4发送海关失败/100海关退单/120海关入库）,若小于0 数字表示处理异常回执
        impInventoryHead.setReturn_info(impRecInventory.getReturn_info());//备注（如:退单原因）
        impInventoryHead.setReturn_time(impRecInventory.getReturn_time());//操作时间(格式：yyyyMMddHHmmssfff)
        impInventoryHead.setUpd_tm(new Date());
        //清单申报成功
        String type = this.receiptMapper.queryBusiTypeByCopNo(impRecInventory.getCop_no());
        if (type.equals(SystemConstants.T_IMP_BOND_INVEN)) {
            impInventoryHead.setData_status(StatusCode.BSQDSBCG);
        } else {
            impInventoryHead.setData_status(StatusCode.QDSBCG);
        }

        boolean isContains = (impInventoryHead.getReturn_status()).contains("-");
        String MaxTimeReturnStatus = null;
        if (isContains) {
            MaxTimeReturnStatus = this.receiptMapper.queryMaxTimeReturnStatus(impInventoryHead.getCop_no());
            if (!StringUtils.isEmpty(MaxTimeReturnStatus)) {
                impInventoryHead.setReturn_status(MaxTimeReturnStatus);
            } else {
                impInventoryHead.setReturn_status("100");
            }
        }

        this.receiptMapper.updateImpInventory(impInventoryHead);  //更新支付单表中的回执状态

    }

    /**
     * 插入入库明细单回执报文数据
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createImpRecDelivery(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("DeliveryReturn");
        if (!StringUtils.isEmpty(list)) {
            ImpRecDelivery impRecDelivery;
            for (int i = 0; i < list.size(); i++) {
                impRecDelivery = new ImpRecDelivery();
                impRecDelivery.setId(IdUtils.getUUId());
                impRecDelivery.setCrt_tm(new Date());
                impRecDelivery.setUpd_tm(new Date());

                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("guid")) {
                        impRecDelivery.setGuid(map.get("guid"));
                    }
                    if (map.containsKey("customsCode")) {
                        impRecDelivery.setCustoms_code(map.get("customsCode"));
                    }
                    if (map.containsKey("operatorCode")) {
                        impRecDelivery.setOperator_code(map.get("operatorCode"));
                    }
                    if (map.containsKey("copNo")) {
                        impRecDelivery.setCop_no(map.get("copNo"));
                    }
                    if (map.containsKey("preNo")) {
                        impRecDelivery.setPre_no(map.get("preNo"));
                    }
                    if (map.containsKey("rkdNo")) {
                        impRecDelivery.setRkd_no(map.get("rkdNo"));
                    }
                    if (map.containsKey("returnStatus")) {
                        impRecDelivery.setReturn_status(map.get("returnStatus"));
                    }
                    if (map.containsKey("returnTime")) {
                        impRecDelivery.setReturn_time(map.get("returnTime"));
                    }
                    if (map.containsKey("returnInfo")) {
                        impRecDelivery.setReturn_info(map.get("returnInfo"));
                    }
                }
                this.receiptMapper.createImpRecDelivery(impRecDelivery); //插入清单状态表数据
                long returnTime = Long.parseLong(impRecDelivery.getReturn_time());
                String copNo = impRecDelivery.getCop_no();
                ImpDeliveryHead impDeliveryHead = this.receiptMapper.findDeliveryByCopNo(copNo);
                if (!StringUtils.isEmpty(impDeliveryHead)) {
                    long systemTime = StringUtils.isEmpty(impDeliveryHead.getReturn_time()) ? 0 : Long.parseLong(impDeliveryHead.getReturn_time());
                    if (returnTime >= systemTime) {
                        this.updateImpDeliveryStatus(impRecDelivery);    //更新清单表状态
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /**
     * 根据入库明细单回执更新入库明细数据
     */
    private void updateImpDeliveryStatus(ImpRecDelivery impRecDelivery) throws Exception {
        ImpDeliveryHead impDeliveryHead = new ImpDeliveryHead();
        impDeliveryHead.setCustoms_code(impRecDelivery.getCustoms_code());//接受清单申报的海关关区代码，参照JGS/T 18《海关关区代码》。
        impDeliveryHead.setOperator_code(impRecDelivery.getOperator_code());//监管场所经营人在海关注册登记的编号
        impDeliveryHead.setCop_no(impRecDelivery.getCop_no());//企业内部标识单证的编号。
        impDeliveryHead.setPre_no(impRecDelivery.getPre_no());//电子口岸标识单证的编号。
        impDeliveryHead.setRkd_no(impRecDelivery.getRkd_no());//海关审核生成的入库单编号
        impDeliveryHead.setReturn_status(impRecDelivery.getReturn_status());//回执状态
        impDeliveryHead.setReturn_time(impRecDelivery.getReturn_time());//回执时间
        impDeliveryHead.setReturn_info(impRecDelivery.getReturn_info());//回执信息
        impDeliveryHead.setUpd_tm(new Date());
        //入库明细单申报成功
        impDeliveryHead.setData_status(StatusCode.RKMXDSBCG);
        this.receiptMapper.updateImpDelivery(impDeliveryHead);  //更新支付单表中的回执状态

    }

}
