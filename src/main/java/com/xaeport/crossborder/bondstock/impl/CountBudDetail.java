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
        return 999;
    }

    @Override
    public String countItemno(List<ImpInventoryBody> impInventoryBodyList, Enterprise enterpriseDetail) {
        String flag = "false";
        Map<String, List<ImpInventoryBody>> itemNoData = BusinessUtils.classifyByGcode(impInventoryBodyList);
        List<ImpInventoryBody> impBondInvenBodyList;
        String item_no;
        String entCustomsCode = null;
        //查找企业信息里的区内企业信息
        //再在账册表头里查找创建账册的创建人所属企业ID匹配账册信息
        Enterprise enterprise = this.bondinvenImportMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        String emsNo = this.bondinvenImportMapper.queryBwlHeadType(enterprise.getId(),enterpriseDetail.getEnt_name());
        for (String itemNo : itemNoData.keySet()) {
            //获取按照料号划分的保税清单表体数据
            impBondInvenBodyList = itemNoData.get(itemNo);
            //获取料号
            item_no = impBondInvenBodyList.get(0).getItem_no();
            //根据料号，账册号查询是否存在账册表体数据
            entCustomsCode = enterpriseDetail.getCustoms_code();


            BwlListType bwlListType = this.bondinvenImportMapper.checkStockSurplus(entCustomsCode, item_no, emsNo);
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
                        this.logger.debug(String.format("生成清单库存：与库存申报单位不一致[账册号: %s][料号: %s,导入单位: %s,库存单位: %s]", emsNo, item_no, unit, bwlListType.getDcl_unitcd()));
                        stockCount = 0;
                        flag = itemNo+","+"生成清单库存：与库存申报单位不一致";
                        break;
                    }
                }
                //对比导入表体数量与仓库库存
                if (qtySum > stockCount || stockCount == 0) {
                    this.logger.debug(String.format("生成清单库存：预减库存量大于剩余库存量，或剩余库存等于零[账册号: %s,料号: %s,数量: %s,库存数量: %s]", emsNo, item_no, qtySum, stockCount));
                    flag = itemNo+","+"生成清单库存：预减库存量大于剩余库存量或剩余库存等于零";
                    break;
                }
            } else {
                this.logger.debug(String.format("生成清单库存：查询无账册信息[账册号: %s,料号: %s,海关编码: %s]", emsNo, item_no, entCustomsCode));
                flag = itemNo+","+"生成清单库存：查询无账册信息";
                break;
            }
        }
        return flag;
    }


    @Override
    //检查对应库存余量是否大于导入商品数量
    public int count(Map<String, Object> excelMap, Users user, String emsNo) {
        return 999;
    }

    //确认保税清单库存无误后，设置账册表体预减数量
    public void setPrevdRedcQty(Map<String, List<ImpInventoryBody>> itemNoData, String emsNo, String entCustomsCode) {
        List<ImpInventoryBody> impBondInvenBodyList;
        String item_no;
        for (String itemNo : itemNoData.keySet()) {
            impBondInvenBodyList = itemNoData.get(itemNo);
            item_no = impBondInvenBodyList.get(0).getItem_no();
            double qtySum = impBondInvenBodyList.stream().mapToDouble(ImpInventoryBody::getQuantity).sum();
            this.bondinvenImportMapper.setPrevdRedcQty(qtySum, item_no, emsNo, entCustomsCode);
            this.logger.debug(String.format("生成保税清单库存：成功完成预减操作[账册号: %s,料号: %s,数量: %s]", emsNo, item_no, qtySum));
        }
    }

}
