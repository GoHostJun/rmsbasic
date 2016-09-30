$(function(){
	navJXChangeClass(4,5)
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
	var strJson={"keyWord":$("#search").val(),"currentPage":page,"pageNum":pageNum,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
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
        	'<i class="choice_icon fl"></i>'+
            '<a href="javascript:;" onclick="mycreate(\''+data._id+'\')" >'+
                   ''+getThumbnailurl(data)+''+
                    ''+getIcon(data)+''+
                   ' <p>'+durationDate+'</p>'+
                   '<div class="pos_div">'+
           				''+createSendStatus(data)+''+
           			' </div>'+
//                    '<div class="pos_new"></div>'+
                '</div>'+
            '</a>'+
            '<div class="news_right fr">'+
            	'<h5>'+
                    '<a href="javascript:;" onclick="mycreate(\''+data._id+'\')" title="'+(data.title==undefined?'':data.title)+'">'+cutTitleString((data.title==undefined?'':data.title),40,"...")+'</a>'+
                   ' '+getIcon(data)+''+
                   ' <a href="javascript:;" onclick="mycreate(\''+data._id+'\')" class="list_icon list_icon5">'+
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
               ' <p class="p_bot">'+dealStatus(data)+'<b>更新时间：<span>'+(data.uutime==undefined?'':data.uutime)+'</span></b></p>'+
            '</div>'+
            '<div class="clear"></div>'+
        '</div>'
			return retData;
	}

function mycreate(_id){
	window.open(ctx+pathPage+"news/toNewsViewIndex/?_id="+_id+"&type=create");
}
function createSendStatus(data){
	if(data.status==8){
		return '<b class="green_bg">已<br>推送</b>';
	}else{
		return '';
	}
}
