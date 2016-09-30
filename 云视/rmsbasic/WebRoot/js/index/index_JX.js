$(function(){
	
	if(getUrlConfig.nameTV=="JS"){
	}else{
		$("#footer").hide();
	}
	//调用滚屏事件
	$('#swiper_div').fullpage({
		'navigation': true,
		
	});
	$("#fp-nav").addClass("left").removeClass("right");


	var img_h=$(".news_circle_img img").height();
	var div_h=$(".news_circle_img").height();
	$(".news_circle_img img").css({"padding-top":(div_h-img_h)/2});



	//消息提醒
	$(".examine_alert").hover(function(){
		$(this).find("p").html("待审核");},function(){
			$(this).find("p").html("消息提醒");
	})
	$(".push_alert").hover(function(){
		$(this).find("p").html("待推送");},function(){
			$(this).find("p").html("消息提醒");
	})
	$("#toDocJX").attr("href",ctx+pathPage+"material/toAddDocs/");
	$("#uploadmaterialJX").click(function(){
		$("#upload_cont").css("visibility","visible");
		$(".min_upload_icon").hasClass("max_upload_icon")&&$(".min_upload_icon").removeClass("max_upload_icon");
		$(".upload_cont").hasClass("max")&&$(".upload_cont").removeClass("max");
	})
	//更多公告
	$("#noticeMainJX").attr("href",ctx+pathPage+"notice/toNoticeMain/");
	
	//用户下拉
    	var listDown=$('.header .user_div .user_pop');
    	$('#user_menu').click(function(){
    	listDown.toggle();
        });
   	    listDown.hover(function(){
    	},function(){
    	$(this).hide();
        });
    	listDown.find('li').click(function(){
    		listDown.hide();
    	}); 	
    //用户信息弹窗
    	var user_popup=$('.user_message_popup').hide();
    	$('.close_btn').click(function(){
    	user_popup.hide()
    	})
    	$('#personal_m').click(function(){
    		getUserInfo();
    		user_popup.show()
    	})
    	$('#save_id').click(function(){
    	//	saveUserInfo();
    		user_popup.hide();
    	})
    	$('#reset_id').click(function(){
    		user_popup.hide();
    	})
    	
    	
    //用户信息弹窗
    	var user_popup=$('.user_message_popup').hide();
    	$('.close_btn').click(function(){
    	user_popup.hide()
    	})
    	$('#personal_m').click(function(){
    		getUserInfo();
    		user_popup.show()
    	})
    	$('#save_id').click(function(){
    	//	saveUserInfo();
    		user_popup.hide();
    	})
    	$('#reset_id').click(function(){
    		user_popup.hide();
    	})
    	
    	
    	
	//获取热点事件
	getHotEvent();
	//获取公告
	getNewNotices(1,2);
	getMyCreateNews();
	getWaitingDealNews();
	getWaitingSendNews();
	getShareNewsListData(1,3);
	var dealNews=judgeToDeal();
	var sendNews=judgeToDealForPush();
	dealAndSendNews(dealNews,sendNews)
	
});

function dealAndSendNews(dealNews,sendNews){
	dealNews=Number(dealNews);
	sendNews=Number(sendNews);
	$(".big_btn_cont .task_alert").each(function(i,item){
		if(dealNews==0&&sendNews==0){
			if(i==0){
				$(this).show();
			}else{
				$(this).hide();
			}
			
		}else if(dealNews>0&&(sendNews==0)){
			if(i==1){
				$(this).show().find("b").html(dealNews);
			}else{
				$(this).hide();
			}
		}else if(dealNews==0&&sendNews>0){
			if(i==2){
				$(this).show().find("b").html(sendNews);
			}else{
				$(this).hide();
			}
		}else{
			if(i==3){
				$(this).show().find("b:eq(0)").html(sendNews+dealNews);
				$(this).find("b:eq(1)").html(dealNews);
				$(this).find("b:eq(2)").html(sendNews);
			}else{
				$(this).hide();
			}
		}
	})
	
	
}

/**
 * 我创建的
 */
function getMyCreateNews(){
	var strJson={"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
		'url':ctx+pathValue+"news/findNews/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
           	if(data.code==0){
           		$("#myCreateIndex").html(data.data.totalRecord);
           	}else{
    			 //toastr.error("查询失败");
    		 }
            
        }
	})
}
/**
 * 待处理
 */
function getWaitingDealNews(){
	var strJson={"checkStatus":3,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
		'url':ctx+pathValue+"news/findNews/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
           	if(data.code==0){
           		$("#waitingDealIndex").html(data.data.totalRecord);
           	}else{
    			 //toastr.error("查询失败");
    		 }
            
        }
	})
}
/**
 * 待推送
 */
function getWaitingSendNews(){
	var strJson={"sendStatus":7,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
		'url':ctx+pathValue+"news/findNews/", //服务器的地址
		'dataType':'json', //返回数据类型
		'headers': {'Content-Type': 'application/json'},
		'type':'POST', //请求类型
		'data':strJson,
		'success':function(data){
			if(data.code==0){
				$("#waitingSendIndex").html(data.data.totalRecord);
			}else{
				//toastr.error("查询失败");
			}
			
		}
	})
}
/**
 * 新闻通联
 * @param pageNow
 * @param pageSize
 */
function getShareNewsListData(pageNow,pageSize){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
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
	       		loadShareNewsHtml(data);
			}else{
				$(".section3").remove();
				$("#fp-nav li:eq(2)").remove();
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询共享资源失败");
		}
	});
}
function loadShareNewsHtml(obj){
	var results = obj.results;
	$("#shareNewsIndex").html('');
	$.each(results,function(i,item){
		$("#shareNewsIndex").append('<div class="news_circle_div">'+
				'<a href="#" onclick="showNewsDetai(\''+item._id+'\')">'+
				'<div class="news_circle_img">'+
				processThumbnailurl(item)+
					'<p>'+item.areaname+'</p>'+
				'</div>'+
			'</a>'+
			'<div class="about_news_div">'+
				'<p>'+cutTitleString(process(item.title),20,"...")+'</p>'+
				'<div class="news_mng clearfloat">'+
					'<div class="num_div">'+
						'<p><i class="num_icon1"></i><span>'+(item.readetotal==undefined?"0":item.readetotal)+'</span></p>'+
						'<p><i class="num_icon2"></i><span>'+(item.pushtotal==undefined?"0":item.pushtotal)+'</span></p>'+
					'</div>'+
					'<p class="news_time">'+processValue(item.uutime)+'</p>'+
				'</div>'+
			'</div>'+
		'</div>')
	})
	
}

function processThumbnailurl(item){
	var img="";
	if(item.thumbnailurl&&item.thumbnailurl!="undefined"){
		if(item.thumbnailurl!='undefined'){
			img="<img src=\""+item.thumbnailurl+getMaterialResolution()+"\"/>";
		}
	}else{
		if(item.audios!=undefined&&item.audios.length>0){
			img="<img src="+ctx+"/common/images/audio.png/>";
		}else{
			img="<img src="+ctx+"/common/images/wengao_img.png/>";
		}
	}
	return img;
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
function showNewsDetai(id){
	//跳到哪个角色的页面？
	window.open(ctx + pathPage + "news/toNewsViewIndex/?_id="+id);
}
