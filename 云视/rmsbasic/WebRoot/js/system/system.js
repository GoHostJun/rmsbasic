// JavaScript Document
$(function() {
	/* 系统设置导航 */
	$(".nav_ul li a").click(function() {
		$(".mini_nav").hide();
		$(".nav_ul li").removeClass("active");
		$(this).parent().addClass("active");
	})
	$("#indexPop").remove();
	$("#indexJX").remove();
	initSystemPage();
})
/**
 * 控制导航菜单显示
 */
function initSystemPage() {
	var toModule_id = $("#toModule_id").val();
	var nomalUser_id = $("#nomalUser_id").val();
	var systemUser_id = $("#systemUser_id").val();
	if (toModule_id != null && toModule_id != 'undefined') {
		$(".mini_nav").hide();
		$(".nav_ul li").removeClass("active");
		$("#li_" + toModule_id).addClass("active");
		if(nomalUser_id != '1'){
			$("#li_notice").show();$("#li_transcode").show();$("#li_path").show();$("#li_organize").show();
			if(nomalUser_id != '2'){
				$("#system_area").show();$("#system_department").show();
				$("#system_user2").show();$("#system_dep").show();
				//路径展示
				$("#push_path").show();$("#directpass_path").show();
			}
		}
		if(systemUser_id== '1'){
			$("#system_user").show();
		}
		if (toModule_id == 'notice') {
			uContDiv('system/toPage/?view=notice/notice_list', 'div_system_content');
		} else if (toModule_id == 'transcode') {
			uContDiv('system/toPage/?view=transcode/transcode', 'div_system_content');
		} else if (toModule_id == 'logs') {
			uContDiv('system/toPage/?view=logs/logs_list', 'div_system_content');
			$("#li_logs").css("border-left", "1px solid #ddd");
		} else if (toModule_id == 'path') {
//			if(getUrlConfig.nameTV=="JS"){
				$("#path_id").show();
//			}
			uContDiv('system/toPage/?view=pushpath/pushpath_list', 'div_system_content');
		} else if (toModule_id == 'organize') {
			$("#quanxian_id").show();
			uContDiv('system/toPage/?view=organize/user/user_list', 'div_system_content');
		}
	}

}
