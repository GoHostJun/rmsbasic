$(document).ready(function() {
	getListData(1,12);
	$("#pageDiv").onairPage({"callback":getListData,"pageSize":12});
});
function setTemplate(){
	var owner = $("#owner").val();
	var templateName = $("#templateName").val();
	var vCodec = $("#vCodec").val();
	var vBitrate = $("#vBitrate").val();
	var fps = $("#fps").val();
	var gop = $("#gop").val();
	var width = $("#width").val();
	var height = $("#height").val();
	var aspect = $("#aspect").val();
	var pixelFormat = $("#pixelFormat").val();
	var picUrl = $("#picUrl").val();
	var picX = $("#picX").val();
	var picY = $("#picY").val();
	var picWidth = $("#picWidth").val();
	var picHeight = $("#picHeight").val();
	var vProfile = $("#vProfile").val();
	var vLevel = $("#vLevel").val();
	var subtitleUrl = $("#subtitleUrl").val();
	var aCodec = $("#aCodec").val();
	var format = $("#format").val();
	var aBitrate = $("#aBitrate").val();
	var channel = $("#channel").val();
	var sampleRate = $("#sampleRate").val();
	var extraParam = $("#extraParam").val();
	var extraInfo = $("#extraInfo").val();
	var suffix = $("#suffix").val();
	var cType = $("#cType").val();
	var tType = $("#tType").val();
	$.ajax({
		url : ctx +"/setTemplate/",
		type : "post",
		data:{
			owner:owner, 
			templateName:templateName, 
			vCodec:vCodec,
			vBitrate:vBitrate,
			fps:fps,
			gop:gop,
			width:width,
			height:height,
			aspect:aspect,
			pixelFormat:pixelFormat,
			picUrl:picUrl,
			picX:picX,
			picY:picY,
			picWidth:picWidth,
			picHeight:picHeight,
			vProfile:vProfile,
			vLevel:vLevel,
			subtitleUrl:subtitleUrl,
			aCodec:aCodec,
			format:format,
			aBitrate:aBitrate,
			sampleRate:sampleRate,
			extraParam:extraParam,
			extraInfo:extraInfo,
			suffix:suffix,
			cType:cType,
			tType:tType,
			channel:channel
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			if(code != 0){
				swal({title:"保存失败",type:"error"});
				return;
			}
//			$("#inner").hide();
//			$("#outter").show();
			$('#myModal').hide();
			swal({title:"保存成功",type:"success"});
			getListData(1,12);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}
function setTemplateById(id){

	$.ajax({
		url : ctx +"/getTemplateById/",
		type : "post",
		data:{
			id:id
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
			$("#update_templateId").val(data.templateId);
			$("#update_owner").val(data.owner);
			$("#update_templateName").val(data.templateName);
			$("#update_vCodec").val(data.vCodec);
			$("#update_vBitrate").val(data.vBitrate);
			$("#update_fps").val(data.fps);
			$("#update_gop").val(data.gop);
			$("#update_width").val(data.width);
			$("#update_height").val(data.height);
			$("#update_aspect").val(data.aspect);
			$("#update_pixelFormat").val(data.pixelFormat);
			$("#update_picUrl").val(data.picUrl);
			$("#update_picX").val(data.picX);
			$("#update_picY").val(data.picY);
			$("#update_picWidth").val(data.picWidth);
			$("#update_picHeight").val(data.picHeight);
			$("#update_vProfile").val(data.vProfile);
			$("#update_vLevel").val(data.vLevel);
			$("#update_subtitleUrl").val(data.subtitleUrl);
			$("#update_aCodec").val(data.aCodec);
			$("#update_format").val(data.format);
			$("#update_aBitrate").val(data.aBitrate);
			$("#update_channel").val(data.channel);
			$("#update_sampleRate").val(data.sampleRate);
			$("#update_extraParam").val(data.extraParam);
			$("#update_extraInfo").val(data.extraInfo);
			$("#update_suffix").val(data.suffix);
			$("#update_cType").val(data.cType);
			$("#update_tType").val(data.tType);
			$("#updateId").val(data._id);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}
function deleteTemplate(id){
	if(!confirm("确认删除吗")){
		return;
	}
	$.ajax({
		url : ctx +"/deleteTemplate/",
		type : "post",
		data:{
			id:id
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			if(code != 0){
				swal({title:"删除失败",type:"error"});
				return;
			}
			swal({title:"删除成功",type:"success"});
			getListData(1,12);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}

function updateTemplate(){

	var owner = $("#update_owner").val();
	var templateName = $("#update_templateName").val();
	var vCodec = $("#update_vCodec").val();
	var vBitrate = $("#update_vBitrate").val();
	var fps = $("#update_fps").val();
	var gop = $("#update_gop").val();
	var width = $("#update_width").val();
	var height = $("#update_height").val();
	var aspect = $("#update_aspect").val();
	var pixelFormat = $("#update_pixelFormat").val();
	var picUrl = $("#update_picUrl").val();
	var picX = $("#update_picX").val();
	var picY = $("#update_picY").val();
	var picWidth = $("#update_picWidth").val();
	var picHeight = $("#update_picHeight").val();
	var vProfile = $("#update_vProfile").val();
	var vLevel = $("#update_vLevel").val();
	var subtitleUrl = $("#update_subtitleUrl").val();
	var aCodec = $("#update_aCodec").val();
	var format = $("#update_format").val();
	var aBitrate = $("#update_aBitrate").val();
	var channel = $("#update_channel").val();
	var sampleRate = $("#update_sampleRate").val();
	var extraParam = $("#update_extraParam").val();
	var extraInfo = $("#update_extraInfo").val();
	var suffix = $("#update_suffix").val();
	var cType = $("#update_cType").val();
	var tType = $("#update_tType").val();
	var id = $("#updateId").val();
	$.ajax({
		url : ctx +"/updateTemplate/",
		type : "post",
		data:{
			id:id,
			owner:owner, 
			templateName:templateName, 
			vCodec:vCodec,
			vBitrate:vBitrate,
			fps:fps,
			gop:gop,
			width:width,
			height:height,
			aspect:aspect,
			pixelFormat:pixelFormat,
			picUrl:picUrl,
			picX:picX,
			picY:picY,
			picWidth:picWidth,
			picHeight:picHeight,
			vProfile:vProfile,
			vLevel:vLevel,
			subtitleUrl:subtitleUrl,
			aCodec:aCodec,
			format:format,
			aBitrate:aBitrate,
			sampleRate:sampleRate,
			extraParam:extraParam,
			extraInfo:extraInfo,
			suffix:suffix,
			cType:cType,
			tType:tType,
			channel:channel
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
//			$("#inner").hide();
//			$("#outter").show();
			$('#myModal').hide();
			swal({title:"保存成功",type:"success"});
			getListData(1,12);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}
function getListData(pageNow,pageSize){
	layer.load(2);
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
//		pageSize = 12;
	}
	var owner = $("#query_owner").val();
	$.ajax({
		url : ctx +"/getTemplateList/",
		type : "post",
		cache : false,
		data:{
			currentPage:pageNow,
			pageNum:pageSize,
			owner:owner
		},
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
//			$("#inner").hide();
//			$("#outter").show();
			loadHtml(response);
			layer.closeAll('loading');
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}

/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var html = [];
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	html.push("<div class=\"wrapper wrapper-content animated fadeInRight\">");
	html.push("<div class=\"row\">"); 
	$(results).each(function(i,item){
//		<div class="col-sm-4">
//		<div class="contact-box">
//		<a href="profile.html">
//		<div class="col-sm-8">
//		<h3><strong>奔波儿灞</strong></h3>
//		<p><i class="fa fa-map-marker"></i> 甘肃·兰州</p>
//		<address>
//		<strong>Baidu, Inc.</strong><br>
//		E-mail:xxx@baidu.com<br>
//		Weibo:<a href="#">http://weibo.com/xxx</a><br>
//		<abbr title="Phone">Tel:</abbr> (123) 456-7890
//		</address>
//		</div>
//		<div class="clearfix"></div>
//		</a>
//		</div>
//		</div>
		html.push("<div class=\"col-sm-4\">");
		html.push("<div class=\"contact-box\">");
		html.push("<a data-toggle=\"modal\" data-target=\"#myModal_update\" onclick=\"setTemplateById('"+item._id+"');\">");
		html.push("<div class=\"col-sm-8\">");
		html.push("<h3><strong>"+item.templateName+"</h3>");
		html.push("<p>模板所有者："+processValue(item.owner)+" 模板ID："+item.templateId+"</p>");
		html.push("</div>");
		html.push("<div class=\"clearfix\"></strong></div>");
		html.push("</a>"); 
		html.push("<button type=\"button\" class=\"btn  btn-danger\" onclick=\"deleteTemplate('"+item._id+"');\">删除</button></div>");
		html.push("</div>");
	});

	html.push("</div></div>");
	$("#listDiv").html(html.join(""));
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}