package com.xaeport.crossborder.excel.validate;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * excel校验工具类
 * Created by lzy on 2018/06/28.
 */
public class ValidateUtil {

    //excel中输入字符为空判断
    public static int checkStrValue(Cell cell) {
        if (cell == null || cell.toString().replaceAll(" ", "").isEmpty() || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return -1;
        } else {
            return 0;
        }
    }

    // //判断行为空
    public static int CheckRowNull(Row row) {
        int num = 0;
        if (row != null) {
            Iterator<Cell> cellItr = row.iterator();
            while (cellItr.hasNext()) {
                Cell cell = cellItr.next();
                if (cell.getCellType() != Cell.CELL_TYPE_BLANK && !cell.toString().replaceAll(" ", "").equals("")) {
                    num++;
                }
            }
        }
        return num;
    }

    public static int checkDoubleValue(Cell cell) {
        double value;
        int flag = 0;
        if (cell == null || cell.toString().replaceAll(" ", "").isEmpty() || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            flag = 0;
        } else {
            try {
                value = Double.parseDouble(cell.toString().replaceAll(" +", ""));
                if (value <= 0) {
                    flag = 3;
                }
            } catch (NumberFormatException e) {
                flag = 2;
            }
        }
        return flag;
    }

    //用于取出制表、回车、换行符
    public static String getStr(String str) {
        String dest = "";
        if (!StringUtils.isEmpty(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
