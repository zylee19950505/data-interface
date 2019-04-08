package com.xaeport.crossborder.bondstock.impl;

import com.xaeport.crossborder.bondstock.CountLoader;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.ReceiptMapper;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//账册表体实减方法
public class CountActlReduce implements CountLoader {
    private final Log logger = LogFactory.getLog(this.getClass());

    private ReceiptMapper receiptMapper = SpringUtils.getBean(ReceiptMapper.class);

    List<ImpInventoryHead> impInventoryHeads = new ArrayList<>();
    List<ImpInventoryBody> impInventoryBodyList = new ArrayList<>();

    @Override
    public int count(Map<String, Object> excelMap, Users user, String emsNo) {
        return 999;
    }

    @Override
    public void count(PassPortHead passPortHead) {
    }

    @Override
    public int count(List<ImpInventoryBody> impInventoryBodyList, Enterprise enterpriseDetail) {
        return 999;
    }

    @Override
    public void count(BondInvtBsc bondInvtBscData) {
        //TODO 保税出区进行实减操作
        //根据企业内部编码查询保税清单表头信息
        impInventoryHeads = receiptMapper.queryImpInventoryHeads(bondInvtBscData.getEtps_inner_invt_no());
        //根据企业内部编码查询保税清单表体信息
        impInventoryBodyList = receiptMapper.queryImpInventoryBodyList(bondInvtBscData.getEtps_inner_invt_no());

        if (!StringUtils.isEmpty(impInventoryBodyList) && !StringUtils.isEmpty(impInventoryHeads)) {
            Map<String, List<ImpInventoryBody>> itemNoData = BusinessUtils.classifyByGcode(impInventoryBodyList);
            //料号
            String item_no;
            //账册编号
            String emsNo;
            //经营企业编号
            String bizopEtpsno;
            //保税清单表体
            List<ImpInventoryBody> impBondInvenBody;
            for (String itemNo : itemNoData.keySet()) {
                //获取按照料号划分的保税清单表体数据
                impBondInvenBody = itemNoData.get(itemNo);
                //获取料号
                item_no = impBondInvenBody.get(0).getItem_no();
                //获取账册号
                emsNo = impInventoryHeads.get(0).getEms_no();
                //经营企业编号
                bizopEtpsno = bondInvtBscData.getBizop_etpsno();
                //根据料号，账册号查询是否存在账册表体数据
                BwlListType bwlListType = this.receiptMapper.checkStockSurplus(bondInvtBscData.getCrt_user(), item_no, emsNo, bizopEtpsno);
                double qtySum = 0;
                double stockCount = 0;
                if (!StringUtils.isEmpty(bwlListType)) {
                    //获取导入保税清单的表体申报总数
                    qtySum = impBondInvenBody.stream().mapToDouble(ImpInventoryBody::getQuantity).sum();
                    //获取账册表体所剩余的库存量
                    stockCount = StringUtils.isEmpty(bwlListType.getSurplus()) ? 0 : bwlListType.getSurplus();
                    //对比导入表体数量与仓库库存
                    if (qtySum > stockCount || stockCount <= 0) {
                        this.logger.info("出区核注清单解析回执：实减库存量大于剩余库存量，或剩余库存小于等于零");
                        continue;
                    } else {
                        //计算数量是否符合
                        if ((bwlListType.getPrevdRedcQty() - qtySum) >= 0) {
                            this.receiptMapper.setPrevdRedcQty(qtySum, item_no, emsNo, bizopEtpsno);
                            this.logger.info("出区核注清单成功进行实减操作");
                        } else {
                            this.logger.info("出区核注清单解析回执：实减操作计算数据为负");
                            continue;
                        }
                    }
                } else {
                    this.logger.info("出区核注清单解析回执：查询无对应账册表体数据，无法进行实减操作");
                    continue;
                }
            }
        }
    }

}
