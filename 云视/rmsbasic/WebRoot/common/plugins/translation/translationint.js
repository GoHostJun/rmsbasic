/**
 * 翻译插件  版本v1.0
 * 创建日期：2015/11/24
 * 创建人： 刘鑫
 * 功能:
 * 1.可以翻译指定位置内的内容（传入指定位置id）
 * 2.可以翻译json数据
 * 3.可以翻译字符串
 * 以上功能需要jquery支持，和语音包支持
 */


/**
 * 初始化加载语言包
 */
$(function(){
	//lang= getCookie("HY_LANGUAGE");
	//取得浏览器语言的前两个字母
//	var lang="en";
//	if(lang==null||lang==""||lang==undefined||lang!="en"){
//		lang="source";
//	}
	var langg;
	if(langCommon=="en"){
		langg="en_US";
	}else if(langCommon=="zh"){
		langg="zh_CN";
	}
	var url=ctx+"/common/plugins/translation/"+langg+".js?cur="+Math.round(Math.random()*10000);
	loadJS("",url);
	
	//------------动态加载语言js文件--------------
});
