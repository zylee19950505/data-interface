package com.xaeport.crossborder.tools;


import com.xaeport.crossborder.data.entity.ImpOrderHead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baozhe on 2017-7-23.
 * 业务处理工具
 */
public class BusinessUtils {

    /**
     * 将EntryHead集合按照总单号拆分成Map<订单号,EntryHead集合>
     *
     * @param entryHeadList EntryHead集合
     * @return Map<订单号,EntryHead集合>
     */
    public static Map<String, List<ImpOrderHead>> getOrderNoHeadListMap(List<ImpOrderHead> entryHeadList) {
        Map<String, List<ImpOrderHead>> orderNoHeadListMap = new HashMap<String, List<ImpOrderHead>>();
        String orderNo = null;
        for (ImpOrderHead entryHead : entryHeadList) {
            orderNo = entryHead.getOrder_No();
            if (orderNoHeadListMap.containsKey(orderNo)) {
                List<ImpOrderHead> entryHeads = orderNoHeadListMap.get(orderNo);
                entryHeads.add(entryHead);
            } else {
                List<ImpOrderHead> entryHeads = new ArrayList<ImpOrderHead>();
                entryHeads.add(entryHead);
                orderNoHeadListMap.put(orderNo, entryHeads);
            }
        }
        return orderNoHeadListMap;
    }


}
