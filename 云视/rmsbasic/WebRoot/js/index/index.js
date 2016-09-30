
//主页初始化文稿总数
function initDocsTotle() {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'presentation/count/',
		data:strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				$("#div_news_docs").html(data.data.count + "<p>新闻文稿</p>");
			}else{
				toastr.error("文稿统计失败");
			}
		},
		error : function(e){}
	});
}

//主页初始化视音频总数
function initVideoAudioTotle() {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"mtype":["0","1"]}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'media/count/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				$("#div_news_media").html(data.data.count + "<p>视音频</p>");
			}else{
				toastr.error("视音频统计失败");
			}
		},
		error : function(e){}
	});
}

//主页初始化图片总数
function initPicTotle() {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"mtype":["2"]}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'media/count/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				$("#div_news_picture").html(data.data.count + "<p>图片</p>");
			}else{
				toastr.error("图片统计失败");
			}
		},
		error : function(e){}
	});
}

//主页初始化通联总数
function initNewsTotle() {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'news/getNewsTotal/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				$("#div_news_news").html(data.data.count + "<p>新闻通联</p>");
			}else{
				toastr.error("通联统计失败");
			}
		},
		error : function(e){}
	});
}

//主页上判断是否有待审核的通联
function judgeToDeal() {
	var dealNews=0;
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"type":"0"}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'news/getNewsUnDeal/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				if(data.data.dealState > 0){
					$("#un_deal").show();
					$("#mengban").show();
					$("#toauditid").show();
					//20160719修改首页添加的逻辑
					dealNews=data.data.dealState;
					
					
				}else{
				}
				
				/*else{
					alert(2);
					$("#un_deal").hide();
				}*/
			}else{
				toastr.error("获取代办任务失败");
			}
		},
		error : function(e){
		}
	});
	return dealNews;
}

//主页上判断是否有待推送的通联
function judgeToDealForPush() {
	var sendNews=0;
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"type":"1"}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'news/getNewsUnDeal/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				if(data.data.dealState > 0){
					$("#un_deal").show();
					$("#mengban").show();
					$("#topushid").show();
					//
					sendNews=data.data.dealState;
				
				}else{
					var display_deal =$('#un_deal').css('display');
					if(display_deal == 'none'){
						$("#un_deal").hide();
					}
					var display_disp =$('#mengban').css('display');
					if(display_disp == 'none'){
						$("#mengban").hide();
					}
				}
			}else{
				toastr.error("获取代办任务失败");
			}
		},
		error : function(e){
		}
		
	});
	return sendNews;
}	

