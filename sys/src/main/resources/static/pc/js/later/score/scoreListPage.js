function getScoreItems(templetId,zjId){
	$('#scoreItemsDg').datagrid({
		toolbar:'#scoreitemlitb',
	    columns:[[    
	        {field:'itemName',title:'评分项名称',width:'30%'},    
	        {field:'weight',title:'权重值',width:'30%',},
	        {field:'goal',title:'得分',width:'35%',
	        	editor:{type:'numberbox',options:{precision:2,min:0,max:100}}}
	    ]],
	    singleSelect: true,
		method: 'get',
		url: ctx+'score/getScoreItems/'+templetId+'/'+zjId,
		rownumbers:true,
		onClickCell: onClickCell,
		onLoadSuccess:function(data){   
			var rows = $('#scoreItemsDg').datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				$('#scoreItemsDg').datagrid('editCell', {index:i,field:'goal'});
			}
        }
	});
}
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

function onClickCell(index, field){
	$('#scoreItemsDg').datagrid('selectRow', index)
			.datagrid('editCell', {index:index,field:field});
}
function saveScore(jsondata){
	$.ajax({
		type : 'POST',
		url : ctx + "score/score" ,
		data :jsondata, 
		success : function(data) {
			promptMessageCallBack(data.res, data.msg,function(data){
				$('#_loadDialog_score_itemList').dialog('close');
				$('#scoreItemsDg').datagrid('reload');
			});
		},
		dataType : "JSON",
		error:function(e){
			promptMessage("1",e);
		}
	});
}
function acceptScore(){
	$('#scoreItemsDg').datagrid('acceptChanges');
	var rows = $('#scoreItemsDg').datagrid('getRows');
	var flag = true;
	for(var i=0;i<rows.length;i++){
		if(!rows[i].goal){
			flag = false;
			break;
		}
	}
	var jsondata = serializeFormToJSON($("#scoreFM").serializeArray());
	jsondata.temp_str1 = JSON.stringify(rows);
	
	console.log(rows);
	if(flag){
		saveScore(jsondata);
	}else{
		promptMessageCallBack("3","你存在评分项未打分,是否提交?",function(){
			saveScore(jsondata);
		});
	}
	
}
$(function(){
	var templetId = $("#templetId").val();
	var zjId = $("#zjId").val();
	getScoreItems(templetId,zjId);
});