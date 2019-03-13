package com.xaeport.crossborder.excel.read;

import com.xaeport.crossborder.excel.headings.*;
import com.xaeport.crossborder.excel.validate.ValidateBase;
import com.xaeport.crossborder.excel.validate.ValidateInstance;
import com.xaeport.crossborder.excel.validate.ValidateUtil;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取excel数据
 * Created by lzy on 2018/06/27.
 */
public class ReadExcel {
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 获取excel book
     *
     * @param inputStream 文件
     * @param fileName    文件名
     * @return
     * @throws IOException
     */

    private Workbook getWorkbook(InputStream inputStream, String fileName) throws IOException {
        Workbook workbook = null;
        long start = System.currentTimeMillis();
        POIFSFileSystem fs;
        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (fileName.endsWith("xls")) {
            fs = new POIFSFileSystem(inputStream);
            workbook = new HSSFWorkbook(fs);
        }
        this.log.info("获取workbook耗时" + (System.currentTimeMillis() - start));
        return workbook;
    }

    /**
     * 读取excel数据
     *
     * @param inputStream excel文件
     * @param fileName    excel文件名称
     * @return
     */
    public Map<String, Object> readExcelData(InputStream inputStream, String fileName, String type) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Sheet sheet;
        try (Workbook workbook = this.getWorkbook(inputStream, fileName)) {
            //根据需求文档只读sheet1中的数据
            sheet = workbook.getSheetAt(0);
            if (sheet == null) return map;
            map = this.getSheetData(sheet, type);
        } catch (IOException e) {
            this.log.error(String.format("读取excel失败%s", fileName), e);
        }
        return map;
    }

    /**
     * 获取excel中的每行的数据
     *
     * @param sheet
     */
    private Map<String, Object> getSheetData(Sheet sheet, String type) {
        ValidateBase excelCheck = ValidateInstance.getValidateObject(type);
        excelCheck.initUnitCode();//初始化申报计量单位参数
        excelCheck.initCountryCode();
        Map<String, Object> map = new HashMap<>();
        List<List<String>> dataList = new ArrayList<>();
        for (int i = 0, len = sheet.getPhysicalNumberOfRows(); i < len; i++) {
            List<String> rowList = new ArrayList<>();
            Row row = sheet.getRow(i);
            int count = ValidateUtil.CheckRowNull(row);
            if (row != null && count > 0) {
                //按照表头的长度来取值，空的补齐，以免影响索引的值
                int lastCell = sheet.getRow(0).getLastCellNum();
                int firstCell = sheet.getRow(0).getFirstCellNum();
                for (int z = firstCell; z < lastCell; z++) {
                    Cell cell = row.getCell(z);
                    if (cell == null) {
                        rowList.add("");
                        int amount = excelCheck.CheckRowError(cell, map, i, z);
                        if (amount < 0) {
                            return map;
                        }
                        continue;
                    }

                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }

                    //用于excel数据校验
                    if (i > 0) {
                        int amount = excelCheck.CheckRowError(cell, map, i, z);
                        if (amount < 0) {
                            return map;
                        }
                    }

                    if (i > 0) {
                        int flag = excelCheck.getUnitCode(cell, map, i, z);
                        if (flag < 0) {
                            return map;
                        }
                    }
                    if (i > 0) {
                        int flag = excelCheck.getCountryCode(cell, map, i, z);
                        if (flag < 0) {
                            return map;
                        }
                    }

                    rowList.add(ValidateUtil.getStr(cell.toString().replaceAll(" ", "")));
                }

                //用于校验模板是否正确
                if (i == 0) {
                    boolean listSame;
                    switch (type) {
                        case "order":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadOrder.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "payment":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadPayment.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "waybill":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadWaybill.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "detail":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadDetail.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "waybillstatus":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadWaybillStatus.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "enterInventory":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadEnterInventory.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "bondInven":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadBondInven.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                        case "bondorder":
                            listSame = excelCheck.isListSame(rowList, ExcelHeadBondOrder.getList(), map);
                            if (!listSame) {
                                return map;
                            }
                            break;
                    }

                }

                //用于初始化表头的索引
                if (i == 0) {
                    excelCheck.getIndexValue(rowList);
                }
                //用于判断订单号不能超过15
                if (i > 0 && count > 0) {
                    int num = excelCheck.checkRowAmount(rowList, map);
                    if (num > 0) {
                        return map;
                    }
                }

                dataList.add(rowList);
            }
        }
        map.put("excelData", dataList);
        return map;
    }
}