$(function(){
	$(".share_wrap").css({"height":$(window).height()});
	$(".news_div").css({"height":$(".news_div").width()/0.64});

	$(window).resize(function(){
		$(".share_wrap").css({"height":$(window).height()});
	})

	$(".share_tip a").click(function(){
		$(this).parent().hide();
	})
	
	$(".img_div img").load(function(){
		var img_w=$(this).width();
		var img_h=$(this).height();
		var div_h=$(".img_div").height();
		$(this).css({"width":img_w,"margin-top":(div_h-img_h)/2});
	})
	
})

function openWX(){
	window.location.href=openUrl;
}

