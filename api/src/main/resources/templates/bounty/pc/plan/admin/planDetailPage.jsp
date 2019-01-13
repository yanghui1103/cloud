<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><jsp:include page="/common.jsp" /><%@ include
	file="/include.inc.jsp"%>
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
		<div title="基本信息" data-options="closable:false" class="basic-info">
			<form>
				<table class="kv-table">
					<tbody>
						<tr>
							<td class="kv-label">计划单名称</td>
							<td class="kv-content"><span id="name1">${plan.name }</span></td>
							<td class="kv-label">项目名称</td>
							<td class="kv-content"><span id="name1">${plan.projName }</span></td>
						</tr>
						<tr>
							<td class="kv-label">项目编号</td>
							<td class="kv-content"><span id="name1">${plan.projCode }</span></td>
							<td class="kv-label">使用板块</td>
							<td class="kv-content"><span id="name1">
							<c:forEach items="${plan.plattes}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach>
							</span></td>
						</tr>
						<tr>
							<td class="kv-label">使用日期</td>
							<td class="kv-content"><span id="name1">${plan.useDate }</span></td>
							<td class="kv-label">使用地点</td>
							<td class="kv-content"><span id="name1">${plan.useAddress }</span></td>
						</tr>
						<tr>
							<td class="kv-label">A类专家人数</td>
							<td class="kv-content"><span id="name1">${plan.useAnum }</span></td>
							<td class="kv-label">B类专家人数</td>
							<td class="kv-content"><span id="name1">${plan.useBnum }</span></td>
						</tr>
						<tr>
							<td class="kv-label">使用类型</td>
							<td class="kv-content"><span id="name1">
							<c:forEach items="${plan.useTypes}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach></span></td>
							<td class="kv-label">抽取方式</td>
							<td class="kv-content"><span id="name1">
							<c:forEach items="${plan.ways}" var="s">
        					<c:out value="${s.dict_name}"></c:out>
  							</c:forEach></span></td>
						</tr>

					</tbody>
				</table>
			</form>
		</div>
		
		
		<div title="抽取日志" data-options="closable:false" class="basic-info">
				
		<table class="easyui-datagrid"  tyle="width:100%;height:auto" title="抽取日志"  > 
			<thead>
				<tr>
					<th data-options="field:'code',width:'20%'">来源</th>
					<th data-options="field:'code2',width:'20%'">抽取者</th>
					<th data-options="field:'code3',width:'20%'">抽取时间</th>
					<th data-options="field:'code4',width:'20%'">使用意见</th>
					<th data-options="field:'code5',width:'20%'">其他说明</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${selectLog}">
				<tr>
					<td>${item.originType }</td>
					<td>${item.creator }</td>
					<td>${item.createTime }</td>
					<td>${item.selectUse }</td>
					<td>${item.remark }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
		
		<div title="抽取结果" data-options="closable:false" class="basic-info">
		<table id="selectZzjsDg" class="easyui-datagrid"  tyle="width:100%;height:auto" title="抽取结果"  > 
		</table>
		
		</div>
		
	</div>
	<div id="_loadDialog_score_detailList"></div>
	<script type="text/javascript">
	$(function(){
		
		$('#selectZzjsDg').datagrid({
			url:ctx+'score/zjs/${plan.id }',
			method:"get",
		    autoRowHeight:true,
	        fitColumns:true,
	        singleSelect:true,
	        rownumbers:true,
	        striped:true,
		    columns:[[    
		        {field:'zjId',hidden:true},    
		        {field:'temp_str1',title:'姓名',width:'15%'},    
		        {field:'temp_str2',title:'身份证号码',width:'15%'},    
		        {field:'temp_str3',title:'专家级别',width:'15%'},    
		        {field:'creator',title:'抽取者',width:'15%'},    
		        {field:'createTime',title:'抽取时间',width:'15%'},    
		        {field:'temp_str4',title:'总分',width:'13%'},
		        {field:'cz',title:'操作',align:'center',width:'10%',
		        	formatter:function(value, row, index){
		        		var str = '<a name="opera" href="javascript:scoreDetail();" class="easyui-linkbutton" ></a> ';
		        		return str;
		        }}
		    ]],
		    onLoadSuccess:function(data){  
		        $("a[name='opera']").linkbutton({text:'评分详情',plain:true,iconCls:'icon-search'});  
			},
			onDblClickRow: function(index,row){
				$('#_loadDialog_planList3').dialog({    
				    title: '专家详情',    
				    width: '99%',    
				    height: 500,    
				    closed: false,    
				    cache: false,    
				    maximizable:true,
				    href: ctx+'zj/zj/'+row.zjId,    
				    modal: true   
				}); 
			}
		});
	});
	function scoreDetail(){
		var row = getSingleGridSelectData($("#selectZzjsDg"));
		if(row !=null){
			$.parser.parse("#_loadDialog_score_itemList");
			$('#_loadDialog_score_detailList').dialog({    
			    title: '评分详情',    
			    width: '60%',    
			    height: 460,    
			    closed: false,    
			    cache: false,    
			    maximizable:true,
			    href: ctx+'score/scoreDetail/'+row.zjId+'/${plan.id }',    
			    modal: true   
			});
		}
	}
	</script>
</body>
</html>