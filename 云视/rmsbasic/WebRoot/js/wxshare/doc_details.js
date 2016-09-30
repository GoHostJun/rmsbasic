$(function(){
	  history.replaceState({}, "", window.location.href.split('?')[0] + "?_id="+shareId+"&userId="+userId+"&redictype=doc");
	  getshareDocs();
	  $("#copyDoc").click(function(){
		  copyDoc();
	  })
	  
})
function getTimeStamp() {
	return new Date().getTime()+"";
}

function copyDoc(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", "");
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", shareId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +path+ "fieldNews/copyFieldNews/",
		data : onairMap.toJson(),
		cache : false,
		success : function(data) {
			if (0 == data.code) {
				$(".popup:eq(0)").show();
				setTimeout(function(){
					$(".popup:eq(0)").hide();
				}, 2000);
			}else{
				$(".popup:eq(1)").show();
				setTimeout(function(){
					$(".popup:eq(1)").hide();
				}, 2000);
			}
		},
		error : function() {
			$(".popup:eq(1)").show();
			setTimeout(function(){
				$(".popup:eq(1)").hide();
			}, 2000);
		}
	});
}

function getshareDocs(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", "");
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("fieldNewsId", shareId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +path+ "fieldNews/findFieldNewsById/",
		data : onairMap.toJson(),
		cache : false,
		success : function(data) {
			if (0 == data.code) {
				 data=data.data;
				$("#title").text(toValue(data.title))
				$("#headtitle").text(toValue(data.title))
				$("#sourceDate").text("记者："+(data.template?toValue(data.template.reporter):""))
				$("#docContent").html(data.template?toValue(data.template.docsContentHTML):"")
				
				
				
				
				
				//判断是不是只有一个素材或者都没有
				if(data.videos&&data.videos.length==1){
					if(!data.pics||data.pics&&data.pics.length==0){
						var video=data.videos[0];
						var videoDefault="";
						var thumbnailsDefault="";
						if(video.defaults&&video.defaults.length>0){
							$.each(video.defaults,function(i,item){
								if(item.fmt=="mp4"){
									videoDefault=item.wanurl;
								}
							})
						}
						if(video.thumbnails&&video.thumbnails.length>0){
							thumbnailsDefault=video.thumbnails[0].wanurl;
						}
						$("#viOrim").html('<video width="" height="" controls="controls" webkit-playsinline  poster="'+thumbnailsDefault+'@320w_100q.src">'+
								'<source src="'+videoDefault+'" type="video/mp4"></source>'+
								'当前浏览器不支持 video直接播放，点击这里下载视频： <a href="myvideo.webm">下载视频</a>'+
						'</video>')
					}
					swiper();
				}else if(!data.videos||data.videos&&data.videos.length==0){
					if(data.pics&&data.pics.length==1){
						var pic=data.pics[0];
						$("#viOrim").html('<img src="'+pic.wanurl+'@320w_100q.src"/>')
					}else if(!data.pics||data.pics&&data.pics.length==0){
						$(".bannar").remove();
					}
					swiper();
				}
				
				//视频音频总数不止一个
				if(data.videos&&data.videos.length>0){
					$.each(data.videos,function(i,item){
						var videoFirst="";
						var video="";
							if(item.defaults&&item.defaults.length>0){
								$.each(item.defaults,function(i,item){
									if(item.fmt=="mp4"){
										if(i==0){
											videoFirst=item.wanurl;
										}
										video=item.wanurl;
									}
								})
							}
							var thumbnailsFirst="";
							if(item.thumbnails&&item.thumbnails.length>0){
								if(i==0){
									thumbnailsFirst=item.thumbnails[0].wanurl;
								}
								$("#docThumb").append('<li onclick=playVideo(\''+video+'\',\''+item.thumbnails[0].wanurl+'\')><img src="'+item.thumbnails[0].wanurl+'@320w_100q.src"/><i></i></li>')
							}
							if(i==0){
								if($("#viOrim").text()==""){
									$("#viOrim").html('<video width="" height="" controls="controls" webkit-playsinline  poster="'+thumbnailsFirst+'@320w_100q.src">'+
											'<source src="'+videoFirst+'" type="video/mp4"></source>'+
											'当前浏览器不支持 video直接播放，点击这里下载视频： <a href="myvideo.webm">下载视频</a>'+
									'</video>')
								}
								
							}
						
						
						
					})
				}else{
					if(data.pics&&data.pics.length>0){
						$.each(data.pics,function(i,item){
							if(i==0){
								if($("#viOrim").text()==""){
									$("#viOrim").html('<img src="'+item.wanurl+'@320w_100q.src"/>')
								}
							}
						})
					}
				}
				if(data.pics&&data.pics.length>0){
					$.each(data.pics,function(i,item){
						$("#docThumb").append('<li onclick=showImg(\''+item.wanurl+'\')><img src="'+item.wanurl+'@320w_100q.src"/></li>')
					})
				}
				swiper();
			}else{
			
			}
		},
		error : function() {
			
		}
	});

	
}
function playVideo(video,img){
	$("#viOrim").html('<video width="" height="" controls  webkit-playsinline poster="'+img+'@320w_100q.src">'+
			'<source src="'+video+'" type="video/mp4"></source>'+
			'当前浏览器不支持 video直接播放，点击这里下载视频： <a href="myvideo.webm">下载视频</a>'+
		'</video>')
}
function showImg(img){
	$("#viOrim").html('<img src="'+img+'@320w_100q.src"/>')
}
function swiper(){
	//小图片滑动
	var dWidth = $('.bannar').width()
	$('.bannar').height(dWidth*0.7)
	$(".img-list li").css({"width":dWidth/4-10+"px","height":dWidth/4})
	$(".img-list ul").width($(".img-list li").length/4*dWidth)
	$(".img-list-div").height($(".img-list li").height()-20+"px")

}

