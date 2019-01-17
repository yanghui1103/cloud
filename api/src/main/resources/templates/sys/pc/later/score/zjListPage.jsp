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
</head> 
<body>	
    <div>
    	<input type="hidden" id="planId" value="${arg }">
       <table id="zjScoreLiDg" style="width:99%;height:460px" title="专家列表"></table>
       <div id="scorelisttb" style="padding:0 30px;">
		<div class="conditions">
       		<a href="javascript:scoreToZj();" class="easyui-linkbutton" iconCls="icon-add" >评分</a> 
       	</div>
      </div>
    </div>
	<script type="text/javascript" src="<%=basePath%>common/js/later/score/zjListPage.js"></script>
</body>
</html>
