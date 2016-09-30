$(function(){
	
	$(window).resize(function(){
		$(".material_set_div").css({"width":$(".material_set_cont").width()});
		var li_w=$(".material_set_cont").width();
		var len=$(".material_set_div").length;
		$(".material_set_list").css({"width":li_w*len});
	})


	//20160513 by lsj
	$(".shared-div li").hover(function(){
		$(this).find(".cancel-share").show();
		$(this).find(".cancel-tishi").show();
	},function(){
		$(this).find(".cancel-share").hide();
		$(this).find(".cancel-tishi").hide();
	})
	$('body').click(function(){
		$(".qr-code").hide();		
	})
	
	
//	$(".material-cont-list > li > div").hover(function(){
//		alert(11);
//		$(this).find(".img-mask").show();
//	},function(){
//		$(this).find(".img-mask").hide();
//	})
	//关闭弹窗
	$(".close-btn,.button-div button").click(function(){
		$(this).parents(".popup").hide();
	})
	//取消分享
	$(".cancel-share").click(function(){
		$(".share-manmk-popup").show();
	})
	//文稿编辑
	$(".edit-li").click(function(){
		$(".edit-wg-popup").show();
	})
    //文稿查看弹窗
    $(".material_img_div,.material_about_div h5,.dongtai_div ul li").click(function(){
    	$(".wengao-look-popup").show();
    	$(".wengao-look-popup").minHeight(bodyH+"px")
    })
    $('.set_time_div a,.sucaiji_div li,.set_img_div,.about_set_div h4').click(function(){
    	$(".material-popup").show();
    })
   
    $("#pageDiv2").onairPage({"callback":getShareDocs,"pageSize":12});
//    动态加载开始
    getShareDocs(1,12);
	

    
})


/**
 * 共享文稿
 * @param pageNow
 * @param pageSize
 */
function getShareDocs(pageNow,pageSize){

	var index = layer.load();
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	var keyWord = $("#search").val();
	if(!keyWord){
		keyWord = "";
	}
	var onairMap = new OnairHashMap();
	onairMap.put("keyWord", keyWord);
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	$.ajax({
		url : ctx + pathValue+"fieldNews/findFieldNews/",
		//url : ctx + pathValue+"presentation/query/",
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
				toastr.error("查询共享文稿失败");
				return;
			}
			if(code == 0 &&data.results&& data.results.length > 0){
				$("#inner2").hide();
	       		$("#outter2").show();
				loadDocsHtml(data);
			}else{
				$("#outter2").hide();
       			$("#inner2").show();
       			$("#inner2").html('');
       			$("#inner2").append("<div class='no_data'></div>");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询共享资源失败");
		}
	});

}

function loadDocsHtml(obj){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	$(results).each(function(i,item){	
		 html.push('<div class="material_div">');
		 html.push('<div class="material_img_div">');
		 html.push('<p>'+processValue(item.template.content)+'</p>');
		 html.push('<a href="javascript:;" onclick="getShareDocsDetail(\''+item._id+'\')">'+processThumbnailurl(item)+'</a>');
		 html.push('</div>');
		 html.push('<div class="material_about_div">');
		 html.push('<h5><a href="javascript:;" onclick="getShareDocsDetail(\''+item._id+'\')">'+cutTitleString(processValue(item.title),30,"...")+'</a></h5>');
		 html.push('<p class="name_p">'+processValue(item.cusename)+'</p>');
		 html.push('<div class="clearfloat">');
		 html.push('<p class="set_time_p fl"><i></i>'+processValue(item.uutime)+'</p>');
		 html.push(	'<i class="icon zhiding '+(item.top==1?"active":"")+'"  onclick="toTop(this,\''+item._id+'\')"><span>置顶</span></i>');
		 html.push(	'</div>');
		 html.push('<div class="caozuo_div clearfloat">');
		 html.push(	'<p class="peo_num fl"><i class="icon"><span>参与人</span></i>'+(item.participants!=undefined?(Array.isArray(item.participants)?item.participants.length:0):0)+'</p>');
		 html.push('<p class="tuisong_p fl"><i class="icon" onclick="openSendShareDocs(\''+item._id+'\')"><span>推送</span></i>'+(item.pushtotal==undefined?0:item.pushtotal)+'</p>');
		 html.push('<i class="icon share_icon fr" onclick="showWechatImg(this,\''+item._id+'\')"><span>分享</span></i>');
		 html.push('</div>');
		 html.push('</div>');
		 html.push('<div class="material-mask">');
		 html.push('<div class="mask-close" onclick="closeWechatImg(this)">');
		 html.push('</div>');
	//	 html.push('<img src="'+ctx+'/common/images/qr-code.png"/>');
		 html.push('<div id="code'+i+'"></div>');
		 html.push('<p>微信扫一扫，分享给同事</p>');
		 html.push('</div>');
		 html.push('</div>');
	})
	$("#shareDocs").html(html.join(""));
	//materialdivhover()
	$("#pageDiv2").resetOnairPageParameter(totalRecord,currentPage);	
}
//共享文稿鼠标移入移除事件
function materialdivhover(){
	$(".material_img_div").hover(function(){
		$(this).find("p").show();
	},function(){
		$(this).find("p").hide();
	})
}
/**
 * 共享文稿列表中的分享
 * @param This
 * @param id
 */
