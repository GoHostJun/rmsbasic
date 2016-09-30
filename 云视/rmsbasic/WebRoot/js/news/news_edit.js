$(function(){
	$("#ascrail2000").remove();
	$("#form").validationEngine();
	//点击页面部分视音频标签隐藏
	$("body").click(function(){
		$(".select_label_option").hide();
	})
	/*20160220开始*/
	initUeditor();
	 $(".extended").click(function(){
			$(this).parent(".extended-div").find(".extended-drop").toggle();
		})
	 $('.extended-div').click(function(e){
		e.stopPropagation();
	 })
		
/*20160220结束*/
	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	initNews(strJson);
	$(".close_icon_h i,.alert_true_btn").click(function(){
		$(".add_alert").hide();
	})
})
//初始化
function initNews(strJson,flag){
	var index = layer.load();
	$.ajax({
        'url':ctx+pathValue+"news/findNewsById/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
        	layer.close(index);
        	if(data.code==0){
        		//模板信息
	       		if(flag!=0 && flag!=1){
	       			if(data.data.template!=undefined){
	       				var template=data.data.template;
	       				wengao(template);
	       			} 
	       		}
	       		if(flag==0||flag==undefined){
	       			$("#avInfo .img_cont").remove();
	       			$("#avInfo img").remove();
	       		 //预览区加载视频,如果没有视频加载音频
			        if(data.data.videos!=undefined&&data.data.videos.length>0){
			        	var wanurl=(data.data.videos[0].prewar==undefined?"":data.data.videos[0].prewar.wanurl)
			        	resourcesPreview(wanurl,"false","false");
			        }else{
			        	if(data.data.audios!=undefined&&data.data.audios.length>0){
				        	var wanurl=(data.data.audios[0].prewar==undefined?"":data.data.audios[0].prewar.wanurl)
				        	resourcesPreview(wanurl,"true","false");
				        }else{
				        	$("#player").append('<img src="'+ctx+'/common/images/material_not_pic3.jpg" width="100%">')
				        	$("#avInfo").append('<img src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
				        }
			        }
	       			//视频信息
	       			if(data.data.videos!=undefined&&data.data.videos.length>0){
	       				$.each(data.data.videos,function(i,item){
	       					$("#avInfo ul").append(
			        				'<li class="img_cont">'+
			        				 '<div class="select_label">'+
			        			       '<p class="select_label_p" onclick="toggleAVLabel(this,event)">标签<b></b></p>'+
			        			       '<div class="select_label_option">'+
			        			         '<p>成片<i class="'+dealAVtypeActive(item,"cp")+'" onclick="addCPMedia(\''+item._id+'\',this,\''+item.mtype+'\',\''+item.extend+'\',event)"></i></p>'+
//			        			         '<p>成片2<i class="'+dealAVtypeActive(item,"cp2")+'" onclick="addCPMedia2(\''+item._id+'\',this,\''+item.mtype+'\',\''+item.extend+'\')"></i></p>'+
//			        			         '<p>X&nbsp;&nbsp;X<i></i></p>'+
			        			      ' </div>'+
			        			    '</div>	'+
			                    	'<div class="img_div" onclick="javascript:resourcesPreview(\''+(item.prewar==undefined?"":item.prewar.wanurl)+'\',\'false\',\'true\');">'+
			                    		'<a><img src="'+item.vslt+getMaterialResolution()+'"/>'+
			                            '<i></i></a>'+
			                            '<b class="del_img" onclick="deleteM(\''+item._id+'\',event,0,'+item.mtype+')"></b>'+
			                            '<a></a>'+
			                        '</div>'+
			                        '<div class="video_cont">'+
			                        	'<p class="video_cont_inner" title="'+(item.name==undefined?"":item.name)+'">'+cutStr((item.name==undefined?"":item.name),20,"...")+'</p>'+
			                        	'<p class="video_cont_time">'+(item.uutime==undefined?"":item.uutime.split(" ")[0])+dealAVtypeValue(item,"cp")+'</p>'+
			                        '</div>	'+
			                       '<div class="clear"></div>'+					   
								'</li>'
			        				);
	       					if((i+1)%2==0){
	       						$("#avInfo").append("<div class='clear'></div>");
	       					}
	       					middelRound();
	       					$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
			    			$(".video_audio_div li .video_cont").css("height",$(".video_audio_div li .img_div").height());
	       				})
	       			}
	       			//音频信息
	       			if(data.data.audios!=undefined&&data.data.audios.length>0){
	       				$.each(data.data.audios,function(i,item){
	       					$("#avInfo ul").append( 
			        				'<li class="img_cont">'+
			        				 '<div class="select_label">'+
			        			       '<p class="select_label_p"onclick="toggleAVLabel(this,event)">标签<b></b></p>'+
			        			       '<div class="select_label_option">'+
			        			         '<p>成片<i class="'+dealAVtypeActive(item,"cp")+'" onclick="addCPMedia(\''+item._id+'\',this,\''+item.mtype+'\',\''+item.extend+'\',event)"></i></p>'+
//			        			         '<p>X&nbsp;&nbsp;X<i></i></p>'+
//			        			         '<p>X&nbsp;&nbsp;X<i></i></p>'+
			        			      ' </div>'+
			        			    '</div>	'+
			                    	'<div class="img_div" onclick="javascript:resourcesPreview(\''+(item.prewar==undefined?"":item.prewar.wanurl)+'\',\'true\',\'true\');">'+
			                    		'<a><img src="'+ctx+'/common/images/audio_list2.jpg"/>'+
			                            '<i></i></a>'+
		       							'<b class="del_img" onclick="deleteM(\''+item._id+'\',event,0,\''+item.mtype+'\')"></b>'+
			                            '<a></a>'+
			                        '</div>'+
			                        '<div class="video_cont">'+
			                        	'<p class="video_cont_inner" title="'+(item.name==undefined?"":item.name)+'">'+cutStr((item.name==undefined?"":item.name),20,"...")+'</p>'+
			                        	'<p class="video_cont_time">'+(item.uutime==undefined?"":item.uutime.split(" ")[0])+dealAVtypeValue(item,"cp")+'</p>'+
			                        '</div>	'+
			                       '<div class="clear"></div>'+					   
								'</li>'
			        				
			                    );
	       					
	       					if((i+1)%2==0){
	       						$("#avInfo").append("<div class='clear'></div>");
	       					}
	       					middelRound();
	       					$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
			    			$(".video_audio_div li .video_cont").css("height",$(".video_audio_div li .img_div").height());
	       				})
	       			}
	       		}
	       		if(flag==1||flag==undefined){
	       			$("#picInfo .img_cont").remove();
	       			$("#picInfo img").remove();
	       			//图片信息
	       			if(data.data.pics!=undefined&&data.data.pics.length>0){
	       				$.each(data.data.pics,function(i,item){
	       					$("#picInfo").append(
	       							'<div class="img_cont">'+
	       							'<div class="img_div" >'+
	       							'<a href='+item.wanurl+' data-lightbox="example-1"><img src="'+item.wanurl+getMaterialResolution()+'"/>'+
	       							'<div class="shadow">'+
	       							'<p>更新时间：'+(item.uutime==undefined?"":item.uutime.split(" ")[0])+'</p>'+
	       							'</div></a>'+
	       							'<b class="del_img" onclick="deleteM(\''+item._id+'\',event,1,\''+item.mtype+'\')"></b>'+
	       							'</div>'+
	       							'<p title="'+(item.name==undefined?"":item.name)+'">'+cutStr((item.name==undefined?"":item.name),10,"...")+'</p>'+
	       					'</div>');
	       					if((i+1)%2==0){
	       						$("#picInfo").append("<div class='clear'></div>");
	       					}
	       				})
	       			}else{
			        	$("#picInfo").append('<img src="'+ctx+'/common/images/material_not_tip.png" width="100%">')
					}
	       			$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);

	       		}
		        
		 }else{
			 toastr.error("查询详情失败");
		 }
        },
		'error' : function(){
			layer.close(index);
			toastr.error("查询详情失败");
		}
        
	});
	
}

