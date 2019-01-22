package com.bw.fit.component.flow.listener.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bw.fit.later.bounty.dao.BountyDao;
import com.bw.fit.plan.admin.dao.PlanDao;
import com.bw.fit.plan.admin.entity.TPlan;
import com.bw.fit.system.common.util.PubFun;
@Service("bountyEndLisenter")
public class BountyEndLisenter implements ExecutionListener{

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private PlanDao planDao;
	@Autowired
	private BountyDao bountyDao;
	
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		String eventName = delegateExecution.getEventName();

		String formKey = (String)  delegateExecution.getVariable("formKey"  );
		String auditCode = (String)  delegateExecution.getVariable("auditCode"  );
        if ("start".equals(eventName)) {
        	String planId = formKey;
        	TPlan tp = new TPlan();
        	tp.setId(planId);
        	tp.setIsBounty(auditCode);
        	PubFun.fillCommonProptities(tp, false, PubFun.getCurrentSession());
        	bountyDao.bountyPlan(tp);
        }else if ("end".equals(eventName)) {
            System.out.println("end=========");
        } else if ("take".equals(eventName)) {//连线监听
            System.out.println("take=========");
        }
	}


}
