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
	<form id="zjEditFm" class="easyui-form" method="post"
		data-options="novalidate:false">
		<input name="id" id="zjId" value="${zj.id }" type="hidden">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">姓名</td>
					<td class="kv-content"><input class="easyui-textbox"
						value="${zj.name }" name="name"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true,validType:['length[1,30]']"></td>
					<td class="kv-label">性别</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name="gender" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.gender }'	,
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


					<td class="kv-label">身份证号码</td>
					<td class="kv-content"><input class="easyui-textbox"
						value="${zj.cardId }" name="cardId"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true,validType:['length[18,18]']"></td>
					<div style="margin-bottom: 20px; margin-left: 90px">
					<td class="kv-label">专家类型</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name='isInner' id="zjInnerFlag" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.isInner }'	,
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

					<td class="kv-label">联系电话</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="phone" value="${zj.phone }"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true,validType:['length[11,11]']">
					</td>
					<td class="kv-label">电子邮箱</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="email" value="${zj.email }"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true,validType:['length[4,30]']"></td>
					
				</tr>
				<tr>

					<td class="kv-label">第一学历</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name="firstEdu" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.firstEdu }'	,
					url: '<%=basePath%>dict/getDictsByParentValue/education',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true, 
					required:true
				">
					</select></td>
					<td class="kv-label">最高学历</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name='highEdu' editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.highEdu }'	,
					url: '<%=basePath%>dict/getDictsByParentValue/education',
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
					<td class="kv-label">特长</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="speciality" value="${zj.speciality }"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true"></td>

					<td class="kv-label">从业年限</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="years" value="${zj.years }"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true"></td>
				</tr>

				<tr>
					<td class="kv-label">业绩</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="achievement" value="${zj.achievement }"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true"></td>

					<td class="kv-label">所属公司</td>
					<td class="kv-content"><input class="easyui-textbox"
						name="belongCompany" value="${zj.belongCompany }"
						style="width: 70%; paddding-right: 2px"
						data-options="required:true"></td>
				</tr>

				<tr>
					<td class="kv-label">专业</td>
					<td class="kv-content"><input class="easyui-combotree"
					  id="quhao"
						data-options="
			url:'<%=basePath%>major/getMajorTree/0',
			method:'get',
			idField: 'id',
					value:'${zj.temp_str1 }'	,
			multiple: true,
			required:true,
			onlyLeafCheck:true,
			textField: 'name'"
						style="width: 70%; paddding-right: 2px"></td>

					<td class="kv-label">专家级别</td>
					<td class="kv-content"><select class="easyui-combogrid"
						name="grade" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.grade }'	,
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
				</tr>
					<input name="temp_str1" type="hidden">
					<input name="temp_str2" type="hidden">

				<tr>
					<td class="kv-label">申请使用类型</td>
					<td class="kv-content"><select class="easyui-combogrid"
						id="temp_str2" editable="false"
						style="width: 70%; paddding-right: 2px"
						data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.temp_str2 }'	,
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
				</tr>
			</tbody>
		</table>
	<div style="margin-left: 2px; width: 50%">
		<input type="file" id="file" name="file" onChange="up('${zj.id }')">
		<ul id="fileLi" style="margin-top: 5px"></ul>
	</div>

		<input name="temp_str5" value="${goFlow }" type="hidden"> 
		 <input name="_method" value="PUT"  type="hidden">
		<div style="margin-bottom: 20px; margin-left: 0px">
			<table id="dgRegter" class="easyui-datagrid" title="职称与资格证书列表"
				style="width: 100%; paddding-right: 2px; height: auto"
				data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tbZjcc',
				method: 'get' ,
				url:'<%=basePath%>zj/certs/${zj.id }'
			">
				<thead>
					<tr>
						<th
							data-options="field:'cType',width:'30%',  
						formatter:function(value,row){
							if(value=='zcgrade'){
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
							data-options="field:'name',width:'40%',editor:{type:'textbox',options:{required:true}}">证书全称</th>
						<th
							data-options="field:'code',width:'30%',editor:{type:'textbox',options:{required:true}}">证书编号</th>
					</tr>
				</thead>
			</table>

			<div id="tbZjcc" style="height: auto">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" -->
<!-- 					data-options="iconCls:'icon-save',plain:true" onclick="append()">确认</a> -->
			</div>
		</div>

		<c:if test="${goFlow=='1' }">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">部门负责人</td>
						<td class="kv-content"><input
							class="easyui-textbox address-select-account" editable=false
							name="deptAdminer" style="width: 70%; paddding-right: 2px"
							data-options="required:true"> <input type="hidden"
							name="accountIds" data-options="required:true"></td>
					</tr>
				</tbody>
			</table>
		</c:if>
	</form>
	<div style="float: right; margin-right: 30px; margin-top: 5px">
		<button class="easyui-linkbutton" type=button iconCls="icon-add"
			data-options="selected:true" onclick="saveEdit();"
			style="width: 150px">保存</button>
	</div>


	<script type="text/javascript">

	$(function(){
		getAttachments($("#zjId").val(),$("#fileLi"),false,true);
	});
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
												"A", false);
									}
								} ]
							});

		});

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
				},
				dataType : "JSON",
				error : function(e) {
					promptMessage("1", "增加证件时发生异常");
				}
			});
			return null;
		}

		function saveEdit() {
			if (!$("#zjEditFm").form('enableValidation').form('validate')) {
				return;
			}

			$('#dgRegter').datagrid('acceptChanges');
			var arr = $("#temp_str2").combogrid("getValues");
			$("input[name='temp_str2']").val(castArrayToStr(arr));
			arr = $("#quhao").combogrid("getValues");
			$("input[name='temp_str1']").val(castArrayToStr(arr));
			promptMessageCallBack("3","是否确认此操作？",function(){		
				$.ajax({
					type : 'PUT',
					url : ctx + "zj/zj",
					data : serializeFormToJSON($("#zjEditFm").serializeArray()),
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

			});
			var rows = getdgRegterChanges();
			if (rows != null) {
				for (var i = 0; i < rows.length; i++) {
					saveZcZtoZj(rows[i].zType, rows[i].attrn, rows[i].attrc, $(
							"#zjId").val());
				}
			}
		}
	</script>
	<script type="text/javascript"
		src="<%=basePath%>common/js/system/address/addressPage.js"></script>
</body>
</html>