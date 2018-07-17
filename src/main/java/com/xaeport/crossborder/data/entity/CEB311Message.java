package com.xaeport.crossborder.data.entity;


import java.util.List;

/*
*
* 订单报文节点
* */
public class CEB311Message {
	private ImpOrderHead orderHead;
	private List<ImpOrderBody> orderBodyList;
	private BaseTransfer baseTransfer;
	private Signature signature;

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public ImpOrderHead getOrderHead() {
		return orderHead;
	}

	public void setOrderHead(ImpOrderHead orderHead) {
		this.orderHead = orderHead;
	}

	public List<ImpOrderBody> getOrderBodyList() {
		return orderBodyList;
	}

	public void setOrderBodyList(List<ImpOrderBody> orderBodyList) {
		this.orderBodyList = orderBodyList;
	}

	public BaseTransfer getBaseTransfer() {
		return baseTransfer;
	}

	public void setBaseTransfer(BaseTransfer baseTransfer) {
		this.baseTransfer = baseTransfer;
	}
}
