$(document).ready(function() {
	$("#ascrail2000").remove();
	$("#pageDiv").onairPage({"callback":getListData,"pageSize":6});
	/**
	 * 点击上传素材按钮，弹出上传插件
	 */
	//$('#uploadFile').attr('href', 'javascript:upload_open();');
	$('#uploadFile').click(function(){
		//upload_open();
		$(".upload_cont").css("visibility","visiable");
	})
	// setInterval(getListData,5000);
	/**
	 * 获取分页信息
	 */
	/**
	 * 将内容区域显示为：正在查询，请稍后......
	 */
	
	/**
	 * 提交post请求，查询数据
	 */
	
	
	/**
	 * 将内容区域：正在查询，请稍后......清除
	 */
	/**
	 * 迭代数据，显示数据
	 */
	
});
/**
 * 处理媒体类型
 * 
 *  
 * @returns {String}
 */
function getMaterialTypeCode(){
	var materialTypeName;
	$(".material_choice span").each(function(){
		 if($(this).attr("class") == "active"){
			 materialTypeName = $(this).text();
		  }
	});
	switch (materialTypeName) {
	case "全部":	
		return "all";
	case "视频":	
		return "0";
	case "音频":
	return "1";
	case "图片":
	return "2";
	default:
		return "all";
	}
}
/**
 * 查询数据
 * 
 * @param pageNow
 * @param pageSize
 */

function getListDataByType(materialTypeName,pageNow,pageSize){
	$("#materialTypeName").val(materialTypeName);
	getListData(pageNow,pageSize);
}
function getListData(pageNow,pageSize) {
	var materialTypeName=$("#materialTypeName").val();
	var materialTypeNames=materialTypeName.split(",");
	var materialTypeNameArray=[];
	for(var i=0;i<materialTypeNames.length;i++){
		materialTypeNameArray.push(""+materialTypeNames[i]+"");
	}
	var index = layer.load();
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	
	var materialTypeCode = (materialTypeNameArray==undefined?getMaterialTypeCode():materialTypeNameArray);
	var keyWord = $("#search").val();
	if(!keyWord){
		keyWord = "";
	}
	var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("mtype", materialTypeCode);
	onairMap.put("keyWord", keyWord);
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	//直加载可用的 素材
	onairMap.put("status", 0);
	$.ajax({
		url : ctx + pathValue+"media/query/",
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
				$("#inner").hide();
	       		$("#outter").show();
				loadHtml(data);
			}else{
				$("#outter").hide();
	   			$("#inner").show();
	   			$("#inner").html('');
	   			$("#inner").append("<div class='no_data'></div>");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询失败，请重试！");
		}
	});
}

/**
 * 所有信息如果不存在显示--
 * 
 * @param obj
 * @returns
 */
function processValue(obj){
	if(obj){
		return obj;
	}
	return "--";
}
/**
 * 处理文件大小
 * 
 * @param fileSize
 * @returns
 */
function formartSize(fileSize){
	if(fileSize){
		if(parseFloat(fileSize/1024/1024) > 1){
			fileSize = parseFloat(fileSize/1024/1024);
			return fileSize.toFixed(2) + " MB";
		}else{
			fileSize = parseFloat(fileSize/1024);
			return fileSize.toFixed(2) + " KB";
		}
	}
	return processValue(fileSize);
}
/**
 * 处理宽高
 * 
 * @param num
 * @returns
 */
function formartWidthHeight(num){
	if(num){
		return parseInt(num);
	}
	return processValue(num);
}
/**
 * 处理描述信息
 * 
 * @param describe
 * @returns
 */
function processDescribe(describe){
	return processValue(describe);
}
/**
 * 处理扩展信息的显示
 * 
 * @param othermsgObj
 * @param attributeName
 * @returns
 */
function processOthermsg(othermsgObj,attributeName){
	if(!othermsgObj){
		return processValue(othermsgObj);
	}
	if(!othermsgObj[attributeName]){
		return processValue(othermsgObj[attributeName]);
	}
	return othermsgObj[attributeName];
}
/**
 * 判断素材是否已被引用
 * 
 * @param cataidsArray
 * @returns {String}
 */
function processIsUsed(cataidsArray){
	if(!cataidsArray || cataidsArray.length == 0){
		return "";
	}else{
		return "<b class=\"green_bg\">已<br>引用</b>";
	}
	
}
/**
 * 状态显示处理
 * @param status
 * @returns {String}
 */
function processStatus(status){
	if(status == 0){
		return "<i id=\"mStatus\" class=\"keyong\">可用</i>";
	}else if(status == 1){
		return "<i id=\"mStatus\" class=\"shibai\">失败</i>";
	}else{
		return "<i id=\"mStatus\" class=\"chulizhong\">处理中</i>";
	}
}
/**
 * 处理媒体类型
 * @param mType
 * @returns {String}
 */
