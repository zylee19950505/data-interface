package com.xaeport.crossborder.service.ordermanage;


import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.xaeport.crossborder.data.entity.BaseTransfer;
import com.xaeport.crossborder.data.entity.OrderHeadAndList;
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

	private Log logger = LogFactory.getLog(this.getClass());

	/*
     * 查询订单申报数据
     */
	public List<OrderHeadAndList> queryOrderDeclareList(Map<String, Object> paramMap) {
		return  orderDeclareMapper.queryOrderDeclareList(paramMap);
	}

	/*
     * 查询订单申报数据总数
     */
	public Integer queryOrderDeclareCount(Map<String, Object> paramMap) {

		return orderDeclareMapper.queryOrderDeclareCount(paramMap);
	}

	public boolean updateSubmitCustom(Map<String, String> paramMap) {
		boolean flag;
		try {
			orderDeclareMapper.updateSubmitCustom(paramMap);
			flag = true;
		} catch (Exception e) {
			flag = false;
			String exceptionMsg = String.format("处理订单单[orderNo: %s]时发生异常", paramMap.get("submitKeys"));
			logger.error(exceptionMsg, e);
		}
		return flag;
	}

	/*
	* 根据创建人id查找企业信息
	* */
	public BaseTransfer queryCompany(String crt_id) {
		//用户表里查找企业id
		String ent_id =  orderDeclareMapper.queryEntId(crt_id);
		//根据企业id查找企业信息
		BaseTransfer baseTransfer = orderDeclareMapper.queryCompany(ent_id);
		return baseTransfer;
	}
}
