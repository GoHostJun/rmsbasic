$(document).ready(function() {
	getListData(1,10);
	$("#pageDiv").onairPage({"callback":getListData});
});

function getListData(pageNow,pageSize){
	layer.load(2);
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	var taskId = $("#taskId").val();
	if(!taskId){
		taskId = "";
	}
//	var onairMap = new OnairHashMap();
//	onairMap.put("keyWord", keyWord);
//	onairMap.put("currentPage", pageNow);
//	onairMap.put("pageNum", pageSize);
	$.ajax({
		url : ctx +"/getLogsList/",
		type : "post",
		cache : false,
		data:{
			currentPage:pageNow,
			pageNum:pageSize,
			taskId:taskId
			
		},
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				$("#pageDiv").hide();
				$("#listDiv").html("<div>暂无数据</div>");
				layer.closeAll('loading');
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
//       			$("#inner").append("<div class='no_data'></div>");
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
	html.push("<table class=\"table table-hover\" style=\"word-break: break-all;\">");
	html.push("<thead><tr>" +
			"<th style=\"width: 4%;\">序号</th><th style=\"width: 8%;\">时间</th><th>接口</th><th>任务id</th><th>参数</th><th>公共参数</th><th>日志内容</th><th>oss内容</th>" +
			"</tr></thead><tbody>");
	$(results).each(function(i,item){ 
		html.push("<tr>");
		html.push("<td><span class=\"line\">"+(i+1)+"</span></td>");
		html.push("<td><span class=\"line\">"+item.ctime+"</span></td>");
		html.push("<td><span class=\"line\">"+item.filesource+"</span></td>");
		html.push("<td><span class=\"line\">"+processValue(item.taskId)+"</span></td>");
      	html.push("<td><textarea class=\"form-control diff-textarea\"  rows=\"2\" cols=\"120\">"+item.contents+"</textarea></td>");
		html.push("<td><textarea class=\"form-control diff-textarea\"  rows=\"2\" cols=\"120\">appCode="+item.appCode+";companyId="+item.companyId+";userId="+item.userId+";versionId="+item.versionId+";serviceCode="+item.serviceCode+"</textarea></td>");
		html.push("<td><textarea class=\"form-control diff-textarea\"  rows=\"2\" cols=\"120\">"+item.logdesc+"</textarea></td>");
		html.push("<td><textarea class=\"form-control diff-textarea\"  rows=\"2\" cols=\"120\">"+processValue(item.ossDesc)+"</textarea></td>");
		html.push("</tr>");
	});
	$("#listDiv").html(html.join(""));
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}