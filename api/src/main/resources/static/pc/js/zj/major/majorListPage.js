/**
 * 
 */

$(function(){
	majorlistquery();
});


function majorlistquery(){
	$('#majorLiDg').treegrid({    
	    url:ctx+'major/majors/0' ,    
	    idField:'id',    
	    treeField:'name',
	    method:'get',
	    rownumbers: true,
	    columns:[[    
	    	{ field: 'id', title: 'ID' ,hidden:true  },
            { field: 'name', title: '名称', width: '30%',fixed:true  },
            { field: 'status', title: '状态', width: '30%' }, 
            { field: 'desp', title: '描述', width: '38%'  }    
	    ]],
	    onDblClickRow: function (row) { 
       	   openMajorDetail(row.id); 
        }
	}); 
}

/***
 * 查看专业详情
 * @param id
 * @returns
 */
function openMajorDetail(id){
	$('#_loadDialog_majorList').dialog({    
	    title: '专业详情',    
	    width: '99%', 
	    height: 450,    
	    closed: false,    
	    cache: false,    
	    maximizable:true,
	    href: ctx+'major/major/'+id,    
	    modal: true   
	}); 
}

//增加查询参数，在页面加载时运行  
function positionReloadgrid() { 
	$('#positionLiDg').datagrid('loadData',{total:0,rows:[]}); //清空DataGrid行数据
	positionlistquery();
}  

function changePlate(falg,pId,tmp){
	$('#plates').combogrid({
		idField: 'dict_value',
		valueField: 'dict_value',
		textField: 'dict_name',
		multiple:true,
		url: ctx +'major/getDictsByParentValue/plate/'+pId,
		method: 'get', 
		columns: [[
			{field:'dict_name',title:'板块名称',width:'100%'} 
		]],
		fitColumns: true,
		required:true
	});
	if(falg == 'update'){
		$('#plates').combogrid({
			onLoadSuccess:function(){
	            $("#plates").combogrid("setValues",tmp);
	        }	
		});
	}
	if(falg == 'clear'){
		$('#plates').combogrid({
			onLoadSuccess:function(){
	            $("#plates").combogrid("clear");
	        }	
		});
	}
}

function changeMajorStatus(){
	var row = getSingleGridSelectData($("#majorLiDg"));
	if(row !=null){
		var status;
		var message;
		if('正常'==row.status){
			status = 'frozen';
			message = '当前状态为正常,是否冻结该专业?';
		}else{
			status = 'normalsta';
			message = '当前状态为冻结,是否解冻该专业?';
		}
		promptMessageCallBack("3",message,function(){					
			$.ajax({
				type : 'PUT',
				url : ctx + "major/changeStatus/"+row.id+"/"+row.parent_id+"/"+status,
				data : {},
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
						 $('#majorLiDg').treegrid('reload');  
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

function openAddMajorPage(){
	var row = getSingleGridSelectData($("#majorLiDg"));
	if(row !=null){
		if("正常"!=row.status){
			promptMessageCallBack(1, "请解冻后再新增!");
		}else{
			$('#_loadDialog_majorList').dialog({    
			    title: '新增专业',    
			    width: 800,    
			    height: 400,    
			    closed: false,    
			    cache: false,    
			    maximizable:true,
			    href: ctx+'system/gotoIframePage/zj/major/majorAddPage/'+row.id,    
			    modal: true   
			});
		}
	}
}

function openEditPosition(){
	var row = getSingleGridSelectData($("#majorLiDg"));
	if(row !=null){
		if(row.parent_id == ''){
			promptMessageCallBack(1, "最顶层节点不可编辑!");
			return;
		}
		if("正常"!=row.status){
			promptMessageCallBack(1, "请解冻后再编辑!");
		}else{
			$('#_loadDialog_majorList').dialog({     
			    title: '编辑专业',    
			    width: 800,    
			    height: 400,    
			    closed: false,    
			    cache: false,    
			    maximizable:true,
			    href: ctx+'major/edit/'+row.id,    
			    modal: true   
			});
		}
	}
}

function deleteMajor(){
	var row = getSingleGridSelectData($("#majorLiDg"));
	if(row !=null){
		if(row.leaf){
			$.ajax({
				type : 'DELETE',
				url : ctx + "major/major/"+row.id,
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(){
						 $('#majorLiDg').treegrid('reload');  
					} );
				},
				dataType : "JSON",
				error:function(jqXHR, textStatus, errorThrown){
					promptMessage("1",errorThrown);
				}
			});
		}else{
			promptMessageCallBack(1, "请从叶子节点开始删除!");
		}
	}
}

/*专家迁移*/
function moveZJ(){
	var row = getSingleGridSelectData($("#majorLiDg"));
	if(row !=null){
		$('#_loadDialog_majorList').dialog({    
		    title: '专家迁移',    
		    width: 800,    
		    height: 400,    
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: ctx+'system/gotoIframePage/zj/major/moveZjPage/'+row.id,    
		    modal: true
		});
	}
}