function browserRedirect() {
	isWX();
	var ua = window.navigator.userAgent.toLowerCase();
	var android="myscheme://com.cdvcloud."+getUrlConfig.shareAndroid+"/get/info?type="+type+"&_id="+shareId+"&userId="+userId;
	var iphone=getUrlConfig.shareIOS+"://OnAir/type="+type+"&_id="+shareId+"&userId="+userId
	var userAgentInfo = navigator.userAgent.toLowerCase();
	var androidAgents = new Array("android");
	var iphoneAgents = new Array("iphone", "ipod", "ipad");
	var flagA = true;
	var flagI = true;
	for ( var v = 0; v < androidAgents.length; v++) {
		if (userAgentInfo.indexOf(androidAgents[v]) > 0) {
			flagA = false;
			break;
		}
	}
	if (flagA) {
		for ( var v = 0; v < iphoneAgents.length; v++) {
			if(userAgentInfo.indexOf(iphoneAgents[v]) > 0){
				flagI = false;
				break;
			}
		}
	}
	if (!flagA) {
		//此操作会调起app并阻止接下来的js执行
        $('body').append("<iframe src='"+android+"' style='display:none' target='' ></iframe>");
        //没有安装应用会执行下面的语句
	}else if(!flagI) {
		 window.location = iphone;
	} else {
		alert("暂不支持");
	}
}

function isWX(){
	//判断是否为微信浏览器打开
	var ua = window.navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){
		//判断是安卓浏览器还是IOS浏览器打开
		var flagIOS = true;
		var ua = navigator.userAgent.toLowerCase();
		if(ua.indexOf("iphone") > -1 || ua.indexOf("ipod") > -1 || ua.indexOf("ipad") > -1) {
			flagIOS = false;
			$(".share_tip_ios").show();
		}
		if (flagIOS) {//判断是否为安卓
			var isAndroid = ua.indexOf("android") > -1; //&& ua.indexOf("mobile");
			if(isAndroid) {
				$(".share_tip").show();
			}
		}
	}else{
		
	} 
}
function toValue(obj){
	if(obj){
		return obj;
	}
	return "";
}