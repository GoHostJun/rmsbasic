$(document).ready(function() {
	getListData();
});
function toupdate(obj,key,value,id){
	var cont = $(obj).parent().parent().children("td:eq(2)").text();
	$(obj).parent().parent().children("td:eq(2)").html("<input id=\"globalval_"+id+"\" value="+cont+">");
	$(obj).parent().parent().children("td:eq(3)").html("<button type=\"button\" class=\"btn btn-sm btn-primary\"  onclick=\"saveglobal('"+key+"','"+id+"',this);\"><i class=\"fa fa-check\"></i> 保存</button>");
//	alert($(obj).parent().parent().children("td:eq(2)").text());
}
/**
 * 保存数据 
 * globalkey:键
 * globalval:值
 * id：主键
 * saveorupdateflag:添加或者修改标识（save：添加，update：修改）
 */
function saveglobal(globalkey,id,obj){
	var globalval = $("#globalval_"+id).val();
	$.ajax({
		type: 'POST',
		url: ctx+'/global/updateGlobal/',
		cache: false,
		async : false,
		data:{"key":globalkey,"value":globalval,"id":id},
		success: function(response){
			if("success"==response){
				$(obj).parent().parent().children("td:eq(2)").text(globalval);
				$(obj).parent().parent().children("td:eq(3)").html("<button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'"+globalkey+"','"+globalval+"','"+id+"');\"><i class=\"fa fa-paste\"></i> 修改</button>");
				swal({title:"修改成功",type:"success"});
			}
		},
		error:function(a,b,c){
		}
   }); 
}
function getListData(){
	layer.load(2);
	$.ajax({
		url : ctx +"/getGlobal/",
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
				loadHtml(data);
				layer.closeAll('loading');
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}

/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var html = [];
	html.push("<div class=\"col-sm-12\">");
	html.push("<div class=\"ibox float-e-margins\">"); 
	html.push("<div class=\"ibox-content\">");
	html.push("<table class=\"table table-hover\">");
	html.push("<thead><tr><th>字段</th><th>字段名称</th><th>值</th><th>操作</th></tr></thead><tbody>");
	html.push("<tr>");
	html.push("<td><span class=\"line\">ftp</span></td>");
	html.push("<td>ftp存储地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.ftp+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'ftp','"+obj.ftp+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">httpWan</span></td>");
	html.push("<td>文件本地外网地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.httpWan+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'httpWan','"+obj.httpWan+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">httpLen</span></td>");
	html.push("<td>文件本地内网地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.httpLen+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'httpLen','"+obj.httpLen+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">queue_username</span></td>");
	html.push("<td>mq用户名</td>"); 
	html.push("<td><span class=\"line\">"+obj.queue_username+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'queue_username','"+obj.queue_username+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">queue_password</span></td>");
	html.push("<td>mq密码</td>"); 
	html.push("<td><span class=\"line\">"+obj.queue_password+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'queue_password','"+obj.queue_password+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">queue_host</span></td>");
	html.push("<td>mq地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.queue_host+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'queue_host','"+obj.queue_host+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">queue_port</span></td>");
	html.push("<td>mq使用端口号</td>"); 
	html.push("<td><span class=\"line\">"+obj.queue_port+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'queue_port','"+obj.queue_port+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">Osslocalhost</span></td>");
	html.push("<td>oss上传的本地路径</td>"); 
	html.push("<td><span class=\"line\">"+obj.Osslocalhost+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'Osslocalhost','"+obj.Osslocalhost+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">bsshttp</span></td>");
	html.push("<td>bss地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.bsshttp+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'bsshttp','"+obj.bsshttp+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">OpenToken</span></td>");
	html.push("<td>是否开启token：1为开启验证;0 为关闭验证</td>"); 
	html.push("<td><span class=\"line\">"+obj.OpenToken+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'OpenToken','"+obj.OpenToken+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">httpAdderssUrl</span></td>");
	html.push("<td>获取对外地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.httpAdderssUrl+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'httpAdderssUrl','"+obj.httpAdderssUrl+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">skipLen</span></td>");
	html.push("<td>跳转内网地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.skipLen+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'skipLen','"+obj.skipLen+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">skipWan</span></td>");
	html.push("<td>跳转外网地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.skipWan+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'skipWan','"+obj.skipWan+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">storageIp</span></td>");
	html.push("<td>文件存储服务ip（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.storageIp+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'storageIp','"+obj.storageIp+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">orsAPI</span></td>");
	html.push("<td>跳转服务地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.orsAPI+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'orsAPI','"+obj.orsAPI+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">callBack</span></td>");
	html.push("<td>回调地址（回调API地址）</td>"); 
	html.push("<td><span class=\"line\">"+obj.callBack+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'callBack','"+obj.callBack+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">ossWanAdderssUrl</span></td>");
	html.push("<td>oss外网地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.ossWanAdderssUrl+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'ossWanAdderssUrl','"+obj.ossWanAdderssUrl+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">ossLenAdderssUrl</span></td>");
	html.push("<td>oss内网地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.ossLenAdderssUrl+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'ossLenAdderssUrl','"+obj.ossLenAdderssUrl+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">pHashDNA</span></td>");
	html.push("<td>跳转外网地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.pHashDNA+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'pHashDNA','"+obj.pHashDNA+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">skipWan</span></td>");
	html.push("<td>phash服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.skipWan+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'skipWan','"+obj.skipWan+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">skipWan</span></td>");
	html.push("<td>跳转外网地址（使用跳转服务需要填）</td>"); 
	html.push("<td><span class=\"line\">"+obj.skipWan+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'skipWan','"+obj.skipWan+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">surfDNA</span></td>");
	html.push("<td>surf服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.surfDNA+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'surfDNA','"+obj.surfDNA+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">colorDNA</span></td>");
	html.push("<td>图片直方图服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.colorDNA+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'colorDNA','"+obj.colorDNA+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">audioFingerPrint</span></td>");
	html.push("<td>声纹识别地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.audioFingerPrint+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'audioFingerPrint','"+obj.audioFingerPrint+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">faceRecognition</span></td>");
	html.push("<td>人脸识别地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.faceRecognition+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'faceRecognition','"+obj.faceRecognition+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">screenShot</span></td>");
	html.push("<td>截图服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.screenShot+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'screenShot','"+obj.screenShot+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">ossUpload</span></td>");
	html.push("<td>使用SDK上传;使用消息队列ossjar包上传填：oss;使用SDK上传填：SDK</td>"); 
	html.push("<td><span class=\"line\">"+obj.ossUpload+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'ossUpload','"+obj.ossUpload+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">ossUploadUrl</span></td>");
	html.push("<td>使用SDK上传 SDK管理服务地址（小鸡）</td>"); 
	html.push("<td><span class=\"line\">"+obj.ossUploadUrl+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'ossUploadUrl','"+obj.ossUploadUrl+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">transition</span></td>");
	html.push("<td>转场识别服务</td>"); 
	html.push("<td><span class=\"line\">"+obj.transition+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'transition','"+obj.transition+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">review</span></td>");
	html.push("<td>技审服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.review+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'review','"+obj.review+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">transcode</span></td>");
	html.push("<td>转码服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.transcode+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'transcode','"+obj.transcode+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">live</span></td>");
	html.push("<td>直播服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.live+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'live','"+obj.live+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">voice</span></td>");
	html.push("<td>语音识别服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.voice+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'voice','"+obj.voice+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">record</span></td>");
	html.push("<td>收录服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.record+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'record','"+obj.record+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">imChar</span></td>");
	html.push("<td>拆条服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.imChar+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'imChar','"+obj.imChar+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">faceRecognition</span></td>");
	html.push("<td>人脸识别地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.faceRecognition+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'faceRecognition','"+obj.faceRecognition+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">fastcode</span></td>");
	html.push("<td>快编服务地址</td>"); 
	html.push("<td><span class=\"line\">"+obj.fastcode+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'fastcode','"+obj.fastcode+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">ossRetry</span></td>");
	html.push("<td> oss上传重试次数;空为不用,数字表示重试几次</td>"); 
	html.push("<td><span class=\"line\">"+obj.ossRetry+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'ossRetry','"+obj.ossRetry+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("<tr>");
	html.push("<td><span class=\"line\">recordv2</span></td>");
	html.push("<td> 收录2.0版本</td>"); 
	html.push("<td><span class=\"line\">"+obj.recordv2+"</span></td>");
	html.push("<td><button type=\"button\" class=\"btn btn-sm btn-info\"  onclick=\"toupdate(this,'recordv2','"+obj.recordv2+"','"+obj._id+"');\"><i class=\"fa fa-paste\"></i> 修改</button></td>");
	html.push("</tr>");
	
	html.push("</tbody></table></div></div></div></div>");
	$("#listDiv").html(html.join(""));
}