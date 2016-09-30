// JavaScript Document

$(function(){
	$(".lizhi_title").css({"height":$(".lizhi_title").width()/2.83});
	$(window).resize(function(){
		$(".lizhi_title").css({"height":$(".lizhi_title").width()/2.83});
	})
	$("#jstoDoc").attr("href",ctx+pathPage+"material/toAddDocs/");
	$("[name=jsuploadmaterial]").click(function(){
		
		//弹窗
		openDirectPassingPath();
	})
	$("#jsnoticeMain").attr("href",ctx+pathPage+"notice/toNoticeMain/");
	$("#hotnews").attr("href",ctx+pathPage+"notice/toNoticeMain/");
})

function openDirectPassingPath(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=directpassingpath/directpassingpath_choose",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['480px','280px'],
				title : " ",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
			//	maxmin : true,
				scrollbar : false,
				//offset: ['300px', '200px'],
				content : data,
				shade:0.01
			});
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

