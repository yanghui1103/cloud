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
		<form id="majorAddFm" class="easyui-form" method="post" 
				data-options="novalidate:false">
			<table class="kv-table">
				<tbody>
					<tr>
					<td class="kv-label">名称</td>
						<td class="kv-content">
						<input type="hidden" value="${arg}" name="parentId" id="pId">
						<input type="hidden" id="returnInfo" name="returnInfo">
						<input class="easyui-textbox" data-options="required:true,validType:['length[0,20]']"
							name="name" style="width: 70%; paddding-right: 2px"></td>
						<td class="kv-label">可使用模板</td>
						<td class="kv-content">
						<select class="easyui-combogrid" id="plates" style="width: 70%;paddding-right:2px" >
						</select>
						</td>
					</tr>
					<tr>
						<td class="kv-label">排序号</td>
						<td class="kv-content">
						<input class="easyui-numberbox" data-options="required:true,validType:['length[0,3]']"
							name="sortNumber" style="width: 70%; paddding-right: 2px"></td>
						<td class="kv-label"></td>
						<td class="kv-content"></td>
					</tr>
					<tr>
						<td class="kv-label">描述</td>
							<td class="kv-content" colspan="3">
						<textarea rows="3" cols="" name="desp" style="width: 90%;paddding-right:2px"></textarea></td>
					</tr>
				</tbody>
			</table>
			</form>
			</br>
			<div style="float: right; margin-right: 30px;">
				<button class="easyui-linkbutton" type=button iconCls="icon-add" data-options="selected:true"
			onclick="addMajor();" style="width: 150px">保存</button>
			</div>
	    </div>
		
	<script type="text/javascript" src="<%=basePath%>common/js/zj/major/majorAddPage.js"></script>
</body>
</html>