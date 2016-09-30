$(function(){
	navJXChangeClass(4,6);
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
    $("#checkAll").click(function(){
		if($(this).is(':checked')&&($(this).attr("read")==1)){
			$("#inner_list :checkbox:enabled").prop("checked",true);
		}else{
			$("#inner_list :checkbox:enabled").attr("checked",false);
			
		}
	})
	$("#readSend").click(function(){
		readNews();
	})
})

/**
 * 已读通联
 * 
 * @param id
 */

function readNews(){
	var ids = [];
	if( $("#inner_list :checkbox:checked").length<1){
		toastr.error("至少选择一条记录");
	}else{
		layer.confirm('确认已读通联？', {
			btn: ['确认','取消'] 
		}, function(outIndex){
			readNewsByIds(outIndex);
		}, function(){
		});
	}
}
function readNewsByIds(outIndex){
	
	var index = layer.load();
	var ids = [];
	 $("[name = news]:checkbox").each(function () {
         if ($(this).is(":checked")) {
        	 ids.push($(this).attr("value"));
         }
     });
	 var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("newsId", ids);
	 onairMap.put("shareUser", userId);
	 layer.close(outIndex);
	$.ajax({
		url : ctx + pathValue+"message/updateBtachNewsStatus/",
		headers:{"Content-Type":"application/json"},
		data :onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			layer.close(index);
			var code = data.code;
			if(code == 0 ){
				toastr.success("操作成功");
				getData(1,10);
			}else{
				toastr.error("操作失败");
			}
			
		},
		error : function() {
			layer.close(index);
			toastr.error("操作失败");
		}
	});
}

//当前用户Id
var currentId="";

