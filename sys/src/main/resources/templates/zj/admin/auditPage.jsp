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
		<div title="专家信息" data-options="closable:false" class="basic-info">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">名称</td>
						<td class="kv-content"><span id="name1">${zj.name }</span></td>
						<td class="kv-label">性别</td>
						<td class="kv-content"><span id="code">${zj.gender }</span></td>
						<td class="kv-label">身份证号码</td>
						<td class="kv-content"><span id="simpleName">${zj.cardId }</span></td>
					</tr>
					<tr>
						<td class="kv-label">联系电话</td>
						<td class="kv-content"><span id="type">${zj.phone }</span></td>
						<td class="kv-label">Email</td>
						<td class="kv-content"><span id="isVisible">${zj.email }</span></td>
						<td class="kv-label">第一学历</td>
						<td class="kv-content"><span id="adminer">${zj.firstEdu }</span></td>
					</tr>
					<tr>
						<td class="kv-label">最高学历</td>
						<td class="kv-content"><span id="type">${zj.highEdu }</span></td>
						<td class="kv-label">特长</td>
						<td class="kv-content"><span id="isVisible">${zj.speciality }</span></td>
						<td class="kv-label">从业年限</td>
						<td class="kv-content"><span id="adminer">${zj.years }</span></td>
					</tr>
					<tr>
						<td class="kv-label">业绩</td>
						<td class="kv-content"><span id="type">${zj.achievement }</span></td>
						<td class="kv-label">所属公司/部门</td>
						<td class="kv-content"><span id="isVisible">${zj.belongCompany }</span></td>
						<td class="kv-label">内外部专家</td>
						<td class="kv-content"><span id="adminer">${zj.isInner }</span></td>
					</tr>

					<tr>
						<td class="kv-label">级别</td>
						<td class="kv-content"><span id="type">${zj.grade }</span></td>
						<td class="kv-label">是否可出席</td>
						<td class="kv-content"><span id="isVisible">${zj.canAttend }</span></td>
					</tr>
				</tbody>
			</table>
					
					<div style="margin-left: 2px; width: 30%">
						<td class="kv-label">相关附件：</td> 
						<ul id="fileLi" style="margin-top: 5px"></ul>
					</div>
		

			<table class="easyui-datagrid" tyle="width:100%;height:auto"
				title="隶属岗位列表">
				<thead>
					<tr>
						<th data-options="field:'code',width:'30%'">编码</th>
						<th data-options="field:'code2',width:'40%'">岗位名称</th>
						<th data-options="field:'code3',width:'30%'">简称</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${zj.postions}">
						<tr>
							<td>${item.code }</td>
							<td>${item.name }</td>
							<td>${item.simpleName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<table class="easyui-datagrid" tyle="width:100%;height:auto"
				title="专业列表">
				<thead>
					<tr>
						<th data-options="field:'code1',width:'30%'">专业名称</th>
						<th data-options="field:'code2',width:'40%'">专业描述</th>
						<th data-options="field:'code3',width:'30%'">隶属板块</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${zj.majors}">
						<tr>
							<td>${item.name }</td>
							<td>${item.desp }</td>
							<td>${item.temp_str1 }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			<table class="easyui-datagrid" style="width: 100%; height: auto"
				title="拥有资格证列表">
				<thead>
					<tr>
						<th data-options="field:'code1',width:'30%'">证件类型</th>
						<th data-options="field:'code2',width:'40%'">证件名称</th>
						<th data-options="field:'code3',width:'30%'">证件编号</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${zj.certificates}">

						<tr>
							<td>${item.cType }</td>
							<td>${item.name }</td>
							<td>${item.code }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<table class="easyui-datagrid" style="width: 100%; height: auto"
				title="使用类型列表">
				<thead>
					<tr>
						<th data-options="field:'code1',width:'100%'">类型名称</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><c:forEach var="item" items="${zj.useTypes}">${item }
			</c:forEach></td>
					</tr>
				</tbody>
			</table>
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
						<td class="kv-label">专家申请业务</td>
						<td class="kv-content"><span  >${zj.coType }</span></td>
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
				<input id="formKey" name="formKey" value='${zj.id }' type="hidden">
				<input name="pdName" value='zjInOutFlow' type="hidden">
				<input name="taskId" id="taskId" value='${taskId }' type="hidden">
				
			</form>
			</br>
			<c:if test="${task =='yes' and node != 'draftsman'}">
			<div style="float: right; margin-right: 30px;">
				<button class="easyui-linkbutton" type=button iconCls="icon-add"
					data-options="selected:true" onclick="auditOperate();"
					style="width: 150px">提交</button>
			</div>
			</c:if>
			<c:if test="${task =='yes' and node =='draftsman' }">
			<div style="float: right; margin-right: 30px;">
				<button class="easyui-linkbutton" type=button iconCls="icon-remove"
					data-options="selected:true" onclick="delOperate();"
					style="width: 150px">作废</button>
			</div>
			</c:if>
		  </div>	
		</div>
		</div>
	<script type="text/javascript">
		$(function(){
			$("#flowImage").load("<%=basePath %>flow/openActivitiProccessImage/"+ $('#taskId').val() );
			getAttachments($("#formKey").val(),$("#fileLi"),false,true);
		});
		
		function auditOperate(){
			$.ajax({
				type : 'POST',
				url : ctx + "flow/audit",
				data :  $("#zjAuditFm").serialize(),
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
					} );
				},
				dataType : "JSON"
			});
		}

		function delOperate(){			
			$.ajax({
				type : 'DELETE',
				url : ctx + "flow/flow/"+$("#aid").val() + "/" +$("#taskId").val() ,
				data :  $("#zjAuditFm").serialize(),
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
					} );
				},
				dataType : "JSON"
			});
		}
	</script>
</body>
</html>