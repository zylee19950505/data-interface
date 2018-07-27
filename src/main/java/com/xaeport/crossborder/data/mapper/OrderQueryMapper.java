package com.xaeport.crossborder.data.mapper;


import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.provider.OrderQuerySQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderQueryMapper {

	@SelectProvider(type = OrderQuerySQLProvider.class,method = "queryOrderHeadList")
	List<ImpOrderHead> queryOrderHeadList(Map<String, String> paramMap);

	@SelectProvider(type = OrderQuerySQLProvider.class,method = "queryOrderHeadListCount")
	Integer queryOrderHeadListCount(Map<String, String> paramMap);

	@SelectProvider(type = OrderQuerySQLProvider.class,method = "queryOrderBodyList")
	List<ImpOrderBody> queryOrderBodyList(Map<String, String> paramMap);

	@SelectProvider(type = OrderQuerySQLProvider.class,method = "queryOrderBodyListCount")
	Integer queryOrderBodyListCount(Map<String, String> paramMap);

/*点击查看邮件详情
* */
	@SelectProvider(type = OrderQuerySQLProvider.class,method = "queryOrderHead")
	ImpOrderHead queryOrderHead(Map<String, String> paramMap);

	@SelectProvider(type = OrderQuerySQLProvider.class,method = "queryOrderBody")
	List<ImpOrderBody> queryOrderBody(Map<String, String> paramMap);
}
