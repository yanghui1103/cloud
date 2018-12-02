<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><jsp:include page="/common.jsp" /><%@ include file="/include.inc.jsp"%>
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
	<form id="zjlistDataFM">
		<input id='id' value="${major.id }" name="id" type="hidden">
	</form>
	<table class="kv-table"  tyle="width:100%;height:auto" > 
		<tbody>
			<tr>
				<td class="kv-label">名称</td>
				<td class="kv-content"><span id="name">${major.name }</span></td>
				<td class="kv-label">状态</td>
				<td class="kv-content"><span id="status">${major.status }</span></td>
			</tr>
			<tr>
				<td class="kv-label" >描述</td>
				<td class="kv-content" colspan="3"><span id="type">${major.desp }</span></td>
			</tr>
			<tr>
				<td class="kv-label" >可使用板块</td>
				<td class="kv-content" colspan="3"><span id="type">
					<c:forEach var="item" items="${major.availablePlates}">${item }&nbsp;&nbsp;
					</c:forEach>
				</span></td>
			</tr>
		</tbody>
	</table>
	<table id="zjListData" title="专家列表">
     </table>
     <div id="_loadDialog_zjDetail"></div>
     <script type="text/javascript" src="<%=basePath%>common/js/zj/major/majorDetailPage.js"></script>
</body>
</html>