function showWechatImg(This,id){
	$(This).parents(".material_div").find(".material-mask").show();
	$(This).parents(".material_div").find("[id^='code']").html('');
	$(This).parents(".material_div").find("[id^='code']").qrcode( {width: 137,height:137,text:"http://"+window.location.host+"/api/share/getShareDocsInfo/?_id="+id+"&userId="+userId+"&type=doc"});
	
}
function closeWechatImg(This){
	$(This).parent(".material-mask").hide();
}
/**
 * 置顶 
 * @param This
 * @param id
 */
function toTop(This,id){
	var index = layer.load();
	var top="1";
	if($(This).hasClass("active")){
		top="0";
	}
	//$(This).toggleClass("active")
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("top", top);
	onairMap.put("id", id);
	$.ajax({
		url : ctx + pathValue+"fieldNews/top/",
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
				toastr.error("置顶失败");
				return;
			}
			if(code == 0){
				getShareDocs(1,12);
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("置顶失败");
		}
	});
	
}
function openSendShareDocs(id){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "shareresource/sendShareNews/?_id="+id+"&pushType=SHAREDOCS",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['400px','300px'],
				title : "新增推送路径",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
			//	maxmin : true,
				scrollbar : false,
				//offset: ['300px', '200px'],
				content : data
			});
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 详情
 * @param id
 */
