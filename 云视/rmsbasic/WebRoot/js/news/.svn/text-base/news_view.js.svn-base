$(function(){
	navJXChangeClass(4,5);
	$("#ascrail2000").remove();
	$(".extended").click(function(){
		$(this).parent(".extended-div").find(".extended-drop").toggle();
	})
	$('.extended-div').click(function(e){
		e.stopPropagation();
	})
	if(type=="create"){
		$("#newsList").attr("href","javascript:uCont('news/toNewsMyCreateList/')")
			$(".btn_div").append(
					'<a href="javascript:;"  class="btn btn_org org1">添加通联人</a>'+
		            '<a href="javascript:;" class="btn btn_org org2">取消通联</a>');
		}
	else  if(type=="join"){
		$("#newsList").attr("href","javascript:uCont('news/toNewsMyWaitingSendList/')")
		$("#edit").remove();
		
		
	}else  if(type=="complete"){
		$("#newsList").attr("href","javascript:uCont('news/toNewsCompleteList/')")
		$("#edit").remove();
		
		
	}else if(type=="assign"){
		$("#newsList").attr("href","javascript:uCont('news/toNewsMyWaitingDealList/')")
		$("#edit").remove();
		$(".btn_div").append(
				'<a href="javascript:;" class="btn btn_green ">审核通过</a>'+
			    '<a href="javascript:;" class="btn btn_red">审核打回</a>');
	} 
	if(typeof(fromType)!='undefined'&&fromType!=undefined){
		if("message"==fromType){
			//单独进行判断
			dealMessage();
		}
	}
	 var index = layer.load();
	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"news/findNewsById/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
        	 layer.close(index);
        	 if(data.code==0){
	       		//未审核打回下
	       		if(data.data.status!=6){
	   				$("#edit").remove();
	       		}else {
	       			if(type=="create"){
	   					$(".btn_div").append(
	   							'<a href="javascript:;" onclick="sub()" class="btn btn_org ">提交审核</a>');
	   				}
	       		}
	       		//模板信息
		        if(data.data.template!=undefined){
		        	var template=data.data.template;
		        	/**
					 * 标题
					 */
					$("#title").html(process(template.title));
					$("#createor").append(process(template.createor));
					/**
					 * 记者
					 */
					$("#reporter").append(process(template.reporter));
					$("#tvStation").append(process(template.tvStationName))
					$("#program").append(process(template.program))
					$("#customType").append(process(template.customType))
					$("#titleType").append(process(template.titleType))
					
					/*20160220新添加字段开始*/
					/**
					 * 通讯员
					 */
					$("#repProviders").append(process(template.repProviders))
					/**
					 * 播报时间
					 */
					$("#playDate").append(process(template.playDate))
					/**
					 * 题花
					 */
					$("#titleDesign").append(process(template.titleDesign))
					/**
					 * 协作
					 */
					$("#assistants").append(process(template.assistants))
					/**
					 * 字幕词
					 */
					$("#subtitleWords").append(process(template.subtitleWords))
					/**
					 * 特约记者
					 */
					$("#specialReporters").append(process(template.specialReporters))
					/**
					 * 文稿
					 */
		        	$("#docs").append(process(template.docsContentHTML));
					/*20160220新添加字段结束*/
					
					/**
					 * 关键词
					 */
					$("#keyWords").append(process(template.keyWords));
					/**
					 * 来源
					 */
					$("#source").append(process(template.source));
					/**
					 * 主持人
					 */
					$("#presenter").append(process(template.presenter));
					/**
					 * 配音
					 */
					$("#dubbingMan").append(process(template.dubbingMan));
					/**
					 * 摄像
					 */
					$("#cameraMan").append(process(template.cameraMan));
					/**
					 * 编辑
					 */
					$("#editor").append(process(template.editor));
		        }
		        //审核信息
		        $("#check").append('<h3>审核人'+dealStatusStr(data.data)+'</h3>');
		        if(data.data.checkuser!=undefined&&data.data.checkuser.length>0){
		        	$.each(data.data.checkuser,function(i,item){
		        		 $("#check").append(' <p><span>审核人：'+item.realname+'</span><i class="'+dealStatusIoc(item)+'" onmouseover="onOver(this)" onmouseout="onOut(this)">'+getSpan(item)+'</i><b>'+item.uutime+'</b></p>')
		        	})
		        }
		        $("#join").append('<h3>通联人</h3>');
		        if(data.data.shareuser!=undefined&&data.data.shareuser.length>0){
		        	$.each(data.data.shareuser,function(i,item){
		        		 $("#join").append(' <p><span>通联人：'+item.realname+'</span><b>'+item.uutime+'</b></p>')
		        	})
		        }
		        
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
		                    	'<div class="img_div" onclick="javascript:resourcesPreview(\''+(item.prewar==undefined?"":item.prewar.wanurl)+'\',\'false\',\'true\');">'+
		                    		'<a><img src="'+item.vslt+getMaterialResolution()+'"/>'+
		                            '<i></i></a>'+
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
		                    	'<div class="img_div" onclick="javascript:resourcesPreview(\''+(item.prewar==undefined?"":item.prewar.wanurl)+'\',\'true\',\'true\');">'+
		                    		'<a><img src="'+ctx+'/common/images/audio_list2.jpg"/>'+
		                            '<i></i></a>'+
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
		       //图片信息
		        if(data.data.pics!=undefined&&data.data.pics.length>0){
		        	$.each(data.data.pics,function(i,item){
		        		$("#picInfo").append(
		        				'<div class="img_cont">'+
		        				'<div class="img_div" >'+
		        				'<a href='+item.wanurl+' data-lightbox="example-1"><img src="'+item.wanurl+getMaterialResolution()+'">'+
		        				'<div class="shadow">'+
		        				'<p>更新时间：'+(item.uutime==undefined?"":item.uutime.split(" ")[0])+'</p>'+
		        				'</div></a>'+
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
		        
		        //////////////////////////////////////////////////
		         var currentUserId=userId;
	            
	            //遍历返回值
	            var tempdata=new Array();
	            if(data.data.comments!=undefined && data.data.comments.length>0){
	            	dataIterator(data.data.comments,tempdata,currentUserId);
	            }
	
	            //评论获取焦点
	            $(".comment").focus(function(){
	                if($(this).val()=='评论…'){
	                    $(this).val('');
	                    $(".text-box").addClass("text-box-on");
	                    $(".btn").addClass("btn-off");
	                }
	            })
	            //评论失去焦点
	            $(".comment").blur(function(){
	                if($(this).val()==''){
	                  //  $(this).val('评论…');
	                    $(".text-box").removeClass("text-box-on");
	                    $(".btn").removeClass("btn-off");
	                }
	            })
	            //评论键盘事件
	           /* $(".comment").keyup(function(){
	                if($(this).val()!=''&& $(".comment").val().length<=140){
	                    $(".btn").removeClass("btn-off");
	                }else{
	                    if(!$(".btn").hasClass("btn-off")){
	                        $(".btn").addClass("btn-off");
	                    }
	                }
	                $(".length").text($(".comment").val().length);
	            })*/
		        
		        ////////////////////////////////////////////////
			 }else{
				 toastr.error("查询详情失败");
			 }
        },
		error : function(){
			 layer.close(index);
			 toastr.error("查询详情失败");
		}
        
	});
	
	/* 设计代码开始  */
	var user_popup=$('.user_message_popup').hide();
	$('.close_btn').click(function(){
	$(".popup").hide()
	})
	$('#personal_m').click(function(){
		user_popup.show()
	})
	$('.edit_btn_div').find('a').click(function(){
	$(".popup").hide()	
	})
	//通联弹窗
    $(".btn_div .btn_green").click(function(){
    	$(".audit_through_popup").show();
    })
    $(".btn_div .btn_red").click(function(){
    	$(".audit_back_popup").show();
    })
    //添加通联人弹出提示
    $(".btn_div .org1").click(function(){
    	/*layer.confirm('是否确认添加通联人？', {
		    btn: ['确认','取消'] 
		}, function(outIndex){
			addJoin(outIndex);
		}, function(){
		});*/
    	$.ajax({
    		type : 'POST',
    		contentType : 'application/json',
    		url : ctx + pathPage + "material/modifyNews/?_id="+_id+"&title="+$("#title").html(),
    		cache : false,
    		async : false,
    		success : function(data) {
    			layer.open({
    				type : 1, // page层
    				area: ['770px','560px'],
    				title : "添加通联人",
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
    	   })
    	
    })
    $(".btn_div .org2").click(function(){
    	layer.confirm('是否取消通联？', {
		    btn: ['确认','取消'] 
		}, function(outIndex){
			cancelNews(outIndex);
		}, function(){
		});
    	//$(".audit_cancel_popup").show();
    })
//	$(".btn_div .btn").click(function(){
//		$(".audit_opinipn").html("双击输入审核意见");
//	})
	
	 //$(".audit_opinipn").dblclick(edit);
	
	
	//音视频区缩略图高度
	setTimeout(function(){
		$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
	},200)
	$(window).resize(function(){
		$(".video_audio_div .img_div img").css("height",$(".video_audio_div .img_div img").width()/1.8);
	})
	
    
	/* 设计代码结束  */
})
function dealAVtypeValue(item,avtype){
	return ((item.extend==undefined?"":item.extend).indexOf(avtype)>=0?"<b>"+(avtype=="cp"?"成片":"")+"</b>":"");
}
//展示提交审核div
	function sub(){
		layer.confirm('是否提交审核？', {
		    btn: ['确认','取消'] 
		}, function(outIndex){
			subAudit(outIndex);
		}, function(){
		});
		//$(".audit_sub_audit").show();
	}


//通联查看页审核状态滑过
function onOver(This){
	$(This).find(".sh_thanks").show();
}
function onOut(This){
	$(This).find(".sh_thanks").hide();
}
/** ***评论*** */
 	function dataIterator(data,tempdata,currentUserId){
            if(tempdata.length>0){
            	data=tempdata;
            }
            var tempdata=new Array();
            if(data.length<1)return;
            data.reverse();
            $.each(data,function(i,item){
            	commentBoxData(tempdata,item,currentUserId);
            })
            dataIterator(tempdata,tempdata,currentUserId);
        }
	//评论展示
	function commentBoxData(tempdata,item,currentUserId){
            var commentBox =
            	 '<b id="head_img_b" class="heading_info">'+item.cusename.substring(item.cusename.length-2,item.cusename.length)+'</b> '+
//            	 '  <div class="head_icon stand"></div>'  +
            	 '  <div class="dialogue stand">' +getMeOrOthers(item,currentUserId).userNameInfo+'  '+
            	 '  <span class="dia_cont stand">'+($('#'+item.pid).length>0?"回复"+$("#"+item.pid+" #head_img_b").html()+": ":"")+ item.comcontent +'</span>'  +
            	 '  '+(getMeOrOthers(item,currentUserId).delOrRepl)+'' +
            	 '  <i></i>'  +
            	 '  </div>'  +
            	 '  </li>'
            if(item.pid=="0"){
                $("#dialogue").append('<li class="other reply_green" user="self" id="'+item._id+'" pid="'+item.pid+'"> '  +commentBox);
            }else if($("#"+item.pid).length>0){
                $("#"+item.pid).after('<li class="reply reply_green" user="self" id="'+item._id+'" pid="'+item.pid+'"> ' +commentBox);
            }else{
                tempdata.push(item);
            }
		
	}
	//根据用户与当前评论人id进行分别不同效果展示
	function getMeOrOthers(item,currentUserId){
		var userNameInfo=""; var delOrRepl="";
		if(item.cuserid==currentUserId){
			userNameInfo= '<span class="dia_name stand"><span>我</span>：</span>' ;
			delOrRepl='<p class="dia_time">'+item.uutime+' <span onclick="removeDel(this)">删除</span></p>';
		}else{
			userNameInfo= '<span class="dia_name stand"><span>'+item.cusename+'</span>：</span>' ;
			delOrRepl='<p class="dia_time">'+item.uutime+' <span onclick="reply(this)" user="'+item.cusename+'">回复</span></p>';
		}
		return {"userNameInfo":userNameInfo,"delOrRepl":delOrRepl};
	}
	//发起评价
	function talk(This){
		
	    if($(This).hasClass("btn-off")){
	        return;
	    }
	    if($(".comment").val()=='评论…' || $.trim($(".comment").val())==''){
	    	 return;
	    }
	    var posi=$(This).prev().attr("posi");
	    if(posi==undefined||posi==""){
	    	posi="0";
	    }
		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"pId":posi,"content":$(".comment").val()}
		strJson= JSON.stringify(strJson);
		 var index = layer.load();
	    $.ajax({
	        'url':ctx+pathValue+"comment/addComment/", //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':strJson,
	        'success':function(data){
	        	$(".comment").attr("posi","");
	        	 layer.close(index);
		       	if(data.code==0){
	       		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	       			strJson= JSON.stringify(strJson);
	       			toastr.success("评论成功");
	       		    getComments();
		       	}else{
		       		toastr.error("评论失败");
		       		
		       	}
	        },
			error : function(){
				$(".comment").attr("posi","");
				layer.close(index);
				toastr.error("评论失败");
			}
	    })
	    
	}
        //删除自己评论
        function removeDel(This){
        	 var index = layer.load();
            var _id= $(This).parents("li").attr("id");
            if($("[pid="+_id+"]").length>0){
            	 layer.close(index);
            	toastr.error("无法删除,当前评论已有人进行回复");
            	return ;
            }
            var strJson={"commentId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
   		    strJson= JSON.stringify(strJson);
            $.ajax({
    	        'url':ctx+pathValue+"comment/deleteComment/", //服务器的地址
    	        'dataType':'json', //返回数据类型
    	        'headers': {'Content-Type': 'application/json'},
    	        'type':'POST', //请求类型
    	        'data':strJson,
    	        'success':function(data){
    	        	 layer.close(index);
    		       	if(data.code==0){
    		       		toastr.success("删除评论成功");
    	       		    getComments();
    		       	}else{
    		       		toastr.error("删除评论失败");
    		       		
    		       	}
    	        },
    			error : function(){
    				toastr.error("删除评论失败");
    				layer.close(index);
    			}
    	    })
        }
        //回复别人评论
        function reply(This){
            $(".comment").focus();
            
           // $(".comment").val('回复'+$(This).attr("user")+":");
            $(".comment").attr("placeholder",'回复'+$(This).attr("user")+":");
           var topParent= $(This).parents("li").attr("id")
           $(".comment").attr("posi",topParent);
        }
        //评论成功或者删除局部刷新获取评论列表
        function getComments(){
        	 var index = layer.load();
        	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
   			strJson= JSON.stringify(strJson);
        	 $.ajax({
		 	        'url':ctx+pathValue+"comment/getComments/", //服务器的地址
		 	        'dataType':'json', //返回数据类型
		 	        'headers': {'Content-Type': 'application/json'},
		 	        'type':'POST', //请求类型
		 	        'data':strJson,
		 	        'success':function(data){
		 	        	layer.close(index);
		 		       	if(data.code==0){
		 		       		$(".comment").val('评论…');
		 		       	 $(".comment").attr("placeholder",'评论…');
		 		       		$("#dialogue").html('');
		 		       		var tempdata=new Array();
			 	            if(data.data!=undefined && data.data.length>0){
			 	            	var currentUserId=userId;
			 	            	dataIterator(data.data,tempdata,currentUserId);
			 	            }
		 		       	}else{
		 		       		toastr.error("获取评论列表失败");
		 		       		
		 		       	}
		 	        },
	    			error : function(){
	    				layer.close(index);
	    				toastr.error("获取评论列表失败");
	    			}
		 	    })
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
	//审核信息
	function dealStatusStr(data){
		var statusStr="";
		if(data.status==3)
		{
			statusStr='<b>待审核</b>';
		}
		else if(data.status==5)
		{
			statusStr='<b class="green">审核通过</b>';
		}
		else if(data.status==6){
			statusStr='<b>审核打回</b>';
		}
		return statusStr;
	}
	//审核状态图标
	function dealStatusIoc(data){
		var statusIoc="";
		if(data.status==3){
			statusIoc='sh_icon3';
		}else if(data.status==5){
			statusIoc='sh_icon1';
		}else if(data.status==6){
			statusIoc='sh_icon2';
		}
		return statusIoc;
	}
	//审核信息的图标显示
	function getSpan(item){
		var spanThanks="";
		if(item.status==6||item.status==5){
			if(item.opinion==undefined||item.opinion=='undefined'||item.opinion==''){
				spanThanks='';
			}else{
				
				spanThanks='<span class="sh_thanks" style="display: none;"><span>'+(item.opinion)+'</span><i></i></span>';
				
			}
		}else if (item.status==3){
			spanThanks='';
		}
		return spanThanks;
	}
	//审核同意
	function agree(This,outIndex){
		layer.close(outIndex);
		var index = layer.load();
		var agree=$(This).parent().prev().find("textarea").val();
		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"checkStatus":5,"checkOpinion":agree}
			strJson= JSON.stringify(strJson);
    	 	$.ajax({
	 	        'url':ctx+pathValue+"check/checkNews/", //服务器的地址
	 	        'dataType':'json', //返回数据类型
	 	        'headers': {'Content-Type': 'application/json'},
	 	        'type':'POST', //请求类型
	 	        'data':strJson,
	 	        'success':function(data){
	 	        	layer.close(index);
	 		       	if(data.code==0){
	 		       		toastr.success("操作成功");
	 		       		uCont('news/toNewsMyWaitingDealList/');
	 		       	}else{
	 		       		toastr.error("操作失败");
	 		       		
	 		       	}
	 	        },
    			error : function(){
    				layer.close(index);
    				toastr.error("操作失败");
    			}
	 	    })
		
	}
	//审核不同意
	function disAgree(This){
		var index = layer.load();
		var disAgree=$(This).parent().prev().find("textarea").val();
		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"checkStatus":6,"checkOpinion":disAgree}
			strJson= JSON.stringify(strJson);
    	 	$.ajax({
	 	        'url':ctx+pathValue+"check/checkNews/", //服务器的地址
	 	        'dataType':'json', //返回数据类型
	 	        'headers': {'Content-Type': 'application/json'},
	 	        'type':'POST', //请求类型
	 	        'data':strJson,
	 	        'success':function(data){
	 	        	layer.close(index);
	 		       	if(data.code==0){
	 		       		toastr.success("操作成功");
	 		       		uCont('news/toNewsMyWaitingDealList/');
	 		       	}else{
	 		       	toastr.error("操作失败");
	 		       		
	 		       	}
	 	        },
    			error : function(){
    				layer.close(index);
    				toastr.error("操作失败");
    			}
	 	    })
		
	}
	//取消通联
	function cancelNews(outIndex){
		layer.close(outIndex);
		var index = layer.load();
		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		strJson= JSON.stringify(strJson);
		$.ajax({
 	        'url':ctx+pathValue+"news/cancelNews/", //服务器的地址
 	        'dataType':'json', //返回数据类型
 	        'headers': {'Content-Type': 'application/json'},
 	        'type':'POST', //请求类型
 	        'data':strJson,
 	        'success':function(data){
 	        	layer.close(index);
 		       	if(data.code==0){
 		       		toastr.success("操作成功");
 		       		uCont('news/toNewsMyCreateList/');
 		       	}else{
 		       		toastr.error("操作失败");
 		       		
 		       	}
 	        },
			error : function(){
				layer.close(index);
				toastr.error("操作失败");
			}
 	    })
		
	}
	//复制通联
	function copyNews(){
		layer.confirm('是否将文稿中的素材复制到素材库？', {
		    btn: ['是','否','取消'] ,
		    closeBtn: 0
		}, function(outIndex){
			copyNewsHasMaterial(outIndex);
		}, function(){
			copyNewsNotMaterial();
		});
	}
	
	
	
	//带素材的文稿
	function copyNewsHasMaterial(outIndex){
		layer.close(outIndex);
		var index = layer.load();
		var strJson={"newsId":_id,"status":"1","accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		strJson= JSON.stringify(strJson);
		$.ajax({
 	        'url':ctx+pathValue+"news/copyNews/", //服务器的地址
 	        'dataType':'json', //返回数据类型
 	        'headers': {'Content-Type': 'application/json'},
 	        'type':'POST', //请求类型
 	        'data':strJson,
 	        'success':function(data){
 	        	layer.close(index);
 		       	if(data.code==0){
 		       		toastr.success("操作成功");
 		       		uCont('material/toDocs/');
 		       	    navChangeClass(0,1);
 		       	}else{
 		       		toastr.error("操作失败");
 		       		
 		       	}
 	        },
			error : function(){
				layer.close(index);
				toastr.error("操作失败");
			}
 	    })
		
	}
	//不带素材的文稿
	function copyNewsNotMaterial(){
		
		var index = layer.load();
		var strJson={"newsId":_id,"status":"0","accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		strJson= JSON.stringify(strJson);
		$.ajax({
 	        'url':ctx+pathValue+"news/copyNews/", //服务器的地址
 	        'dataType':'json', //返回数据类型
 	        'headers': {'Content-Type': 'application/json'},
 	        'type':'POST', //请求类型
 	        'data':strJson,
 	        'success':function(data){
 	        	layer.close(index);
 		       	if(data.code==0){
 		       		toastr.success("操作成功");
 		       		uCont('material/toDocs/');
 		       	    navChangeClass(0,1);
 		       	}else{
 		       		toastr.error("操作失败");
 		       		
 		       	}
 	        },
			error : function(){
				layer.close(index);
				toastr.error("操作失败");
			}
 	    })
		
	}
	//通联提交审核
	function subAudit(outIndex){
		layer.close(outIndex);
		var index = layer.load();
		var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		strJson= JSON.stringify(strJson);
		$.ajax({
 	        'url':ctx+pathValue+"check/submitNewsToCheck/", //服务器的地址
 	        'dataType':'json', //返回数据类型
 	        'headers': {'Content-Type': 'application/json'},
 	        'type':'POST', //请求类型
 	        'data':strJson,
 	        'success':function(data){
 	        	layer.close(index);
 		       	if(data.code==0){
 		       		toastr.success("操作成功");
 		       		window.location.reload();
 		       	}else{
 		       		toastr.error("操作失败");
 		       		
 		       	}
 	        },
			error : function(){
				layer.close(index);
				toastr.error("操作失败");
			}
 	    })
	}
	//跳转到编辑通联
	function editNews(){
		uContDiv("news/toNewsEdit/", "content")
	}
	
	function middelRound(){
		var icon_w=$(".video_audio_div .img_div i").width();
		var icon_h=$(".video_audio_div .img_div i").height();
		$(".video_audio_div .img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	}
	/**
	 * 首页消息进入时，滚轮滚动到评论
	 * @param data
	 * @returns
	 */
	function dealMessage(){
		/*$("#edit").remove();
		if(data.cuserid==userId){
			$(".btn_div").append(
					'<a href="javascript:;"  class="btn btn_org org1">添加通联人</a>'+
		            '<a href="javascript:;" class="btn btn_org org2">取消通联</a>');
		}
		
		if(data.shareuser!=undefined&&data.shareuser.length>0){
			$.each(data.shareuser,function(i,item){
				if(userId==item.userId){
					
					return ;
				}
			})
			
		}
		if(data.checkuser!=undefined&&data.checkuser.length>0){
			$.each(data.checkuser,function(i,item){
				if(userId==item.userId){
					$(".btn_div").append(
							'<a href="javascript:;" class="btn btn_green ">审核通过</a>'+
						    '<a href="javascript:;" class="btn btn_red">审核打回</a>');
					return ;
				}
			})
			
		}*/
		window.location.hash="#public_tile";
		
		 
	}

/*添加通联人
 * _id 文稿id
 */
function addJoin(outIndex){
	layer.close(outIndex);
	window.open(ctx+pathPage+"news/toNewsModifyIndex/?_id="+_id);
}