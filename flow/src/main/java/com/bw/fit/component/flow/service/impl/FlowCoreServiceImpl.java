package com.bw.fit.component.flow.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
 












import com.bw.fit.component.flow.model.RejectTaskCMD;
import com.bw.fit.component.flow.service.FlowCoreService;

@Service
public class FlowCoreServiceImpl implements FlowCoreService {
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService; 

	@Override
	public void rollBack(String procInstId, String destTaskKey, String messageContent) {
		// TODO Auto-generated method stub
		  //获得当前任务的对应实列
		  
		  Task taskEntity =  taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
		  //当前任务key
		  String taskDefKey = taskEntity.getTaskDefinitionKey();
		  //获得当前流程的定义模型
		  
		  ProcessDefinitionEntity  processDefinition  =(ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService)
		      .getDeployedProcessDefinition(taskEntity.getProcessDefinitionId()); 
		  
		  //获得当前流程定义模型的所有任务节点
		  
		  List<ActivityImpl> activitilist = processDefinition.getActivities();
		  //获得当前活动节点和驳回的目标节点"draft"
		  ActivityImpl currActiviti = null;//当前活动节点
		  ActivityImpl destActiviti = null;//驳回目标节点
		  int sign = 0;
		  for(ActivityImpl activityImpl : activitilist){
		   //确定当前活动activiti节点
		   
		   if(taskDefKey.equals(activityImpl.getId())){
		    currActiviti = activityImpl;
		    
		    sign++;
		   }else if(destTaskKey.equals(activityImpl.getId())){
		    destActiviti = activityImpl;
		    
		    sign++;
		   }
		   //System.out.println("//-->activityImpl.getId():"+activityImpl.getId());
		   if(sign==2){
		    break;//如果两个节点都获得,退出跳出循环
		   }
		  }
		  System.out.println("//-->currActiviti activityImpl.getId():"+currActiviti.getId());
		  System.out.println("//-->destActiviti activityImpl.getId():"+destActiviti.getId());
		  //保存当前活动节点的流程想参数
		  
		  List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(0);
		  
		  for(PvmTransition pvmTransition:currActiviti.getOutgoingTransitions()){
		   hisPvmTransitionList.add(pvmTransition);
		  }
		  //清空当前活动几点的所有流出项
		  
		  currActiviti.getOutgoingTransitions().clear();
		  System.out.println("//-->currActiviti.getOutgoingTransitions().clear():"+currActiviti.getOutgoingTransitions().size());
		  //为当前节点动态创建新的流出项
		  
		  TransitionImpl newTransitionImpl = currActiviti.createOutgoingTransition();
		  //为当前活动节点新的流出目标指定流程目标
		  newTransitionImpl.setDestination(destActiviti);
		  //保存驳回意见
		  
		  taskEntity.setDescription(messageContent);//设置驳回意见
		  taskService.saveTask(taskEntity);
		  //设定驳回标志  
		  
		  Map<String, Object> variables = new java.util.HashMap<String, Object>(0);
		  variables.put("messageContent", messageContent);
		  //执行当前任务驳回到目标任务draft		  
		  taskService.complete(taskEntity.getId(), variables);
		  
		  //清除目标节点的新流入项
		  
		  destActiviti.getIncomingTransitions().remove(newTransitionImpl);
		  //清除原活动节点的临时流程项
		  
		  currActiviti.getOutgoingTransitions().clear();
		  //还原原活动节点流出项参数
		  
		  currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);

	}

	@Override
	public void suspendProcessDefinitionById(String defId) {
		// TODO Auto-generated method stub
		repositoryService.suspendProcessDefinitionById(defId);
	}

	@Override
	public void suspendProcessDefinitionByKey(String defKey) {
		// TODO Auto-generated method stub
		repositoryService.suspendProcessDefinitionByKey(defKey);
	}

	@Override
	public void activateProcessDefinitionById(String defId) {
		// TODO Auto-generated method stub
		repositoryService.activateProcessDefinitionById(defId);
	}

	@Override
	public void activateProcessDefinitionByKey(String defKey) {
		// TODO Auto-generated method stub
		repositoryService.activateProcessDefinitionByKey(defKey);
	}

	@Override
	public void addCandidateStarterGroup(String defId, String groupId) {
		// TODO Auto-generated method stub
		repositoryService.addCandidateStarterGroup(defId, groupId);
	}

	@Override
	public void addCandidateStarterUser(String defId, String userId) {
		// TODO Auto-generated method stub
		repositoryService.addCandidateStarterUser(defId, userId);
	}

	@Override
	public List<ProcessDefinition> getCanStartableByUser(String userId) {
		// TODO Auto-generated method stub
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery().startableByUser(userId).list();
		return list;
	}

	@Override
	public BufferedImage getProcessDiagramByDefId(String defId)
			throws Exception {
		// TODO Auto-generated method stub
		InputStream is = repositoryService.getProcessDiagram(defId);
		BufferedImage image = ImageIO.read(is);
		return image;
	}

	@Override
	public void deleteDeploymentCasCade(String defId, boolean b)
			throws Exception {
		// TODO Auto-generated method stub
		repositoryService.deleteDeployment(defId, b);
	}

	@Override
	public void createTask() {
		// TODO Auto-generated method stub
		taskService.newTask();
	}

	@Override
	public void createTask(String taskId) {
		// TODO Auto-generated method stub
		taskService.newTask(taskId); // 必须保证这个id不存在，否则会主键冲突
	}

	@Override
	public void deleteTaskCascade(String taskId, boolean b) {
		// TODO Auto-generated method stub
		taskService.deleteTask(taskId, b);
	}

	@Override
	public void deleteTaskCascade(Collection<String> taskIds, boolean cascade) {
		// TODO Auto-generated method stub
		taskService.deleteTasks(taskIds, cascade);
	}

	@Override
	public void createTaskGroupRelation(String taskId, String groupId) {
		// TODO Auto-generated method stub
		taskService.addCandidateGroup(taskId, groupId);
	}

	@Override
	public void createTaskUserRelation(String taskId, String userId) {
		// TODO Auto-generated method stub
		taskService.addCandidateUser(taskId, userId);
	}

	@Override
	public List<Task> getTasksOfTheGroup(String groupId) {
		// TODO Auto-generated method stub
		List<Task> list = taskService.createTaskQuery()
				.taskCandidateGroup(groupId).list();
		return list;
	}

	@Override
	public List<Task> getTasksOfTheUser(String userId) {
		// TODO Auto-generated method stub
		List<Task> list = taskService.createTaskQuery()
				.taskCandidateUser(userId).list();
		return list;
	}

	@Override
	public List<Task> getTasksOfTheAssginee(String userId) {
		List<Task> list = taskService.createTaskQuery()
                .taskAssignee(userId).list();
		return list;
	}

	@Override
	public void createTaskOwner(String taskId, String userId) {
		// TODO Auto-generated method stub
		taskService.setOwner(taskId, userId);
	}

	@Override
	public void deleteGroupTaskRelation(String taskId, String groupId) {
		// TODO Auto-generated method stub
		taskService.deleteCandidateGroup(taskId, groupId);
	}

	@Override
	public void createTaskAssignee(String taskId, String userId) {
		// TODO Auto-generated method stub
		taskService.setAssignee(taskId, userId);
	}

	@Override
	public void deleteUserTaskRelation(String taskId, String userId, String type) {
		// TODO Auto-generated method stub
		/**
		 * IdentityLinkType.ASSIGNEE或OWNER 将会act_ru_task 表中owner ,assignee 都会置空
		 * IdentityLinkType.CANDIDATE 则是删除用户权限数据，只是把act_ru_identitylink那条记录删除
		 * **/
		taskService.deleteUserIdentityLink(taskId, userId, type);
		taskService.setVariable(taskId, userId, type);
	}

	@Override
	public void createAttachment(String taskId, String processinstanceId,
			String name, String descp, String type, String url) {
		// TODO Auto-generated method stub
		taskService.createAttachment(type, taskId, processinstanceId, name,
				descp, url);
	}

	@Override
	public void createAttachment(String taskId, String processinstanceId,
			String name, String descp, String type, String url, InputStream is) {
		// TODO Auto-generated method stub
		taskService.createAttachment(type, taskId, processinstanceId, name,
				descp, is);
	}

	@Override
	public void deleteAttachment(String attachmentId) {
		// TODO Auto-generated method stub
		taskService.deleteAttachment(attachmentId);
	}

	@Override
	public List<Attachment> getAttachmentsOfProccesInstance(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return taskService.getProcessInstanceAttachments(processInstanceId);
	}

	@Override
	public List<Attachment> getAttachmentsOfTheTask(String taskId) {
		// TODO Auto-generated method stub
		return taskService.getTaskAttachments(taskId);
	}

	@Override
	public void createTaskComment(String taskId, String processInstanceId,
			String message) {
		// TODO Auto-generated method stub 
		taskService.addComment(taskId, processInstanceId, message);
	}

	@Override
	public void createTaskComment(Task t,String message) {
		// TODO Auto-generated method stub 
		taskService.addComment(t.getId(), t.getProcessInstanceId(), message);
	}
	@Override
	public List<Comment> getCommentOfTheTask(String taskId) {
		// TODO Auto-generated method stub
		return taskService.getTaskComments(taskId);
	}

	@Override
	public List<Comment> getCommentOfProcessInstance(String instanceId) {
		// TODO Auto-generated method stub
		return taskService.getProcessInstanceComments(instanceId);
	}


	@Override
	public void completeTask(Task task, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		if(isOwnerAssigneeSameUser(task)){
			// 说明自己办理自己的任务
			taskService.complete(task.getId(), vars);
		}else{
			// 办理的是委托来的任务
			taskService.resolveTask(task.getId(), vars);
		}
		/** 将vars里这些参数传入下一个环节，且可以利用这个决定流程走向 **/
	}

	
	@Override
	public void completeTask(String taskId) {
		taskService.complete(taskId);
	}

	@Override
	public void claim(String taskId, String accountId) {
		taskService.claim(taskId, accountId);
	}

	@Override
	public void startProcessByPdId(String processDefiniedId) {
		// TODO Auto-generated method stub
		runtimeService.startProcessInstanceById(processDefiniedId);
	}

	@Override
	public void startProcessByPdId(String processDefiniedId,
			Map<String, Object> vars) {
		// TODO Auto-generated method stub
		runtimeService.startProcessInstanceById(processDefiniedId, vars);
	}

	@Override
	public void startProcessByPdId(String processDefiniedId,
			String bussiness_key) {
		// TODO Auto-generated method stub
		runtimeService.startProcessInstanceById(processDefiniedId,
				bussiness_key);
	}

	@Override
	public void startProcessByPdId(String processDefiniedId,
			String bussiness_key, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		runtimeService.startProcessInstanceById(processDefiniedId,
				bussiness_key, vars);
	}

	@Override
	public void signalProcess(String exeId) {
		// TODO Auto-generated method stub
		runtimeService.signal(exeId);
	}

	@Override
	public void signalProcess(String exeId, Map<String, Object> vars) {
		// TODO Auto-generated method stub
		runtimeService.signal(exeId, vars);
	}

	@Override
	public void suspendProcessInstanceByPiId(String processInstanceId) {
		// TODO Auto-generated method stub
		runtimeService.suspendProcessInstanceById(processInstanceId);
	}

	@Override
	public void activateProcessInstanceByPiId(String processInstanceId) {
		// TODO Auto-generated method stub
		runtimeService.activateProcessInstanceById(processInstanceId);
	}

	@Override
	public boolean isProcessSuspend(String processInstanceId) {
		// TODO Auto-generated method stub
		Execution exe = runtimeService.createExecutionQuery()
				.processInstanceId(processInstanceId).singleResult();

		return exe.isSuspended();
	}

	@Override
	public boolean isProcessEnd(String processInstanceId) {
		// TODO Auto-generated method stub
		Execution exe = runtimeService.createExecutionQuery()
				.processInstanceId(processInstanceId).singleResult();

		return exe.isEnded();
	}

	@Override
	public void deleteProcessInstance(String piid, String reason) {
		// TODO Auto-generated method stub
		runtimeService.deleteProcessInstance(piid, reason);
	}

	@Override
	public void rollBackProcess(String currentTaskId) throws Exception {
		/*** 驳回 ****/
		try {
			Map<String, Object> variables;
			// 取得当前任务
			HistoricTaskInstance currTask = historyService
					.createHistoricTaskInstanceQuery().taskId(currentTaskId)
					.singleResult();
			// 取得流程实例
			ProcessInstance instance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(currTask.getProcessInstanceId())
					.singleResult();
			if (instance == null) {
				// 流程结束
			}
			variables = instance.getProcessVariables();
			// 取得流程定义
			ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (processEngine
					.getRepositoryService().getProcessDefinition(currTask
					.getProcessDefinitionId()));

			if (definition == null) {
				// log.error("流程定义未找到");
				return;
			}
			// 取得上一步活动
			ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
					.findActivity(currTask.getTaskDefinitionKey());
			List<PvmTransition> nextTransitionList = currActivity
					.getIncomingTransitions();
			// 清除当前活动的出口
			List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
			List<PvmTransition> pvmTransitionList = currActivity
					.getOutgoingTransitions();
			for (PvmTransition pvmTransition : pvmTransitionList) {
				oriPvmTransitionList.add(pvmTransition);
			}
			pvmTransitionList.clear();

			// 建立新出口
			List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
			for (PvmTransition nextTransition : nextTransitionList) {
				PvmActivity nextActivity = nextTransition.getSource();
				ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
						.findActivity(nextActivity.getId());
				TransitionImpl newTransition = currActivity
						.createOutgoingTransition();
				newTransition.setDestination(nextActivityImpl);
				newTransitions.add(newTransition);
			}
			// 完成任务
			List<Task> tasks = taskService.createTaskQuery()
					.processInstanceId(instance.getId())
					.taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
			for (Task task : tasks) {
				taskService.complete(task.getId(), variables);
				historyService.deleteHistoricTaskInstance(task.getId());
			}
			// 恢复方向
			for (TransitionImpl transitionImpl : newTransitions) {
				currActivity.getOutgoingTransitions().remove(transitionImpl);
			}
			for (PvmTransition pvmTransition : oriPvmTransitionList) {
				pvmTransitionList.add(pvmTransition);
			}

			return;
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public boolean isJonitTaskCompleted(ActivityExecution execution) {
		return false;
	}

	@Override
	public List<HistoricIdentityLink> getDealersOfTheTask(String taskId) {
		// TODO Auto-generated method stub
		List<HistoricIdentityLink> list = historyService.getHistoricIdentityLinksForTask(taskId);//taskService.getIdentityLinksForTask(taskId);
		
		return list;
	}

	@Override
	public boolean getMultiInstanceTask(String taskId) {
		// TODO Auto-generated method stub
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String excId = task.getExecutionId();  
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();  
		int s = Integer.valueOf(execution.getVariable("nrOfInstances").toString());
		if(s >1){
			return true ; // 多例任务
		}
		return false;
	}

	@Override
	public List<Task> getCurrentTasksOfUser(String userId) {
		// TODO Auto-generated method stub
		List<Task> all = new ArrayList<>();
		List<Task> aginneTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		List<Task> candidateTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		all.addAll(aginneTasks);
		all.addAll(candidateTasks); 
//		historyService.createHistoricProcessInstanceQuery().startedBy(userId).list();
		return all ;
	}

	@Override
	public List<HistoricTaskInstance> getUserhistoryTaskInstance(String userId,boolean finished) {
		// TODO Auto-generated method stub
		if(finished){
			List<HistoricTaskInstance> list=processEngine.getHistoryService() // 历史任务Service  
		            .createHistoricTaskInstanceQuery() // 创建历史任务实例查询  
		            .taskAssignee(userId) // 指定办理人  
		            .finished() // 查询已经完成的任务    
		            .list(); 
			return list ;
		}else{
			List<HistoricTaskInstance> list=processEngine.getHistoryService() // 历史任务Service  
		            .createHistoricTaskInstanceQuery() // 创建历史任务实例查询  
		            .taskAssignee(userId) // 指定办理人   
		            .list(); 
			return list ;
		} 
	}

	@Override
	public List<HistoricTaskInstance> getHistoryTaskInstance(
			String processInstanceId) {
		// TODO Auto-generated method stub
		List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的Service  
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询  
                .processInstanceId(processInstanceId)//  
                .orderByTaskCreateTime().asc()  
                .list();  
		return list ;		
	}


	@Override
	public Deployment deployFlowResource(String resourcePath) {
		// TODO Auto-generated method stub
		Deployment d = repositoryService.createDeployment()
		.addClasspathResource(resourcePath).deploy();
		return d;
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String key,
			Map<String, Object> vars) {
		// TODO Auto-generated method stub
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, vars);
		return pi;
	}

	@Override
	public void cliamTaskToUser(String taskId, String userId) {
		// TODO Auto-generated method stub
		taskService.claim(taskId, userId);
	}

	@Override
	public void delegateTask(String taskId, String userId) {
		// TODO Auto-generated method stub
		taskService.delegateTask(taskId, userId);
	}

	@Override
	public void resolveTask(String taskId) {
		// TODO Auto-generated method stub
		taskService.resolveTask(taskId);
	}
	@Override
	public void resolveTask(String taskId,Map<String,Object> vars) {
		// TODO Auto-generated method stub
		taskService.resolveTask(taskId,vars);
	}

	@Override
	public boolean isOwnerAssigneeSameUser(Task task) {
		// TODO Auto-generated method stub
		return task.getOwner().equals(task.getAssignee());
	}

	@Override
	public String getNextNode(String procInstanceId) {
		// 1、首先是根据流程ID获取当前任务：
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(procInstanceId).list();
        String nextId = "";
        for (Task task : tasks) {
            RepositoryService rs = processEngine.getRepositoryService();
            // 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
            ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)
                    .getDeployedProcessDefinition(task.getProcessDefinitionId());
            List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
            // 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
            String excId = task.getExecutionId();
            RuntimeService runtimeService = processEngine.getRuntimeService();
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                    .singleResult();
            String activitiId = execution.getActivityId();
            // 4、然后循环activitiList
            // 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
            for (ActivityImpl activityImpl : activitiList) {
                String id = activityImpl.getId();
                if (activitiId.equals(id)) {
                    List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
                    for (PvmTransition tr : outTransitions) {
                        PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
                        nextId = ac.getId();
                    }
                    break;
                }
            }
        }
        return nextId;
	}

	@Override
	public TaskDefinition getNextTaskInfo(String processInstanceId)
			throws Exception {
//		ProcessDefinitionEntity processDefinitionEntity = null;
//		String id = null;
//		TaskDefinition task = null;
//		//获取流程发布Id信息 
//		String definitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId(); 
//		processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(definitionId); 
//		List<Task> taskQuery = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
//		//当前流程节点Id信息  
//		String activitiId = taskQuery.get(0).getTaskDefinitionKey(); 
//		List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();
//
//for(ActivityImpl activityImpl : activitiList){
//	id = activityImpl.getId();
//if (activitiId.equals(id)) {
//
//			task = nextTaskDefinition(activityImpl, activityImpl.getId(), processInstanceId);
//
//		                break;
//
//		         }}return task;
		return null ;
	}
	 
	
	
}
