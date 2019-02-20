<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><jsp:include page="/common.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
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
		<div class="container" style="height: 99%">
			<div class="left-tree" style="overflow-y:scroll">
			<label class="kv-label">关键词:</label><input type="text" id="key" value="" class="empty" />
				<div style="display:none">
	 			<input type="radio" id="name" name="keyType" class="radio first" checked />
					<input type="radio" id="getNodesByParamFuzzy" name="funType" class="radio" style="margin-left:36px;" checked />
				</div>
				<div class="zTreeDemoBackground left">
					<ul id="addrOrgTree" class="ztree"></ul>
				</div>
				<input type="hidden" value="${sessionScope.CurrentUser.currentOrgId}" id="addr_org_id">
			</div>
			<div class="content" style="height:100%">
				<div align="center" style="height:60px;padding-top: 10px">
					<label class="kv-label">关键词:</label>
					<input type="text" name="keyWords" id="keyWords" value="" class="empty" />
					<input type="button" onclick="changeConstraintTerm('-9')" value="查找" />
					<br>
					<c:if test="${ifshow_org }">
						<input onclick="checkboxClick()" id="select_org" type="checkbox" checked="checked"><label for="select_org" class="kv-label">组织</label>
					</c:if>
					<c:if test="${ifshow_position }">
						<input onclick="checkboxClick()" id="select_position" type="checkbox" checked="checked"><label for="select_position" class="kv-label">岗位</label>
					</c:if>
					<c:if test="${ifshow_account }">
						<input onclick="checkboxClick()" id="select_account" type="checkbox" checked="checked"><label for="select_account" class="kv-label">账户</label>
					</c:if>
				</div>
				<form name="myform" style="height:80%;">
				<table width="100%" height="100%" border="1" rules="all">
					<tr height="3%">
						<td width="45%">待选</td>
						<td width="10%"></td>
						<td width="45%">已选</td>
					</tr>
					<tr>
						<td>
							<select id="dxlb_select" style="WIDTH:100%;height: 100%" ${isMultiple } name="list1" size="12" onclick="showDetail(this)" ondblclick="moveOption(document.myform.list1, document.myform.list2)"> 
	                            <c:forEach items="${selectList }" var="sm">
	                           		<option value="${sm.value }" data-val="${sm.tmp }" >${sm.text }</option>
	                            </c:forEach>
		                    </select>
						</td>
						<td>
							<input style="margin-left: 5px" type="button" value="添加" onclick="moveOption(document.myform.list1, document.myform.list2)">
			                <br/> 
			                <br/> 
			                <input style="margin-left: 5px" type="button" value="删除" onclick="moveOption(document.myform.list2, document.myform.list1)">
						</td>
						<td >
							<select style="WIDTH:100%;height: 100%" name="list2" size="12" ${isMultiple } onclick="showDetail(this)" ondblclick="moveOption(document.myform.list2, document.myform.list1)"> 
               					<c:forEach items="${selectedList }" var="sdm">
	                           		<option value="${sdm.value }" data-val="${sdm.tmp }" >${sdm.text }</option> 
	                            </c:forEach>
               				</select>
						</td>
					</tr>
					<tr height="5%">
						<td colspan="3" >
							<input id="selectids" type="hidden" name="selectids" >
							<input id="selectnames" type="hidden" name="selectnames">
							<span id="addressDetail"></span>
						</td>
					</tr>
				</table>
				</form>
		</div>
		</div>
	
    <link href="<%=basePath%>common/fit/v4/static/lightblue/css/basic_info.css" rel="stylesheet">
	<link rel="stylesheet" href="<%=basePath%>common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>common/js/system/address/organizationListPage.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/ztree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>common/ztree/js/jquery.ztree.excheck.js"></script>
</body>
</html>