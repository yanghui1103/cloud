package com.bw.fit.component.flow.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.entity.TFlowBranch;
import com.bw.fit.component.flow.entity.TNode2Dealer;
import com.bw.fit.component.flow.entity.TTodo;
import com.bw.fit.component.flow.model.Audit;
import com.bw.fit.system.common.model.RbackException;

/*****
 * 流程Plus组件
 * @author yangh
 *
 */
public interface FlowPlusService {

	/****
	 * 创建流程，并启动之；
	 * @param flowKey 流程定义名称
	 * @param vars    流转全局变量
	 * @return
	 */
	public ProcessInstance createNewFlowAndStart(String flowKey, Map<String, Object> vars);

	/*****
	 * 根据节点编码，查询处理人，及其协作关系
	 * @param nodeCode
	 * @param pdinst pdKey
	 * @return
	 */
	List<TNode2Dealer> getNodeDealersConf(String nodeCode, String pdinst);
	/*****
	 * 将该流程定义，所有事先已经维护好的节点处理人，
	 * 一次性赋值好。
	 * @param pdinst
	 * @param dealers
	 */
	public void makNodeAllDealersOfPdInst(String pdinst, Map<String, Object> dealers) ;
	/*****
	 * 一个id，对应多个流程实例；主要适用于一个流程过来，可能满足多个分支，然后采取的这种方式。
	 * @param tb
	 * @return
	 * @throws RbackException
	 */
	public JSONObject createOneForeignMultiFlow(TFlowBranch tb) throws RbackException;
	/*****
	 * 创建1，待办，2，待阅或者3，起草
	 * @param td
	 * @return
	 * @throws RbackException
	 */
	public JSONObject createTodo(TTodo td)  throws RbackException;
	/*****
	 * 创建起草
	 * @param td
	 * @return
	 * @throws RbackException
	 */
	public JSONObject createDraftTodo(TTodo td)  throws RbackException;
	/*****
	 * 完成待办/待阅
	 * @param td
	 * @return
	 * @throws RbackException
	 */
	public JSONObject completeTodo(TTodo td)  throws RbackException;
	/****
	 * 处理待办
	 * @param audit
	 * @return
	 * @throws RbackException
	 */
	public JSONObject audit(Audit audit)  throws RbackException;
	/*****
	 * DEL伊泰OA的待办
	 * @return
	 * @throws RbackException
	 */
	public JSONObject sendTodoForYt(String appname, String modelId, String modelname, String taskId)  throws RbackException;

	/*****
	 * 删除待办，也删除fit里的待办通知
	 * @param audit
	 * @return
	 * @throws RbackException
	 */
	public JSONObject deletePiPlus(Audit audit) throws RbackException ;
	/****
	 * 查詢专家主体还有操作的业务处于待审核的流程
	 * @param zjId
	 * @return
	 */
	public JSONObject getExisteFlows(String zjId);
	/****
	 * 流程上的节点安排处理人
	 * @param nd
	 * @return
	 * @throws RbackException
	 */
	public JSONObject createNodeDealer(TNode2Dealer nd) throws RbackException ;
}
