package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.DeliveryDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class DeliveryDataThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private DeliveryDeclareMapper deliveryDeclareMapper;

    //无参数的构造方法。
    public DeliveryDataThread() {
    }

    //有参数的构造方法。
    public DeliveryDataThread(DeliveryDeclareMapper deliveryDeclareMapper) {
        this.deliveryDeclareMapper = deliveryDeclareMapper;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.YDZTSBCG);//在map中添加状态（dataStatus）为：入库明细单申报中（CBDS70）

        List<ImpLogistics> impLogisticsList;
        List<ImpDeliveryHead> impDeliveryHeadList;
        ImpDeliveryHead impDeliveryHead;

        while (true) {

            try {
                //查询
                impLogisticsList = deliveryDeclareMapper.findLogisticsData(paramMap);

                if (CollectionUtils.isEmpty(impLogisticsList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成入库明细单报文的运单数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("入库明细单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }

                for (ImpLogistics impLogistics : impLogisticsList) {
                    try {
                        impDeliveryHead = new ImpDeliveryHead();
                        impDeliveryHead.setGuid(IdUtils.getUUId());
                        impDeliveryHead.setApp_type("1");
                        impDeliveryHead.setApp_status("2");
                        impDeliveryHead.setCustoms_code("9007");
                        impDeliveryHead.setPre_no("");
                        impDeliveryHead.setRkd_no("");
                        impDeliveryHead.setOperator_code("");
                        impDeliveryHead.setOperator_name("");
                        impDeliveryHead.setIe_flag("I");
                        impDeliveryHead.setTraf_mode("5");
                        impDeliveryHead.setTraf_no("飞机");
                        impDeliveryHead.setVoyage_no(impLogistics.getVoyage_no());
                        impDeliveryHead.setBill_no(impLogistics.getBill_no());
                        impDeliveryHead.setLogistics_no(impLogistics.getLogistics_no());
                        impDeliveryHead.setLogistics_code(impLogistics.getLogistics_code());
                        impDeliveryHead.setLogistics_name(impLogistics.getLogistics_name());
                        impDeliveryHead.setUnload_location("");
                        impDeliveryHead.setNote("");
                        impDeliveryHead.setData_status("CBDS7");
                        impDeliveryHead.setCrt_id("系统自生成");
                        impDeliveryHead.setCrt_tm(new Date());
                        impDeliveryHead.setUpd_id("系统自生成");
                        impDeliveryHead.setUpd_tm(new Date());

                        try {
                            //设置运单数据为已进行入库明细写入
                            this.deliveryDeclareMapper.updateLogistics(impLogistics.getGuid());
                            // 插入入库明细单数据
                            this.deliveryDeclareMapper.insertImpDelivery(impDeliveryHead);
                            this.logger.debug(String.format("插入入库明细单数据为: %s", impDeliveryHead.getLogistics_no()));
                        } catch (Exception e) {
                            String exceptionMsg = String.format("插入入库明细单[logistics_no: %s]数据时发生异常", impDeliveryHead.getLogistics_no());
                            this.logger.error(exceptionMsg, e);
                        }

                    } catch (Exception e) {
                        String exceptionMsg = String.format("处理入库明细单时发生异常");
                        this.logger.error(exceptionMsg, e);
                    }

                }

            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                    logger.error("生成入库明细单时发生异常，等待5秒重新开始获取数据", e);
                } catch (InterruptedException ie) {
                    logger.error("入库明细单生成器暂停时发生异常", ie);
                }
            }
        }
    }

}
