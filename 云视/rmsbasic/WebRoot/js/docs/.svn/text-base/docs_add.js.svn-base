$(document).ready(function() {
	$("#form").validationEngine();
	//点击页面部分视音频标签隐藏
	$("body").click(function(){
		$(".select_label_option").hide();
	})
	
	//回车事件
	/*
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getDataList();
        }
    }; */
	/*20160220开始*/
	initUeditor();
	 $(".extended").click(function(){
			$(this).parent(".extended-div").find(".extended-drop").toggle();
		})
		$('.extended-div').click(function(e){
			e.stopPropagation();
		})
		
  /*20160220结束*/
	//初始化记者和所在电视台
	$("#tvStation").append(station(areaCode))
	$("#reporter").val(uname)
	$("#createor").text(uname)
	
	/**
	 * 文稿编辑添加图片弹窗
	 */ 
	$(".addPic").click(function() {
		
		/**
		 * 清空搜索值
		 */
		$("#search").val("");
		/**
		 * 设置隐藏域值为图片，用于区分是添加图片还是音视频
		 */
		$("#mediaType").val("pic");
		/**
		 * 显示弹窗内容
		 */
		$(".add_alert").show();
		/**
		 * 初始化分页插件
		 */
		$("#pageDiv").onairPage({"callback" : getDataList,"pageSize":6});
		/**
		 * 查询数据
		 */
		getDataList(1,6);
		keySearchEvent();
	});
	/**
	 * 文稿编辑添加视频弹窗
	 */
	$(".addVideo").click(function() {
		$("#search").val("");
		/**
		 * 设置隐藏域值为音视频，用于区分是添加图片还是音视频
		 */
		$("#mediaType").val("videoaudio");
		/**
		 * 显示弹窗内容
		 */
		$(".add_alert").show();
		/**
		 * 初始化分页插件
		 */
		$("#pageDiv").onairPage({"callback":getDataList,"pageSize":6});
		/**
		 * 查询数据
		 */
		getDataList(1,6);
		keySearchEvent();
	});	
	/**
	 * 关闭弹窗
	 */
	$(".close_icon_h i,.alert_true_btn").click(function() {
		$(".add_alert").hide();
	});
	//重置
	$("#rest_buttom").click(function(){
		layer.confirm('确认重置文稿？', {
		    btn: ['确认','取消'] 
		}, function(outIndex){
			layer.close(outIndex);
			$(".wengao_edit_div").find("input,textarea").each(function(i,item){
				$(this).val('');
			});
			$("#docsContent").val('');
		    $("#docsContentHTML").val('');
		    ue.setContent('');
		}, function(){
		});
	})
	/**
	 * 点击上传素材按钮，弹出上传插件
	 */
//	$('#uploadFile').attr('href', 'javascript:upload_open();');
	$('#uploadFile').click(function(){
	//	upload_open();
		document.getElementById('upload_cont').style.visibility='visible'
			$(".min_upload_icon").hasClass("max_upload_icon")&&$(".min_upload_icon").removeClass("max_upload_icon");
		$(".upload_cont").hasClass("max")&&$(".upload_cont").removeClass("max");
	})
	$("#uploadFile").mousedown(function(){
		$(this).addClass("click");
	})
	$("#uploadFile").mouseup(function(){
		$(this).removeClass("click");
	})
});
//取消隐藏列表
function hideConfirm(){
	$(".add_alert").hide();
}
/**
 * 创建文稿
 */
