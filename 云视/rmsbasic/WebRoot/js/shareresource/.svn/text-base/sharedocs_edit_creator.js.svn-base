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
//成品
var productArr=[];
var ue ;
$(document).ready(function() {
	$("#form").validationEngine();
	//点击页面部分视音频标签隐藏
	$("body").click(function(){
		$(".select_label_option").hide();
	})
	/*20160220开始*/
	initUeditor();
	 $(".extended").click(function(){
			$(this).parent(".extended-div").find(".extended-drop").toggle();
		})
	 $('.extended-div').click(function(e){
		e.stopPropagation();
	 })
		
 /*20160220结束*/
	findByShareDocId();
	//回车事件
	/*document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getDataList();
        }
    }; */
	//重置
	$("#docsRest").click(function(){
		layer.confirm('确认重置文稿？', {
		    btn: ['确认','取消'] 
		}, function(outIndex){
			layer.close(outIndex);
			$(".wengao_edit_div").find("input,textarea").each(function(i,item){
				if($(this).attr("id")=="reporter"){
					return;
				}
				$(this).val('');
			})
			$("#docsContent").val('');
		    $("#docsContentHTML").val('');
			ue.setContent('');
		}, function(){
		});
	})
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
	$('#uploadFile').click(function(){
		//upload_open();
		$(".upload_cont").css("visibility","visiable");
	})
	$("#uploadFile").mousedown(function(){
		$(this).addClass("click");
	})
	$("#uploadFile").mouseup(function(){
		$(this).removeClass("click");
	})
});



//音视频区缩略图高度
setTimeout(function(){
	$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
},200)
$(window).resize(function(){
	 $(".video_audio_div li .video_cont").css("height",$(".video_audio_div li .img_div").height());
	$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
	$(".video_audio_div .img_div img2").css("height",$(".video_audio_div .img_div img").height());
})



