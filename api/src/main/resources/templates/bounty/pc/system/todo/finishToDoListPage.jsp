<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.bw.fit.common.util.*"
	isELIgnored="false" pageEncoding="UTF-8"%><jsp:include page="/common.jsp"></jsp:include>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<table id="fshtodolistdg"  >
	</table>
	<div name="fshtodolisttb" style="padding: 2px 5px;"> 
		<form id="fshtodolistFM">
			<div id="fshtodolist_toolBar" class="easyui-accordion">
			关键词: <input name="keyWords" class="easyui-textbox"
				style="width: 200px">
			<a class="easyui-linkbutton" iconcls="icon-search" onclick="fshtodolistpage_query()">查询</a>
			</div>
		</form>
	</div>
<script type="text/javascript" src="<%=basePath%>common/fit/fshtodoListPage.js"></script>

</body>
</html>