function addDocs(){
	var bool = $('#form').validationEngine('validate');
    if (!bool) {
    	return;
    }
	var index = layer.load();
	/**
	 * 标题
	 */
	var title = $("#title").val();
	var createor=uname;
	/**
	 * 记者
	 */
	var reporter = $("#reporter").val();
	/**
	 * 供片台标识
	 */
	var tvStationCode = $("#tvStation").val();
	/**
	 * 供片台名称
	 */
	var tvStationName = $("#tvStation").find("option:selected").text();
	
	/**
	 * 关键词
	 */
	var keyWords = $("#keyWords").val();
	/**
	 * 来源
	 */
	var source = $("#source").val();
	/**
	 * 主持人
	 */
	var presenter = $("#presenter").val();
	/**
	 * 配音
	 */
	var dubbingMan = $("#dubbingMan").val();
	/**
	 * 摄像
	 */
	var cameraMan = $("#cameraMan").val();
	/**
	 * 编辑
	 */
	var editor = $("#editor").val();
	
	/*20160220添加的字段开始*/
	/**
	 * 栏目
	 */
	var programNum=$("#program").val();
	var program=$("#program").find("option:selected").text();
	/**
	 * 通讯员
	 */
	var repProviders=$("#repProviders").val();
	/**
	 * 分类
	 */
	var  customTypeNum=$("#customType").val();
	var customType = $("#customType").find("option:selected").text();
	/**
	 * 播报时间
	 */
	var playDate=$("#playDate").val();
	/**
	 * 文稿类型
	 */
	var titleTypeNum =$("#titleType").val();
	var titleType =$("#titleType").find("option:selected").text();
	/**
	 * 题花
	 */
	var titleDesign=$("#titleDesign").val();
	/**
	 * 协作
	 */
	var assistants=$("#assistants").val();
	/**
	 * 字幕词
	 */
	var subtitleWords=$("#subtitleWords").val();
	/**
	 * 特约记者
	 */
	var specialReporters=$("#specialReporters").val();
	/**
	 * 文稿
	 */
	var ueContent = ue.getContent();
	var ueContentTxt = ue.getContentTxt();
	$("#docsContent").val(ueContentTxt);
	$("#docsContentHTML").val(ueContent);
	var docsContent=$("#docsContent").val();
	var docsContentHTML=$("#docsContentHTML").val();
	/*20160220添加的字段结束*/
	/**
	 * 构建参数
	 */
	 var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("title",title);
	 /**
	  * 构建文稿编目参数
	  */
	 var templateObj = new Object();
	 templateObj.title = title;
	 templateObj.program = program;
	 templateObj.createor = createor;
	 templateObj.reporter = reporter;
	 templateObj.tvStationName = tvStationName;
	 templateObj.tvStationCode = tvStationCode;
	 templateObj.customType = customType;
	 templateObj.keyWords = keyWords;
	 templateObj.source = source;
	 templateObj.presenter = presenter;
	 templateObj.dubbingMan = dubbingMan;
	 templateObj.cameraMan = cameraMan;
	 templateObj.editor = editor;
	 templateObj.repProviders = repProviders;	 
	 templateObj.titleType = titleType;	 
	 templateObj.playDate = playDate;	 
	 templateObj.titleDesign = titleDesign;	 
	 templateObj.assistants = assistants;	 
	 templateObj.subtitleWords = subtitleWords;	 
	 templateObj.specialReporters = specialReporters;	 
	 templateObj.content = docsContent;	 
	 templateObj.docsContentHTML = docsContentHTML;	 
	 templateObj.customTypeNum = customTypeNum;
	 templateObj.titleTypeNum = titleTypeNum;	
	 templateObj.programNum = programNum;
	 onairMap.put("template",templateObj);
	 onairMap.put("areaname",tvStationName);
	 onairMap.put("areacode",tvStationCode);
	 /**
	  * 来源
	  */
	 onairMap.put("src","webCreateDoc");
	 /**
	  * 缩略图
	  */
	 onairMap.put("thumbnailurl",getThumbnailurl());
	 /**
	  * 视频id数组
	  */
	 onairMap.put("videoIds",getMediaIds(0));
	 /**
	  * 音频id数组
	  */
	 onairMap.put("audioIds",getMediaIds(1));
	 /**
	  * 图片id数组
	  */
	 onairMap.put("picIds",getMediaIds(2));
//	 window.close();
//	 window.opener.location.href= ctx + pathValue+"presentation/query/";
//	 return;
	 //关闭提示效果
	 window.onbeforeunload = function (e) {
		
		};
	 /**
	  * 提交表单
	  */
	 $.ajax({
		 url : ctx + pathValue+"presentation/insert/",
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
				 toastr.success("文稿创建成功！");
				 window.opener.location.href="javascript:uCont('material/toDocs/')";
				 window.close();
			 }else{
				 toastr.error("文稿创建失败，请重试！");
			 }
		 },
		 error : function() {
			 layer.close(index);
			 toastr.error("文稿创建失败，请重试！");
		}
	});
}
/**
 * 查询素材信息
 * @param pageNow
 * @param pageSize
 */
