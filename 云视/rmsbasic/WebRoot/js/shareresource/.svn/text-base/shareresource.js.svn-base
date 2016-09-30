$(function(){
	$("#ascrail2000").remove();
	$(".tab li").click(function(){
	 	$(".tab li").eq($(this).index()).addClass("active").siblings().removeClass('active');
	 	
	 	
	 	$(".news-style-div > div").hide().eq($(this).index()).show();
	 	if($(this).index()==1){
	 		$(".city-list").hide();
	 		$("#search").val('');
	 		//请求url
	 		//$("#pageDiv").remove();
	 		uContDiv("shareresource/toShareOutsize/","sharecoutsize")
	 		//$("#sharecoutsize").html()
	 	}else{
	 		uCont('shareresource/toShareList/');
	 		$(".city-list").show();
	 	}
 	})
	
	$(".news-style-div>div:eq(1)").hide();
	$(".left-list-ul").find("h5,.news-img,.list_icon5").click(function(){
		$(this).parents(".left-list-ul").find(".news-popup").css("display","block");
	})
	$(".left-list-ul .list_icon7").click(function(){
		$(".tuicong-popup").css("display","block");
	})
	$("#closeDetial").click(function(){
		$("#detail").hide();
	})
	$("#pageDiv").onairPage({"callback":getListData});
	getListData();
	loadCity();
	loadNoticeDay();
	loadNoticeFocus();
	loadNewsClickSort();
	loadnewsCount();
	
$("#dailyNoticeMore").attr("href",ctx+pathPage+"notice/toNoticeMain/?noticeTypeId=dailybroadcast")
$("#focusNoticeMore").attr("href",ctx+pathPage+"notice/toNoticeMain/?noticeTypeId=importbroadcast")
})

function getList(){
	if($(".tab").find("li").eq(0).hasClass("active")){
		getListData(1,10);
	}else if($(".tab").find("li").eq(1).hasClass("active")){
		getShareDocs(1,12);
	}
}
/**
 * 加载供片台
 */
function loadCity(){
	var tvStation=areaMap();
	$.each(tvStation,function(i,item){
		$("#citylist").append('<li value='+i+'>'+item+'</li>');
	})
	cityClickActive();
}
/**
 * 点击供片台切换样式
 */
function cityClickActive(){
	$("#citylist").find("li").click(function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		//重置分页
		//alert($(this).val())
		getListData(1,10);
	})
}
/**
 * 加载通联量排行榜
 */
function loadnewsCount(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		url : ctx + pathValue+"news/findCommunicationsByCount/",
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
				toastr.error("查询通联量排行榜失败");
				return;
			}
			$("#newsCount ul").eq(0).html('');
			$("#newsCount ul").eq(1).html('');
			if(code == 0 && data.statistic.length > 0){
				$.each(data.statistic,function (i,item){
					if(i<3){
						$("#newsCount ul").eq(0).append("<li><i>"+(i+1)+"</i>"+item.areaName+"<span>"+item.count+"</span></li>")
					}else if(i<5){
						$("#newsCount ul").eq(1).append("<li><i>"+(i+1)+"</i>"+item.areaName+"<span>"+item.count+"</span></li>")
					}
				})
			}else{
				
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询通联量排行榜失败");
		}
	});
}


