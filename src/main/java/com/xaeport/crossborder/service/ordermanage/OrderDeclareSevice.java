package com.xaeport.crossborder.service.ordermanage;


import com.xaeport.crossborder.data.mapper.OrderDeclareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/*
* 邮件审核
*
* */
@Service
public class OrderDeclareSevice {

	@Autowired
	OrderDeclareMapper orderDeclareMapper;


	public List<Map<String,String>> queryOrderDeclareList(Map<String, Object> paramMap) {
		return  orderDeclareMapper.queryOrderDeclareList(paramMap);
	}

	public Integer queryOrderDeclareCount(Map<String, Object> paramMap) {

		return orderDeclareMapper.queryOrderDeclareCount(paramMap);
	}
}