function getDataList(pageNow, pageSize) {
	var index = layer.load();
	 if(!pageNow || !pageSize){
	 var pagePar = $("#pageDiv").getOnairPageParameter();
	 pageNow = pagePar.pageNow;
	 pageSize = pagePar.pageSize;
	 }
	 /**
	  * 搜索条件获取
	  */
	 var keyWord = $("#search").val();
	 if(!keyWord){
	 keyWord = "";
	 }
	 /**
	  * 构建查询参数
	  */
	 var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 /**
	  * 从隐藏域获取媒体类型，判断是查询音视频还是图片，mediaType：0（视频），1（音频），2（图片）
	  */
	 var mediaType = $("#mediaType").val();
	 if(mediaType == "pic"){
		 onairMap.put("mtype", ["2"]);
	 }else{
		 onairMap.put("mtype", ["0","1"]);
	 }	 
	 onairMap.put("keyWord", keyWord);
	 /**
	  * 分页条件
	  */
	 onairMap.put("currentPage", pageNow);
	 onairMap.put("pageNum", pageSize);
	 //直加载可用的 素材
	 onairMap.put("status", 0);
	 $.ajax({
		 url : ctx + pathValue+"media/query/",
		 headers:{"Content-Type":"application/json"},
		 data : onairMap.toJson(),
		 type : "post",
		 cache : false,
		 dataType : "json",
		 success : function(response) {
			 layer.close(index);
			 var code = response.code;
			 var data = response.data;
			 if(code == 0 && data.results.length > 0){
				 /**
				  * 数据不为空，渲染页面
				  */
				 loadVideoHtml(data);
				$("#inner").hide();
	       		$("#outter").show();
			 }else{
				$("#outter").hide();
	   			$("#inner").show();
	   			$("#inner").html('');
	   			$("#inner").append("<div class='no_data'></div>");
					//$("#listDiv").html("无相关查询结果！");
//					$("#listDiv").html("");
			 }
		 },
		 error : function() {
			 layer.close(index);
			 toastr.error("查询素材列表失败");
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
		return processValue(describe);
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
		if (null != time && "" != time) {
			if (time > 60 && time < 60 * 60) {
				time = "00:" + foramt(parseInt(time / 60.0)) + ":" + foramt(parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60)) + "";
			} else if (time >= 60 * 60 && time < 60 * 60 * 24) {
				time = foramt(parseInt(time / 3600.0) + ":" + foramt(parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)))
						+ ":"
						+ foramt(parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) - parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60))
						+ "";
			} else {
				time = "00:00:" + foramt(parseInt(time)) + "";
			}
		}
		return time;

	 }	 
}

/**
 * 为了避免重复添加音视频和图片，用此map储存已添加的音视频和图片的id，即key为id，值也为id
 * 每次添加音视频和图片到相应数组时都先判断此map是否已经存在相应的id
 */
var tempMap = new OnairHashMap();
/**
 * 选择媒体文件后，点击确认按钮触发此方法
 */
