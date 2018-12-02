<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<link href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/easyui.css"  rel="stylesheet" >
	<link href="<%=basePath%>common/fit/v4/static/lightblue/css/index.css"  rel="stylesheet" >
    <link href="<%=basePath%>common/fit/v4/static/lightblue/css/basic_info.css" rel="stylesheet">
	<link rel="stylesheet" href="<%=basePath%>common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>common/fit/v4/static/lightblue/css/providers.css">
	<link href="<%=basePath%>common/fit/v4/static/lightblue/css/base.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/fit/v4/custom/lightblue/uimaker/icon.css">
	
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/common/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/fit/v4/js/easyui-lang-zh_CN.js"></script>	
	
	<script type="text/javascript" src="<%=basePath%>common/ztree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/ztree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/system/position/positionLeftTree.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/js/system/position/positionListPage.js"></script>

</head> 
<body>
	<div class="container">
		<div class="left-tree" style="overflow-y:scroll">
		<input type="hidden" value="${sessionScope.CurrentUser.currentOrgId}" id="org_id" />
		<input type="hidden" value="-1" id="org_ids" />
		<label class="kv-label">关键词:</label><input type="text" id="key" value="" class="empty" />
			<div style="display:none">
 			<input type="radio" id="name" name="keyType" class="radio first" checked />
				<input type="radio" id="getNodesByParamFuzzy" name="funType" class="radio" style="margin-left:36px;" checked />
			</div>
			<div class="zTreeDemoBackground left">
				<ul id="positionTree" class="ztree"></ul>
			</div>
		</div>
		<div class="content">
			<div class="easyui-tabs1" style="width:100%;">					
		      <div title="岗位列表" data-options="closable:false" class="basic-info">
		      	<div id="_loadDialog_positionList"></div>
		      	<div id="_loadDialog_address"></div>
		      	<table id="positionLiDg">
       			</table> 
		      </div>
		    </div>
		    <div style="padding: 0 20px; float: right;position: absolute;top:0px;right:0px">
				<a href="javascript:addPositionPage();" class="easyui-linkbutton" iconCls="icon-add"
					data-options="selected:true">新增</a> 
				<a href="javascript:openEditPosition()" class="easyui-linkbutton" iconCls="icon-edit" >编辑</a> 
				<a href="javascript:deletePosition();" class="easyui-linkbutton" iconCls="icon-remove" >删除</a> 
			</div>	
		</div>
	</div>
	
</body>
</html>
<script type="text/javascript">
$('.easyui-tabs1').tabs({
    tabHeight: 36,
    onSelect: function() {
    	setTimeout(function() {
  		resizeTabs();
  	},100)
    }
  });
  $(window).resize(function(){
  	$('.easyui-tabs1').tabs("resize");
  	setTimeout(function() {
  		resizeTabs();
  	},100)
  }).resize();
  function resizeTabs() {
		var $body = $('body'),
			$window = $(window);

		if($window.height() > $body.height()) {

			$('.panel-body').height($window.height() - 44);

		}
  }
    </script>
