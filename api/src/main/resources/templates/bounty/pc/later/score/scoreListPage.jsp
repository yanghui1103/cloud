<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.bw.fit.system.common.util.*"
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
    <div>
	    <form id="scoreFM" class="easyui-form" method="post" 
				data-options="novalidate:false">
	    	<input type="hidden" name="planId" id="planId" value="${planId }">
	    	<input type="hidden" name="zjId" id="zjId" value="${zj.id }">
	    	<input type="hidden" name="foreignId" id="templetId" value="${templetId }">
	    </form>
	    <table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">专家姓名</td>
					<td class="kv-content">${zj.name}</td>
 						<td class="kv-label">身份证号</td>
					<td class="kv-content">${zj.cardId}</td>
					</tr>
			</tbody>
		</table>
    	<table id="scoreItemsDg" title="评分项列表" style="width: 100%;paddding-right:2px;height:auto">
			</table>
       <div id="scoreitemlitb" style="padding:0 30px;">
		<div class="conditions">
       		<a href="javascript:acceptScore();" class="easyui-linkbutton" iconCls="icon-save" >保存并提交</a> 
       	</div>
      </div>
    </div>
	<script type="text/javascript" src="<%=basePath%>common/js/later/score/scoreListPage.js"></script>
</body>
</html>
