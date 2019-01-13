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
		<form id="templetAddFm" class="easyui-form" method="post" 
				data-options="novalidate:false">
			<table class="kv-table">
				<tbody>
					<tr>
					<td class="kv-label">模板名称</td>
						<td class="kv-content">${templet.templetName }</td>
						<td class="kv-label">使用类型</td>
						<td class="kv-content">${templet.useType }</td>
					</tr>
				</tbody>
			</table>
			</form>
			<table id="dg" class="easyui-datagrid" title="评分项列表" style="width: 100%;paddding-right:2px;height:auto"
			data-options="
				singleSelect: true,
				method: 'get',
				rownumbers:true,
				url: '<%=basePath%>templet/getItems/${templet.id }'
			">
		<thead>
			<tr>
				<th data-options="field:'itemName',width:'55%',editor:{type:'textbox',options:{required:true}}">评分项名称</th>
				<th data-options="field:'weight',width:'40%',editor:{type:'numberbox',options:{required:true,precision:2,min:0,max:1}}">权重值(0~1且总和小于1)</th>
			</tr>
		</thead>
	</table>
	    </div>
	</div>
</body>
</html>
