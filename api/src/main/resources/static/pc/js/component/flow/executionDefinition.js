
$(function(){
    dataGridQuery();
});


function dataGridQuery(){
    $('#executionDefDg').datagrid({
        pagination:true,
        method:"get",
        url:ctx+'getMicroServiceResult/v1/flow2-proj/flow/processDefinition' ,
        remoteSort: false,
        columns: [[
            { field: 'temp_str2',hidden:true  },
            { field: 'id', hidden:true  },
            { field: 'name', title: '流程名称', width: '20%',fixed:true  },
            { field: 'instId', title: '实例ID', width: '10%',fixed:true  },
            { field: 'logName', title: '节点名称', width: '25%',sortable:true },
            { field: 'isdeleted', title: '已设定办理者', width: '25%' } ,
            { field: 'isdelete3d', title: '设定者', width: '10%' } ,
            { field: 'temp_str3', title: '设定时间', width: '10%' ,sortable:true}
        ]],
        fit: true ,
        idField: "temp_str1",
        pagination: true,
        singleSelect:true,
        rownumbers: true,
        fitColumns:true,
        pageNumber: 1,
        pageSize: 10,
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