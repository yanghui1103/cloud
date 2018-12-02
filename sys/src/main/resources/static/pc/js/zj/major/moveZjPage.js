/***
 * 专业详情js
 * @author jiaw
 * 2018-9-12
 */


$(function(){
	getParentTree();
	zjlist();
});

function move(){
	var majors = $("#parentTree").combotree("getValues");
	if(majors.length==0){
		promptMessage("3","请选择要迁移到哪些专业");
		return;
	}
	var rows = $('#zjListData').datagrid('getChecked');
	if(rows.length==0){
		promptMessage("3","请选择要迁移的专家");
		return;
	}
	var oldMajorId = $("#id").val();
	var zjs = [];
	$.each(rows,function(i,d){
		zjs.push(d.id);
	});
	$.ajax({
		type : 'PUT',
		url : ctx + "major/moveZj/"+zjs+"/"+majors+"/"+oldMajorId,
		success : function(data) {
			promptMessageCallBack(data.res, data.msg,function(){
				$('#_loadDialog_majorList').dialog('close');
			});
		},
		dataType : "JSON",
		error:function(jqXHR, textStatus, errorThrown){
			promptMessage("1",errorThrown);
		}
	});
}

function zjlist(){  
	$('#zjListData').datagrid({ 
		pagination:true,
		method:"get",
		queryParams:   serializeFormToJSON($("#zjlistDataFM").serializeArray()),
	    url:ctx+'major/zjs',   
	    remoteSort: false,checkOnSelect: true, selectOnCheck: false,
        columns: [[
        			{ field: 'ck', checkbox : true  },
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
             striped: true 
	});  
}

function getParentTree(){
	$('#parentTree').combotree({
		url:ctx +'major/getMajorTree/0',
		method:'get',
		value:$("#parent_id").val(),
		idField: 'id',
		textField: 'name',
		fitColumns: true,
		required:true,
		multiple: true,
		onlyLeafCheck:true
	});
}