function getShareDocsDetail(id){
	
	$("#sharevideoAudioUl").empty();
	$("#sharevideoAudioNoData").html('');
	
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("fieldNewsId",id);
	
	$.ajax({
		url : ctx + pathValue+"fieldNews/findFieldNewsById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			if(code == 0){
				var result = response.data;
				if(result){
					
					var template = result.template;
					/**
					 * 标题
					 */
					$("#sharetitle").html(process(template.title));
					$("#shareuutime").html(process(result.uutime));
					$("#sharecreateor").html(process(result.cusename));
					/**
					 * 记者
					 */
					$("#sharereporter").html(process(template.reporter));
					$("#sharetvStation").html(process(template.tvStationName))
					/**
					 * 文稿
					 */
		        	$("#sharedocs").html(process(template.docsContentHTML));
					$("#shareflashDiv").html('');
					$("#shareflashDiv").html('<div class="bfqloading" id="previewFlashContent">'+
					'<div id="player">'+
						'</div>		'+		
						'</div>');
					/**
					 * 分享人
					 */
					$("#shareuserul").html('');	
					if(result.participants&&result.participants.length>0){
						$.each(result.participants,function(i,item){
							if(item.userName){
								$("#shareuserul").append("<li>"+(item.userName.substring(item.userName.length-2,item.userName.length))+"</li>");
							}else{
								$("#shareuserul").append("<li>"+item.userName+"</li>");
							}
						})
					}
					/**
					 * 右侧操作
					 */
					$("#rightopre").html('');
					$("#rightopre").append('<li class="edit-li" onclick="editsharedocs(\''+id+'\',\''+(result.cuserid==userId?"0":"1")+'\')"></li>'+
				    		'<li class="download-li"onclick="downsharedocs(\''+id+'\')" ></li>'+
				    		'<li class="del-li '+(result.cuserid==userId?"":"active")+'" onclick="deletesharedocs(\''+id+'\',\''+(result.cuserid==userId?"0":"1")+'\')"></li>'+
				    		'<li class="qr-share" onclick="tosharedocs(this,\''+id+'\',event)">'+
				    			'<div class="qr-code">'+
				    				'<div class="">'+
				    					'<i></i>'+
					    				'<div name="sharediv"></div>'+
					    				'<p>微信扫一扫，分享给同事</p>'+
				    				'</div>	'+	    				
		    					'</div>'+
				    		'</li>')
					/**
					 * 最下方素材列表
					 */
					$("#shareDocsOpe").html('');
					$("#shareDocsOpeNoData").html('');
					if(result.logs){
						$("#shareDocsOpe").show();
						loadshareDocsOpeList(result.logs)
					}else{
						$("#shareDocsOpe").hide();
						$("#shareDocsOpeNoData").append("<div class='no_data'></div>");
					}
					//$("#shareDocsOpe").html()
					var videoAudioTempArr = [];
					var picTempArr = [];
					var videos = result.videos;
					if(videos && videos.length > 0){
						 //预览区加载视频
						for(var i=0;i<videos.length;i++){
							var video = videos[i];
							videoAudioTempArr.push(video);
						}
						
					}
					var audios = result.audios;
					if(audios && audios.length > 0){
						for(var i=0;i<audios.length;i++){
							var audio = audios[i];
							videoAudioTempArr.push(audio);
						}
					}
					
					if(videoAudioTempArr.length > 0){
						loadDocsMediaHtml(videoAudioTempArr,"videoaudio");
					}
					var pics = result.pics;
					if(pics && pics.length > 0){
						for(var i=0;i<pics.length;i++){
							var pic = pics[i];
							picTempArr.push(pic);
						}
						loadDocsMediaHtml(picTempArr,"pic");
					}
					
					//默认加载视音频文件
					if(videos!=undefined&&videos.length>0){
			        	var wanurl=(videos[0].prewar==undefined?"":videos[0].prewar.wanurl)
			        	resourcesPreview(wanurl,"false","false");
				    }else{
				    	
			        	if(audios!=undefined&&audios.length>0){
				        	var wanurl=(audios[0].prewar==undefined?"":audios[0].prewar.wanurl)
				        	resourcesPreview(wanurl,"true","false");
				        }else{
				        	if(pics==undefined ||pics.length<=0){
				        		
								$("#sharevideoAudioNoData").append('<img src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
				        	}
				        	$("#player").append('<img src="'+ctx+'/common/images/material_not_pic3.jpg" width="100%">')
				        }
				     }
					
				}
				var bodyH = $(".news-style-div").height()+460;
			    var popupH = $("#shareDetail .popup_inner").height()+300;
			    if(popupH<bodyH){
			    	$("#shareDetail").height(bodyH+"px");
			    }
			    else{
			    	$("#shareDetail").height(popupH+"px");
			    }
//				alert(popuph);//浏览器当前窗口文档body的高度
//				alert(document.body.scrollheight)
//			    $("#shareDetail").height(document.body.scrollHeight);
			    
			}else{
				toastr.error("查询共享失败！");
			}
					
		},
		error : function() {
			layer.close(index);
			toastr.error("查询共享失败！");
		}
	});
	$("html,body").animate({
		scrollTop : 0
	}, 0);
	$("#shareDetail").show();
	

}

