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
</head>
<body>
	<div title="操作" data-options="closable:false" class="basic-info">
		<form id="stdAddFm">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">使用类型</td>
						<td class="kv-content"><select class="easyui-combogrid"
							name="useType" editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${std.useType }',
					url: '<%=basePath%>dict/getDictsByParentValue/useType',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
						</select></td>
						<td class="kv-label">内外部专家</td>
						<td class="kv-content"><select class="easyui-combogrid"
							name="isInner"  editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${std.isInner }',
					url: '<%=basePath%>dict/getDictsByParentValue/zjgtype',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
						</select></td>
					</tr>
					<tr>
						<td class="kv-label">专家级别</td>
						<td class="kv-content"><select class="easyui-combogrid"
							name="grade"  editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${std.grade }',
					url: '<%=basePath%>dict/getDictsByParentValue/expertlev',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
						</select></td>
						<td class="kv-label">计算单位</td>
						<td class="kv-content"><select class="easyui-combogrid"
							name="unit"  editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${std.unit }',
					url: '<%=basePath%>dict/getDictsByParentValue/feeunit',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
						</select></td>
					</tr>
					
					<tr>
						<td class="kv-label">计算单位</td>
						<td class="kv-content"><input class="easyui-numberbox" value="${std.fee }"
							name="fee" style="width: 70%; paddding-right: 2px"
							data-options="required:true,min:0,precision:2"></td>
						<input name="id" value="${std.id }" type="hidden" >
					</tr>
				</tbody>
			</table>
		</form>
		</br>
		<div style="float: right; margin-right: 30px;">
			<button class="easyui-linkbutton" type=button iconCls="icon-add"
				data-options="selected:true" onclick="stdAdd();"
				style="width: 150px">保存</button>
		</div>
	</div>

	<script type="text/javascript">
		function stdAdd() {
			promptMessageCallBack("3", "是否确认保存津补贴标准记录？", function() {
				$.ajax({
							type : 'POST',
							url : ctx + "reward/standard",
							data : serializeFormToJSON($("#stdAddFm")
									.serializeArray()),
							success : function(data) {
								promptMessageCallBack(data.res, data.msg,
										function() {
									zjReloadgrid();
										});
							},
							dataType : "JSON",
							error : function(jqXHR, textStatus, errorThrown) {
								promptMessage("1", errorThrown.message);
							}
						});
			});
		}
	</script>
</body>
</html>