$(function(){


    query();
});

function query(){
    $('#logList').datagrid({
        pagination:true,
        method:"get",
        url: ctx+'getMicroServiceResult/v3/common-proj/log/log' ,
        queryParams:  serializeFormToJSON($("#requestDgFm").serializeArray()),
        remoteSort: false,
        columns: [[
            { field: 'id', title: 'ID' ,hidden:true  },
            { field: 'creator', title: '操作者', width: '10%'   },
            { field: 'ip', title: 'IP'  , width: '20%' },
            { field: 'url', title: 'url'  , width: '20%' },
            { field: 'logType', title: '日志类型' , width: '20%'  },
            { field: 'result', title: '返回结果'  , width: '20%' },
            { field: 'createTime', title: '采集时间'  , width: '10%' }
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
    $('#_loadDialog_loglist').dialog({
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