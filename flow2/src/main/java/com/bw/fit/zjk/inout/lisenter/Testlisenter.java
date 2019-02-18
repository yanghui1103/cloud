package com.bw.fit.zjk.inout.lisenter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-29 14:55
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component(value="Testlisenter")
public class Testlisenter {
    public void sysout(){
        System.out.println("sys out..............");
    }
    public void sysout2(  DelegateTask task){
        System.out.println("sys out2..............");
        task.addCandidateUser("");
        task.getProcessDefinitionId();
    }
}
