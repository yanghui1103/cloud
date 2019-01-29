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
public class Flow2ApplicationTests {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Autowired
    private FlowCoreService flowCoreService;

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
                .addClasspathResource("processes/test1.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println(processDefinition.getId());
    }

    @Test
    public void start(){

        Map<String,Object> vars = new HashMap<>();
        vars.put("dealers", "staff");

        //启动流程定义，返回流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceById("myProcess_2:2:17514",vars);
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID："+processId);


    }

    @Test
    public void currentTask(){
        String processId ="42501";
        List<Task> tasks=taskService.createTaskQuery().processInstanceId(processId).list();
        for(Task task:tasks){
            System.out.println("当前任务名称："+task.getName());
            System.out.println("当前任务taskId："+task.getId());
            System.out.println("当前任务taskKey："+task.getTaskDefinitionKey());
        }
    }

    @Test
    public void complete(){
        String processId ="42501";
        List<Task> tasks=taskService.createTaskQuery().processInstanceId(processId).list();
        for(Task task:tasks){
            System.out.println("执行前，任务名称："+task.getName());
            taskService.complete(task.getId());
        }
    }

    @Test
    public void completeCurrentTask(){
        String processId ="42501";
        Map<String,Object> vars = new HashMap<>();
        vars.put("dealers1", "staff001,staff011");
        List<Task> tasks=taskService.createTaskQuery().processInstanceId(processId).taskCandidateUser("staff").list();
        for(Task task:tasks){
            flowCoreService.cliamTaskToUser(task.getId(),"staff");
            taskService.complete(task.getId(),vars);
        }
    }

    @Test
    public void cliamTask(){
        String processId ="42501";
        Map<String,Object> vars = new HashMap<>();
        vars.put("dealers2", "staff002,staff012");
        List<Task> tasks=taskService.createTaskQuery().processInstanceId(processId).taskCandidateUser("staff001").list();
        System.out.println(tasks.size());
        for(Task task:tasks){
            flowCoreService.cliamTaskToUser(task.getId(),"staff001");
            flowCoreService.createTaskAssignee(task.getId(),"staff007");
//            taskService.complete(task.getId());
        }

        // 到转办人名下
        Task t =taskService.createTaskQuery().processInstanceId(processId).taskAssignee("staff007").singleResult();
        taskService.complete(t.getId(),vars);


    }

    @Test
    public void rollbackCuurent(){
        try {
            flowCoreService.rollBackProcess("805013");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rollback(){
        try {
            flowCoreService.rollBack("42501","_3","msg");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
