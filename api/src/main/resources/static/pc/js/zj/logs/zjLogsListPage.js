/**
 * 专家管理
 * @author yangh
 * 2018-9-11
 */
$(function(){
	zjlogslistquery();
});

function zjlogslistquery(){   
	$('#zjLogsLiDg').datagrid({ 
		pagination:true,
		method:"get",
	    url:ctx+'zjLog/zjLogs' ,
        queryParams:   serializeFormToJSON($("#zjLogslistFM").serializeArray()),
	    remoteSort: false, 
        columns: [[
                   { field: 'id', title: 'ID' ,hidden:true  },
                   { field: 'name', title: '名称', width: '20%',fixed:true  },
                   { field: 'gender', title: '性别', width: '10%' },
                   { field: 'isInner', title: '是否内部专家', width: '20%' }, 
                   { field: 'grade', title: '专家级别', width: '20%' }, 
                   { field: 'temp_str2', title: '使用类型', width: '20%'  }, 
                   { field: 'coType', title: '操作类型', width: '10%'  }, 
                   { field: 'createTime', title: '操作类型', width: '10%'  }
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
             striped: true
	});  
}

//增加查询参数，在页面加载时运行  
function zjLogsReloadgrid() {
	$('#zjLogsLiDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	$('#zjLogsLiDg').datagrid('options').queryParams= serializeFormToJSON($("#zjLogslistFM").serializeArray());  
	$("#zjLogsLiDg").datagrid('reload');
}  
