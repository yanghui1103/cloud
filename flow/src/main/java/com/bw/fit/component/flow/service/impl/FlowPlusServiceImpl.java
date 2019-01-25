package com.bw.fit.component.flow.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.service.FlowCoreService;
import com.bw.fit.component.flow.service.FlowPlusService;
@Service
public class FlowPlusServiceImpl implements FlowPlusService {

	@Autowired
	private TaskService taskService ;
	@Autowired
	private RuntimeService runtimeService ;
	@Autowired
	private FlowCoreService flowCoreService;

}
