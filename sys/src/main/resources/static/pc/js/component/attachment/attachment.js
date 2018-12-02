
//上传文件触发上传文件方法
function up(foregin_id){
    var file = $("#file")[0].files[0]; //取值
    if($('#file').val().length>0){
    	ajaxFileUpload(foregin_id,$("#fileLi"))
    }
}

/****
 * 显示附件列表
 * @param obj 附件占位UL
 * @param data 附件数据
 * @param canDel 删除权限 boolean 不写默认false
 * @param canDown 下载权限 boolean 不写默认false
 * @returns
 */
function showFiles(obj,data,canDel,canDown){
	obj.empty();
	if(data){
		$.each(data,function(i,item){
			var tmpName = item.name;
			if (tmpName.length > 20) {
				tmpName = tmpName.substring(0,18)+"...";
			}
			var str = "<li>"+tmpName;
			if(canDel){
				str += "<i title='下载' onclick='downloadFile(\""+item.id+"\")' style='padding-right: 10px;cursor:pointer;float: right;' class='icon iconfont'>&#xe670;</i>";
			}
			if(canDown){
				str += "<i title='删除' onclick='deleteFile(\""+item.id+"\",\""+item.foreignId+"\",this)' style='padding-right: 10px;cursor:pointer;float: right;' class='icon iconfont'>&#xe60f;</i>";
			}
			str +=  "</li><hr>";
			obj.append(str);
		});
	}
}
/*****
 * 删除附件
 * @param fileId 文件id
 * @param fid 文件关联表id
 * @param obj 文件列表占位UL
 * @returns
 */
function deleteFile(fileId,fid,tmp){
	var obj = $(tmp).parents("ul");
	$.ajax({
		type : 'DELETE',
		url : ctx + "attachment/deleteFile/"+fileId+"/"+fid,
		success : function(data) {
			promptMessageCallBack(data.res, data.msg);
			if(data.res!="1"){
				showFiles(obj,data.rows,true,true);
			}
		},
		dataType : "JSON",
		error:function(jqXHR, textStatus, errorThrown){
			promptMessage("1",errorThrown);
		}
	});
}

/****
 * 下载文件
 * @param fileId 文件id
 * @returns
 */
function downloadFile(fileId){
	window.location.href = ctx + "attachment/downloadFile/"+fileId+"/"+false;
}

/****
 * 附件上传,并展示在对应展位UL内
 * @param foregin_id
 * @param obj ul标签
 */
function ajaxFileUpload(foregin_id,obj) {
	$.ajaxFileUpload({
		url : ctx+'attachment/attachment_upload_multi/'+foregin_id,// 用于文件上传的服务器端请求地址
		secureuri : false,// 一般设置为false
		fileElementId : 'file',// 文件上传空间的id属性 <input type="file" id="file" // name="file" />
		dataType : 'text',// 返回值类型 一般设置为json
		success : function(d, status) // 服务器成功响应处理函数
		{  
			//兼容ie浏览器
			var userAgent = navigator.userAgent;
			if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        		data = $.parseJSON(d);
        	}else if (userAgent.indexOf("Trident") > -1 && userAgent.indexOf("rv:11.0") > -1) {//判断是否IE11浏览器   
        		data = $.parseJSON(d);
        	}else{
        		data = jQuery.parseJSON(jQuery(d).text());
        	} 
			promptMessageCallBack(data.res, data.msg);
			if(data.res!="1"){
				showFiles(obj,data.rows,true,true);
			}
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{ 
			promptMessage("1",e.message);
		}
	}); 
}

/*****
 * 根据外键查询所有有效附件，并渲染到UL内，并有相关操作权限
 * @param foreignid 
 * @param obj  UL对象
 * @param canDel
 * @param canDown
 */
function getAttachments(foreignid,obj,canDel,canDown){
	$.ajax({
		type : 'GET',
		url : ctx + "attachment/attachments/"+foreignid,
		success : function(data) {
			showFiles(obj,data.rows,canDel,canDown);
		},
		dataType : "JSON",
		error:function(jqXHR, textStatus, errorThrown){
			promptMessage("1",errorThrown);
		}
	});
}

$(function(){
	getAttachments('79a4724f04d144589923511cdc6eb104',$("#fileLi"),true,true);
});