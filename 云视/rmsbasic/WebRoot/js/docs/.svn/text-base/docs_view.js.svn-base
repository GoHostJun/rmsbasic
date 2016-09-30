/**
 * 已选择准备添加到文稿的音视频用此数组存放，数组中存放的对象属性有：vslt:缩略图地址，name：文件名称，
 * uutime:更新时间（页面展示需要，更新时间进行了处理，支取年月日）;
 * wanurl：外网地址，mType：媒体类型，
 */
var videoAudioTempArr = [];
/**
 * 为了避免重复添加音视频和图片，用此map储存已添加的音视频和图片的id，即key为id，值也为id
 * 每次添加音视频和图片到相应数组时都先判断此map是否已经存在相应的id
 */
var tempMap = new OnairHashMap();
/**
 * 已选择准备添加到文稿的图片用此数组存放，数组中存放的对象属性有：_id:唯一标识，vslt:缩略图地址，name：文件名称，
 * uutime:更新时间（页面展示需要，更新时间进行了处理，支取年月日）;
 * wanurl：外网地址，mType：媒体类型，
 */
var picTempArr = [];
$(document).ready(function() {
	findByDocId();
	 $(".extended").click(function(){
			$(this).parent(".extended-div").find(".extended-drop").toggle();
		})
	 $('.extended-div').click(function(e){
		e.stopPropagation();
	 })
});

function findByDocId(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", $("#docId").val());
	$.ajax({
		url : ctx + pathValue+"presentation/queryById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0){
				var result = data.result;				
				if(result){
					var template = result.template;
					/**
					 * 标题
					 */
					$("#title").html(process(template.title));
					$("#createor").append(process(template.createor));
					/**
					 * 记者
					 */
					$("#reporter").append(process(template.reporter));
					$("#tvStation").append(process(template.tvStationName))
					$("#program").append(process(template.program))
					$("#customType").append(process(template.customType))
					$("#titleType").append(process(template.titleType))
					/*20160220新添加字段开始*/
					/**
					 * 通讯员
					 */
					$("#repProviders").append(process(template.repProviders))
					/**
					 * 播报时间
					 */
					$("#playDate").append(process(template.playDate))
					/**
					 * 题花
					 */
					$("#titleDesign").append(process(template.titleDesign))
					/**
					 * 协作
					 */
					$("#assistants").append(process(template.assistants))
					/**
					 * 字幕词
					 */
					$("#subtitleWords").append(process(template.subtitleWords))
					/**
					 * 特约记者
					 */
					$("#specialReporters").append(process(template.specialReporters))
					/**
					 * 文稿
					 */
		        	$("#docs").append(process(template.docsContentHTML));
					/*20160220新添加字段结束*/
					
					/**
					 * 关键词
					 */
					$("#keyWords").append(process(template.keyWords));
					/**
					 * 来源
					 */
					$("#source").append(process(template.source));
					/**
					 * 主持人
					 */
					$("#presenter").append(process(template.presenter));
					/**
					 * 配音
					 */
					$("#dubbingMan").append(process(template.dubbingMan));
					/**
					 * 摄像
					 */
					$("#cameraMan").append(process(template.cameraMan));
					/**
					 * 编辑
					 */
					$("#editor").append(process(template.editor));
					var videos = result.videos;
					if(videos && videos.length > 0){
						 //预览区加载视频
						for(var i=0;i<videos.length;i++){
							var video = videos[i];
							videoAudioTempArr.push(video);
							tempMap.put(video["_id"], video["_id"]);
						}
						
					}
					var audios = result.audios;
					if(audios && audios.length > 0){
						for(var i=0;i<audios.length;i++){
							var audio = audios[i];
							videoAudioTempArr.push(audio);
							tempMap.put(audio["_id"], audio["_id"]);
						}
					}
					//默认加载视音频文件
					if(videos!=undefined&&videos.length>0){
			        	var wanurl=(videos[0].prewar==undefined?"":videos[0].prewar.wanurl)
			        	resourcesPreview(wanurl,"false","false");
				    }else{
			        	if(audios!=undefined&&audios.length>0){
				        	var wanurl=(audios[0].prewar==undefined?"":audios[0].prewar.wanurl)
				        	resourcesPreview(wanurl,"true","false");
				        }else{
				        	$("#player").append('<img src="'+ctx+'/common/images/material_not_pic3.jpg" width="100%">')
				        	$("#videoAudioDiv").append('<img src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
				        }
				     }
					if(videoAudioTempArr.length > 0){
						loadMediaHtml(videoAudioTempArr,"videoaudio");
					}
					var pics = result.pics;
					if(pics && pics.length > 0){
						for(var i=0;i<pics.length;i++){
							var pic = pics[i];
							picTempArr.push(pic);
							tempMap.put(pic["_id"], pic["_id"]);
							loadMediaHtml(picTempArr,"pic");
						}
					}else{
			        	$("#picDiv").append('<img src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
					}
					
					
				}
			}else{
				toastr.error("查询文稿失败！");
			}
					
		},
		error : function() {
			layer.close(index);
			toastr.error("查询文稿失败！");
		}
	});
}


