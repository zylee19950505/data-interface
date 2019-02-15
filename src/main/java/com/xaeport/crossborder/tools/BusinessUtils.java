package com.xaeport.crossborder.tools;

import com.xaeport.crossborder.data.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2018-15-31.
 * 业务处理工具
 */
public class BusinessUtils {

    /**
     * 将集合按照相应条件拆分成Map<代码,数据>
     * Map<归类条件字段,需要归类数据集合>
     */

    //根据企业Id，对跨境清单数据进行归类
    public static Map<String, List<ImpInventoryHead>> getEntIdInventoryMap(List<ImpInventoryHead> impInventoryHeadLists) {
        Map<String, List<ImpInventoryHead>> entIdDataListMap = new HashMap<String, List<ImpInventoryHead>>();
        String entId = null;
        for (ImpInventoryHead impInventoryHead : impInventoryHeadLists) {
            entId = impInventoryHead.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpInventoryHead> impInventoryHeadList = entIdDataListMap.get(entId);
                impInventoryHeadList.add(impInventoryHead);
            } else {
                List<ImpInventoryHead> impInventoryHeadList = new ArrayList<ImpInventoryHead>();
                impInventoryHeadList.add(impInventoryHead);
                entIdDataListMap.put(entId, impInventoryHeadList);
            }
        }
        return entIdDataListMap;
    }

    //根据企业Id，对跨境订单数据进行归类
    public static Map<String, List<ImpOrderHead>> getEntIdOrderMap(List<ImpOrderHead> impOrderHeadLists) {
        Map<String, List<ImpOrderHead>> entIdDataListMap = new HashMap<String, List<ImpOrderHead>>();
        String entId = null;
        for (ImpOrderHead impOrderHead : impOrderHeadLists) {
            entId = impOrderHead.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpOrderHead> impOrderHeadList = entIdDataListMap.get(entId);
                impOrderHeadList.add(impOrderHead);
            } else {
                List<ImpOrderHead> impOrderHeadList = new ArrayList<ImpOrderHead>();
                impOrderHeadList.add(impOrderHead);
                entIdDataListMap.put(entId, impOrderHeadList);
            }
        }
        return entIdDataListMap;
    }

    //根据企业Id，对跨境支付单数据进行归类
    public static Map<String, List<ImpPayment>> getEntIdDataMap(List<ImpPayment> impPaymentLists) {
        Map<String, List<ImpPayment>> entIdDataListMap = new HashMap<String, List<ImpPayment>>();
        String entId = null;
        for (ImpPayment impPayment : impPaymentLists) {
            entId = impPayment.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpPayment> impPaymentList = entIdDataListMap.get(entId);
                impPaymentList.add(impPayment);
            } else {
                List<ImpPayment> impPaymentList = new ArrayList<ImpPayment>();
                impPaymentList.add(impPayment);
                entIdDataListMap.put(entId, impPaymentList);
            }
        }
        return entIdDataListMap;
    }

    //根据企业Id，对跨境运单数据进行归类
    public static Map<String, List<ImpLogistics>> getEntIdlogisticDataMap(List<ImpLogistics> impLogisticsLists) {
        Map<String, List<ImpLogistics>> entIdDataListMap = new HashMap<String, List<ImpLogistics>>();
        String entId = null;
        for (ImpLogistics impLogistics : impLogisticsLists) {
            entId = impLogistics.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpLogistics> impLogisticsList = entIdDataListMap.get(entId);
                impLogisticsList.add(impLogistics);
            } else {
                List<ImpLogistics> impLogisticsList = new ArrayList<ImpLogistics>();
                impLogisticsList.add(impLogistics);
                entIdDataListMap.put(entId, impLogisticsList);
            }
        }
        return entIdDataListMap;
    }

    //根据企业Id，对跨境运单状态数据进行归类
    public static Map<String, List<ImpLogistics>> getEntIdStatusDataMap(List<ImpLogistics> impLogisticsStatusLists) {
        Map<String, List<ImpLogistics>> entIdDataListMap = new HashMap<String, List<ImpLogistics>>();
        String entId = null;
        for (ImpLogistics impLogisticsStatus : impLogisticsStatusLists) {
            entId = impLogisticsStatus.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpLogistics> impLogisticsStatusList = entIdDataListMap.get(entId);
                impLogisticsStatusList.add(impLogisticsStatus);
            } else {
                List<ImpLogistics> impLogisticsStatusList = new ArrayList<ImpLogistics>();
                impLogisticsStatusList.add(impLogisticsStatus);
                entIdDataListMap.put(entId, impLogisticsStatusList);
            }
        }
        return entIdDataListMap;
    }

    //根据提运单号，对跨境入库明细单进行分类
    public static Map<String, List<ImpDeliveryHead>> getBillNoDeliveryMap(List<ImpDeliveryHead> impDeliveryHeadList) {
        Map<String, List<ImpDeliveryHead>> entIdDataListMap = new HashMap<String, List<ImpDeliveryHead>>();
        String billNo = null;
        for (ImpDeliveryHead impDeliveryHead : impDeliveryHeadList) {
            billNo = impDeliveryHead.getBill_no();
            if (entIdDataListMap.containsKey(billNo)) {
                List<ImpDeliveryHead> impLogisticsStatusList = entIdDataListMap.get(billNo);
                impLogisticsStatusList.add(impDeliveryHead);
            } else {
                List<ImpDeliveryHead> impDeliveryHeadLists = new ArrayList<ImpDeliveryHead>();
                impDeliveryHeadLists.add(impDeliveryHead);
                entIdDataListMap.put(billNo, impDeliveryHeadLists);
            }
        }
        return entIdDataListMap;
    }

    //根据料号，对保税核注清单表体数据进行分类
    public static Map<String, List<BondInvtDt>> classifyByGdsMtno(List<BondInvtDt> bondInvtDtList) {
        Map<String, List<BondInvtDt>> gdsMtnoDataListMap = new HashMap<String, List<BondInvtDt>>();
        String gdsMtno = null;
        for (BondInvtDt bondInvtDt : bondInvtDtList) {
            gdsMtno = bondInvtDt.getGds_mtno();
            if (gdsMtnoDataListMap.containsKey(gdsMtno)) {
                List<BondInvtDt> bondInvtDts = gdsMtnoDataListMap.get(gdsMtno);
                bondInvtDts.add(bondInvtDt);
            } else {
                List<BondInvtDt> bondInvtDts = new ArrayList<>();
                bondInvtDts.add(bondInvtDt);
                gdsMtnoDataListMap.put(gdsMtno, bondInvtDts);
            }
        }
        return gdsMtnoDataListMap;
    }

    //根据料号，对保税清单表体数据进行分类
    public static Map<String, List<ImpInventoryBody>> classifyByGcode(List<ImpInventoryBody> impInventoryBodyList) {
        Map<String, List<ImpInventoryBody>> itemRecordNoDataListMap = new HashMap<String, List<ImpInventoryBody>>();
        String itemRecordNo = null;
        for (ImpInventoryBody impInventoryBody : impInventoryBodyList) {
            itemRecordNo = impInventoryBody.getItem_record_no();
            if (itemRecordNoDataListMap.containsKey(itemRecordNo)) {
                List<ImpInventoryBody> impInventoryBodies = itemRecordNoDataListMap.get(itemRecordNo);
                impInventoryBodies.add(impInventoryBody);
            } else {
                List<ImpInventoryBody> impInventoryBodies = new ArrayList<>();
                impInventoryBodies.add(impInventoryBody);
                itemRecordNoDataListMap.put(itemRecordNo, impInventoryBodies);
            }
        }
        return itemRecordNoDataListMap;
    }

}
