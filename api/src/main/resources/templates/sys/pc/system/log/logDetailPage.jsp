<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.bw.fit.common.util.*" pageEncoding="UTF-8"%><jsp:include
	page="/common.jsp" /><%@ include file="/include.inc.jsp"%>
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
	<div class="easyui-tabs" style="width: 100%;">
		<div title="操作日志详情" data-options="closable:false" class="basic-info">
			<form>
				<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">操作者</td>
							<td class="kv-content"><span id="name1">${log.creator }</span></td>
						</tr>
						<tr>
							<td class="kv-label">操作时间</td>
							<td class="kv-content"><span id="name1">${log.createTime }</span></td>
						</tr>
						<tr>
							<td class="kv-label">URL</td>
							<td class="kv-content"><span id="name1">${log.url  }</span></td>
						</tr>
						<tr>
							<td class="kv-label">IP</td>
							<td class="kv-content"><span id="name1">${log.ip  }</span></td>
						</tr>
						<tr>
							<td class="kv-label">HTTP请求</td>
							<td class="kv-content"><span id="name1">${log.operateFunction  }</span></td>
						</tr>
						<tr>
							<td class="kv-label">返回值</td>
							<td class="kv-content"><span id="name1">${log.result  }</span></td>
						</tr>
						<tr>
							<td class="kv-label">请求参数</td>
							<td class="kv-content"><span id="name1">${log.params }</span></td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>