//取消隐藏列表
function hideConfirm(){
	$(".add_alert").hide();
}
function findByShareDocId(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("fieldNewsId", $("#sharedocId").val());
	$.ajax({
		url : ctx + pathValue+"fieldNews/findFieldNewsById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var result = response.data;
			if(code == 0){
				//var result = data.result;				
				if(result){
					
					var template = result.template;
					/**
					 * 标题
					 */
					$("#title").val(process(template.title));
				
					
					$("#createor").text(process(template.createor));
					/**
					 * 记者
					 */
					$("#reporter").val(process(template.reporter));
					
					$("#tvStation").append(station(tvStation,template.tvStationCode))
					
					
					/**
					 * 关键词
					 */
					$("#keyWords").val(process(template.keyWords));
					/**
					 * 来源
					 */
					$("#source").val(process(template.source));
					/**
					 * 主持人
					 */
					$("#presenter").val(process(template.presenter));
					/**
					 * 配音
					 */
					$("#dubbingMan").val(process(template.dubbingMan));
					/**
					 * 摄像
					 */
					$("#cameraMan").val(process(template.cameraMan));
					/**
					 * 编辑
					 */
					$("#editor").val(process(template.editor));
					/*20160220新添加字段开始*/
					/**
					 * 栏目
					 */
					$("#program").append(station(programJson,template.programNum));
					/**
					 * 通讯员
					 */
					$("#repProviders").val(process(template.repProviders))
					/**
					 * 分类
					 */
					$("#customType").append(station(customTypeJson,template.customTypeNum));
					/**
					 * 播报时间
					 */
					$("#playDate").val(process(template.playDate))
					/**
					 * 文稿类型
					 */
					$("#titleType").append(station(titleTypeJson,template.titleTypeNum));
					/**
					 * 题花
					 */
					$("#titleDesign").val(process(template.titleDesign))
					/**
					 * 协作
					 */
					$("#assistants").val(process(template.assistants))
					/**
					 * 字幕词
					 */
					$("#subtitleWords").val(process(template.subtitleWords))
					/**
					 * 特约记者
					 */
					$("#specialReporters").val(process(template.specialReporters))
					/**
					 * 文稿
					 */
					$("#docsContent").val(process(template.content));
		        	$("#docsContentHTML").val(process(template.docsContentHTML));
		        	ue.ready(function() {
		        		ue.setContent(template.docsContentHTML);
		        		//ueditor.setContent('UEditor报错TypeError: me.body is undefined');
		        		});
		        	/**
		        	 * 来源
		        	 */
					$("#src").val(process(result.src))
					/*20160220新添加字段结束*/
					var videos = result.videos;
					if(videos && videos.length > 0){
						for(var i=0;i<videos.length;i++){
							videos[i].wanurl=videos[i].prewar.wanurl
							var video = videos[i];
							tempMap.put(video["_id"], video["_id"]);
							loadMediaHtml(video,"videoaudio");
							
						}
						
					}
					var audios = result.audios;
					if(audios && audios.length > 0){ 
						for(var i=0;i<audios.length;i++){
							var audio = audios[i];
							tempMap.put(audio["_id"], audio["_id"]);
							loadMediaHtml(audio,"audio");
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
				        	$("#videoAudioDiv").append('<img name="noAv" src="'+ctx+'/common/images/material_not_tip.png" width="100%">')

				        }
				     }
					var pics = result.pics;
					if(pics && pics.length > 0){
						for(var i=0;i<pics.length;i++){
							var pic = pics[i];
							tempMap.put(pic["_id"], pic["_id"]);
							loadMediaHtml(pic,"pic");
						}
					}else{
			        	$("#picDiv").append('<img name="noPic" src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
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
	var tvStation=areaMap();
var programJson={"0":"新闻联播"};
var customTypeJson={"0":"国内新闻","1":"国际新闻"};
var titleTypeJson={"0":"标题","1":"口播","2":"片头"};
function station(objectJson,station){
	var options="";
	$.each(objectJson,function(i,item){
		if(i==station){
			options+="<option value="+i+" selected>"+item+"</option>"
		}else{
			options+="<option value="+i+">"+item+"</option>"
		}
	})
	return options;
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
	 onairMap.put("id",$("#sharedocId").val());
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
	 //关闭提示效果
	 window.onbeforeunload = function (e) {
			
		};
	 /**
	  * 提交表单
	  */
	 $.ajax({
		 url : ctx + pathValue+"fieldNews/updateFieldNews/",
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
				 toastr.success("共享文稿修改成功！");
				// window.opener.location.href="javascript:uCont('material/toDocs/')";
				 window.close();
			 }else{
				 toastr.error("共享文稿修改失败，请重试！");
			 }
		 },
		 error : function() {
			 layer.close(index);
			 toastr.error("共享文稿改失败，请重试！");
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
    		tempObj._id = tempArr[0];
    		
    		/**
    		 * 判断是否已经添加过该媒体文件
    		 */
    		if(!tempMap.containsKey(tempArr[0])){
    			tempObj.vslt = tempArr[1];
        		tempObj.name = tempArr[2];
        		tempObj.uutime = tempArr[3].split(" ")[0];
        		tempObj.wanurl = tempArr[4];
        		tempObj.mtype = tempArr[5];
        		tempMap.put(tempArr[0], tempArr[0]);
            	if(mediaType == "pic"){
        			$("[name=noPic]").remove()
            		/**
            		 * 如果是图片放入图片数组中
            		 */
            		loadMediaHtml(tempObj,mediaType);
            		
            		
    	       	 }else{
    	       		 $("[name=noAv]").remove()
    	       		/**
             		 * 如果是音视频，放入音视频数组中
             		 */
    	       		loadMediaHtml(tempObj,mediaType);
    	       	 }	     
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
	 if(mtype == 2){
		 return "";
	 }else if(mtype == 0){
		 return "<i onclick=\"resourcesPreview('"+wanurl+"','false','true');\"></i>";
	 }	else if(mtype == 1){
		 return "<i onclick=\"resourcesPreview('"+wanurl+"','true','true');\"></i>";
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
var index;
function loadVideoHtml(obj) {
	$("#listDiv").html("");
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];	 
	$(results).each(function(i,item){
		html.push("<div class=\"news_div\">");
		html.push("<input name=\"material\" type=\"checkbox\"  id='"+item._id+"' vslt='"+item.vslt+"' materialName='"+item.name+"' uutime='"+processValue(item.uutime)+"'wanurl='"+(item.prewar==undefined?item.wanurl:item.prewar.wanurl)+"' mtype='"+item.mtype+"' class=\"choice_icon\">");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"\" target=\"_blank\">");
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
		html.push("<img src=\""+vslt+"\">");
		html.push(processPlayPic());
		html.push("<p>"+processDuration(item.duration)+"</p>");
		html.push("</div> </a>");
		html.push("<div class=\"news_right fr\">");
		html.push("<h5>");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"\" target=\"_blank\" title='"+processValue(item.name)+"'>"+cutStr(processValue(item.name),11,"...")+"</a>");
		html.push(processType(item.mtype));
		html.push("<p class=\"p_bot fr\" style='width:auto; margin-top:0;'>");
		html.push("<b style='width:130px;'>更新时间：<span>"+processValue(item.uutime).split(" ")[0]+"</span>");
		html.push("</b>");
		html.push("</p>");
		html.push("<div class=\"clear\"></div>");
		html.push("</h5>");
		html.push("<p>");
		html.push("<b>大小：<span>"+formartSize(item.size)+"</span></b> ");
		if(item.mtype!=2){
			html.push("<b>码率：<span>"+processValue(item.rate)+"Kbps</span></b> ");
		}
		if(item.mtype!=1){
			html.push("<b>分辨率：<span>"+formartWidthHeight(item.width)+" * "+formartWidthHeight(item.height)+"</span></b> ");
		}
		if(item.mtype!=0&&item.mtype!=2){
			html.push("<b>编码：<span>"+processCode()+"</span></b>");
		}
		html.push(" <b style='margin-right:0'>封装：<span>"+processValue(item.fmt)+"</span>");
		html.push("</b>");
		html.push("</p>");
		html.push("<p>");
		html.push("来源：<span>"+processValue(item.src)+"</span>");
		html.push("</p>");
		html.push("<p class=\"jianjie_p\" title='"+processDescribe(item.describe)+"'>");
		html.push("简介：<span>"+jianjie(processDescribe(item.describe),39,69)+"</span>");
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
	flashvars.isVideo=isAudio;
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
function loadMediaHtml(obj,mediaType){
	layer.closeAll('page');
	if(mediaType == "pic"){
		//$("#picDiv").find("[first]").remove();
		$(obj).each(function(i,item){
			var html = [];	 
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
			var vslt2=((item.vslt==undefined||item.vslt=='undefined')?"":item.vslt);
			html.push("<div id=\""+item._id+"\" class=\"img_cont\" first vslt=\""+vslt2+"\">");
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
			html.push("<b onclick=deleteMedia(\""+item._id+"\"); style=\"z-index:999\" class=\"del_img\"></b>");
			html.push("</div> ");
			html.push("<p title='"+item.name+"'>"+cutStr(item.name,10,"...")+"</p>");
			html.push("</div>");
			$("#picDiv").append(html.join(""));
			$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
		});
	}else{
		//$("#videoAudioDiv ul li").not("li[status]").remove();
		$(obj).each(function(i,item){
			var avHtml=[];
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
			var vslt2=((item.vslt==undefined||item.vslt=='undefined')?"":item.vslt);
			var extend2=(item.extend==undefined?"":item.extend);
			avHtml.push('<li id="'+item._id+'" class="img_cont" vslt="'+vslt2+'" extend="'+extend2+'" mtype="'+item.mtype+'">'+
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
		    avHtml.push(processPlayerIco(wanurl,item.mtype));
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
	var icon_w=$(".video_audio_div .img_div i").width();
	var icon_h=$(".video_audio_div .img_div i").height();
	$(".video_audio_div .img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	
}
/**
 * 获取缩略图
 * @returns {Boolean}
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