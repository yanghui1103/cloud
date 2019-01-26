package com.bw.fit;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Flow2ApplicationTests {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    @Test
    public void contextLoads() {

    }

    @Resource
    RepositoryService repositoryService;

    @Test
    public void processes() {
        Deployment deployment = repositoryService.createDeployment()
                .name("qjlc111")
                .tenantId("tenantcm001")
                .addClasspathResource("processes/test2.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println(processDefinition.getId());
    }

    @Test
    public void start(){
        //启动流程定义，返回流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceById("myProcess_2:1:747504");
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID："+processId);


    }

    @Test
    public void complete(){
        String processId ="760001";
        List<Task> tasks=taskService.createTaskQuery().processInstanceId(processId).list();
        for(Task task:tasks){
            System.out.println("执行前，任务名称："+task.getName());
            taskService.complete(task.getId());
        }
    }
}
