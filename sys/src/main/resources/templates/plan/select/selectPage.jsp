<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="3containrder">
		<div id="slZjlisttb" style="padding: 0 5px;">
				<table class="kv-table">
					<tbody>
					<form id='selectFm'>
						<tr>
							<td class="kv-label">内外部专家</td>
							<td class="kv-content"><select class="easyui-combogrid"
								name="isInner" id="isInner" editable="false"
								style="width: 70%; paddding-right: 2px"
								data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/zjgtype',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true
					">
							</select></td>
							<td class="kv-label">使用板块</td>
							<td class="kv-content"><select class="easyui-combogrid"
								id="slplatte" editable="false"
								style="width: 70%; paddding-right: 2px"
								data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					multiple:true,
					url: '<%=basePath%>dict/getDictsByParentValue/plate',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true
				">
							</select></td>
							<td class="kv-label">专业</td>
							<td class="kv-content"><input class="easyui-combotree"
								id="slmajor"  
								data-options="
			url:'<%=basePath%>major/getMajorTree/0',
			method:'get',
			idField: 'id',
			multiple: true,
			onlyLeafCheck:true,
			textField: 'name'"
								style="width: 70%; paddding-right: 2px"></td>
						</tr>


						<tr>
							<td class="kv-label">使用类型</td>
							<td class="kv-content"><select class="easyui-combogrid"
								id="sluseType"   editable="false"
								style="width: 70%; paddding-right: 2px"
								data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					multiple: true,
					url: '<%=basePath%>dict/getDictsByParentValue/useType',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true
				">
							</select></td>
							<td class="kv-label">第一学历</td>
							<td class="kv-content"><select class="easyui-combogrid"
								id="slfirstEdu" editable="false"
								style="width: 70%; paddding-right: 2px"
								data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					multiple: true,
					url: '<%=basePath%>dict/getDictsByParentValue/education',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true 
				">
							</select></td>
							<td class="kv-label">最高学历</td>
							<td class="kv-content"><select class="easyui-combogrid"
								id="slhlEdu" editable="false"
								style="width: 70%; paddding-right: 2px"
								data-options="limitToList:true,
					idField: 'dict_value',
					multiple: true,
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/education',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true 
				">
							</select></td>
						</tr>


						<tr>
							<td class="kv-label">性别</td>
							<td class="kv-content"><select class="easyui-combogrid"
								id="zjtypes" editable="false"
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
					fitColumns: true
				">
							</select></td>
							<td class="kv-label">从业年限(以上)</td>
							<td class="kv-content"><input class="easyui-numberbox" data-options="required:true"
								name="years" style="width: 70%; paddding-right: 2px" value="0"></td>
							
							
							<td class="kv-label">所属公司/单位</td>
							<td class="kv-content"><input
								class="easyui-textbox address-select-org clsZj" 
								id="slbelongCompany" style="width: 70%; paddding-right: 2px"
								> <input type="hidden" class="clsZj"
								name="belongCompany" ></td>
								
						</tr>
						<tr>

							<td class="kv-label">岗位</td>
							<td class="kv-content"><input
								class="easyui-textbox address-select-position clsZj" editable=false
								id="slpostionNames" style="width: 70%; paddding-right: 2px"
								> <input type="hidden" class="clsZj"
								name="positionIds" ></td>

							<td class="kv-label">岗位级别</td>
							<td class="kv-content"><select class="easyui-combogrid clsZj"
								id="slplevel" style="width: 70%; paddding-right: 2px"
								data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/zjgtype',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true
					">
							</select></td>
							<td class="kv-label">专家级别</td>
							<td class="kv-content"><select class="easyui-combogrid"
								id="slmajorgrade" editable="false"
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
					fitColumns: true
				">
							</select></td>
						</tr>
						<tr>

							<td class="kv-label">评标次数(以上)</td>
							<td class="kv-content"><input class="easyui-numberbox"
								name="logId" style="width: 70%; paddding-right: 2px" data-options="required:true" value="0"></td>
							<td class="kv-label">年龄(以上)</td>
							<td class="kv-content"><input class="easyui-numberbox"
								name="status" style="width: 70%; paddding-right: 2px" data-options="required:true" value="0"></td>
						</tr>
		</form>
						<tr>
							<td class="kv-label">机选人数:</td>
							<td class="kv-content"><input class="easyui-numberbox"
								style="width: 70%; paddding-right: 2px" id="rabioNumber"
								data-options="required:true,validType:['length[1,60]']"></td>

							<td class="kv-label"><a href="javascript:rabioSelect();"
								class="easyui-linkbutton" iconCls="icon-add">机选</a></td>
							<td class="kv-content">
								<p></p>
							</td>
						</tr>

					</tbody>
				</table>
				<input name="temp_str1" type="hidden">
				<input name="temp_str2" type="hidden">
				<input name="temp_str3" type="hidden">
				<input name="temp_str4" type="hidden">
				<input name="temp_str5" type="hidden">
				<input name="temp_str6" type="hidden">
		</div>
			<input name="id" id="id" value="${arg }" type="hidden">
		<shiro:hasRole name="PSNSelect">
			<input id="PSNSelect" type="hidden">
		</shiro:hasRole>
		<table id="slslZjLiDg" style="width: 100%; height: 520px" title="专家库" ></table>
	</div>
	<div id="_loadDialog_selectList"></div>
	<script type="text/javascript"
		src="<%=basePath%>common/js/system/address/addressPage.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>common/js/plan/select/selectPage.js"></script>
</body>
</html>
