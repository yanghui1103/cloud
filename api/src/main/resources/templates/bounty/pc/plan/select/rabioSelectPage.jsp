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
		<div class="tbars">
		<form id="rselectFm">
		<input name="temp_str1" value="${zjIds }"  type="hidden">
		<input name="temp_str2" value="${planId }"  type="hidden">
		
		<span class="con-span">使用意见</span>
			<select class="easyui-combogrid"  name="temp_str3"  editable="false" style="height:35px;width:166px;" data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					required:true,
					url: '<%=basePath%>dict/getDictsByParentValue/selectUse',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true
				">
			</select>	
			
            <span class="con-span">其他说明</span><input class="easyui-textbox" type="text" name="remark" style="width:300px;height:35px;line-height:35px;"></input>
        	
        	<a href="javascript:useSubmit();" class="easyui-linkbutton" iconCls="icon-add" >提交</a> 
        	</form>
        	</div>
			<table class="easyui-datagrid" id="slrtZjs" tyle="width:100%;height:auto" title="待选取专家列表"  > 
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
		<script type="text/javascript">
		$(function(){
			
			$('#slrtZjs').datagrid({
				onDblClickRow: function(index,row){
					openzjDetail(row.id);
				}
			});

			
			
		});
		
		function useSubmit(){
			alert(($("#rselectFm").serialize()));
			promptMessageCallBack("3","是否确认该操作？",function(){					
				$.ajax({
					type : 'POST',
					url : ctx + "select/select",
					data :  ($("#rselectFm").serialize()),
					success : function(data) {
						promptMessageCallBack(data.res, data.msg,function(){
						} );
					},
					dataType : "JSON"
				});
			});
		}
		</script>
</body>
</html>