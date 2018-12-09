//获取评分项详情
function getItems(id){
	if(!id){
		id = '-9';
	}
	$('#itemsLiDg').datagrid({  
	    columns:[[    
	        {field:'itemName',title:'评分项名称',width:'55%'},    
	        {field:'weight',title:'权重值(0~1且总和小于1)',width:'40%'}
	    ]],
	    singleSelect: true,
		method: 'get',
		url: ctx+'templet/getItems/'+id,
		rownumbers:true
	});
}
//获取模板下拉框
function getTemplets(id){
	$('#selectTemplet').combogrid({    
		limitToList:true,
		idField: 'id',
		textField: 'templetName',
		value:id,
		required:true,
		url:  ctx+'templet/getTempletByUseType/'+$("#planUseType").val(),
		method: 'get', 
		columns: [[
			{field:'templetName',title:'名称',width:'95%'} 
		]],
		fitColumns: true,
		onChange:function(newValue, oldValue){
			getItems(newValue);
		}
	}); 
}
//专家评分
function zjScore(planId){
	$('#_loadDialog_scoreList').dialog({
	    title: '专家评分',    
	    width: '90%',    
	    height: 460,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'system/gotoIframePage/later/score/zjListPage/'+planId,    
	    modal: true   
	});
}
//保存选择的模板
function saveTemplet(){
	$.ajax({
		type : 'PUT',
		data : serializeFormToJSON($("#selectTempletFm")
				.serializeArray()),
		url : ctx + "templet/changeTemplet",
		success : function(data) {
			if(data.res==2){
				$('#_loadDialog_selectTemp').dialog('close');
				zjScore(data.planId);
			}else{
				promptMessage(data.res, data.msg);
			}
		},
		dataType : "json",
		error:function(jqXHR, textStatus, errorThrown){
			promptMessage("1",errorThrown);
		}
	});
}
//选择模板
function selectTemplet(){
	var newTemplet = $("#selectTemplet").combogrid('getValue');
	var oldTemplet = $("#oldTemplet").val();
	var planId = $("#planId").val();
	if (!$("#selectTempletFm").form('enableValidation')
			.form('validate')) {
		promptMessage("1","请先选择模板!!");
	}else{
		var rows = $('#itemsLiDg').datagrid("getRows");
		var sum = 0;
		for(var i=0;i<rows.length;i++){
			sum += parseFloat(rows[i]['weight']);
		}
		if(sum == 1){
			if(oldTemplet&&oldTemplet!=newTemplet){
				promptMessageCallBack("3","你确定要更换模板吗?更换模板后之前的评分将被清除!!",function(){
					saveTemplet();
				});
			}else{
				saveTemplet();
			}
		}else{
			promptMessage("1","该模板不可用!!");
		}
	}
}

$(function(){
	var id = $("#oldTemplet").val();
	getTemplets(id);
	getItems(id)
});