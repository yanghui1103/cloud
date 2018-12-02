/***
 * 专业编辑js
 * @author jiaw
 * 2018-9-13
 */
var tmp = $("#returnInfo").val().split(',');
$(function(){
	getParentTree();
	var row = getSingleGridSelectData($("#majorLiDg"));
	var pId = row.parent_id;
	if(pId == ''){
		pId = '0';
	}
	changePlate('update',pId,tmp);
});

function getParentTree(){
	$('#parentTree').combotree({
		url:ctx +'major/getMajorTree/0',
		method:'get',
		value:$("#parent_id").val(),
		idField: 'id',
		textField: 'name',
		fitColumns: true,
		required:true,
		onChange:function(newValue, oldValue){
			changePlate('clear',newValue,tmp);
		} 
	});
}
function editMajor(){	 
	if (!$("#majorEditFm").form('enableValidation')
			.form('validate')) {
		return;
	}
	var t = $("#plates").combobox('getValues');
	if(t.length != 0){
		var plates = '';
		$.each(t,function(i,item){
			plates += item +',';
		});
		$("#returnInfo").val(plates);
	}
	$.ajax({
		type : 'PUT',
		url : ctx + "major/major",
		data : serializeFormToJSON($("#majorEditFm")
				.serializeArray()),
		success : function(data) {
			promptMessageCallBack(data.res, data.msg,function(){
				 $('#majorLiDg').treegrid('reload');  
			} );
		},
		dataType : "JSON",
		error:function(jqXHR, textStatus, errorThrown){
			promptMessage("1",errorThrown);
		}
	});

	
}