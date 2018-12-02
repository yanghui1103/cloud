var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#dg').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		//$('#dg').datagrid('acceptChanges');
		$('#dg').datagrid('appendRow',{});
		editIndex = $('#dg').datagrid('getRows').length-1;
		$('#dg').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#dg').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}

function accept(){
	if (endEditing()){
		var rows = $('#dg').datagrid('getRows');
		var sum = 0;
		for(var i=0;i<rows.length;i++){
			sum += parseFloat(rows[i]['weight']);
		}
		if(parseFloat(sum)<=1){
			$('#dg').datagrid('acceptChanges');
			//保存表单的方法
			if (!$("#templetAddFm").form('enableValidation')
					.form('validate')) {
				return;
			}
			var jsondata = serializeFormToJSON($("#templetAddFm").serializeArray());
			jsondata.temp_str1 = JSON.stringify(rows);
			$.ajax({
				type : 'POST',
				url : ctx + "templet/templet" ,
				data :jsondata, 
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(data){
						$('#_loadDialog_templetList').dialog('close');
						$('#templetLiDg').datagrid('reload');
					});
				},
				dataType : "JSON",
				error:function(e){
					promptMessage("1",e);
				}
			});
		}else{
			promptMessage("1","权重总和不能大于1");
			editIndex=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
			$('#dg').datagrid('selectRow', editIndex)
			.datagrid('beginEdit', editIndex);
		}
	}
}


function update(){
	if (endEditing()){
		var rows = $('#dg').datagrid('getRows');
		var sum = 0;
		for(var i=0;i<rows.length;i++){
			sum += parseFloat(rows[i]['weight']);
		}
		if(parseFloat(sum)<=1){
			$('#dg').datagrid('acceptChanges');
			//保存表单的方法
			if (!$("#templetAddFm").form('enableValidation')
					.form('validate')) {
				return;
			}
			var jsondata = serializeFormToJSON($("#templetAddFm").serializeArray());
			jsondata.temp_str1 = JSON.stringify(rows);
			console.log(rows);
			$.ajax({
				type : 'PUT',
				url : ctx + "templet/templet" ,
				data :jsondata, 
				success : function(data) {
					promptMessageCallBack(data.res, data.msg,function(data){
						$('#_loadDialog_templetList').dialog('close');
						$('#templetLiDg').datagrid('reload');
					});
				},
				dataType : "JSON",
				error:function(e){
					promptMessage("1",e);
				}
			});
		}else{
			promptMessage("1","权重总和不能大于1");
			editIndex=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
			$('#dg').datagrid('selectRow', editIndex)
			.datagrid('beginEdit', editIndex);
		}
	}
}
