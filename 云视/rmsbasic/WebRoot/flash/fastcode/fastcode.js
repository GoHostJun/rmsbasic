// For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
var swfVersionStr = "11.1.0";
// To use express install, set to playerProductInstall.swf, otherwise the empty string. 
var xiSwfUrlStr = "playerProductInstall.swf";


// video config
var host = ctx + pathValue+"fastcode/";
//资源前置路径,为要加载flash的路径,例如：http://demo.com/assets/flash/
var assetPath = ctx+"/flash/fastcode/";




//防缓存
var d = new Date();
var flashvars = {};
flashvars.time = d.getTime();

var preloaderUrl = assetPath + "PreLoader.swf" + "?time=" + flashvars.time;//preloader.swf
var mainUrl = "VideoEditor.swf" + "?time=" + flashvars.time;//主文件

var config = {};			

config.host = host;
config.assetPath = assetPath;
//config.preVideos = preVideos;

config.mainUrl = mainUrl;
config.zhutuDes="在此功能模式下，您编辑后生成的视频的最大时长是9秒。";
config.xiangqingDes="在此功能模式下，您编辑后生成的视频的最大时长是11分钟。";

//录音de接口地址
//config.recordURL="../saveRecord/";
//快编前往按钮的跳转
config.buildPage="javascript:uCont('material/toMaterial/')";
//字幕的字体
//config.flist=["微软雅黑","宋体","新宋体"];
//config.flist=["Arial","Verdana","Times New Roman","helvetica","宋体","黑体","新宋体","楷体"];
config.flist=["微软雅黑","Arial","宋体","新宋体","黑体"];
//控制是否开启fixedInfo字段
//config.fixedInfo="${param.materialIds}";
//控制搜索框
config.canSearch="false";
//录音上传超时时间秒
//config.recordTime=45;
//字幕最大字数
//config.maxChars=140;
config.defaultSize="1280,720";


var params = {};
params.quality = "high";
params.bgcolor = "#000000";
params.allowscriptaccess = "sameDomain";
params.allowFullScreenInteractive = "true";
// params.wmode = "transparent";
var attributes = {};
attributes.id = "PreLoader";
attributes.name = "PreLoader";
attributes.align = "middle";
function getConfig() {
	return config;
}
startFastCode();
function startFastCode(){
	//api接口
	var apis = {};
	apis.getList = "getFastcode/?materialIds="+ids+"&";
	apis.build = "saveFastcode/";
	apis.deleteMaterial="deleteFastcode/";//删除资源
	apis.updateMaterial="updateFastcode/";//修改资源
	apis.addMaterial="addFastcode/";//添加资源
	config.apis = apis;
	swfobject.embedSWF(preloaderUrl, "flashContent", "100%", "100%",
			swfVersionStr, xiSwfUrlStr, flashvars, params, attributes);
	swfobject.createCSS("#flashContent",
	"display:block;text-align:left;");
}
