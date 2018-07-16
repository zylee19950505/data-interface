package com.xaeport.crossborder.generated.thread;

import com.xaeport.crossborder.configuration.AppConfiguration;
import com.xaeport.crossborder.convert.generate.BaseBill;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import sun.misc.MessageUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManifestGenMsgThread implements Runnable {

    private Log logger = LogFactory.getLog(this.getClass());
    private AppConfiguration appConfiguration = SpringUtils.getBean(AppConfiguration.class);
    private OrderDeclareMapper orderDeclareMapper = SpringUtils.getBean(OrderDeclareMapper.class);
    private MessageUtils messageUtils;
    private BaseBill baseBill;

    public ManifestGenMsgThread(OrderDeclareMapper orderDeclareMapper, AppConfiguration appConfiguration, MessageUtils messageUtils, BaseBill baseBill) {
        this.orderDeclareMapper = orderDeclareMapper;
        this.appConfiguration = appConfiguration;
        this.messageUtils = messageUtils;
        this.baseBill = baseBill;
    }


    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("dataStatus", StatusCode.DDYSB);//订单已申报

        String senderId = this.appConfiguration.getSenderId();// 发送方ID
        String receiverId = this.appConfiguration.getReceiverId();// 接收方ID

        SignedData signedData;
        String entryHeadId;
        ExpMftHead expMftHead;
        ExpMftList expMftList;
        List<ImpOrderHead> entryHeads;
        List<ExpMftList> expMftLists;
        int packNoTotal = 0;
        BigDecimal grossWtTotal;
        double gross_wt;
        String commitUserId;
        Users commitUser = null;

        List<ImpOrderHead> entryHeadList;

        while (true){
            try {
                entryHeadList = orderDeclareMapper.findWaitGenerated(paramMap);
                if (CollectionUtils.isEmpty(entryHeadList)) {
                    // 如无待生成数据，则等待3s后重新确认
                    try {
                        Thread.sleep(3000);
                        logger.debug("未发现需生成订单报文的数据，中等待3秒");
                    } catch (InterruptedException e) {
                        logger.error("订单报文生成器暂停时发生异常", e);
                    }
                    continue;
                }
                for (ImpOrderHead entryHead: entryHeadList) {
                    //获取head表的id
                    String headGuid = entryHead.getGuid();
                    try {
//                        signedData = this.messageUtils
                        //用id来获取list表体
                        List<ImpOrderBody> orderLists = new ArrayList<ImpOrderBody>();
                        orderLists = orderDeclareMapper.queryOrderListByGuid(headGuid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }




            }catch (Exception e){
                e.printStackTrace();
            }


        }

    }
}
