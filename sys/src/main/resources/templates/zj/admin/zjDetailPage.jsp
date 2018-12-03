<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><jsp:include page="/common.jsp" /><%@ include file="/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="easyui-tabs" style="width:100%;">					
		      <div title="基本信息" data-options="closable:false" class="basic-info">
		      	<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">名称</td>
							<td class="kv-content"><span id="name1">${zj.name }</span></td>
							<td class="kv-label">性别</td>
							<td class="kv-content"><span id="code">${zj.gender }</span></td>
							<td class="kv-label">身份证号码</td>
							<td class="kv-content"><span id="simpleName">${zj.cardId }</span></td>
						</tr>
						<tr>
							<td class="kv-label">联系电话</td>
							<td class="kv-content"><span id="type">${zj.phone }</span></td>
							<td class="kv-label">Email</td>
							<td class="kv-content"><span id="isVisible">${zj.email }</span></td>
							<td class="kv-label">第一学历</td>
							<td class="kv-content"><span id="adminer">${zj.firstEdu }</span></td>
						</tr>
						<tr>
							<td class="kv-label">最高学历</td>
							<td class="kv-content"><span id="type">${zj.highEdu }</span></td>
							<td class="kv-label">特长</td>
							<td class="kv-content"><span id="isVisible">${zj.speciality }</span></td>
							<td class="kv-label">从业年限</td>
							<td class="kv-content"><span id="adminer">${zj.years }</span></td>
						</tr>
						<tr>
							<td class="kv-label">业绩</td>
							<td class="kv-content"><span id="type">${zj.achievement }</span></td>
							<td class="kv-label">所属公司/部门</td>
							<td class="kv-content"><span id="isVisible">${zj.belongCompany }</span></td>
							<td class="kv-label">内外部专家</td>
							<td class="kv-content"><span id="adminer">${zj.isInner }</span></td>
						</tr>
						
						<tr>
							<td class="kv-label">级别</td>
							<td class="kv-content"><span id="type">${zj.grade }</span></td>
							<td class="kv-label">是否可出席</td>
							<td class="kv-content"><span id="isVisible">${zj.canAttend }</span></td>
						</tr>
					</tbody>
				</table>
	
					<div style="margin-left: 2px; width: 30%">
						<input id="zjId"  value="${zj.id }"  type="hidden" >
						<td class="kv-label">相关附件：</td> 
						<ul id="fileLi" style="margin-top: 5px"></ul>
					</div>
		
		<table class="easyui-datagrid"  tyle="width:100%;height:auto" title="隶属岗位列表"  > 
			<thead>
				<tr>
					<th data-options="field:'code',width:'30%'">编码</th>
					<th data-options="field:'code2',width:'40%'">岗位名称</th>
					<th data-options="field:'code3',width:'30%'">简称</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${zj.postions}">
				<tr>
					<td>${item.code }</td>
					<td>${item.name }</td>
					<td>${item.simpleName }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<table class="easyui-datagrid"  tyle="width:100%;height:auto" title="专业列表"  > 
			<thead>
				<tr>
					<th data-options="field:'code1',width:'30%'">专业名称</th>
					<th data-options="field:'code2',width:'40%'">专业描述</th>
					<th data-options="field:'code3',width:'30%'">隶属板块</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${zj.majors}">
				<tr>
					<td>${item.name }</td>
					<td>${item.desp }</td>
					<td>${item.temp_str1 }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		
		<table class="easyui-datagrid"  style="width:100%;height:auto" title="拥有资格证列表"  > 
			<thead>
				<tr>
					<th data-options="field:'code1',width:'30%'">证件类型</th>
					<th data-options="field:'code2',width:'40%'">证件名称</th>
					<th data-options="field:'code3',width:'30%'">证件编号</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${zj.certificates}">
				<tr>
					<td>${item.cType }</td>
					<td>${item.name }</td>
					<td>${item.code }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<table class="easyui-datagrid"  style="width:100%;height:auto" title="使用类型列表"  > 
			<thead>
				<tr>
					<th data-options="field:'code1',width:'100%'">类型名称</th> 
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
			<c:forEach var="item" items="${zj.useTypes}">${item }
			</c:forEach>
				</td> </tr>
			</tbody>
		</table>
	 </div>
	 
	 <div title="操作历史" data-options="closable:false" class="basic-info">
			<table class="easyui-datagrid" style="width: 100%; height: auto"
				title="操作历史">
				<thead>
					<tr>
						<th data-options="field:'code1',width:'30%'">操作类型</th>
						<th data-options="field:'code2',width:'40%'">说明</th>
						<th data-options="field:'code3',width:'20%'">操作者</th>
						<th data-options="field:'code4',width:'10%'">操作时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${zjHistory}">
						<tr>
							<td>${item.coType }</td>
							<td>${item.remark }</td>
							<td>${item.creator }</td>
							<td>${item.createTime }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		getAttachments($("#zjId").val(),$("#fileLi"),false,true);
	});
	</script>
</body>
</html>