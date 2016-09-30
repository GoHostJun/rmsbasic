$(document).ready(function() {
//	$("#msg-list").niceScroll({cursorwidth:"2px", cursorcolor:"#398F70"});
	getData(1,10);
});

function getData(currentPage,pageNum){
	var strJson={"currentPage":currentPage,"pageNum":pageNum,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	init(strJson);
}

function init(strJson){
	var index = layer.load();
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"news/findNewsByUser/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
    	layer.close(index);
       	if(data.code==0){
       		$("#msg-list").html('');
       		if(data.data.results!=undefined&&data.data.results.length>0){
       			var html="<ul>";
		        $.each(data.data.results,function(i,item){ 
		        	html += putList(item);
		        });
		        html += "</ul>";
		        $("#msg-list").append(html);
       		}else{
       			$("#msg-list").html('<span style=\"display: block; margin: 94px auto; padding: 0; text-align: center;font-size:12px; width: 80px;\" >暂无信息</span>');
       		}
		 }else{
		//	 toastr.error("查询失败");
		 }
        },
		error : function(){
			layer.close(index);
		//	toastr.error("查询失败");
		}
        
	});
}

//function init(strJson){
//	var index = layer.load();
//	strJson= JSON.stringify(strJson);
//	$.ajax({
//        'url':ctx+pathValue+"news/findNewsByUser/", //服务器的地址
//        'dataType':'json', //返回数据类型
//        'headers': {'Content-Type': 'application/json'},
//        'type':'POST', //请求类型
//        'data':strJson,
//        'success':function(data){
//    	layer.close(index);
//       	if(data.code==0){
//       		$("#msg-list").html('');
//       		if(data.data.results!=undefined&&data.data.results.length>0){
//       			var html="<ul id=\'scoll_ul_id\'>";
//       			html += "<p id='noMoreDataId' style='text-align:center; position:absolute; bottom:-22px; left:0; width:49%; z-index:99;font-size:12px; display:none;'>无更多数据</p>";
//		        $.each(data.data.results,function(i,item){ 
//		        	html += putList(item);
//		        });
//		        html += "</ul>";
//		        $("#msg-list").append(html);
//		   //     scrollLoadMyNews();
//       		}else{
//       			$("#msg-list").html('<span style=\"display: block; margin: 94px auto; padding: 0; text-align: center; width: 80px;\" >暂无信息</span>');
//       		}
//		 }else{
//			 toastr.error("查询失败");
//		 }
//        },
//		error : function(){
//			layer.close(index);
//			toastr.error("查询失败");
//		}
//        
//	});
//}

// function scrollLoadMyNews(){
//	var pageno = 2;   //当前页数
//	var pageNum = 5;   //每页条数
//	$("#msg-list").scroll(function(){
//        var divHeight = $(this).height();
//        var nScrollHeight = $(this)[0].scrollHeight;
//        var nScrollTop = $(this)[0].scrollTop;
//        if(nScrollTop + divHeight >= nScrollHeight) {
//        	var strJson={"currentPage":pageno,"pageNum":pageNum,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
//			var index = layer.load();
//			strJson= JSON.stringify(strJson);
//			$.ajax({
//				'url' : ctx + pathValue + "news/findNewsByUser/", // 服务器的地址
//				'dataType' : 'json', // 返回数据类型
//				'headers' : {'Content-Type' : 'application/json'},
//				'type' : 'POST', // 请求类型
//				'data' : strJson,
//				'success' : function(data) {
//					layer.close(index);
//					if (data.code == 0) {
//						if (data.data.results != undefined && data.data.results.length > 0) {
//							var html="";
//							$.each(data.data.results,function(i, item) {
//								html += putList(item);
//							});
//							 $("#scoll_ul_id").append(html);
//							pageno = pageno + 1;
//						}else{
//							$("#noMoreDataId").show();
//						}
//					} else {
//						toastr.error("查询失败");
//					}
//				},
//				error : function() {
//					layer.close(index);
//					toastr.error("查询失败");
//				}
//		
//			});
//        }
//	});
// }

