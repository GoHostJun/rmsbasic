$(function(){
	$("#ascrail2000").remove();
	$("#indexPop").remove();
	$("#indexJX").remove();
	$("#page").onairPage({"callback":getData});
	getData();
	$("#searchButton").click(function(){
		getData(1,10);
	})
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getData(1,10);
        }
    }; 
})

function getData(pagePara,pageNumPara){
	var page;
	var pageNum;
	page=(pagePara==undefined?$("#page").getOnairPageParameter().pageNow:pagePara)
	pageNum=(pageNumPara==undefined?$("#page").getOnairPageParameter().pageSize:pageNumPara)
	var strJson={"keyWord":$("#search").val(),"currentPage":page,"pageNum":pageNum,"completed":9,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	init(strJson);
}

function init(strJson){
	 var index = layer.load();
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"news/findNews/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
        	layer.close(index);
       	if(data.code==0){
       		$("#page").resetOnairPageParameter(data.data.totalRecord);
       		$("#inner_list").html('');
       		$("#inner").hide();
       		$("#outter").show();
       		if(data.data.results!=undefined&&data.data.results.length>0){
		        $.each(data.data.results,function(i,item){ 
		        	$("#inner_list").append(putList(item));
		        	//素材下载、通联创建圆圈居中的样式
		        	var icon_w=$(".list_img_div i").width();
		        	var icon_h=$(".list_img_div i").height();
		        	$(".list_img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
		        })	
       		}else{
       			$("#outter").hide();
       			$("#inner").show();
       			$("#inner").html('');
       			$("#inner").append("<div class='no_data'></div>");
       		}
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

function putList(data){
		var duration=getAVPinfo(data).duration;
		var durationDate="";
		if(duration!=0){
			durationDate=MillisecondToDate(duration)
		}
		var retData='<div class="news_div">'+
        	'<i class="choice_icon   fl"></i>'+
            '<a href="javascript:;" onclick="mycomplete(\''+data._id+'\')" >'+
               	   ''+getThumbnailurl(data)+''+
                   ''+getIcon(data)+''+
                   ' <p>'+durationDate+'</p>'+
                    '<div class="pos_div">'+
                    	''+sendStatus(data)+''+
                   ' </div>'+
                '</div>'+
            '</a>'+
            '<div class="news_right fr">'+
            	'<h5>'+
            		'<a href="javascript:;" onclick="downLoadAll(\''+data._id+'\')" target="_blank" class="list_icon list_icon1"><p>下载</p></a>'+
                    '<a href="javascript:;" onclick="mycomplete(\''+data._id+'\')" title="'+(data.title==undefined?'':data.title)+'">'+cutTitleString((data.title==undefined?'':data.title),40,"...")+'</a>'+
                   ' '+getIcon(data)+''+
                   ''+sendOrWait(data).sendRight+' ' +
                   ' <a href="javascript:;" onclick="mycomplete(\''+data._id+'\')" class="list_icon list_icon5">'+
                    	'<p>预览</p>'+
                    '</a>'+
                    '<div class="clear"></div>'+
                '</h5>'+
                '<p><b>供片台：<span>'+(data.template.tvStationName==undefined?'':data.template.tvStationName)+'</span></b>'+
            	'<b title="'+(data.template.reporter==undefined?'':data.template.reporter)+'">记者：<span>'+cutStr((data.template.reporter==undefined?'':data.template.reporter),5,"...")+'</span></b>'+
            				 '<b title="'+(data.template.keyWords==undefined?'':data.template.keyWords)+'">关键词：<span>'+cutStr((data.template.keyWords==undefined?'':data.template.keyWords),9,"...")+'</span></b>'+
            						 '<b>创建人：<span>'+(data.cusename==undefined?'':data.cusename)+'</span></b></p>'+
                '<p>视音频：<span>'+getAVPinfo(data).avCount+'</span>&nbsp;&nbsp;|&nbsp;&nbsp;图片：<span>'+getAVPinfo(data).pCount+'</span></p>'+
                '<p class=\"jianjie_p\" >简介：<span>'+jianjie((data.template.content==undefined?'':data.template.content),46,76)+'</span></p>'+
               ' <p class="p_bot">'+dealCompleteStatus(data).statusDiv+'<b>更新时间：<span>'+(data.uutime==undefined?'':data.uutime)+'</span></b></p>'+
            '</div>'+
            '<div class="clear"></div>'+
        '</div>'
			return retData;
	}
function mycomplete(_id){
	window.open(ctx+pathPage+"news/toNewsViewIndex/?_id="+_id+"&type=complete");
}
//当前用户角色判断是否可以推送
function sendOrWait(data){
	var sendLeft="";
	var sendRight="";
	
	$.each(data.shareuser,function(i,item){
		if(item.userId==userId){
			sendRight='<a href="javascript:;" onclick="send(\''+data._id+'\',\''+item.inewstatus+'\',\''+item.netstatus+'\')" class="list_icon list_icon7"> 	<p>推送</p> </a>';
			return false;
		}else{
			sendRight='';
		}
		
	})
	
	return  {"sendLeft":sendLeft,"sendRight":sendRight};
	//	return  {"sendLeft":"待<br>推送","sendRight":'<a href="javascript:;" onclick="send(\''+data._id+'\')" class="list_icon list_icon7"> 	<p>推送</p> </a>'};
}
/*
function send(_id,inewstatus,netstatus){
	layer.confirm(
			' <div class="tsp_cont">'+
            '<p class="title">选择推送到：<p>'+
           ' <div class="ts_popup">  '+              	 
                '<div class="ts_inner fl ">'+
                    '<div class="inews '+(inewstatus==undefined?'':(inewstatus==0?'active success':''))+'"  ></div>'+
                    ''+((inewstatus==undefined||inewstatus=='undefined')?'':(inewstatus==0?'<p>推送成功</p>':'<p class="error">推送失败</p>'))+''+
                '</div>'+
                '<div class="ts_inner fr">'+
                    '<div class="paipai '+(netstatus==undefined?'':(netstatus==0?'active success':''))+'"></div>'+
                    ''+((inewstatus==undefined||inewstatus=='undefined')?'':(inewstatus==0?'<p>推送成功</p>':'<p class="error">推送失败</p>'))+''+
                '</div>'+
                '<div class="clear"></div>'+
            '</div>'+
          '</div>', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		
		if($(".inews.success").length==1||$(".inews").hasClass("success")){
			if($(".paipai.success").length==1){
				return ;
			}else{
				if($(".paipai.active").length==1||$(".paipai").hasClass("active")){
					toSendNet(outIndex,_id);
				}else {
					return ;
				}
				
			}
		}else {
			if($(".inews.active").length==1&&$(".paipai.success").length!=1&&$(".paipai.active").length==1){
				toSendNews(outIndex,_id);
				toSendNet(outIndex,_id);
			}else if($(".inews.active").length==1&&$(".paipai.success").length==1){
				toSendNews(outIndex,_id);
			}else{
				return;
			}
			
		}
		
		
	}, function(){
	});
	//window.open(ctx+pathPage+"news/toNewsSendIndex/?_id="+_id);
	//推送弹窗
	$(".ts_popup div").not(".success").click(function(){
		$(this).toggleClass("active")
	})
}
*/
function send(_id){
	layer.confirm('是否确认推送？', {
		btn: ['确认','取消'] 
	}, function(outIndex){
		toSendNews(outIndex,_id);
	}, function(){
	});
}

function addActiveOrSuccess(inewstatus){
	if(inewstatus==undefined){
		
	}else{
		if(inewstatus==0){
			alert(11)
		return 'p="active"'
			//$(divClass).addClass("active").addClass("success")
		}
	}
}

/**
 * 已完成中状态返回个人状态的返回
 * @param data
 * @returns
 */
function dealCompleteStatus(data){
	var statusDiv;
	var status;
	if(data.shareuser!=undefined&&data.shareuser.length>0){
		$.each(data.shareuser,function(i,item){
			if(userId==item.userId){
				statusDiv =dealStatus(item)
				status =item.status
				return ;
			}
		})
		
	}
	if(status!=undefined){
		return {"statusDiv":statusDiv,"status":status};
	}
	if(data.checkuser!=undefined&&data.checkuser.length>0){
		$.each(data.checkuser,function(i,item){
			if(userId==item.userId){
				statusDiv =dealStatus(item)
				status =item.status
				return ;
			}
		})
		
	}
	 return {"statusDiv":statusDiv,"status":status};;
}
//下载全部
function downLoadAll(_id){
	this.window.location = ctx + pathValue + "download/download4zip/?id="+_id;
}

function sendStatus(data){
	if(dealCompleteStatus(data).status==8){
		return '<b class="green_bg">已<br>推送</b>';
	}else{
		return '';
	}
}
//提交网台
function toSendNet(outIndex,_id){
	layer.close(outIndex);
	var index = layer.load();
	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"news/sendNewsToNetStation/", // 服务器的地址
        'headers': {'Content-Type': 'application/json'},
        'dataType':'json', // 返回数据类型
        'type':'POST', // 请求类型
        'data':strJson,
        'success':function(data){
        	$("#sendStatus").html('');
        	layer.close(index);
        	if(data.code==0){
        		toastr.success("推送拍拍成功");
        		uCont('news/toNewsCompleteList/');
    		 }else{
    			 toastr.error("推送拍拍失败");
    			 uCont('news/toNewsCompleteList/');
    		 }
        },
		error : function(){
			layer.close(index);
			 toastr.error("推送拍拍失败");
		}
	});
}
//提交INEWS
function toSendNews(outIndex,_id){
	layer.close(outIndex);
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
        		uCont('news/toNewsCompleteList/');
    		 }else{
    			 toastr.error("推送失败");
    			 uCont('news/toNewsCompleteList/');
    		 }
        },
		error : function(){
			layer.close(index);
			 toastr.error("推送失败");
		}
	});
}