/**
 * 
 */

function addNewDict(){	 
	if (!$("#dictAddFm").form('enableValidation')
			.form('validate')) {
		return;
	}

	console.log(transferFormToString($("#dictAddFm")));
	debugger;
	$.ajax({
		type : 'POST',
		url : ctx + "addMicroServiceResult/v1/sys-proj/dict/dict/"+transferFormToString($("#dictAddFm")),
		data : serializeFormToJSON($("#dictAddFm")
				.serializeArray()),
		success : function(data) {
			promptMessageCallBack(data.res, data.msg,function(){
				 $('#dataDictTreeGd').treegrid('reload');  
			} );
		},
		dataType : "JSON"
	});

	
}