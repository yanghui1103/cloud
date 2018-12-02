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
	<script type="text/javascript" src="<%=basePath%>common/js/zj/admin/zjSdlistPage.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/component/attachment/attachment.js"></script>
</head> 
<body>	
      <div id="zjSdlisttb" style="padding:0 30px;">
		<form id="zjSdlistFM">
			<div class="conditions">
            <span class="con-span">关键词: </span><input class="easyui-textbox" type="text" name="keyWords" style="width:166px;height:35px;line-height:35px;"></input>
            <span class="con-span">使用类型: </span>
			<select class="easyui-combogrid"  id="zjtypes"  editable="false" style="height:35px;width:166px;" data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					multiple: true,
					url: '<%=basePath%>dict/getDictsByParentValue/useType',
					method: 'get', 
					columns: [[
						{field:'dict_value',title:'值',width:50},
						{field:'dict_name',title:'名称',width:140} 
					]],
					fitColumns: true
				">
			</select>	
			<input name="temp_str1" id ="zjSdlistTyps" type="hidden">
        	</div>
			<div class="conditions">
        		<a href="javascript:zjReloadgrid();" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a> 
        		<a href="javascript:openApplySd();" class="easyui-linkbutton" iconCls="icon-add" >屏蔽</a> 
        		<a href="javascript:openApplyUnSd();" class="easyui-linkbutton" iconCls="icon-edit" >解除屏蔽</a> 
        	</div>
        </form>
      </div>      
       <table id="zjSdLiDg" style="width:100%;height:520px" title="全体专家列表" data-options="
                autoRowHeight:false,
                fitColumns:true,
                striped:true,
                checkOnSelect:true,
                selectOnCheck:true,
                collapsible:true,
                toolbar:'#zjSdlisttb' "></table>
	<div id="_loadDialog_zjSdlist"></div>
	<div id="_loadDialog_address"></div>
</body>
</html>
