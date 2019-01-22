package com.bw.fit.component.flow.listener.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.plan.admin.dao.PlanDao;
import com.bw.fit.plan.admin.entity.TPlan;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.zj.admin.dao.ZjDao;
import com.bw.fit.zj.admin.entity.TZj;
import com.bw.fit.zj.admin.service.ZjService;

@Service(value = "zjTaskEndLisenter")
public class ZjTaskEndLisenter implements ExecutionListener {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ZjService zjService;
	@Autowired
	private ZjDao zjDao;

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		String eventName = delegateExecution.getEventName();
		String formKey = (String) delegateExecution.getVariable("formKey");
		String coType = (String) delegateExecution.getVariable("coType");

		if ("start".equals(eventName)) {
			TZj t = zjDao.getCo(formKey);
			TZj tg = zjDao.getCo(formKey);
			if (t != null && (coType.equals("registerzj"))) {
				/****
				 * （注册 ）归档的话
				 */
				TZj tzj = new TZj();
				tzj.setId(formKey);
				tzj.setTemp_str1(PubFun.getUUID());
				JSONObject json = zjService.registerGuidang(tzj);
				System.out.println("guidang:" + json.get("res"));
			} else if (t != null && coType.equals("editzj")) {
				/****
				 * （编辑）归档的话
				 */
				TZj tzj = new TZj();
				tzj = zjDao.getCo(formKey);
				JSONObject json = zjService.EditGuidang(tzj);
				System.out.println("guidang:" + json.get("res"));
			} else if (tg != null && coType.equals("unshield")) {
				TZj tzj = new TZj();
				tzj.setId(formKey);
				tzj.setTemp_str1(PubFun.getUUID());
				JSONObject json = zjService.unShieldGuidang(tzj);
				System.out.println("guidang:" + json.get("res"));
			} else if (tg != null && coType.equals("shield")) {
				TZj tzj = new TZj();
				tzj.setId(formKey);
				tzj.setTemp_str1(PubFun.getUUID());
				JSONObject json = zjService.shieldGuidang(tzj);
				System.out.println("guidang:" + json.get("res"));
			} else if (tg != null && coType.equals("exitzj")) {
				TZj tzj = new TZj();
				tzj.setId(formKey);
				tzj.setTemp_str1(PubFun.getUUID());
				JSONObject json = zjService.exitGuidang(tzj);
				System.out.println("guidang:" + json.get("res"));
			} else {
				return;
			}
		}
	}

}