function wengao(template){
	/**
	 * 标题
	 */
	$("#title").val(process(template.title));

	
	$("#createor").text(process(template.createor));
	/**
	 * 记者
	 */
	$("#reporter").val(process(template.reporter));
	
	$("#tvStation").append(station(tvStation,template.tvStationCode))
	
	
	/**
	 * 关键词
	 */
	$("#keyWords").val(process(template.keyWords));
	/**
	 * 来源
	 */
	$("#source").val(process(template.source));
	/**
	 * 主持人
	 */
	$("#presenter").val(process(template.presenter));
	/**
	 * 配音
	 */
	$("#dubbingMan").val(process(template.dubbingMan));
	/**
	 * 摄像
	 */
	$("#cameraMan").val(process(template.cameraMan));
	/**
	 * 编辑
	 */
	$("#editor").val(process(template.editor));
	/*20160220新添加字段开始*/
	/**
	 * 栏目
	 */
	$("#program").append(station(programJson,template.programNum));
	/**
	 * 通讯员
	 */
	$("#repProviders").val(process(template.repProviders))
	/**
	 * 分类
	 */
	$("#customType").append(station(customTypeJson,template.customTypeNum));
	/**
	 * 播报时间
	 */
	$("#playDate").val(process(template.playDate))
	/**
	 * 文稿类型
	 */
	$("#titleType").append(station(titleTypeJson,template.titleTypeNum));
	/**
	 * 题花
	 */
	$("#titleDesign").val(process(template.titleDesign))
	/**
	 * 协作
	 */
	$("#assistants").val(process(template.assistants))
	/**
	 * 字幕词
	 */
	$("#subtitleWords").val(process(template.subtitleWords))
	/**
	 * 特约记者
	 */
	$("#specialReporters").val(process(template.specialReporters))
	/**
	 * 文稿
	 */
	$("#docsContent").val(process(template.content));
	$("#docsContentHTML").val(process(template.docsContentHTML));
	ue.ready(function() {
		ue.setContent(template.docsContentHTML);
		//ueditor.setContent('UEditor报错TypeError: me.body is undefined');
		});
	
	
	/*20160220新添加字段结束*/
}
function dealAVtypeActive(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"active":"");
}
function dealAVtypeValue(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"<b>"+(avtype=="cp"?"成片":"")+"</b>":"");
}
/**
 * 视音频标签的显示隐藏
 * @param This
 * @param e
 */
