var menus = '';
var SystemMenu ='';
$(function(){
	$.ajax({url: "account/menus" ,
		type:'get',
		beforeSend: function(request) {
			request.setRequestHeader("sessionId", $("#sessionId").val());
		},
		data:serializeFormToJSON($("#zjAddFm").serializeArray()),
		async:false,
		dataType : "JSON",
		success:function(data){
			menus = data ;
		}
	});
});
 SystemMenu = [{
	title: '系统管理',
	icon: '&#xe63f;',
	isCurrent: true,
	menu:  menus 
}];