function getListData(pageNow,pageSize){
	var index = layer.load();
	if(!pageNow || !pageSize){
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
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	$("#citylist").find("li").each(function(){
		if($(this).hasClass("active")){
			if($(this).val()!=0){
				onairMap.put("areacode", $(this).val());
			}
		}
	})
	
	$.ajax({
		url : ctx + pathValue+"news/findNewsByShare/",
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
				toastr.error("查询共享资源失败");
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
			toastr.error("查询共享资源失败");
		}
	});
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
		//alert(item.content)
		 html.push('<li class="clearfix">');
		 html.push('<div class="news-img fl" onclick="getShareDetail(\''+item._id+'\')">');
		 html.push(processThumbnailurl(item));
		 html.push('</div>');
		 html.push('<div class="news-msg fl">');
		 html.push('<h5 onclick="getShareDetail(\''+item._id+'\')"><span>'+processValue(item.template.tvStationName)+'</span>'+cutTitleString(process(item.title),20,"...")+'</h5>');
		 html.push('<p class="reporter"><span>创建人：'+processValue(item.cusename)+'</span><span>记者：'+cutTitleString(process(item.template.reporter),12,"...")+'</span></p>');
		 html.push('<p class="jianjie">简介：'+cutTitleString(process(item.template.content),98,"...")+'</p>');
		 html.push('<div class="msg-bot">');
		 html.push('<a class="list_icon list_icon5" onclick="getShareDetail(\''+item._id+'\')"><p><span>预览</span></p></a><span>'+(item.readetotal==undefined?"0":item.readetotal)+'</span>');
		 html.push('<a class="list_icon list_icon7"  onclick="showSendShare(\''+item._id+'\')"><p><span>推送</span></p></a><span>'+(item.pushtotal==undefined?"0":item.pushtotal)+'</span>');
		 html.push('<span class="update-time fr">更新时间：'+processValue(item.uutime)+'</span>');
		 html.push('</div>');
		 html.push('</div>');
		 html.push('</li>');
	});
	$("#listDiv").html(html.join(""));
	//hover预览和推送
	$(".msg-bot a").hover(function(){
		$(this).find("p").css("display","block");
	},function(){
		$(this).find("p").css("display","none");
	})
	
	//素材下载、通联创建圆圈居中的样式
	var icon_w=$(".list_img_div i").width();
	var icon_h=$(".list_img_div i").height();
	$(".list_img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}
/**
 * 加载推送路径
 */
function showSendShare(id){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "shareresource/sendShareNews/?_id="+id+"&pushType=SHARENEWS",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['400px','300px'],
				title : "新增推送路径",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
			//	maxmin : true,
				scrollbar : false,
				//offset: ['300px', '200px'],
				content : data
			});
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
//提交推送
function subSend(){
//	layer.confirm('确认推送新闻通联？', {
//	    btn: ['确认','取消'] 
//	}, function(outIndex){
//		sendINEWS(outIndex,id);
//	}, function(){
//	});
}
function getShareDetail(id){
	$("#videoAudioDiv").empty();
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("newsId",id);
	
	$.ajax({
		url : ctx + pathValue+"news/findNewsById/",
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
				var result = data;				
				if(result){
					
					var template = result.template;
					/**
					 * 标题
					 */
					$("#title").html(process(template.title));
					$("#uutime").html(process(result.uutime));
					$("#createor").html(process(template.createor));
					/**
					 * 记者
					 */
					$("#reporter").html(process(template.reporter));
					$("#tvStation").html(process(template.tvStationName))
					/**
					 * 文稿
					 */
		        	$("#docs").html(process(template.docsContentHTML));
					$("#flashDiv").html('');
					$("#flashDiv").html('<div class="bfqloading" id="previewFlashContent">'+
					'<div id="player">'+
						'</div>		'+		
						'</div>');
					var videoAudioTempArr = [];
					var picTempArr = [];
					var videos = result.videos;
					if(videos && videos.length > 0){
						 //预览区加载视频
						for(var i=0;i<videos.length;i++){
							var video = videos[i];
							videoAudioTempArr.push(video);
						}
						
					}
					var audios = result.audios;
					if(audios && audios.length > 0){
						for(var i=0;i<audios.length;i++){
							var audio = audios[i];
							videoAudioTempArr.push(audio);
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
						}
						loadMediaHtml(picTempArr,"pic");
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
				        	if(pics==undefined ||pics.length<=0){
								$("#videoAudioDiv").append('<img src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
				        	}
				        	$("#player").append('<img src="'+ctx+'/common/images/material_not_pic3.jpg" width="100%">')
				        }
				     }
					
					var bodyH = $(".news-style-div").height()+500;
				    var popupH = $("#detail .popup_inner").height()+200;
				    if(popupH<bodyH){
				    	$("#detail").height(bodyH+"px");
				    }
				    else{
				    	$("#detail").height(popupH+"px");
				    }
				    /**
					 * 右侧操作
					 */
					$("#rightopre").html('');
					if(result.cuserid==userId){
						$("#rightopre").append('<ul >'+
								'<li class="del-li '+(result.cuserid==userId?"":"active")+'" onclick="deleteshareNews(\''+id+'\',\''+(result.cuserid==userId?"0":"1")+'\')">'+
								'</li></ul>')
					}
				}
			}else{
				toastr.error("查询共享失败！");
			}
					
		},
		error : function() {
			layer.close(index);
			toastr.error("查询共享失败！");
		}
	});
	$("html,body").animate({
		scrollTop : 0
	}, 0);
	$("#detail").show();
	
}

function deleteshareNews(id,isdelete){
	if(isdelete==1){
		return ;
	}
	layer.confirm('确认删除新闻通联？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		layer.close(outIndex);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("newsId", id);
		onairMap.put("shareNews", 1);
		$.ajax({
			url : ctx + pathValue+"news/unshareNews/",
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
					toastr.error("删除失败");
					return;
				}
				if(code == 0){
					$("#detail").hide();
					getListData();
				}
			},
			error : function() {
				layer.close(index);
				toastr.error("删除失败");
			}
		});
		
	}, function(){
	});


	

}

