/**
 * 评分管理-计划单
 * @author jiaw
 * 2018-10-16
 */
$(function(){
	planlistquery();
});

function planlistquery(){   
	$('#planLiDg').datagrid({ 
		pagination:true,
		method:"get",
	    url:ctx+'plan/plans' , 
        queryParams:   ($("#planlistFM").serialize()),
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'isAudit', title: 'isAudit' ,hidden:true  },
                   { field: 'name', title: '计划单名称', width: '30%',fixed:true  },
                   { field: 'projCode', title: '项目编码', width: '10%' },
                   { field: 'projName', title: '项目名称', width: '20%' }, 
                   { field: 'useDate', title: '使用日期', width: '20%' }, 
                   { field: 'useAddress', title: '使用地点', width: '20%' }, 
                   { field: 'creator', title: '创建者', width: '10%'  }
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
            	 openDetail(row.id); 
             }     
	});  
}




//增加查询参数，在页面加载时运行  
function planReloadgrid() {  
	$('#planLiDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	$('#planLiDg').datagrid('options').queryParams= serializeFormToJSON($("#planlistFM").serializeArray());  
	$("#planLiDg").datagrid('reload');
}  

function openDetail(id){
	$('#_loadDialog_planList').dialog({    
	    title: '计划单详情',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'plan/plan/'+id ,    
	    modal: true   
	}); 	
	
}

function score(){
	var row = getSingleGridSelectData($("#planLiDg"));
	if(row !=null){
		$('#_loadDialog_selectTemp').dialog({    
		    title: '选择模板',    
		    width: '60%',    
		    height: 460,    
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: ctx+'templet/selectTemplet/'+row.id,    
		    modal: true   
		});
	}
}