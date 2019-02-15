package com.xaeport.crossborder.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.StatusRecord;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.mapper.VerificationMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.data.status.VerifyType;
import com.xaeport.crossborder.tools.DateTools;
import com.xaeport.crossborder.tools.IdUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import com.xaeport.crossborder.verification.entity.ImpCBHeadVer;
import com.xaeport.crossborder.verification.entity.VerificationResult;
import com.xaeport.crossborder.verification.processor.VerificationProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Random;

public class VerificationThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());

    private VerificationProcessor verificationProcessor = new VerificationProcessor();
    private VerificationMapper verificationMapper = SpringUtils.getBean(VerificationMapper.class);

    @Override
    public void run() {
        while (true) {
            // 判断是否有待校验数据
            if (!ProcesserThread.hasVerificationData()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("验证线程等待时发生异常", e);
                }
                continue;
            }
            // 每次从待处理队列中获取一条数据
            ImpCBHeadVer impCBHeadVer = new ImpCBHeadVer();
            Verify verify = new Verify();
            try {
                impCBHeadVer = ProcesserThread.takePendingQueueData();
                // 判断当前处理对象是否在处理队列中
                if (ProcesserThread.getProcessQueue().contains(impCBHeadVer)) {
                    // 处理队列中存在则跳过
                    logger.debug("获取的数据在处理队列中 " + impCBHeadVer);
                    continue;
                } else {
                    // 处理队列中不存在则添加到处理队列中
                    ProcesserThread.getProcessQueue().add(impCBHeadVer);
                }

                // 获取校验结果
                VerificationResult verificationResult = verificationProcessor.excuteVerification(impCBHeadVer);
                if (verificationResult == null) {
                    ProcesserThread.getProcessQueue().remove(impCBHeadVer);
                    logger.debug("校验结果为NULL,移除校验数据");
                    continue;
                }

                // 封装校验实体数据
                verify = packageVerify(impCBHeadVer, verificationResult);
                try {
                    // 添加校验状态及记录
                    verificationMapper.insertVerifyRecordByVerify(verify);// 添加校验记录
                    verificationMapper.insertVerifyStatus(verify);// 添加校验状态
                    //1.跨境进口业务（订单）逻辑校验通过后，更新为订单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_IMP_ORDER.equals(impCBHeadVer.getBusiness_type())) {
                        verificationMapper.updateOrderStatus(verify.getCb_head_id(), StatusCode.DDDSB);
                    }
                    //1.跨境进口业务（支付单）逻辑校验通过后，更新为支付单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_IMP_PAYMENT.equals(impCBHeadVer.getBusiness_type())) {
                        verificationMapper.updatePaymentStatus(verify.getCb_head_id(), StatusCode.ZFDDSB);
                    }
                    //1.跨境进口业务（运单）逻辑校验通过后，更新为运单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_IMP_LOGISTICS.equals(impCBHeadVer.getBusiness_type())) {
                        verificationMapper.updateLogisticsStatus(verify.getCb_head_id(), StatusCode.YDDSB);
                    }
                    //1.跨境进口业务（清单）逻辑校验通过后，更新为清单待申报状态
                    if (!verificationResult.hasError() && SystemConstants.T_IMP_INVENTORY.equals(impCBHeadVer.getBusiness_type())) {
                        verificationMapper.updateInventoryStatus(verify.getCb_head_id(), StatusCode.QDDSB);
                    }
                } catch (Exception e) {
                    logger.error(String.format("更新业务数据时发生异常:[guid=%s, bill_no=%s,Order_no=%s,type=%s,code=%s,status=%s,result=%s]", impCBHeadVer.getGuid(), impCBHeadVer.getBill_no(), impCBHeadVer.getOrder_no(), verify.getType(), verify.getCode(), verify.getStatus(), verify.getResult()), e);
                }
            } catch (InterruptedException e) {
                logger.error(String.format("验证线程读取待校验数据时发生异常:[guid=%s, bill_no=%s,Order_no=%s,type=%s,code=%s,status=%s,result=%s]", impCBHeadVer.getGuid(), impCBHeadVer.getBill_no(), impCBHeadVer.getOrder_no(), verify.getType(), verify.getCode(), verify.getStatus(), verify.getResult()), e);
            } catch (JsonProcessingException e) {
                logger.error(String.format("封装校验实体做JSON转换时发生异常:[guid=%s, bill_no=%s,Order_no=%s,type=%s,code=%s,status=%s,result=%s]", impCBHeadVer.getGuid(), impCBHeadVer.getBill_no(), impCBHeadVer.getOrder_no(), verify.getType(), verify.getCode(), verify.getStatus(), verify.getResult()), e);
            } finally {
                ProcesserThread.getProcessQueue().remove(impCBHeadVer);
            }
        }
    }

    // 修改 entryhead 状态表
    private void updateInventoryStatus(Verify verify, String status) throws Exception {
        try {
            // 修改状态为：舱单待申报
            this.verificationMapper.updateInventoryStatus(verify.getCb_head_id(), status);

            StatusRecord statusRecord = new StatusRecord();
            statusRecord.setSr_id(IdUtils.getUUId());
            statusRecord.setStatus_code(status);
            statusRecord.setBelong("crossBorderHead");
            statusRecord.setOdd_no(verify.getOrder_no());
            statusRecord.setNotes("数据校验-逻辑校验-分单操作状态变更");
            statusRecord.setCreate_time(new Date());
            this.verificationMapper.insertStatusRecord(statusRecord);

        } catch (Exception e) {
            this.logger.error("修改 verify_status 状态表失败,Cb_head_id=" + verify.getCb_head_id() + " ,status=" + status, e);
            throw new Exception("修改 verify_status 状态表失败,Cb_head_id=" + verify.getCb_head_id() + " ,status=" + status);
        }
    }

    // Verify 校验实体数据封装
    private Verify packageVerify(ImpCBHeadVer impCBHeadVer, VerificationResult verificationResult) throws JsonProcessingException {
        Verify verify = new Verify();
        // 生成订单号：表头ID（36位）- 业务类型 - 提运单号（最长37位）- 订单号（最长60位） - yyyymmddhhMMssSSS（17位）+r（1位随机数）
        String cb_verify_no = impCBHeadVer.getGuid() + "-" + impCBHeadVer.getBusiness_type() + "-" + impCBHeadVer.getBill_no() + "-" + impCBHeadVer.getOrder_no() + "-" + DateTools.getDateTimeStr17String(new Date()) + new Random().nextInt(10);
        verify.setVs_id(IdUtils.getUUId());
        verify.setVr_id(IdUtils.getUUId());
        verify.setCb_verify_no(cb_verify_no);
        verify.setCb_head_id(impCBHeadVer.getGuid());
        verify.setBill_no(impCBHeadVer.getBill_no());
        verify.setOrder_no(impCBHeadVer.getOrder_no());
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
        verify.setEnterprise_id(impCBHeadVer.getEnt_id());
        verify.setEntry_message(new ObjectMapper().writeValueAsString(impCBHeadVer));
        return verify;
    }


}
