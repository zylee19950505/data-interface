package com.xaeport.crossborder.service.ordermanage;


import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/*
 * 订单申报
 */
@Service
public class OrderDeclareSevice {

	@Autowired
	OrderDeclareMapper orderDeclareMapper;

	/*
     * 查询订单申报数据
     */
	public List<Map<String,String>> queryOrderDeclareList(Map<String, Object> paramMap) {
		return  orderDeclareMapper.queryOrderDeclareList(paramMap);
	}

	/*
     * 查询订单申报数据总数
     */
	public Integer queryOrderDeclareCount(Map<String, Object> paramMap) {

		return orderDeclareMapper.queryOrderDeclareCount(paramMap);
	}
}
