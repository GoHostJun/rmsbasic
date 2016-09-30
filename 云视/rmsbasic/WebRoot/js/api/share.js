$(function(){
	$(".share_wrap").css({"height":$(window).height()});
	$(".news_div").css({"height":$(".news_div").width()/0.64});

	$(window).resize(function(){
		$(".share_wrap").css({"height":$(window).height()});
	})

	$(".share_tip a").click(function(){
		$(this).parent().hide();
	})
	
	$(".img_div img").load(function(){
		var img_w=$(this).width();
		var img_h=$(this).height();
		var div_h=$(".img_div").height();
		$(this).css({"width":img_w,"margin-top":(div_h-img_h)/2});
	})
	
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
})

function browserRedirect(type,mapInfoId,userId) {
	var android="myscheme://com.cdvcloud."+getUrlConfig.shareAndroid+"/get/info?type="+type+"&_id="+mapInfoId+"&userId="+userId;
	var iphone=getUrlConfig.shareIOS+"://OnAir/type="+type+"&_id="+mapInfoId+"&userId="+userId
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
//        setTimeout(function(){alert("请先下载安装相关APP");},1000);
	}else if(!flagI) {
		window.location = iphone;
//        setTimeout(function(){alert("请先下载安装相关APP");},1000);
	} else {
		alert("暂不支持");
	}
}