package com.xaeport.crossborder.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.OrderNo;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.mapper.BondOrderImpMapper;
import com.xaeport.crossborder.data.mapper.VerificationMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.IdUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.entity.ImpBDHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.processor.VerificationProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Random;

public class VerificationThreadBond implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());

    private VerificationProcessor verificationProcessor = new VerificationProcessor();
    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);
    private BondOrderImpMapper bondOrderImpMapper = SpringUtils.getBean(BondOrderImpMapper.class);

    @Override
    public void run() {
        while (true) {
            // 判断是否有待校验数据
            if (!ProcesserThread.hasVerificationBondData()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("保税验证线程等待时发生异常", e);
                }
                continue;
            }
            // 每次从待处理队列中获取一条数据
            ImpBDHeadVer impBDHeadVer = new ImpBDHeadVer();
            Verify verify = new Verify();
            try {
                impBDHeadVer = ProcesserThread.takePendingQueueBondData();
                // 判断当前处理对象是否在处理队列中
                if (ProcesserThread.getProcessBondQueue().contains(impBDHeadVer)) {
                    // 处理队列中存在则跳过
                    logger.debug("获取的保税数据在处理队列中 " + impBDHeadVer);
                    continue;
                } else {
                    // 处理队列中不存在则添加到处理队列中
                    ProcesserThread.getProcessBondQueue().add(impBDHeadVer);
                }

                // 获取校验结果
                VerificationResult verificationResult = verificationProcessor.excuteBondVerification(impBDHeadVer);
                if (verificationResult == null) {
                    ProcesserThread.getProcessBondQueue().remove(impBDHeadVer);
                    logger.debug("保税校验结果为NULL,移除校验数据");
                    continue;
                }

                // 封装校验实体数据
                verify = this.packageBondVerify(impBDHeadVer, verificationResult);
                try {
                    // 添加校验状态及记录
                    verificationMapper.insertVerifyRecordByVerify(verify);// 添加校验记录
                    verificationMapper.insertVerifyStatus(verify);// 添加校验状态
                    //1.保税订单逻辑校验通过后，更新为保税订单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_IMP_BOND_ORDER.equals(impBDHeadVer.getBusiness_type())) {
                        verificationMapper.updateBondOrderStatus(verify.getCb_head_id(), StatusCode.BSDDDSB);
                        this.insertOrderNo(verify);
                    }
                    //2.保税清单逻辑校验通过后，更新为保税清单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_IMP_BOND_INVEN.equals(impBDHeadVer.getBusiness_type())) {
                        verificationMapper.updateBondInvenStatus(verify.getCb_head_id(), StatusCode.BSQDDSB);
                    }
                    //3.入区核注清单逻辑校验通过后，更新为入区核注清单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_BOND_INVT.equals(impBDHeadVer.getBusiness_type()) && SystemConstants.BSRQ.equals(impBDHeadVer.getFlag())) {
                        verificationMapper.updateBondInvtStatus(verify.getCb_head_id(), StatusCode.RQHZQDDSB);
                    }
                    //4.入区核放单逻辑校验通过后，更新为入区核放单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_PASS_PORT.equals(impBDHeadVer.getBusiness_type()) && SystemConstants.BSRQ.equals(impBDHeadVer.getFlag())) {
                        verificationMapper.updatePassPortStatus(verify.getCb_head_id(), StatusCode.RQHFDDSB);
                    }
                    //5.出区核注清单逻辑校验通过后，更新为出区核注清单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_BOND_INVT.equals(impBDHeadVer.getBusiness_type()) && SystemConstants.BSCQ.equals(impBDHeadVer.getFlag())) {
                        verificationMapper.updateBondInvtStatus(verify.getCb_head_id(), StatusCode.CQHZQDDSB);
                    }
                    //6.出区核放单逻辑校验通过后，更新为出区核放单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_PASS_PORT.equals(impBDHeadVer.getBusiness_type()) && SystemConstants.BSCQ.equals(impBDHeadVer.getFlag())) {
                        verificationMapper.updatePassPortStatus(verify.getCb_head_id(), StatusCode.CQHFDDSB);
                    }
                } catch (Exception e) {
                    logger.error(String.format("更新保税业务数据时发生异常:[guid = %s,order_no = %s,type = %s,code = %s,status = %s,result = %s]", impBDHeadVer.getGuid(), impBDHeadVer.getOrder_no(), verify.getType(), verify.getCode(), verify.getStatus(), verify.getResult()), e);
                }
            } catch (InterruptedException e) {
                logger.error(String.format("验证保税线程读取待校验数据时发生异常:[guid = %s,order_no = %s,type = %s,code = %s,status = %s,result = %s]", impBDHeadVer.getGuid(), impBDHeadVer.getOrder_no(), verify.getType(), verify.getCode(), verify.getStatus(), verify.getResult()), e);
            } catch (JsonProcessingException e) {
                logger.error(String.format("封装保税校验实体做JSON转换时发生异常:[guid = %s,order_no = %s,type = %s,code = %s,status = %s,result = %s]", impBDHeadVer.getGuid(), impBDHeadVer.getOrder_no(), verify.getType(), verify.getCode(), verify.getStatus(), verify.getResult()), e);
            } finally {
                ProcesserThread.getProcessBondQueue().remove(impBDHeadVer);
            }
        }
    }

    private void insertOrderNo(Verify verify) {
        String billNo = verify.getBill_no();
        String brevityCode = billNo.substring(0, 2);
        Integer sum = this.bondOrderImpMapper.queryEntInfoByBrevityCode(brevityCode);
        if (sum > 0) {
            OrderNo orderNo = new OrderNo();
            orderNo.setId(IdUtils.getUUId());
            orderNo.setOrder_no(verify.getOrder_no());
            orderNo.setCrt_tm(new Date());
            orderNo.setUsed("0");
            this.bondOrderImpMapper.insertOrderNo(orderNo);
        }
    }

    // Verify 校验实体数据封装
    private Verify packageBondVerify(ImpBDHeadVer impBDHeadVer, VerificationResult verificationResult) throws JsonProcessingException {
        Verify verify = new Verify();
        String code = null;
        String id = null;
        String billNo = null;
        switch (impBDHeadVer.getBusiness_type()) {
            case SystemConstants.T_IMP_BOND_ORDER:
                code = impBDHeadVer.getOrder_no();
                id = impBDHeadVer.getGuid();
                billNo = impBDHeadVer.getBill_no();
                break;
            case SystemConstants.T_IMP_BOND_INVEN:
                code = impBDHeadVer.getOrder_no();
                id = impBDHeadVer.getGuid();
                billNo = impBDHeadVer.getBill_no();
                break;
            case SystemConstants.T_BOND_INVT:
                code = impBDHeadVer.getEtps_inner_invt_no();
                id = impBDHeadVer.getId();
                billNo = "";
                break;
            case SystemConstants.T_PASS_PORT:
                code = impBDHeadVer.getEtps_preent_no();
                id = impBDHeadVer.getId();
                billNo = "";
                break;
        }
        // 生成订单号：表头ID（36位）- 业务类型 - 业务数据编码（最长60位） - yyyymmddhhMMssSSS（17位）+r（1位随机数）
        String cb_verify_no = id + "-" + impBDHeadVer.getBusiness_type() + "-" + code + "-" + DateTools.getDateTimeStr17String(new Date()) + new Random().nextInt(10);
        verify.setVs_id(IdUtils.getUUId());
        verify.setVr_id(IdUtils.getUUId());
        verify.setCb_verify_no(cb_verify_no);
        verify.setCb_head_id(id);
        verify.setBill_no(billNo);
        verify.setOrder_no(code);
        verify.setType(VerifyType.LOGIC);
        verify.setCode("SW001");
        if (verificationResult.hasError()) {
            String errorMsg = verificationResult.getErrorMsg();
            String errorField = verificationResult.getErrorFiled();
            String errorGno = verificationResult.getErrorGno();

            String json = "{";
            json += "\"message\":\"" + errorMsg + "\"";
            if (!StringUtils.isEmpty(errorField)) {
                json += ", \"field\":\"" + errorField + "\"";
            }
            if (!StringUtils.isEmpty(errorGno)) {
                json += ", \"g_num\":\"" + errorGno + "\"";
            }
            json += "}";
            verify.setResult(json);
            verify.setStatus(VerifyType.NOTPASS);

        } else {
            verify.setStatus(VerifyType.PASS);
            verify.setResult("逻辑校验通过");
        }
        verify.setEnterprise_id(impBDHeadVer.getCrt_ent_id());
        verify.setEntry_message(new ObjectMapper().writeValueAsString(impBDHeadVer));
        return verify;
    }

}
