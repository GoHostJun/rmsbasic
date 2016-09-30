$(function(){
	$("#ascrail2000").remove();
	//初始化
	init();
	//加载部门
	loadDep();
	//初始化加载所有user
	loadUserByTypt("");
	//回车搜索
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 $("#search").trigger("click");
        }
    }; 
	//点击左侧通联人
	$(".tonglian_man").click(function(){
		loadUserByTypt("");
		if(!$(this).hasClass("active")){
			return ;
		}
//		$(".make_sel i:eq(1)").addClass("active");
//		$(".make_sel i:eq(0)").removeClass("active");
		$(this).toggleClass("active");
		$(".auditing_man").toggleClass("active");
	})
	//点击左侧审核人
	$(".auditing_man").click(function(){
		loadUserByTypt("审核人");
		if(!$(this).hasClass("active")){
			return ;
		}
		$(".make_sel i:eq(0)").addClass("active");
		$(".make_sel i:eq(1)").removeClass("active");
		$(this).toggleClass("active");
		$(".tonglian_man").toggleClass("active");
	})
	//点击左侧添加审核人是
	$(".ture_btn").click(function(){
		loadUserByTypt("审核人");
		if($(this).hasClass("active")){
			return ;
		}
		$(this).addClass("active");
		$(".no_btn").removeClass("active");
		$(".auditing_man").removeClass("active");
		$(".tonglian_man").addClass("active");

	})
	//点击左侧添加审核人否
	$(".no_btn").click(function(){
		loadUserByTypt("");
		if($(this).hasClass("active")){
			return ;
		}
		$(this).addClass("active");
		$(".ture_btn").removeClass("active");
		$(".auditing_man").addClass("active");
		$(".tonglian_man").removeClass("active");
		$(".auditing_man li:gt(0)").remove();
	})

	//点击提交
