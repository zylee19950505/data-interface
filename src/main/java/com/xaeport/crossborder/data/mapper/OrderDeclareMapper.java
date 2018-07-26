package com.xaeport.crossborder.data.mapper;


/*
 * 订单申报
 */

import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.OrderHeadAndList;
import com.xaeport.crossborder.data.provider.OrderDeclareSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDeclareMapper {

	/*
	 *
	  *  查询订单申报数据
	 */
	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderDeclareList")
	List<OrderHeadAndList> queryOrderDeclareList(Map<String, Object> paramMap);

	/*
	 * 查询订单申报数据总数
	 */
	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderDeclareCount")
	Integer queryOrderDeclareCount(Map<String, Object> paramMap);

	@UpdateProvider(type = OrderDeclareSQLProvider.class,method = "updateSubmitCustom")
	void updateSubmitCustom(Map<String, String> paramMap);

	/*
	* 根据状态查找订单
	* */
	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "findWaitGenerated")
	List<ImpOrderHead> findWaitGenerated(Map<String, String> paramMap);

	/*
	* 根据id查找标题信息
	* */
	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryOrderListByGuid")
	List<ImpOrderBody> queryOrderListByGuid(@Param("headGuid") String headGuid);


	/*
	* 修改申报状态
	* */
	@UpdateProvider(type = OrderDeclareSQLProvider.class,method = "updateEntryHeadOrderStatus")
	void updateEntryHeadOrderStatus(@Param("headGuid") String headGuid,@Param("ddysb") String ddysb);

	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryEntId")
	String queryEntId(@Param("crt_id") String crt_id);

	@SelectProvider(type = OrderDeclareSQLProvider.class,method = "queryCompany")
	BaseTransfer queryCompany(@Param("ent_id") String ent_id);
}
