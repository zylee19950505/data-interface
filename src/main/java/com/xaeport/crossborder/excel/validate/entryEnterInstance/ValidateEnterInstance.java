package com.xaeport.crossborder.excel.validate.entryEnterInstance;

import com.xaeport.crossborder.data.LoadData;
import com.xaeport.crossborder.excel.headings.ExcelHeadEnterInventory;
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
public class ValidateEnterInstance extends ValidateBase {
    //要校验字段索引
    private LoadData loadData = SpringUtils.getBean(LoadData.class);
    private int gds_MtnoIndex; //账册备案料号";//list
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

    private Map<Integer, String> indexMap = new HashMap<>();
    private Map<String, Integer> orderNoMap = new HashMap<>();

    public void getIndexValue(List<String> list) {
        gds_MtnoIndex = list.indexOf(ExcelHeadEnterInventory.gds_mtno);//账册备案料号
        gdecdIndex = list.indexOf(ExcelHeadEnterInventory.gdecd);//商品编码
        gds_nmIndex = list.indexOf(ExcelHeadEnterInventory.gds_nm);//商品名称
        gds_spcf_model_descIndex = list.indexOf(ExcelHeadEnterInventory.gds_spcf_model_desc);//商品规格型号
        dcl_unitcdIndex = list.indexOf(ExcelHeadEnterInventory.dcl_unitcd);//计量单位
        lawf_unitcdIndex = list.indexOf(ExcelHeadEnterInventory.lawf_unitcd);//法定计量单位
        lawf_qtyIndex = list.indexOf(ExcelHeadEnterInventory.lawf_qty);//法定数量
        secd_lawf_qtyIndex = list.indexOf(ExcelHeadEnterInventory.secd_lawf_qty);//第二法定数量
        secd_lawf_unitcdIndex = list.indexOf(ExcelHeadEnterInventory.secd_lawf_unitcd);//第二法定计量单位
        dcl_total_amtIndex = list.indexOf(ExcelHeadEnterInventory.dcl_total_amt);//总价
        dcl_qtyIndex = list.indexOf(ExcelHeadEnterInventory.dcl_qty);//数量
        natcdIndex = list.indexOf(ExcelHeadEnterInventory.natcd);//原产国(地区)
        rmkIndex = list.indexOf(ExcelHeadEnterInventory.rmk);//备注
        this.initMap();
    }

    public void initMap() {
        indexMap.put(gds_MtnoIndex, "账册备案料号,60");
        indexMap.put(gdecdIndex, "商品编码,250");
        indexMap.put(gds_nmIndex, "商品名称,510");
        indexMap.put(gds_spcf_model_descIndex, "商品规格型号,19");//double
        indexMap.put(dcl_unitcdIndex, "计量单位,10");
        indexMap.put(lawf_unitcdIndex, "法定计量单位,19");//double
        indexMap.put(lawf_qtyIndex, "法定数量,18");
        indexMap.put(secd_lawf_qtyIndex, "第二法定数量,100");
        indexMap.put(secd_lawf_unitcdIndex, "第二法定计量单位,18");
        indexMap.put(dcl_total_amtIndex, "总价,100");
        indexMap.put(dcl_qtyIndex, "数量,60");
        indexMap.put(natcdIndex, "原产国(地区),18");
        indexMap.put(rmkIndex, "备注,18");

    }


    public int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {

        //导入excel模板非空和长度判断
        boolean isEmpty = this.CheckedEmptyAndLen(indexMap, error_num, cell, rowNum, cell_num);
        if (!isEmpty) {
            return -1;
        }
        return 0;
    }

    public int getUnitCode(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {
        int flag = 0;
        String unitValue = cell.toString().replace(" ", "");
        if (cell_num == dcl_unitcdIndex) {
            String message = indexMap.get(cell_num).split(",")[0];
            if (!unitMap.containsKey(unitValue)) {
                error_num.put("error", String.format(String.format("导入失败请修改后重新导入，第%%d行第%%d列。<%s>数据格式不对！", message), rowNum + 1, cell_num + 1));
                flag = -1;
            } else {
                String unitCode = unitMap.get(unitValue);
                cell.setCellValue(unitCode);
                flag = 1;
            }
        }
        return flag;
    }
    public int getCountryCode(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num) {
        int flag = 0;
        String countryValue = cell.toString().replace(" ", "");
        if (cell_num == natcdIndex) {
            String message = indexMap.get(cell_num).split(",")[0];
            if (!countryMap.containsKey(countryValue)) {
                error_num.put("error", String.format(String.format("导入失败请修改后重新导入，第%%d行第%%d列。<%s>数据格式不对！", message), rowNum + 1, cell_num + 1));
                flag = -1;
            } else {
                String countryCode = countryMap.get(countryValue);
                cell.setCellValue(countryCode);
                flag = 1;
            }
        }
        return flag;
    }


    @Override
    public int checkRowAmount(List list, Map<String, Object> map) {
        return 0;
    }


}
