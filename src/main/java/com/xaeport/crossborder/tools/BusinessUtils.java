package com.xaeport.crossborder.tools;

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


}
