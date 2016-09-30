
//查看公告详情
function getNoticeDetail(noticeId) {
	var strJson={"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"noticeId":noticeId}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'notice/findNoticeById/',
		data: strJson,
		async : false,
		success : function(data) {
			var html = "";
			if(data.code == 0){
				html += "<div class='detail_title'>"+
							"<h2>"+toString(data.data.noticetitle)+"</h2>"+
							"<div class='fr detail_title_message'>"+
								"<span>发布时间："+toString(data.data.ctime)+"</span>"+
								"<span>发布人："+toString(data.data.cusename)+"</span>"+
							"</div>"+
							"<div class='clear'></div>"+
						"</div>"+
						"<div class='detail_cont_inner'>"+
						toString(data.data.noticecontenthtml)+
						"</div>";
				$("#notice_detail_id").html(html);
			}else{
				toastr.error("获取公告详情失败");
			}
		},
		error : function(e){}
	});
}

function canceltolist(){
	window.location = ctx + pathPage + 'notice/toNoticeMain/';
}

function toString(str){
	if(str == null || str == undefined){
		str = '';
	}
	return str;
}
