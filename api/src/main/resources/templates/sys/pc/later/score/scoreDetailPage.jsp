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
    	<table id="scoreDetailsDg" title="评分明细表" style="width: 100%;paddding-right:2px;height:auto">
		</table>
    </div>
    <script type="text/javascript">
	    $('#scoreDetailsDg').datagrid({
			url: ctx+'score/getScoreItems/${templetId }/${zj.id }',
			method: 'get',
			singleSelect: true,
			rownumbers:true,
		    autoRowHeight:false,
	        fitColumns:true,
	        striped:true,
		    columns:[[    
		        {field:'itemName',title:'评分项名称',width:'20%'},    
		        {field:'weight',title:'权重值',width:'15%'},
		        {field:'goal',title:'得分',width:'20%'},
		        {field:'operator',title:'最后评分人',width:'15%'},
		        {field:'versionTime',title:'最后评分时间',width:'25%'},
		    ]]
	    });
    </script>
</body>
</html>
