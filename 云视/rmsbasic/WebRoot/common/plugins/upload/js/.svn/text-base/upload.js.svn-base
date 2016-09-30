$(document).ready(function() {
	$("#upload_btn_stop").click(function() {
		$("#upload_btn_continue").css("display", "block");
		$("#upload_btn_stop").css("display", "none");
	});
	$("#upload_btn_continue").click(function() {
		$("#upload_btn_continue").css("display", "none");
		$("#upload_btn_stop").css("display", "block");
	});
	$("#upload_btn_cancel").click(function() {
		_t.enable();
	});
});

$(".stream-cancel").click(function() {
	_t.enable();
});

/**
 * 上传控件状态（最大化、最小化）
 * 
 * @param obj
 */
function upload_stats(status_text) {
	var warp_height = "40px";
	var content_height = "hide";
	if (status_text == "min") {
		$("#upload_img_min").attr("title", "最大化");
		$("#upload_img_max").css("display", "block");
		$("#upload_img_min").css("display", "none");
	} else {
		$("#upload_img_min").attr("title", "最小化");
		$("#upload_img_max").css("display", "none");
		$("#upload_img_min").css("display", "block");
		warp_height = "420px";
		content_height = "show";
	}
	$("#upload_div_wrap").animate({
		height : warp_height
	});
	$("#upload_div_content").animate({
		height : content_height
	});
}

/**
 * 关闭上传控件
 */
function upload_close() {
	$("#upload_div_wrap").hide("slow");
}

/**
 * 打开上传控件
 * 
 * @param id
 *            素材id
 * @param m_type
 *            素材类型（音频，视频）
 * @param mg
 *            m：素材g：成品
 */
function upload_open() {
	var upload_div_page = $("#upload_div_page").html();
	if (upload_div_page != "" && upload_div_page.indexOf("『素材』") > 0) {
		$("#upload_div_wrap").show("slow");
		upload_stats("max");
	} else {
		if (can_upload()) {
			var uri=ctx+"/upload/toUpload/?m_type=";
			$.ajax({
				type : "POST",
				url : uri,
				success : function(data) {
					$("#upload_div_wrap").slideDown();
					$("#upload_div_page").html(data);
					//$("#upload_div_wrap").show("slow");
					//upload_stats("max");
				}
			});
//			$("#upload_div_page").load(ctx+"/common/plugins/upload/upload.html","",function(){
//				$("#upload_div_wrap").slideDown();
//				$("#upload_div_wrap").show("slow");
//				upload_stats("max");
//			});
		}
	}
}

/**
 * 清空已上传
 */
function upload_clear() {
	var percentSpan = $("li div span.stream-percent");
	for ( var i = 0; i < percentSpan.length; i++) {
		if (percentSpan[i].innerHTML == "100%") {
			$(percentSpan[i]).parent().parent().remove();
		}
	}
}

/**
 * 保存上传的文件
 * 
 * @param file
 */
function upload_save(file) {
	var file_name = file.name;
	var file_size = file.size;
	if(file_size != 0){
		var file_suffix = file_name.substring(file_name.lastIndexOf("." + 1));
		fileName = encodeURIComponent(file_name);
		var uri = ctx + getPathValue()+"media/saveMaterialtop/?fileName=" + fileName + "&fileSize=" + file_size;
		$.ajax({
			type : "POST",
			url : uri,
			success : function(data) {
				if($("#addUpload").val()==0){
					if(data.code==0){
						loadUploadHtml(data.data.id);
					}
				}else{
					uCont('material/toMaterial/');
				}
			},
			error:function(){
				//alert($("#addUpload").val())
			}
		});
	}
}

/**
 * 判断当前是否可以上传
 * 
 * @returns {Boolean}
 */
function can_upload() {
	var title = $("#upload_div_title_percent").find("div span.stream-percent");
	if (title.length > 0) {
		if (title[0].innerHTML == "100%" || title[0].innerHTML == "0%") {
			return true;
		} else {
			alert("当前有上传任务，请稍后再上传！");
			$("#upload_div_wrap").show("slow");
			upload_stats("max");
			return false;
		}
	}
	return true;
}

/**
 * 页面正在上传文件
 */
function fileUploading(){
	var UnloadConfirm = {};
	UnloadConfirm.MSG_UNLOAD = "视频正在上传，您的操作会导致上传被取消，确认是否继续？";
	UnloadConfirm.set = function(a) {
		window.onbeforeunload = function(b) {
			b = b || window.event;
			b.returnValue = a;
			return a;
		}
	};
	UnloadConfirm.clear = function() {
		fckDraft.delDraftById();
		window.onbeforeunload = function() {
		}
	};
	UnloadConfirm.set(UnloadConfirm.MSG_UNLOAD);
}

/**
 * 取消页面刷新与关闭的监听
 */
function fileUploaded(){
	var UnloadConfirm = {};
	UnloadConfirm.set = function(a) {
		window.onbeforeunload = function(b) {
		}
	};
	UnloadConfirm.set();
}

