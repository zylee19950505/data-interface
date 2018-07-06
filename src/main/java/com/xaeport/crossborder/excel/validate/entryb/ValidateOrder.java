package com.xaeport.crossborder.excel.validate.entryb;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.excel.headings.ExcelHeadOrder;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018/06/28.
 */
public class ValidateOrder extends ValidateBase {
    //要校验字段索引
    private LoadData loadData = SpringUtils.getBean(LoadData.class);
    private int orderNoIndex; //订单编号";//head //list
    private int logisticsNoIndex; //物流运单编号";//
    private int itemNameIndex; //商品名称";//list
    private int gcodeIndex; //商品编码";//
    private int gmodelIndex; //商品规格型号";//
    private int qtyIndex; //数量";//list
    private int unitIndex; //计量单位";//list
    private int qty1Index; //第一法定数量";//
    private int unit1Index; //第一法定单位";//
    private int qty2Index; //第二法定数量";//
    private int unit2Index; //第二法定单位";//
    private int total_PriceIndex; //总价";//list
    private int ebp_CodeIndex; //电商平台代码";//
    private int ebp_NameIndex; //电商平台名称";//
    private int assureCodeIndex; //担保企业编号";//
    private int declTimeIndex; //申报日期";//
    private int customsCodeIndex; //申报海关代码";//
    private int portCodeIndex; //口岸海关代码";//enterprise
    private int ieDateIndex; //进口日期";//
    private int buyer_Reg_NoIndex; //订购人注册号";//head
    private int buyer_Id_TypeIndex; //订购人证件类型";//head
    private int buyer_Id_NumberIndex; //订购人证件号码";//head
    private int buyer_NameIndex; //订购人姓名";//head
    private int buyerTelephoneIndex; //订购人电话";//
    private int goodsValueIndex; //商品价格";//head
    private int discountIndex; //非现金抵扣金额";//head
    private int tax_TotalIndex; //代扣税款";//head
    private int agentCodeIndex; //申报企业代码";//enterprise
    private int agentNameIndex; //申报企业名称";//enterprise
    private int trafModeIndex; //运输方式";
    private int originCountryIndex; //原产国";//
    private int startCountryIndex; //起运国";//
    private int noteIndex; //备注";//head

    private Map<Integer, String> indexMap = new HashMap<>();

    public void getIndexValue(List<String> list) {
        orderNoIndex = list.indexOf(ExcelHeadOrder.orderNo);//订单编号  //head //list
        ebp_CodeIndex = list.indexOf(ExcelHeadOrder.ebp_Code);//电商平台代码
        ebp_NameIndex = list.indexOf(ExcelHeadOrder.ebp_Name);//电商平台名称
        buyer_Reg_NoIndex = list.indexOf(ExcelHeadOrder.buyer_Reg_No);//订购人注册号//head
        buyer_Id_TypeIndex = list.indexOf(ExcelHeadOrder.buyer_Id_Type);//订购人证件类型//head
        buyer_Id_NumberIndex = list.indexOf(ExcelHeadOrder.buyer_Id_Number);//订购人证件号码//head
        buyer_NameIndex = list.indexOf(ExcelHeadOrder.buyer_Name);//订购人姓名//head
        goodsValueIndex = list.indexOf(ExcelHeadOrder.goodsValue);//商品价格//head
        discountIndex = list.indexOf(ExcelHeadOrder.discount);//非现金抵扣金额//head
        tax_TotalIndex = list.indexOf(ExcelHeadOrder.tax_Total);//代扣税款//head

        itemNameIndex = list.indexOf(ExcelHeadOrder.itemName);//商品名称//list
        originCountryIndex = list.indexOf(ExcelHeadOrder.originCountry);//原产国//list
        qtyIndex = list.indexOf(ExcelHeadOrder.qty);//数量//list
        unitIndex = list.indexOf(ExcelHeadOrder.unit);//计量单位//list
        total_PriceIndex = list.indexOf(ExcelHeadOrder.buyer_Id_Type);//总价//list

        this.initMap();
    }

    public void initMap() {
        indexMap.put(orderNoIndex, "订单编号,60");
        indexMap.put(ebp_CodeIndex, "电商平台代码,18");
        indexMap.put(ebp_NameIndex, "电商平台名称,100");
        indexMap.put(buyer_Reg_NoIndex, "订购人注册号,60");
        indexMap.put(buyer_Id_TypeIndex, "订购人证件类型,1");
        indexMap.put(buyer_Id_NumberIndex, "订购人证件号码,60");
        indexMap.put(buyer_NameIndex, "订购人姓名,60");
        indexMap.put(goodsValueIndex, "商品价格,19");
        indexMap.put(discountIndex, "非现金抵扣金额,19");
        indexMap.put(tax_TotalIndex, "代扣税款,19");

        indexMap.put(itemNameIndex, "商品名称,250");
        indexMap.put(originCountryIndex, "原产国,3");
        indexMap.put(qtyIndex, "数量,19");
        indexMap.put(unitIndex, "计量单位,19");
        indexMap.put(total_PriceIndex, "总价,19");


    }


    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        if (cell_num != noteIndex) {
            boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
            if (!isEmpty) {
                return -1;
            }
        }

        // 重量
        if (cell_num == goodsValueIndex || cell_num == discountIndex || cell_num == tax_TotalIndex ||
                cell_num == qtyIndex || cell_num == unitIndex || cell_num == total_PriceIndex) {
            String message = indexMap.get(cell_num).split(",")[0];
            int flag = ValidateUtil.checkDoubleValue(cell);
            boolean checkNumberType = this.CheckNumberType(flag, error_num, rowNum, cell_num, message);
            if (!checkNumberType) {
                return -1;
            }
        }

        return 0;
    }


}
