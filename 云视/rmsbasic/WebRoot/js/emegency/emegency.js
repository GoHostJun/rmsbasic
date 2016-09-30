$(function(){
	getEmeListData(1,10)
	$("#pageDiv").onairPage({"callback":getEmeListData});
})
function getEmeListData(pageNow,pageSize){
	
	var keyWord = $("#txtKeyWord").val();
	if(!keyWord){
		keyWord = "";
	}
	var onairMap = new OnairHashMap();
	if(!pageNow && !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	onairMap.put("keyWord", keyWord);
	$.ajax({
		url : ctx +"/retry/retryQurey/",
		headers:{"Content-Type":"application/json"},
		type : "post",
		cache : false,
		dataType : "json",
		data : onairMap.toJson(),
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				toastr.error("查询失败");
				return;
			}
			if(code == 0 && data.results.length > 0){
				$("#inner").hide();
	       		$("#outter").show();
				loadHtml(data);
			}else{
				$("#outter").hide();
       			$("#inner").show();
       			$("#inner").html('');
       			$("#inner").append("<div class='no_data'></div>");
			}
		},
		error : function() {
			toastr.error("查询失败");
		}
	});
}
/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var results = obj.results;
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var html = [];
	html.push("<table style='    word-break: break-all;' border='0' cellpadding='0' cellspacing='0'>" +
			"<tr><th>文件名</th><th >文件地址</th><th  width=30px>任务状态</th>" +
			"<th>错误信息</th><th  width=10px>转码任务下发url地址</th>" +
			"<th  width=40px> 转码任务下发内容</th><th  width=30px>截图任务下发url地址</th>" +
			"<th  width=40px>截图任务下发内容</th><th>用户标识</th>" +
			"<th width=150px>创建时间</th><th  width=180px>操作</th></tr><tbody>")
	$(results).each(function(i,item){	
		html.push("<tr><td width=100px>"+item.fileName+"</td><td > <input type='text' value='"+item.fileDepositUrl+"'>" +
				"</td><td>"+item.status+"</td>" +
				"<td width=100px>"+item.errorMessage+"</td>" +
				"<td > <input  style='width:70px'  type='text' value='"+item.transcodeUrl+"'></td>" +
				"<td> <input  style='width:70px' type='text' value='"+item.transcodeParam+"'></td>" +
				"<td> <input style='width:70px' type='text' value='"+item.screenUrl+"'</td>" +
				"<td> <input style='width:70px'  type='text' value='"+item.screenParam+"'</td>" +
				"<td> <input  style='width:70px' type='text' value='"+item.userId+"' </td><td>"+item.ctime+"</td><td><a href='javascript:url(\"transcode\",\""+item._id+"\");'>转码</a>" +
						"<a href='javascript:url(\"screenshot\",\""+item._id+"\");' class='td_del'>截图</a>" +
								"<a href='javascript:retry(\"" + item.userId + "\",\""+item.fileDepositUrl+"\",\""+item.fileName+"\");' class='td_del'>素材</a></td></tr>")
	});
	html.push("</tbody></table>")
	$("#table_body").html(html.join(""));
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}

function url(taskType,_id){
	$.ajax({
			url : ctx +"/retry/retryUrl/",
			data : {taskType:taskType,_id:_id},
			type : "post",
			cache : false,
			success : function(response) {
				toastr.success("下发成功");
			},
			error : function() {
				toastr.error("下发失败");
			}
		});
    }
    function retry(userId,file,fileName){
    	if(!userId){
    		userId =$("[name='userId']").val();
    	}
    	if(!file){
    		file =$("[name='file']").val();
    	}
    	if(!fileName){
    		fileName =$("[name='fileName']").val();
    	}

    	$.ajax({
			url : ctx +"/retry/retry/",
			data : {userId:userId,
			file:file,
			fileName:fileName
			},
			type : "post",
			cache : false,
			success : function(response) {
				toastr.success("重发成功");
			},
			error : function() {
				toastr.error("重发失败");
			}
    	});
    }