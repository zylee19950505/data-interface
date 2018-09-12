package com.xaeport.crossborder.service.ordermanage;

import com.xaeport.crossborder.data.entity.ImpOrderBody;
import com.xaeport.crossborder.data.entity.ImpOrderHead;
import com.xaeport.crossborder.data.entity.Order;
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
	@Transactional
	public Map<String,String> saveOrderDetail(LinkedHashMap<String, String> entryHead, ArrayList<LinkedHashMap<String, String>> entryLists) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		if (saveOrderDetail(entryHead, entryLists, rtnMap,"订单查询-编辑-重报")) return rtnMap;

		rtnMap.put("result", "true");
		rtnMap.put("msg", "编辑信息成功，请到“订单查询-订单申报”处重新进行订单申报！");
		return rtnMap;

	}
	public boolean saveOrderDetail(LinkedHashMap<String, String> entryHead,
								   List<LinkedHashMap<String, String>> entryLists,
								   Map<String, String> rtnMap,String notes){

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
		}
		/*// 修改entry_head的 操作状态SWOP3 和 申报状态（置空）、申报结果（置空）
		this.entryMapper.updateEntryHeadStatus(StatusCode.BDSB, "", "", entryHeadId);
		// 插入状态改变记录表
		StatusRecord statusRecord = new StatusRecord();
		statusRecord.setSr_id(IdUtils.getUUId());
		statusRecord.setCreate_time(new Date());
		statusRecord.setOdd_no(entryHeadId);
		statusRecord.setNotes(notes);
		statusRecord.setBelong("Entryhead");
		statusRecord.setStatus_code(StatusCode.BDSB);
		this.entryMapper.insertStatusRecord(statusRecord);*/
		return false;
	}

	public ImpOrderHead returnOrderDetail(Map<String, String> paramMap) {
		return orderQueryMapper.returnOrderDetail(paramMap);
	}
}