$(".ctrl_s_btn").click(function(){
	var joinjson=new Array();
	var dealjson=new Array();
	$.each( $(".tonglian_man li"),function(i,item){
		if(i==0) return;
		joinjson.push({"userId":$(this).find("[name=userId]").val(),"realname":$(this).find("[name=realname]").val(),"position":$(this).find("[name=position]").val()})
	})
	$.each( $(".auditing_man li"),function(i,item){
		if(i==0) return;
		dealjson.push({"userId":$(this).find("[name=userId]").val(),"realname":$(this).find("[name=realname]").val(),"position":$(this).find("[name=position]").val()})
	})
//	if(joinjson.length==0){
//		toastr.warning("至少选择一个通联人");
//		return;
//	}
	
	if($(".ture_btn ").hasClass("active")){
		if($(".auditing_man li").length==1){
			toastr.warning("请添加审核人");
			return ;
		}
	}
	layer.confirm('确定创建通联？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		createNews(outIndex,joinjson,dealjson);
	}, function(){
		
	})
	
	
})
	//点击搜索查询匹配用户
	$("#search").click(function(){
		 var index = layer.load();
		 var jsonStr={"keyWord":$("#searchInput").val(),"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
		 jsonStr= JSON.stringify(jsonStr);
		 $.ajax({
		        url:ctx+pathValue+"org/getUserByName/", //服务器的地址
		        dataType:'json', //返回数据类型
		        headers: {'Content-Type': 'application/json'},
		        type:'POST', //请求类型
		        data:jsonStr,
		        success:function(data){
			        layer.close(index);
			       	if(data.code==0){
			       		loadUser(data.data.results);
					 }else{
						 toastr.error("查询失败");
					 }
			    },
				error : function(){
					layer.close(index);
					 toastr.error("查询失败");
				}
			});
	})
	$(".reset_btn").click(function(){
		$(".tonglian_man li:gt(0)").remove();
		$(".auditing_man li:gt(0)").remove();
	})

})
var customTypeJson={"0":"国内新闻","1":"国际新闻"};
function init(){
	 var index = layer.load();
	 var jsonStr={"keyWord":$("#searchInput").val(),"id":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 
	 $.ajax({
	        url:ctx+pathValue+"presentation/queryById/", //服务器的地址
	        dataType:'json', //返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', //请求类型
	        data:jsonStr,
	        success:function(data){
		        layer.close(index);
		       	if(data.code==0){
		       		var data=data.data.result;
		       		var duration=getAVPinfo(data).duration;
		    		var durationDate="";
		    		if(duration!=0){
		    			durationDate=MillisecondToDate(duration)
		    		}
		       		$('#docDiv').append(
		       				'<div class="inner">'+
		       		        '<a href="'+ctx+pathPage+'material/toViewDocs/?docId='+data._id+'" target="_blank">'+
		       		        ''+getImg(data,durationDate)+''+
		       		        '</a>'+
		       		       ' <div class="title_right">'+
		       		        	'<h4 title="'+(data.title==undefined?'':data.title)+'">'+cutTitleString((data.title==undefined?'':data.title),40,"...")+'</h4>'+
		       		            '<p style="margin:10px 0;">供片台：<span>'+(data.template.tvStationName==undefined?'':data.template.tvStationName)+'</span> 记者：<span>'+(data.template.reporter==undefined?'':data.template.reporter)+'</span>  关键词：<span>'+(data.template.keyWords==undefined?'':data.template.keyWords)+'</span> 分类：'+
		       		            '<span>'+(data.template.customType==undefined?'':data.template.customType)+'</span></p>'+
		       		            '<p style="margin:10px 0;">视音频：<span>'+getAVPinfo(data).avCount+'</span> 图片：<span>'+getAVPinfo(data).pCount+'</span> </p>'+
		       		            '<p class=\"jianjie_p\" style="margin:20px 0;" title="'+(data.template.content==undefined?'':data.template.content)+'">简介：<span>'+jianjie((data.template.content==undefined?'':data.template.content),46,76)+'</span></p>'+
		       		            '<p class="time"><b>更新时间：<span>'+(data.uutime==undefined?'':data.uutime)+'</span></b></p>'+
		       		       ' </div>'+
		       		        '<div class="clear"></div>'+
		       		    '</div>');
		       		//素材下载、通联创建圆圈居中的样式
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
/**
 * 根据类别key 取得json对应value
 * @param json
 * @param key
 * @returns
 */
function checkByJson(json,key){
	var type;
	$.each(json,function(i,item){
		if(i==key){
			type= item;
		}
	})
	return type;
}
function getImg(data,durationDate){
	var imgVar="";
	 if(data.thumbnailurl!=undefined){
		 imgVar= '<div class="title_img noimg_div ">'+
		 ''+getThumbnailurl(data)+''+
		 (durationDate==""?"":' <p>'+durationDate+'</p>')+
		 ' </div>'
	 }
	 return imgVar;
}

function getThumbnailurl(data){
	if(data.thumbnailurl=='undefined'||data.thumbnailurl==undefined||data.thumbnailurl==''){
		return  '<img src="'+ctx+'/common/images/wengao_img.png">';
	}else{
		return  '<img src="'+data.thumbnailurl+'"> <i></i>';
	}
	
}
function createNews(outIndex,joinjson,dealjson){
	layer.close(outIndex);
	var shareNews=1;
	if($("#shareActive").hasClass("active")){
		shareNews=0;
	}
	var strJson={"shareNews":shareNews,"joinjson":joinjson,"dealjson":dealjson,catalogueId:_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	strJson= JSON.stringify(strJson); 
	var index = layer.load();
	$.ajax({
	        'url':ctx+pathValue+"news/createNews/", //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':strJson,
	        'success':function(data){
	        	layer.close(index);
		       	if(data.code==0){
		       		toastr.success("创建通联成功");
			        uCont('news/toNewsMyCreateList/');
			        navChangeClass(1,4);
				 }else{
					 toastr.error("创建通联失败");
				 }
	        } ,
			'error' : function(){
				layer.close(index);
				toastr.error("创建通联失败");
			}
	});
}