function toggleAVLabel(This,e){
	$(This).nextAll(".select_label_option").toggle();
	e.stopPropagation();
}
/**
 * 修改素材，添加成品的属性
 * @param mediaId 媒体资源id
 */
function addCPMedia(mediaId,This,mtype,extend,event){
	event.stopPropagation();
	var videoAudioIndex;
	$(This).toggleClass("active");
	if(!$(This).hasClass("active")){
		//ajax
		var cpIndex=extend.indexOf("cp");
		extend=Array.of(extend);
		extend.splice(cpIndex,1);
		updateNewsMaterial(extend,mtype,mediaId)
		//$(This).parents(".img_cont").find(".video_cont_time b").remove();
	}else{
		if(extend==undefined||extend.length==0||extend=='undefined'){
			extend=new Array();
			extend.push("cp");
		}else{
			extend=Array.of(extend);
			extend.push("cp");
		}
		updateNewsMaterial(extend,mtype,mediaId)
		//$(This).parents(".img_cont").find(".video_cont_time").append("<b>成片</b>");
	}
	
}

function updateNewsMaterial(extend,mtype,mediaId){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	 onairMap.put("newsId",_id);
	 onairMap.put("extend",extend);
	 if(mtype==0){
			onairMap.put("videoId", mediaId);
		}else {
			onairMap.put("audioId", mediaId);
		}
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 $.ajax({
	        'url':ctx+pathValue+"news/updateNewsMaterial/", //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':onairMap.toJson(),
	        'success':function(data){
	        	layer.close(index);
		       	if(data.code==0){
		       		toastr.success("操作成功");
	 		       	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		 		   	strJson= JSON.stringify(strJson);
		 		   	initNews(strJson,0);
		       	}else{
		       		toastr.error("操作失败");
		       		
		       	}
	        },
			error : function() {
				layer.close(index);
				toastr.error("操作失败");
			}
	    })
}

var tvStation=areaMap();

