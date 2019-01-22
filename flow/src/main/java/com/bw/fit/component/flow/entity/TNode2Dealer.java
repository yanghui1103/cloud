package com.bw.fit.component.flow.entity;

import com.bw.fit.system.common.entity.BaseEntity;
/****
 * 流程节点对应的处理人
 * @author yangh
 *
 */
public class TNode2Dealer extends BaseEntity {

	private String pdinst;
	private String nodeCode ;
	private String dealer;
	private String typeCode;
	
	
	public String getPdinst() {
		return pdinst;
	}
	public void setPdinst(String pdinst) {
		this.pdinst = pdinst;
	}
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
