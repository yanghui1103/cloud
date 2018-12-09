/**
 * 专家管理
 * @author yangh
 * 2018-9-11
 */
$(function(){
	stdLiDglistquery();
});

function stdLiDglistquery(){   
	$('#stdLiDg').datagrid({ 
		pagination:true,
		method:"get",
	    url:ctx+'reward/standards' , 
        queryParams:   ($("#stdlistFM").serialize()),
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'useType', title: '使用类型', width: '20%',fixed:true  },
                   { field: 'isInner', title: '内外部专家', width: '20%' }, 
                   { field: 'grade', title: '专家级别', width: '20%' }, 
                   { field: 'unit', title: '计算单位', width: '20%'  }, 
                   { field: 'fee', title: '发放金额(人民币:元)', width: '20%'  }
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
             striped: true ,
             onDblClickRow:function(index,row){
                 notSupportOperation("DBL",null,null);
             }
	});  
}




//增加查询参数，在页面加载时运行  
function zjReloadgrid() {  
	$('#stdLiDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	$('#stdLiDg').datagrid('options').queryParams= serializeFormToJSON($("#stdlistFM").serializeArray());  
	$("#stdLiDg").datagrid('reload');
}  

function openAddstd(){ 
		$('#_loadDialog_stdList').dialog({    
			title: '津补贴标准录入',    
			width: '99%',    
			height: 500,    
			closed: false,    
			cache: false,    
	    	maximizable:true,
	    	href: ctx+'reward/standardEdit/-9',    
	    	modal: true   
		});
}
 

function openEditstd(){
	var row = getSingleTreeGridSelectData($("#stdLiDg"));
	if(row !=null){
		$('#_loadDialog_stdList').dialog({    
		    title: '津补贴标准编辑',    
		    width: '99%',    
		    height: 500,    
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: ctx+'reward/standardEdit/' + row.id,    
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
	var row = getSingleTreeGridSelectData($("#stdLiDg"));
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
	var row = getSingleTreeGridSelectData($("#stdLiDg"));
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
	var row = getSingleTreeGridSelectData($("#stdLiDg"));
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