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
	<script type="text/javascript" src="<%=basePath%>common/js/zj/major/majorListPage.js"></script>
</head> 
<body>
		<form id="zjlistFM">
			<div class="conditions">
        		<a href="javascript:openAddMajorPage();" class="easyui-linkbutton" iconCls="icon-add" >新增</a> 
        		<a href="javascript:openEditPosition()" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a> 
        		<a href="javascript:deleteMajor();" class="easyui-linkbutton" iconCls="icon-remove" >删除</a> 
        		<a href="javascript:changeMajorStatus();" class="easyui-linkbutton" iconCls="icon-reload" >变更状态</a> 
        		<a href="javascript:moveZJ();" class="easyui-linkbutton" iconCls="icon-reload" >专家迁移</a> 
        	</div>
        </form>
       <table id="majorLiDg" title="专业列表" data-options="toolbar:'#zjlistFM' ">
        </table>
        <div id="_loadDialog_majorList"></div>
</body> 
</html>
