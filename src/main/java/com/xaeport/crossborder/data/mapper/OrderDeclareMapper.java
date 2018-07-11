package com.xaeport.crossborder.data.mapper;


/*
 * 订单申报
 */

import com.xaeport.crossborder.data.provider.OrderDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDeclareMapper {

	/*
	 * 查询订单申报数据
	 */
	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderDeclareList")
	List<Map<String,String>> queryOrderDeclareList(Map<String, Object> paramMap);

	/*
	 * 查询订单申报数据总数
	 */
	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderDeclareCount")
	Integer queryOrderDeclareCount(Map<String, Object> paramMap);


}
