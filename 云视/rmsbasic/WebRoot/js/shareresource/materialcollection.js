var li_w=0;
var num=0;;
var len=0;


$(function(){
	//加载轮播
	getMaterialCollectionCarousel();
	$("#restmedia").click(function(){
		$("#searchmedia").val('');
		$("#begindate").val('');
		$("#enddate").val('');
	})
	$("#materialrigth").html('');
	$("#mylatelyul").html('');
	//获取素材集
	getMaterialsetleft(8,1);
	//最新动态
	getownDocsLatelystatus(6,1);
	$("#mylately").addscroll({callback:getownDocsLatelystatus,drawnodata:"<p>无更多数据</p>",ele:"ul"})
	$("#materialcollection").addscroll({callback:getMaterialsetleft,pagesize:8,drawnodata:"<p>无更多数据</p>",ele:"ul"})

})


function getMaterialsetleft(pagesize,currentnum){
	if(!pagesize||!currentnum){
		pagesize=$("#materialcollection").getPageParameter.pagesize;
		currentnum=$("#materialcollection").getPageParameter.currentnum;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", currentnum);
	onairMap.put("pageNum", pagesize);
	var index = layer.load();
	$.ajax({
		url : ctx + pathValue+"materialSet/query/",
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
				toastr.error("查询素材集失败");
				return;
			}else{
				var totalRecord = data.totalRecord;
				var currentPage = data.currentPage;
				$("#materialcollection").resetPageParameter(totalRecord,currentPage);
				var results = data.results;
				if(results.length>0){
					$(".sucaiji_div").show();
					$(results).each(function(i,item){
						$("#materialrigth").append('<li><a onclick="getMaterialSetDetail(\''+item._id+'\')"><span>'+process(item.src)+'</span>'+process(item.title)+'</a></li>');
					})
				}
			}
			
		},
		error : function() {
			layer.close(index);
			toastr.error("查询素材集失败");
		}
	});
}

function getownDocsLatelystatus(pagesize,currentnum){
	if(!pagesize||!currentnum){
		pagesize=$("#mylately").getPageParameter.pagesize;
		currentnum=$("#mylately").getPageParameter.currentnum;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", currentnum);
	onairMap.put("pageNum", pagesize);
	var index = layer.load();
	$.ajax({
		url : ctx + pathValue+"message/findFieldNewsMessages/",
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
				toastr.error("查询最新动态失败");
				return;
			}else{
				var totalRecord = data.totalRecord;
				var currentPage = data.currentPage;
				$("#mylately").resetPageParameter(totalRecord,currentPage);
				var results = data.results;
				if(results.length>0){
					$(".dongtai_div").show();
					$(results).each(function(i,item){
						$("#mylatelyul").append('<li><a >'+process(item.cusename)+'分享的<span>'+cutTitleString(process(item.msgtitle),15,"...")+'</span>有了新动态</a></li>');
						//$("#mylatelyul").append('<li><a onclick="getShareDocsDetail(\''+item.newsid+'\')">'+process(item.cusename)+'分享的<span>'+cutTitleString(process(item.msgtitle),15,"...")+'</span>有了新动态</a></li>');
					})
				}
			}
			
		},
		error : function() {
			layer.close(index);
			toastr.error("查询最新动态失败");
		}
	});
}

/**
 * 加载轮播
 */
function getMaterialCollectionCarousel(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 3);
	$.ajax({
		url : ctx + pathValue+"materialSet/query/",
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
				toastr.error("查询素材集失败");
				return;
			}
			//清空轮播图和轮播下面的圈
			$("#materialsetlist").html('');
			$("#materialsetnav").html('');
			if(code == 0 && data.results.length > 0){
				loadMaterialCollectionHtml(data);
				realize();
				//轮播
				 li_w=$(".material_set_cont").width();
				 len=$(".material_set_div").length;
				$(".material_set_div").css({"width":$(".material_set_cont").width()});

				$(".material_set_list").css({"width":li_w*len});

				$(".picnav li").click(function(){
					num=$(this).index();
					navChange(num);	
				});

				$(".prev").unbind("click");
				$(".next").unbind("click");
				$(".prev").click(function(){
					num=num>0?num-1:len-1;
					navChange(num);	
				});
				
				$(".next").click(function(){
					rightClick();
				});
				var pic_nav_w=(10+12)*len;
				$(".picnav").css({"margin-left":-pic_nav_w/2});
				$(".picnav li:first").addClass("active");
				$(".material_set_cont").unbind("mouseenter").unbind("mouseleave");
				$(".material_set_cont").hover(function(){
					clearInterval(timer)},autoChange);
				autoChange();
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询素材集失败");
		}
	});


}

function rightClick(){
	num=num<len-1?num+1:0;
	navChange(num);	
}
function autoChange(){
	timer=setInterval(rightClick(),5000);	
}
function navChange(i){
	$(".material_set_list").stop(true).animate({"margin-left":-li_w*i},500);
	$(".picnav li").removeClass("active").eq(i).addClass("active");	
}
/**
 * 加载轮播图
 * @param obj
 */
