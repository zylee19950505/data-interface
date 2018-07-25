package com.xaeport.crossborder.service.ordermanage;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Order;
import com.xaeport.crossborder.data.mapper.OrderQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/*点击查看邮件详情
	* */
	public Order getOrderDetail(String guid) {
		Map<String,String> paramMap =  new HashMap<>();
		paramMap.put("id",guid);
		ImpOrderHead orderHead =  orderQueryMapper.queryOrderHead(paramMap);
		List<ImpOrderBody> orderBodies = orderQueryMapper.queryOrderBody(paramMap);
		Order order = new Order();
		order.setImpOrderHead(orderHead);
		order.setImpOrderLists(orderBodies);
		return order;
	}
}
