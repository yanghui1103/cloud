<%@ page language="java" contentType="text/html; charset=UTF-8"
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
    <table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">选择专业</td>
				<td class="kv-content">
					<input class="easyui-combotree jq-form-input" id="parentTree"
					 name="parentId" style="width: 70%;paddding-right:2px">
				</td>
				<td class="kv-label">操作</td>
				<td class="kv-content">
					<button class="easyui-linkbutton" type=button iconCls="icon-reload"
					data-options="selected:true" onclick="move();"
					style="width: 150px">迁移专家</button>
				</td>
			</tr>
		</tbody>
	</table>
	<table id="zjListData" title="选择专家" >
     </table>
     <form id="zjlistDataFM">
		<input id='id' value="${arg}" name="id" type="hidden">
	</form>
     <div id="_loadDialog_zjDetail"></div>
     <script type="text/javascript" src="<%=basePath%>common/js/zj/major/moveZjPage.js"></script>
</body>
</html>