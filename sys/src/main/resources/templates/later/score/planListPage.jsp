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
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/common/common.js"></script>
	<link href="<%=basePath%>common/fit/v4/static/lightblue/css/base.css" rel="stylesheet">
	<link href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/easyui.css"  rel="stylesheet" > 
    <link href="<%=basePath%>common/fit/v4/static/lightblue/css/providers.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/icon.css">
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.easyui.min.js"></script>	
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/combotree-enhance.js"></script>	
    <script type="text/javascript" src="<%=basePath%>common/fit/v4/custom/lightblue/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/later/score/planListPage.js"></script>
</head> 
<body>	
		<form id="planlistFM">
			<div class="conditions">
            <span class="con-span">关键词: </span><input class="easyui-textbox" type="text" name="keyWords" style="width:166px;height:35px;line-height:35px;"></input>
        		<a href="javascript:planReloadgrid();" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a> 
        		<a href="javascript:score();" class="easyui-linkbutton" iconCls="icon-edit" >专家评分</a> 
        	</div>
        </form>
       <table id="planLiDg" title="计划单列表" data-options="toolbar:'#planlistFM' "></table>
	<div id="_loadDialog_planList"></div>
	<div id="_loadDialog_selectTemp"></div>
	<div id="_loadDialog_scoreList"></div>
	<div id="_loadDialog_score_itemList"></div>
</body>
</html>
