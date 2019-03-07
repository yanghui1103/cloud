$(function(){

    query();
});

function query(){
    $('#requestDg').datagrid({
        pagination:true,
        method:"get",
        url: ctx+'getMicroServiceResult/v3/flow2-proj/flow/flow,drafter,'+ currentAccountId,
        queryParams:  serializeFormToJSON($("#requestDgFm").serializeArray()),
        remoteSort: false,
        columns: [[
            { field: 'id',  hidden:true  },
            { field: 'flowId',  hidden:true  },
            { field: 'title', title: '主题'  , width: '70%' },
            { field: 'drafter', title: '发起人', width: '10%'   },
            { field: 'createTime', title: '时间'  , width: '20%' }
        ]],
        fit: true ,
        idField: "id",
        pagination: true,
        singleSelect:true,
        rownumbers: true,
        pageNumber: 1,
        pageSize: 20,
        pageList: [ 10,20, 30, 40, 50],
        striped: true, //奇偶行是否区分
        onDblClickRow: function (index, row) {
            openDetail(row.id);
        }
    });
}


//增加查询参数，在页面加载时运行
function myRequestReloadgrid() {
    $('#requestDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
    $('#requestDg').datagrid('options').queryParams= serializeFormToJSON($("#requestDgFm").serializeArray());
    $("#requestDg").datagrid('reload');
}

function openDetail(id) {
    $('#_loadDialog_requestlist').dialog({
        title: '流程详情',
        width: '99%',
        height: 500,
        closed: false,
        cache: false,
        maximizable:true,
        href: ctx+'flowDetail/pdInstId/232501' ,
        modal: true
    });
}

function startFlowTest(){ // 我的申请页里发起流程的测试案例，当然也可以使用ajax方法data里绑定键值对。
    $.post(ctx+"addMicroServiceResult/v1/flow2-proj/flow/start,processDefinitionKey,test32,标题,002/-9",function(data){
        console.log(data);
        myRequestReloadgrid();
    });
}