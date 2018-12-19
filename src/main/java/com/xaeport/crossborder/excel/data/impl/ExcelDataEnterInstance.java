package com.xaeport.crossborder.excel.data.impl;

import com.xaeport.crossborder.data.entity.BondInvtDt;
import com.xaeport.crossborder.excel.data.ExcelData;
import com.xaeport.crossborder.excel.headings.ExcelHeadEnterInventory;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核注清单模板数据
 * Created by lzy on 2018/06/28.
 */
public class ExcelDataEnterInstance implements ExcelData {
    private Log log = LogFactory.getLog(this.getClass());
    private int gds_MtnoIndex; //账册备案料号(商品料号)";//list
    private int gdecdIndex; //商品编码";//list
    private int gds_nmIndex; //商品名称";//list
    private int gds_spcf_model_descIndex; //商品规格型号";//list
    private int dcl_unitcdIndex; //计量单位";//list
    private int lawf_unitcdIndex; //法定计量单位";//list
    private int lawf_qtyIndex; //法定数量";//list
    private int secd_lawf_qtyIndex; //第二法定数量";//list
    private int secd_lawf_unitcdIndex; //第二法定计量单位";//list
    private int dcl_total_amtIndex; //总价";//list
    private int dcl_qtyIndex; //数量";//list
    private int natcdIndex; //原产国(地区)";//list
    private int rmkIndex; //备注";//list


