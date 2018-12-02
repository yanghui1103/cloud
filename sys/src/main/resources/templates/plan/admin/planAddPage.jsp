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
		<form id="planAddFm">
		<input name="id" value="${uuid }" type="hidden">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">计划单名称</td>
						<td class="kv-content"><input class="easyui-textbox"
							name="name" style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[1,60]']"></td>
						<td class="kv-label">项目名称</td>
						<td class="kv-content"><input class="easyui-textbox"
							name="projName" style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[1,60]']"></td>
					</tr>
					<tr>
						<td class="kv-label">项目编号</td>
						<td class="kv-content"><input class="easyui-textbox"
							name="projCode" style="width: 70%; paddding-right: 2px"
							data-options="validType:['length[0,60]']"></td>
						<td class="kv-label">使用板块</td>
						<td class="kv-content"><select class="easyui-combogrid"
							id="platteId" editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/plate',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					multiple:true,
					fitColumns: true, 
					required:true
				">
						</select></td>
					</tr>
					<tr>
						<td class="kv-label">使用日期</td>
						<td class="kv-content"><input class="easyui-datebox" editable="false"
							name="useDate" style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[1,200]']"></td>
						<td class="kv-label">使用地点</td>
						<td class="kv-content"><input class="easyui-textbox"
							name="useAddress" style="width: 70%; paddding-right: 2px"
							data-options="validType:['length[0,100]']"></td>
					</tr>
					<tr>
						<td class="kv-label">A类专家人数</td>
						<td class="kv-content"><input class="easyui-numberbox"
							name="useAnum" style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[1,3]']"></td>
						<td class="kv-label">B类专家人数</td>
						<td class="kv-content"><input class="easyui-numberbox"
							name="useBnum" style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[1,3]']"></td>
					</tr>
					<tr>
						<td class="kv-label">使用类型</td>
						<td class="kv-content"><select class="easyui-combogrid"
							id="useTypeid" editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
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
						<td class="kv-label">抽取方式</td>
						<td class="kv-content"><select class="easyui-combogrid"
							id="waysId" editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/selectWay',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					multiple:true,
					fitColumns: true, 
					required:true
				">
						</select></td>
					</tr>
					
					<input name="temp_str1"  type="hidden">
					<input name="temp_str2"  type="hidden">
					<input name="temp_str3"  type="hidden">
				</tbody>
			</table>
		</form>
		</br>
		<div style="float: right; margin-right: 30px;">
			<button class="easyui-linkbutton" type=button iconCls="icon-add"
				data-options="selected:true" onclick="planAdd();"
				style="width: 150px">保存</button>
		</div>
	</div>

	<script type="text/javascript">
		function planAdd() {
			var  arr = $("#platteId").combogrid("getValues");
			$("input[name='temp_str1']").val(castArrayToStr(arr));
			arr = $("#useTypeid").combogrid("getValues");
			$("input[name='temp_str2']").val(castArrayToStr(arr));
			arr = $("#waysId").combogrid("getValues");
			$("input[name='temp_str3']").val(castArrayToStr(arr));
			
			promptMessageCallBack("3", "是否确认新增抽取计划单？", function() {
				$.ajax({
							type : 'POST',
							url : ctx + "plan/plan",
							data : serializeFormToJSON($("#planAddFm")
									.serializeArray()),
							success : function(data) {
								promptMessageCallBack(data.res, data.msg,
										function() {
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