function loadMaterialCollectionHtml(obj){
	var results = obj.results;
	$(results).each(function(i,item){
		var materialSetHtml=[];
		materialSetHtml.push('<div class="material_set_div clearfloat">');
		materialSetHtml.push('<div class="set_img_div">');
		materialSetHtml.push('<img src="'+process(item.thumbnailurl)+getMaterialResolution()+'" onclick="getMaterialSetDetail(\''+item._id+'\')">');	
		materialSetHtml.push('</div>');
		materialSetHtml.push('<div class="about_set_div">');
		materialSetHtml.push('<h4 onclick="getMaterialSetDetail(\''+item._id+'\')"><span>'+process(item.src)+'</span>'+process(item.title)+'</h4>');	
		materialSetHtml.push('<p>描述：<span>'+process(item.remark)+'</span></p>');	
		materialSetHtml.push('<div class="clearfix mt-div">');	
		materialSetHtml.push('<p class="peo_num fl"><i></i><span>'+(item.participants!=undefined?(Array.isArray(item.participants)?item.participants.length:0):0)+'</span></p>');		
		materialSetHtml.push(' <p class="set_time_p fl"><i class="time-icon"></i><span>'+item.ctime+'</span></p>');	   
		materialSetHtml.push('</div>');	
		materialSetHtml.push('</div>');
		materialSetHtml.push('<div class="set_time_div">');				
		materialSetHtml.push('<a href="javascript:;" onclick="getMaterialSetDetail(\''+item._id+'\')">查看</a>');	
		materialSetHtml.push('</div>');
		materialSetHtml.push('</div>');
		$("#materialsetlist").append(materialSetHtml.join(''));
		$("#materialsetnav").append("<li></li>");
	})
	
}
/**
 * 轮播自适应
 */
function realize(){
	$(".material_set_div").css({"width":$(".material_set_cont").width()});
	var li_w=$(".material_set_cont").width();
	var len=$(".material_set_div").length;
	$(".material_set_list").css({"width":li_w*len});
}

function getMaterialSetDetail(id){
	
	//queryById
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id",id);
	$.ajax({
		url : ctx + pathValue+"materialSet/queryById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			if(code==0){
				loadMaterialSetHtml(response.data);
				
			}
		},
		error:function(){
			layer.close(index);
			toastr.error("查询素材集失败！");
		}
	})
}

function loadMaterialSetHtml(obj){
	$("#medsrc").val(obj.src)
	$("#materialsetremark").html('')
	$("#materialsettitle").html(process(obj.title));
	$("#materialsetcusename").html(process(obj.cusename));
	$("#materialsetctime").html(process(obj.ctime));
	$("#materialsetremark").prepend(cutTitleString(process(obj.remark),80,"...")+"<span class='fr live-u' >"+process(obj.src)+"</span>");
	$("#materialsetsrc").html(process(obj.src));
	
	/**
	 * 分享人
	 */
	$("#materialsetshareuser").html('');
	if(obj.participants&&obj.participants.length>0){
		$.each(obj.participants,function(i,item){
			$("#materialsetshareuser").append("<li>"+(item.userName.substring(item.userName.length-2,item.userName.length))+"</li>");
		})
	}
	$("#materialsetul").html('');
	$("#materialsetul").append('<li class="del-li  '+(obj.cuserid==userId?"":"active")+'"  onclick="deletematerialset(\''+obj._id+'\',\''+(obj.cuserid==userId?"0":"1")+'\')" ></li>'+
			    		'<li class="qr-share" onclick="tosharematerial(this,\''+obj._id+'\',event)" >'+
			    			'<div class="qr-code"  >'+
			    				'<div class="">'+
			    				'	<i></i>'+
			    				'<div name="sharediv"></div>'+
				    			'	<p>微信扫一扫，分享给同事</p>'+
			    				'</div>		'+	    				
	    					'</div>'+
			    	'	</li>')
	/**
	 * 加载素材集下的所有素材
	 */
	$("#pageDiv3").onairPage({"callback":getMaterialSetInnerMaterial,"pageSize":12,"src":process(obj.src)});
	getMaterialSetInnerMaterial(1,12);
	 $(".material-popup").show();
	 
//		var bodyH = $(".news-style-div").height()+480;
//	    var popupH = $(".material-popup  .popup_inner").height()+300;
//	    alert(bodyH+"   "+popupH)
//	    if(popupH<bodyH){
//	    	$(".material-popup").height(bodyH+"px");
//	    }
//	    else{
//	    	$(".material-popup").height(popupH+"px");
//	    }
	   // 
	
}
/**
 * 
 * @param id
 * @param isdelete 0能删 1不能删除
 */
