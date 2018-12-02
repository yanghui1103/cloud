/**
 * 
 */

function initSelectDg() {
	var  arr = $("#slplatte").combogrid("getValues");
	$("input[name='temp_str1']").val(castArrayToStr(arr));
	//var  arr = $("#slmajor").combotree("getValues");
	//$("input[name='temp_str2']").val(castArrayToStr(arr));
	var  arr = $("#sluseType").combogrid("getValues");
	$("input[name='temp_str3']").val(castArrayToStr(arr));
	var  arr = $("#slfirstEdu").combogrid("getValues");
	$("input[name='temp_str4']").val(castArrayToStr(arr));
	var  arr = $("#slhlEdu").combogrid("getValues");
	$("input[name='temp_str5']").val(castArrayToStr(arr));
	var  arr = $("#slhlEdu").combogrid("getValues");
	$("input[name='temp_str6']").val(castArrayToStr(arr));
	var  arr = $("#slplevel").combogrid("getValues");
	$("input[name='temp_str8']").val(castArrayToStr(arr));
	var  arr = $("#slmajorgrade").combogrid("getValues");
	$("input[name='temp_str9']").val(castArrayToStr(arr));
//	var innerzj =   $("#isInner").combogrid("getValue");
//	if( innerzj =='outerzj'){
//		$(".clsZj").textbox('setValue','')
//	}
	

	if (!$("#selectFm").form('enableValidation')
			.form('validate')) {
		return;
	}else{
		$('#slslZjLiDg').datagrid({
			pagination : true,
			method : "get",
			url : ctx + 'zj/zjs',
			queryParams :  serializeFormToJSON($("#selectFm").serializeArray()),
			remoteSort : false,
			columns : [ [ {
				field : 'id',
				title : 'ID',
				hidden : true
			}, {
				field : 'name',
				title : '名称',
				width : '20%',
				fixed : true
			}, {
				field : 'gender',
				title : '性别',
				width : '10%'
			}, {
				field : 'isInner',
				title : '是否内部专家',
				width : '20%'
			}, {
				field : 'grade',
				title : '专家级别',
				width : '20%'
			}, {
				field : 'temp_str2',
				title : '使用类型',
				width : '20%'
			} ,{field:'opt',title:'操作',width:50,align:'center',  
	            formatter:function(value,row,index){  
	                var btn = "<a class=editcls href=javascript:personSelect('"+row.id+"')></a>";  
	                if($("#PSNSelect").length>0){
	                	return btn;
	                }
	            }  
	        }  
			
			] ],
			 onLoadSuccess:function(data){  
			        $('.editcls').linkbutton({text:'选取',plain:true});  
			    }  ,
			fit : false,
			idField : "id",
			pagination : true,
			singleSelect : true,
			rownumbers : true,
			fitColumns : true,
			pageNumber : 1,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			striped : true, // 奇偶行是否区分
			onDblClickRow : function(index, row) {
				openzjDetail(row.id);
			}
		});
	}
}

function openzjDetail(id) {
	$('#_loadDialog_selectList').dialog({
		title : '专家详情',
		width : '99%',
		height : 500,
		closed : false,
		cache : false,
		maximizable : true,
		href : ctx + 'zj/zj/' + id,
		modal : true
	});
}

$(function() {
	$(".easyui-combogrid").combogrid({
		onSelect : function(index, row) {
			initSelectDg();
		},
		onUnselect:function(){ 
			initSelectDg();
		}
	});

	$(".address-select-position").textbox(
			{
				icons : [ {
					iconCls : 'icon-search',
					handler : function(e) {
						openAddress($("#_loadDialog_address"),
								$("input[name='positionIds']"),
								$(".address-select-position"), "P", true);
					}
				} ]
			});

	$(".address-select-org").textbox(
			{
				icons : [ {
					iconCls : 'icon-search',
					handler : function(e) {
						openAddress($("#_loadDialog_address"),
								$("input[name='belongCompany']"),
								$(".address-select-org"), "O", true);
					}
				} ]
			});
	initSelectDg();

});


function rabioSelect(){
	 var zjIds = "";
	 var rows = $("#slslZjLiDg").datagrid("getRows"); 
	 for(var i=0;i<rows.length;i++)
	 {
		 zjIds = zjIds + rows[i].id +",";
	 }
	 if(rows.length <1){
		 promptMessageCallBack("1", "没有专家可供机选!请重新选择帅选条件",function(){
			} );
		 return ;
	 }
	 var rabioNumber = $("#rabioNumber").val();
	 if(rabioNumber ==null || rabioNumber==''){
		 promptMessageCallBack("1", "请输入机选人数",function(){
			} );
		 return ;
		 
	 }
	 var planId = $("#id").val();
		$('#_loadDialog_planList2').dialog({    
		    title: '机选出来的专家列表',    
		    width: '80%',    
		    height: 500,    
		    closed: false,    
		    cache: false,    
		    maximizable:false,
		    closable:false,
		    href: ctx+'zj/rabioSelect/'+zjIds+'/'+rabioNumber+'/'+planId ,  
		    modal: true   
		}); 	
}


function personSelect(zjId){
	 var planId = $("#id").val();
		$.ajax({
			type : 'POST',
			url : ctx + "select/select2/"+zjId+"/"+planId ,
			data :  {},
			success : function(data) {
				promptMessageCallBack(data.res, data.msg,function(){
				} );
			},
			dataType : "JSON"
		});
	
}