<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.bw.fit.system.common.util.*" pageEncoding="UTF-8"%><jsp:include
	page="/common.jsp" /><%@ include file="/include.inc.jsp"%>
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

	<div style="margin: 1px 0px;"></div>
	<form id="dealerEditFm" class="easyui-form" method="post"
		data-options="novalidate:false">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">流程名称</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="name" style="width: 70%; paddding-right: 2px"
						 value="${pdinst }"></td>
					<td class="kv-label">节点名称</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="name" style="width: 70%; paddding-right: 2px"
						 value="${node }"></td>
				</tr>
				
				<tr>
					<td class="kv-label">节点任务类型</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="name" style="width: 70%; paddding-right: 2px"
						 value="${type }"></td>
					<td class="kv-label">处理人</td>
						<td class="kv-content"><input
							class="easyui-textbox address-select-account" editable=false   value="${names }" 
							name="deptAdminer" style="width: 70%; paddding-right: 2px"
							data-options="required:true"> <input type="hidden"
							name="accountIds" value="${ids }" data-options="required:true"></td>
				</tr>
	</tbody>
	</table>
	<input value="${nodeCode  }" name="nodeCode" >
	<input value="${pdinstCode  }" name="pdinst" >
	</form>
	<div style="float: right; margin-right: 30px; margin-top: 5px">
		<button class="easyui-linkbutton" type=button iconCls="icon-add"
			data-options="selected:true" onclick="dealerSubmit();"
			style="width: 150px">保存</button>
	</div>
	
	<script type="text/javascript"
		src="<%=basePath%>common/js/system/address/addressPage.js"></script>
	<script type="text/javascript">

	$(function() {
		$(".address-select-account")
				.textbox(
						{
							icons : [ {
								iconCls : 'icon-search',
								handler : function(e) {
									openAddress($("#_loadDialog_address"),
											$("input[name='accountIds']"),
											$(".address-select-account"),
											"A", true);
								}
							} ]
						});
	});
	
	
	function dealerSubmit(){
		if (!$("#dealerEditFm").form('enableValidation').form('validate')) {
			return;
		}
		$.ajax({
			type : 'POST',
			url : ctx + "flow/dealer",
			data : serializeFormToJSON($("#dealerEditFm").serializeArray()),
			success : function(data) {
				promptMessageCallBack(data.res, data.msg, function(data) {
				});
			},
			dataType : "JSON",
			error : function(e) {
				promptMessage("1", e);
			}
		});
	}
	
	</script>
</body>
</html>