package com.xaeport.crossborder.parser;

import com.xaeport.crossborder.configuration.SystemConstants;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * exp解析
 * Created by Administrator on 2017/7/24.
 */
@Component
public class ExpParser {

    private final ParserHolder parserHolder;

    @Autowired
    public ExpParser(ParserHolder parserHolder) {
        this.parserHolder = parserHolder;
    }

    /**
     * 解析报文获取报文类型
     *
     * @param expPath 报文文件
     */
    private String getExpType(byte[] expPath) throws IOException, DocumentException {
        String type = "";
        String sample = new String(expPath, "UTF-8").trim();
        //判断回执报文类型
        if (sample.contains("<CEB312Message")) {//订单回执
            type = SystemConstants.RECEIPT_REPORT_TYPE_ORDER;
        } else if (sample.contains("<CEB412Message")) {//支付单回执
            type = SystemConstants.RECEIPT_REPORT_TYPE_PAYMENT;
        } else if (sample.contains("<CEB512Message")) {//运单回执
            type =SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS;
        } else if (sample.contains("<CEB514Message")) {//运单状态回执
            type = SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS_STATUS;
        } else if (sample.contains("<CEB622Message")) {//清单回执
            type = SystemConstants.RECEIPT_REPORT_TYPE_INVENTORY;
        } else if (sample.contains("<CEB712Message")) {//入库明细单回执
            type = SystemConstants.RECEIPT_REPORT_TYPE_DELIVERY;
        } else if (sample.contains("<CheckGoodsInfo")) {//预订数据报文
            type = SystemConstants.RECEIPT_REPORT_TYPE_SUBSCRIPTION;
        } else if (sample.contains("<CEB816Message")) {//电子税单回执
            type = SystemConstants.RECEIPT_REPORT_TYPE_TAX;
        }
        return type;
    }

    //生成报文类型，并解析报文节点
    public Map expParser(byte[] expPath) throws IOException, DocumentException {
        Map<String, Object> mapData = new HashMap<>();
        String type = this.getExpType(expPath);
        mapData.put("type", type);
        Map<String, List<List<Map<String, String>>>> map = null;
        switch (type) {
            case SystemConstants.RECEIPT_REPORT_TYPE_ORDER://订单回执报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_ORDER).expParser(expPath, "OrderReturn");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_PAYMENT://支付单回执报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_PAYMENT).expParser(expPath, "PaymentReturn");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS://运单回执报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS).expParser(expPath, "LogisticsReturn");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS_STATUS://运单状态回执报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_LOGISTICS_STATUS).expParser(expPath, "LogisticsStatusReturn");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_INVENTORY://清单回执报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_INVENTORY).expParser(expPath, "InventoryReturn");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_DELIVERY://入库明细单回执
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_DELIVERY).expParser(expPath, "DeliveryReturn");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_SUBSCRIPTION://预定数据报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_SUBSCRIPTION).expParser(expPath, "CheckGoodsInfoHead");
                break;
            case SystemConstants.RECEIPT_REPORT_TYPE_TAX://电子税单回执报文
                map = this.parserHolder.getParser(SystemConstants.RECEIPT_REPORT_TYPE_TAX).expParser(expPath, "Tax", "TaxHeadRd", "TaxListRd");
                break;
        }
        mapData.put("Receipt", map);
        return mapData;
    }

}
