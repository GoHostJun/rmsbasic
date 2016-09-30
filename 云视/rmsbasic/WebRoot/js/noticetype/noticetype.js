$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showNoticeTypeList
	});
	showNoticeTypeList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showNoticeTypeList(1,10);
	     }
   	}; 
   
   
});

/**
 * 加载客户列表
 */
function showNoticeTypeList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	onairMap.put("keyWord", $("#noticenameword").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +pathValue+  "notice/findNoticeTypeAll/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data.results));
				initSelectAll();
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
					tableBodyData.push("<th></th>");
					tableBodyData.push("<th>公告类型名称</th>");
					tableBodyData.push("<th>公告类型唯一标示</th>");
					tableBodyData.push("<th>公告类型样式</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td>" + toString(item.noticename) + "</td>");
					tableBodyData.push("<td>" + toString(item.uniquename) + "</td>");
					tableBodyData.push("<td>" +toString(item.noticestyle) + "</td>");
					tableBodyData.push("<td><a href='javascript:openUpdateNoticeType(\"" + item._id + "\");'>编辑</a><a href='javascript:deleteNoticeType(\""
							+ item._id + "\");' class='td_del'>删除</a></td>");
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
 * 删除公告类型
 */
function deleteNoticeType(customId) {

	 layer.confirm("确定要删除公告类型吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("noticeTypeId",customId);
			onairMap.put("accessToken", getAccessToken());
			onairMap.put("timeStamp", getTimeStamp());
			 $.ajax({
		        'url':ctx  +pathValue+ 'notice/deleteNoticeType/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除公告类型成功！');
						layer.closeAll('page');
						showNoticeTypeList();
					} else {
						layer.msg('删除公告类型失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}
//弹出新增公告类型窗口
function openAddNoticeType(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=noticetype/noticetype_add",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增公告类型",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
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
 * 新增公告类型
 */
function addNoticeType() {
	if (!$.trim($("#noticename").val())) {
		$("#notice_title").html("公告类型名称不能为空");
		$("#noticename").focus();
		return;
	}
	if (!$.trim($("#uniqueName").val())) {
		$("#unique_name").html("不能为空");
		$("#uniqueName").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("name", $("#noticename").val());
	onairMap.put("uniqueName", $("#uniqueName").val());
	onairMap.put("style", $("#noticestyle").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "notice/addNoticeType/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加公告类型成功！');
				layer.closeAll('page');
				showNoticeTypeList();
			} else {
				layer.msg('添加公告类型失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
//弹出修改客户窗口
function openUpdateNoticeType(noticeTypeId){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=noticetype/noticetype_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改公告类型", data);
			shownoticeTypeInfo(noticeTypeId);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * 展示公告类型详情
 * 
 * @param userId
 */
function shownoticeTypeInfo(noticeTypeId) {
	var onairMap = new OnairHashMap();
	onairMap.put("noticeTypeId", noticeTypeId);
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  + pathValue+"notice/findNoticeTypeById/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var noticeType = data.data;
			//alert(noticeType.noticename)
			$("#noticeTypeId").val(noticeType._id)
			$("#noticename").val(noticeType.noticename);
			$("#noticestyle").val(noticeType.noticestyle);
		}
	});
}
/**
 * 修改公告类型列表
 */
function updateNoticeType() {
	if (!$.trim($("#noticename").val())) {
		$("#notice_title").html("公告类型名称不能为空");
		$("#noticename").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("noticeTypeId", $("#noticeTypeId").val());
	onairMap.put("name", $("#noticename").val());
	onairMap.put("style", $("#noticestyle").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "notice/updateNoticeType/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改公告类型成功！');
				layer.closeAll('page');
				showNoticeTypeList();
			} else {
				layer.msg('修改公告类型失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