    public Map<String, Object> getExcelData(List<List<String>> excelData) throws Exception {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<BondInvtDt> bondInvtDtList = new ArrayList<>();
        BondInvtDt bondInvtDt;
        Map<String, String> temporary = new HashMap<>();//用于存放临时计算值
        this.getIndexValue(excelData.get(0));//初始化表头索引
        for (int i = 1, length = excelData.size(); i < length; i++) {
            bondInvtDt = new BondInvtDt();
            bondInvtDtList = this.bondInvtDtData(excelData.get(i), bondInvtDt, bondInvtDtList);
        }
        map.put("bondInvtDtList", bondInvtDtList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpEnterInstance方法
     */
   /* public List<BondInvtDt> getOrderHeadData(Map<String, List<String>> data) {
        List<ImpOrderHead> listData = new ArrayList<>();
        ImpOrderHead impOrderHead;
        DecimalFormat df = new DecimalFormat("0.00000");
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            impOrderHead = new ImpOrderHead();
            List<String> value = entry.getValue();

            impOrderHead.setOrder_No(value.get(putrec_seqnoIndex));//订单编号
            impOrderHead.setEbp_Code(value.get(ebp_CodeIndex));//电商平台代码
            impOrderHead.setEbp_Name(value.get(ebp_NameIndex));//电商平台名称
            impOrderHead.setEbc_Code(value.get(ebc_CodeIndex));//电商企业代码
            impOrderHead.setEbc_Name(value.get(ebc_NameIndex));//电商企业名称
            impOrderHead.setBuyer_Reg_No(value.get(buyer_Reg_NoIndex));//订购人注册号
            impOrderHead.setBuyer_Id_Number(value.get(buyer_Id_NumberIndex));//订购人证件号码
            impOrderHead.setBuyer_Name(value.get(buyer_NameIndex));//订购人姓名
            impOrderHead.setBuyer_TelePhone(value.get(buyer_TelePhoneIndex));//订购人电话
            impOrderHead.setConsignee(value.get(consigneeIndex));//收货人姓名
            impOrderHead.setConsignee_Telephone(value.get(consignee_TelephoneIndex));//收货人电话
            impOrderHead.setConsignee_Address(value.get(consignee_AddressIndex));//收件地址
//            impOrderHead.setNote(excelData.get(i).get(noteIndex));//备注
            impOrderHead.setGoods_Value(getDouble(value.get(total_PriceIndex)));//商品价格
            impOrderHead.setFreight(getDouble(value.get(freightIndex)));//运杂费
            impOrderHead.setDiscount(getDouble(value.get(discountIndex)));//非现金抵扣金额
            impOrderHead.setTax_Total(getDouble(value.get(tax_TotalIndex)));//代扣税款

            //double actural_Paid = Double.parseDouble(impOrderHead.getGoods_Value()) + Double.parseDouble(impOrderHead.getFreight()) + Double.parseDouble(impOrderHead.getTax_Total()) - Double.parseDouble(impOrderHead.getDiscount());
            // 实际支付金额 = 商品价格 + 运杂费 + 代扣税款 - 非现金抵扣金额，与支付凭证的支付金额一致。
            if(!StringUtils.isEmpty(actural_Paid)){
                String acturalPaid = df.format(actural_Paid);
                impOrderHead.setActural_Paid(acturalPaid);
            }

            listData.add(impOrderHead);
        }
        return listData;
    }
*/
    /**
     * 合并excel中属于EntryHead的数据
     *
     * @param orderHeadLists
     * @param impOrderHeadMap
     * @return
     */
   /* public Map<String, List<String>> getMergeData(List<String> orderHeadLists, Map<String, List<String>> impOrderHeadMap) throws Exception {
        String orderNo = orderHeadLists.get(orderNoIndex);
        DecimalFormat df = new DecimalFormat("0.00000");
        if (impOrderHeadMap.containsKey(orderNo)) {
            List<String> list = impOrderHeadMap.get(orderNo);//存放每次合并的结果
            String allTotalPrice = list.get(total_PriceIndex);
            String addTotalPrice = orderHeadLists.get(total_PriceIndex);
            if (StringUtils.isEmpty(allTotalPrice)){
                allTotalPrice = "0";
            }
            if (StringUtils.isEmpty(addTotalPrice)){
                addTotalPrice = "0";
            }
            double total_value_Total = Double.parseDouble(allTotalPrice) + Double.parseDouble(addTotalPrice);//合并所有表体的总价
            list.set(total_PriceIndex, df.format(total_value_Total));//商品价格
            impOrderHeadMap.put(orderNo, list);
        } else {
            String totalPrice = orderHeadLists.get(total_PriceIndex);
            if(StringUtils.isEmpty(totalPrice)){
                totalPrice = "0";
                orderHeadLists.set(total_PriceIndex,totalPrice);
            }
            impOrderHeadMap.put(orderNo, orderHeadLists);
        }
        return impOrderHeadMap;
    }*/

    /**
     * 封装ImpOrderBody数据
     *
     * @param entryLists
     * @param bondInvtDtList
     * @return
     */
    public List<BondInvtDt> bondInvtDtData(List<String> entryLists, BondInvtDt bondInvtDt, List<BondInvtDt> bondInvtDtList) {
        DecimalFormat df = new DecimalFormat("0.00000");
        bondInvtDt.setGds_mtno(entryLists.get(gds_MtnoIndex));//账册备案料号(商品料号)
        bondInvtDt.setGdecd(entryLists.get(gdecdIndex));//商品编码
        bondInvtDt.setGds_nm(entryLists.get(gds_nmIndex));//商品名称
        bondInvtDt.setGds_spcf_model_desc(entryLists.get(gds_spcf_model_descIndex));//商品规格型号
        bondInvtDt.setDcl_unitcd(entryLists.get(dcl_unitcdIndex));//计量单位
        bondInvtDt.setLawf_unitcd(entryLists.get(lawf_unitcdIndex));//法定计量单位
        bondInvtDt.setLawf_qty((entryLists.get(lawf_qtyIndex)));//法定数量
        bondInvtDt.setSecd_lawf_qty((entryLists.get(secd_lawf_qtyIndex)));//第二法定数量
        bondInvtDt.setSecd_lawf_unitcd((entryLists.get(secd_lawf_unitcdIndex)));//第二法定计量单位
        bondInvtDt.setDcl_total_amt((entryLists.get(dcl_total_amtIndex)));//总价
        bondInvtDt.setDcl_qty((entryLists.get(dcl_qtyIndex)));//数量
        bondInvtDt.setNatcd(entryLists.get(natcdIndex));//原产国(地区)
        bondInvtDt.setRmk(entryLists.get(rmkIndex));//备注


        bondInvtDtList.add(bondInvtDt);
        return bondInvtDtList;
    }

    /**
     * 初始化索引值
     *
     * @param bondInvtLists
     */
    public void getIndexValue(List<String> bondInvtLists) {
        gds_MtnoIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.gds_mtno);//账册备案料号
        gdecdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.gdecd);//商品编码
        gds_nmIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.gds_nm);//商品名称
        gds_spcf_model_descIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.gds_spcf_model_desc);//商品规格型号
        dcl_unitcdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.dcl_unitcd);//计量单位
        lawf_unitcdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.lawf_unitcd);//法定计量单位
        lawf_qtyIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.lawf_qty);//法定数量
        secd_lawf_qtyIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.secd_lawf_qty);//第二法定数量
        secd_lawf_unitcdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.secd_lawf_unitcd);//第二法定计量单位
        dcl_total_amtIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.dcl_total_amt);//总价
        dcl_qtyIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.dcl_qty);//数量
        natcdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.natcd);//原产国(地区)
        rmkIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.rmk);//备注

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
