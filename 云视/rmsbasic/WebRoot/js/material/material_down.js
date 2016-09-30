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
	onairMap.put("id", mId);
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
				//resourcesPreview(data.prewar.wanurl);
				
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
 * 处理描述信息
 * 
 * @param describe
 * @returns
 */
function processDescribe(describe){
	if(!describe){
		return processValue(describe);
	}
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
	$("#fileSize").text(formartSize(obj.size));
	$("#rate").text(processValue(obj.rate) + " kps");
	$("#pixels").text(formartWidthHeight(obj.width)+" * "+formartWidthHeight(obj.height));
	$("#code").text("h.264");
	$("#fmt").text(processValue(obj.fmt));
	$("#happenPlace").text(processOthermsg(obj.othermsg,'happenPlace'));
	$("#happenTime").text(processOthermsg(obj.othermsg,'happenTime'));
	$("#desc").text(processDescribe(obj.describe));
	$("#updateTime").text(processValue(obj.uutime));
	if(mtype==2){
		$("#duration").remove();
	}else{
		$("#duration").text(MillisecondToDate(processValue(obj.duration)))
	}
	$("#title").text(processValue(obj.name))
	$("#title").attr("href",ctx+pathPage+'material/toMaterialPreview/?mId='+mId);
	$("#toPreview").attr("href",ctx+pathPage+'material/toMaterialPreview/?mId='+mId);
	if(obj.vslt!=undefined){
		$("#vslt").attr("src",processValue(obj.vslt));
	}else{
		$("#vslt").remove();
	}
	$("#viewMark").attr("href",(obj.prewar==undefined?"javascript:;":obj.prewar.wanurl))
	if(obj.defaults!=undefined && obj.defaults.length>0){
		$.each(obj.defaults,function(i,item){
			if(i==0){
				$("#playMark").attr("href",(item.wanurl==undefined?"javascript:;":item.wanurl))
			}
		})
	}
	
}
/** ***预览*** */
function resourcesPreview(wanurl) {
	var swfVersionStr = "11.1.0";
	var d = new Date();
	var flashvars = {};
	flashvars.time = d.getTime();
	flashvars.assetsUrl = ctx + "/flash/preview/";
	flashvars.mediaURL = encodeURIComponent(wanurl);
//	flashvars.mediaURL = encodeURIComponent("http://192.168.0.144?companyId=companyId&appCode=appCode&userId=userId&resId=7f723d854eb6469b932f3dab4e6caa1a&resType=0&cType=ld&tType=pc");
	flashvars.playerType = "1";
	flashvars.autoHideControlBar="true";

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

