$(document).ready(function() {
	//当前显示
	if(getUrlConfig.nameTV=="JS"){
		$(".sidebar_div").show();
	}else{
		$(".code_hover").show();
	}
	
	//去掉首页默认的的
	$("#indexPop").remove();
	$("#indexJX").remove();
	
	
	$("#ascrail2000").remove();
	$("#pageDiv").onairPage({"callback":getListData});
	$("#addDocs").attr("href",ctx+pathPage+"material/toAddDocs/"); 
	if(type!=undefined||type!=null){
		$(".material_choice span").removeClass("active");
		if(type==0){
			$(".material_choice span:eq(1)").addClass("active");
		}else if(type==1){
			$(".material_choice span:eq(2)").addClass("active");
		}else if(type==2){
			$(".material_choice span:eq(3)").addClass("active");
		}else {
			$(".material_choice span:eq(0)").addClass("active");
		}
	}
	//回车搜索
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getListData(1,10);
        }
    }; 
	/**
	 * 点击上传素材按钮，弹出上传插件
	 */
    $("#uploadFile").click(function(){
//    	if($("#webuploaddiv").html()==""){
//    		uCustomDiv("/upload/towebUpload/","webuploaddiv");
//    		}else{
//    			$(".upload_cont").show();
//    		}
    	$("#upload_cont").css("visibility","visible");
    	$(".min_upload_icon").hasClass("max_upload_icon")&&$(".min_upload_icon").removeClass("max_upload_icon");
		$(".upload_cont").hasClass("max")&&$(".upload_cont").removeClass("max");
    	//document.getElementById('upload_cont').style.visibility='visible'
	})
	getListData();
	//全勾选or全不勾选
	$("#checkAll").click(function(){
		if($(this).is(':checked')){
			$("#listDiv :checkbox:enabled").prop("checked",true);
		}else{
			$("#listDiv :checkbox:enabled").attr("checked",false);
			
		}
	})
//	 setInterval(getListData,5000);
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
	$("[name=clintImg]").attr("src",ctx+"/images/sidebar/"+getUrlConfig.clintImg);
});

function getListDataByMaterialType(t){
	$(".material_choice span").each(function(){
		$(this).attr("class","");		
	});
	$(t).attr("class","active");
	getListData(1,10);
}
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
		return "";
	case "视频":	
		return ["0"];
	case "音频":
	return ["1"];
	case "图片":
	return ["2"];
	default:
		return "";
	}
}
/**
 * 查询数据
 * 
 * @param pageNow
 * @param pageSize
 */
