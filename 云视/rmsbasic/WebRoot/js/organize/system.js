$(function() {
	uContDiv('system/toPage/?view=organize/custom/material_list','div_system_content');
	$(".nav_ul li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	})
})