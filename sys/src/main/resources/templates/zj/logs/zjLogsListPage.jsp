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
	<link rel="stylesheet" href="<%=basePath %>common/fit/v4/static/lightblue/css/providers1.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/icon.css">
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.easyui.min.js"></script>	
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/combotree-enhance.js"></script>	
    <script type="text/javascript" src="<%=basePath%>common/fit/v4/custom/lightblue/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/zj/logs/zjLogsListPage.js"></script>
</head> 
<body>	
    <div class="container">
      <div id="zjLogslisttb" style="padding:0 30px;">
		<form id="zjLogslistFM">
			<div class="conditions">
            <span class="con-span">关键词: </span><input class="easyui-textbox" type="text" name="keyWords" style="width:166px;height:35px;line-height:35px;"></input>
        		<a href="javascript:zjLogsReloadgrid();" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a> 
        	</div>
        </form>
      </div>      
       <table id="zjLogsLiDg" style="width:100%;height:520px" title="对专家操作日志列表" data-options="
                autoRowHeight:false,
                fitColumns:true,
                striped:true,
                checkOnSelect:true,
                selectOnCheck:true,
                collapsible:true,
                toolbar:'#zjLogslisttb' "></table>
    </div>
	<div id="_loadDialog_zjLogslist"></div>
</body>
</html>
