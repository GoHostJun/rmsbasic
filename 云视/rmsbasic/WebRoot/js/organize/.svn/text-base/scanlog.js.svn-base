$(function(){
	$("#pageDiv").onairPage({
		"callback" : showscanlog
	});
	showscanlog();
	
})
function showscanlog(currentpage,pagesize){
	var index = layer.load();
	var page;
	var pageNum;
	page=(currentpage==undefined?$("#pageDiv").getOnairPageParameter().pageNow:currentpage)
	pageNum=(pagesize==undefined?$("#pageDiv").getOnairPageParameter().pageSize:pagesize)
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	onairMap.put("keyWord", $("#txtKeyWord").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "scanlog/findScanlLogAll/",
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
//					tableBodyData.push("<th class='i_td'><i></i><input type='checkbox' id='all' /></th>");
					tableBodyData.push("<th>文件名</th>");
					tableBodyData.push("<th>用户标识</th>");
					tableBodyData.push("<th>应用标识</th>");
					tableBodyData.push("<th>企业标识 </th>");
					tableBodyData.push("<th>回调状态</th>");
					tableBodyData.push("<th>注册状态</th>");
					tableBodyData.push("<th>日志详情</th>");
					tableBodyData.push("<th> 任务开始时间</th>");
					tableBodyData.push("<th> 任务结束时间</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
//					tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='custom_id' value="+item._id+"></td>");
					tableBodyData.push("<td>" + toString(item.fileName) + "</td>");
					tableBodyData.push("<td> <input type='text' value='"+toString(item.userId)+"' /></td>");
					tableBodyData.push("<td>" + cutTitleString(toString(item.appCode),5,"...") + "</td>");
					tableBodyData.push("<td> <input type='text' value='"+toString(item.companyId)+"' /></td>");
					tableBodyData.push("<td>" +(item.callbackStatus==0?"成功":"失败") + "</td>");
					tableBodyData.push("<td>" +(item.status==0?"成功":"失败") + "</td>");
					tableBodyData.push("<td> <textarea>" + toString(item.logdesc) + " </textarea></td>");
					tableBodyData.push("<td>" + toString(item.startTime) + "</td>");
					tableBodyData.push("<td>" +toString(item.endTime) + "</td>");
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