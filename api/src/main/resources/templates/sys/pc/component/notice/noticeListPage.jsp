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
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>plugins/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/component/notice/noticeListPage.js"></script>
</head> 
<body>	
      	<form id="noticelistFM">
			<div class="conditions">
            <span class="con-span">标题: </span>
            <input class="easyui-textbox" type="text" name="title" style="width:166px;height:35px;line-height:35px;"></input>
            <span class="con-span">类型: </span>
			<select class="easyui-combogrid"  name="typeCode" style="width:166px; height:35px;paddding-right: 2px" 
				data-options="limitToList:true,
					idField: 'dict_value',
					textField: 'dict_name',
					url: '<%=basePath%>dict/getDictsByParentValue/bulletinType',
					method: 'get', 
					columns: [[
						{field:'dict_name',title:'名称',width:'100%'} 
					]],
					fitColumns: true
				">
			</select>
			<a href="javascript:noticeListquery();" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a> 
       		<a href="javascript:addNotice();" class="easyui-linkbutton" iconCls="icon-add" >新增</a> 
       		<a href="javascript:editNotice();" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a> 
       		<a href="javascript:deleteNotice();" class="easyui-linkbutton" iconCls="icon-remove" >删除</a> 
	       	</div>
	     </form>
       <table id="noticeLiDg" title="公告制度列表 " data-options="toolbar:'#noticelistFM' "></table>
    </div>
	<div id="_loadDialog_noticeList"></div>
</body>
</html>
