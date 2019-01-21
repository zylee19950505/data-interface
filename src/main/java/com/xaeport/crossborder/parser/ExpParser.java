package com.xaeport.crossborder.parser;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
            type = "CEB312";
        } else if (sample.contains("<CEB412Message")) {//支付单回执
            type = "CEB412";
        } else if (sample.contains("<CEB512Message")) {//运单回执
            type = "CEB512";
        } else if (sample.contains("<CEB514Message")) {//运单状态回执
            type = "CEB514";
        } else if (sample.contains("<CEB622Message")) {//清单回执
            type = "CEB622";
        } else if (sample.contains("<CEB712Message")) {//入库明细单回执
            type = "CEB712";
        } else if (sample.contains("<CheckGoodsInfo")) {//预订数据报文
            type = "CheckGoodsInfo";
        } else if (sample.contains("<CEB816Message")) {//电子税单回执
            type = "TAX";
        } else if (sample.contains("<CommonResponeMessage")) {//核注清单处理成功回执
            type = "COMMON";
        } else if (sample.contains("<INV201")) {//核注清单(报文回执/审核回执)
            type = "INV201";
        } else if (sample.contains("<INV202")) {//核注清单生成报关单回执
            type = "INV202";
        }
        return type;
    }

    //生成报文类型，并解析报文节点
    public Map expParser(byte[] expPath) throws IOException, DocumentException {
        Map<String, Object> mapData = new HashMap<>();
        String type = this.getExpType(expPath);
        mapData.put("type", type);
        Map<String, List<List<Map<String, String>>>> map = null;
        Map<String, List<Map<String, String>>> mapNew = null;
        switch (type) {
            case "CEB312"://订单回执报文
                map = this.parserHolder.getParser("ceb312").expParser(expPath, "OrderReturn");
                break;
            case "CEB412"://支付单回执报文
                map = this.parserHolder.getParser("ceb412").expParser(expPath, "PaymentReturn");
                break;
            case "CEB512"://运单回执报文
                map = this.parserHolder.getParser("ceb512").expParser(expPath, "LogisticsReturn");
                break;
            case "CEB514"://运单状态回执报文
                map = this.parserHolder.getParser("ceb514").expParser(expPath, "LogisticsStatusReturn");
                break;
            case "CEB622"://清单回执报文
                map = this.parserHolder.getParser("ceb622").expParser(expPath, "InventoryReturn");
                break;
            case "CEB712"://入库明细单回执
                map = this.parserHolder.getParser("ceb712").expParser(expPath, "DeliveryReturn");
                break;
            case "CheckGoodsInfo"://预定数据报文
                map = this.parserHolder.getParser("CheckGoodsInfo").expParser(expPath, "CheckGoodsInfoHead");
                break;
            case "TAX"://电子税单回执报文
                map = this.parserHolder.getParser("TAX").expParser(expPath, "Tax", "TaxHeadRd", "TaxListRd");
                break;
            case "COMMON"://核注清单处理成功回执
                mapNew = this.parserHolder.getParserNew("invCommon").expParserNew(expPath, "SeqNo", "EtpsPreentNo", "CheckInfo", "DealFlag");
                break;
            case "INV201"://核注清单(报文回执/审核回执)
                map = this.parserHolder.getParser("inv201msg").expParser(expPath, "EnvelopInfo", "HdeApprResult");
                break;
            case "INV202"://核注清单生成报关单回执
                map = this.parserHolder.getParser("inv202customs").expParser(expPath, "EnvelopInfo", "InvApprResult");
                break;
        }
        mapData.put("Receipt", map);
        if (!StringUtils.isEmpty(mapNew)) {
            mapData.put("Receipt", mapNew);
        }
        return mapData;
    }

}
