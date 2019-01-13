<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><jsp:include page="/common.jsp" /><%@ include
	file="/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/common/common.js"></script>
	<link href="<%=basePath%>common/fit/v4/static/lightblue/css/base.css" rel="stylesheet">
	<link href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/easyui.css"  rel="stylesheet" >
	<link href="<%=basePath%>common/fit/v4/static/lightblue/css/index.css"  rel="stylesheet" >
	<link href="<%=basePath%>common/fit/v4/static/lightblue/css/platform.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/icon.css">
	<link rel="stylesheet" href="<%=basePath%>common/fit/v4/static/lightblue/css/workbench.css">
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>common/fit/v4/static/lightblue/js/echarts-all.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/component/attachment/attachment.js"></script>
</head>
<body>
	<div class="easyui-tabs" style="width: 100%;">
		<div title="基本信息" data-options="closable:false" class="basic-info">
			<form>
				<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">计划单名称</td>
							<td class="kv-content"><span id="name1">${plan.name }</span></td>
							<td class="kv-label">项目名称</td>
							<td class="kv-content"><span id="name1">${plan.projName }</span></td>
						</tr>
						<tr>
							<td class="kv-label">项目编号</td>
							<td class="kv-content"><span id="name1">${plan.projCode }</span></td>
							<td class="kv-label">使用板块</td>
							<td class="kv-content"><span id="name1">
							<c:forEach items="${plan.plattes}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach>
							</span></td>
						</tr>
						<tr>
							<td class="kv-label">使用日期</td>
							<td class="kv-content"><span id="name1">${plan.useDate }</span></td>
							<td class="kv-label">使用地点</td>
							<td class="kv-content"><span id="name1">${plan.useAddress }</span></td>
						</tr>
						<tr>
							<td class="kv-label">A类专家人数</td>
							<td class="kv-content"><span id="name1">${plan.useAnum }</span></td>
							<td class="kv-label">B类专家人数</td>
							<td class="kv-content"><span id="name1">${plan.useBnum }</span></td>
						</tr>
						<tr>
							<td class="kv-label">使用类型</td>
							<td class="kv-content"><span id="name1">
							<c:forEach items="${plan.useTypes}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach></span></td>
							<td class="kv-label">抽取方式</td>
							<td class="kv-content"><span id="name1">
							<c:forEach items="${plan.ways}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach></span></td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
		
		<div title="流程信息" data-options="closable:false" class="basic-info">
			<div id="flowImage" style="width:100%;"></div>
			<table class="easyui-datagrid" tyle="width:100%;height:auto"
				title="流转历史列表">
				<thead>
					<tr>
						<th data-options="field:'code',width:'20%'">处理人</th>
						<th data-options="field:'code2',width:'30%'">处理意见</th>
						<th data-options="field:'codeq',width:'30%'">其他说明</th>
						<th data-options="field:'code3',width:'20%'">处理时间</th>
					</tr>
				</thead>
				<tbody>
						<c:forEach var="item" items="${deals}">
							<tr><td>${item.operator }</td>
							<td>${item.auditOpt }</td>
							<td>${item.remark }</td>
							<td>${item.versionTime }</td></tr>
					   </c:forEach>
				</tbody>
			</table>
		</div>
		
		<div title="审核操作" data-options="closable:false" class="basic-info">
		<div style="width:100%;">
			<form id="zjAuditFm">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">申请业务</td>
						<td class="kv-content"><span  >专家抽取计划单创建申请</span></td>
					</tr>
					<tr>
						<td class="kv-label">审核意见</td>
						<td class="kv-content">						
						<c:forEach var="item" items="${flowoperate}">
						<input type="radio" name="auditOpt" value="${item.dict_value }">${item.dict_name }</input>
						</c:forEach>
						</td>
						</tr>
						
					<tr>
						<td class="kv-label">其他说明</td>
						<td class="kv-content"><input class="easyui-textbox"
						name="remark" style="width: 70%; paddding-right: 2px" ></td>
					</tr>
				
					</tr>
				</tbody>
				</table>	
				<input id="aid" name="id" value='${foreignId }' type="hidden">
				<input id="formKey" name="formKey" value='${plan.id }' type="hidden">
				<input name="pdName" value='zjInOutFlow' type="hidden">
				<input name="taskId" id="taskId" value='${taskId }' type="hidden">
				
			</form>
			</br>
			<div style="float: right; margin-right: 30px;">
				<button class="easyui-linkbutton" type=button iconCls="icon-add"
					data-options="selected:true" onclick="auditOperate();"
					style="width: 150px">提交</button>
			</div>
		  </div>	
		</div>
		
		
	</div>
	<div id="_loadDialog_score_detailList"></div>
	<script type="text/javascript">
	$(function(){
		$("#flowImage").load("<%=basePath %>flow/openActivitiProccessImage/"+ $('#taskId').val() );
		
		
	});
	</script>
</body>
</html>