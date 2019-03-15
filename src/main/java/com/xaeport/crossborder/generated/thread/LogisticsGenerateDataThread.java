package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.WaybillImportMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

public class LogisticsGenerateDataThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private WaybillImportMapper waybillImportMapper;

    //无参数的构造方法。
    public LogisticsGenerateDataThread() {
    }

    //有参数的构造方法。
    public LogisticsGenerateDataThread(WaybillImportMapper waybillImportMapper) {
        this.waybillImportMapper = waybillImportMapper;
    }

    @Override
    public void run() {
        List<OrderNo> orderNoList;
        String logisticsNo;
        String type = null;
        ImpLogistics impLogistics;
        ImpOrderHead impOrderHead;
        while (true) {

            try {
                //查询
                orderNoList = waybillImportMapper.queryOrderNoList();
                if (CollectionUtils.isEmpty(orderNoList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成运单的订单表数据，等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("生成运单生成器暂停时发生异常", e);
                    }
                    continue;
                }
                for (OrderNo orderNo : orderNoList) {
                    try {
                        impOrderHead = this.waybillImportMapper.queryOrderNoData(orderNo);
                        String billNo = impOrderHead.getBill_No();
                        if (billNo.contains("EM")) {
                            type = "EMS";
                        }
                        String brevityCode = billNo.substring(0, 2);

                        Enterprise enterprise = this.waybillImportMapper.queryEnterpriseInfo(brevityCode);
                        logisticsNo = this.waybillImportMapper.queryLogisticsNo(type);
                        impLogistics = new ImpLogistics();
                        impLogistics.setBill_no(impOrderHead.getBill_No());
                        impLogistics.setGuid(IdUtils.getUUId());
                        impLogistics.setApp_type("1");//企业报送类型。1-新增2-变更3-删除。默认为1
                        impLogistics.setApp_status("2");//业务状态:1-暂存,2-申报,默认为2
                        impLogistics.setCurrency("142");//币制
                        impLogistics.setPack_no("1");//件数
                        impLogistics.setData_status(StatusCode.YDDSB);//运单待申报
                        impLogistics.setCrt_tm(new Date());//创建时间
                        impLogistics.setUpd_tm(new Date());//更新时间
                        impLogistics.setEnt_id(StringUtils.isEmpty(enterprise.getId()) ? "暂无" : enterprise.getId());
                        impLogistics.setEnt_name(StringUtils.isEmpty(enterprise.getEnt_name()) ? "暂无" : enterprise.getEnt_name());
                        impLogistics.setEnt_customs_code(StringUtils.isEmpty(enterprise.getCustoms_code()) ? "暂无" : enterprise.getCustoms_code());
                        impLogistics.setBusiness_type(SystemConstants.T_IMP_LOGISTICS);

                        impLogistics.setOrder_no(impOrderHead.getOrder_No());
                        impLogistics.setLogistics_no(StringUtils.isEmpty(logisticsNo) ? "暂无" : logisticsNo);//物流运单编号
                        impLogistics.setLogistics_code(StringUtils.isEmpty(enterprise.getCustoms_code()) ? "暂无" : enterprise.getCustoms_code());//物流企业编号
                        impLogistics.setLogistics_name(StringUtils.isEmpty(enterprise.getEnt_name()) ? "暂无" : enterprise.getEnt_name());//物流企业名称
                        impLogistics.setConsingee(impOrderHead.getConsignee());//收货人姓名
                        impLogistics.setConsignee_telephone(impOrderHead.getConsignee_Telephone());//收货人电话
                        impLogistics.setConsignee_address(impOrderHead.getConsignee_Address());//收件地址
                        impLogistics.setNote(impOrderHead.getNote());//备注
                        impLogistics.setFreight(impOrderHead.getFreight());//运费
                        impLogistics.setInsured_fee(StringUtils.isEmpty(impOrderHead.getInsured_fee()) ? "0" : impOrderHead.getInsured_fee());//保价费
                        //设置运单货物物品重量（毛重重量）
                        if (!StringUtils.isEmpty(impOrderHead.getGross_weight())) {
                            //毛重若存在则取毛重数据
                            impLogistics.setWeight(impOrderHead.getGross_weight());
                        } else {
                            //毛重若不存在，则取净重为1.1倍数据，或为空
                            impLogistics.setWeight(StringUtils.isEmpty(impOrderHead.getNet_weight()) ? "" : String.valueOf(Double.parseDouble(impOrderHead.getNet_weight()) * 1.1));
                        }

                        try {
                            // 更新订单数据为已生成状态
                            this.waybillImportMapper.deleteOrderNo(impLogistics.getOrder_no());
                            // 更新运单编号为已生成状态
                            this.waybillImportMapper.updateLogisticsNo(impLogistics.getLogistics_no());
                            // 插入运单数据
                            this.waybillImportMapper.insertImpLogistics(impLogistics);

                            this.logger.debug(String.format("插入运单数据，订单号为: %s", impLogistics.getOrder_no()));
                        } catch (Exception e) {
                            String exceptionMsg = String.format("插入运单[order_no: %s]数据时发生异常", impLogistics.getOrder_no());
                            this.logger.error(exceptionMsg, e);
                        }
                    } catch (Exception e) {
                        String exceptionMsg = String.format("生成运单时发生异常");
                        this.logger.error(exceptionMsg, e);
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成运单时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("运单生成器暂停时发生异常", ie);
                }
            }
        }
    }

}
