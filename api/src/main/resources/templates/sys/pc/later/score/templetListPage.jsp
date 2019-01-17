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
    <script type="text/javascript" src="<%=basePath%>common/fit/v4/custom/lightblue/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/zj/admin/zjListPage.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/later/score/templetListPage.js"></script>
</head> 
<body>	
      	<form id="templetlistFM">
			<div class="conditions">
            <span class="con-span">模板名称: </span>
            <input class="easyui-textbox" type="text" name="templetName" style="width:166px;height:35px;line-height:35px;"></input>
            <span class="con-span">使用类型: </span>
			<select class="easyui-combogrid"
				name="useType"
				style="width:166px; height:35px;paddding-right: 2px"
				data-options="limitToList:true,
				idField: 'dict_value',
				textField: 'dict_name',
				url: '<%=basePath%>dict/getDictsByParentValue/useType',
				method: 'get', 
				columns: [[
					{field:'dict_name',title:'名称',width:'95%'} 
				]],
				fitColumns: true
			">
			</select>	
			<a href="javascript:templetListquery();" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a> 
       		<a href="javascript:openAddTemplet();" class="easyui-linkbutton" iconCls="icon-add" >新增</a> 
       		<a href="javascript:editTemplet();" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a> 
       		<a href="javascript:deleteTemplet();" class="easyui-linkbutton" iconCls="icon-remove" >删除</a> 
	       	</div>
	     </form>
       <table id="templetLiDg" title="模板列表" data-options="toolbar:'#templetlistFM' "></table>
	<div id="_loadDialog_templetList"></div>
</body>
</html>