//主页上初始化最新公告
function getNewNotices(currentPage,pageNum) {
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"currentPage":currentPage,"pageNum":pageNum}
	strJson= JSON.stringify(strJson);
	$("#ul_news_notices").html("");
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'notice/findNotices/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				var html = "";
				var htmlJX = "";
				if(data.data.results != null &&data.data.results.length > 0){
					for(var i = 0; i< data.data.results.length ; i++){
						var noticeTitle=data.data.results[i].noticetitle?data.data.results[i].noticetitle:"";
						var noticeContent=data.data.results[i].noticecontent?data.data.results[i].noticecontent:"";
						noticeContent=cutString(noticeContent,150,"...");
						var strinfo = data.data.results[i].noticetitle + " : " + data.data.results[i].noticecontent;
						var cutStrInfo = cutString(strinfo,88,"...");
						html += "<li id=\""+data.data.results[i]._id+"\"><i class=\"stand\"></i>"+
									"<p style=\"cursor:pointer;word-break:break-all;\" class=\"stand notice_text\" onclick=\"toPage('notice/indexToNoticeDetailMain/?id="+data.data.results[i]._id+"');\">"+ cutStrInfo + "</p>"+
									"<span class=\"stand fr\">"+getTimeByType('y',data.data.results[i].ctime) +
										"<p>"+getTimeByType('t',data.data.results[i].ctime)+"</p>"+
									"</span>"+
								"</li>";
						htmlJX+='<div class="notice_about clearfloat">'+
							'<i></i>'+
							'<div class="notice_news">'+
								'<a href="#" onclick="toPage(\'notice/indexToNoticeDetailMain/?id='+data.data.results[i]._id+'\');">'+
									'<h4>'+noticeTitle+'</h4>'+
									'<p>'+noticeContent+'</p>'+
								'</a>'+
							'</div>'+
						'</div>';
					}
				}else{
					//toastr.info("暂无公告");
				}
				//以前的公告第一套
				$("#ul_news_notices").html(html);
				//目前公告第二版本
				$("#ul_news_noticesJX").html(htmlJX);
				
			}else{
				toastr.error("获取公告失败");
			}
		},
		error : function(e){}
	});
}
//主页上初始化我的消息【记者、审核人】
function getMyNewsMessages() {
	var index = layer.load();
	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"currentPage":1,"pageNum":5}
	strJson= JSON.stringify(strJson);
	$("#div_news_message").html("");
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url: ctx+pathValue+"news/findNewsByUser/", //服务器的地址
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				var html = "";
				if(data.data.results != null && data.data.results.length > 0){
					html += "<ul id='news_message_div' style='position:relative;'>";
					html += "<p style='text-align:center; position:absolute; bottom:-22px; left:0; width:100%; z-index:99;font-size:12px; display:none;'>无更多数据</p>";
					for(var i = 0; i< data.data.results.length ; i++){
							//拼接审核列表
							var detailListStr = "";
							var detailList = data.data.results[i].check;
							var checkuserList = data.data.results[i].checkuser;
							var new_id = data.data.results[i]._id;
							var type = "" ;
							if(toString(data.data.results[i].cuserid) == userId){
								type = "create";
							}
							if(null != checkuserList && checkuserList.length > 0){
								for(var j=0;j<checkuserList.length;j++){
									if(checkuserList[j].userId == userId && checkuserList[j].status =='3'){
										type = "assign" ;
									}
								}
							}
							if(null != detailList && detailList.length > 0){
								for(var k=0;k<detailList.length;k++){
									var info = "";
									if(toString(detailList[k].checkinfo) != ""){
										info = "，意见："+"<span style=\"width:55%;\">"+cutStr(toString(detailList[k].checkinfo),32,"...")+"</span>";
									}
									detailListStr += "<p>"+
										toString(detailList[k].cusename)+toString(detailList[k].checkopt)+","+statusToMsg(detailList[k].status) + info +
									"</p>";
								}
							}else{
								detailListStr = "--";
							}   //<b id="head_img_b" style="margin-top:0;">'+item.name.substring(item.name.length-2,item.name.length)+'</b>
							html +=	"<li class='other reply_green'>"+
										"<b id=\"head_img_b\" class=\"heading_info\" style=\"margin-top:0;\">"+toString(data.data.results[i].cusename).substring(toString(data.data.results[i].cusename).length-2,toString(data.data.results[i].cusename).length)+"</b>"+
										"<div class='dialogue stand'>"+
										"<p>"+
											"<span class='dia_name'>"+toString(data.data.results[i].cusename)+"</span>"+
											"<span class='station'>"+toString(data.data.results[i].areaname)+"</span>"+
											"<span class='dia_time'>"+getTimeByType('y',data.data.results[i].uutime)+"&nbsp;&nbsp;"+getTimeByType('t',data.data.results[i].uutime)+
											"</span>"+
										"</p>"+
										"<p class='dia_cont'>"+
										"<span class='stand' style='margin-top: 10px;'>创建了一条新闻通联任务："+"<a title='"+toString(data.data.results[i].title)+"' style='margin-top: 0px;' href='javascript:waitToDo(\""+new_id+"\",\""+type+"\");'>"+cutStr(toString(data.data.results[i].title),32,"...")+"</a>"+
											"</span>"+
										"</p>"+
										"<i></i>"+
										"<p class='audit_status'>"+
											"任务当前总体状态："+
											"<span style=\"font-weight:bold\">"+statusToMsg(data.data.results[i].status)+"</span>"+
										"</p>"+
										"<div class='dia_down'>"+
											"<div class='dia_down_cont'>"+
												"<p class='stand'>审核状态详情：</p>"+
												"<div class='stand' style='width: 80%;'>"+
												   detailListStr+
												"</div>"+
											"</div>"+
											"<b class='zoom_btn' onclick='openMessageDiv(this);'></b>"+
										"</div>"+
									"</div>"+
								"</li>";
					}
					html += "</ul>";
				}
				$("#div_news_message").html(html);
				scrollLoadNews();
			}else{
				toastr.error("获取消息失败");
			}
			layer.close(index);
		},
		error : function(e){
			layer.close(index);
		}
	});
}

