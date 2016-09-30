$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showWeChatMessageList
	});
	showWeChatMessageList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showWeChatMessageList(1,10);
	     }
   	}; 
});

/**
 * 加载客户列表
 */
function showWeChatMessageList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	onairMap.put("email", $("#txtKeyWord").val());
	onairMap.put("consumerid", $("#consumerid").val());
	onairMap.put("status", $("#status").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/api/wechatmessage/getAllWeChatMessage/",
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
					tableBodyData.push("<th></th>");
					tableBodyData.push("<th>用户名</th>");
					tableBodyData.push("<th>客户标识</th>");
					tableBodyData.push("<th>微信发送消息</th>");
					tableBodyData.push("<th>微信返回信息</th>");
					tableBodyData.push("<th>发送url</th>");
					tableBodyData.push("<th>重发次数</th>");
					tableBodyData.push("<th>发送状态</th>");
					tableBodyData.push("<th>创建时间</th>");
					tableBodyData.push("<th>重发时间</th>");
					tableBodyData.push("<th width='40px'>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td>" + toString(item.email) + "</td>");
					tableBodyData.push("<td>" +toString(item.consumerid) + "</td>");
					tableBodyData.push("<td> <textarea>" + toString(item.templatemessage) + "</textarea></td>");
					tableBodyData.push("<td> <textarea>" + toString(item.backmessage )+ "</textarea></td>");
					tableBodyData.push("<td> <textarea>" + toString(item.wxurl) + "</textarea></td>");
					tableBodyData.push("<td>" + toString(item.count) + "</td>");
					tableBodyData.push("<td>" + (toString(item.status)=="0"?"成功":"失败") + "</td>");
					tableBodyData.push("<td>" + toString(item.ctime) + "</td>");
					tableBodyData.push("<td>" + toString(item.uutime) + "</td>");
					if(toString(item.status)==1){
						tableBodyData.push("<td><a href='javascript:retryPush(\"" + item._id + "\");'>重发</a></td>");
					}else{
						tableBodyData.push("<td></td>");
					}
					tableBodyData.push("</tr>");
				});
				tableBodyData.push("</tbody>");
				tableBodyData.push("</table>");
				$("#pageDiv").show();
	} else {
		$("#pageDiv").hide();
		tableBodyData.push("<div class='no_data'></div>");
	//	tableBodyData = [ "<tr><td colspan='12'><br/><center>暂无数据，马上<a href='javascript:userAddOpen();' class='default_a' style='color:blue;'><b>添加用户</b></a>。</center><br/></td></tr>" ];
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
 * 重发消息
 */
function retryPush(id) {
	var onairMap = new OnairHashMap();
	onairMap.put("_id",id);
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url :ctx +  "/api/wechatmessage/retryPushWx/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('重发成功！');
				showWeChatMessageList();
			} else {
				layer.msg('重发失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
