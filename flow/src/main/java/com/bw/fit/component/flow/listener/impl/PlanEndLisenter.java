package com.bw.fit.component.flow.listener.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bw.fit.plan.admin.dao.PlanDao;
import com.bw.fit.plan.admin.entity.TPlan;
@Service("planEndLisenter")
public class PlanEndLisenter implements ExecutionListener{

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private PlanDao planDao;
	
	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		String eventName = delegateExecution.getEventName();

		String formKey= (String)  delegateExecution.getVariable("formKey"  );
		String useType = (String)  delegateExecution.getVariable("useType"  );
        if ("start".equals(eventName)) {
        	if("kjpings".equals(useType)){
        		TPlan p = new TPlan();
        		p.setId(formKey);
        		p.setIsAudit("1");
        		planDao.updateAuditStatus(p);
        	}
        	
        }else if ("end".equals(eventName)) {
            System.out.println("end=========");
        } else if ("take".equals(eventName)) {//连线监听
            System.out.println("take=========");
        }
	}


}
