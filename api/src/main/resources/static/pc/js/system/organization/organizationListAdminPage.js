/**
 * org admin
 */
function printOrgDetailInfo(org){
	// alert(getDictNameByValue("depoartment"));
	$("td > span").empty();
	$("#name1").text(org.name);
	$("#code").text(org.code);
	$("#simpleName").text(org.simpleName);
	$("#type").text(getDictNameByValue(org.type));
	$("#isVisible").text(getDictNameByValue(org.isVisible));
	$("#adminer").text(org.adminer);
	$("#phone").text(org.phone);
	$("#address").text(org.address);
	$("#postCode").text(org.postCode);
	$("#summary").text(org.summary);
	$("#id").val(org.id);
}


function addOrgPage(){
	$('#_loadDialog_orgList').dialog({
	    title: '新增组织',    
	    width: '98%',
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'gotoIframePage/sys/pc/system/organization/organizationAdd/-9',
	    modal: true   
	}); 
}

function fRemoveOrganazition2() {
	debugger;
    deleteOrg();
}
function fRemoveOrganazition(){
	var id = ($("#id").val());
	if(id!='' && id!='undefined'){
		promptMessageCallBack("3","是否确认删除该组织?",function(){
            $.ajax({
                type : 'DELETE',
                url : ctx + "deleteMicroServiceResult/v1/sys-proj/org/organization,"+id,
                data : {},
                beforeSend: function(request) {
                    request.setRequestHeader("sessionId", $("#sessionId").val());
                },
                success : function(data) {
                    promptMessage(data.res, data.msg );
                },
                dataType : "JSON"
            });
		})
	}
}