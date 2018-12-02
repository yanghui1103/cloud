$(function(){
	wdllistquery();
});

function wdllistquery(){   
	$('#wdlLiDg').datagrid({ 
		pagination:true,
		method:"get",
	    url:ctx+'flow/todos' , 
        queryParams:   ($("#wdllistFM").serialize()),
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'taskId', title: 'taskId' ,hidden:true  },
                   { field: 'title', title: '标题', width: '50%',fixed:true  },
                   { field: 'creator', title: '发起人', width: '10%' },
                   { field: 'createTime', title: '流转时间', width: '20%' } 
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
            	 openwdlDetail(row.id,row.taskId); 
             }     
	});  
}

//增加查询参数，在页面加载时运行  
function wdlReloadgrid() {  
	$('#wdlLiDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	$('#wdlLiDg').datagrid('options').queryParams= serializeFormToJSON($("#wdllistFM").serializeArray());  
	$("#wdlLiDg").datagrid('reload');
}  

function openwdlDetail(id,taskId){
	$('#_loadDialog_wdlList').dialog({    
	    title: '审核处理页',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'zj/audit/'+id+"/"+taskId,    
	    modal: true   
	});
}