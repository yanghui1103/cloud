<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.bw.fit.system.common.util.*" pageEncoding="UTF-8"%><%@ include
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
<title>流程状态</title>
</head>
<body>
	<div>
		<div class="alert alert-error">
			<i class="iconfont">&#xe62e;</i> <span>抱歉，流程已结束或已删除!</span>
		</div>

	</div>
</body>
</html>