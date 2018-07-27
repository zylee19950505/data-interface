package com.xaeport.crossborder.service.receipt;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 回值解析
 * Created by Administrator on 2017/7/25.
 */
@Service
public class ReceiptService {
    private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    ReceiptMapper receiptMapper;

//    @Transactional(rollbackForClassName = "Exception")
//    public boolean createReceipt(Map map, String refileName) {
//        boolean flag = true;
//        try {
//            String type = (String) map.get("type");
//            Map<String, List<List<Map<String, String>>>> receipt = (Map<String, List<List<Map<String, String>>>>) map.get("Receipt");
//            EnvelopInfo envelopInfo = this.getEnvelopInfo(receipt, refileName);
//            this.receiptMapper.createReceiptEnvelop(envelopInfo);//插入回值信封
//            switch (type) {
//                case "EXP310"://税费回执
//                    TaxReceiptRecord taxReceiptRecord = this.createTaxReceiptRecord(receipt, envelopInfo, refileName);
//                    this.receiptMapper.createTaxReceiptRecord(taxReceiptRecord);//插入税费回执
//                    EntryHead entryHead = this.updateEntryHead(taxReceiptRecord);
//                    this.receiptMapper.updateEntryHead(entryHead);//更新Entryhead
//                    break;
//                case "EXP312"://舱单回执
//                    ManifestHeadReceipt manifestHeadReceipt = this.createManifestHeadReceipt(receipt, envelopInfo, refileName);
//                    this.receiptMapper.createManifestHeadReceipt(manifestHeadReceipt); //插入舱单数据表头
//                    this.createManifestListReceipt(receipt, manifestHeadReceipt, refileName);//插入舱单数据表体
//                    this.updateManifestStatus(manifestHeadReceipt);    //更新舱单状态
//                    break;
//                case "EXP302"://报单回值
//                    DeclareReceipt declareReceipt = this.createDeclareReceipt(receipt, envelopInfo, refileName);
//                    String dbOpTime = this.receiptMapper.getOpTime(declareReceipt.getBill_no(), declareReceipt.getAss_bill_no());//数据中的操作时间
//                    //this.logger.debug("开始插入报关单回执");
//                    this.receiptMapper.createDeclareReceipt(declareReceipt);//插入报关单回执
//                    //this.logger.debug("完成插入报关单回执，开始插入更新状态");
//                    this.updateDeclareStatus(declareReceipt, envelopInfo, dbOpTime);//更新状态
//                    //this.logger.debug("完成插入更新状态");
//                    break;
//            }
//        } catch (Exception e) {
//            flag = false;
//            this.logger.error(String.format("报文回执入库失败,文件名为:%s", refileName), e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//        return flag;
//    }
//
//    /**
//     * 封装回执信封数据
//     *
//     * @param receipt 回值报文中的数据
//     * @return 返回 EnvelopInfo 对象
//     */
//    private EnvelopInfo getEnvelopInfo(Map<String, List<List<Map<String, String>>>> receipt, String refileName) {
//        EnvelopInfo envelopInfo = new EnvelopInfo();
//        List<Map<String, String>> list = receipt.get("EnvelopInfo").get(0);
//        for (Map<String, String> map : list) {
//            envelopInfo.setRe_id(IdUtils.getUUId());
//            envelopInfo.setRefile_name(refileName);
//            if (map.containsKey("version")) {
//                envelopInfo.setVersion(map.get("version"));
//            }
//            if (map.containsKey("file_name")) {
//                envelopInfo.setFile_name(map.get("file_name"));
//            }
//            if (map.containsKey("message_id")) {
//                envelopInfo.setMessage_id(map.get("message_id"));
//            }
//            if (map.containsKey("message_type")) {
//                envelopInfo.setMessage_type(map.get("message_type"));
//            }
//            if (map.containsKey("sender_id")) {
//                envelopInfo.setSender_id(map.get("sender_id"));
//            }
//            if (map.containsKey("receiver_id")) {
//                envelopInfo.setReceiver_id(map.get("receiver_id"));
//            }
//            if (map.containsKey("send_time")) {
//                envelopInfo.setSend_time(map.get("send_time"));
//            }
//            envelopInfo.setCreate_time(new Date());
//        }
//        return envelopInfo;
//    }
//
//    /**
//     * 税费回执
//     *
//     * @param receipt     回执数据
//     * @param envelopInfo 信封数据
//     */
//    private TaxReceiptRecord createTaxReceiptRecord(Map<String, List<List<Map<String, String>>>> receipt, EnvelopInfo envelopInfo, String refileName) {
//        TaxReceiptRecord taxReceiptRecord = new TaxReceiptRecord();
//        List<Map<String, String>> list = receipt.get("EntryDuty").get(0);
//        String reId = envelopInfo.getRe_id();
//        for (Map<String, String> map : list) {
//            taxReceiptRecord.setTr_id(IdUtils.getUUId());
//            taxReceiptRecord.setRe_id(reId);
//            taxReceiptRecord.setRefile_name(refileName);
//            if (map.containsKey("BillNo")) {
//                taxReceiptRecord.setBill_no(map.get("BillNo"));
//            }
//            if (map.containsKey("AssBillNo")) {
//                taxReceiptRecord.setAss_bill_no(map.get("AssBillNo"));
//            }
//            if (map.containsKey("IEFlag")) {
//                taxReceiptRecord.setI_e_flag(map.get("IEFlag"));
//            }
//            if (map.containsKey("EntryId")) {
//                taxReceiptRecord.setEntry_id(map.get("EntryId"));
//            }
//            if (map.containsKey("OpTime")) {
//                taxReceiptRecord.setOp_time(map.get("OpTime"));
//            }
//            if (map.containsKey("RealDuty")) {
//                taxReceiptRecord.setReal_duty(Double.parseDouble(map.get("RealDuty")));
//            }
//            if (map.containsKey("RealTax")) {
//                taxReceiptRecord.setReal_tax(Double.parseDouble(map.get("RealTax")));
//            }
//            if (map.containsKey("RealReg")) {
//                taxReceiptRecord.setReal_reg(Double.parseDouble(map.get("RealReg")));
//            }
//            if (map.containsKey("RealAnti")) {
//                taxReceiptRecord.setReal_anti(Double.parseDouble(map.get("RealAnti")));
//            }
//            if (map.containsKey("RealRsv1")) {
//                taxReceiptRecord.setReal_rsv1(Double.parseDouble(map.get("RealRsv1")));
//            }
//            if (map.containsKey("RealRsv2")) {
//                taxReceiptRecord.setReal_rsv2(Double.parseDouble(map.get("RealRsv2")));
//            }
//            if (map.containsKey("RealNcad")) {
//                taxReceiptRecord.setReal_ncad(Double.parseDouble(map.get("RealNcad")));
//            }
//        }
//        return taxReceiptRecord;
//    }
//
//    /**
//     * EntryHead数据
//     *
//     * @param taxReceiptRecord 税费
//     * @return 返回 EntryHead对象
//     */
//    private EntryHead updateEntryHead(TaxReceiptRecord taxReceiptRecord) {
//        EntryHead entryHead = new EntryHead();
//        entryHead.setBill_no(taxReceiptRecord.getBill_no());//总运单号
//        entryHead.setAss_bill_no(taxReceiptRecord.getAss_bill_no());//分单号
//        entryHead.setEntry_id(taxReceiptRecord.getEntry_id());//报关单号
//        entryHead.setReal_duty(taxReceiptRecord.getReal_duty());//实征关税额
//        entryHead.setReal_tax(taxReceiptRecord.getReal_tax());//实征增值税额
//        entryHead.setReal_reg(taxReceiptRecord.getReal_reg());//实征消费税额
//        entryHead.setReal_anti(taxReceiptRecord.getReal_anti());//实征反倾销税额
//        entryHead.setReal_rsv1(taxReceiptRecord.getReal_rsv1());//实征反补贴税款
//        entryHead.setReal_rsv2(taxReceiptRecord.getReal_rsv2());//实征废旧基金
//        entryHead.setReal_ncad(taxReceiptRecord.getReal_ncad());//
//        return entryHead;
//    }
//
//    /**
//     * 报关单回执
//     *
//     * @param receipt     回执数据
//     * @param envelopInfo 回执信封
//     * @param refileName  回执文件名
//     * @return 返回DeclareReceipt 对象
//     */
//    private DeclareReceipt createDeclareReceipt(Map<String, List<List<Map<String, String>>>> receipt, EnvelopInfo envelopInfo, String refileName) {
//        DeclareReceipt declareReceipt = new DeclareReceipt();
//        List<Map<String, String>> list = receipt.get("EntryHead").get(0);
//        String reId = envelopInfo.getRe_id();
//        declareReceipt.setDr_id(IdUtils.getUUId());
//        declareReceipt.setRe_id(reId);
//        declareReceipt.setRefile_name(refileName);
//        for (Map<String, String> map : list) {
//            if (map.containsKey("PreEntryId")) {
//                declareReceipt.setPre_entry_id(map.get("PreEntryId"));
//            }
//            if (map.containsKey("OpType")) {
//                declareReceipt.setOptype(map.get("OpType"));
//            }
//            if (map.containsKey("BillNo")) {
//                declareReceipt.setBill_no(map.get("BillNo"));
//            }
//            if (map.containsKey("AssBillNo")) {
//                declareReceipt.setAss_bill_no(map.get("AssBillNo"));
//            }
//            if (map.containsKey("IEFlag")) {
//                declareReceipt.setI_e_flag(map.get("IEFlag"));
//            }
//            if (map.containsKey("EntryId")) {
//                declareReceipt.setEntry_id(map.get("EntryId"));
//            }
//            if (map.containsKey("OpTime")) {
//                declareReceipt.setOp_time(map.get("OpTime"));
//            }
//            if (map.containsKey("OpResult")) {
//                declareReceipt.setOp_result(map.get("OpResult"));
//            }
//            if (map.containsKey("Notes")) {
//                declareReceipt.setNotes(map.get("Notes"));
//            }
//        }
//        return declareReceipt;
//    }
//
//    /**
//     * 根据报单回执更新报关单状态
//     *
//     * @param declareReceipt
//     */
//    private void updateDeclareStatus(DeclareReceipt declareReceipt, EnvelopInfo envelopInfo, String dbOpTime) throws Exception {
//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date sendTime = sd.parse(envelopInfo.getSend_time());
//
//        String result = declareReceipt.getOp_result();
//        String billNo = declareReceipt.getBill_no();
//        String assBillNo = declareReceipt.getAss_bill_no();
//        String entryId = declareReceipt.getEntry_id();
//        String notes = declareReceipt.getNotes();
//        String reOpTime = declareReceipt.getOp_time();//回执报文中的操作时间
//        long re_op = 0;
//        long db_op;
//        if (!StringUtils.isEmpty(reOpTime)) {
//            re_op = Long.parseLong(reOpTime);
//        }
//
//        //this.logger.debug("主单号：" + declareReceipt.getBill_no() + " 分单号：" + declareReceipt.getAss_bill_no() + " 回执申报状态：" + declareReceipt.getOp_result() + "回执备注" + declareReceipt.getNotes());
//        if (dbOpTime != null) {
//            db_op = Long.parseLong(dbOpTime);
//            //this.logger.debug("数据库中的操作时间：" + dbOpTime + " 回执报文中的操作时间：" + reOpTime);
//            if (re_op < db_op) return;
//        }
//        //this.logger.debug("数据库中的操作时间：" + dbOpTime + " 回执报文中的操作时间：" + reOpTime);
//        //this.logger.debug("回执报文的操作时间>=数据库的操作时间 ,可以修改");
//        EntryHead entryHead = new EntryHead();
//        entryHead.setBill_no(billNo);//主单号
//        entryHead.setAss_bill_no(assBillNo);//分单号
//        entryHead.setEntry_id(entryId);//报关单
//        entryHead.setDeclare_status(result);//回执申报状态
//        entryHead.setDeclare_result(notes);//回执备注信息
//
//        //根据报文回执顺序进行判断是否更新对应的状态
//        switch (result) {
//            //D0 中心入库成功
//            case StatusCode.ZXRKCG: {
//                entryHead.setUpdate_time(sendTime);
//                this.receiptMapper.updateDeclare(entryHead);
//                break;
//            }
//            //D2 发往海关成功
//            case StatusCode.FWHGCG: {
//                entryHead.setUpdate_time(sendTime);
//                this.receiptMapper.updateDeclare(entryHead);
//                break;
//            }
//            //05 查验
//            case StatusCode.CY: {
//                entryHead.setUpdate_time(sendTime);
//                entryHead.setIs_check("Y");
//                entryHead.setOp_status(StatusCode.BDSBCG);//SWOP32 报单申报成功
//                this.createDeclareStatusRecord(declareReceipt);
//                this.receiptMapper.updateCheckDeclare(entryHead);
//                break;
//            }
//            //D1 中心入库失败
//            case StatusCode.ZXRKSB: {
//                entryHead.setUpdate_time(sendTime);
//                entryHead.setOp_status(StatusCode.BDSBSB);//SWOP33 报单申报失败
//                this.receiptMapper.updateDeclareStatus(entryHead);
//                this.createDeclareStatusRecord(declareReceipt);//创建状态记录数据
//                break;
//            }
//            //N 新增、修改、追加失败
//            case StatusCode.XZXGZJSB: {
//                entryHead.setUpdate_time(sendTime);
//                entryHead.setOp_status(StatusCode.BDSBSB);//SWOP33 报单申报失败
//                this.receiptMapper.updateDeclareStatus(entryHead);
//                this.createDeclareStatusRecord(declareReceipt);//创建状态记录数据
//                break;
//            }
//            //D3 发往海关失败
//            case StatusCode.FWHGSB: {
//                entryHead.setUpdate_time(sendTime);
//                entryHead.setOp_status(StatusCode.BDSBSB);//SWOP33 报单申报失败
//                this.receiptMapper.updateDeclareStatus(entryHead);
//                this.createDeclareStatusRecord(declareReceipt);//创建状态记录数据
//                break;
//            }
//            default: {
//                entryHead.setUpdate_time(sendTime);
//                entryHead.setOp_status(StatusCode.BDSBCG);//SWOP32 报单申报成功
//                this.createDeclareStatusRecord(declareReceipt);
//                if (StatusCode.isContain(result)) {//放行
//                    entryHead.setIs_clearance("Y");
//                    this.receiptMapper.updateClearanceDeclare(entryHead);
//                } else {
//                    this.receiptMapper.updateDeclareStatus(entryHead);
//                }
//                break;
//            }
//        }
//
//    }
//
//    /**
//     * 当报单回执为成功或者失败时，给状态记录表中插入数据
//     *
//     * @param declareReceipt
//     * @throws Exception
//     */
//    private void createDeclareStatusRecord(DeclareReceipt declareReceipt) throws Exception {
//        StatusRecord statusRecord = new StatusRecord();
//        statusRecord.setSr_id(IdUtils.getUUId());
//        statusRecord.setStatus_code(declareReceipt.getOp_result());//状态代码
//        statusRecord.setBelong("declare_receipt");//报单回执记录表
//        statusRecord.setOdd_no(declareReceipt.getAss_bill_no());//分单号
//        statusRecord.setCreate_time(new Date());//改变时间
//        statusRecord.setNotes(declareReceipt.getNotes());//备注
//
//        //向状态记录表中插入数据
//        this.receiptMapper.createStatusRecord(statusRecord);
//    }
//
//    /**
//     * 插入舱单数据表头
//     *
//     * @param receipt     回执数据
//     * @param envelopInfo 信封数据
//     * @param refileName  回执文件名
//     * @throws Exception
//     */
//    @Transactional(rollbackFor = NullPointerException.class)
//    private ManifestHeadReceipt createManifestHeadReceipt(Map<String, List<List<Map<String, String>>>> receipt, EnvelopInfo envelopInfo, String refileName) throws Exception {
//        List<Map<String, String>> list = receipt.get("ExpMftHead").get(0);
//        ManifestHeadReceipt manifestHeadReceipt = new ManifestHeadReceipt();
//        String reId = envelopInfo.getRe_id();
//        manifestHeadReceipt.setMhr_id(IdUtils.getUUId());
//        manifestHeadReceipt.setRe_id(reId);
//        manifestHeadReceipt.setRefile_name(refileName);
//        for (Map<String, String> map : list) {
//            if (map.containsKey("BillNo")) {
//                manifestHeadReceipt.setBill_no(map.get("BillNo"));
//            }
//            if (map.containsKey("VoyageNo")) {
//                manifestHeadReceipt.setVoyage_no(map.get("VoyageNo"));
//            }
//            if (map.containsKey("EntryDate")) {
//                manifestHeadReceipt.setEntry_date(map.get("EntryDate"));
//            }
//            if (map.containsKey("RtnFlag")) {
//                manifestHeadReceipt.setRtn_flag(map.get("RtnFlag"));
//            }
//            if (map.containsKey("Notes")) {
//                manifestHeadReceipt.setNotes(map.get("Notes"));
//            }
//        }
//
//        return manifestHeadReceipt;
//    }
//
//    /**
//     * 向舱单表体中插入数据
//     *
//     * @param receipt             回执数据
//     * @param manifestHeadReceipt 舱单数据表头
//     * @param refileName          回执文件名
//     * @throws Exception
//     */
//    private void createManifestListReceipt(Map<String, List<List<Map<String, String>>>> receipt, ManifestHeadReceipt manifestHeadReceipt, String refileName) throws Exception {
//        List<List<Map<String, String>>> list = receipt.get("ExpMftList");
//        if (!StringUtils.isEmpty(list)) {
//            ManifestListReceipt manifestListReceipt;
//            String mhrId = manifestHeadReceipt.getMhr_id();//舱单表头ID
//            for (int i = 0, length = list.size(); i < length; i++) {
//                manifestListReceipt = new ManifestListReceipt();
//                manifestListReceipt.setMlr_id(IdUtils.getUUId());//主键ID
//                manifestListReceipt.setMhr_id(mhrId);//舱单表头ID
//                manifestListReceipt.setRefile_name(refileName);//回执文件名
//
//                List<Map<String, String>> mapList = list.get(i);
//                for (Map<String, String> map : mapList) {
//                    if (map.containsKey("BillNo")) {
//                        manifestListReceipt.setBill_no(map.get("BillNo"));
//                    }
//                    if (map.containsKey("AssBillNo")) {
//                        manifestListReceipt.setAss_bill_no(map.get("AssBillNo"));
//                    }
//                    if (map.containsKey("EntryDate")) {
//                        manifestListReceipt.setEntry_date(map.get("EntryDate"));
//                    }
//                    if (map.containsKey("RtnFlag")) {
//                        manifestListReceipt.setRtn_flag(map.get("RtnFlag"));
//                    }
//                    if (map.containsKey("Notes")) {
//                        manifestListReceipt.setNotes(map.get("Notes"));
//                    }
//                }
//                //插入舱单表体数据
//                this.receiptMapper.createManifestListReceipt(manifestListReceipt);
//            }
//        }
//    }
//
//    /**
//     * 根据舱单回执更新报关舱单状态
//     *
//     * @param manifestheadreceipt 舱单数据对象
//     */
//    private void updateManifestStatus(ManifestHeadReceipt manifestheadreceipt) throws Exception {
//        EntryHead entryHead = new EntryHead();
//        entryHead.setBill_no(manifestheadreceipt.getBill_no());//主单号
//        entryHead.setManifest_status(manifestheadreceipt.getRtn_flag());//舱单状态
//        entryHead.setDeclare_result(manifestheadreceipt.getNotes());//舱单回执备注信息
//        String result = manifestheadreceipt.getRtn_flag();
//        //当回执结果为海关入库成功NY/失败NN时，需要向记录表中插入数据
//        if (result.equals(StatusCode.HGRKCG)) {//海关入库成功
//            entryHead.setOp_status(StatusCode.BDSB);
//            this.receiptMapper.updateManifestStatus(entryHead);
//            this.createManiFestStatusRecord(manifestheadreceipt);//向状态记录表中插入数据
//        } else if (result.equals(StatusCode.HGRKSB)) {//海关入库失败
//            entryHead.setOp_status(StatusCode.CDCB);
//            this.receiptMapper.updateManifestStatus(entryHead);
//            this.createManiFestStatusRecord(manifestheadreceipt);//向状态记录表中插入数据
//        } else {
//            this.receiptMapper.updateManifest(entryHead);  //更新EntryHead表中的舱单状态
//        }
//    }
//
//    /**
//     * 向状态记录表中插入数据
//     *
//     * @param manifestHeadReceipt 舱单回执
//     * @throws Exception
//     */
//    private void createManiFestStatusRecord(ManifestHeadReceipt manifestHeadReceipt) throws Exception {
//        StatusRecord statusRecord = new StatusRecord();
//        statusRecord.setSr_id(IdUtils.getUUId());
//        statusRecord.setStatus_code(manifestHeadReceipt.getRtn_flag());//状态代码
//        statusRecord.setBelong("manifest_head_receipt");//舱单数据表体
//        statusRecord.setOdd_no(manifestHeadReceipt.getBill_no());//分单号
//        statusRecord.setCreate_time(new Date());//改变时间
//        statusRecord.setNotes(manifestHeadReceipt.getNotes());//备注
//        this.receiptMapper.createStatusRecord(statusRecord);//向状态记录表中插入数据
//    }
}
