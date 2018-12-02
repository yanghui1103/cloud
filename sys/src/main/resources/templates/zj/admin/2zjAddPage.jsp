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
	<form id="zjAddFm" class="easyui-form" method="post"
		data-options="novalidate:false">
		<input name="id" id="zjAddUid" value="<%=PubFun.getUUID()%>"
			type="hidden">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">姓名</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="name" style="width: 70%; paddding-right: 2px"
						data-options="required:true,validType:['length[1,30]']"></td>
					<td class="kv-label">性别</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name="gender" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/gender',
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
					<td class="kv-label">性别</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name="gender" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/gender',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
					</select></td>

					<td class="kv-label">身份证号码</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="cardId" style="width: 70%; paddding-right: 2px"
						data-options="required:true,validType:['length[18,18]']"></td>
					<div style="margin-bottom: 20px; margin-left: 90px">
				</tr>

				<tr>
					<td class="kv-label">专家类型</td>
					<td class="kv-content">
					<select class="easyui-combogrid" name='isInner' id="zjInnerFlag"
						editable="false" style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/zjgtype',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
					</select>
				</td>

					<td class="kv-label">联系电话</td>
					<td class="kv-content">
						<input class="easyui-textbox" name="phone"
							style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[11,11]']">
					</td>
				</tr>
				<tr>	

					<td class="kv-label">电子邮箱</td>
					<td class="kv-content">
						<input class="easyui-textbox" name="email"
							style="width: 70%; paddding-right: 2px"
							data-options="required:true,validType:['length[4,30]']">
					</td>

					<td class="kv-label">第一学历</td>
					<td class="kv-content">
						<select class="easyui-combogrid" name="firstEdu" editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/education',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
						</select>
					</td></tr>

				<tr>	
					<td class="kv-label">最高学历</td>
					<td class="kv-content">
						<select class="easyui-combogrid" name='highEdu' editable="false"
							style="width: 70%; paddding-right: 2px"
							data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/education',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
						</select>
					</td>
 

				<tr>	
					<td class="kv-label">特长</td>
					<td class="kv-content">
					<input class="easyui-textbox" name="speciality"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true">
				</td>

					<td class="kv-label">从业年限</td>
					<td class="kv-content">
					<input class="easyui-textbox" name="years"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true">
				</td>
				</tr>
				
				<tr>
					<td class="kv-label">业绩</td>
					<td class="kv-content">
					<input class="easyui-textbox" name="achievement"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true">
				</td>

					<td class="kv-label">所属公司</td>
					<td class="kv-content">
					<input class="easyui-textbox" name="belongCompany"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true">
				</td>
				</tr>

				<tr>
					<td class="kv-label">专业</td>
					<td class="kv-content">
					<input class="easyui-combotree" name="temp_str1" id="quhao"
						data-options="
			url:'<%=basePath%>major/getMajorTree/0',
			method:'get',
			idField: 'id',
			multiple: true,
			required:true,
			onlyLeafCheck:true,
			textField: 'name'"
						style="width: 70%; paddding-right: 2px">
				</td>

					<td class="kv-label">专家级别</td>
					<td class="kv-content">
					<select class="easyui-combogrid" name="grade" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/expertlev',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
					</select>
				</td>
				</tr>
				<input name="temp_str5" value=true type="hidden">


				<tr>
					<td class="kv-label">申请使用类型</td>
					<td class="kv-content">
					<select class="easyui-combogrid" name="temp_str2" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					multiple:true,
					url: '<%=basePath%>dict/getDictsByParentValue/useType',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
					</select>
				</td>
				</tr>
				</tbody>
			</table>
				

				<div style="margin-bottom: 20px; margin-left: 0px">


					<table id="dgRegter" class="easyui-datagrid" title="职称与资格证书列表"
						style="width: 100%; paddding-right: 2px; height: auto"
						data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tbZjcc',
				method: 'get' 
			">
						<thead>
							<tr>
								<th
									data-options="field:'zType',width:'30%',  
						formatter:function(value,row){
							if(value=='1'){
								return '职称证书';
							}else{
							return '资格证书';}
						},
						editor:{
							type:'combobox',
							options:{editable:false,
								valueField:'productid',
								textField:'productname',
								method:'get',  
								url:'<%=basePath%>common/js/zj/admin/products.json',
								required:true
							}
						}">类型</th>
								<th
									data-options="field:'attrn',width:'40%',editor:{type:'textbox',options:{required:true,validType:['length[1,30]']}}">证书全称</th>
								<th
									data-options="field:'attrc',width:'30%',editor:{type:'textbox',options:{required:true,validType:['length[1,30]']}}">证书编号</th>
							</tr>
						</thead>
					</table>

					<div id="tbZjcc" style="height: auto">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-save',plain:true" onclick="append()">确认</a>
					</div>
				</div>
	</form>

	<div style="float: right; margin-right: 30px; margin-top: 5px">
		<button class="easyui-linkbutton" type=button iconCls="icon-add"
			data-options="selected:true" onclick="zjRegister();"
			style="width: 150px">保存</button>
	</div>

	<script type="text/javascript"
		src="<%=basePath%>common/js/system/address/addressPage.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>common/js/system/account/accountAddPage.js"></script>

	<script type="text/javascript">
		var editIndex = undefined;
		function endEditing() {
			if (editIndex == undefined) {
				return true
			}
			if ($('#dgRegter').datagrid('validateRow', editIndex)) {
				$('#dgRegter').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function append() {
			if (endEditing()) {
				$('#dgRegter').datagrid('appendRow', {});
				editIndex = $('#dgRegter').datagrid('getRows').length - 1;
				$('#dgRegter').datagrid('selectRow', editIndex).datagrid(
						'beginEdit', editIndex);

			}
		}

		function acceptZs() {
			if (endEditing()) {
				$('#dgRegter').datagrid('acceptChanges');
			}
		}
		function getdgRegterChanges() {
			var rows = $('#dgRegter').datagrid('getChanges');
			return rows;
		}

		$(function() {
			$("#outerZj").hide();
			$("#zjInnerFlag").combogrid({
				onChange : function(newVal, oldVal) {
					if (newVal == "outerzj") {
						$("#outerZj").show();
						$.parser.parse("#outerZj");
					} else {
						$("#outerZj").hide();
					}
				}
			});

		});

		function zjRegister() {
			if (!$("#zjAddFm").form('enableValidation').form('validate')) {
				return;
			}
			$.ajax({
				type : 'POST',
				url : ctx + "zj/zj",
				data : serializeFormToJSON($("#zjAddFm").serializeArray()),
				success : function(data) {
					promptMessageCallBack(data.res, data.msg, function(data) {
						zjReloadgrid();
					});
				},
				dataType : "JSON",
				error : function(e) {
					promptMessage("1", e);
				}
			});

			var rows = getdgRegterChanges();
			if (rows != null) {
				for (var i = 0; i < rows.length; i++) {
					saveZcZtoZj(rows[i].zType, rows[i].attrn, rows[i].attrc, $(
							"#zjAddUid").val());
				}
			}
		}

		function saveZcZtoZj(ztype, name, code, id) {
			$.ajax({
				type : 'POST',
				url : ctx + "zj/cert",
				data : {
					'ztype' : ztype,
					'name' : name,
					'code' : code,
					'id' : id
				},
				success : function(data) {
					// 				if(data.res !='2'){
					// 					promptMessage(data.res, data.msg);
					// 				}
				},
				dataType : "JSON",
				error : function(e) {
					promptMessage("1", "增加证件时发生异常");
				}
			});
			return null;
		}
	</script>
</body>
</html>