$(function(){
	if(userRole=="管理员"){
		//重置样式
		$(".js_list").css("margin-top","0px");
	}
	$("#pageDiv").onairPage({
		"callback" : showdirectpassingpathList
	});
	showdirectpassingpathList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showdirectpassingpathList(1,10);
	     }
  	}; 
})
/**
 * 加载推送路径列表
 */
function showdirectpassingpathList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	onairMap.put("keyWord", $("#pushnameword").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +pathValue+"directpassingpath/findAllDirectPassingPath/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data.results));
				//initSelectAll();
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
					tableBodyData.push("<th>目标路径名称</th>");
					tableBodyData.push("<th>目标路径ID</th>");
					tableBodyData.push("<th>目标路径地址</th>");
					tableBodyData.push("<th>推送路径地址</th>");
					tableBodyData.push("<th>其它信息</th>");
					tableBodyData.push("<th width='80px'>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td>" + toString(item.tarpathname) + "</td>");
					tableBodyData.push("<td>" + toString(item.tarpathid) + "</td>");
					tableBodyData.push("<td>" +toString(item.tarpathaddr) + "</td>");
					tableBodyData.push("<td>" +toString(item.pushpathaddr) + "</td>");
					tableBodyData.push("<td>" +toString(item.othermsg) + "</td>");
					tableBodyData.push("<td><a href='javascript:openUpdatedirectpassingpath(\"" + item._id + "\");'>编辑</a><a href='javascript:deletedirectpassingpath(\""
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

function openAdddirectpassingpath(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=directpassingpath/directpassingpath_create",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增推送路径",
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
 * 删除公告类型
 */
function deletedirectpassingpath(Id) {

	 layer.confirm("确定要删除吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("_id",Id);
			onairMap.put("accessToken", getAccessToken());
			onairMap.put("timeStamp", getTimeStamp());
			 $.ajax({
		        'url':ctx  +pathValue+ 'directpassingpath/deleteDirectPassingPath/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除成功！');
						layer.closeAll('page');
						showdirectpassingpathList();
					} else {
						layer.msg('删除失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}
//弹出修改路径窗口
function openUpdatedirectpassingpath(id){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=directpassingpath/directpassingpath_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改直传路径", data);
			showDirectPassingPath(id);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}