function getListData(pageNow,pageSize) {
	var index = layer.load();

	if(!pageNow && !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}

	var materialTypeCode = getMaterialTypeCode();
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
				loadHtml(data,"listDiv");
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
 * 删除素材
 * 
 * @param id
 */

function deleteMaterial(){
	var ids = [];
	if( $("#listDiv :checkbox:checked").length<1   ){
		toastr.error("至少选择一条记录");
	}else{
		$("[name = material]:checkbox").each(function () {
	         if ($(this).is(":checked")&&$(this).attr("deleteabled")!='') {
	        	 ids.push($(this).attr("value"));
	         }
	     });
		if(ids.length<1){
			toastr.error("至少选择一条尚未引用记录");
			return;
		}else{
			layer.confirm('确认删除素材？', {
				btn: ['确认','取消'] 
			}, function(outIndex){
				deleteMaterialByIds(outIndex);
			}, function(){
			});
		}
	}
}
function deleteMaterialByIds(outIndex){
	
	var index = layer.load();
	var ids = [];
	 $("[name = material]:checkbox").each(function () {
         if ($(this).is(":checked")&&$(this).attr("deleteabled")!='') {
        	 ids.push($(this).attr("value"));
         }
     });
	 var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("ids", ids);
	 layer.close(outIndex);
	$.ajax({
		url : ctx + pathValue+"media/delete/",
		headers:{"Content-Type":"application/json"},
		data :onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			var code = data.code;
			if(code == 0 ){
				toastr.success("删除成功");
				/**
				 * 删除成功后，需刷新数据
				 */
				getListData(1,10);
			}else{
				toastr.error("删除失败");
			}
			
		},
		error : function() {
			layer.close(index);
			toastr.error("删除失败");
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
 * @param id
 * @returns {String}
 */
function processStatus(status,id){
	if(status == 0){
		return "<i id=\"mStatus\" class=\"keyong\">可用</i>";
	}else if(status == 1){
		return "<i id=\"mStatus\" class=\"shibai\">失败</i>";
	}
//	else{
//		return "<i id=\"mStatus\" processStatus='"+id+"' class=\"chulizhong\">处理中</i>";
//	}
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
var initTimer;
function loadHtml(obj,divId){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	var dealingMaterial=new Array();
	$(results).each(function(i,item){
		html.push("<div id=\""+item._id+"\" class=\"news_div\">");
		html.push("<input class=\"choice_icon\" name=\"material\" type=\"checkbox\" value=\""+item._id+"\" "+checkboxIfDisabled(item.cataids)+" />");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"&mtype="+item.mtype+"\" target=\"_blank\">");
		//html.push("<div class=\"list_img_div  list_audio_div noimg_div fl\">");
		//html.push("<img src=\""+(item.vslt==undefined?"":item.vslt)+"\">");
		html.push((item.mtype==1?"<div class=\"list_img_div  list_audio_div fl\">":"<div class=\"list_img_div noimg_div fl\">"));
		if(item.mtype==0){
			html.push((item.vslt==undefined?"":"<img src='"+item.vslt+getMaterialResolution()+"'>"));
		}else if(item.mtype==2){
			html.push((item.vslt==undefined?"":"<img src='"+item.wanurl+getMaterialResolution()+"'>"));
		}
		html.push(processType(item.mtype));
		html.push("<div class=\"pos_div\">");	
		html.push(processIsUsed(item.cataids));
		html.push("</div>");
		html.push("</div>");
		html.push("</a>");
		html.push("<div class=\"news_right fr\">");
		html.push("<h5>");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"&mtype="+item.mtype+"\" target=\"_blank\" title='"+processValue(item.name)+"'>"+cutTitleString(processValue(item.name),40,"...")+"</a>");
		
		
		toStatus(html,item);
		html.push("<div class=\"clear\"></div>");
		html.push("</h5>");
		html.push("<p>");
		html.push("<b>大小：<span>"+formartSize(item.size)+" </span></b>");
		html.push("<b>分辨率：<span>"+formartWidthHeight(item.width)+" * "+formartWidthHeight(item.height)+"</span></b>");
		html.push("<b>格式：<span>"+processValue(item.fmt)+"</span></b>");
		html.push("</p>");
		html.push("<p><b>来源：<span>"+processValue(item.src)+"</span></b></p>");
		html.push("<p class=\"jianjie_p\" >简介：<span>"+setContent(item,'content')+"</span></p>");
		html.push("<p class=\"p_bot\">");
		html.push(processStatus(item.status,item._id));
		html.push("<b>更新时间：<span>"+processValue(item.uutime)+"</span></b></p>");
		if(item.status == 2){
			html.push("<p class='progress' style='position: relative;border: 1px solid #eeeeee;;background: #ffffff;width: 125px;height: 20px;' >" +
			"<span name='progress' style='background: #b7ffba;display: block; height: 20px; width: 0%;'></span>" +
			"<span name='progressword'style='top: -3px;left:14px;position: absolute;'processStatus='"+item._id+"' mytype='"+item.mtype+"'>处理中 &nbsp&nbsp&nbsp&nbsp0%</span></p>");
		}
		
		html.push("</div>");
		html.push("<div class=\"clear\"></div>");
		html.push("</div>");
		if(item.status==2){
			dealingMaterial.push(item.status);
		}
	});
//	$("#listDiv").html(html.join(""));
	$("#"+divId).html(html.join(""));
	//素材下载、通联创建圆圈居中的样式
	var icon_w=$(".list_img_div i").width();
	var icon_h=$(".list_img_div i").height();
	$(".list_img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
	initSelectAll();
	//intervalMaterial(dealingMaterial);	
}

function setContent(item){
	if(item.describe!="null"&&processDescribe(item.describe)){
		return jianjie(processDescribe(item.describe),46,76)
	}else{
		return "--";
	}
}
/**
 * 根据该素材是否已经被引用进行可勾选与否
 * @param cataidsArray
 * @returns {Boolean}
 */
function checkboxIfDisabled(cataidsArray){
	if(!cataidsArray || cataidsArray.length == 0){
	
		return "";
	}else{
	
		return "deleteAbled";
	}
}
/**
 * 素材的定时任务
 * @param dealingMaterial 未处理数据集合
 * @param initTimer 定时任务
 */
function intervalMaterial(dealingMaterial){
	if(dealingMaterial.length>0){
		if(initTimer==undefined){
			initTimer=setInterval(getListData,10000);
		}
	}else{
		if(initTimer!=undefined){
			clearInterval(initTimer);
		}
	}

}
function toFastCode(){
	var ids=new Array();
	$("#listDiv :checkbox:checked").each(function(i,item){
		ids.push($(this).val())
	})
	window.open(ctx + pathPage+"material/toFastCode/?ids="+ids);
}
function toStatus(html,item){
	
	if(item.status == 0){
		html.push("<a onclick='downLoad(\""+item._id+"\",\""+item.wanurl+"\")' href='javascript:;'  class=\"list_icon list_icon1\">");
		html.push("<p>下载</p>");
		html.push("</a>");
//		html.push("<a  href=\""+ctx+pathPage+"material/toReferDocsList/?mId="+item._id+"&mtype="+item.mtype+"\" target=\"_blank\" class=\"list_icon list_icon6\">");
//		html.push("<p>引用</p>");
//		html.push("</a>");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialEdit/?mId="+item._id+"\" target=\"_blank\" class=\"list_icon list_icon4\">");
		html.push("<p>编辑</p>");
		html.push("</a>");
		html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+item._id+"&mtype="+item.mtype+"\" target=\"_blank\" class=\"list_icon list_icon5\">");
		html.push("<p>预览</p>");
		html.push("</a>");
		return html.join("");
	}else{
		html.push("<a  href='javascript:;' class=\"list_icon list_icon1 noclick\">");
		html.push("<p>下载</p>");
		html.push("</a>");
//		html.push("<a  href='javascript:;' class=\"list_icon list_icon6 noclick\">");
//		html.push("<p>引用</p>");
//		html.push("</a>");
		html.push("<a href='javascript:;'  class=\"list_icon list_icon4 noclick\">");
		html.push("<p>编辑</p>");
		html.push("</a>");
		html.push("<a href='javascript:;' class=\"list_icon list_icon5 noclick\">");
		html.push("<p>预览</p>");
		html.push("</a>");
		return html.join("");
	}
	
	
}

function downLoad(_id,wanurl){
		var contentLayer=	' <div class="tsp_cont">'+
           ' <div class="ts_popup">  '+              	 
                '<div class="ts_inner fl ">'+
                    '<div class="down_div">'+
                    '<p>转出码下载</p>'+
                    '<i class="download_icon1" onclick="downMedia(\''+_id+'\',0)"></i>'+
                '</div>'+
                '</div>'+
                '<div class="ts_inner fr">'+
                    '<div class="down_div">'+
                    '<p>源文件下载</p>'+
                    '<a class="download_icon2" href="'+wanurl+'" ></a>'+
                    '</div>'+
                '</div>'+
                '<div class="clear"></div>'+
            '</div>'+
          '</div>';
	
	layer.open({
		type : 1, // page层
		area : [ '350px','280px' ],
		title : "信息",
		shade : 0.5, // 遮罩透明度
		moveType : 1, // 拖拽风格，0是默认，1是传统拖动
		shift : 0, // 0-6的动画形式，-1不开启
		scrollbar : false,
		content : contentLayer
	});
	
}

function downMedia(id,type){
	layer.closeAll('page');
	window.open(ctx + pathValue+"download/downLoadMaterialZip/?id="+id+"&type="+type)
}


//获取素材进度
function loadProgress(){
		var pulls = $("span[processStatus]");
		var map = new Map();
		for ( var j = 0; j < pulls.size(); j++) {
			var $sp = $(pulls.get(j));
			var trid = $sp.attr("processstatus");
			map.put(j, trid);
		}
		if (map.getValues().toString() != "") {
			var cataIds = map.getValues().toString();
			var onairMap = new OnairHashMap();
			onairMap.put("accessToken", getAccessToken());
			onairMap.put("timeStamp", getTimeStamp());
			onairMap.put("ids", cataIds);
			$.ajax({
				url : ctx + pathValue+"media/queryProgress/",
				headers:{"Content-Type":"application/json"},
				data : onairMap.toJson(),
				type : "post",
				cache : false,
				dataType : "json",
				success : function(response) {
					var code = response.code;
					var data = response.data;
					if(code != 0){
						return;
					}
					if(code == 0 && data.length > 0){
						data.forEach(function(element, index){
							console.log(element.progress)
							$("#"+element.id).find("[name=progress]").width(element.progress);
							$("#"+element.id).find("[name=progressword]").text("处理中  "+element.progress);
							if(element.progress=="100%"){
								$("#"+element.id).find("[name=progressword]").text("处理完成  "+element.progress);
								refreshStatus();
							}
//							$("#"+element.id).find("[name=progress]").width("90%");
							
						})
					}
				}					
			});
		}
}

/**定时刷新  **/
function refreshStatus() {
	var pulls = $("span[processStatus]");
	var map = new Map();
	for ( var j = 0; j < pulls.size(); j++) {
		var $sp = $(pulls.get(j));
		var trid = $sp.attr("processstatus");
		map.put(j, trid);
	}
	if (map.getValues().toString() != "") {
		var cataIds = map.getValues().toString();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("ids", cataIds);
		$.ajax({
			url : ctx + pathValue+"media/queryByIds/",
			headers:{"Content-Type":"application/json"},
			data : onairMap.toJson(),
			type : "post",
			cache : false,
			dataType : "json",
			success : function(response) {
				var code = response.code;
				var data = response.data;
				if(code != 0){
					return;
				}
				if(code == 0 && data.results.length > 0){
					for ( var i = 0; i < data.results.length; i++) {
						if(data.results[i].status==0||data.results[i].status==1){
							if(data.results[i].status==0){
								$("#"+data.results[i]._id).find(".progress").hide()
								$("#"+data.results[i]._id).find("span:eq(0)").text(formartSize(data.results[i].size));
								$("#"+data.results[i]._id).find("span:eq(1)").text(formartWidthHeight(data.results[i].width)+" * "+formartWidthHeight(data.results[i].height));
								$("#"+data.results[i]._id).find("span:eq(2)").text(processValue(data.results[i].fmt));
								$("#"+data.results[i]._id+" .list_img_div").removeClass(".noimg_div");
								$("#"+data.results[i]._id+" .p_bot").html("<i id=\"mStatus\" class=\"keyong\">可用</i><b>更新时间：<span>"+processValue(data.results[i].uutime)+"</span></b>")
								var html = [];
								html.push("<a href=\""+ctx+pathPage+"material/toMaterialPreview/?mId="+data.results[i]._id+"&mtype="+data.results[i].mtype+"\" target=\"_blank\" title='"+processValue(data.results[i].name)+"'>"+cutTitleString(processValue(data.results[i].name),40,"...")+"</a>");
								toStatus(html, data.results[i]);
								html.push("<div class=\"clear\"></div>");
								$("#"+data.results[i]._id).find("h5").html(html.join(""));
							}
							if(data.results[i].status==1){
								$("#"+data.results[i]._id+" .p_bot").html("<i id=\"mStatus\" class=\"shibai\">失败</i><b>更新时间：<span>"+processValue(data.results[i].uutime)+"</span></b>")
							}
							$("#"+data.results[i]._id).find("[name=progressword]").removeAttr("processStatus")
							if($("#"+data.results[i]._id+" .list_img_div").find("img").length==0){
								setTimeout(loadVlst(data.results[i]._id), 3000);
								//$("#"+data.results[i]._id+" .list_img_div").prepend((data.results[i].vslt==undefined?"":"<img src='"+(data.results[i].vslt+getMaterialResolution())+"'>"));
							}
						}
					}
				}
			}					
		});
	}
}

function loadVlst(id){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", id);
	$.ajax({
		url : ctx + pathValue+"media/queryById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				return;
			}
			if($("#"+data._id+" .list_img_div").find("img").length==0){
				$("#"+data._id+" .list_img_div").prepend((data.vslt==undefined?"":"<img src='"+(data.vslt+getMaterialResolution())+"'>"));
			}
		}					
	});
}

refreshId = setInterval("loadProgress()", 5000);
function Map() {
	var myArrays = new Array();
	// 添加键值，如果键重复则替换值
	this.put = function(key, value) {
		var v = this.get(key);
		if (v == null) {
			var len = myArrays.length;
			myArrays[len] = {
				Key : key,
				Value : value
			};
		} else {
			for ( var i = 0; i < myArrays.length; i++) {
				if (myArrays[i].Key == key) {
					myArrays[i].Value = value;
				}
			}
		}
	},
	// 根据键获取值
	this.get = function(key) {
		for ( var i = 0; i < myArrays.length; i++) {
			if (myArrays[i].Key == key) {
				return myArrays[i].Value;
			}
		}
		return null;
	},
	// 以数组形式返回值列表
	this.getValues = function() {
		var newArray = new Array();
		for ( var i = 0; i < myArrays.length; i++) {
			newArray[i] = myArrays[i].Value;
		}
		return newArray;
	}
}

function initSelectAll(){
	$("#checkAll").prop('checked',false);
	 //全选或全不选 
     $("#checkAll").click(function(){  
        if(this.checked){  
            $("#listDiv :checkbox").prop('checked',true);   
        }else{    
            $("#listDiv :checkbox").prop('checked',false);
        }    
     }); 
     $("input[name='material']").click(function () {
                $("input[name='material']:checked").length == $("input[name='material']").length ? $("#checkAll").prop("checked", true) : $("#checkAll").prop("checked", false);
     });

}