function openMessageDiv(obg){
	//个人首页我的信息板块
		$(obg).parent().find('.dia_down_cont').toggle();
		$(obg).toggleClass('active')
}
 function scrollLoadNews(){
	var pageno = 2;   //当前页数
	var pageNum = 5;   //每页条数
	$("#div_news_message").scroll(function(){
        var divHeight = $(this).height();
        var nScrollHeight = $(this)[0].scrollHeight;
        var nScrollTop = $(this)[0].scrollTop;
        if(nScrollTop + divHeight >= nScrollHeight) {
        	var strJson = {"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"currentPage":pageno,"pageNum":pageNum}
			strJson= JSON.stringify(strJson);
	       //请求数据开始
	        $.ajax({
				type : 'POST',
				dataType:'json',
				headers: {'Content-Type': 'application/json'},
				url: ctx+pathValue+"news/findNewsByUser/", //服务器的地址
				data: strJson,
				async : false,
				success : function(data) {
					if(data.code == 0){
						var html = "";
						if(data.data.results != null && data.data.results.length > 0){
							for(var i = 0; i< data.data.results.length ; i++){
									//拼接审核列表
									var detailListStr = "";
									var detailList = data.data.results[i].check;
									var checkuserList = data.data.results[i].checkuser;
									var new_id = data.data.results[i]._id;
									var type = "" ;
									if(toString(data.data.results[i].cuserid) == userId){
										type = "create";
									}
									if(null != checkuserList && checkuserList.length > 0){
										for(var j=0;j<checkuserList.length;j++){
											if(checkuserList[j].userId == userId && checkuserList[j].status =='3'){
												type = "assign" ;
											}
										}
									}
									if(detailList != "" && detailList != undefined && detailList.length == 0){
										for(var k=0;k<detailList.length;k++){
										var info = "";
										if(toString(detailList[k].checkinfo) != ""){
											info = "，意见："+"<span style=\"width:55%;\">"+cutStr(toString(detailList[k].checkinfo),32,"...")+"</span>";
										}
											detailListStr += "<p>"+
											toString(detailList[k].cusename)+toString(detailList[k].checkopt)+","+statusToMsg(detailList[k].status) + info +
											"</p>";
										}
									}else{
										detailListStr = "--";
									}
									html +=	"<li class='other reply_green'>"+
												"<b id=\"head_img_b\"  class=\"heading_info\" style=\"margin-top:0;\">"+toString(data.data.results[i].cusename).substring(toString(data.data.results[i].cusename).length-2,toString(data.data.results[i].cusename).length)+"</b>"+
												"<div class='dialogue stand'>"+
												"<p>"+
													"<span class='dia_name'>"+toString(data.data.results[i].cusename)+"</span>"+
													"<span class='station'>"+toString(data.data.results[i].areaname)+"</span>"+
													"<span class='dia_time'>"+getTimeByType('y',data.data.results[i].uutime)+"&nbsp;&nbsp;"+getTimeByType('t',data.data.results[i].uutime)+
													"</span>"+
												"</p>"+
												"<p class='dia_cont'>"+
											"<span class='stand' style='margin-top: 10px;'>创建了一条新闻通联任务："+"<a title='"+toString(data.data.results[i].title)+"' style='margin-top: 0px;' href='javascript:waitToDo(\""+new_id+"\",\""+type+"\");'>"+cutStr(toString(data.data.results[i].title),32,"...")+"</a>"+
											"</span>"+
											"</p>"+
												"<i></i>"+
												"<p class='audit_status'>"+
													"任务当前总体状态："+
													"<span style=\"font-weight:bold\">"+statusToMsg(data.data.results[i].status)+"</span>"+
												"</p>"+
												"<div class='dia_down'>"+
													"<div class='dia_down_cont'>"+
														"<p class='stand'>审核状态详情：</p>"+
														"<div class='stand' style='width: 80%;'>"+
														   detailListStr+
														"</div>"+
													"</div>"+
													"<b class='zoom_btn' onclick='openMessageDiv(this);'></b>"+
												"</div>"+
											"</div>"+
										"</li>";
							}
							$("#news_message_div").append(html);
							pageno = pageno + 1;
						}else{
							$("#news_message_div>p").show();
						}
					}else{
						toastr.warning("加载失败");
					}
				},
				error : function(e){}
			});
		//请求数据完毕
        }
	});
 }

 function lookNewsDetail(new_id){
 	window.open(ctx + pathPage + "news/toNewsViewIndex/?_id="+new_id);
 }
 
 function waitToDo(new_id,type){
  	if(type=='assign'){
  		uCont("news/toNewsInnerView/?_id="+new_id+"&type=assign");
  	}else if(type=='create'){
  		window.open(ctx + pathPage + "news/toNewsViewIndex/?_id="+new_id + "&type=create");
  	}else{
  		window.open(ctx + pathPage + "news/toNewsViewIndex/?_id="+new_id);
  	}
 }
 
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
 
function initHref(){
	$("#docMain").attr("href",ctx+pathPage+"material/toDocsListIndex/");
	$("#materialMain").attr("href",ctx+pathPage+"material/toMaterialListIndex/");     //?type=0,1
	$("#picMain").attr("href",ctx+pathPage+"material/toMaterialListIndex/?type=2");
	$("#tongLianMain").attr("href",ctx+pathPage+"news/toMyNewsListIndex/");
	$("#noticeMain").attr("href",ctx+pathPage+"notice/toNoticeMain/");
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
 
//title = cutString(title,15,"...");
function cutString(str,len,suffix){  
  	if(!str) return "";  
  	if(len<= 0) return "";  
    if(!suffix) suffix = "";  
   	var templen=0;  
   	for(var i=0;i<str.length;i++){  
        if(str.charCodeAt(i)>255){  
           templen+=2;  
       	}else{  
           templen++  
        }  
        if(templen == len){  
           return str.substring(0,i+1)+suffix;  
        }else if(templen >len){  
           return str.substring(0,i)+suffix;  
        }  
    }  
    return str;  
} 

function setNavigation(a,b){
	navChangeClass(a,b);
}

function waitting_to_push(){
	setNavigation(1,6);
	window.open(ctx + pathPage + "news/toNewsListIndex/");
}

function waitting_to_audit(){
	setNavigation(1,5);
	window.open(ctx + pathPage + "news/toAuditNewsListIndex/");
}
