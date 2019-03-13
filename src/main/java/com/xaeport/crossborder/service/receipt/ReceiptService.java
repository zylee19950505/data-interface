package com.xaeport.crossborder.service.receipt;

import com.xaeport.crossborder.bondstock.CountLoader;
import com.xaeport.crossborder.bondstock.impl.CountActlIncrease;
import com.xaeport.crossborder.bondstock.impl.CountActlReduce;
import com.xaeport.crossborder.bondstock.impl.CountPreIncrease;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.ReceiptMapper;
import com.xaeport.crossborder.data.status.ReceiptType;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.service.bondinvenmanage.BondinvenImportService;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 跨境报文回值解析（订单，支付单，运单，运单状态，清单回执报文）
 * Created by Administrator on 2017/7/25.
 */
@Service
public class ReceiptService {
    private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    ReceiptMapper receiptMapper;
    @Autowired
    BondinvenImportService bondinvenImportService;

    @Transactional(rollbackForClassName = "Exception")
    public boolean createReceipt(Map map, String refileName) {
        boolean flag = true;
        try {
            String type = (String) map.get("type");
            Map<String, List<List<Map<String, String>>>> receipt = new HashMap<>();
            Map<String, List<Map<String, String>>> receiptNew = new HashMap<>();
            if (type != ("COMMON")) {
                receipt = (Map<String, List<List<Map<String, String>>>>) map.get("Receipt");
            } else {
                receiptNew = (Map<String, List<Map<String, String>>>) map.get("Receipt");
            }
            switch (type) {
                case ReceiptType.KJDD://跨境订单回执代码
                    this.createImpRecorder(receipt, refileName);
                    break;
                case ReceiptType.KJZFD://跨境支付单回执代码
                    this.createImpRecPayment(receipt, refileName);
                    break;
                case ReceiptType.KJYD://跨境运单回执代码
                    this.createImpRecLogistics(receipt, refileName);
                    break;
                case ReceiptType.KJYDZT://跨境运单状态回执代码
                    this.createImpRecLogisticsStatus(receipt, refileName);
                    break;
                case ReceiptType.KJQD://跨境清单回执代码
                    this.createImpRecInventory(receipt, refileName);
                    break;
                case ReceiptType.KJRKMXD://跨境入库明细单回执
                    this.createImpRecDelivery(receipt, refileName);
                    break;
                case ReceiptType.KJYDSJ://跨境预订数据
                    this.createCheckGoodsInfo(receipt, refileName);
                    break;
                case ReceiptType.KJSD://跨境电子税单
                    this.createTax(receipt, refileName);
                    break;
                case ReceiptType.BSSJZX://保税数据中心回执
                    this.createInvtCommon(receiptNew, refileName);
                    break;
                case ReceiptType.BSHZQDSH://保税核注清单(报文回执/审核回执)
                    this.createInvtHdeAppr(receipt, refileName);
                    break;
                case ReceiptType.BSHZQDBGD://保税核注清单生成报关单回执
                    this.createInvtInvAppr(receipt, refileName);
                    break;
                case ReceiptType.BSHFDSH://保税核放单(报文回执/审核回执)
                    this.createPassPortHdeAppr(receipt, refileName, ReceiptType.BSHFDSH);
                    break;
                case ReceiptType.BSHFDGK://保税核放单过卡回执
                    this.createPassPortHdeAppr(receipt, refileName, ReceiptType.BSHFDGK);
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
     * 插入电子税单回执报文数据（进口跨境直购）
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
     * 插入预定数据回执报文数据（进口跨境直购）
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
     * 插入（核注清单/核放单）处理成功回执数据中心报文（进口保税）
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createInvtCommon(Map<String, List<Map<String, String>>> receipt, String refileName) throws Exception {
        List<Map<String, String>> seqnolist = receipt.get("SeqNo");
        List<Map<String, String>> etpspreentnolist = receipt.get("EtpsPreentNo");
        List<Map<String, String>> checkinfolist = receipt.get("CheckInfo");
        List<Map<String, String>> dealflaglist = receipt.get("DealFlag");

        if (!StringUtils.isEmpty(seqnolist) && !StringUtils.isEmpty(etpspreentnolist)) {
            RecBondInvtCommon recBondInvtCommon;
            for (int i = 0; i < seqnolist.size(); i++) {
                recBondInvtCommon = new RecBondInvtCommon();
                recBondInvtCommon.setGuid(IdUtils.getUUId());
                recBondInvtCommon.setCrt_tm(new Date());
                recBondInvtCommon.setUpd_tm(new Date());

                Map<String, String> map = seqnolist.get(i);
                if (map.containsKey("SeqNo")) {
                    recBondInvtCommon.setSeq_no(map.get("SeqNo"));
                }
                map = etpspreentnolist.get(i);
                if (map.containsKey("EtpsPreentNo")) {
                    recBondInvtCommon.setEtps_preent_no(map.get("EtpsPreentNo"));
                }
                map = checkinfolist.get(i);
                if (map.containsKey("CheckInfo")) {
                    recBondInvtCommon.setCheck_info(map.get("CheckInfo"));
                }
                map = dealflaglist.get(i);
                if (map.containsKey("DealFlag")) {
                    recBondInvtCommon.setDeal_flag(map.get("DealFlag"));
                }
                this.receiptMapper.createInvtCommon(recBondInvtCommon); //插入（核注清单或核放单）表数据
                this.updateBondInvtStatusByCommon(recBondInvtCommon);    //更新对应表状态
            }
        }
    }

    /**
     * 根据核注清单处理成功回执更新状态（进口保税）
     */
    private void updateBondInvtStatusByCommon(RecBondInvtCommon recBondInvtCommon) throws Exception {
        String etpsPreentNo = recBondInvtCommon.getEtps_preent_no();
        BondInvtBsc bondInvtBsc;
        PassPortHead passPortHead;
        bondInvtBsc = this.receiptMapper.queryIsBondInvt(etpsPreentNo);
        passPortHead = this.receiptMapper.queryIsPassPort(etpsPreentNo);

        if (!StringUtils.isEmpty(bondInvtBsc)) {
            bondInvtBsc.setEtps_inner_invt_no(recBondInvtCommon.getEtps_preent_no());
            bondInvtBsc.setInvt_preent_no(recBondInvtCommon.getSeq_no());
            bondInvtBsc.setReturn_status(recBondInvtCommon.getDeal_flag());
            bondInvtBsc.setReturn_info(recBondInvtCommon.getCheck_info());
            bondInvtBsc.setUpd_time(new Date());
            if (bondInvtBsc.getFlag().equals("EXIT")) {
                bondInvtBsc.setStatus(StatusCode.CQHZQDSBCG);
                this.receiptMapper.updateBondInvtStatusByCommon(bondInvtBsc);
                this.receiptMapper.updateNemsInvtByCommon(bondInvtBsc);
            } else if (bondInvtBsc.getFlag().equals("ENTER")) {
                bondInvtBsc.setStatus(StatusCode.RQHZQDSBCG);
                this.receiptMapper.updateBondInvtStatusByCommon(bondInvtBsc);
            }
        } else if (!StringUtils.isEmpty(passPortHead)) {
            passPortHead.setEtps_preent_no(recBondInvtCommon.getEtps_preent_no());
            passPortHead.setSas_passport_preent_no(recBondInvtCommon.getSeq_no());
            passPortHead.setReturn_status(recBondInvtCommon.getDeal_flag());
            passPortHead.setReturn_info(recBondInvtCommon.getCheck_info());
            passPortHead.setUpd_time(new Date());
            if (passPortHead.getFlag().equals("EXIT")) {
                passPortHead.setStatus(StatusCode.CQHFDSBCG);
                this.receiptMapper.updatePassPortStatusByCommon(passPortHead);
                this.receiptMapper.updatePassPortAcmpByCommon(passPortHead);
            } else if (passPortHead.getFlag().equals("ENTER")) {
                passPortHead.setStatus(StatusCode.RQHFDSBCG);
                this.receiptMapper.updatePassPortStatusByCommon(passPortHead);
                switch (passPortHead.getBind_typecd()) {
                    //一车一单
                    case "2":
                        this.receiptMapper.updatePassPortAcmpByCommon(passPortHead);
                        break;
                    //一车多单
                    case "1":
                        this.receiptMapper.updatePassPortAcmpByCommon(passPortHead);
                        break;
                    //一单多车
                    case "3":
                        this.receiptMapper.updatePassPortListByCommon(passPortHead);
                        break;
                }
            }
        }
    }

    /**
     * 核注清单(报文回执/审核回执)（进口保税）（核注清单报文一）INV201
     * 报文类型：INV201
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createInvtHdeAppr(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("HdeApprResult");
        if (!StringUtils.isEmpty(list)) {
            RecBondInvtHdeAppr recBondInvtHdeAppr;
            for (int i = 0; i < list.size(); i++) {
                recBondInvtHdeAppr = new RecBondInvtHdeAppr();
                recBondInvtHdeAppr.setGuid(IdUtils.getUUId());
                recBondInvtHdeAppr.setCrt_tm(new Date());
                recBondInvtHdeAppr.setUpd_tm(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("etpsPreentNo")) {
                        recBondInvtHdeAppr.setEtps_preent_no(map.get("etpsPreentNo"));
                    }
                    if (map.containsKey("businessId")) {
                        recBondInvtHdeAppr.setBusiness_id(map.get("businessId"));
                    }
                    if (map.containsKey("tmsCnt")) {
                        recBondInvtHdeAppr.setTms_cnt(map.get("tmsCnt"));
                    }
                    if (map.containsKey("typecd")) {
                        recBondInvtHdeAppr.setTypecd(map.get("typecd"));
                    }
                    if (map.containsKey("manageResult")) {
                        recBondInvtHdeAppr.setManage_result("INV201_" + map.get("manageResult"));
                    }
                    if (map.containsKey("manageDate")) {
                        recBondInvtHdeAppr.setManage_date(sdf.parse(map.get("manageDate")));
                    }
                    if (map.containsKey("rmk")) {
                        recBondInvtHdeAppr.setRmk(map.get("rmk"));
                    }
                }
                this.receiptMapper.createInvtHdeAppr(recBondInvtHdeAppr); //插入核注清单回执表数据
                if (recBondInvtHdeAppr.getTypecd().equals("1")) {
                    Date returnTime = recBondInvtHdeAppr.getManage_date();
                    String etpsPreentNo = recBondInvtHdeAppr.getEtps_preent_no();
                    BondInvtBsc bondInvtBsc = this.receiptMapper.queryBondInvt(etpsPreentNo);
                    Date systemTime;
                    if (!StringUtils.isEmpty(bondInvtBsc)) {
                        systemTime = bondInvtBsc.getReturn_time();
                        if (systemTime == null || (returnTime.getTime() >= systemTime.getTime())) {
                            this.updateBondInvtStatusByHdeAppr(recBondInvtHdeAppr, bondInvtBsc);//更新核注清单表状态
                        } else {
                            continue;
                        }
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
     * 根据核注清单回执更新表状态（进口保税）（核注清单报文一）
     * 报文类型：INV201
     */
    private void updateBondInvtStatusByHdeAppr(RecBondInvtHdeAppr recBondInvtHdeAppr, BondInvtBsc bondInvtBscData) throws Exception {
        BondInvtBsc bondInvtBsc = new BondInvtBsc();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //出入区回执更新
        bondInvtBsc.setInvt_preent_no(recBondInvtHdeAppr.getEtps_preent_no());
        bondInvtBsc.setBond_invt_no(recBondInvtHdeAppr.getBusiness_id());
        bondInvtBsc.setChg_tms_cnt(Integer.parseInt(recBondInvtHdeAppr.getTms_cnt()));
        bondInvtBsc.setDclcus_typecd(recBondInvtHdeAppr.getTypecd());
        bondInvtBsc.setReturn_status(recBondInvtHdeAppr.getManage_result());
        bondInvtBsc.setReturn_time(sdf.parse(sdf.format(recBondInvtHdeAppr.getManage_date())));
        bondInvtBsc.setReturn_info(recBondInvtHdeAppr.getRmk());
        bondInvtBsc.setEtps_inner_invt_no(bondInvtBscData.getEtps_inner_invt_no());

        //出区更新核注清单表头，表体
        if (bondInvtBscData.getFlag().equals("EXIT")) {
            this.receiptMapper.updateBondInvtStatusByHdeAppr(bondInvtBsc);
            this.receiptMapper.updateNemssByHdeAppr(bondInvtBsc);
            //实减操作
            if (recBondInvtHdeAppr.getManage_result().equals("INV201_1")) {
                CountLoader countLoader = new CountActlReduce();
                countLoader.count(bondInvtBscData);
            }
        }

        //入区更新核注清单表头，表体
        if (bondInvtBscData.getFlag().equals("ENTER")) {
            this.receiptMapper.updateBondInvtBscByHdeAppr(bondInvtBsc);
            this.receiptMapper.updateBondInvtDtByHdeAppr(bondInvtBsc);
            //预增操作
            if (recBondInvtHdeAppr.getManage_result().equals("INV201_1")) {
                CountLoader countLoader = new CountPreIncrease();
                countLoader.count(bondInvtBsc);
            }
        }

    }

    /**
     * 核注清单生成报关单回执（进口保税）（核注清单报文二）INV202
     * 报文类型：INV202
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createInvtInvAppr(Map<String, List<List<Map<String, String>>>> receipt, String refileName) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("InvApprResult");
        if (!StringUtils.isEmpty(list)) {
            RecBondInvtInvAppr recBondInvtInvAppr;
            for (int i = 0; i < list.size(); i++) {
                recBondInvtInvAppr = new RecBondInvtInvAppr();
                recBondInvtInvAppr.setGuid(IdUtils.getUUId());
                recBondInvtInvAppr.setCrt_tm(new Date());
                recBondInvtInvAppr.setUpd_tm(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("invPreentNo")) {
                        recBondInvtInvAppr.setInv_preent_no(map.get("invPreentNo"));
                    }
                    if (map.containsKey("businessId")) {
                        recBondInvtInvAppr.setBusiness_id(map.get("businessId"));
                    }
                    if (map.containsKey("entrySeqNo")) {
                        recBondInvtInvAppr.setEntry_seq_no(map.get("entrySeqNo"));
                    }
                    if (map.containsKey("manageResult")) {
                        recBondInvtInvAppr.setManage_result("INV202_" + map.get("manageResult"));
                    }
                    if (map.containsKey("createDate")) {
                        recBondInvtInvAppr.setCreate_date(sdf.parse(map.get("createDate")));
                    }
                    if (map.containsKey("reason")) {
                        recBondInvtInvAppr.setReason(map.get("reason"));
                    }
                }
                this.receiptMapper.createInvtInvAppr(recBondInvtInvAppr); //插入核注清单回执表
                Date returnTime = recBondInvtInvAppr.getCreate_date();
                String invPreentNo = recBondInvtInvAppr.getInv_preent_no();
                BondInvtBsc bondInvtBsc = this.receiptMapper.queryBondInvt(invPreentNo);
                Date systemTime;
                if (!StringUtils.isEmpty(bondInvtBsc)) {
                    systemTime = bondInvtBsc.getReturn_time();
                    if (systemTime == null || (returnTime.getTime() >= systemTime.getTime())) {
                        this.updateBondInvtStatusByInvAppr(recBondInvtInvAppr, bondInvtBsc);//更新核注清单表数据
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
     * 根据核注清单回执更新表状态（进口保税）（核注清单报文二）
     * 报文类型：INV202
     */
    private void updateBondInvtStatusByInvAppr(RecBondInvtInvAppr recBondInvtInvAppr, BondInvtBsc bondInvtBscData) throws Exception {
        BondInvtBsc bondInvtBsc = new BondInvtBsc();

        bondInvtBsc.setInvt_preent_no(recBondInvtInvAppr.getInv_preent_no());
        bondInvtBsc.setBond_invt_no(recBondInvtInvAppr.getBusiness_id());
        bondInvtBsc.setEntry_no(recBondInvtInvAppr.getEntry_seq_no());
        bondInvtBsc.setReturn_status(recBondInvtInvAppr.getManage_result());
        bondInvtBsc.setReturn_time(recBondInvtInvAppr.getCreate_date());
        bondInvtBsc.setReturn_info(recBondInvtInvAppr.getReason());
        bondInvtBsc.setEtps_inner_invt_no(bondInvtBscData.getEtps_inner_invt_no());

        //更新核注清单表表头数据状态
        this.receiptMapper.updateBondInvtStatusByInvAppr(bondInvtBsc);

        //更新核注清单表表体数据状态
        if (bondInvtBscData.getFlag().equals("EXIT")) {
            this.receiptMapper.updateNemssByInvAppr(bondInvtBsc);
        }
        //更新核注清单表表体数据状态
        if (bondInvtBscData.getFlag().equals("ENTER")) {
            this.receiptMapper.updateBondInvtDtByHdeAppr(bondInvtBsc);
        }

    }


    /**
     * 核放单(报文回执/审核回执/过卡)（进口保税）
     * 报文类型 : SAS221  SAS223
     */
    @Transactional(rollbackFor = NullPointerException.class)
    private void createPassPortHdeAppr(Map<String, List<List<Map<String, String>>>> receipt, String refileName, String type) throws Exception {
        List<List<Map<String, String>>> list = receipt.get("HdeApprResult");
        if (!StringUtils.isEmpty(list)) {
            RecPassPortHdeAppr recPassPortHdeAppr;
            for (int i = 0; i < list.size(); i++) {
                recPassPortHdeAppr = new RecPassPortHdeAppr();
                recPassPortHdeAppr.setGuid(IdUtils.getUUId());
                recPassPortHdeAppr.setCrt_tm(new Date());
                recPassPortHdeAppr.setUpd_tm(new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<Map<String, String>> mapList = list.get(i);
                for (Map<String, String> map : mapList) {
                    if (map.containsKey("etpsPreentNo")) {
                        recPassPortHdeAppr.setEtps_preent_no(map.get("etpsPreentNo"));
                    }
                    if (map.containsKey("businessId")) {
                        recPassPortHdeAppr.setBusiness_id(map.get("businessId"));
                    }
                    if (map.containsKey("tmsCnt")) {
                        recPassPortHdeAppr.setTms_cnt(map.get("tmsCnt"));
                    }
                    if (map.containsKey("typecd")) {
                        recPassPortHdeAppr.setTypecd(map.get("typecd"));
                    }
                    if (map.containsKey("manageResult")) {
                        recPassPortHdeAppr.setManage_result(type + "_" + map.get("manageResult"));
                    }
                    if (map.containsKey("manageDate")) {
                        recPassPortHdeAppr.setManage_date(sdf.parse(map.get("manageDate")));
                    }
                    if (map.containsKey("rmk")) {
                        recPassPortHdeAppr.setRmk(map.get("rmk"));
                    }
                }
                this.receiptMapper.createPassPortHdeAppr(recPassPortHdeAppr); //插入核放单回执表数据

                Date returnTime = recPassPortHdeAppr.getManage_date();
                String etpsPreentNo = recPassPortHdeAppr.getEtps_preent_no();
                PassPortHead passPortHead = this.receiptMapper.queryPassPort(etpsPreentNo);
                Date systemTime;
                if (!StringUtils.isEmpty(passPortHead)) {
                    systemTime = passPortHead.getReturn_date();
                    if (systemTime == null || (returnTime.getTime() >= systemTime.getTime())) {
                        this.updatePassportStatusByHdeAppr(recPassPortHdeAppr, passPortHead);//更新核放单表数据
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
     * 根据核放单回执更新表数据（进口保税）
     * 报文类型 : SAS221  SAS223
     */
    private void updatePassportStatusByHdeAppr(RecPassPortHdeAppr recPassPortHdeAppr, PassPortHead passPortHd) throws Exception {
        PassPortHead passPortHead = new PassPortHead();
        passPortHead.setSas_passport_preent_no(recPassPortHdeAppr.getEtps_preent_no());
        passPortHead.setPassport_no(recPassPortHdeAppr.getBusiness_id());
        passPortHead.setChg_tms_cnt(Integer.parseInt(recPassPortHdeAppr.getTms_cnt()));
        passPortHead.setDcl_typecd(recPassPortHdeAppr.getTypecd());
        passPortHead.setReturn_status(recPassPortHdeAppr.getManage_result());
        passPortHead.setReturn_date(recPassPortHdeAppr.getManage_date());
        passPortHead.setReturn_info(recPassPortHdeAppr.getRmk());
        this.receiptMapper.updatePassportStatusByHdeAppr(passPortHead);  //更新核放表表头数据状态

        if (passPortHd.getFlag().equals("EXIT")) {
            //保税出区一车一票
            this.receiptMapper.updatePassPortAcmpByHdeAppr(passPortHead);  //更新核放单表表体数据状态
        }

        if (passPortHd.getFlag().equals("ENTER")) {
            switch (passPortHd.getBind_typecd()) {
                //保税入区一车一票
                case "2":
                    this.receiptMapper.updatePassPortAcmpByHdeAppr(passPortHead);
                    break;
                //保税入区一车多票
                case "1":
                    this.receiptMapper.updatePassPortAcmpByHdeAppr(passPortHead);
                    break;
                //保税入区一票多车
                case "3":
                    this.receiptMapper.updatePassPortListByHdeAppr(passPortHead);
                    break;
            }
        }

        //实增操作
        if (passPortHd.getFlag().equals("ENTER") && (recPassPortHdeAppr.getManage_result().equals("SAS223_1"))) {
            //TODO 保税入区进行实增操作
            CountLoader countLoader = new CountActlIncrease();
            countLoader.count(passPortHd);
        }
    }


    /**
     * 插入订单回执报文数据（进口跨境直购）
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
     * 根据订单回执更新订单状态（进口跨境直购）
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

        String type = this.receiptMapper.queryBusiTypeByOrderNo(impRecOrder.getOrderNo());
        switch (type) {
            case SystemConstants.T_IMP_ORDER:
                impOrderHead.setData_status(StatusCode.DDSBCG);
                break;
            case SystemConstants.T_IMP_BOND_ORDER:
                impOrderHead.setData_status(StatusCode.BSDDSBCG);
                break;
        }
        this.receiptMapper.updateImpOrder(impOrderHead);
    }


    /**
     * 插入支付单回执报文数据（进口跨境直购）
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
     * 根据支付单回执更新支付单表状态（进口跨境直购）
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
     * 插入运单回执报文数据（进口跨境直购）
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
     * 根据运单回执更新运单（进口跨境直购）
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
     * 插入运单状态回执报文数据（进口跨境直购）
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
     * 根据运单状态回执更新运单状态表（进口跨境直购）
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
     * 将运单表置为“CBDS52”状态（运单申报成功）（进口跨境直购）
     */
    private void updateImpLogisticsDataStatus(ImpRecLogisticsStatus impRecLogisticsStatus, String ydztsbcg) {
        this.receiptMapper.updateImpLogisticsDataStatus(impRecLogisticsStatus, ydztsbcg);
    }


    /**
     * 插入清单回执报文数据（进口跨境直购）
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
     * 根据清单回执更新清单表（进口跨境直购）
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
     * 插入入库明细单回执报文数据（进口跨境直购）
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
     * 根据入库明细单回执更新入库明细数据（进口跨境直购）
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
