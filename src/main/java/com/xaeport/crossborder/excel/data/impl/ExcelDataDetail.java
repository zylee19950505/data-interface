package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.ImpInventoryBody;
import com.xaeport.crossborder.data.entity.ImpInventoryHead;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadDetail;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

public class ExcelDataDetail implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
    private int orderNoIndex; //"订单编号";//head //list
//    private int copNoIndex; //"企业内部编号";//head
    private int logisticsNoIndex; //"物流运单编号";//head
    private int logisticsCodeIndex; //"物流企业代码";//head
    private int logisticsNameIndex; //"物流企业名称";//head
    private int gnameIndex; //"商品名称";//list
    private int gcodeIndex; //"商品编码";//list
    private int gmodelIndex; //"商品规格型号";//list
    private int qtyIndex; //"数量";//list
    private int unitIndex; //"计量单位";//list
    private int qty1Index; //"第一法定数量";//list
    private int unit1Index; //"第一法定计量单位";//list
    private int qty2Index; //"第二法定数量";//list
    private int unit2Index; //"第二法定计量单位";//list
    private int total_PriceIndex; //"总价";//list
    private int ebp_CodeIndex; //"电商平台代码";//head
    private int ebp_NameIndex; //"电商平台名称";//head
    private int ebc_CodeIndex; //"电商企业代码";//head
    private int ebc_NameIndex; //"电商企业名称";//head
    private int assureCodeIndex; //"担保企业编号";//head
    private int customsCodeIndex; //"申报海关代码";//head
    private int portCodeIndex; //"口岸海关代码";//head
    private int ieDateIndex; //"进口日期";//head
    private int buyer_Id_NumberIndex; //"订购人证件号码";//head
    private int buyer_NameIndex; //"订购人姓名";//head
    private int buyerTelephoneIndex; //"订购人电话";//head
    private int consignee_AddressIndex; //"收件地址";//head
    private int freightIndex; //"运费";//head
    private int insuredFeeIndex; //"保费";//head
    private int agentCodeIndex; //"申报企业代码";//head
    private int agentNameIndex; //"申报企业名称";//head
    private int trafModeIndex; //"运输方式";//head
    private int trafNoIndex; //"运输工具编号";//head
    private int flightVoyageIndex; //"航班航次号";//head
    private int billNoIndex; //"提运单号";//head
    private int originCountryIndex; //"原产国";//list
    private int startCountryIndex; //"起运国";//head
    private int grossWeightIndex; //"毛重";//head
    private int netWeightIndex; //"净重";//head
    private int noteIndex; //"备注";//list


    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<ImpInventoryBody> impInventoryBodyList = new ArrayList<>();
        Map<String,List<String>> impInventoryHeadMap = new LinkedHashMap<>();
        ImpInventoryBody impInventoryBody;
        this.getIndexValue(excelData.get(0));//初始化表头索引
        for (int i = 1, length = excelData.size(); i < length; i++) {
            impInventoryBody = new ImpInventoryBody();
            impInventoryHeadMap = this.getMergeData(excelData.get(i), impInventoryHeadMap);
            impInventoryBodyList = this.impInventoryBodyData(excelData.get(i), impInventoryBody, impInventoryBodyList);
        }

        List<ImpInventoryHead> impInventoryHeadList = this.getInventoryHeadData(impInventoryHeadMap);
        map.put("ImpInventoryHead", impInventoryHeadList);
        map.put("ImpInventoryBody", impInventoryBodyList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpOrderHead方法
     */
    public List<ImpInventoryHead> getInventoryHeadData(Map<String, List<String>> data) {
        List<ImpInventoryHead> listData = new ArrayList<>();
        ImpInventoryHead impInventoryHead;
        DecimalFormat df = new DecimalFormat("0.00000");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            impInventoryHead = new ImpInventoryHead();
            List<String> value = entry.getValue();

            impInventoryHead.setOrder_no(value.get(orderNoIndex));//交易平台的订单编号，同一交易平台的订单编号应唯一。订单编号长度不能超过60位。
            impInventoryHead.setEbp_code(value.get(ebp_CodeIndex));//电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。
            impInventoryHead.setEbp_name(value.get(ebp_NameIndex));//电商平台的海关注册登记名称；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台名称为准。
            impInventoryHead.setEbc_code(value.get(ebc_CodeIndex));//电商企业的海关注册登记编号。
            impInventoryHead.setEbc_name(value.get(ebc_NameIndex));//电商企业的海关注册登记名称。
            impInventoryHead.setLogistics_no(value.get(logisticsNoIndex));//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
            impInventoryHead.setLogistics_code(value.get(logisticsCodeIndex));//物流企业的海关注册登记编号。
            impInventoryHead.setLogistics_name(value.get(logisticsNameIndex));//物流企业在海关注册登记的名称。
//            impInventoryHead.setCop_no(value.get(copNoIndex));//企业内部标识单证的编号。
//            impInventoryHead.setPre_no("");//电子口岸标识单证的编号。
            impInventoryHead.setAssure_code(value.get(assureCodeIndex));//担保扣税的企业海关注册登记编号，只限清单的电商平台企业、电商企业、物流企业。
//            impInventoryHead.setEms_no("");//保税模式必填，填写区内仓储企业在海关备案的账册编号，用于保税进口业务在特殊区域辅助系统记账（二线出区核减）。
//            impInventoryHead.setInvt_no("");//海关接受申报生成的清单编号。
            impInventoryHead.setCustoms_code(value.get(customsCodeIndex));//接受清单申报的海关关区代码，参照JGS/T 18《海关关区代码》。
            impInventoryHead.setPort_code(value.get(portCodeIndex));//商品实际进出我国关境口岸海关的关区代码，参照JGS/T 18《海关关区代码》。
//            impInventoryHead.setIe_date("");//运载所申报商品的运输工具申报进境的日期，进口申报时无法确知相应的运输工具的实际进境日期时，免填。格式:YYYYMMDD
            impInventoryHead.setBuyer_id_number(value.get(buyer_Id_NumberIndex));//订购人的身份证件号码。
            impInventoryHead.setBuyer_name(value.get(buyer_NameIndex));//订购人的真实姓名。
            impInventoryHead.setBuyer_telephone(value.get(buyerTelephoneIndex));//订购人电话。
            impInventoryHead.setConsignee_address(value.get(consignee_AddressIndex));//收货地址
            impInventoryHead.setAgent_code(value.get(agentCodeIndex));//申报单位的海关注册登记编号。
            impInventoryHead.setAgent_name(value.get(agentNameIndex));//申报单位在海关注册登记的名称。
//            impInventoryHead.setArea_code("");//保税模式必填，区内仓储企业的海关注册登记编号。
//            impInventoryHead.setArea_name("");//保税模式必填，区内仓储企业在海关注册登记的名称。
            impInventoryHead.setTraf_mode(value.get(trafModeIndex));//填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 运输方式代码。直购进口指跨境段物流运输方式，保税进口指二线出区物流运输方式。
            impInventoryHead.setTraf_no(value.get(trafNoIndex));//直购进口必填。货物进出境的运输工具的名称或运输工具编号。填报内容应与运输部门向海关申报的载货清单所列相应内容一致；同报关单填制规范。保税进口免填。
            impInventoryHead.setVoyage_no(value.get(flightVoyageIndex));//直购进口必填。货物进出境的运输工具的航次编号。保税进口免填。
            impInventoryHead.setBill_no(value.get(billNoIndex));//直购进口必填。货物提单或运单的编号，保税进口免填。
//            impInventoryHead.setLoct_no("");//针对同一申报地海关下有多个跨境电子商务的监管场所,需要填写区分
//            impInventoryHead.setLicense_no("");//商务主管部门及其授权发证机关签发的进出口货物许可证件的编号
            impInventoryHead.setCountry(value.get(startCountryIndex));//直购进口填写起始发出国家（地区）代码，参照《JGS-20 海关业务代码集》的国家（地区）代码表；保税进口填写代码“142”。
//            impInventoryHead.setWrap_type("");//海关对进出口货物实际采用的外部包装方式的标识代码，采用1 位数字表示，如：木箱、纸箱、桶装、散装、托盘、包、油罐车等
//            impInventoryHead.setNote(value.get(noteIndex));//备注

            impInventoryHead.setInsured_fee(getDouble(value.get(insuredFeeIndex)));//物流企业实际收取的商品保价费用。
            impInventoryHead.setFreight(getDouble(value.get(freightIndex)));//运杂费
            impInventoryHead.setGross_weight(getDouble(value.get(grossWeightIndex)));//货物及其包装材料的重量之和，计量单位为千克。
            impInventoryHead.setNet_weight(getDouble(value.get(netWeightIndex)));//货物的毛重减去外包装材料后的重量，即货物本身的实际重量，计量单位为千克。

            listData.add(impInventoryHead);
        }
        return listData;
    }

    /**
     * 封装ImpOrderBody数据
     *
     * @param inventoryBodies
     * @param impInventoryBody
     * @param impInventoryBodyData
     * @return
     */
    public List<ImpInventoryBody> impInventoryBodyData(List<String> inventoryBodies, ImpInventoryBody impInventoryBody, List<ImpInventoryBody> impInventoryBodyData) {
        DecimalFormat df = new DecimalFormat("0.00000");
        impInventoryBody.setOrder_no(inventoryBodies.get(orderNoIndex));//清单编号
//        impInventoryBody.setItem_record_no("");//账册备案料号: 保税进口必填
//        impInventoryBody.setItem_no("");//企业商品货号: 电商企业自定义的商品货号（SKU）。
//        impInventoryBody.setItem_name("");//企业商品品名: 交易平台销售商品的中文名称。
        impInventoryBody.setG_code(inventoryBodies.get(gcodeIndex));//商品编码: 按商品分类编码规则确定的进出口商品的商品编号，分为商品编号和附加编号，其中商品编号栏应填报《中华人民共和国进出口税则》8位税则号列，附加编号应填报商品编号，附加编号第9、10位。
        impInventoryBody.setG_name(inventoryBodies.get(gnameIndex));//商品名称: 商品名称应据实填报，与电子订单一致。
        impInventoryBody.setG_model(inventoryBodies.get(gmodelIndex));//商品规格型号: 满足海关归类、审价以及监管的要求为准。包括：品牌、规格、型号等。
//        impInventoryBody.setBar_code("");//条形码: 商品条形码一般由前缀部分、制造厂商代码、商品代码和校验码组成。
        impInventoryBody.setCountry(inventoryBodies.get(originCountryIndex));//原产国（地区）: 填写海关标准的参数代码，参照《JGS-20 海关业务代码集》- 国家（地区）代码表。
        impInventoryBody.setUnit(inventoryBodies.get(unitIndex));//计量单位
        impInventoryBody.setUnit1(inventoryBodies.get(unit1Index));//第一计量单位
        impInventoryBody.setUnit2(inventoryBodies.get(unit2Index));//第二计量单位
        impInventoryBody.setNote(inventoryBodies.get(noteIndex));//促销活动，商品单价偏离市场价格的，可以在此说明。

        impInventoryBody.setQty(getDouble(inventoryBodies.get(qtyIndex)));//商品实际数量
        impInventoryBody.setQty1(getDouble(inventoryBodies.get(qty1Index)));//第一法定数量
        impInventoryBody.setQty2(getDouble(inventoryBodies.get(qty2Index)));//第二法定数量
        impInventoryBody.setTotal_price(getDouble(inventoryBodies.get(total_PriceIndex)));//总价

        double Price = Double.parseDouble(impInventoryBody.getTotal_price()) / Double.parseDouble(impInventoryBody.getQty());
        if (!StringUtils.isEmpty(Price)) {
            String price = df.format(Price);
            impInventoryBody.setPrice(price);//单价
        }
        impInventoryBodyData.add(impInventoryBody);
        return impInventoryBodyData;
    }

    /**
     * 初始化索引值
     *
     * @param detailLists
     */
    public void getIndexValue(List<String> detailLists) {
        orderNoIndex = detailLists.indexOf(ExcelHeadDetail.orderNo);
        logisticsNoIndex = detailLists.indexOf(ExcelHeadDetail.logisticsNo);
        logisticsCodeIndex = detailLists.indexOf(ExcelHeadDetail.logisticsCode);
        logisticsNameIndex = detailLists.indexOf(ExcelHeadDetail.logisticsName);
        gnameIndex = detailLists.indexOf(ExcelHeadDetail.gname);
        gcodeIndex = detailLists.indexOf(ExcelHeadDetail.gcode);
        gmodelIndex = detailLists.indexOf(ExcelHeadDetail.gmodel);
        qtyIndex = detailLists.indexOf(ExcelHeadDetail.qty);
        unitIndex = detailLists.indexOf(ExcelHeadDetail.unit);
        qty1Index = detailLists.indexOf(ExcelHeadDetail.qty1);
        unit1Index = detailLists.indexOf(ExcelHeadDetail.unit1);
        qty2Index = detailLists.indexOf(ExcelHeadDetail.qty2);
        unit2Index = detailLists.indexOf(ExcelHeadDetail.unit2);
        total_PriceIndex = detailLists.indexOf(ExcelHeadDetail.total_Price);
        ebp_CodeIndex = detailLists.indexOf(ExcelHeadDetail.ebp_Code);
        ebp_NameIndex = detailLists.indexOf(ExcelHeadDetail.ebp_Name);
        ebc_CodeIndex = detailLists.indexOf(ExcelHeadDetail.ebc_Code);
        ebc_NameIndex = detailLists.indexOf(ExcelHeadDetail.ebc_Name);
        assureCodeIndex = detailLists.indexOf(ExcelHeadDetail.assureCode);
        customsCodeIndex = detailLists.indexOf(ExcelHeadDetail.customsCode);
        portCodeIndex = detailLists.indexOf(ExcelHeadDetail.portCode);
        ieDateIndex = detailLists.indexOf(ExcelHeadDetail.ieDate);
        buyer_Id_NumberIndex = detailLists.indexOf(ExcelHeadDetail.buyer_Id_Number);
        buyer_NameIndex = detailLists.indexOf(ExcelHeadDetail.buyer_Name);
        buyerTelephoneIndex = detailLists.indexOf(ExcelHeadDetail.buyerTelephone);
        consignee_AddressIndex = detailLists.indexOf(ExcelHeadDetail.consignee_Address);
        freightIndex = detailLists.indexOf(ExcelHeadDetail.freight);
        insuredFeeIndex = detailLists.indexOf(ExcelHeadDetail.insuredFee);
        agentCodeIndex = detailLists.indexOf(ExcelHeadDetail.agentCode);
        agentNameIndex = detailLists.indexOf(ExcelHeadDetail.agentName);
        trafModeIndex = detailLists.indexOf(ExcelHeadDetail.trafMode);
        trafNoIndex = detailLists.indexOf(ExcelHeadDetail.trafNo);
        flightVoyageIndex = detailLists.indexOf(ExcelHeadDetail.flightVoyage);
        billNoIndex = detailLists.indexOf(ExcelHeadDetail.billNo);
        originCountryIndex = detailLists.indexOf(ExcelHeadDetail.originCountry);
        startCountryIndex = detailLists.indexOf(ExcelHeadDetail.startCountry);
        grossWeightIndex = detailLists.indexOf(ExcelHeadDetail.grossWeight);
        netWeightIndex = detailLists.indexOf(ExcelHeadDetail.netWeight);
        noteIndex = detailLists.indexOf(ExcelHeadDetail.note);
    }

    /**
     * 合并excel中属于EntryHead的数据
     *
     * @param impInventoryHeadLists
     * @param impInventoryHeadMap
     * @return
     */
    public Map<String, List<String>> getMergeData(List<String> impInventoryHeadLists, Map<String, List<String>> impInventoryHeadMap) throws Exception {
        String orderNo = impInventoryHeadLists.get(orderNoIndex);
        impInventoryHeadMap.put(orderNo, impInventoryHeadLists);
        return impInventoryHeadMap;
    }

    protected String getString(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str;
        } else {
            return "";
        }

    }

    protected String getDouble(String str) {
        DecimalFormat df = new DecimalFormat("0.00000");
        if (!StringUtils.isEmpty(str)) {
            return df.format(Double.parseDouble(str));
        } else {
            return "0";
        }
    }


}
