package com.xaeport.crossborder.data.entity;


import java.util.List;

/*
*
* 订单报文节点
* */
public class CEB311Message {
	private List<ImpOrderHead> impOrderHeadList;
	private List<ImpOrderBody> impOrderBodyList;
	private List<OrderHead> orderHeadList;
	private List<OrderList> orderListList;
	private BaseTransfer baseTransfer;
	private Signature signature;

	public List<ImpOrderHead> getImpOrderHeadList() {
		return impOrderHeadList;
	}

	public void setImpOrderHeadList(List<ImpOrderHead> impOrderHeadList) {
		this.impOrderHeadList = impOrderHeadList;
	}

	public List<ImpOrderBody> getImpOrderBodyList() {
		return impOrderBodyList;
	}

	public void setImpOrderBodyList(List<ImpOrderBody> impOrderBodyList) {
		this.impOrderBodyList = impOrderBodyList;
	}

	public List<OrderHead> getOrderHeadList() {
		return orderHeadList;
	}

	public void setOrderHeadList(List<OrderHead> orderHeadList) {
		this.orderHeadList = orderHeadList;
	}

	public List<OrderList> getOrderListList() {
		return orderListList;
	}

	public void setOrderListList(List<OrderList> orderListList) {
		this.orderListList = orderListList;
	}

	public BaseTransfer getBaseTransfer() {
		return baseTransfer;
	}

	public void setBaseTransfer(BaseTransfer baseTransfer) {
		this.baseTransfer = baseTransfer;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
}
