$(document).ready(function() {
	$("#indexPop").hide();
	$("#indexJX").hide();
	$("#addDocs").attr("href",ctx+pathPage+"material/toAddDocs/"); 
	$("#pageDiv").onairPage({"callback":getListData});
	getListData();
	$("#check").click(function(){
		if( $("#listDiv :radio:checked").length<1   ){
			toastr.error("至少选择一条记录");
		}else{
			layer.confirm('确认引用文稿？', {
			    btn: ['确认','取消'] 
			}, function(outIndex){
				referDoc(outIndex);
			}, function(){
			});
			
		}
		
	})
	//搜索
	$("#searchBotton").click(function(){
		getListData();
	})
	//回车事件
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getListData();
        }
    }; 
	
});

function referDoc(outIndex){
	layer.close(outIndex);
	var index = layer.load();
	var map = new OnairHashMap();
	var videoIds=new Array();
	var audioIds=new Array();
	var picIds=new Array();
	map.put("accessToken", getAccessToken());
	map.put("timeStamp", getTimeStamp());
	map.put("id", $("#listDiv :radio:checked").val())
	if(mtype==0){
		videoIds.push(id);
		map.put("videoIds", videoIds);
	}else if(mtype==1){
		map.put("audioIds", audioIds);
		audioIds.push(id);
	}else if(mtype==2){
		map.put("picIds", picIds);
		picIds.push(id);
	}
	$.ajax({
		url : ctx + pathValue+"presentation/addMedia/",
		headers:{"Content-Type":"application/json"},
		data : map.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0 ){
				toastr.success("引用成功");
				window.opener.location.href="javascript:uCont('material/toMaterial/')";
				 window.close();
			}else{
				toastr.error("引用失败");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("引用失败");
		}
	});
}

