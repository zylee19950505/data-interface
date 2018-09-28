package com.xaeport.crossborder.data.entity;

import java.util.List;

public class CEBManifestMessage {

	private ManifestHead manifestHead;
	private List<ManifestHead> manifestHeadList;
	private MessageHead messageHead;//报文中的messageHead节点

	public ManifestHead getManifestHead() {
		return manifestHead;
	}

	public void setManifestHead(ManifestHead manifestHead) {
		this.manifestHead = manifestHead;
	}

	public List<ManifestHead> getManifestHeadList() {
		return manifestHeadList;
	}

	public void setManifestHeadList(List<ManifestHead> manifestHeadList) {
		this.manifestHeadList = manifestHeadList;
	}

	public MessageHead getMessageHead() {
		return messageHead;
	}

	public void setMessageHead(MessageHead messageHead) {
		this.messageHead = messageHead;
	}
}