function putList(data){
	//拼接审核列表
	var myState = "";
	var checkList = data.checkuser;
	var shareList = data.shareuser;
	
	if(toString(data.cuserid) == userId){
		myState = dealStatus(data.status);
	}else{
		if(checkList != "" && checkList != undefined && checkList.length > 0){
			for(var i = 0;i<checkList.length;i++){
				if(checkList[i].userId == userId){
					myState = dealStatus(checkList[i].status);
				}
			}
		}
		if(myState == ""){
			if(shareList != "" && shareList != undefined && shareList.length > 0){
				for(var i = 0;i<shareList.length;i++){
					if(shareList[i].userId == userId){
						//myState = dealStatus(shareList[i].status);
						//待推送
						if(shareList[i].status == 8){
							myState = dealStatus(shareList[i].status);
						}else if(shareList[i].status == 7){
							if(data.status != 3 && data.status != 6){
								myState = dealStatus(shareList[i].status);
							}
						}
					}
				}
			}
		}
	}
	if(myState == ""){
		return "";
	}
	var imgurl = getThumbnailurl(data);
		var retData="<li>" +
						imgurl+
						"<div class=\"msg-div\">"+
							"<div class=\"msg-cont\">"+
								"<div><span class=\"msg-name\">"+(data.cusename==undefined?'':data.cusename)+"</span><h4 title='"+(data.title==undefined?'':data.title)+"' style='cursor:pointer;' onclick=mycreate('"+data._id+"')>"+cutStr((data.title==undefined?'':data.title),28,"...")+"</h4><p>"+cutStr((data.template.content==undefined?'':data.template.content),48,"...")+"</p></div>"+
								myState+
							"</div>"+
							"<p class=\"reporter\">记者："+cutStr((data.template.reporter==undefined?'':data.template.reporter),5,"...")+"&nbsp;"+(data.template.tvStationName==undefined?'':data.template.tvStationName)+"<span class=\"fr\">"+getTimeByType('y',(data.uutime==undefined?'':data.uutime))+"&nbsp;&nbsp;"+getTimeByType('t',(data.uutime==undefined?'':data.uutime))+"</span></p>"+
						"</div>"+
					"</li>";
		return retData;
}

//function putList(data){
//	//拼接审核列表
//	var myState = "";
//	var checkList = data.checkuser;
//	var shareList = data.shareuser;
//	if(toString(data.cuserid) == userId){
//		myState = dealStatus(data.status);
//	}else{
//		if(checkList != "" && checkList != undefined && checkList.length > 0){
//			for(var i = 0;i<checkList.length;i++){
//				if(checkList[i].userId == userId){
//					myState = dealStatus(checkList[i].status);
//				}
//			}
//		}
//		if(myState == ""){
//			if(shareList != "" && shareList != undefined && shareList.length > 0){
//				for(var i = 0;i<shareList.length;i++){
//					if(shareList[i].userId == userId){
//						//myState = dealStatus(shareList[i].status);
//						//待推送
//						if(shareList[i].status == 8){
//							myState = dealStatus(shareList[i].status);
//						}else if(shareList[i].status == 7){
//							if(data.status != 3 && data.status != 6){
//								myState = dealStatus(shareList[i].status);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//	if(myState == ""){
//		return "";
//	}
//	var imgurl = getThumbnailurl(data);
//		var retData="<li>" + imgurl +
//				"<div class='msg-div'>"+
//					"<div class='msg-cont'>"+
//						"<div><span class='msg-name'>"+(data.cusename==undefined?'':data.cusename)+"</span><h4 title='"+(data.title==undefined?'':data.title)+"' style='cursor:pointer;' onclick=mycreate('"+data._id+"')>"+cutStr((data.title==undefined?'':data.title),28,"...")+"</h4></div>"+
//						"<p class='msg-cont-inner' title='"+(data.template.content==undefined?'':data.template.content)+"' style='cursor:pointer;' onclick=mycreate('"+data._id+"')>"+cutStr((data.template.content==undefined?'':data.template.content),70,"...")+"</p>"+
//						myState +
//					"</div>"+
//					"<p class='reporter'>记者："+cutStr((data.template.reporter==undefined?'':data.template.reporter),5,"...")+"&nbsp;"+(data.template.tvStationName==undefined?'':data.template.tvStationName)+"<span class='fr'>"+getTimeByType('y',(data.uutime==undefined?'':data.uutime))+"&nbsp;&nbsp;"+getTimeByType('t',(data.uutime==undefined?'':data.uutime))+"</span></p>"+
//				"</div>"+"<div class='clear'>"+"</div>"+
//				"</li>";
//		return retData;
//}

 /**
  * 获取时间的年月日、时分秒部分
  * @param {} type
  * @param {} time
  * @return {}
  */
 function getTimeByType(type,time){
 	var arr = time.split(' ');
 	if(type == 'y'){
 		return arr[0];
 	}else if(type == 't'){
 		return arr[1];
 	}else{
 		return "";
 	}
 }
 
  //审核信息