var programJson={"0":"新闻联播"};
var customTypeJson={"0":"国内新闻","1":"国际新闻"};
var titleTypeJson={"0":"标题","1":"口播","2":"片头"};
function station(objectJson,station){
	var options="";
	$.each(objectJson,function(i,item){
		if(i==station){
			options+="<option value="+i+" selected>"+item+"</option>"
		}else{
			options+="<option value="+i+">"+item+"</option>"
		}
	})
	return options;
}
//提交通联
 function saveNews(){
	  var bool = $('#form').validationEngine('validate');
	  if (!bool) {
		  return;
	  }
	 var index = layer.load();
	 var onairMap = new OnairHashMap();
	 var template = new OnairHashMap();
	 /**
		 * 标题
		 */
		var title = $("#title").val();
		var createor=uname;
		/**
		 * 记者
		 */
		var reporter = $("#reporter").val();
		/**
		 * 供片台标识
		 */
		var tvStationCode = $("#tvStation").val();
		/**
		 * 供片台名称
		 */
		var tvStationName = $("#tvStation").find("option:selected").text();
		/**
		 * 关键词
		 */
		var keyWords = $("#keyWords").val();
		/**
		 * 来源
		 */
		var source = $("#source").val();
		/**
		 * 主持人
		 */
		var presenter = $("#presenter").val();
		/**
		 * 配音
		 */
		var dubbingMan = $("#dubbingMan").val();
		/**
		 * 摄像
		 */
		var cameraMan = $("#cameraMan").val();
		/**
		 * 编辑
		 */
		var editor = $("#editor").val();
		/*20160220添加的字段开始*/
		/**
		 * 栏目
		 */
		var programNum=$("#program").val();
		var program=$("#program").find("option:selected").text();
		/**
		 * 通讯员
		 */
		var repProviders=$("#repProviders").val();
		/**
		 * 分类
		 */
		var  customTypeNum=$("#customType").val();
		var customType = $("#customType").find("option:selected").text();
		/**
		 * 播报时间
		 */
		var playDate=$("#playDate").val();
		/**
		 * 文稿类型
		 */
		var titleTypeNum =$("#titleType").val();
		var titleType =$("#titleType").find("option:selected").text();
		/**
		 * 题花
		 */
		var titleDesign=$("#titleDesign").val();
		/**
		 * 协作
		 */
		var assistants=$("#assistants").val();
		/**
		 * 字幕词
		 */
		var subtitleWords=$("#subtitleWords").val();
		/**
		 * 特约记者
		 */
		var specialReporters=$("#specialReporters").val();
		/**
		 * 文稿
		 */
		var ueContent = ue.getContent();
		var ueContentTxt = ue.getContentTxt();
		$("#docsContent").val(ueContentTxt);
		$("#docsContentHTML").val(ueContent);
		var docsContent=$("#docsContent").val();
		var docsContentHTML=$("#docsContentHTML").val();
		/*20160220添加的字段结束*/
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("newsId", _id);
	 onairMap.put("newsTitle", $("#title").val());
	 /**
	  * 构建文稿编目参数
	  */
	 var templateObj = new Object();
	 templateObj.title = title;
	 templateObj.program = program;
	 templateObj.createor = createor;
	 templateObj.reporter = reporter;
	 templateObj.tvStationName = tvStationName;
	 templateObj.tvStationCode = tvStationCode;
	 templateObj.customType = customType;
	 templateObj.keyWords = keyWords;
	 templateObj.source = source;
	 templateObj.presenter = presenter;
	 templateObj.dubbingMan = dubbingMan;
	 templateObj.cameraMan = cameraMan;
	 templateObj.editor = editor;
	 templateObj.repProviders = repProviders;	 
	 templateObj.titleType = titleType;	 
	 templateObj.playDate = playDate;	 
	 templateObj.titleDesign = titleDesign;	 
	 templateObj.assistants = assistants;	 
	 templateObj.subtitleWords = subtitleWords;	 
	 templateObj.specialReporters = specialReporters;	 
	 templateObj.content = docsContent;	 
	 templateObj.docsContentHTML = docsContentHTML;	
	 templateObj.customTypeNum = customTypeNum;
	 templateObj.titleTypeNum = titleTypeNum;	
	 templateObj.programNum = programNum;
	 onairMap.put("template",templateObj);
	 onairMap.put("areaname",tvStationName);
	 onairMap.put("areacode",tvStationCode);
	$.ajax({
        'url':ctx+pathValue+"news/updateNews/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':onairMap.toJson(),
        'success':function(data){
        	layer.close(index);
        	if(data.code==0){
        		toastr.success("修改成功");
        		window.location.href=ctx+pathPage+'news/toNewsViewIndex/?_id='+_id+"&type=create"
        	}else{
        		toastr.error("修改失败");
        	}
        } ,
		'error' : function(){
			layer.close(index);
			toastr.error("修改失败");
		}
	})
}

