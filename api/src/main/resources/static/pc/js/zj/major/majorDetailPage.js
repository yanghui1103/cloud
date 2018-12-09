/***
 * 专业详情js
 * @author jiaw
 * 2018-9-12
 */


$(function(){
	zjlist();
});

function zjlist(){  
	$('#zjListData').datagrid({ 
		pagination:true,
		method:"get",
		queryParams:   serializeFormToJSON($("#zjlistDataFM").serializeArray()),
	    url:ctx+'major/zjs',   
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'name', title: '名称', width: '15%',fixed:true  },
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
function openzjDetail(id){
	$('#_loadDialog_zjDetail').dialog({    
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