//加载音视频
function loadDocsMediaHtml(obj,mediaType){
	layer.closeAll('page');
	
	if(mediaType == "pic"){
		$(obj).each(function(i,item){
			var html = [];	 
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()));
			var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			html.push('<li>');
			html.push(processPlayerIco(wanurl,item.mtype));
			html.push('	<p>'+cutStr(item.name,14,"...")+'</p>');
			html.push('	<div class="">');
			html.push('<a href='+item.wanurl+' data-lightbox="example-1">')
			html.push("<img  src='"+vslt+"'> ");
			html.push('<a>')
			html.push('	</div>');
			html.push('</li>');
			if((i+1)%2==0){
				html.push("<div class='clear'></div>");
			}
			$("#sharevideoAudioUl").append(html.join(""));
		});
	}else{
		$(obj).each(function(i,item){
			var avHtml=[];
			var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":(item.vslt+getMaterialResolution()))
		//	var vslt=((item.vslt==undefined||item.vslt=='undefined')?ctx+"/common/images/audio_list2.jpg":item.vslt)
            var wanurl=(item.prewar==undefined?item.wanurl:item.prewar.wanurl);
			avHtml.push('<li>');
		    avHtml.push(processPlayerIco(wanurl,item.mtype));
		    avHtml.push(dealAVtypeValue(item,"cp"));
		    avHtml.push('	<p>'+cutStr(item.name,14,"...")+'</p>');
		    avHtml.push('	<div class="">');
		    avHtml.push('<img src="'+vslt+'"/>')
		    avHtml.push('	</div>');
		    avHtml.push('</li>');
	        if((i+1)%2==0){
	        	avHtml.push("<div class='clear'></div>");
			}	
			$("#sharevideoAudioUl").append(avHtml.join(""));
		})
	}
	
} 
/**
 * 操作日志
 * @param logs
 */
function loadshareDocsOpeList(logs){
	//加载操作日志
		$(logs).each(function(i,item){
			var logHtml=[];
			logHtml.push('<li class="clearfix">');
			logHtml.push('<span>'+process(item.userName)+'</span>');
			logHtml.push(process(item.content));
			logHtml.push('<span class="time-s fr">');
			logHtml.push(process(item.time)+'</span></li>');
			$("#shareDocsOpe").append(logHtml.join(""));
		})
}
/**
 * 
 * @param id
 * @param isdelete 0能删 1不能删除
 */
function deletesharedocs(id,isdelete){
	if(isdelete==1){
		return ;
	}
	layer.confirm('确认删除共享文稿？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		layer.close(outIndex);
		//deleteDocs(outIndex);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("ids", id);
		$.ajax({
			url : ctx + pathValue+"fieldNews/deleteFieldNews/",
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
					toastr.error("删除失败");
					return;
				}
				if(code == 0){
					$("#shareDetail").hide();
					getShareDocs(1,12);
				}
			},
			error : function() {
				layer.close(index);
				toastr.error("删除失败");
			}
		});
		
	}, function(){
	});


	
}
/**
 * 分享
 * @param id
 */
function downsharedocs(id){
	layer.confirm('确认复制文稿？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		layer.close(outIndex);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("id", id);
		$.ajax({
			url : ctx + pathValue+"fieldNews/copyFieldNews/",
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
					toastr.error("复制失败");
					return;
				}
				if(code == 0){
					toastr.success("复制成功");
					//getShareDocs();
				}
			},
			error : function() {
				layer.close(index);
				toastr.error("复制失败");
			}
		});
	}, function(){
	});
	//copyFieldNews
	
}

function tosharedocs(This,id,e){
	$("[name=sharediv]").html('');
//	alert(ctx+"/api/share/getShareDocsInfo/?_id="+id+"&userId="+userId+"&type=1")
	$("[name=sharediv]").qrcode( {width: 137,height:137,text:"http://"+window.location.host+"/api/share/getShareDocsInfo/?_id="+id+"&userId="+userId+"&type=doc"});
	$(".qr-code").show();
	e.stopPropagation();
}
/**
 * 
 * @param id
 * @param isown 0自己为创建人 1分享人打开
 */
function editsharedocs(id,isown){
	layer.confirm('确认编辑共享文稿？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		layer.close(outIndex);
		if(isown==0){
			window.open(ctx+pathPage+"sharedocs/toEditShareDocsCreate/?sharedocId="+id)
		}else{
			window.open(ctx+pathPage+"sharedocs/toEditShareDocsShare/?sharedocId="+id)
		}
	}, function(){
	});
	
}
function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}
