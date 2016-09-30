/**
 * 修改素材，添加成品的属性
 * @param mediaId 媒体资源id
 */
function addCPMedia(mediaId,This,event){
	event.stopPropagation();
	var videoAudioIndex;
	$(This).toggleClass("active");
	//li 上存标签的数组
	var extend=$(This).parents("li").attr("extend");
	if(!extend){
		extend=[];
	}else{
		if(!Array.isArray(extend)){
			extend=extend.split(',');
		//	extend=Array.of(extend);
		}
	}
	
	if(!$(This).hasClass("active")){
		var cpIndex=extend.indexOf("cp");
		extend.splice(cpIndex,1);
		$(This).parents(".img_cont").find(".video_cont_time b").remove();
	}else{
		extend.push("cp");
		$(This).parents(".img_cont").find(".video_cont_time").append("<b>成片</b>");
		
	}
	$(This).parents("li").attr("extend",extend);
}
/**
 * 文稿上传素材回调方法
 * @param mediaId
 */
function loadUploadHtml(mediaId){
	//数据库查询当前id
	var data=getMediaData(mediaId);
	if(data){
		var html = [];	 
		if(data.mtype == 2){
			$("[name=noPic]").remove();
			$(data).each(function(i,item){	
				var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/images/docs/loading.gif":(item.vslt+getMaterialResolution()));
				var vslt2=((item.vslt==undefined||item.vslt=='undefined')?"":item.vslt);
				html.push("<div id=\""+item._id+"\"  mtype=\""+item.mtype+"\"  class=\"img_cont\"  vslt=\""+vslt2+"\" >");
				html.push(dealUploadStatus(item,item.status,item._id,2));
				html.push("<div class=\"img_div\">");
				html.push('<a href='+item.wanurl+' data-lightbox="example-1">')
				html.push("<img  src='"+vslt+"'> ");
				html.push("<div class=\"shadow\">");
				html.push("<p>更新时间："+processValue(item.uutime).split(" ")[0]+"</p>");
				html.push("</div> ");
				html.push('<a>')
				//var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
				//html.push(processPlayerIco(wanurl,item.mtype));
				html.push("<b onclick=deleteMedia(\""+item._id+"\"); style=\"z-index:999\" class=\"del_img\"></b>");
				html.push("</div> ");
				html.push("<p title='"+item.name+"'>"+cutStr(item.name,10,"...")+"</p>");
				html.push("</div>");
				$("#picDiv").append(html.join(""));
			});
	    }else{
			//$("#videoAudioDiv ul").html('');
	    	 $("[name=noAv]").remove();
			$(data).each(function(i,item){
				var avHtml=[];
				var vslt;
				if(item.mtype==0){
					vslt=ctx+"/images/docs/loading.gif";
				}else if(item.mtype==1){
					vslt=ctx+"/images/docs/loading.gif"
				}
				var vslt2=((item.vslt==undefined||item.vslt=='undefined')?"":item.vslt)
				avHtml.push('<li id="'+item._id+'" status="'+item.status+'" mtype="'+item.mtype+'" vslt="'+vslt2+'"  class="img_cont">'+
			    '<div class="select_label">'+
			       '<p class="select_label_p" onclick="toggleAVLabel(this,event)">标签<b></b></p>'+
			       '<div class="select_label_option">'+
			         '<p>成片<i class="'+dealAVtypeActive(item,"cp")+'" onclick="addCPMedia(\''+item._id+'\',this,event)"></i></p>'+
			      ' </div>'+
			    '</div>	')	
				avHtml.push(dealUploadStatus(item,item.status,item._id,0));
		    	avHtml.push('<div class="img_div">'+
		    		'<img src="'+vslt+'"/>')
	           // var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			    //avHtml.push(processPlayerIco(wanurl,item.mtype));
			    avHtml.push("<a><b onclick=deleteMedia(\""+item._id+"\"); style=\"z-index:999\" class=\"del_img\"></b></a>");
	            avHtml.push( '</div>')
				avHtml.push('<div class="video_cont">'+
		        	'<p class="video_cont_inner"title="'+item.name+'">'+cutStr(item.name,20,"...")+'</p>'+
		        	'<p class="video_cont_time" ><i onclick="toTopAV(this,\''+item._id+'\')"></i>'+processValue(item.uutime).split(" ")[0]+dealAVtypeValue(item,"cp")+'</p>'+
		        '</div>	')
	            avHtml.push('</li>');
				$("#videoAudioDiv ul").append(avHtml.join(""));
				$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
				$(".video_audio_div li .video_cont").css("height",$(".video_audio_div li .img_div").height());
			})
		}	
		imiddle();
	}
	 $(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
}
function dealUploadStatus(item,status,id,type){
	if(status!=1&&status!=0){
		return "<input type='hidden' name='uploadStatus' value='"+id+"'>";
	}else{
		tempMap.put(id,id);
	}
}

/**
 * 查询素材数据
 * 
 * @param pageNow
 * @param pageSize
 */
function getMediaData(mediaId) {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", mediaId);
	var result;
	$.ajax({
		url : ctx + pathValue+"media/queryById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		cache : false,
		type : "post",
		dataType : "json",
		async:false,
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0){
				result= data;
			}else{
				toastr.error("查询素材详情失败");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询素材详情失败");
		}
	});
	return result;
}
refreshId = setInterval("refreshMediaStatus()", 5000);
function refreshMediaStatus() {
	var pulls = $("[name=uploadStatus]");
	var fails= $("[name=uploadFail]");
	var idsArray;
	if(pulls.length>0){
		$("#saveandendit").find("a:eq(0)").css("background","#e5e5e5");
		$("#saveandendit").find("a:eq(0)").attr("onclick","");
		$.each(pulls,function(i,item){
			if(i==0){
				idsArray=$(this).val();
			}else{
				idsArray+=","+$(this).val();
			}
		})
	}else{
		if(fails.length==0){
			$("#saveandendit").find("a:eq(0)").css("background","#4d90fe");
			$("#saveandendit").find("a:eq(0)").attr("onclick","addDocs();");
		}
		return ;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("ids", idsArray);
	$.ajax({
		url : ctx + pathValue+"media/queryByIds/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				return;
			}
			if(code == 0 && data.results.length > 0){
				for ( var i = 0; i < data.results.length; i++) {
					if(data.results[i].status==0||data.results[i].status==1){
						tempMap.put(data.results[i]._id,data.results[i]._id);
						var id="#"+data.results[i]._id;
						var mtype=$(id).attr("mtype");
						if(data.results[i].status==0){
							var vslt2=((data.results[i].vslt==undefined||data.results[i].vslt=='undefined')?"":data.results[i].vslt);
							$(id).attr("vslt",vslt2);
							$(id).find("[name=uploadStatus]").remove();
							var wanurl=(data.results[i].prewar==undefined?data.results[i].wanurl:data.results[i].prewar.wanurl);
							if(mtype==0){
								$(id).find("img").attr("src",(data.results[i].vslt+getMaterialResolution()))
								$(id).find("img").after(processPlayerIco(wanurl,data.results[i].mtype))
							}else if(mtype==1){
								$(id).find("img").attr("src",ctx+"/common/images/audio_list2.jpg")
								$(id).find("img").after(processPlayerIco(wanurl,data.results[i].mtype))
							}else if(mtype==2){
								$(id).find("img").attr("src",(data.results[i].vslt+getMaterialResolution()))
								$(id).find("a").eq(0).attr("href",data.results[i].wanurl)
							}
							imiddle();
						}
						if(data.results[i].status==1){
							$(id).attr("name","uploadFail");
							$(id).find("img").attr("src",ctx+"/images/docs/loading-fail.png")
							//$("i[processStatus]").parent().html("<i id=\"mStatus\" class=\"shibai\">失败</i><b>更新时间：<span>"+processValue(data.results[i].uutime)+"</span></b>");
						}
					}
				}
			}
		}					
	});
}
/**
 * 移除已添加的媒体文件，添加媒体文件时用媒体资源ID命名了div："<div id=\""+item._id+"\" class=\"img_cont\">"
 * @param mediaId 媒体资源id
 */