function getListData(pageNow,pageSize){
	var index = layer.load();
	if(!pageNow
|| !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	var keyWord = $("#search").val();
	if(!keyWord){
		keyWord = "";
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("keyWord", keyWord);
	onairMap.put("mediaId", id);
	onairMap.put("mtype", mtype);
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	$.ajax({
		url : ctx + pathValue+"presentation/query/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code != 0){
				toastr.error("查询文稿列表失败");
				return;
			}
			if(code == 0 && data.results.length > 0){
				$("#inner").hide();
	       		$("#outter").show();
				loadHtml(data);
			}else{
				$("#outter").hide();
       			$("#inner").show();
       			$("#inner").html('');
       			$("#inner").append("<div class='no_data'></div>");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询文稿列表失败");
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
 * 处理媒体类型显示的图标
 * @param mType
 * @returns {String}
 */
function processIcon(videos,audios,pics){
	if(videos && videos.length > 0){
		return "<b class=\"video_icon\"></b>";
	}else if(audios && audios.length > 0){
		return "<b class=\"audio_icon\"></b>";
	}else if(pics && pics.length > 0){
		return "<b class=\"img_icon\"></b>";
	}else {
		return "";
	}
}
/**
 * 处理视音频总数显示
 * @param mType
 * @returns {String}
 */
function processVideoAudioNum(videos,audios){
	var count = 0;
	if(videos && videos.length > 0){
		count += videos.length;
	}else if(audios && audios.length > 0){
		count += audios.length;
	}
	return count;
}
/**
 * 处理视音频总数显示
 * @param mType
 * @returns {String}
 */
function processPicsNum(pics){
	var count = 0;
	if(pics && pics.length > 0){
		count +=  pics.length;
	}
	return count;
}
/**
 * 处理扩展信息的显示
 * 
 * @param othermsgObj
 * @param attributeName
 * @returns
 */
function processTemplateInfo(templateObj,attributeName){
	if(!templateObj){
		return processValue(templateObj);
	}
	if(!templateObj[attributeName]){
		return processValue(templateObj[attributeName]);
	}
	return templateObj[attributeName];
}
function processThumbnailurl(item,durationDate){
	var html = [];
	if(item.thumbnailurl&&item.thumbnailurl!="undefined"){
		html.push("<div class=\"list_img_div fl\">");
		if(item.thumbnailurl!='undefined'){
			html.push("<img src=\""+item.thumbnailurl+getMaterialResolution()+"\"/>");
		}
	//	html.push("<div class=\"changci\"><span>唱词</span></div>");
		html.push(getIcon(item)); 		
		html.push("<p>"+durationDate+"</p>"); 		
		html.push("</div>"); 		
	}else{
		if(item.audios!=undefined&&item.audios.length>0){
			html.push("<div class=\"list_img_div list_audio_div fl\">");
		}else{
			html.push("<div class=\"list_img_div noimg_div  fl\">");
		}
	//	html.push("<div class=\"changci\"><span>唱词</span></div>");
		html.push(getIcon(item)); 
		html.push("<p>"+durationDate+"</p>"); 		
		html.push("</div>");
	}
	return html.join("");
}
function getIcon(data){
	if(data.videos!=undefined&&data.videos.length>0){
		var icon='<i><b class="video_icon"></b></i>';
	}else if(data.audios!=undefined&&data.audios.length>0){
		var icon='<i><b class="audio_icon"></b></i>';
	}else{
		var icon='<b class="img_icon"></b>';
	}
	return icon;
}

/**
 * 状态显示处理
 * @param status
 * @returns {String}
 */
function processStatus(status){
	if(status == 10){
		return "<i id=\"mStatus\" class=\"chulizhong\">服务中</i>";
	}else if(status == 11){
		return "<i id=\"mStatus\" class=\"keyong\">服务完成</i>";
	}else{
		return "";
	}
}
/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	$(results).each(function(i,item){	
		var duration=getAVPinfo(item).duration;
		var durationDate="";
		if(duration!=0){
			durationDate=MillisecondToDate(duration)
		}
		html.push("<div class=\"news_div\">");
		html.push("<input type=\"radio\" name='docId' class=\"choice_icon\" value='"+item._id+"'>"); 
		html.push("<a href=\""+ctx+pathPage+"material/toViewDocs/?docId="+item._id+"\" target=\"_blank\">");
		html.push(processThumbnailurl(item,durationDate));
		html.push("</a>");
		html.push("<div class=\"news_right fr\">");
		html.push("<h5>");
		html.push("<a href=\""+ctx+pathPage+"material/toViewDocs/?docId="+item._id+"\" target=\"_blank\" title='"+processValue(item.title)+"'>"+cutTitleString(processValue(item.title),40,"...")+"</a>"); 
		html.push(processIcon(item.videos,item.audios,item.pics));
		html.push("<div class=\"clear\"></div>");
		html.push("</h5>");
		html.push("<p>");
		html.push("<b>供片台：<span>"+processTemplateInfo(item.template,'tvStationName')+"</span></b>"); 
		html.push("<b title='"+processTemplateInfo(item.template,'reporter')+"'>记者：<span>"+cutStr(processTemplateInfo(item.template,'reporter'),5,"...")+"</span></b>"); 
		html.push("<b title='"+processTemplateInfo(item.template,'keyWords')+"'>关键词：<span>"+cutStr(processTemplateInfo(item.template,'keyWords'),9,"...")+"</span></b>");
		html.push("<b>创建人：<span>"+processValue(item.cusename)+"</span></b>");
		html.push("</p>");
		html.push("<p>视音频：<span>"+processVideoAudioNum(item.videos,item.audios)+"</span>&nbsp;&nbsp;|&nbsp;&nbsp;图片：<span>"+processPicsNum(item.pics)+"</span></p>");
		html.push("<p class=\"jianjie_p\" title='"+processTemplateInfo(item.template,'content')+"'>简介：<span>"+jianjie(processTemplateInfo(item.template,'content'),46,76)+"</span></p>");
		html.push("<p class=\"p_bot\">"+processStatus(item.status)+"<b>更新时间：<span>"+processValue(item.uutime)+"</span></b></p>");
		html.push("</div>");
		html.push("<div class=\"clear\"></div>");
		html.push("</div>");
	});
	$("#listDiv").html(html.join(""));
	//素材下载、通联创建圆圈居中的样式
	var icon_w=$(".list_img_div i").width();
	var icon_h=$(".list_img_div i").height();
	$(".list_img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}
function createNews(id){
	uCont('news/toNewsCreate/?_id='+id)
}
//获取时长 视频音频和图片数量
function getAVPinfo(data){
	var duration=0;
	var avCount=0;
	var pCount=0;
	if(data.videos!=undefined&&data.videos.length>0){
		$.each(data.videos,function(i,item){
			if(item.duration!=undefined){
				duration+=item.duration;
			}
			avCount++;
		})
	}
	if(data.audios!=undefined&&data.audios.length>0){
		$.each(data.audios,function(i,item){
			if(item.duration!=undefined){
				if(duration==0){
					duration+=item.duration;
				}
			}
		
			avCount++;
		})
	}
	if(data.pics!=undefined&&data.pics.length>0){
		$.each(data.pics,function(i,item){
			pCount++;
		})
	}
	return {"duration":duration,"avCount":avCount,"pCount":pCount};
}