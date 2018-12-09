/**
 * 专家管理
 * @author yangh
 * 2018-9-11
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


function openAddPlan(){
	$('#_loadDialog_planList').dialog({    
	    title: '新增抽取计划单',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'system/gotoIframePage/plan/admin/planAddPage/-9' ,    
	    modal: true   
	}); 	
}

function openZjSelect(){
	var row = getSingleTreeGridSelectData($("#planLiDg"));
	if(row !=null){
		if(row.isAudit !='1'){
			promptMessageCallBack("1", "计划单尚未审核完毕！不得抽取专家",function(){
			} );
		}else{
			$('#_loadDialog_planList').dialog({    
			    title: '抽取专家',    
			    width: '99%',    
			    height: 500,    
			    closed: false,    
			    cache: false,    
			    maximizable:true,
			    href: ctx+'system/gotoIframePage/plan/select/selectPage/'+row.id,  
			    modal: true   
			}); 	
		}
	}	
}


function deletePlan(){	 
	var row = getSingleTreeGridSelectData($("#planLiDg"));
	if(row !=null){
		promptMessageCallBack("3","是否确认删除该抽取计划单？",function(){					
			$.ajax({
				type : 'DELETE',
				url : ctx + "plan/plan/"+row.id,
				data : {},
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
						planReloadgrid();
					} );
				},
				dataType : "JSON"
			});
		});
	}
}

function openDetail(id){
	$('#_loadDialog_planList').dialog({    
	    title: '新增抽取计划单',    
	    width: '99%',    
	    height: 500,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'plan/plan/'+id ,    
	    modal: true   
	}); 	
	
}