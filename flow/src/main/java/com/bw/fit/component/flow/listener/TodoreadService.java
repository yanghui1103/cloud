package com.bw.fit.component.flow.listener;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;

public interface TodoreadService {
	/*****
	 * 推送待办待阅到OA
	 * @return
	 * @throws RbackException
	 */
	public JSONObject sendTodoOA() throws RbackException;
	/*****
	 * 置为OA待办/待阅为已经办理或已经阅读
	 * @return
	 * @throws RbackException
	 */
	public JSONObject sendTodoneOA() throws RbackException ;
	/*****
	 * 删除oa中的待办
	 * @return
	 * @throws RbackException
	 */
	public JSONObject DeleteTodoOA() throws RbackException ;
}
