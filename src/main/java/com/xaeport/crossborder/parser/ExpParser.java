package com.xaeport.crossborder.parser;

import com.xaeport.crossborder.data.status.ReceiptType;
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
        if (sample.contains("<CEB312Message")) {//跨境订单回执
            type = ReceiptType.KJDD;
        } else if (sample.contains("<CEB412Message")) {//跨境支付单回执
            type = ReceiptType.KJZFD;
        } else if (sample.contains("<CEB512Message")) {//跨境运单回执
            type = ReceiptType.KJYD;
        } else if (sample.contains("<CEB514Message")) {//跨境运单状态回执
            type = ReceiptType.KJYDZT;
        } else if (sample.contains("<CEB622Message")) {//跨境清单回执
            type = ReceiptType.KJQD;
        } else if (sample.contains("<CEB712Message")) {//跨境入库明细单回执
            type = ReceiptType.KJRKMXD;
        } else if (sample.contains("<CheckGoodsInfo")) {//跨境预订数据报文
            type = ReceiptType.KJYDSJ;
        } else if (sample.contains("<CEB816Message")) {//跨境电子税单回执
            type = ReceiptType.KJSD;
        } else if (sample.contains("<CommonResponeMessage")) {//保税（核注清单/核放单）数据中心回执
            type = ReceiptType.BSSJZX;
        } else if (sample.contains("<INV201")) {//保税核注清单(报文回执/审核回执)
            type = ReceiptType.BSHZQDSH;
        } else if (sample.contains("<INV202")) {//保税核注清单生成报关单回执
            type = ReceiptType.BSHZQDBGD;
        } else if (sample.contains("<SAS221")) {//保税核放单（审核/审核报文）回执
            type = ReceiptType.BSHFDSH;
        } else if (sample.contains("<SAS223")) {//保税核放单过卡回执
            type = ReceiptType.BSHFDGK;
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
            case ReceiptType.KJDD://跨境订单回执报文
                map = this.parserHolder.getParser(ReceiptType.KJDD).expParser(expPath, "OrderReturn");
                break;
            case ReceiptType.KJZFD://跨境支付单回执报文
                map = this.parserHolder.getParser(ReceiptType.KJZFD).expParser(expPath, "PaymentReturn");
                break;
            case ReceiptType.KJYD://跨境运单回执报文
                map = this.parserHolder.getParser(ReceiptType.KJYD).expParser(expPath, "LogisticsReturn");
                break;
            case ReceiptType.KJYDZT://跨境运单状态回执报文
                map = this.parserHolder.getParser(ReceiptType.KJYDZT).expParser(expPath, "LogisticsStatusReturn");
                break;
            case ReceiptType.KJQD://跨境清单回执报文
                map = this.parserHolder.getParser(ReceiptType.KJQD).expParser(expPath, "InventoryReturn");
                break;
            case ReceiptType.KJRKMXD://跨境入库明细单回执
                map = this.parserHolder.getParser(ReceiptType.KJRKMXD).expParser(expPath, "DeliveryReturn");
                break;
            case ReceiptType.KJYDSJ://跨境预定数据报文
                map = this.parserHolder.getParser(ReceiptType.KJYDSJ).expParser(expPath, "CheckGoodsInfoHead");
                break;
            case ReceiptType.KJSD://跨境电子税单回执报文
                map = this.parserHolder.getParser(ReceiptType.KJSD).expParser(expPath, "Tax", "TaxHeadRd", "TaxListRd");
                break;
            case ReceiptType.BSSJZX://保税(核注清单/核放单)数据中心回执
                mapNew = this.parserHolder.getParserNew(ReceiptType.BSSJZX).expParserNew(expPath, "SeqNo", "EtpsPreentNo", "CheckInfo", "DealFlag");
                break;
            case ReceiptType.BSHZQDSH://保税核注清单(报文/审核)回执
                map = this.parserHolder.getParser(ReceiptType.BSHZQDSH).expParser(expPath, "EnvelopInfo", "HdeApprResult");
                break;
            case ReceiptType.BSHZQDBGD://保税核注清单生成报关单回执
                map = this.parserHolder.getParser(ReceiptType.BSHZQDBGD).expParser(expPath, "EnvelopInfo", "InvApprResult");
                break;
            case ReceiptType.BSHFDSH://保税核放单（审核/审核报文）回执
                map = this.parserHolder.getParser(ReceiptType.BSHFDSH).expParser(expPath, "EnvelopInfo", "HdeApprResult");
                break;
            case ReceiptType.BSHFDGK://保税核放单过卡回执
                map = this.parserHolder.getParser(ReceiptType.BSHFDGK).expParser(expPath, "EnvelopInfo", "HdeApprResult");
                break;
        }
        mapData.put("Receipt", map);
        if (!StringUtils.isEmpty(mapNew)) {
            mapData.put("Receipt", mapNew);
        }
        return mapData;
    }

}
