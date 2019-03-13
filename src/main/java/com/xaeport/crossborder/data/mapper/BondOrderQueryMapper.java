package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Verify;
import com.xaeport.crossborder.data.provider.BondOrderQuerySQLProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BondOrderQueryMapper {

	@Delete("DELETE FROM T_VERIFY_STATUS WHERE CB_HEAD_ID = #{guid}")
	int deleteVerifyStatus(String guid);

	//查询订单表头数据
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "queryOrderHeadList")
	List<ImpOrderHead> queryOrderHeadList(Map<String, String> paramMap);

	//查询订单表头数据总数
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "queryOrderHeadListCount")
	Integer queryOrderHeadListCount(Map<String, String> paramMap);

	//查询订单表体数据总数
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "queryOrderBodyList")
	List<ImpOrderBody> queryOrderBodyList(Map<String, String> paramMap);

	//查询订单表体数据总数
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "queryOrderBodyListCount")
	Integer queryOrderBodyListCount(Map<String, String> paramMap);

	//查询订单表头数据
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "queryOrderHead")
	ImpOrderHead queryOrderHead(Map<String, String> paramMap);

	//查询订单表体数据
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "queryOrderBody")
	List<ImpOrderBody> queryOrderBody(Map<String, String> paramMap);

	//查询清单表头详情
	@SelectProvider(type = BondOrderQuerySQLProvider.class, method = "queryVerifyDetail")
	Verify queryVerifyDetail(Map<String, String> paramMap);


	//修改订单表头数据（订单查询）
	@UpdateProvider(type = BondOrderQuerySQLProvider.class,method = "updateOrderHead")
	void updateOrderHead(LinkedHashMap<String, String> entryHead);

	//修改订单表体数据（订单查询）
	@UpdateProvider(type = BondOrderQuerySQLProvider.class,method = "updateOrderHeadByList")
	void updateOrderHeadByList(LinkedHashMap<String, String> entryHead);

	//修改订单表头数据（订单查询）
	@UpdateProvider(type = BondOrderQuerySQLProvider.class,method = "updateOrderList")
	void updateOrderList(LinkedHashMap<String, String> entryList);

	//修改订单表头数据（逻辑校验）
	@UpdateProvider(type = BondOrderQuerySQLProvider.class,method = "updateOrderHeadByLogic")
	void updateOrderHeadByLogic(LinkedHashMap<String, String> entryHead);

	//修改订单表体数据（逻辑校验）
	@UpdateProvider(type = BondOrderQuerySQLProvider.class,method = "updateOrderBodyByLogic")
	void updateOrderBodyByLogic(LinkedHashMap<String, String> entryHead);

	//查询订单回执详情（订单查询）
	@SelectProvider(type = BondOrderQuerySQLProvider.class,method = "returnOrderDetail")
	ImpOrderHead returnOrderDetail(Map<String, String> paramMap);
}
