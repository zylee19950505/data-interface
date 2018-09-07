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
     * 将impPayment集合按照总单号拆分成Map<企业ID码,impPayment集合>
     *
     * @param impInventoryHeadLists impPayment集合
     * @return Map<企业ID码,impPayment集合>
     */

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

}
