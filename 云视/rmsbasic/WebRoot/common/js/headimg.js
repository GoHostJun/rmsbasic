// JavaScript Document
var headImgEmail;
$(function(){
	
	//选择头像颜色
	var choice_color;
	
	$(".popup_cont .choice_color span").click(function(){
		$(".popup_cont .choice_color span").removeClass("active");
		$(this).addClass("active");
		choice_color=$(this).attr("title");
		//alert(choice_color);
		$(".head_img .heading_info").css("background",choice_color);
	})

})
function getMyScore(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("loginName",headImgEmail);
	onairMap.put("getLog", "false");
	var logouturi = ctx + pathValue+"queryIntegral/";
	$.ajax({
		url : logouturi,
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				$("#score").text(data.data.score)
			}else{
				$("#score").text("0");
			}
		},
		error : function(e){}
	});
}