<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.bw.fit.system.common.util.*"
	pageEncoding="UTF-8"%><jsp:include page="/common.jsp" /><%@ include file="/include.inc.jsp"%>
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
	<div style="float: right;margin-right:30px;margin-top:5px" >
		<button class="easyui-linkbutton" type=button iconCls="icon-add" data-options="selected:true"
			onclick="saveEdit();" style="width: 150px">保存</button>
	</div>
	
	<div style="margin:30px 0px;"></div>
	<form id="zjEditFm"  class="easyui-form" method="post"
		data-options="novalidate:false">
		<input name="id"  id="zjId" value="${zj.id }" type="hidden">
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>姓名</div>
			<input class="easyui-textbox"  name="name" style="width: 70%;paddding-right:2px"  
			value="${zj.name }"	data-options="required:true,validType:['length[1,30]']">
		</div>
				
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>性别</div>
			<select class="easyui-combogrid"  name="gender"  editable="false"   style="width: 70%;paddding-right:2px" data-options="limitToList:true,
					panelWidth: 200,
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
			</select>	
		</div>
		
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>身份证号码</div>
			<input class="easyui-textbox"  name="cardId" style="width: 70%;paddding-right:2px"    value="${zj.cardId }" 
				data-options="required:true,validType:['length[18,18]']">
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>专家类型</div>
			<input class="easyui-textbox"  value="${zj.isInner }" style="width: 70%;paddding-right:2px"    value="${zj.cardId }" 
				>
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>联系电话</div>
			<input class="easyui-textbox"  name="phone"  style="width: 70%;paddding-right:2px"   value="${zj.phone }" 
				data-options="required:true,validType:['length[11,11]']">
		</div>
		 
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>电子邮箱</div>
			<input class="easyui-textbox"  name="email"   style="width: 70%;paddding-right:2px"   value="${zj.email }" 
				data-options="required:true,validType:['length[4,30]']">
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>第一学历</div>
			<select class="easyui-combogrid"  name="firstEdu"    editable="false"   style="width: 70%;paddding-right:2px" data-options="limitToList:true,
					panelWidth: 200,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.firstEdu }',
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
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>最高学历</div>
			<select class="easyui-combogrid"  name='highEdu'  editable="false"   style="width: 70%;paddding-right:2px" data-options="limitToList:true,
					panelWidth: 200,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.highEdu }',
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
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>特长</div>
			<input class="easyui-textbox"  name="speciality" value="${zj.speciality }"  style="width: 70%;paddding-right:2px"  
				data-options="required:true">
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>从业年限</div>
			<input class="easyui-textbox"  name="years"  value="${zj.years }"   style="width: 70%;paddding-right:2px"  
				data-options="required:true">
		</div>
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>业绩</div>
			<input class="easyui-textbox"  name="achievement"   value="${zj.achievement }"   style="width: 70%;paddding-right:2px"  >
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px;" >  
			<div>所属公司/部门</div>
			<input class="easyui-textbox"  name="belongCompany"   value="${zj.belongCompany }"   style="width: 70%;paddding-right:2px"  >
		</div>
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>专业</div>
			<input class="easyui-combotree" id="major" data-options="
			url:'<%=basePath%>major/getMajorTree/0',
			method:'get',
			idField: 'id',
			value:'${zj.temp_str1 }',
			multiple: true,
			required:true,
			onlyLeafCheck:true,
			textField: 'name'"
			style="width: 70%;paddding-right:2px"  >
		</div>	
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>申请专家级别</div>
			<select class="easyui-combogrid"  name="grade"  editable="false"   style="width: 70%;paddding-right:2px" data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					value:'${zj.grade }',
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
		</div>
		<input name="temp_str5"  value="${goFlow }" type="hidden">
		<input name="temp_str1" type="hidden">
		<input name="temp_str2" type="hidden">
		<input name="_method"  value="PUT" type="hidden">
		
		<div style="margin-bottom: 20px;margin-left: 90px">  
			<div>申请使用类型</div>
			<select class="easyui-combogrid"  id="useType" editable="false"   style="width: 70%;paddding-right:2px" data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					multiple:true,
					value:'${zj.temp_str2 }',
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
		</div>

		<div style="margin-bottom: 20px; margin-left: 90px">
			<table id="dgRegter" class="easyui-datagrid" title="职称与资格证书列表"
				style="width: 70%; paddding-right: 2px; height: auto"
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
							data-options="field:'cType',width:100,  
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
							data-options="field:'name',width:250,editor:{type:'textbox',options:{required:true}}">证书全称</th>
						<th
							data-options="field:'code',width:250,editor:{type:'textbox',options:{required:true}}">证书编号</th>
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
		
		<c:if test="${goFlow=='1' }">
		<div style="margin-bottom: 20px; margin-left: 90px">
			<div>部门负责人</div>
			<input class="easyui-textbox address-select-account"  editable=false  name="deptAdminer" style="width: 70%;paddding-right:2px"  
				data-options="required:true">
			<input type="hidden"  name="accountIds"  
				data-options="required:true">
		</div>
		</c:if>		
	</form>	
	
	<script type="text/javascript">
		$(function(){
			$(".address-select-account").textbox({
				icons: [{
					iconCls:'icon-search',
					handler: function(e){
						openAddress($("#_loadDialog_address"),$("input[name='accountIds']"),$(".address-select-account"),"A",true);
					}
				}]
			});
			
		});

	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#dgRegter').datagrid('validateRow', editIndex)){
			$('#dgRegter').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function append(){ 
		if(endEditing()){
			$('#dgRegter').datagrid('appendRow',{});
			editIndex = $('#dgRegter').datagrid('getRows').length-1;
			$('#dgRegter').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
		}
	}

	function acceptZs(){
		if (endEditing()){
			$('#dgRegter').datagrid('acceptChanges');
		}
	}
	function getdgRegterChanges(){
		var rows = $('#dgRegter').datagrid('getChanges');
		return rows ;
	}
	

	function saveZcZtoZj(ztype,name,code,id){
		$.ajax({
			type : 'POST',
			url : ctx + "zj/cert" ,
			data : {'ztype':ztype,'name':name,'code':code,'id':id},
			success : function(data) {
			},
			dataType : "JSON",
			error:function(e){
				promptMessage("1","增加证件时发生异常");
			}
		});
		return null ;
	}
	
	function saveEdit(){
		if (!$("#zjEditFm").form('enableValidation')
				.form('validate')) {
			return;
		}
		$("input[name='temp_str1']").val($("#major").combotree("getValues"));
		$("input[name='temp_str2']").val($("#useType").combotree("getValues"));
		$.ajax({
			type : 'PUT',
			url : ctx + "zj/zj" ,
			data :serializeFormToJSON($("#zjEditFm")
					.serializeArray()), 
			success : function(data) {
				promptMessageCallBack(data.res, data.msg,function(data){
					zjReloadgrid();
				});
			},
			dataType : "JSON",
			error:function(e){
				promptMessage("1",e);
			}
		});
		
		var rows = getdgRegterChanges();
		if(rows != null){
			for(var i=0;i<rows.length;i++){
				saveZcZtoZj(rows[i].zType,rows[i].attrn,rows[i].attrc,$("#zjId").val());
			}
		}
	}
	</script>
	<script type="text/javascript" src="<%=basePath%>common/js/system/address/addressPage.js"></script>
</body>
</html>