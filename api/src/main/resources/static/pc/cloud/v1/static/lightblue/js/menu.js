var menus = '';
var SystemMenu ='';
$(function(){
	menus = getMicroServiceResultV1("sys-proj","account","menus,"+ [[$("#sessionId").val()]] );

});
 SystemMenu = [{
	title: '系统管理',
	icon: '&#xe63f;',
	isCurrent: true,
	menu:  menus 
}];