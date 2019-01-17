<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>
<div data-options="closable:false" class="basic-info">
		<form id="noticeAddFm"  class="easyui-form" method="post"
		data-options="novalidate:false">
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label">标题</td>
						<td class="kv-content"><input class="easyui-textbox" 
							name="title" style="width: 70%; paddding-right: 2px"
							data-options="required:true"></td>
						<td class="kv-label">类型</td>
						<td class="kv-content">
						<select class="easyui-combogrid"  name="typeCode"  editable="false" style="width: 70%; paddding-right: 2px"  
						data-options="limitToList:true,
							idField: 'dict_value',
							textField: 'dict_name',
							url: '<%=basePath%>dict/getDictsByParentValue/bulletinType',
							method: 'get', 
							columns: [[
								{field:'dict_value',title:'值',width:'49%'},
								{field:'dict_name',title:'名称',width:'50%'} 
							]],
							fitColumns: true, 
							required:true
						">
					</select></td>
					</tr>
					
					<tr>
						<td class="kv-label">内容</td>
						<td class="kv-content" colspan="3">
						<textarea id="myEditor" name="content" style="width:99%;height:300px"></textarea>
						
					</tr>
				</tbody>
			</table>
		</form>
		</br>
		<div style="float: right; margin-right: 30px;">
			<button class="easyui-linkbutton" type=button iconCls="icon-add"
				data-options="selected:true" onclick="noticeAdd();"
				style="width: 150px">保存</button>
		</div>
	</div>
<script type="text/javascript" src="<%=basePath%>common/js/component/notice/noticeAddPage.js"></script>
<script type="text/javascript">
$(function(){  
    //富文本编辑器  
    UE.delEditor('myEditor');
    UE.getEditor('myEditor');  
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
    UE.Editor.prototype.getActionUrl = function(action){  
        if(action == '/editor/upload/file'){  
            return ctx +'/editor/upload/file';  
        }else{  
            var ac =  this._bkGetActionUrl.call(this, action);  
            return ac ;
        }  
    }   
});  
</script>
</body>
</html>