package com.xaeport.crossborder.bondstock.impl;

import com.xaeport.crossborder.bondstock.CountLoader;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.ReceiptMapper;
import com.xaeport.crossborder.tools.BusinessUtils;
import com.xaeport.crossborder.tools.SpringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

//账册表体实增操作
public class CountActlIncrease implements CountLoader {

    private Log logger = LogFactory.getLog(this.getClass());

    private ReceiptMapper receiptMapper = SpringUtils.getBean(ReceiptMapper.class);

    @Override
    public void count(BondInvtBsc bondInvtBsc) {
    }

    @Override
    public int count(Map<String, Object> excelMap, Users users, String emsNo) {
        return 999;
    }

    @Override
    public void count(PassPortHead passPortHead) {
        switch (passPortHead.getBind_typecd()) {
            //一车一票
            case "2":
                this.computeOneToOne(passPortHead);
                break;
            //一车多票
            case "1":
                this.computeOneToMany(passPortHead);
                break;
            //一票多车
            case "3":
                this.computeManyToOne(passPortHead);
                break;
        }
    }

    @Override
    public int count(List<ImpInventoryBody> impInventoryBodyList, Enterprise enterpriseDetail) {
        return 999;
    }

    //一车一单
    public void computeOneToOne(PassPortHead passPortHead) {
        //保税清单编号
        String bondInvtNo = passPortHead.getBond_invt_no();
        //根据保税清单编号查询核注清单表头表体
        BondInvtBsc bondInvtBsc = this.receiptMapper.queryBondInvtByBondInvtNo(bondInvtNo);
        List<BondInvtDt> bondInvtDtList = this.receiptMapper.queryBondInvtDtList(bondInvtBsc);
        if (!StringUtils.isEmpty(bondInvtBsc) && !StringUtils.isEmpty(bondInvtDtList)) {
            //按照料号获取商品数据
            Map<String, List<BondInvtDt>> gdsMtnoData = BusinessUtils.classifyByGdsMtno(bondInvtDtList);
            //料号
            String gds_mtno;
            //账册号
            String emsNo;
            //经营企业编号
            String bizopEtpsno;
            //核注清单表头
            List<BondInvtDt> bondInvtDts;
            for (String gdsMtno : gdsMtnoData.keySet()) {
                //获取按照料号划分的入区核注清单表体数据
                bondInvtDts = gdsMtnoData.get(gdsMtno);
                //获取料号
                gds_mtno = bondInvtDts.get(0).getGds_mtno();
                //获取账册号
                emsNo = bondInvtBsc.getPutrec_no();
                //经营企业编号
                bizopEtpsno = bondInvtBsc.getBizop_etpsno();
                //根据账册号查询是否存在该账册
                BwlHeadType bwlHeadType = this.receiptMapper.checkBwlHeadType(emsNo);
                BwlListType bwlList = this.receiptMapper.checkBwlListType(emsNo, gds_mtno, bizopEtpsno);
                if (!StringUtils.isEmpty(bwlHeadType) && !StringUtils.isEmpty(bwlList)) {
                    double qtySum = bondInvtDts.stream().mapToDouble(BondInvtDt::getQuantity).sum();
                    //进行实增数据库计算程序
                    this.receiptMapper.actlIncreaseBwlListType(qtySum, emsNo, gds_mtno, bizopEtpsno);
                    this.logger.debug(String.format("入区核放单库存：成功完成一车一单实增操作[账册号: %s,料号: %s,数量: %s]", emsNo, gds_mtno, qtySum));
                } else {
                    this.logger.debug(String.format("入区核放单库存：查询无账册信息，无法进行一车一单实增[账册号: %s,料号: %s,海关编码: %s]", emsNo, gds_mtno, bizopEtpsno));
                    continue;
                }
            }
        }
    }

