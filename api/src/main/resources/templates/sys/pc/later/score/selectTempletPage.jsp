<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.bw.fit.system.common.util.*"
	pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
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
</head>
<body>
	<div class="3containrder">
		<div style="padding: 0 5px;">
		<form id="selectTempletFm" class="easyui-form" method="post" 
				data-options="novalidate:false">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">计划单名称</td>
						<td class="kv-content">${plan.name}</td>
  							<td class="kv-label">使用类型</td>
						<td class="kv-content">
						<c:forEach items="${plan.useTypes}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach></td>
						</tr>
						<tr>
						<td class="kv-label">选择模板</td>
						<td class="kv-content">
						<input type="hidden" name="planId" id="planId" value="${plan.id }">
						<input type="hidden" id="planUseType" value="${planUseType }">
						<input type="hidden" id="oldTemplet" value="${oldTemplet }">
						<select class="easyui-combogrid" editable="false" id="selectTemplet"
							name = "templetId" style="width: 70%; paddding-right: 2px">
						</select></td>
						<td class="kv-label">操作</td>
						<td class="kv-content">
							<a href="javascript:selectTemplet();"  
							class="easyui-linkbutton" 
							iconCls="icon-save" >使用此模板评分</a> 
						</td>
					</tr>
				</tbody>
			</table>
			</form>
			<table id="itemsLiDg" class="easyui-datagrid" title="评分项列表" style="width: 100%;paddding-right:2px;height:auto">
			</table>
	    </div>
	</div>
	<script type="text/javascript" src="<%=basePath%>common/js/later/score/selectTempletPage.js"></script>
</body>
</html>
