/**
 * 
 */
function deleteDict(){	 
	var row = getSingleTreeGridSelectData($("#dataDictTreeGd"));
	if(row !=null){			
		var url = ctx + "getMicroServiceResult/v1/sys-proj/dict/dict,"+row.id ;
		$.get(url,function(data){
			if(data.canDel=="0"){
				promptMessage("1","不允许删除本节点");
			}	else{
				promptMessageCallBack("3","是否确认删除该记录",function(){
					$.ajax({
						type : 'DELETE',
						url : ctx + "deleteMicroServiceResult/v1/sys-proj/dict/dict,"+row.id,
						data : {},
						success : function(data) {
                            $('#dataDictTreeGd').treegrid('reload');
							promptMessage(data.res, data.msg );
						},
						dataType : "JSON"
					});
				});
			}	
		});
	}
}

function addDictDlg(){
	var row = getSingleTreeGridSelectData($("#dataDictTreeGd"));
	if(row !=null){
        var url = ctx + "getMicroServiceResult/v1/sys-proj/dict/dict,"+row.id ;
		$.get(url,function(data){
			if(data.canAdd=="0"){
				promptMessage("1","不允许增加子节点");		
			}	else{
				$('#_loadDialog_dictList').dialog({    
				    title: '新增数据字典',    
				    width: 800,    
				    height: 500,    
				    closed: false,    
				    cache: false,    
				    maximizable:true,
				    href: ctx+'towardMicroServicePage/v1/sys-proj/dict,openDictAddPage,'+row.id+'/sys,pc,system,dict,dictAdd',
				    modal: true
				}); 

			}	
		});
	}
}

function editDictDlg(){
	var row = getSingleTreeGridSelectData($("#dataDictTreeGd"));
	if(row !=null){				
		var url = ctx + "getMicroServiceResult/v1/sys-proj/dict/dict,"+row.id ;
		$.get(url,function(data){
			if(data.canEdit=="0"){
				promptMessage("1","不允许修改此节点");		
			}	else{
				$('#_loadDialog_dictList').dialog({    
				    title: '编辑数据字典',    
				    width: 800,    
				    height: 500,    
				    closed: false,    
				    cache: false,    
				    maximizable:true,
				    href: ctx+'towardMicroServicePage/v1/sys-proj/dict,openDictEditPage,'+row.id+'/sys,pc,system,dict,dictEdit',
				    modal: true   
				}); 

			}	
		});
	}

	
}