function processConfirm(){
	/**
	 * 从隐藏域获取当前操作的是音视频还是图片
	 */
	 var mediaType = $("#mediaType").val();
	 /**
	  * 迭代数据
	  */
	 $(".choice_icon").each(function () {
		 /**
		  * 判断是否选中
		  */
        if ($(this).is(":checked")) {
        	/**
        	 * 获取checkbox的value值，在渲染页面时将媒体文件的相关属性值存储在了checkbox的value上，并用逗号分隔
        	 * 包括且顺序为：_id:唯一标识，vslt:缩略图地址，name：文件名称，uutime:更新时间，wanurl：外网地址，mType：媒体类型
        	 */
        	var tempArr=[];
    		var tempObj = new Object();
    		tempArr.push( $(this).attr("id"));
    		if($(this).attr("mType")==0||$(this).attr("mType")==1){
    			tempArr.push( $(this).attr("vslt"));
    		}
    		if($(this).attr("mType")==2){
    			tempArr.push( $(this).attr("wanurl"));
    		}
    		tempArr.push( $(this).attr("materialName"));
    		tempArr.push( $(this).attr("uutime"));
    		tempArr.push( $(this).attr("wanurl"));
    		tempArr.push( $(this).attr("mType"));
    		//视频选取
    		tempObj._id = tempArr[0];
    		/**
    		 * 判断是否已经添加过该媒体文件
    		 */
    		if(!tempMap.containsKey(tempArr[0])){
    			tempObj.vslt = tempArr[1];
        		tempObj.name = tempArr[2];
        		tempObj.uutime = tempArr[3].split(" ")[0];
        		tempObj.wanurl = tempArr[4];
        		tempObj.mType = tempArr[5];
        		tempMap.put(tempArr[0], tempArr[0]);
            	loadMediaHtml(tempObj);
    		}	   	
        }
	  });
	 $(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
}
/**
 * 处理缩略图上的播放小图标，图片无需显示，视频需要
 * @param wanurl
 * @returns {String}
 */
function processPlayerIco(wanurl,mtype){
	var mediaType = $("#mediaType").val();
	 if(mtype == 2){
		 return "";
	 }else if(mtype == 0){
		 return "<i onclick=\"resourcesPreview('"+wanurl+"','false');\"></i>";
	 }	else if(mtype == 1){
		 return "<i onclick=\"resourcesPreview('"+wanurl+"','true');\"></i>";
	 }
}


function dealAVtypeActive(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"active":"");
}
function dealAVtypeValue(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"<b>"+(avtype=="cp"?"成片":"")+"</b>":"");
}
function toTopAV(This,id){
	$(This).parents(".img_cont").prependTo("#videoAudioDiv ul");
	/*for (var i=0 ;i<videoAudioTempArr.length;i++){
		if(videoAudioTempArr[i]["_id"]==id){
			videoAudioTempArr.splice(0,0,videoAudioTempArr[i]);
			videoAudioTempArr.splice(i+1,1);
		}
	}*/
}
/**
 * 视音频标签的显示隐藏
 * @param This
 * @param e
 */
function toggleAVLabel(This,e){
	$(This).nextAll(".select_label_option").toggle();
	e.stopPropagation();
}

function processPlayPic(){
	var mediaType = $("#mediaType").val();
	 if(mediaType == "pic"){
		 return "";
	 }else{
		 return "<i></i>";
	 }	 
}
function loadVideoHtml(obj) {
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];	 
	$(results).each(function(i,item){
		html.push("<div class=\"news_div\">");
		html.push("<input name=\"material\" type=\"checkbox\"  id='"+item._id+"' vslt='"+item.vslt+"' materialName='"+item.name+"' uutime='"+processValue(item.uutime)+"'wanurl='"+(item.prewar==undefined?item.wanurl:item.prewar.wanurl)+"' mtype='"+item.mtype+"' class=\"choice_icon\">");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"&mtype="+item.mtype+"\" target=\"_blank\">");
		//html.push("<div class=\"list_img_div noimg_div fl\">");
		html.push((item.mtype==1?"<div class=\"list_img_div  list_audio_div fl\">":"<div class=\"list_img_div fl\">"));
		var vslt;
		if(item.mtype==0){
			 vslt=((item.vslt=='undefined'||item.vslt==undefined)?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()));
		}else if(item.mtype==2){
			 vslt=((item.wanurl=='undefined'||item.wanurl==undefined)?ctx+"/common/images/audio_list2.jpg":(item.wanurl+getMaterialResolution()));
		}else{
			vslt=ctx+"/common/images/audio_list2.jpg";
		}
		html.push("<img  src=\""+vslt+"\">");
		html.push(processPlayPic());
		html.push("<p>"+processDuration(item.duration)+"</p>");
		html.push("</div> </a>");
		html.push("<div class=\"news_right fr\">");
		html.push("<h5>");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"&mtype="+item.mtype+"\" target=\"_blank\" title='"+processValue(item.name)+"'>"+cutStr(processValue(item.name),11,"...")+"</a>");
		html.push(processType(item.mtype));
		html.push("<p class=\"p_bot fr\" style='width:auto; margin-top:0;'>");
		html.push("<b style='width:180px;'>更新时间：<span>"+processValue(item.uutime)+"</span>");
		html.push("</b>");
		html.push("</p>");
		html.push("<div class=\"clear\"></div>");
		html.push("</h5>");
		html.push("<p>");
		html.push("<b>大小：<span>"+formartSize(item.size)+"</span>");
		if(item.mtype!=2){
			html.push("</b> <b>码率：<span>"+processValue(item.rate)+"Kbps</span>");
		}
		if(item.mtype!=1){
			html.push("</b> <b>分辨率：<span>"+formartWidthHeight(item.width)+" * "+formartWidthHeight(item.height)+"</span>");
		}
		if(item.mtype!=0&&item.mtype!=2){
			html.push("</b> <b>编码：<span>"+processCode()+"</span>");
		}
		html.push("</b> <b style='margin-right:0'>封装：<span>"+processValue(item.fmt)+"</span>");
		html.push("</b>");
		html.push("</p>");
		html.push("<p>");
		html.push("来源：<span>"+processValue(item.src)+"</span>");
		html.push("</p>");
		html.push("<p class=\"jianjie_p\" title='"+processDescribe(item.describe)+"'>");
		html.push("简介：<span >"+jianjie(processDescribe(item.describe),39,69)+"</span>");
		html.push("</p>");
		
		html.push("</div>");
		html.push("<div class=\"clear\"></div>");
		html.push("</div>");
	});
	$("#listDiv").html(html.join(""));
	var sobig_icon_w=$(".news_div .list_img_div i").width();
	$(".news_div .list_img_div i").css({"margin-left":-sobig_icon_w/2});
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}
var tvStation=areaMap();		
function station(station){
	var options="";
	$.each(tvStation,function(i,item){
		if(i==station){
			options+="<option value="+i+" selected>"+item+"</option>"
		}else{
			options+="<option value="+i+">"+item+"</option>"
		}
	})
	return options;
}
/** ***预览*** */
function resourcesPreview(wanurl,isAudio) {
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

function keySearchEvent(){
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	        getDataList(1,6);
	     }
   	};
}
/**
 * 通过添加已存在素材进行展示
 * @param obj
 */
