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
    private int gross_wtIndex; //毛重";//list
    private int net_wtIndex; //净重";//list
    private int dcl_total_amtIndex; //总价";//list
    private int dcl_qtyIndex; //数量";//list
    private int natcdIndex; //原产国(地区)";//list
    //private int usecdIndex; //用途代码//list
    private int ec_customs_codeIndex; //电商海关编码//list
    private int lvyrlf_modecdIndex; //用途代码//list
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
            String ecCustomsCode = excelData.get(1).get(ec_customs_codeIndex);
            if (!ecCustomsCode.equals(excelData.get(i).get(ec_customs_codeIndex))) {
                map.put("error", "电商海关编码不一致");
                return map;
            }
            bondInvtDtList = this.bondInvtDtData(excelData.get(i), bondInvtDt, bondInvtDtList);
        }
        map.put("bondInvtDtList", bondInvtDtList);
        this.log.debug("封装数据耗时" + (System.currentTimeMillis() - start));
        return map;
    }

    /**
     * 封装ImpOrderBody数据
     *
     * @param entryLists
     * @param bondInvtDtList
     * @return
     */
    public List<BondInvtDt> bondInvtDtData(List<String> entryLists, BondInvtDt bondInvtDt, List<BondInvtDt> bondInvtDtList) {
//        DecimalFormat df = new DecimalFormat("0.00000");
        DecimalFormat dfTwo = new DecimalFormat("0.00");
        DecimalFormat dfFour = new DecimalFormat("0.0000");

        bondInvtDt.setGds_mtno(entryLists.get(gds_MtnoIndex));//账册备案料号(商品料号)
        bondInvtDt.setGdecd(entryLists.get(gdecdIndex));//商品编码
        bondInvtDt.setGds_nm(entryLists.get(gds_nmIndex));//商品名称
        bondInvtDt.setGds_spcf_model_desc(entryLists.get(gds_spcf_model_descIndex));//商品规格型号
        bondInvtDt.setDcl_unitcd(entryLists.get(dcl_unitcdIndex));//计量单位
        bondInvtDt.setLawf_unitcd(entryLists.get(lawf_unitcdIndex));//法定计量单位
        bondInvtDt.setLawf_qty((entryLists.get(lawf_qtyIndex)));//法定数量
        bondInvtDt.setSecd_lawf_qty((entryLists.get(secd_lawf_qtyIndex)));//第二法定数量
        bondInvtDt.setSecd_lawf_unitcd((entryLists.get(secd_lawf_unitcdIndex)));//第二法定计量单位
        bondInvtDt.setGross_wt(entryLists.get(gross_wtIndex));//毛重
        bondInvtDt.setNet_wt(entryLists.get(net_wtIndex));//净重

        bondInvtDt.setDcl_qty(entryLists.get(dcl_qtyIndex));//数量

        String dclTotalAmt = dfTwo.format(Double.parseDouble(entryLists.get(dcl_total_amtIndex)));
        String dclUprcAmt = dfFour.format(Double.parseDouble(entryLists.get(dcl_total_amtIndex)) / Double.parseDouble(entryLists.get(dcl_qtyIndex)));
        bondInvtDt.setDcl_uprc_amt(dclUprcAmt);//单价
        bondInvtDt.setDcl_total_amt(dclTotalAmt);//总价

        bondInvtDt.setNatcd(entryLists.get(natcdIndex));//原产国(地区)
        //bondInvtDt.setUsecd(entryLists.get(usecdIndex));//用途代码
        bondInvtDt.setEc_customs_code(entryLists.get(ec_customs_codeIndex));//电商海关编码
        bondInvtDt.setLvyrlf_modecd(entryLists.get(lvyrlf_modecdIndex));//征免方式
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
        gross_wtIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.gross_wt);//毛重
        net_wtIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.net_wt);//净重
        dcl_total_amtIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.dcl_total_amt);//总价
        dcl_qtyIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.dcl_qty);//数量
        natcdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.natcd);//原产国(地区)
        //usecdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.usecd);//用途代码
        ec_customs_codeIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.ec_customs_code);//用途代码
        lvyrlf_modecdIndex = bondInvtLists.indexOf(ExcelHeadEnterInventory.lvyrlf_modecd);//用途代码
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