function processType(mType){
	if(mType == 0){
		return "<i><b class=\"video_icon\"></b></i>";
	}else if(mType == 1){
		return "<i><b class=\"audio_icon\"></b></i>";
	}else{
		return "<b class=\"img_icon\"></b>";
	}
}
/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	$("#listDiv").html('');
	$(results).each(function(i,item){
		var duration=(item.mtype==2)?"":MillisecondToDate(item.duration==undefined?"":item.duration);
		$("#listDiv").append('<div class="news_div">'+
        '<input type="checkbox" class="choice_icon" value="'+item._id+'" mtype="'+item.mtype+'">'+
        '<a href="'+ctx+pathPage+'material/toMaterialPreview/?mId='+item._id+'" target="_blank">'+
//            '<div class="list_img_div  noimg_div fl">'+
            ''+(item.mtype==1?"<div class=\"list_img_div  list_audio_div fl\">":"<div class=\"list_img_div fl\">")+''+
           ''+toVstl(item)+''+

                '<p>'+duration+'</p>'+
            '</div>'+
        '</a>'+
        '<div class="news_right fr">'+
            '<h5>'+
                '<a href="'+ctx+pathPage+'material/toMaterialPreview/?mId='+item._id+'" target="_blank" title="'+processValue(item.name)+'">'+cutStr(processValue(item.name),11,"...")+'</a>'+
                ''+processType(item.mtype)+''+
                '<div class="clear"></div>'+
            '</h5>'+
            '<p>'+
            ''+getInfoByMtype(item)+''+
            '</p>'+
            '<p>来源：<span>'+processOthermsg(item.othermsg,'happenPlace')+'</span></p>'+
            '<p class="jianjie_p" title="'+processDescribe(item.describe)+'">简介：<span>'+jianjie(processDescribe(item.describe),39,69)+'</span></p>'+
            '<p class="p_bot"><b>更新时间：<span>'+processValue(item.uutime)+'</span></b></p>'+
        '</div>'+
        '<div class="clear"></div>'+
    '</div>')
		
	});
	//素材下载、通联创建圆圈居中的样式
	var icon_w=$(".list_img_div i").width();
	var icon_h=$(".list_img_div i").height();
	$(".list_img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
	
}

function getInfoByMtype(item){
	var result="";
	result+= '<b>大小：<span>'+formartSize(item.size)+'</span></b>';
	if(item.mtype!=2){
		result+="<b>码率：<span>"+processValue(item.rate)+"Kbps</span></b> ";
	}
	if(item.mtype!=1){
		result+="<b>分辨率：<span>"+formartWidthHeight(item.width)+" * "+formartWidthHeight(item.height)+"</span></b> ";
	}
	if(item.mtype!=0&&item.mtype!=2){
		result+=" <b>编码：<span>h.264</span></b>";
	}
	result+=" <b style='margin-right:0'>封装：<span>"+processValue(item.fmt)+"</span></b>";
	return result;
}
//添加素材
function addMaterial(){
	var index = layer.load();
	var videoIds=new Array();
	var audioIds=new Array();
	var picIds=new Array();
	$("#listDiv").find("[type='checkbox']:checked").each(function(i,item){
		
		if($(this).attr("mtype")==0){
			videoIds.push($(this).val());
		}else if($(this).attr("mtype")==1){
			audioIds.push($(this).val());
		}else if($(this).attr("mtype")==2){
			picIds.push($(this).val());
		}
		
	})
	var onairMap = new OnairHashMap();
	if(videoIds.length>0){
		onairMap.put("videoIds", videoIds);
	}else if(audioIds.length>0){
		onairMap.put("audioIds", audioIds);
	}else if(picIds.length>0){
		onairMap.put("picIds", picIds);
	}
	onairMap.put("newsId",_id);
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 $.ajax({
 	        'url':ctx+pathValue+"news/addNewsMaterial/", //服务器的地址
 	        'dataType':'json', //返回数据类型
 	        'headers': {'Content-Type': 'application/json'},
 	        'type':'POST', //请求类型
 	        'data':onairMap.toJson(),
 	        'success':function(data){
 	        	layer.close(index);
 		       	if(data.code==0){
 		       		toastr.success("操作成功");
 		       		$(".add_alert").hide();
	 		       	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
		 		   	strJson= JSON.stringify(strJson);
		 		   	initNews(strJson,$("#saveFlag").val());
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
function toVstl(item){
	if(item.vslt==undefined){
		return "";
	}else{
		if(item.mtype==0||item.mtype==1){
			return '<img src="'+item.vslt+getMaterialResolution()+'" ><i></i>';
		}else{
			return '<img src="'+item.vslt+getMaterialResolution()+'" >';
		}
	}
}