//加载音视频
function loadMediaHtml(obj,mediaType){
	layer.closeAll('page');
	
	
	if(mediaType == "pic"){
		$(obj).each(function(i,item){
			var html = [];	 
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()));
			var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			html.push('<li>');
			html.push(processPlayerIco(wanurl,item.mtype));
			html.push('	<p>'+cutStr(item.name,14,"...")+'</p>');
			html.push('	<div class="">');
			html.push('<a href='+item.wanurl+' data-lightbox="example-1">')
			html.push("<img  src='"+vslt+"'> ");
			html.push('<a>')
			html.push('	</div>');
			html.push('</li>');
			if((i+1)%2==0){
				html.push("<div class='clear'></div>");
			}
			$("#videoAudioDiv").append(html.join(""));
		});
	}else{
		$(obj).each(function(i,item){
			var avHtml=[];
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
		//	var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":item.vslt)
            var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			avHtml.push('<li>');
		    avHtml.push(processPlayerIco(wanurl,item.mtype));
		    avHtml.push(dealAVtypeValue(item,"cp"));
		    avHtml.push('	<p>'+cutStr(item.name,14,"...")+'</p>');
		    avHtml.push('	<div class="">');
		    avHtml.push('<img src="'+vslt+'"/>')
		    avHtml.push('	</div>');
		    avHtml.push('</li>');
	        if((i+1)%2==0){
	        	avHtml.push("<div class='clear'></div>");
			}	
			$("#videoAudioDiv").append(avHtml.join(""));
		})
	}
	
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
function dealAVtypeValue(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"<b>"+(avtype=="cp"?"成片":"")+"</b>":"");
}

function processThumbnailurl(item){
	var html = [];
	if(item.thumbnailurl&&item.thumbnailurl!="undefined"){
		if(item.thumbnailurl!='undefined'){
			html.push("<img src=\""+item.thumbnailurl+getMaterialResolution()+"\"/>");
		}
	}else{
		if(item.audios!=undefined&&item.audios.length>0){
			html.push("<img src="+ctx+"/common/images/audio.png/>");
		}else{
			html.push("<img src="+ctx+"/common/images/wengao_img.png/>");
		}
	}
	return html.join("");
}

//右侧加载公告
function loadNoticeDay(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	//onairMap.put("currentPage", 5);
	onairMap.put("pageNum", 5);
	onairMap.put("noticeTypeId", "dailybroadcast");
	$.ajax({
		url : ctx + pathValue+"notice/findNotices/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			$("#dailyNotice").html('');
			if(response.code==0){
				if(response.data.results&&response.data.results.length>0){
					$.each(response.data.results,function(i,item){
						$("#dailyNotice").append("<li class="+(i==0?'active':'')+" onclick=\"toPage('notice/indexToNoticeDetailMain/?id="+item._id+"');\"><i></i>"+cutStr(item.noticetitle,15,"...")+"</li>")
					})
				}
			}else{
				toastr.error("查询云新每日播出稿件失败");
			}
		},
		error:function(){
			//layer.close(index);
			toastr.error("查询云新每日播出稿件失败");
			}
		})
}
function loadNoticeFocus(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("pageNum", 5);
	onairMap.put("noticeTypeId", "importbroadcast");
	$.ajax({
		url : ctx + pathValue+"notice/findNotices/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			$("#focusNotice").html('');
			if(response.code==0){
				if(response.data.results&&response.data.results.length>0){
					$.each(response.data.results,function(i,item){
						$("#focusNotice").append("<li onclick=\"toPage('notice/indexToNoticeDetailMain/?id="+item._id+"');\">"+
			 			"<p>"+cutStr(item.noticetitle,16,"...")+"</p>"+
			 			"<p>"+item.uutime+"</p>"+
			 		"</li>")
					})
				}
			}else{
				toastr.error("查询策划与报道重点失败");
			}
		},
		error:function(){
			//layer.close(index);
			toastr.error("查询策划与报道重点失败");
			}
		})
}
//点击量排行
function loadNewsClickSort(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("pageNum", 5);
	$.ajax({
		url : ctx + pathValue+"news/findNewsByCount/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			$("#newsClickSort").html('');
			if(response.code==0){
				if(response.data.results&&response.data.results.length>0){
					$.each(response.data.results,function(i,item){
						$("#newsClickSort").append("<li onclick='showNewsDetai(\""+item._id+"\")'><i>"+(i+1)+"</i>"+cutStr(item.title,14,"...")+"</li>")
					})
				}
			}else{
				toastr.error("查询点击量排行榜失败");
			}
		},
		error:function(){
			//layer.close(index);
			toastr.error("查询点击量排行榜失败");
			}
		})
}
function showNewsDetai(id){
	//跳到哪个角色的页面？
	window.open(ctx + pathPage + "news/toNewsViewIndex/?_id="+id);
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

function closeSend(){
	$(".popup").hide()
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
			"373px", swfVersionStr, xiSwfUrlStr, flashvars, params,
			attributes);
	swfobject.createCSS("#flashContent",
			"display:block;text-align:left;");

}