    //一车多单
    public void computeOneToMany(PassPortHead passPortHead) {
        //保税清单编号
        String bondInvtNo = passPortHead.getBond_invt_no();
        String listNo = bondInvtNo.replaceAll("/", ",");
        //根据保税清单编号查询核注清单表头表体
        List<BondInvtBsc> bondInvtBscList = this.receiptMapper.queryBondInvtListByNo(listNo);
        List<BondInvtDt> bondInvtDtList = this.receiptMapper.queryBondInvtDtLists(listNo);
        if (!StringUtils.isEmpty(bondInvtBscList) && !StringUtils.isEmpty(bondInvtDtList)) {
            //按照料号获取商品数据
            Map<String, List<BondInvtDt>> gdsMtnoData = BusinessUtils.classifyByGdsMtno(bondInvtDtList);
            //料号
            String gds_mtno;
            //账册号
            String emsNo;
            //经营企业编号
            String bizopEtpsno;
            //核注清单表头
            List<BondInvtDt> bondInvtDts;
            for (String gdsMtno : gdsMtnoData.keySet()) {
                //获取按照料号划分的入区核注清单表体数据
                bondInvtDts = gdsMtnoData.get(gdsMtno);
                //获取料号
                gds_mtno = bondInvtDts.get(0).getGds_mtno();
                //获取账册号
                emsNo = bondInvtBscList.get(0).getPutrec_no();
                //经营企业编号
                bizopEtpsno = bondInvtBscList.get(0).getBizop_etpsno();

                //根据账册号查询是否存在该账册
                BwlHeadType bwlHeadType = this.receiptMapper.checkBwlHeadType(emsNo);
                BwlListType bwlList = this.receiptMapper.checkBwlListType(emsNo, gds_mtno, bizopEtpsno);
                if (!StringUtils.isEmpty(bwlHeadType) && !StringUtils.isEmpty(bwlList)) {
                    double qtySum = bondInvtDts.stream().mapToDouble(BondInvtDt::getQuantity).sum();
                    //进行实增数据库计算程序
                    this.receiptMapper.actlIncreaseBwlListType(qtySum, emsNo, gds_mtno, bizopEtpsno);
                    this.logger.debug(String.format("入区核放单库存：成功完成一车多单实增操作[账册号: %s,料号: %s,数量: %s]", emsNo, gds_mtno, qtySum));
                } else {
                    this.logger.debug(String.format("入区核放单库存：查询无账册信息，无法进行一车多单实增[账册号: %s,料号: %s,海关编码: %s]", emsNo, gds_mtno, bizopEtpsno));
                    continue;
                }
            }
        }
    }

    //一单多车
    public void computeManyToOne(PassPortHead passPortHead) {
        //保税清单编号
        String bondInvtNo = passPortHead.getBond_invt_no();
        //根据保税清单编号查询核注清单表头，核放单表体
        BondInvtBsc bondInvtBsc = this.receiptMapper.queryBondInvtByBondInvtNo(bondInvtNo);
        List<PassPortList> passPortListList = this.receiptMapper.queryPassPortList(passPortHead);
        if (!StringUtils.isEmpty(bondInvtBsc) && !StringUtils.isEmpty(passPortListList)) {
            //按照料号获取商品数据
            Map<String, List<PassPortList>> gdsMtNoData = BusinessUtils.classifyByGdsMtnoByPassPort(passPortListList);
            //料号
            String gds_mtNo;
            //账册号
            String emsNo;
            //经营企业编号
            String bizopEtpsno;
            //核放单表头
            List<PassPortList> passPortLists;
            for (String gdsMtNo : gdsMtNoData.keySet()) {
                //获取按照料号划分的入区核放单表体数据
                passPortLists = gdsMtNoData.get(gdsMtNo);
                //获取料号
                gds_mtNo = passPortLists.get(0).getGds_mtNo();
                //获取账册号
                emsNo = bondInvtBsc.getPutrec_no();
                //根据账册号查询是否存在该账册
                bizopEtpsno = bondInvtBsc.getBizop_etpsno();

                BwlHeadType bwlHeadType = this.receiptMapper.checkBwlHeadType(emsNo);
                BwlListType bwlList = this.receiptMapper.checkBwlListType(emsNo, gds_mtNo, bizopEtpsno);
                if (!StringUtils.isEmpty(bwlHeadType) && !StringUtils.isEmpty(bwlList)) {
                    double qtySum = passPortLists.stream().mapToDouble(PassPortList::getQuantity).sum();
                    //进行实增数据库计算程序
                    this.receiptMapper.actlIncreaseBwlListType(qtySum, emsNo, gds_mtNo, bizopEtpsno);
                    this.logger.debug(String.format("入区核放单库存：成功完成一单多车实增操作[账册号: %s,料号: %s,数量: %s]", emsNo, gds_mtNo, qtySum));
                } else {
                    this.logger.debug(String.format("入区核放单库存：查询无账册信息，无法进行一单多车实增[账册号: %s,料号: %s,海关编码: %s]", emsNo, gds_mtNo, bizopEtpsno));
                    continue;
                }
            }
        }
    }

}