function loadMediaHtml(obj){
	
	var mediaType = $("#mediaType").val();
	if(mediaType == "pic"){
		//$("#picDiv").find("[first]").remove();
		$(obj).each(function(i,item){	
			var html = [];	 
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
			var vslt2=((item.vslt==undefined||item.vslt=='undefined')?"":item.vslt)
			html.push("<div id=\""+item._id+"\" class=\"img_cont\" first vslt=\""+vslt2+"\" >");
			html.push("<div class=\"img_div\">");
			html.push('<a href='+item.wanurl+' data-lightbox="example-1">')
			html.push("<img  src='"+vslt+"'> ");
			html.push("<div class=\"shadow\">");
			html.push("<p>更新时间："+processValue(item.uutime).split(" ")[0]+"</p>");
			html.push("</div> ");
			html.push('<a>')
			var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			html.push("<b onclick=deleteMedia(\""+item._id+"\"); style=\"z-index:999\" class=\"del_img\"></b>");
			html.push("</div> ");
			html.push("<p title='"+item.name+"'>"+cutStr(item.name,10,"...")+"</p>");
			html.push("</div>");
			$("#picDiv").append(html.join(""));
		});
    }else{
		//$("#videoAudioDiv ul li").not("li[status]").remove();
		$(obj).each(function(i,item){
			var avHtml=[];
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
			var vslt2=((item.vslt==undefined||item.vslt=='undefined')?"":item.vslt)
			avHtml.push('<li id="'+item._id+'" class="img_cont" vslt="'+vslt2+'" mtype="'+item.mType+'">'+
		    '<div class="select_label">'+
		       '<p class="select_label_p" onclick="toggleAVLabel(this,event)">标签<b></b></p>'+
		       '<div class="select_label_option">'+
		         '<p>成片<i class="'+dealAVtypeActive(item,"cp")+'" onclick="addCPMedia(\''+item._id+'\',this,event)"></i></p>'+
//		         '<p>X&nbsp;&nbsp;X<i></i></p>'+
//		         '<p>X&nbsp;&nbsp;X<i></i></p>'+
		      ' </div>'+
		    '</div>	')					
	    	avHtml.push('<div class="img_div">'+
	    		'<img src="'+vslt+'"/>')
            var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
		    avHtml.push(processPlayerIco(wanurl,item.mType));
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
