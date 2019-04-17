package com.xaeport.crossborder.service.detaillistmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.bondstock.CountLoader;
import com.xaeport.crossborder.bondstock.impl.CountBudDetail;
import com.xaeport.crossborder.configuration.SystemConstants;
import com.xaeport.crossborder.data.entity.*;
import com.xaeport.crossborder.data.mapper.BuilderDetailMapper;
import com.xaeport.crossborder.data.mapper.DetailBuilderMapper;
import com.xaeport.crossborder.data.status.StatusCode;
import com.xaeport.crossborder.tools.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class DetailBuilderService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    DetailBuilderMapper detailBuilderMapper;

    @Autowired
    BuilderDetailMapper builderDetailMapper;

    public List<BuilderDetail> queryBuilderDetailList(Map<String, String> paramMap) {
        return detailBuilderMapper.queryBuilderDetailList(paramMap);
    }

    public Integer queryBuilderDetailCount(Map<String, String> paramMap) {
        return detailBuilderMapper.queryBuilderDetailCount(paramMap);
    }

    public boolean builderDetail(Map<String, String> paramMap, Users currentUser) {
        String submitKeys = paramMap.get("submitKeys");
        String[] orders = submitKeys.split(",");
        List<ImpOrderBody> impOrderBodyList;
        List<ImpInventoryBody> impInventoryBodyList;
        //建一张缓存数据表,往表里插入数据,再启动一个单线程去扫描这个表;
        Map<String, String> map = new HashMap<>();
        try {
            for (String orderNo : orders) {
                //先在这里进行库存检查
                impOrderBodyList = this.builderDetailMapper.queryOrderList(orderNo);

                String ent_id = currentUser.getEnt_Id();
                Enterprise enterpriseDetail = builderDetailMapper.getEnterpriseDetail(ent_id);
                try {
                    impInventoryBodyList = this.getInventoryBodyData(impOrderBodyList, enterpriseDetail);
                } catch (Exception e) {
                    this.logger.error("组装清单[" + orderNo + "]数据时发生异常", e);
                    continue;
                }

                CountLoader countLoader = new CountBudDetail();
                int flag = countLoader.count(impInventoryBodyList, enterpriseDetail);
                if (flag == 0) {
                    String id = IdUtils.getShortUUId();
                    map.put("id", id);
                    map.put("orderNo", orderNo);
                    map.put("dataStatus", "QDSCZ");
                    int count = this.detailBuilderMapper.queryBuilderCache(map);
                    if (count > 0) {
                        this.detailBuilderMapper.updateBuilderCache(map);
                    } else {
                        this.detailBuilderMapper.insertBuilderCache(map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<ImpInventoryBody> getInventoryBodyData(List<ImpOrderBody> impOrderBodyList, Enterprise enterpriseDetail) {

        //查找账册信息里企业id是订单的企业id的情况
        //String bws_no = this.builderDetailMapper.queryBwsNoByEntId(enterpriseDetail.getId(), enterpriseDetail.getEnt_name());

        //账册企业信息的企业信息
        Enterprise enterprise = this.builderDetailMapper.queryAreaenterprise(enterpriseDetail.getArea_code());
        String emsNo = this.builderDetailMapper.queryBwlHeadType(enterprise.getId());
        List<ImpInventoryBody> impInventoryBodyList = new ArrayList<>();
        int count = 0;
        for (ImpOrderBody impOrderBody : impOrderBodyList) {
            count++;
            ImpInventoryBody impInventoryBody = new ImpInventoryBody();
            //根据账册号找账册表体信息,通过商品货号确定商品账册信息
            BwlListType bwlListType = this.builderDetailMapper.queryBwsListByEntBwsNo(emsNo, impOrderBody.getItem_No(), enterpriseDetail.getBrevity_code());
            impInventoryBody.setQty(impOrderBody.getQty());//商品实际数量
            impInventoryBody.setItem_no(impOrderBody.getItem_No());//企业商品货号: 电商企业自定义的商品货号（SKU）。
            impInventoryBody.setItem_name(impOrderBody.getItem_Name());//企业商品品名: 交易平台销售商品的中文名称。
            impInventoryBody.setQuantity(Double.parseDouble(impOrderBody.getQty()));
            impInventoryBody.setUnit(impOrderBody.getUnit());//计量单位
            impInventoryBody.setGds_seqno(impOrderBody.getGds_seqno());
            impInventoryBodyList.add(impInventoryBody);
        }
        return impInventoryBodyList;
    }
}