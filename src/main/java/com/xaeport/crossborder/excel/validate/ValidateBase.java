package com.xaeport.crossborder.excel.validate;

import com.xaeport.crossborder.data.entity.UnitCode;
import com.xaeport.crossborder.data.mapper.SystemToolMapper;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入校验基类
 * Created by lzy on 2018/06/28.
 */
public abstract class ValidateBase {

    protected Map<String, String> unitMap = new HashMap<>();

    //校验导出数据
    public abstract int CheckRowError(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num);

    //初始化校验导入excel表头索引
    public abstract void getIndexValue(List<String> list);

    //初始化校验字段索引
    public abstract void initMap();

    //申报计量单位转化
    public abstract int getUnitCode(Cell cell, Map<String, Object> error_num, int rowNum, int cell_num);

    //非空和长度判断
    protected boolean CheckedEmptyAndLen(Map<Integer, String> indexMap, Map<String, Object> error_num, Cell cell, int rowNum, int cell_num) {
        if (indexMap.containsKey(cell_num)) {
            String messInfo = indexMap.get(cell_num).split(",")[0];
            int len = Integer.parseInt(indexMap.get(cell_num).split(",")[1]);
            if (messInfo.equals("运费") || messInfo.equals("保费")|| messInfo.equals("运杂费")|| messInfo.equals("保价费")|| messInfo.equals("非现金抵扣金额")|| messInfo.equals("代扣税款")) {
                return true;
            } else if (ValidateUtil.checkStrValue(cell) == -1) {
                error_num.put("error", String.format("导入失败请修改后重新导入，第%d行第%d列。<%s>不能为空！", rowNum + 1, cell_num + 1, messInfo));
                return false;
            } else if (cell.toString().replaceAll(" ", "").length() > len) {
                error_num.put("error", String.format("导入失败请修改后重新导入，第%d行第%d列。<%s >数据格式不对！", rowNum + 1, cell_num + 1, messInfo));
                return false;
            }
        }
        return true;
    }

    //判断数值型的是否大于0或者数据格式是否正确
    protected boolean CheckNumberType(int flag, Map<String, Object> infMap, int rowNum, int cell_num, String message) {
        switch (flag) {
            case 1:
                infMap.put("error", String.format("导入失败请修改后重新导入，第%d行第%d列。<%s>不能为空！", rowNum + 1, cell_num + 1, message));
                return false;
            case 2:
                infMap.put("error", String.format("导入失败请修改后重新导入，第%d行第%d列。<%s>输入数据格式不对！", rowNum + 1, cell_num + 1, message));
                return false;
            case 3:
                infMap.put("error", String.format("导入失败请修改后重新导入，第%d行第%d列。<%s>必须大于0！", rowNum + 1, cell_num + 1, message));
                return false;
        }
        return true;
    }

    //校验模板是否正确
    public boolean isListSame(List<String> list1, List<String> list2, Map<String, Object> map) {
        if (null != list1 && null != list2) {
            if (list1.size() == list2.size() && list2.containsAll(list1)) {
                return true;
            } else {
                map.put("error", "导入模板错误！");
                return false;
            }
        }
        return true;
    }

    //初始化申报计量单位参数
    public void initUnitCode() {
        SystemToolMapper systemToolMapper = SpringUtils.getBean(SystemToolMapper.class);
        List<UnitCode> unitCode = systemToolMapper.queryUnitCode();
        if (unitCode.isEmpty()) return;
        for (UnitCode uc : unitCode) {
            unitMap.put(uc.getUnit_name(), uc.getUnit_code());
        }
    }

}
