$(function(){
	//导航
	$(".jx_nav > li > p").hover(function(){
			$(this).parent().addClass("nav_show");
		},function(){
			$(".jx_nav > li").hover(function(){
				$(this).addClass("nav_show");
				$(".mark").hover(function(){
					$(this).addClass("nav_show");
				});},function(){
					$(this).removeClass("nav_show");
				});},function(){
			$(this).parent().removeClass("nav_show");
	})

	//点击切换
	$(".jx_nav li > p > a").click(function(){
		$(".jx_nav li").removeClass("active");
		$(this).parent().parent().addClass("active");
	})
	$(".little_nav li").click(function(){
		$(".little_nav li").removeClass("active");
		$(".jx_nav li").removeClass("active");
		$(this).addClass("active");
		$(this).parent().parent().addClass("active");
	})
	//用户下拉
	var listDown=$('.user_div .user_pop');
	$('#user_name').click(function(){
	listDown.toggle();
    });
	    listDown.hover(function(){
	},function(){
	$(this).hide();
    });
	listDown.find('li').click(function(){
		listDown.hide();
	}); 

});