function deleteMedia(mediaId){
	/**
	 * 移除本身
	 */
	$("#"+mediaId).remove();
	tempMap.remove(mediaId);
	
}

/**
 * 获取缩略图地址，如果添加了视频，默认选择第一个视频的缩略图作为文稿的缩略图，如果没有视频，选择第一张图片的缩略图作为文稿的缩略图
 * @returns
 */
function getThumbnailurl(){
	//alert($("#videoAudioDiv li:eq(0)[status]").length)
	//首先遍历视音频
	var vslt;
	$("#videoAudioDiv").find("li").each(function(){
		if($(this).attr("vslt")){
			vslt=$(this).attr("vslt");
			return false;
		}
	})
	//没有遍历图片
	if(!vslt){
		$("#picDiv").find(".img_cont").each(function(){
			if($(this).attr("vslt")){
				vslt=$(this).attr("vslt");
				return false;
			}
		})
	}
	return vslt||"";
}

/**
 * 根据媒体类型，返回已经添加的视频、音频、图片唯一标识
 * @param mType
 * @returns {Array}
 */
function getMediaIds(mType){
	var arr = [];
	if(mType == 2){
		$("#picDiv").find(".img_cont").each(function(){
			arr.push({"id":$(this).attr("id")});
		})
	
	}else{
		$("#videoAudioDiv").find("li[mtype='"+mType+"']").each(function(){
			if($(this).attr("extend")){
				arr.push({"id":$(this).attr("id"),"extend":$(this).attr("extend")});
			}else{
				arr.push({"id":$(this).attr("id")});
			}
		})
	}
	return arr;
}

function imiddle(){
	var icon_w=$(".video_audio_div .img_div i").width();
	var icon_h=$(".video_audio_div .img_div i").height();
	$(".video_audio_div .img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
}

window.onbeforeunload = function (e) {
	  e = e || window.event;

	  // 兼容IE8和Firefox 4之前的版本
	  if (e) {
	    e.returnValue = '确定放弃对此文稿所有的操作?';
	  }

	  // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
	  return '确定放弃对此文稿所有的操作?';
	};
	
	