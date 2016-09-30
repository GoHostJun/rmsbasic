$(document).ready(function() {
	//$("#my-dynamic").niceScroll({cursorwidth:"2px", cursorcolor:"#398F70"});
	judgeToDealForAudit();
	judgeToDealForPush();
	getMyNewsCommentList();
});

//判断待审核的通联数目
function judgeToDealForAudit() {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"type":"0"}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'news/getNewsUnDeal/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				if(data.data.dealState > 0){
					$("#wait_to_audit_li").html("<i>"+data.data.dealState+"</i>待审核");
				}
			}
		},
		error : function(e){}
	});
}

//判断待推送的通联数目
function judgeToDealForPush() {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"type":"1"}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'news/getNewsUnDeal/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				if(data.data.dealState > 0){
					$("#wait_to_push_li").html("<i>"+data.data.dealState+"</i>待推送");
				}
			}
		},
		error : function(e){}
	});
}

function to_push_list(){
	window.open(ctx + pathPage + "news/toNewsListIndex/");
}

function to_audit_list(){
	window.open(ctx + pathPage + "news/toAuditNewsListIndex/");
}

function to_publish_list(){
	window.open(ctx + pathPage + "news/toMyNewsListIndex/");
}

function to_complete_list(){
	window.open(ctx + pathPage + "news/toCompleteNewsListIndex/");
}

function getMyNewsCommentList() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("type", 0);
	onairMap.put("status", 0);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'message/findMessages/',
		data:onairMap.toJson(),
		async : false,
		success : function(data) {
			$("#my-dynamic").html("");
			if(data.code == 0){
				var html = "";
				if(data.data.results != null && data.data.results.length > 0){
//					if(data.data.results.length > 4){
//						$("#my-dynamic").niceScroll({cursorwidth:"2px", cursorcolor:"#398F70"});
//					}
					var len=3;
					if(data.data.results.length <= 3){
						len = data.data.results.length;
					}
					html += "<ul>";
					for(var i = 0; i< len ; i++){
						html += "<li style='cursor:pointer;' onclick=updateMessageStatus('"+data.data.results[i]._id+"','"+data.data.results[i].newsid+"');><p title='"+data.data.results[i].newstitle+"'>通联："+toString(data.data.results[i].newstitle)+"</p><span><a href=\"#\">您有新评论</a></span></li>";
					}
					html += "</ul>";
				}else{
					html +='<span style=\"display: block; margin: 50px auto; padding: 0; text-align: center; width: 80px;font-size:12px;\" >暂无动态</span>';
				}
				$("#my-dynamic").html(html);
			}else{
				//toastr.error("获取评论失败");
			}
		},
		error : function(e){}
	});
}

//function getMyNewsCommentList() {
//	var onairMap = new OnairHashMap();
//	onairMap.put("accessToken", getAccessToken());
//	onairMap.put("timeStamp", getTimeStamp());
//	onairMap.put("type", 0);
//	onairMap.put("status", 0);
//	$.ajax({
//		type : 'POST',
//		dataType:'json',
//		headers: {'Content-Type': 'application/json'},
//		url : ctx + pathValue + 'message/findMessages/',
//		data:onairMap.toJson(),
//		async : false,
//		success : function(data) {
//			$("#my-dynamic").html("");
//			if(data.code == 0){
//				var html = "";
//				if(data.data.results != null && data.data.results.length > 0){
//					if(data.data.results.length > 4){
//						$("#my-dynamic").niceScroll({cursorwidth:"2px", cursorcolor:"#398F70"});
//					}
//					html += "<ul>";
//					for(var i = 0; i< data.data.results.length ; i++){
//						html += "<li style='cursor:pointer;' onclick=updateMessageStatus('"+data.data.results[i]._id+"','"+data.data.results[i].newsid+"');><p title='"+data.data.results[i].newstitle+"'>通联："+toString(data.data.results[i].newstitle)+"</p><span><a href=\"#\">您有新评论</a></span></li>";
//					}
//					html += "</ul>";
//				}else{
//					html +='<span style=\"display: block; margin: 50px auto; padding: 0; text-align: center; width: 80px;font-size:10px;\" >暂无动态</span>';
//				}
//				$("#my-dynamic").html(html);
//			}else{
//				toastr.error("获取评论失败");
//			}
//		},
//		error : function(e){}
//	});
//}

 function toString(str){
	if(str == null || str == undefined || str == 'null'){
		str = '';
	}
	return str;
}

function updateMessageStatus(messageId,newsId){
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("messageId", messageId);
		$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'message/updateMessageStatus/',
		data:onairMap.toJson(),
		async : false,
		success : function(data) {
			getMyNewsCommentList();
			window.open(ctx + pathPage + "news/toNewsViewIndex/?_id="+newsId+"&fromType=message");
		},
		error : function(e){}
	});
}