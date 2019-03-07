/**
 * 
 */

$(function(){
	accountlistquery();
});


function accountlistquery(){    
	$('#accountListDg').datagrid({ 
		pagination:true,
		method:"get",
	    url: ctx+'getMicroServiceResult/v3/sys-proj/account/accounts' ,
        queryParams:   serializeFormToJSON($("#accountlistFM").serializeArray()),
	    remoteSort: false, 
        columns: [[
                   { field: 'code'  ,hidden:true  },
                   { field: 'tempStr2'  ,hidden:true  },
                   { field: 'id', hidden:true  },
                   { field: 'name', title: '用户姓名', width: '30%',fixed:true  },
                   { field: 'logName', title: '账号', width: '30%',sortable:true },
                   { field: 'tempStr1', title: '状态', width: '20%' } ,
                   { field: 'createTime', title: '创建时间', width: '20%' ,sortable:true}
               ]],
             fit: true ,
             idField: "id",
             pagination: true,
             singleSelect:true,
             rownumbers: true, 
             fitColumns:true,
             pageNumber: 1,
             pageSize: 20,
             pageList: [ 10,20, 30, 40, 50],
             striped: true, //奇偶行是否区分                 
             onDblClickRow: function (index, row) {
            	 openUserDetail(row.id);
             }     
	});  
}




//增加查询参数，在页面加载时运行  
function accountReloadgrid() {  
	$('#accountListDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	$('#accountListDg').datagrid('options').queryParams= serializeFormToJSON($("#accountlistFM").serializeArray());  
	$("#accountListDg").datagrid('reload');
}  


function openUserDetail(id){
    $('#_loadDialog_accountlist').dialog({
    	title: '用户详情',
        width: '99%',
        height: 500,
        closed: false,
        cache: false,
        maximizable:false,
        href:  ctx+  'towardMicroServicePage/v1/sys-proj/user,user,' + id+","+ $("#sessionId").val() +"/"+"sys,pc,system,user,userDetail" ,
        modal: true
	}); 	
}




function deleteAccount(){	 
	var row = getSingleTreeGridSelectData($("#accountListDg"));
	if(row !=null){
		promptMessageCallBack("3","是否确认作废该账户？",function(){					
			$.ajax({
				type : 'DELETE',
				url: ctx + "deleteMicroServiceResult/v1/sys-proj/account/account,"+row.tempStr2 ,
				data : {},
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
						accountReloadgrid();
					} );
				},
				dataType : "JSON"
			});
		});
	}
}


function openAddAccount(){
    var cuurentAccount = JSON.parse($("#currentUser").val()) ;
	$('#_loadDialog_accountlist').dialog({
	    title: '账户新增',    
	    width: '99%',
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
        href:  ctx+  'towardMicroServicePage/v1/sys-proj/account,roles,' + cuurentAccount.id +"/"+"sys,pc,system,account,accountAdd" ,
        modal: true
	}); 	
}

function openTransferAccount(){
	var row = getSingleTreeGridSelectData($("#accountListDg"));
	$('#_loadDialog_accountlist').dialog({    
	    title: '账户过户',    
	    width: 800,    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'account/openAccountTransferPage/'+row.temp_str2,    
	    modal: true   
	}); 	
}