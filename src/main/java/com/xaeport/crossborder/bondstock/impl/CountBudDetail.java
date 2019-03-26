package com.xaeport.crossborder.bondstock.impl;

import com.xaeport.crossborder.bondstock.CountLoader;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BondinvenImportMapper;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

//账册表体预减方法
public class CountBudDetail implements CountLoader {

    private final Log logger = LogFactory.getLog(this.getClass());
    private BondinvenImportMapper bondinvenImportMapper = SpringUtils.getBean(BondinvenImportMapper.class);

    @Override
    public void count(BondInvtBsc bondInvtBsc) { }

    @Override
    public void count(PassPortHead passPortHead) { }

    @Override
    //检查对应库存余量是否大于导入商品数量
    public int count(List<ImpInventoryBody> impInventoryBodyList, Enterprise enterpriseDetail) {
        int flag = 0;
        Map<String, List<ImpInventoryBody>> itemRecordNoData = BusinessUtils.classifyByGcode(impInventoryBodyList);
        List<ImpInventoryBody> impBondInvenBodyList;
        String item_record_no;
        String entCustomsCode = null;
        //查找企业信息里的区内企业信息
        //再在账册表头里查找创建账册的创建人所属企业ID匹配账册信息
        Enterprise enterprise = this.bondinvenImportMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        String emsNo = this.bondinvenImportMapper.queryBwlHeadType(enterprise.getId(),enterpriseDetail.getEnt_name());
        for (String itemRecordNo : itemRecordNoData.keySet()) {
            //获取按照料号划分的保税清单表体数据
            impBondInvenBodyList = itemRecordNoData.get(itemRecordNo);
            //获取料号
            item_record_no = impBondInvenBodyList.get(0).getItem_record_no();
            //根据料号，账册号查询是否存在账册表体数据
            entCustomsCode = enterpriseDetail.getCustoms_code();


            BwlListType bwlListType = this.bondinvenImportMapper.checkStockSurplus(entCustomsCode, item_record_no, emsNo);
            if (!StringUtils.isEmpty(bwlListType)) {
                //获取导入保税清单的表体总数
                double qtySum = impBondInvenBodyList.stream().mapToDouble(ImpInventoryBody::getQuantity).sum();
                //获取账册表体所剩余的库存量
                double stockCount = StringUtils.isEmpty(bwlListType.getSurplus()) ? 0 : bwlListType.getSurplus();
                String unit;
                for (ImpInventoryBody impInventoryBody : impBondInvenBodyList) {
                    unit = impInventoryBody.getUnit();
                    //对比导入表体单位与仓库单位
                    if (!unit.equals(bwlListType.getDcl_unitcd())) {
                        stockCount = 0;
                        flag = 3;
                        break;
                    }
                }
                //对比导入表体数量与仓库库存
                if (qtySum > stockCount || stockCount == 0) {
                    flag = 3;
                    break;
                }
            } else {
                flag = 3;
                break;
            }
        }
        if (flag == 0) {
            //确认保税清单库存无误后，设置账册表体预减数量
            this.setPrevdRedcQty(itemRecordNoData, emsNo, entCustomsCode);
            return flag;
        } else {
            return flag;
        }
    }

    @Override
    //检查对应库存余量是否大于导入商品数量
    public int count(Map<String, Object> excelMap, Users user, String emsNo) {
        return 999;
    }

    //确认保税清单库存无误后，设置账册表体预减数量
    public void setPrevdRedcQty(Map<String, List<ImpInventoryBody>> itemRecordNoData, String emsNo, String entCustomsCode) {
        List<ImpInventoryBody> impBondInvenBodyList;
        String item_record_no;
        for (String itemRecordNo : itemRecordNoData.keySet()) {
            impBondInvenBodyList = itemRecordNoData.get(itemRecordNo);
            item_record_no = impBondInvenBodyList.get(0).getItem_record_no();
            double qtySum = impBondInvenBodyList.stream().mapToDouble(ImpInventoryBody::getQuantity).sum();
            this.bondinvenImportMapper.setPrevdRedcQty(qtySum, item_record_no, emsNo, entCustomsCode);
        }
    }

}
