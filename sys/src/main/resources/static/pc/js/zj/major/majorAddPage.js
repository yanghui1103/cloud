/***
 * 专业新增js
 * @author jiaw
 * 2018-9-12
 */

function addMajor(){
	if (!$("#majorAddFm").form('enableValidation')
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
		type : 'POST',
		url : ctx + "major/major",
		data : serializeFormToJSON($("#majorAddFm")
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

$(function(){
	changePlate('insert',$("#pId").val(),'');
});
