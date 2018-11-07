package com.xaeport.crossborder.service.ordermanage;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Order;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.mapper.OrderQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class OrderQueryService {

	@Autowired
	OrderQueryMapper orderQueryMapper;

	public List<ImpOrderHead> queryOrderHeadList(Map<String, String> paramMap) {
		return orderQueryMapper.queryOrderHeadList(paramMap);
	}

	public Integer queryOrderHeadListCount(Map<String, String> paramMap) {
		return orderQueryMapper.queryOrderHeadListCount(paramMap);
	}

	public List<ImpOrderBody> queryOrderBodyList(Map<String, String> paramMap) {
		return orderQueryMapper.queryOrderBodyList(paramMap);
	}

	public Integer queryOrderBodyListCount(Map<String, String> paramMap) {
		return orderQueryMapper.queryOrderBodyListCount(paramMap);
	}

	public Order getOrderDetail(String guid) {
		Map<String,String> paramMap =  new HashMap<>();
		paramMap.put("id",guid);
		ImpOrderHead orderHead =  orderQueryMapper.queryOrderHead(paramMap);
		List<ImpOrderBody> orderBodies = orderQueryMapper.queryOrderBody(paramMap);
		Verify verify = orderQueryMapper.queryVerifyDetail(paramMap);
		Order order = new Order();
		order.setImpOrderHead(orderHead);
		order.setImpOrderLists(orderBodies);
		order.setVerify(verify);
		return order;
	}

	@Transactional
	public Map<String,String> saveOrderDetail(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		if (saveOrderDetail(entryHead, entryLists, rtnMap,"订单查询-编辑-重报")) return rtnMap;

		rtnMap.put("result", "true");
		rtnMap.put("msg", "编辑信息成功，请到“订单申报”处重新进行申报！");
		return rtnMap;

	}
	public boolean saveOrderDetail(
			LinkedHashMap<String, String> entryHead,
			List<LinkedHashMap<String, String>> entryLists,
			Map<String, String> rtnMap,String notes
	){
		if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
			rtnMap.put("result", "false");
			rtnMap.put("msg", "未发现需要修改数据！");
			return true;
		}
		String entryHeadId = entryHead.get("entryhead_guid");
		if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
			// 更新表头数据
			this.orderQueryMapper.updateOrderHead(entryHead);
		}
		if (!CollectionUtils.isEmpty(entryLists)) {
			// 更新表体数据
			for (LinkedHashMap<String, String> entryList : entryLists) {
				if (!CollectionUtils.isEmpty(entryList) && entryList.size() > 2) {
					orderQueryMapper.updateOrderList(entryList);
				}
			}
			this.orderQueryMapper.updateOrderHeadByList(entryHead);
		}
		return false;
	}

	public ImpOrderHead returnOrderDetail(Map<String, String> paramMap) {
		return orderQueryMapper.returnOrderDetail(paramMap);
	}

	@Transactional
	public Map<String,String> saveLogicalDetail(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		if (saveOrderDetailByOrder(entryHead, entryLists, rtnMap,"订单查询-编辑-重报")) return rtnMap;

		rtnMap.put("result", "true");
		rtnMap.put("msg", "编辑信息成功，请到“订单申报”处确认是否校验通过！");
		return rtnMap;

	}
	public boolean saveOrderDetailByOrder(
			LinkedHashMap<String, String> entryHead,
			List<LinkedHashMap<String, String>> entryLists,
			Map<String, String> rtnMap,
			String notes
	){
		if ((CollectionUtils.isEmpty(entryHead) && entryHead.size() < 1) && CollectionUtils.isEmpty(entryLists)) {
			rtnMap.put("result", "false");
			rtnMap.put("msg", "未发现需要修改数据！");
			return true;
		}
		String guid = entryHead.get("entryhead_guid");
		if (!CollectionUtils.isEmpty(entryHead) && entryHead.size() > 1) {
			// 更新表头数据
			this.orderQueryMapper.updateOrderHeadByLogic(entryHead);
		}
		if (!CollectionUtils.isEmpty(entryLists)) {
			// 更新表体数据
			for (LinkedHashMap<String, String> entryList : entryLists) {
				if (!CollectionUtils.isEmpty(entryList) && entryList.size() > 2) {
					orderQueryMapper.updateOrderBodyByLogic(entryList);
				}
			}
		}
		orderQueryMapper.deleteVerifyStatus(guid);
		return false;
	}

}
