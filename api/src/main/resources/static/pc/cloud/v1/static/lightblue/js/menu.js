var menus = '';
var SystemMenu ='';
$(function(){
	$.ajax({url:  getMicroServiceResultV1("sys-proj/account/menus/"+$("#sessionId").val() ,3),
			type:'get',
			async:false,
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