function getData(pagePara,pageNumPara){
	var page;
	var pageNum;
	page=(pagePara==undefined?$("#page").getOnairPageParameter().pageNow:pagePara)
	pageNum=(pageNumPara==undefined?$("#page").getOnairPageParameter().pageSize:pageNumPara)
	var strJson={"keyWord":$("#search").val(),"currentPage":page,"pageNum":pageNum,"sendStatus":7,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
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
function setCheck(data,read){
	if(read==1){
		return '<input class="choice_icon" name="news"  type="checkbox" value="'+data._id+'" read="'+read+'">';
	}else{
		return "<input class='choice_icon' type='checkbox' disabled >";
	}
}
function putList(data){
		var duration=getAVPinfo(data).duration;
		var durationDate="";
		if(duration!=0){
			durationDate=MillisecondToDate(duration)
		}
		var read=checkCheckRead(data);
		var retData='<div class="news_div">'+
			''+setCheck(data,read)+''+
        	'<i class="choice_icon fl"></i>'+
            '<a href="javascript:;" onclick="myWaitingSend(\''+data._id+'\')" >'+
               		''+getThumbnailurl(data)+''+
                   ''+getIcon(data)+''+
                   ' <p>'+durationDate+'</p>'+
                   '<div class="pos_div">'+
                	'<b class="org_bg">'+sendOrWait(data).sendLeft+'</b>'+
                	' </div>'+
                	 ''+newOrNotShare(data)+''+
                '</div>'+
            '</a>'+
            '<div class="news_right fr">'+
            	'<h5>'+
            		'<a href="javascript:;" onclick="downLoadAll(\''+data._id+'\')" target="_blank" class="list_icon list_icon1"><p>下载</p></a>'+
                    '<a href="javascript:;" onclick="myWaitingSend(\''+data._id+'\')" title="'+(data.title==undefined?'':data.title)+'">'+cutTitleString((data.title==undefined?'':data.title),40,"...")+'</a>'+
                   ' '+getIcon(data)+''+
                   ''+sendOrWait(data).sendRight+' ' +
                   ' <a href="javascript:;" onclick="myWaitingSend(\''+data._id+'\')" class="list_icon list_icon5">'+
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
               ' <p class="p_bot"><b>更新时间：<span>'+(data.uutime==undefined?'':data.uutime)+'</span></b></p>'+
            '</div>'+
            '<div class="clear"></div>'+
        '</div>'
			return retData;
	}
function myWaitingSend(_id){
	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"shareUser":userId}
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"message/updateNewsStatus/", //服务器的地址
        'headers': {'Content-Type': 'application/json'},
        'dataType':'json', //返回数据类型
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
//        	layer.close(index);
        	if(data.code==0){
        		
    		 }else{
    			 
    		 }
        },
		error : function(){
//			layer.close(index);
		}
	});
	window.open(ctx+pathPage+"news/toNewsViewIndex/?_id="+_id+"&type=join");
}
//根据当前用户判断是待推送还是已推送
function sendOrWait(data){
	var sendLeft="";
	var sendRight="";
	$.each(data.shareuser,function(i,item){
		if(item.userId==currentId){
			if(item.status==7){
				sendLeft= "待<br>推送";
				sendRight='<a href="javascript:;" onclick="send(\''+data._id+'\')" class="list_icon list_icon7"> 	<p>推送</p> </a>';
			}else if(item.status==8){
				sendLeft= "已推送";
				sendRight='';
			}
		}
	})
	//return  {"sendLeft":sendLeft,"sendRight":sendRight};
	return  {"sendLeft":"待<br>推送","sendRight":'<a href="javascript:;" onclick="send(\''+data._id+'\')" class="list_icon list_icon7"> 	<p>推送</p> </a>'};
}
//下载全部
function downLoadAll(_id){
	this.window.location = ctx + pathValue + "download/download4zip/?id="+_id;
}
/*
function send(_id){
	layer.confirm(
			' <div class="tsp_cont">'+
            '<p class="title">选择推送到：<p>'+
           ' <div class="ts_popup">  '+              	 
                '<div class="ts_inner fl ">'+
                    '<div class="inews"></div>'+
//                    '<p>未完成</p>'+
                '</div>'+
                '<div class="ts_inner fr">'+
                    '<div class="paipai"></div>'+
//                    '<p>未完成</p>'+
                '</div>'+
                '<div class="clear"></div>'+
            '</div>'+
          '</div>', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		if($(".inews").hasClass("active")&&!$(".paipai").hasClass("active")){
			toSendNews(outIndex,_id);
		}else if(!$(".inews").hasClass("active")&&$(".paipai").hasClass("active")){
			toSendNet(outIndex,_id);
		}else if($(".inews").hasClass("active")&&$(".paipai").hasClass("active")){
			toSendNews(outIndex,_id);
			toSendNet(outIndex,_id);
		}else {
			toastr.warning("请至少选择一种推送方式");
		}
		//toSendNews(outIndex,_id);
	}, function(){
	});
	//window.open(ctx+pathPage+"news/toNewsSendIndex/?_id="+_id);
	//推送弹窗
	$(".ts_popup div").click(function(){
		$(this).toggleClass("active")
	})
}*/


/**
 * 加载推送路径
 */

function send(_id){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "shareresource/sendShareNews/?_id="+_id+"&pushType=NEWS",
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

/*
function send(_id){
	layer.confirm('是否确认推送？', {
		btn: ['确认','取消'] 
	}, function(outIndex){
		toSendNews(outIndex,_id);
	}, function(){
	});
}
*/
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
        		uCont('news/toNewsMyWaitingSendList/');
    		 }else{
    			 toastr.error("推送失败");
    			 uCont('news/toNewsMyWaitingSendList/');
    		 }
        },
		error : function(){
			layer.close(index);
			 toastr.error("推送失败");
		}
	});
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
        		uCont('news/toNewsMyWaitingSendList/');
    		 }else{
    			 toastr.error("推送拍拍失败");
    			 uCont('news/toNewsMyWaitingSendList/');
    		 }
        },
		error : function(){
			layer.close(index);
			 toastr.error("推送拍拍失败");
		}
	});
}