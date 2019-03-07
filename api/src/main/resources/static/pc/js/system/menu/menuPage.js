/**
 * 组织管理JS
 */


		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		

		var zNodes = null ;

		function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
		}
		var lastValue = "", nodeList = [], fontCss = {};
		function clickRadio(e) {
			lastValue = "";
			searchNode(e);
		}

		
		function searchNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			if (!$("#getNodesByFilter").attr("checked")) {
				var value = $.trim(key.get(0).value);
				var keyType = "";
				if ($("#name").attr("checked")) {
					keyType = "name";
				} else if ($("#level").attr("checked")) {
					keyType = "level";
					value = parseInt(value);
				} else if ($("#id").attr("checked")) {
					keyType = "id";
					value = parseInt(value);
				}
				if (key.hasClass("empty")) {
					value = "";
				}
				if (lastValue === value) return;
				lastValue = value;
				if (value === "") return;
				updateNodes(false);

				if ($("#getNodeByParam").attr("checked")) {
					var node = zTree.getNodeByParam(keyType, value);
					if (node === null) {
						nodeList = [];
					} else {
						nodeList = [node];
					}
				} else if ($("#getNodesByParam").attr("checked")) {
					nodeList = zTree.getNodesByParam(keyType, value);
				} else if ($("#getNodesByParamFuzzy").attr("checked")) {
					nodeList = zTree.getNodesByParamFuzzy(keyType, value);
				}
			} else {
				updateNodes(false);
				nodeList = zTree.getNodesByFilter(filter);
			}
			updateNodes(true);

		}
		function updateNodes(highlight) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			for( var i=0, l=nodeList.length; i<l; i++) {
				nodeList[i].highlight = highlight;
				zTree.updateNode(nodeList[i]);
			}
		}
		function getFontCss(treeId, treeNode) {
			return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
		}
		function filter(node) {
			return !node.isParent && node.isFirstNode;
		}

		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"),
			
			type = { "Y":"ps", "N":"ps"};
			zTree.setting.check.chkboxType = type; 
		}
		
		var key;
		$(document).ready(function(){					
					$.get(ctx+"getMicroServiceResult/v2/sys-proj/menu/menus,"+$("input[name='temp_str1']").val() ,function(data){
							zNodes = data  ;
							$.fn.zTree.init($("#menuTree"), setting, zNodes);
							setCheck(); 
					});
		});
		
		
 
			function addRole2Menus(){
				var treeObj=  $.fn.zTree.getZTreeObj("menuTree");
				var nodes=treeObj.getCheckedNodes(true);
				var ids = "";
				for(var i=0;i<nodes.length;i++){  
					ids = ids +nodes[i].id ;
					ids = ids + ",";
				}
				$("input[name='menus']").val(ids);
				$.ajax({
					url: ctx + "updateMicroServiceResult/v1/sys-proj/role/role2Menu/" + transferFormToString($("#role2menuFm")),
					data: {},
					type:'put',
					success:function(data){
						promptMessage(data.res,data.msg);
					}
				});
			}