function deletematerialset(id,isdelete){
	if(isdelete==1){
		return ;
	}
	layer.confirm('确认删除素材集？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		layer.close(outIndex);
		var ids=[];ids.push(id);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("ids", ids);
		$.ajax({
			url : ctx + pathValue+"materialSet/delete/",
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
					$(".material-popup").hide();
					 li_w=0;
					 num=0;
					 len=0;
					getMaterialCollectionCarousel();
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
 * 
 * @param This
 * @param id
 * @param e
 * @returns
 */
function tosharematerial(This,id,e){
	$("[name=sharediv]").html('');
	$("[name=sharediv]").qrcode( {width: 137,  height:137,text:"http://"+window.location.host+"/api/share/getMaterialSetInfo/?_id="+id+"&userId="+userId+"&type=media"});
	$(".qr-code").show();
	e.stopPropagation();
}
/**
 * 
 */
function getMaterialSetInnerMaterial(pageNow,pageSize){
//	var mediasrc=
	//alert(src+","+this.src)
	var src2=$("#medsrc").val()
	//var src2=this.src||src;
	var index = layer.load();
	if(!pageNow && !pageSize){
		var pagePar = $("#pageDiv3").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}

	var keyWord = $("#searchmedia").val();
	if(!keyWord){
		keyWord = "";
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("keyWord", keyWord);
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	onairMap.put("startTime", $("#begindate").val());
	onairMap.put("endTime", $("#enddate").val());
	onairMap.put("src", src2);
	$.ajax({
		url : ctx + pathValue+"materialSet/queryMediaList/",
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
				toastr.error("查询失败，请重试！");
				return;
			}
			if(code == 0 && data.results.length > 0){
				$("#inner3").hide();
	       		$("#outter3").show();
	       		loadMaterialSetInnerMaterial(data,"materialviewul");
			}else{
				$("#outter3").hide();
       			$("#inner3").show();
       			$("#inner3").html('');
       			$("#inner3").append("<div class='no_data'></div>");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询失败，请重试！");
		}
	});
}


/**
 * 渲染页面
 * 
 * @param obj
 */
function loadMaterialSetInnerMaterial(obj,divId){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	$(results).each(function(i,item){
		html.push('<li>');
		html.push('<div class="" onclick="getMaterialDetail(\''+item._id+'\','+item.mtype+')">');
	//	html.push((item.mtype==1?"<div class=\"list_img_div  list_audio_div fl\">":"<div class=\"list_img_div noimg_div fl\">"));
		if(item.mtype==0){
			html.push((item.vslt==undefined?"":"<img src='"+item.vslt+getMaterialResolution()+"'>"));
		}else if(item.mtype==2){
			html.push((item.vslt==undefined?"":"<img src='"+item.wanurl+getMaterialResolution()+"'>"));
		}else if(item.mtype==1){
			html.push("<img src='"+ctx+"/common/images/audio.png'>");
		}
		//html.append('<img src="images/img1.jpg"/>');	
		html.push('<p class="when-long">'+MillisecondToDate(processValue(item.duration))+'</p>');	
		html.push('<div class="img-mask" onclick="importmymaterial(\''+item._id+'\',event,\''+item.status+'\')">  ');	
		html.push('<p><i></i>导入我的素材库</p>');		
		html.push('</div>');	
		html.push('</div>');
		html.push('<h2 onclick="getMaterialDetail(\''+item._id+'\','+item.mtype+')">'+cutTitleString(processValue(item.name),10,"...")+'</h2>');
		html.push('<p><i class="time-icon"></i>'+processValue(item.uutime)+'</p>');
		html.push('</li>');
	});
	$("#"+divId).html(html.join(""));
	$(".material-cont-list > li > div").hover(function(){
		$(this).find(".img-mask").show();
	},function(){
		$(this).find(".img-mask").hide();
	})
	$("#pageDiv3").resetOnairPageParameter(totalRecord,currentPage);
	var bodyH = $(".news-style-div").height()+480;
    var popupH = $(".material-popup  .popup_inner").height()+300;
    if(popupH<bodyH){
    	$(".material-popup").height(bodyH+"px");
    }
    else{
    	$(".material-popup").height(popupH+"px");
    }
}

function getMaterialDetail(id,mtype){
	window.open(ctx+pathPage+"material/toMaterialPreview/?mId="+id+"&mtype="+mtype)
}
function importmymaterial(id,e,status){
	e.stopPropagation();
	if(status==2){
		toastr.error("处理中的素材无法导入！");
		return ;
	}else if(status==1){
		toastr.error("处理失败的素材无法导入！");
		return ;
	}
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id",id);
	$.ajax({
		url : ctx + pathValue+"materialSet/copyMaterialSet/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			if(code==0){
				toastr.success("导入素材成功！");
			}else{
				toastr.error("导入素材失败！");
			}
		},
		error:function(){
			layer.close(index);
			toastr.error("导入素材失败！");
		}
	})
}
