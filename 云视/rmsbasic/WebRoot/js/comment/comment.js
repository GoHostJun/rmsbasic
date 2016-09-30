$(document).ready(function() {
	getMyNewsCommentList();
	//首页头部消息弹窗lsj
	$(".msg_btn").hover(function(){
		$(".msg_btn_down").toggle();
	})
	$(".messages").hover(function(){
		$(".msg_btn_down").toggle();
	})
});

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
			if(data.code == 0){
				var html = "";
				if(data.data.results != null && data.data.results.length > 0){
					html += "<span class='num'>"+data.data.results.length;
					html += "</span><i></i>";
					html += "<div class='msg_btn_down'><i></i>";
					html += "<ul>";
					for(var i = 0; i< data.data.results.length ; i++){
						html += "<li><span class='msg_li_cont'>";
						html += "通联：";
						html += toString(data.data.results[i].newstitle);
						html += "</span><span><a href='javascript:' onclick=updateMessageStatus('"+data.data.results[i]._id+"','"+data.data.results[i].newsid+"');>有新评论</a></span></li>";
					}
					html += "</ul>";
					html += "</div>";
					$("#comment_div_JX").html(html);
				}else{
					$("#comment_div_JX").html("<i></i>");
				}
				$("#comment_div").html(html);
				
				if(data.data.results != null && data.data.results.length >= 5){
					$(".msg_btn_down ul").css({"height":"230px","overflow":"auto"});
				}
			}else{
				toastr.error("获取评论失败");
			}
		},
		error : function(e){}
	});
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


 function toString(str){
	if(str == null || str == undefined || str == 'null'){
		str = '';
	}
	return str;
}

function gobottom(){
	$("html,body").animate({
		scrollTop : document.body.clientHeight
	}, 600);
}