/**
 * 根据媒体类型，返回对应的icon太样式
 * @param mType
 * @returns {String}
 */
function processType(mType){
	if(mType == 0){
		return "<b class=\"video_icon\"></b>";
	}else if(mType == 1){
		return "<b class=\"audio_icon\"></b>";
	}else{
		return "<b class=\"img_icon\"></b>";
	}
}
/**
 * 处理文件大小，字节转换为MB
 * @param fileSize
 * @returns
 */
function formartSize(fileSize){
	if(fileSize){
		fileSize = parseFloat(fileSize/1024/1024);
		return fileSize.toFixed(2);
	}
	return processValue(fileSize);
}
/**
 * 处理宽高
 * @param num
 * @returns
 */
function formartWidthHeight(num){
	if(num){
		return parseInt(num);
	}
	return processValue(num);
}
/**
 * 处理扩展信息的显示
 * @param othermsgObj
 * @param attributeName
 * @returns
 */
function processOthermsg(othermsgObj,attributeName){
	if(!othermsgObj){
		return processValue(othermsgObj);
	}
	if(!othermsgObj[attributeName]){
		return processValue(othermsgObj[attributeName]);
	}
	return othermsgObj[attributeName];
}
/**
 * 处理描述信息
 * @param describe
 * @returns
 */
function processDescribe(describe){
	if(!describe){
		return processValue(describe);
	}
}
/**
 * 所有信息如果不存在显示--
 * @param obj
 * @returns
 */
function process(obj){
	if(obj){
		return obj;
	}
	return "";
}
/**
 * 所有信息如果不存在显示--
 * @param obj
 * @returns
 */
function processValue(obj){
	if(obj){
		return obj;
	}
	return "--";
}
/**
 * 处理编码格式，视频返回h264，图片显示--
 * @param obj
 * @returns
 */
function processCode(){
	var mediaType = $("#mediaType").val();
	 if(mediaType == "pic"){
		 return "--";
	 }else{
		 return "h.264";
	 }	 
}
/**
 * 处理时长，将毫秒转换为：00：00：00（时：分：秒）格式
 * @param obj
 * @returns
 */
function processDuration(duration){

	var mediaType = $("#mediaType").val();
	 if(mediaType == "pic"){
		 return "";
	 }else{
		if(!duration){
			return processValue(duration);
		}
		var time = parseFloat(duration) / 1000;
		if (time) {
	       if (time > 60 && time < 60 * 60) {
	           time = "00:" + parseInt(time / 60.0) + ":" + parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60);
	       }else if (time >= 60 * 60 && time < 60 * 60 * 24) {
	           time = parseInt(time / 3600.0) + ":" + parseInt((parseFloat(time / 3600.0) -
	           parseInt(time / 3600.0)) * 60) + ":" +
	           parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
	           parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) ;
	       }else {
	           time = "00:"+"00:"+((parseInt(time)<10)?("0"+parseInt(time)):parseInt(time));
	       }
		}
	   return time;
	 }	 
}
/**
 * 获取缩略图地址，如果添加了视频，默认选择第一个视频的缩略图作为文稿的缩略图，如果没有视频，选择第一张图片的缩略图作为文稿的缩略图
 * @returns
 */
function getThumbnailurl(){
	if(videoAudioTempArr.length > 0){
		return videoAudioTempArr[0]["vslt"];
	}
	if(picTempArr.length > 0){
		return picTempArr[0]["vslt"];
	}
	return "";
}
/**
 * 处理缩略图上的播放小图标，图片无需显示，视频需要
 * @param wanurl
 * @returns {String}
 */
