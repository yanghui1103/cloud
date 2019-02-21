/**
 * 
 */

function updateDict(){
	if (!$("#dicteditFm").form('enableValidation')
			.form('validate')) {
		return;
	}
	$.ajax({
		type : 'put',
		url : ctx + "updateMicroServiceResult/v1/sys-proj/dict/dict/"+transferFormToString($("#dicteditFm")),
		data : serializeFormToJSON($("#dicteditFm")
				.serializeArray()),
		success : function(data) {
			promptMessageCallBack(data.res, data.msg,function(){
				 $('#dataDictTreeGd').treegrid('reload');  
			} );
		},
		dataType : "JSON"
	});
}



