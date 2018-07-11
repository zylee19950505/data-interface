package com.xaeport.crossborder.data.mapper;


/*
* 邮件申报
* */

import com.xaeport.crossborder.data.provider.OrderDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDeclareMapper {


	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderDeclareList")
	List<Map<String,String>> queryOrderDeclareList(Map<String, Object> paramMap);

	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderDeclareCount")
	Integer queryOrderDeclareCount(Map<String, Object> paramMap);
}
