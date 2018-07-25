package com.xaeport.crossborder.data.entity;

import java.util.List;

public class Order {
	public ImpOrderHead impOrderHead;
	public List<ImpOrderBody> impOrderLists;

	//逻辑校验实体(预留)
	public Verify verify;

	public ImpOrderHead getImpOrderHead() {
		return impOrderHead;
	}

	public void setImpOrderHead(ImpOrderHead impOrderHead) {
		this.impOrderHead = impOrderHead;
	}

	public List<ImpOrderBody> getImpOrderLists() {
		return impOrderLists;
	}

	public void setImpOrderLists(List<ImpOrderBody> impOrderLists) {
		this.impOrderLists = impOrderLists;
	}

	public Verify getVerify() {
		return verify;
	}

	public void setVerify(Verify verify) {
		this.verify = verify;
	}
}
