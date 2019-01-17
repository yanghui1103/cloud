<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><jsp:include page="/common.jsp" />
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
	<div style="padding: 0 5px;">
		<form id="positionAddFm" class="easyui-form" method="post" 
				data-options="novalidate:false">
			<table class="kv-table">
				<tbody>
					<tr>
					<td class="kv-label">所属组织</td>
						<td class="kv-content">
						<input type="hidden" value="${orgIds }" name="temp_str1"  >
						<input class="easyui-textbox" readonly value="${orgNames }" 
							 style="width: 70%; paddding-right: 2px"></td>
						<td class="kv-label">名称</td>
						<td class="kv-content">
						<input class="easyui-textbox" data-options="required:true,validType:['length[0,20]']"
							name="name" style="width: 70%; paddding-right: 2px"></td>
						</td>
					</tr>
					<tr>
						<td class="kv-label">简称</td>
						<td class="kv-content">
						<input class="easyui-textbox" data-options="rvalidType:['length[0,10]']"
							name="simpleName" style="width: 70%; paddding-right: 2px"></td>
						<td class="kv-label">等级</td>
						<td class="kv-content">
						<input class="easyui-textbox" data-options="required:true,rvalidType:['length[0,10]']"
							name="grade" style="width: 70%; paddding-right: 2px"></td>
					</tr>
					<tr>
						<td class="kv-label">编码</td>
						<td class="kv-content">
						<input class="easyui-textbox" data-options="required:true,validType:['length[0,10]']"
							name="code" style="width: 70%; paddding-right: 2px"></td>
						<td class="kv-label">排序号</td>
						<td class="kv-content">
						<input class="easyui-numberbox" data-options="required:true,validType:['length[0,3]']"
							name="sortNumber" style="width: 70%; paddding-right: 2px"></td>
					</tr>
				</tbody>
			</table>
			</form>
			</br>
			<div style="float: right; margin-right: 30px;">
				<button class="easyui-linkbutton" type=button iconCls="icon-add" data-options="selected:true"
			onclick="addNewPosition();" style="width: 150px">保存</button>
			</div>
	    </div>
	
	<script type="text/javascript" src="<%=basePath%>common/js/system/position/positionAddPage.js"></script>
</body>
</html>