function dealStatus(status){
	var statusDiv="";
	if(status==3){
		statusDiv='<i class="adopt"><p>待审</p></i>';
	}else if(status==5){
		statusDiv='<i><p>通过</p></i>';
	}else if(status==6){
		statusDiv='<i class="examine"><p>打回</p></i>';
	}else if(status==7){
		statusDiv='<i class="adopt"><p>待推</p></i>';
	}else if(status==8){
		statusDiv='<i><p>已推</p></i>';
	}
	return statusDiv;
}
 
// //审核信息
//function dealStatus(status){
//	var statusDiv="";
//	if(status==3){
//		statusDiv='<i class="examine"><p>待审</p></i>';
//	}else if(status==5){
//		statusDiv='<i class="adopt"><p>通过</p></i>';
//	}else if(status==6){
//		statusDiv='<i><p>打回</p></i>';
//	}else if(status==7){
//		statusDiv='<i class="examine"><p>待推</p></i>';
//	}else if(status==8){
//		statusDiv='<i class="adopt"><p>已推</p></i>';
//	}
//	return statusDiv;
//}

function getThumbnailurl(data){
	if(data.thumbnailurl=='undefined'||data.thumbnailurl==undefined||data.thumbnailurl==''){
		return "";
	}else{
		var videosList = data.videos;
		var audiosList = data.audios;
		var durationTime = 0;
		var durationStr = "";
		if(videosList != "" && videosList != undefined && videosList.length > 0){
			for(var i = 0;i<videosList.length;i++){
				durationTime += videosList[i].duration;
			}
		}
		if(audiosList != "" && audiosList != undefined && audiosList.length > 0){
			for(var i = 0;i<audiosList.length;i++){
				durationTime += audiosList[i].duration;
			}
		}
		if(durationTime != 0){
			durationStr = "<div class=\"duration\">"+
						"<p>"+processDuration(durationTime)+"</p>"+
						"</div>";
		}
		var imgstr = "<div class=\"img-div\">"+
						"<img src=\""+data.thumbnailurl+"\"/>"+
						durationStr +
					"</div>";
		return imgstr;
	}
}

//function getThumbnailurl(data){
//	if(data.thumbnailurl=='undefined'||data.thumbnailurl==undefined||data.thumbnailurl==''){
//		return "";
//	}else{
//		var imgstr = "";
//		imgstr = "<img onclick=mycreate('"+data._id+"') src='"+data.thumbnailurl+"' style='position:relative; z-index:99;cursor:pointer;' />" ;
//		return imgstr;
//	}
//}

function mycreate(_id){
	window.open(ctx+pathPage+"news/toNewsViewIndex/?_id="+_id+"&type=create");
}

 function toString(str){
	if(str == null || str == undefined || str == 'null'){
		str = '';
	}
	return str;
}

function statusToMsg(status){
	if(status == '3'){
		return '待审核';
	}else if(status == '4'){
	  return '审核中';
	}else if(status == '5'){
	  return '审核通过';
	}else if(status == '6'){
	  return '审核打回';
	}else if(status == '7'){
	  return '未推送';
	}else if(status == '8'){
	  return '已推送';
	}else if(status == '9'){
	  return '已完成';
	}else if(status == '10'){
	  return '服务中';
	}else if(status == '11'){
	  return '服务完成';
	}
}

/**
 * 处理时长，将毫秒转换为：00：00：00（时：分：秒）格式
 * @param obj
 * @returns
 */
function processDuration(duration){
		var time = parseFloat(duration) / 1000;
		if (time) {
	       if (time > 60 && time < 60 * 60) {
	           time = "00:" + parseInt(time / 60.0) + ":" + parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60);
	       }else if (time >= 60 * 60 && time < 60 * 60 * 24) {
	           time = parseInt(time / 3600.0) + ":" + parseInt((parseFloat(time / 3600.0) -
	           parseInt(time / 3600.0)) * 60) + ":" +
	           parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
	           parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) ;
	       }else {
	           time = "00:"+"00:"+((parseInt(time)<10)?("0"+parseInt(time)):parseInt(time));
	       }
		}
	   return time;
}

