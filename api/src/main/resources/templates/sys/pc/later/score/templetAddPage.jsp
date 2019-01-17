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
						<td class="kv-content"><input class="easyui-textbox" data-options="required:true"
							name="templetName" style="width: 70%; paddding-right: 2px"></td>
						<td class="kv-label">使用类型</td>
						<td class="kv-content"><select class="easyui-combogrid"
							name="useType"  editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
							idField: 'dict_value',
							textField: 'dict_name',
							required:true,
							url: '<%=basePath%>dict/getDictsByParentValue/useType',
							method: 'get', 
							columns: [[
								{field:'dict_name',title:'名称',width:'95%'} 
							]],
							fitColumns: true
						">
						</select></td>
					</tr>
				</tbody>
			</table>
			</form>
			<table id="dg" class="easyui-datagrid" title="评分项列表" style="width: 100%;paddding-right:2px;height:auto"
			data-options="
				singleSelect: true,
				method: 'get',
				toolbar: '#tbMBcc',
				rownumbers:true,
				onClickRow: onClickRow
			">
		<thead>
			<tr>
				<th data-options="field:'itemName',width:'55%',editor:{type:'textbox',options:{required:true}}">评分项名称</th>
				<th data-options="field:'weight',width:'40%',editor:{type:'numberbox',options:{required:true,precision:2,min:0,max:1}}">权重值(0~1且总和小于1)</th>
			</tr>
		</thead>
	</table>
		<div id="tbMBcc" style="height:auto">
			<a href="javascript:append();"  class="easyui-linkbutton" iconCls="icon-add" >增加条目</a> 
			<a href="javascript:removeit();"  class="easyui-linkbutton" iconCls="icon-remove" >删除条目</a> 
			<a href="javascript:accept();"  class="easyui-linkbutton" iconCls="icon-save" >保存并提交</a> 
		</div>
	    </div>
	</div>
	<script type="text/javascript" src="<%=basePath%>common/js/later/score/templetAddAndUpdatePage.js"></script>
</body>
</html>
