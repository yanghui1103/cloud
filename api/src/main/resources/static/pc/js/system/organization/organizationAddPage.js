/**
 * add org
 */

function addOrg2(){ 
	if (!$("#orgAddFm").form('enableValidation')
			.form('validate')) {
		return;
	}
	alert([[${sessionId}]]);
	$.ajax({
		type : 'POST',
		url : ctx + "org/organization",
		data : serializeFormToJSON($("#orgAddFm")
				.serializeArray()),
		beforeSend: function(request) {
			request.setRequestHeader("sessionId", [[${sessionId}]]);
		},
		success : function(data) {
			promptMessage(data.res, data.msg );
		},
		dataType : "JSON"
	});
}


$(function(){
		$(".address-select").textbox({
			icons: [{
				iconCls:'icon-search',
				handler: function(e){
					openAddress($("#_loadDialog_address"),$("input[name='parentId']"),$(".address-select"),"O",false);
				}
			}]
		})
	});
