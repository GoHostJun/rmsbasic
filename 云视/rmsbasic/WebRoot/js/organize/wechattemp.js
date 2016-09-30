$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showWechattempList
	});
	showWechattempList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showWechattempList(1,10);
	     }
   	}; 
});

/**
 * 加载微信消息模板列表
 */
function showWechattempList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/api/wechattemplate/findAllWeChatTemplate/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data.results));
			} else {
				$("#table_body").html(loadTableData(data.data.results));
			}
			$("#pageDiv").resetOnairPageParameter(data.data.totalRecord, data.data.currentPage);
		},
		error : function() {
			layer.close(index);
			$("#table_body").html("查询出错了...");
		}
	});
}
function loadTableData(data){

	var tableBodyData = [];
	if (0 < data.length) {
					tableBodyData.push("<table border='0' cellpadding='0' cellspacing='0'>");
					tableBodyData.push("<tr>");
					tableBodyData.push("<th class='i_td'><i></i></th>");
					tableBodyData.push("<th>消息模板名称</th>");
					tableBodyData.push("<th>消息模板类型</th>");
					tableBodyData.push("<th>消息模板id</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td>" + toString(item.title) + "</td>");
					tableBodyData.push("<td>" +toString(item.type) + "</td>");
					tableBodyData.push("<td>" + toString(item.template_id) + "</td>");
					if(toString(item.userName)!="system"){
						tableBodyData.push("<td><a href='javascript:openUpdateWechattemp(\"" + item._id + "\");'>编辑</a><a href='javascript:deleteWechattemp(\""
								+ item._id + "\");' class='td_del'>删除</a></td>");
					}else{
						tableBodyData.push("<td></td>")
					}
					tableBodyData.push("</tr>");
				});
				tableBodyData.push("</tbody>");
				tableBodyData.push("</table>");
				$("#pageDiv").show();
	} else {
		$("#pageDiv").hide();
		tableBodyData.push("<div class='no_data'></div>");
	}
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
 * 删除
 */
function deleteWechattemp(id) {

	 layer.confirm("确定要删除微信消息模板吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("_id",id);
			 $.ajax({
		        'url':ctx  + '/api/wechattemplate/deleWeChatTemplate/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除微信消息模板成功！');
						layer.closeAll('page');
						showWechattempList();
					} else {
						layer.msg('删除微信消息模板失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}
//弹出新增
function openAddWechattemp(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/wechattemplate_add",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增微信消息模板",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
//				maxmin : true,
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
 * 新增客户
 */
function addWechattemp() {
	if (!$.trim($("#title").val())) {
		$("#wechat_title").html("微信消息模板名称不能为空");
		$("#title").focus();
		return;
	}
	if (!$.trim($("#type").val())) {
		$("#wechat_type").html("微信消息模板类型不能为空");
		$("#type").focus();
		return;
	}
	if (!$.trim($("#template_id").val())) {
		$("#wechat_template_id").html("微信消息模板id不能为空");
		$("#template_id").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("title", $("#title").val());
	onairMap.put("type", $("#type").val());
	onairMap.put("template_id", $("#template_id").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/api/wechattemplate/addWeChatTemplate/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加微信消息模板成功！');
				layer.closeAll('page');
				showWechattempList();
			}else {
				layer.msg('添加微信消息模板失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
//弹出修改窗口
function openUpdateWechattemp(id){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/wechattemplate_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改微信消息模板", data);
			showWechattempInfo(id);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * 展示详情
 * 
 * @param id
 */
function showWechattempInfo(id) {
	var onairMap = new OnairHashMap();
	onairMap.put("_id", id);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  + "/api/wechattemplate/findWeChatTemplateById/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var wechattemp = data.data;
			$("#title").val(wechattemp.title)
			$("#type").val(wechattemp.type);
			$("#template_id").val(wechattemp.template_id);
		}
	});
}
/**
 * 修改客户列表
 */
function updateWechattemp(customId) {
	if (!$.trim($("#title").val())) {
		$("#wechat_title").html("微信消息模板名称不能为空");
		$("#title").focus();
		return;
	}
	if (!$.trim($("#type").val())) {
		$("#wechat_type").html("微信消息模板类型不能为空");
		$("#type").focus();
		return;
	}
	if (!$.trim($("#template_id").val())) {
		$("#wechat_template_id").html("微信消息模板id不能为空");
		$("#template_id").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("title", $("#title").val());
	onairMap.put("type", $("#type").val());
	onairMap.put("template_id", $("#template_id").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/api/wechattemplate/modefyWeChatTemplate/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改微信消息模板成功！');
				layer.closeAll('page');
				showWechattempList();
			} else {
				layer.msg('修改微信消息模板失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
