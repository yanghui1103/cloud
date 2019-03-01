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

function openDetail(id) {
    $('#_loadDialog_requestlist').dialog({
        title: '日志详情',
        width: '99%',
        height: 500,
        closed: false,
        cache: false,
        maximizable:true,
        href: ctx+'towardMicroServicePage/v1/common-proj/log,log,'+id+'/common,pc,base,log,logDetail' ,
        modal: true
    });
}