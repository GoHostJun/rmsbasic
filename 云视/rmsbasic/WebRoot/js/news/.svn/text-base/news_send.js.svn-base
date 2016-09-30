$(function(){
	$("#ascrail2000").remove();
	init();

})
function init(){
	 var index = layer.load();
	 var jsonStr={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 
	 $.ajax({
	        url:ctx+pathValue+"news/findNewsById/", // 服务器的地址
	        dataType:'json', // 返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', // 请求类型
	        data:jsonStr,
	        success:function(data){
		        layer.close(index);
		       	if(data.code==0){
		       		var data=data.data;
		       		if(data==undefined){
		       			return;
		       		}
		       		var duration=getAVPinfo(data).duration;
		    		var durationDate="";
		    		if(duration!=0){
		    			durationDate=MillisecondToDate(duration)
		    		}
		    		$("#title").text(data.template.title==undefined?'':data.template.title);
		       		$('#docDiv').append(
		       				'<div class="inner">'+
		       		        '<a href="javascript:;" onclick="viewNews()">'+
		       		        ''+getImg(data,durationDate)+''+
		       		        '</a>'+
		       		       ' <div class="title_right">'+
		       		        	'<h4 title="'+(data.title==undefined?'':data.title)+'">'+cutStr((data.title==undefined?'':data.title),11,"...")+'</h4>'+
		       		            '<p style="margin:10px 0;">供片台：<span>'+(data.template.tvStationName==undefined?'':data.template.tvStationName)+'</span> 记者：<span>'+(data.template.reporter==undefined?'':data.template.reporter)+'</span>  关键词：<span>'+(data.template.keyWords==undefined?'':data.template.keyWords)+'</span> 分类：'+
		       		            '<span>'+(data.template.customType==undefined?'':data.template.customType)+'</span></p>'+
		       		            '<p style="margin:10px 0;">视音频：<span>'+getAVPinfo(data).avCount+'</span> 图片：<span>'+getAVPinfo(data).pCount+'</span> </p>'+
		       		            '<p class=\"jianjie_p\" style="margin:20px 0;" title="'+(data.template.content==undefined?'':data.template.content)+'">简介：<span>'+jianjie((data.template.content==undefined?'':data.template.content),46,76)+'</span></p>'+
		       		            '<p class="time"><b>更新时间：<span>'+(data.uutime==undefined?'':data.uutime)+'</span></b></p>'+
		       		       ' </div>'+
		       		        '<div class="clear"></div>'+
		       		    '</div>');
		       		// 素材下载、通联创建圆圈居中的样式
		       		var big_icon_w = $(".tonglian_title .title_img i").width();
		       		$(".tonglian_title .title_img i").css({
		       			"margin-left" : -big_icon_w / 2
		       		});
				 }else{
					 toastr.error("查询失败");
				 }
		    },
			error : function(){
				layer.close(index);
				toastr.error("查询失败");
			}
		});
}
function getImg(data,durationDate){
	var imgVar="";
	 if(getThumbnailurl1(data)!=""){
		 imgVar= '<div class="title_img">'+
		 '<img src="'+getThumbnailurl1(data)+'">'+
		 '<i></i>'+
		 ' <p>'+durationDate+'</p>'+
		 ' </div>'
	 }
	 return imgVar;
}

function getThumbnailurl1(data){
	var url=(data.thumbnailurl==undefined?"":data.thumbnailurl)
	return url;
}



function toSend(){
	var index = layer.load();
	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"news/sendNews/", // 服务器的地址
        'headers': {'Content-Type': 'application/json'},
        'dataType':'json', // 返回数据类型
        'type':'POST', // 请求类型
        'data':strJson,
        'success':function(data){
        	$("#sendStatus").html('');
        	layer.close(index);
        	if(data.code==0){
        		toastr.success("推送成功");
        		$("#sendStatus").append('<span class="span_yes"><i></i>推送已完成!</span>');
    		 }else{
    			 toastr.error("推送失败");
    			 $("#sendStatus").append('<span class="span_no"><i></i>推送失败!</span>');
    		 }
        },
		error : function(){
			layer.close(index);
			 toastr.error("推送失败");
			 $("#sendStatus").append('<span class="span_no"><i></i>推送失败!</span>');
		}
	});
}
function viewNews(){
	window.open(ctx+pathPage+"news/toNewsViewIndex/?_id="+_id+"&type=join");
}

function download4zip(){
	this.window.location = ctx + pathValue + "download/download4zip/?id="+_id;
}
