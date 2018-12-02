/**
 * 评分项管理
 * @author jiaw
 * 2018-10-11
 */

//打开新增模板页
function openAddTemplet(){
	$('#_loadDialog_templetList').dialog({    
	    title: '新增模板',    
	    width: '60%',    
	    height: 460,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'system/gotoIframePage/later/score/templetAddPage/-9',    
	    modal: true   
	}); 
}

//查询模板列表
function templetListquery(){   
	$('#templetLiDg').datagrid({ 
		pagination:true,
		method:"get",
	    url:ctx+'templet/templets', 
        queryParams:   serializeFormToJSON($("#templetlistFM").serializeArray()), 
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'templetName', title: '模板名称', width: '30%',fixed:true  },
                   { field: 'useType', title: '使用类型', width: '30%'  },
                   { field: 'creator', title: '创建人', width: '15%'  },
                   { field: 'createTime', title: '创建时间', width: '25%'  }
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
            	 openTempletDetail(row.id); 
             }
	});  
}

//模板删除
function deleteTemplet(){
	var row = getSingleGridSelectData($("#templetLiDg"));
	if(row !=null){
		promptMessageCallBack("3","是否确认删除该模板?",function(){
			$.ajax({
				type : 'DELETE',
				url : ctx + "templet/templet/"+row.id,
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
						 $('#templetLiDg').datagrid('reload');  
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

/***
 * 查看详情
 * @param id
 * @returns
 */
function openTempletDetail(id){
	$('#_loadDialog_templetList').dialog({    
	    title: '模板详情',    
	    width: '60%', 
	    height: 450,
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'templet/detail/'+id,    
	    modal: true   
	}); 
}

//打开模板编辑页
function editTemplet(id){
	var row = getSingleGridSelectData($("#templetLiDg"));
	if(row !=null){
		$('#_loadDialog_templetList').dialog({    
		    title: '模板编辑',    
		    width: '60%', 
		    height: 450,
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: ctx+'templet/edit/'+row.id,    
		    modal: true   
		});
	}
	
}

$(function(){
	templetListquery();
});