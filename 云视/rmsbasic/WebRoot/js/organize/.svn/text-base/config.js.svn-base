$(document).ready(function() {
	showConfigList();
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showConfigList();
	     }
   	}; 
});

/**
 * 加载客户列表
 */
function showConfigList(a,b) {
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/rms/custom/getConfiguration/",
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data));
				initSelectAll();
			} else {
				$("#table_body").html(loadTableData(data.data));
			}
		},
		error : function() {
			layer.close(index);
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * 全选相关的操作
 */
function initSelectAll(){
	 //全选或全不选 
     $("#all").click(function(){  
        if(this.checked){  
            $("#table_body :checkbox").prop('checked',true);   
        }else{    
            $("#table_body :checkbox").prop('checked',false);
        }    
     }); 
     $("input[name='role_name']").click(function () {
                $("input[name='role_name']:checked").length == $("input[name='role_name']").length ? $("#all").prop("checked", true) : $("#all").prop("checked", false);
     });

}
function loadTableData(data){
	var tableBodyData = [];
					tableBodyData.push("<table border='0' cellpadding='0' cellspacing='0'>");
					tableBodyData.push("<tr>");
					tableBodyData.push("<th>globalKey</th>");
					tableBodyData.push("<th>globalValue</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$.each(data,function(i, item) {
			if(i=="_id"){
				$("#globalId").val(item);
				return ;
			}
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + i + "</td>");
					tableBodyData.push("<td>" +item + "</td>");
					tableBodyData.push("<td><a href='javascript:;' onclick='openUpdateConfig(this)' confkey='"+i+"' confvalue='"+item+"' >编辑</a><a href='javascript:deleteConfig(\""+ i + "\",\""+ item + "\");' class='td_del'>删除</a></td>");
					tableBodyData.push("</tr>");
				});
				tableBodyData.push("</tbody>");
				tableBodyData.push("</table>");
	return tableBodyData.join("");

}

function toString(str){
	if(str!=undefined){
		return str;
	}else{
		return "";
	}
}


/**
 * 删除配置项
 */
function deleteConfig(key,value) {

	 layer.confirm("确定要删除配置吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("globalKey", key);
			onairMap.put("globalValue", value);
			onairMap.put("_id", $("#globalId").val());
			 $.ajax({
		        'url':ctx  + '/rms/custom/deleteConfiguration/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除配置项成功！');
						layer.closeAll('page');
						showConfigList();
					} else {
						layer.msg('删除配置项失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}
//弹出新增配置窗口
function openAddConfig(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/config_add",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增配置项",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
				maxmin : true,
				scrollbar : false,
				//offset: ['300px', '200px'],
				content : data
			});
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * 新增配置
 */
function addConfig() {
	var onairMap = new OnairHashMap();
	onairMap.put("globalKey", $("#globalKey").val());
	onairMap.put("globalValue", $("#globalValue").val());
	onairMap.put("_id", $("#globalId").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/rms/custom/updateConfiguration/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加配置项成功！');
				layer.closeAll('page');
				showConfigList();
			} else {
				layer.msg('添加配置项失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
//弹出修改配置窗口
function openUpdateConfig(This){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/config_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改客户", data);
			$("#globalKey2").val($(This).attr("confkey"))
			$("#globalValue2").val($(This).attr("confvalue"))
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 修改配置项
 */
function updateConfig(customId) {
	var onairMap = new OnairHashMap();
	onairMap.put("globalKey", $("#globalKey2").val());
	onairMap.put("globalValue", $("#globalValue2").val());
	onairMap.put("_id", $("#globalId").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/rms/custom/updateConfiguration/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改配置项成功！');
				layer.closeAll('page');
				showConfigList();
			} else {
				layer.msg('修改配置项失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
