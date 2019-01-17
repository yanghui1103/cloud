<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	<table class="easyui-datagrid" tyle="width:100%;height:auto"
		title="待选取专家列表">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true"></th>
				<th data-options="field:'code',width:'30%'">专家名称</th>
				<th data-options="field:'code2',width:'10%'">性别</th>
				<th data-options="field:'code3',width:'30%'">内外部专家</th>
				<th data-options="field:'code4',width:'30%'">专家级别</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${zjs}">
				<tr>
					<td>${item.id }</td>
					<td>${item.name }</td>
					<td>${item.gender }</td>
					<td>${item.isInner }</td>
					<td>${item.grade }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>