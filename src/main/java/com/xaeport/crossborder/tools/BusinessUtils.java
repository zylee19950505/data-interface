package com.xaeport.crossborder.tools;

import com.xaeport.crossborder.data.entity.ImpLogistics;
import com.xaeport.crossborder.data.entity.ImpLogisticsStatus;
import com.xaeport.crossborder.data.entity.ImpPayment;

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
     * @param impPaymentLists impPayment集合
     * @return Map<企业ID码,impPayment集合>
     */
    public static Map<String, List<ImpPayment>> getEntIdDataMap(List<ImpPayment> impPaymentLists) {
        Map<String, List<ImpPayment>> entIdDataListMap = new HashMap<String, List<ImpPayment>>();
        String entId = null;
        for (ImpPayment impPayment : impPaymentLists) {
            entId = impPayment.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpPayment> impPayments = entIdDataListMap.get(entId);
                impPayments.add(impPayment);
            } else {
                List<ImpPayment> impPayments = new ArrayList<ImpPayment>();
                impPayments.add(impPayment);
                entIdDataListMap.put(entId, impPayments);
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

    public static Map<String, List<ImpLogisticsStatus>> getEntIdStatusDataMap(List<ImpLogisticsStatus> impLogisticsStatusLists) {
        Map<String, List<ImpLogisticsStatus>> entIdDataListMap = new HashMap<String, List<ImpLogisticsStatus>>();
        String entId = null;
        for (ImpLogisticsStatus impLogisticsStatus : impLogisticsStatusLists) {
            entId = impLogisticsStatus.getEnt_id();
            if (entIdDataListMap.containsKey(entId)) {
                List<ImpLogisticsStatus> impLogisticsStatusList = entIdDataListMap.get(entId);
                impLogisticsStatusList.add(impLogisticsStatus);
            } else {
                List<ImpLogisticsStatus> impLogisticsStatusList = new ArrayList<ImpLogisticsStatus>();
                impLogisticsStatusList.add(impLogisticsStatus);
                entIdDataListMap.put(entId, impLogisticsStatusList);
            }
        }
        return entIdDataListMap;
    }

}