function templateMap(template){
	 $("#wengao [id]").each(function(i,item){
		 template.put($(this).attr("id"),$(this).val());
	 })
	 
}
//删除素材
function deleteM(MaterId,e,flag,mtype){
	 var index = layer.load();
	var videoIds=new Array();
	var audioIds=new Array();
	var picIds=new Array();
	var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("newsId", _id);
	 if(mtype==0){
		 videoIds.push(MaterId);
		 onairMap.put("videoIds", videoIds);
	 }else if(mtype==1){
		 audioIds.push(MaterId);
		 onairMap.put("audioIds",audioIds);
	 }else if(mtype==2){
		 picIds.push(MaterId);
		 onairMap.put("picIds", picIds);
	 }
	e.stopPropagation();
	$.ajax({
        'url':ctx+pathValue+"news/deleteNewsMaterial/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':onairMap.toJson(),
        'success':function(data){
        	layer.close(index);
        	if(data.code==0){
        		toastr.success("删除成功");
        		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	 		   	strJson= JSON.stringify(strJson);
	 		   	initNews(strJson,flag);
        	}else{
        		toastr.error("删除失败");
        	}
        },
		'error' : function(){
			layer.close(index);
			toastr.error("删除失败");
		}
	})
}
/**
 * 初始化素材列表
 * @param flag flag==0 素材列表显示视频音频 flag==1 素材列表显示图片
 */
function showMaterial(flag){
	$("#saveFlag").val(flag);
	$(".add_alert ").show();
	var materialTypeName;
	if(flag==0){
		materialTypeName=["0","1"];
	}else if(flag==1){
		materialTypeName=["2"];
	}
	//方法在news_material_list  js中
	getListDataByType(materialTypeName,1,6);
}

		/** ***预览*** */
        function resourcesPreview(wanurl,isAudio,isAutoPlay) {
        	
			$(".cms_div_content2").removeClass("novideo");
			$("#draggable").html("<div id='previewFlashContent'></div><i class='hide_icon hide'></i>");//情况当前播放内容
			$("#draggable i.hide_icon").click(function(){
				$("#draggable").toggleClass("active");
				$(this).toggleClass("active");
			})
			$("#fs").html("<div id='previewFlashContent'></div>");
			var swfVersionStr = "11.1.0";
			var d = new Date();
			var flashvars = {};
			flashvars.time = d.getTime();
			flashvars.assetsUrl = ctx + "/flash/preview/";
			flashvars.mediaURL = encodeURIComponent(wanurl);
			flashvars.playerType = "1";
			flashvars.autoHideControlBar="true";
			flashvars.autoPlay=isAutoPlay;
			flashvars.isAudio=isAudio;
			var xiSwfUrlStr = "playerProductInstall.swf";
			var preloaderUrl = flashvars.assetsUrl + "YsPlayer.swf"
					+ "?time=" + flashvars.time;// preloader.swf
			var params = {};
			params.quality = "high";
			params.bgcolor = "#000000";
			params.allowscriptaccess = "sameDomain";
			params.allowfullscreen = "true";
			params.wmode = "transparent";
			params.autoPlay="false";
			var attributes = {};
			attributes.id = "MoviePreviewer";
			attributes.name = "MoviePreviewer";
			attributes.align = "middle";
			swfobject.embedSWF(preloaderUrl, "previewFlashContent", "100%",
					"199px", swfVersionStr, xiSwfUrlStr, flashvars, params,
					attributes);
			swfobject.createCSS("#flashContent",
					"display:block;text-align:left;");

}
//视频图像上圆圈图标居中
function middelRound(){
	var icon_w=$(".video_audio_div .img_div i").width();
	var icon_h=$(".video_audio_div .img_div i").height();
	$(".video_audio_div .img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
}
function dataToValue(value){
	return value==undefined?"":value;
}
//取消隐藏列表
function hideConfirm(){
	$(".add_alert").hide();
}