/**
 * 专家管理
 * @author yangh
 * 2018-9-11
 */
$(function(){
	zjlistquery();
});

function zjlistquery(){   
	
	$('#zjLiDg').datagrid({ 
		pagination:true,
		method:"get",
	    url:ctx+'zj/zjs' , 
        queryParams:   ($("#zjlistFM").serialize()),
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'name', title: '名称', width: '20%',fixed:true  },
                   { field: 'gender', title: '性别', width: '10%' },
                   { field: 'isInner', title: '是否内部专家', width: '20%' }, 
                   { field: 'grade', title: '专家级别', width: '20%' }, 
                   { field: 'temp_str2', title: '使用类型', width: '20%'  }, 
                   { field: 'canAttend', title: '出席状态', width: '10%'  }
               ]],
             fit: false ,    
             idField: "id",
             pagination: true,
             singleSelect:true,
             rownumbers: true, 
             fitColumns:true,
             pageNumber: 1,
             pageSize: 10,
             pageList: [ 10,20, 30, 40, 50],
             striped: true, //奇偶行是否区分                 
             onDblClickRow: function (index, row) {  
            	 openzjDetail(row.id); 
             }     
	});  
}




//增加查询参数，在页面加载时运行  
function zjReloadgrid() {  
	var  arr = $("#zjtypes").combogrid("getValues");
	$("#zjlistTyps").val(arr.toString());
	$('#zjLiDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	$('#zjLiDg').datagrid('options').queryParams= serializeFormToJSON($("#zjlistFM").serializeArray());  
	$("#zjLiDg").datagrid('reload');
}  

function updateZj1(){ 
	var row = getSingleTreeGridSelectData($("#zjLiDg"));
	if(row !=null){
		$('#_loadDialog_zjList').dialog({    
			title: '专家编辑',    
			width: '99%',    
			height: 500,    
			closed: false,    
			cache: false,    
	    	maximizable:true,
	    	href: ctx+'zj/edit/'+row.id+"/1",    
	    	modal: true   
		}); 
	}

	
}
function updateZjN(){ 
	var row = getSingleTreeGridSelectData($("#zjLiDg"));
	if(row !=null){
		$('#_loadDialog_zjList').dialog({    
			title: '专家直接编辑',    
			width: '99%',    
			height: 500,    
			closed: false,    
			cache: false,    
	    	maximizable:true,
	    	href: ctx+'zj/edit/'+row.id+"/0",    
	    	modal: true   
		}); 
	}
}

function openAddzj(){
	$('#_loadDialog_zjList').dialog({    
	    title: '专家注册',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'system/gotoIframePage/zj/admin/zjAddPage/-9',    
	    modal: true   
	}); 
}

function openApplyOut(){
	var row = getSingleTreeGridSelectData($("#zjLiDg"));
	if(row !=null){
		$('#_loadDialog_zjList').dialog({    
		    title: '专家申请退出',    
		    width: '99%',    
		    height: 500,    
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: ctx+'zj/out/'+row.id+"/exitzj",    
		    modal: true   
		}); 
	}
}

function openzjDetail(id){
	$('#_loadDialog_zjList').dialog({    
	    title: '专家详情',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'zj/zj/'+id,    
	    modal: true   
	}); 
}

function updateZjAudit(id){
	var row = getSingleTreeGridSelectData($("#zjLiDg"));
	if(row !=null){
	$('#_loadDialog_zjList').dialog({    
	    title: '专家详情',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'zj/audit/'+row.id+"/62543",    
	    modal: true   
	}); 
	}
}

function openUpdatezj(id){
	$('#_loadDialog_zjList').dialog({    
	    title: '专家直接编辑',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'zj/edit/'+id,    
	    modal: true   
	}); 	
}



function updateZjAttend(){	 
	var row = getSingleTreeGridSelectData($("#zjLiDg"));
	if(row !=null){
		promptMessageCallBack("3","是否确认变更专家可出席状态？",function(){					
			$.ajax({
				type : 'PUT',
				url : ctx + "zj/attend/"+row.id,
				data : {"_method":"PUT"},
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
						zjReloadgrid();
					} );
				},
				dataType : "JSON",
				error:function(jqXHR, textStatus, errorThrown){
					promptMessage("1",errorThrown);
				}
			});
		});
	}
}


function updateZj(){	 
	var row = getSingleTreeGridSelectData($("#zjLiDg"));
	if(row !=null){
		promptMessageCallBack("3","是否确认变更已经归档的专家资料？,确认后系统将直接变更归档，不走审核流程",function(){					
			$.ajax({
				type : 'PUT',
				url : ctx + "zj/zj/"+row.id,
				data : {"_method":"PUT"},
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
					} );
				},
				dataType : "JSON",
				error:function(jqXHR, textStatus, errorThrown){
					promptMessage("1",errorThrown);
				}
			});
		});
	}
}