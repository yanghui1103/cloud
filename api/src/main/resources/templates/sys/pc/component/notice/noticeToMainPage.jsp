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
</head> 
<body>	
      	<form id="noticelistFMMain">
			<div class="conditions">
            <span class="con-span">标题: </span>
            <input type="hidden" name="typeCode" value="${arg }">
            <input class="easyui-textbox" type="text" name="title" style="width:166px;height:35px;line-height:35px;"></input>
			<a href="javascript:noticesquery();" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a> 
	       	</div>
	     </form>
       <table id="noticeMainLiDg" data-options="toolbar:'#noticelistFMMain' "></table>
    </div>
	<div id="_loadDialog_notices"></div>
</body>
</html>
<script type="text/javascript">
function noticesquery(){   
	$('#noticeMainLiDg').datagrid({ 
		method:"get",
	    url:ctx+'notice/notices', 
        queryParams:serializeFormToJSON($("#noticelistFMMain").serializeArray()), 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'title', title: '标题', width: '30%',fixed:true},
                   { field: 'creator', title: '创建人', width: '20%'  },
                   { field: 'createTime', title: '创建时间', width: '25%'  },
                   { field: 'versionTime', title: '修改时间', width: '25%'  }
               ]],
             fit: true ,    
             idField: "id",
             pagination: true,
             remoteSort: false, 
             singleSelect:true,
             rownumbers: true, 
             fitColumns:true,
             pageNumber: 1,
             pageSize: 20,
             pageList: [ 10,20, 30, 40, 50],
             striped: true, //奇偶行是否区分                 
             onDblClickRow: function (index, row) {  
            	 openNoticesDetail(row.id); 
             }
	});  
}
function openNoticesDetail(id){
	$('#_loadDialog_noticeList').dialog({    
	    title: '查看详情',    
	    width: '95%', 
	    height: 550,
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'notice/detail/'+id,    
	    modal: true   
	});
}
$(function(){
	noticesquery();
});
</script>