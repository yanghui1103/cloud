/**
 *
 */
$(function(){
	$(".address-select").textbox({
		icons: [{
			iconCls:'icon-search',
			handler: function(e){
				openAddress($("#_loadDialog_address"),$("input[name='tempStr1']"),$(".address-select"),'O',true);
			}
		}]
	})
});

function editPosition(){
	if (!$("#positionEditFm").form('enableValidation')
		.form('validate')) {
		return;
	}
	$.ajax({
		type : 'put',
		url : ctx + "updateMicroServiceResult/v1/sys-proj/position/position/"+transferFormToString($("#positionEditFm")),
		data : serializeFormToJSON($("#positionEditFm")
			.serializeArray()),
		success : function(data) {
			promptMessageCallBack(data.res, data.msg,function(){
				$('#positionLiDg').datagrid('reload');
			} );
		},
		dataType : "JSON"
	});
}