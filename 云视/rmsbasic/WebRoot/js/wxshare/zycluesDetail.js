$(function(){
	getZYCues();
	 history.replaceState({}, "", window.location.href.split('?')[0] + "?_id="+shareId+"&userId="+userId+"&redictype=zyclues"
			  +"&title="+title+"&imgUrl="+imgUrl+"&subbmit="+subbmit+"&loginid="+loginid
			  +"&nikename="+nikename+"&from="+from+"&isappinstalled="+isappinstalled+"&type="+type);
	if(role==0){
		$(".bottom-fixed").hide();
	}
	  
	  $("#pass").click(function(){
		  addClub();
	  })
	  $("#notPass").click(function(){
		  WeixinJSBridge.call('closeWindow');
	  })
	 
})

function swiper(){
	//小图片滑动
	var dWidth = $('.bannar').width()
	$('.bannar').height(dWidth*0.7)
	$(".img-list li").css({"width":dWidth/4-10+"px","height":dWidth/4})
	$(".img-list ul").width($(".img-list li").length/4*dWidth)
	$(".img-list-div").height($(".img-list li").height()-20+"px")

}
var cueContent,prostation;
var thumbs=[];

function addClub(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", "");
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("subbmit", subbmit);
	onairMap.put("loginid", loginid);
	onairMap.put("nikename", nikename);
	onairMap.put("id", shareId);
	onairMap.put("cueContent", cueContent);
	onairMap.put("title", title);
	onairMap.put("prostation", prostation);
	onairMap.put("thumbs", thumbs);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +path+ "addDocsFromZY/",
		data : onairMap.toJson(),
		cache : false,
		success : function(data) {
			if (0 == data.code) {
				$(".popup:eq(0)").show();
				setTimeout(function(){
					$(".popup:eq(0)").hide();
					WeixinJSBridge.call('closeWindow');
				}, 2000);
			}else{
				$(".popup:eq(1)").show();
				setTimeout(function(){
					$(".popup:eq(1)").hide();
					WeixinJSBridge.call('closeWindow');
				}, 2000);
			}
		},
		error : function() {
			$(".popup:eq(1)").show();
			setTimeout(function(){
				$(".popup:eq(1)").hide();
				WeixinJSBridge.call('closeWindow');
			}, 2000);
		}
	});
	
}
function getTimeStamp() {
	return new Date().getTime()+"";
}

function getZYCues(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", "");
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("_id", shareId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +path+ "getZYCueById/",
		data : onairMap.toJson(),
		cache : false,
		success : function(data) {
			if (0 == data.code) {
				 data=data.data;
				 prostation=toValue(data.prostation)
				$("#title").text(toValue(data.assetname))
				$("#headtitle").text(toValue(data.assetname))
				$("#sourceDate").html(toValue(data.prostation)+"<span>"+toValue(data.creationdate)+"</span>")
				$("#cueContent").html(toValue(data.content))
				$("#cueContent img").remove();
				 cueContent=$("#cueContent").html()
				if(data.thumbnailfileid&&data.thumbnailfileid.length>1){
					$.each(data.thumbnailfileid,function(i,item){
						thumbs.push(item.thumb);
						if(i==0){
							$("#viOrim").html('<img src="'+item.thumb+'@320w_100q.src"/>')
						}
						$("#thumb").append('<li onclick=showImg(\''+item.thumb+'\')><img src="'+item.thumb+'@320w_100q.src"/></li>')
					})
					swiper();
				}else if(data.thumbnailfileid&&data.thumbnailfileid.length==1){
					thumbs.push(data.thumbnailfileid[0].thumb);
					$("#viOrim").html('<img src="'+data.thumbnailfileid[0].thumb+'@320w_100q.src"/>')
				}else{
					$(".bannar").remove();
				}
			}else{
			
			}
		},
		error : function() {
			
		}
	});

	
}
function showImg(img){
	$("#viOrim").html('<img src="'+img+'@320w_100q.src"/>')
}

function browserRedirect() {
	isWX();
	var ua = window.navigator.userAgent.toLowerCase();
	var android="myscheme://com.cdvcloud."+getUrlConfig.shareAndroid+"/get/info?_id="+shareId+"&type="+type+"&subbmit="+subbmit+"&loginid="+loginid+"&nikename="+nikename;
	var iphone=getUrlConfig.shareIOS+"://OnAir/type="+type+"&_id="+shareId+"&subbmit="+subbmit+"&loginid="+loginid+"&nikename="+nikename;
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