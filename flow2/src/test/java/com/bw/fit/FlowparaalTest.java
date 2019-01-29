package com.bw.fit;

import com.bw.fit.component.flow.service.FlowCoreService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowparaalTest{

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Autowired
    private FlowCoreService flowCoreService;

    @Resource
    RepositoryService repositoryService;

    @Test
    public void processes() {
        Deployment deployment = repositoryService.createDeployment()
                .tenantId("tenantcm001")
                .addClasspathResource("processes/11.bpmn").deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println(processDefinition.getId());
    }

    @Test
    public void start(){
        Map<String,Object> vars = new HashMap<>();
        vars.put("dealers", "staff,staff007");
        //启动流程定义，返回流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceById("myProcess_11:1:140008",vars);
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID："+processId);
    }
}