function processPlayerIco(wanurl,mtype){
	if(mtype == 2){
		 return "";
	 }else if(mtype == 0){
		 return "<i onclick=\"resourcesPreview('"+wanurl+"','false','true');\"></i>";
	 }	else if(mtype == 1){
		 return "<i onclick=\"resourcesPreview('"+wanurl+"','true','true');\"></i>";
	 }	 
}
//加载音视频
function loadMediaHtml(obj,mediaType){
	layer.closeAll('page');
	var html = [];	 
	
	if(mediaType == "pic"){
		$(obj).each(function(i,item){
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
			html.push("<div id=\""+item._id+"\" class=\"img_cont\">");
			html.push("<div class=\"img_div\">");
			html.push('<a href='+item.wanurl+' data-lightbox="example-1">')
			html.push("<img  src='"+vslt+"'> ");
			html.push("<div class=\"shadow\">");
			html.push("<p>更新时间："+processValue(item.uutime).split(" ")[0]+"</p>");
			html.push("</div> ");
			html.push('<a>')
			var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			html.push(processPlayerIco(wanurl,item.mtype));
	//		html.push("<b onclick=deleteMedia(\""+item._id+"\"); style=\"z-index:999\" class=\"del_img\"></b>");
			html.push("</div> ");
			html.push("<p title='"+item.name+"'>"+cutStr(item.name,10,"...")+"</p>");
			html.push("</div>");
			$("#picDiv").html(html.join(""));
			$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
		});
	}else{
		$("#videoAudioDiv ul").html('');
		$(obj).each(function(i,item){
			var avHtml=[];
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
			avHtml.push('<li id="'+item._id+'" class="img_cont">')					
	    	avHtml.push('<div class="img_div">'+
	    		'<img src="'+vslt+'"/>')
            var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
		    avHtml.push(processPlayerIco(wanurl,item.mtype));
            avHtml.push( '</div>')
			avHtml.push('<div class="video_cont">'+
	        	'<p class="video_cont_inner"title="'+item.name+'">'+cutStr(item.name,20,"...")+'</p>'+
	        	'<p class="video_cont_time" >'+processValue(item.uutime).split(" ")[0]+dealAVtypeValue(item,"cp")+'</p>'+
	        '</div>	')
            avHtml.push('</li>');
			$("#videoAudioDiv ul").append(avHtml.join(""));
			$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
			$(".video_audio_div li .video_cont").css("height",$(".video_audio_div li .img_div").height());
		})
	}
	var icon_w=$(".video_audio_div .img_div i").width();
	var icon_h=$(".video_audio_div .img_div i").height();
	$(".video_audio_div .img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	
} 
function dealAVtypeActive(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"active":"");
}
function dealAVtypeValue(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"<b>"+(avtype=="cp"?"成片":"")+"</b>":"");
}

function processPlayPic(){
	var mediaType = $("#mediaType").val();
	 if(mediaType == "pic"){
		 return "";
	 }else{
		 return "<i></i>";
	 }	 
}

//音视频区缩略图高度
setTimeout(function(){
	$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
},200)
$(window).resize(function(){
	$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
})

/** ***预览*** */
function resourcesPreview(wanurl,isAudio,isAutoPlay) {
$("#player").remove();
$("#previewFlashContent").html("<div id=\"player\"></div>");
	var swfVersionStr = "11.1.0";
	var d = new Date();
	var flashvars = {};
	flashvars.time = d.getTime();
	flashvars.assetsUrl = ctx + "/flash/preview/";
	flashvars.mediaURL = encodeURIComponent(wanurl);
	flashvars.playerType = "1";
	flashvars.autoHideControlBar="true";
	flashvars.autoPlay=isAutoPlay;
	flashvars.isAudio=isAudio;
	var xiSwfUrlStr = "playerProductInstall.swf";
	var preloaderUrl = flashvars.assetsUrl + "YsPlayer.swf"
			+ "?time=" + flashvars.time;// preloader.swf
	var params = {};
	params.quality = "high";
	params.bgcolor = "#000000";
	params.allowscriptaccess = "sameDomain";
	params.allowfullscreen = "true";
	params.wmode = "transparent";
	
	var attributes = {};
	attributes.id = "MoviePreviewer";
	attributes.name = "MoviePreviewer";
	attributes.align = "middle";
	swfobject.embedSWF(preloaderUrl, "player", "100%",
			"199px", swfVersionStr, xiSwfUrlStr, flashvars, params,
			attributes);
	swfobject.createCSS("#flashContent",
			"display:block;text-align:left;");

}