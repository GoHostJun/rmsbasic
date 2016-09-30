$(document).ready(function() {
	getListData(1,10);
	$("#pageDiv").onairPage({"callback":queryListData});
});
function clearTask(){
	$("#taskType").val("");
	$("#status").val("");
	$("#localid").val("");
	$("#task_id").val("");
}
function getListData(pageNow,pageSize){
	layer.load(2);
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	var keyWord = $("#search").val();
	if(!keyWord){
		keyWord = "";
	}
//	var onairMap = new OnairHashMap();
//	onairMap.put("keyWord", keyWord);
//	onairMap.put("currentPage", pageNow);
//	onairMap.put("pageNum", pageSize);
	$.ajax({
		url : ctx +"/getTaskList/",
		type : "post",
		cache : false,
		data:{
			currentPage:pageNow,
			pageNum:pageSize
		},
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				$("#pageDiv").hide();
				$("#listDiv").html("<div>暂无数据</div>");
				swal({title:"查询失败",type:"error"});
				return;
			}
			if(code == 0 && data.results.length > 0){
//				$("#inner").hide();
//	       		$("#outter").show();
				loadHtml(data);
				layer.closeAll('loading');
			}else{
//				$("#outter").hide();
//       			$("#inner").show();
//       			$("#inner").html('');
				$("#pageDiv").hide();
				$("#listDiv").html("<div>暂无数据</div>");
				layer.closeAll('loading');
			}
		},
		error : function() {
			$("#pageDiv").hide();
			$("#listDiv").html("<div>暂无数据</div>");
			layer.closeAll('loading');
			swal({title:"查询失败",type:"error"});
//			toastr.error("查询文稿列表失败");
		}
	});
}

function queryListData(pageNow,pageSize){
	layer.load(2);
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	var taskType = $("#taskType").val();
	var status = $("#status").val();
	var localid = $("#localid").val();
	var task_id = $("#task_id").val();
	$.ajax({
		url : ctx +"/gerQueryList/",
		type : "post",
		cache : false,
		data:{
			status:status,
			taskType:taskType,
			localid:localid,
			task_id:task_id,
			currentPage:pageNow,
			pageNum:pageSize
		},
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				$("#pageDiv").hide();
				$("#listDiv").html("<div>暂无数据</div>");
				layer.closeAll('loading');
				swal({title:"查询失败",type:"error"});
				return;
			}
			if(code == 0 &&null!=data.results&& data.results.length > 0){
//				$("#inner").hide();
//	       		$("#outter").show();
				loadHtml(data);
				layer.closeAll('loading');
			}else{
//				$("#outter").hide();
//       			$("#inner").show();
//       			$("#inner").html('');
//       			$("#inner").append("<div class='no_data'></div>");
				$("#pageDiv").hide();
				$("#listDiv").html("<div>暂无数据</div>");
				layer.closeAll('loading');
			}
		},
		error : function() {
//			layer.close(index);
			$("#pageDiv").hide();
			$("#listDiv").html("<div>暂无数据</div>");
			layer.closeAll('loading');
			swal({title:"查询失败",type:"error"});
		}
	});
}
function queryDataById(id){
	layer.load(2);
	$.ajax({
		url : ctx +"/getDataById/",
		type : "post",
		cache : false,
		data:{
			id:id
		},
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				layer.closeAll('loading');
				swal({title:"查询失败",type:"error"});
				return;
			}
			if(code == 0 &&null!=data){
				$("#content").text("");
				$("#content").html(data);
				layer.closeAll('loading');
			}else{
				$("#content").text("");
				$("#content").html("--");
				layer.closeAll('loading');
			}
		},
		error : function() {
			$("#content").text("");
			$("#content").html("--");
			layer.closeAll('loading');
		}
	});
}

/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	html.push("<div class=\"col-sm-12\">");
	html.push("<div class=\"ibox float-e-margins\">"); 
	html.push("<div class=\"ibox-content\">");
	html.push("<table class=\"table table-hover\">");
	html.push("<thead><tr>" +
			"<th>序号</th><th>开始时间</th><th>结束时间</th><th>服务接口</th><th>状态</th><th>服务任务ID</th><th>任务ID</th><th>任务结果</th>" +
			"</tr></thead><tbody>");
	if(null!=results&&results.length>0){
		$(results).each(function(i,item){ 
			html.push("<tr>");
			html.push("<td><span class=\"line\">"+(i+1)+"</span></td>");
			html.push("<td><span class=\"line\">"+processValue(item.createTime)+"</span></td>");
			html.push("<td><span class=\"line\">"+processValue(item.endTime)+"</span></td>");
			html.push("<td><span class=\"line\">"+processValue(item.taskType)+"</span></td>");
			if(item.status==0){
				html.push("<td><span class=\"line\">处理成功</span></td>");
			}
			if(item.status==1){
				html.push("<td><span class=\"line\">处理中</span></td>");
			}
			if(item.status==2){
				html.push("<td><span class=\"line\">处理失败</span></td>");
			}
			if(item.status==3){
				html.push("<td><span class=\"line\">oss上传中</span></td>");
			}
			if(item.status==4){
				html.push("<td><span class=\"line\">等待回调</span></td>");
			}
			html.push("<td><span class=\"line\">"+processValue(item.localid)+"</span></td>");
			html.push("<td><span class=\"line\">"+processValue(item.task_id)+"</span></td>");
			html.push("<td><button type=\"button\" class=\"btn btn-sm btn-primary\" data-toggle=\"modal\" data-target=\"#myModal5\" onclick=\"queryDataById('"+item._id+"');\">详情</button></td>");
			html.push("</tr>");
		});
		$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
		$("#pageDiv").show();
	}else{
		$("#pageDiv").hide();	
	}
	$("#listDiv").html(html.join(""));
}
