package com.bw.fit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.FlowHandleWays;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.service.FlowCoreService;
import com.bw.fit.component.form.entity.TForm;
import com.bw.fit.component.form.mapper.FormMapper;
import com.bw.fit.component.form.service.FormPlusService;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
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
    @Autowired
    private FormPlusService formPlusService;
    @Resource
    FormMapper formMapper;
    @Resource
    RepositoryService repositoryService;
    @Resource
    FlowHandleWays flowHandleWays;

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

    @Test
    public void pushForm() throws RbackException {
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("formKey","001");
        json.put("htmlName","personName");
        json.put("name","姓名");
        json.put("attr","张三");
        json.put("attrType","string");
        json.put("tabName","t1");
        json.put("tabOrder","1");

        array.add(json);

        json = new JSONObject();
        json.put("formKey","001");
        json.put("htmlName","sex");
        json.put("name","性别");
        json.put("attr","男");
        json.put("attrType","string");
        json.put("tabName","t1");
        json.put("tabOrder","2");

        array.add(json);

        json = new JSONObject();
        json.put("formKey","001");
        json.put("htmlName","list1");
        json.put("name","");
        json.put("attr",array.toJSONString());
        json.put("attrType","list");
        json.put("tabName","t2");
        json.put("tabOrder","3");

        array.add(json);

        JSONObject jsonObject = formPlusService.insert(array,"qq1");
        System.out.println("res:"+jsonObject.toJSONString());
    }

    @Test
    public void get(){
        List<TForm> tFormList = formMapper.getFormInfo("001");
    }

    @Test
    public void gettest(){
        System.out.println(flowHandleWays.getListMap().get(1).keySet());
    }

}
