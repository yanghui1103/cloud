/**
 * 评分管理-打分
 * @author jiaw
 * 2018-10-16
 */
$(function(){
	var planId = $("#planId").val();
	getZjs(planId);
})

function getZjs(id){
	$('#zjScoreLiDg').datagrid({    
		url:ctx+'score/zjs/'+id,
		method:"get",
	    autoRowHeight:false,
	    toolbar:'#scorelisttb',
        fitColumns:true,
        striped:true,
        rownumbers:true,
        checkOnSelect:true,
        selectOnCheck:true,
        collapsible:true,
	    columns:[[    
	        {field:'zjId',hidden:true},    
	        {field:'temp_str1',title:'姓名',width:'15%'},    
	        {field:'temp_str2',title:'身份证号码',width:'20%'},    
	        {field:'temp_str3',title:'专家级别',width:'15%'},    
	        {field:'creator',title:'抽取者',width:'15%'},    
	        {field:'createTime',title:'抽取时间',width:'15%'},    
	        {field:'temp_str4',title:'得分',width:'15%'}
	    ]]   
	}); 
}

function scoreToZj(){
	var row = getSingleGridSelectData($("#zjScoreLiDg"));
	var planId = $("#planId").val();
	if(row !=null){
		$('#_loadDialog_score_itemList').dialog({    
		    title: '评分',    
		    width: '60%',    
		    height: 460,    
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: ctx+'score/score/'+row.zjId+'/'+planId,    
		    modal: true   
		});
	}
}