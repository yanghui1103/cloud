$(function(){
    query();
});

function query(){
    $('#innermsgList').datagrid({
        pagination:true,
        method:"get",
        url: ctx+'getMicroServiceResult/v1/common-proj/inform/innerMsg' ,
        queryParams:   {},
        remoteSort: false,
        columns: [[
            { field: 'id', title: 'ID' ,hidden:true  },
            { field: 'title', title: '主题', width: '40%'   },
            { field: 'sender', title: '发送者'  , width: '20%' },
            { field: 'createTime', title: '发送时间'  , width: '20%' },
            { field: 'isRead', title: '是否已阅' , width: '20%'  }
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
    $('#_loadDiag_innermsglist').dialog({
        title: '详情',
        width: '99%',
        height: 500,
        closed: false,
        cache: false,
        maximizable:true,
        href: ctx+'towardMicroServicePage/v1/common-proj/log,log,'+id+'/common,pc,base,log,logDetail' ,
        modal: true
    });
}