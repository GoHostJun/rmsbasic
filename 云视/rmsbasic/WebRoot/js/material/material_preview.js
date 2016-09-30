$(document).ready(function() {
	
	getData();
	
});
/**
 * 查询数据
 * 
 * @param pageNow
 * @param pageSize
 */
function getData() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", $("#mId").val());
	$.ajax({
		url : ctx + pathValue+"media/queryById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		cache : false,
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0){
				loadHtml(data);
				if(data.mtype==0){
					resourcesPreview(data.prewar.wanurl,"false");
				}else if(data.mtype==1){
					resourcesPreview(data.prewar.wanurl,"true");
				}else{
					$("#previewFlashContent").append("<img src='"+data.wanurl+"'>")
				}
				/*
				if(data.prewar==undefined){
					$("#previewFlashContent").append("<img src='"+data.wanurl+"'>")
				}else{
					if(data.mtype==0){
						resourcesPreview(data.prewar.wanurl,"true");
					}else{
						resourcesPreview(data.prewar.wanurl,"false");
					}
				}*/
				
			}else{
				toastr.error("查询素材详情失败");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询素材详情失败");
		}
	});
}
/**
 * 所有信息如果不存在显示--
 * 
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
 * 处理文件大小
 * 
 * @param fileSize
 * @returns
 */
function formartSize(fileSize){
	if(fileSize){
		if(parseFloat(fileSize/1024/1024) > 1){
			fileSize = parseFloat(fileSize/1024/1024);
			return fileSize.toFixed(2) + " MB";
		}else{
			fileSize = parseFloat(fileSize/1024);
			return fileSize.toFixed(2) + " KB";
		}
	}
	return processValue(fileSize);
}

/**
 * 处理宽高
 * 
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
 * 
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


function loadHtml(obj){
	$("#title").append(processValue(obj.name))
	if(mtype==2){
		$("#titletype").text("图片");
		$("#preId").find("ul").hide();
		$("#preId").find(".tab-div:eq(0)").hide();
		$("#preId").find(".tab-div:eq(4)").show();
		setInputValue(4,obj);
	}else if(mtype==1){
		$("#titletype").text("音频");
		$("#preId").find("ul").hide();
		$("#preId").find(".tab-div:eq(0)").hide();
		$("#preId").find(".tab-div:eq(3)").show();
		setInputValue(3,obj);
	}else if(mtype==0){
		$("#titletype").text("视频");
		setInputValue(0,obj);
		if(obj.defaults){
			var defaults=obj.defaults;
			if(defaults.length>0){
				var supDefault;
				var shdDefault;
				var hdDefault;
				var sdDefault;
				var ldDefault;
				$.each(defaults,function(i,item){
					if (item.ctype=="shd"){
						shdDefault=item;
					}else if(item.ctype=="hd"){
						hdDefault=item;
					}else if(item.ctype=="sd"){
						sdDefault=item;
					}else if(item.ctype=="ld"){
						ldDefault=item;
					}
				})
				if(shdDefault){
					supDefault=shdDefault;
				}else if(hdDefault){
					supDefault=hdDefault;
				}
				/*else if(sdDefault){
					supDefault=sdDefault;
				}*/
				if(supDefault){
					setInputValue(1,supDefault);
				}
				if(sdDefault){
					setInputValue(2,sdDefault);
				}
			}
		}
	}
	
	
}
/**
 * 
 * @param type 0：原视频，1：高清视频，2：低清视频（转码最低的），3：音频，4：图片
 * @param obj
 */
function setInputValue(type,obj){
	var prekey;
	if(type==0){
		prekey='';
	}else if(type==1){
		prekey='super';
	}else if(type==2){
		prekey='pre';
	}else if(type==3){
		prekey='audio';
	}else if(type==4){
		prekey='pic';
	}
	
	$("#"+prekey+"fileSize").text(formartSize(obj.size));
	$("#"+prekey+"rate").text(processValue(obj.rate) + " Kbps");
	$("#"+prekey+"pixels").text(formartWidthHeight(obj.width)+" * "+formartWidthHeight(obj.height));
	$("#"+prekey+"format").text(processValue(obj.format));
	$("#"+prekey+"vedioSize").text(processValue(obj.vedioSize));
	$("#"+prekey+"duration").text(MillisecondToDate(processValue(obj.duration)));
	$("#"+prekey+"fmt").text(processValue(obj.fmt));
	$("#"+prekey+"happenPlace").text(processOthermsg(obj.othermsg,'happenPlace'));
	$("#"+prekey+"happenTime").text(processOthermsg(obj.othermsg,'happenTime'));
	$("#"+prekey+"descSpan").attr("title",processDescribe(obj.describe));
	$("#"+prekey+"desc").html(cutTitleString(processDescribe(obj.describe),100,"..."));
	$("#"+prekey+"updateTime").text(processValue(obj.uutime));
	$("#"+prekey+"source").text(processValue(obj.src));
	$("#"+prekey+"channels").text(processValue(obj.channels));
}
function processDescribe(describe){
	if(describe){
		return processValue(describe);
	}
}
/** ***预览*** */
function resourcesPreview(wanurl,isAudio) {
	var swfVersionStr = "11.1.0";
	var d = new Date();
	var flashvars = {};
	flashvars.time = d.getTime();
	flashvars.assetsUrl = ctx + "/flash/preview/";
	flashvars.mediaURL = encodeURIComponent(wanurl);
//	flashvars.mediaURL = encodeURIComponent("http://192.168.0.144?companyId=companyId&appCode=appCode&userId=userId&resId=7f723d854eb6469b932f3dab4e6caa1a&resType=0&cType=ld&tType=pc");
	flashvars.playerType = "1";
	flashvars.autoHideControlBar="true";
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
	swfobject.embedSWF(preloaderUrl, "previewFlashContent", "100%",
			"500px", swfVersionStr, xiSwfUrlStr, flashvars, params,
			attributes);
	swfobject.createCSS("#flashContent",
			"display:block;text-align:left;");

}
//20160516 by lsj
function tabChange(This){
	$(".preview_right .tab li").eq($(This).index()).addClass("active").siblings().removeClass('active');
	$(".tab-div").hide().eq($(This).index()).show();
}