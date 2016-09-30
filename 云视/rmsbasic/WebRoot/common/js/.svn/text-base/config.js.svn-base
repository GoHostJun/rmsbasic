
var getUrlConfig= (function(){
	var tempURL=function(){
		return window.location.href;
	}
	var filterUrl=function(){
		var navImg;
		var shareAndroid;
		var shareIOS;
		var clintImg;
		var width="100%";
		var nameTV="";
		if(tempURL().indexOf("report")>0){
			if("undefined" != typeof(consumerId)){
				if("CRIEnglish"==consumerId){
					clintImg="crienglishapp.png"
				}
				else if("CDVCLOUD"==consumerId){
					clintImg="cdvcloudapp.png"
				}
				else if("gzxw"==consumerId){
					clintImg="guizhouapp.png"
				}
				else{
					clintImg="yunnanapp.png";
				}
			}
			navImg="yunnanLogo.png";
//			shareAndroid="yuntonglian";
//			shareIOS="ynYTL";
			shareAndroid="onairyuntonglian";
			shareIOS="jiexunYTL";
			nameTV="YN";
		}else if(tempURL().indexOf("jxtv")>0){
			navImg="jiangxiLogo.png"
			shareAndroid="jxyuntonglian";
			shareIOS="jxYTL";
			clintImg="jiangxiapp.png";
		}else if(tempURL().indexOf("gztv")>0){
			navImg="guizhouLogo.png"
			shareAndroid="gzyuntonglian";
			shareIOS="gzYTL";
			clintImg="guizhouapp.png";
		}else if(tempURL().indexOf("jstv")>0){
			navImg="jiangsuLogo.png"
			shareAndroid="jsyuntonglian";
			shareIOS="jsYTL";
			clintImg="jiangsuapp.png";
			nameTV="JS";
		}else{
			navImg="jiexunLogo.png"
			shareAndroid="onairyuntonglian";
			shareIOS="jiexunYTL";
			clintImg="jiexunapp.png";
			width="250px"
		}
		return {"navImg":navImg,"shareAndroid":shareAndroid,"shareIOS":shareIOS,"clintImg":clintImg,"width":width,"nameTV":nameTV}
	}
	
	return filterUrl();
})()
$(function(){
	$("#navImg").attr("src",ctx+"/common/images/"+getUrlConfig.navImg).attr("width",getUrlConfig.width);
	$("[name=clintImg]").attr("src",ctx+"/images/sidebar/"+getUrlConfig.clintImg);
})
