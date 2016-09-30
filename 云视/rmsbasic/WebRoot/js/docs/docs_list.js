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
	$("#addDocs").attr("href",ctx+pathPage+"material/toAddDocs/"); 
	$("#pageDiv").onairPage({"callback":getListData});
	getListData();
	//全勾选or全不勾选
	$("#checkAll").click(function(){
		if($(this).is(':checked')){
			$("#listDiv :checkbox").prop("checked",true);
		}else{
			$("#listDiv :checkbox").attr("checked",false);
			
		}
	})
	//删除文稿
	$("#delete").click(function(){
		if( $("#listDiv :checkbox:checked").length<1   ){
			toastr.error("至少选择一条记录");
		}else{
			layer.confirm('确认删除文稿？', {
			    btn: ['确认','取消'] 
			}, function(outIndex){
				deleteDocs(outIndex);
			}, function(){
			});
		}
	})
	//回车事件
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getListData(1,10);
        }
    }; 
    
  //分享隐藏
    $('body').click(function(){
		$(".qr-code").hide();		
	})
	
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
	$("[name=clintImg]").attr("src",ctx+"/images/sidebar/"+getUrlConfig.clintImg);
});
//删除文稿
function deleteDocs(outIndex){
	layer.close(outIndex);
	var index = layer.load();
	var ids=new Array();
	$("#listDiv :checkbox:checked").each(function(i,item){
		ids.push($(this).val())
	})
	var map = new OnairHashMap();
	map.put("ids", ids);
	map.put("accessToken", getAccessToken());
	map.put("timeStamp", getTimeStamp());
	$.ajax({
		url : ctx + pathValue+"presentation/delete/",
		headers:{"Content-Type":"application/json"},
		data : map.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0 ){
				toastr.success("删除成功");
				uCont('material/toDocs/')
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

function getListData(pageNow,pageSize){
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
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("keyWord", keyWord);
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	$.ajax({
		url : ctx + pathValue+"presentation/query/",
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
				toastr.error("查询文稿列表失败");
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
			toastr.error("查询文稿列表失败");
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
 * 处理媒体类型显示的图标
 * @param mType
 * @returns {String}
 */
function processIcon(videos,audios,pics){
	if(videos && videos.length > 0){
		return "<b class=\"video_icon\"></b>";
	}else if(audios && audios.length > 0){
		return "<b class=\"audio_icon\"></b>";
	}else if(pics && pics.length > 0){
		return "<b class=\"img_icon\"></b>";
	}else {
		return "";
	}
}
/**
 * 处理视音频总数显示
 * @param mType
 * @returns {String}
 */
function processVideoAudioNum(videos,audios){
	var count = 0;
	if(videos && videos.length > 0){
		count += videos.length;
	}
	if(audios && audios.length > 0){
		count += audios.length;
	}
	return count;
}
/**
 * 处理视音频总数显示
 * @param mType
 * @returns {String}
 */
function processPicsNum(pics){
	var count = 0;
	if(pics && pics.length > 0){
		count +=  pics.length;
	}
	return count;
}
/**
 * 处理扩展信息的显示
 * 
 * @param othermsgObj
 * @param attributeName
 * @returns
 */
function processTemplateInfo(templateObj,attributeName){
	if(!templateObj){
		return processValue(templateObj);
	}
	if(!templateObj[attributeName]){
		return processValue(templateObj[attributeName]);
	}
	return templateObj[attributeName];
}
function processThumbnailurl(item,durationDate){
	var html = [];
	if(item.thumbnailurl&&item.thumbnailurl!="undefined"){
		html.push("<div class=\"list_img_div fl\">");
		if(item.thumbnailurl!='undefined'){
			html.push("<img src=\""+item.thumbnailurl+getMaterialResolution()+"\"/>");
		}
	//	html.push("<div class=\"changci\"><span>唱词</span></div>");
		html.push(getIcon(item)); 		
		html.push("<p>"+durationDate+"</p>"); 		
		html.push("</div>"); 		
	}else{
		if(item.audios!=undefined&&item.audios.length>0){
			html.push("<div class=\"list_img_div list_audio_div fl\">");
		}else{
			html.push("<div class=\"list_img_div noimg_div  fl\">");
		}
	//	html.push("<div class=\"changci\"><span>唱词</span></div>");
		html.push(getIcon(item)); 
		html.push("<p>"+durationDate+"</p>"); 		
		html.push("</div>");
	}
	return html.join("");
}

function getIcon(data){
	if(data.videos!=undefined&&data.videos.length>0){
		var icon='<i><b class="video_icon"></b></i>';
	}else if(data.audios!=undefined&&data.audios.length>0){
		var icon='<i><b class="audio_icon"></b></i>';
	}else{
		var icon='<b class="img_icon"></b>';
	}
	return icon;
}

/**
 * 状态显示处理
 * @param status
 * @returns {String}
 */
function processStatus(status){
	if(status == 10){
		return "<i id=\"mStatus\" class=\"chulizhong\">服务中</i>";
	}else if(status == 11){
		return "<i id=\"mStatus\" class=\"keyong\">服务完成</i>";
	}else{
		return "";
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
	$(results).each(function(i,item){	
		var duration=getAVPinfo(item).duration;
		var durationDate="";
		if(duration!=0){
			durationDate=MillisecondToDate(duration)
		}
		html.push("<div class=\"news_div\">");
		html.push("<input type=\"checkbox\" name=\"doc_name\" class=\"choice_icon\" value='"+item._id+"'>"); 
		html.push("<a href=\""+ctx+pathPage+"material/toViewDocs/?docId="+item._id+"\" target=\"_blank\">");
		html.push(processThumbnailurl(item,durationDate));
		//html.push(getIcon(item));
		html.push("</a>");
		html.push("<div class=\"news_right  fr\">");
			html.push("<h5>");
			html.push("<a href=\""+ctx+pathPage+"material/toViewDocs/?docId="+item._id+"\" target=\"_blank\" title='"+processValue(item.title)+"'>"+cutTitleString(processValue(item.title),40,"...")+"</a>"); 
			html.push(processIcon(item.videos,item.audios,item.pics));
			html.push("<a href='javascript:;' onclick='tosharedocs(this,\""+item._id+"\",event)' class=\"list_icon list_icon8\"><p>分享</p>");
			html.push('<div class="qr-code" ><div class=""><i></i><div name="sharediv"></div><p>微信扫一扫，分享给同事</p></div>	</div></a>');
			html.push("<a href=\"javascript:;\" onclick=\"showSendShare(\'"+item._id+"\')\" class=\"list_icon list_icon7\"><p>推送</p></a>"); 
			html.push("<a href=\"javascript:;\" onclick=\"openCreateNews(this,\'"+item._id+"\')\" titl='"+item.title+"' class=\"list_icon list_icon2\"><p>创建</p></a>"); 
//			html.push("<a href=\""+ctx+pathPage+"docs/toDocsService/?docId="+item._id+"\" target=\"_blank\" class=\"list_icon list_icon3\"><p>服务</p></a>"); 
			html.push("<a href=\""+ctx+pathPage+"material/toEditDocs/?docId="+item._id+"\" target=\"_blank\" class=\"list_icon list_icon4\"><p>编辑</p></a>"); 
			html.push("<div class=\"clear\"></div>");
			html.push("</h5>");
		html.push("<p>");
		html.push("<b>供片台：<span>"+processTemplateInfo(item.template,'tvStationName')+"</span></b>"); 
		html.push("<b title='"+processTemplateInfo(item.template,'reporter')+"'>记者：<span>"+cutStr(processTemplateInfo(item.template,'reporter'),5,"...")+"</span></b>"); 
		html.push("<b title='"+processTemplateInfo(item.template,'keyWords')+"'>关键词：<span>"+cutStr(processTemplateInfo(item.template,'keyWords'),9,"...")+"</span></b>");
		html.push("<b>创建人：<span>"+processValue(item.cusename)+"</span></b>");
		html.push("</p>");
		html.push("<p>视音频：<span>"+processVideoAudioNum(item.videos,item.audios)+"</span>&nbsp;&nbsp;|&nbsp;&nbsp;图片：<span>"+processPicsNum(item.pics)+"</span></p>");
		
		html.push("<p class=\"jianjie_p\" >简介：<span >"+jianjie(processTemplateInfo(item.template,'content'),46,76)+"</span></p>");
		html.push("<p class=\"p_bot\">"+processStatus(item.status)+"<b>更新时间：<span>"+processValue(item.uutime)+"</span></b></p>");
		html.push("</div>");
		html.push("<div class=\"clear\"></div>");
		html.push("</div>");
	});
	$("#listDiv").html(html.join(""));
	//素材下载、通联创建圆圈居中的样式
	var icon_w=$(".list_img_div i").width();
	var icon_h=$(".list_img_div i").height();
	$(".list_img_div i").css({"top":"50%","margin-left":-icon_w/2,"margin-top":-icon_h/2});
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
	initSelectAll();
}
/**
 * 新页签添加通联
 * @param id
 */
function openCreateNews(This,id){
	var title=$(This).attr("titl").replace(/\"/g,"\'");
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "material/createNews/?_id="+id+"&title="+title,
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['770px','560px'],
				title : "创建通联",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
			//	maxmin : true,
				scrollbar : false,
				//offset: ['300px', '200px'],
				content : data,
				shade:0.01
			});
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
	//uCont('news/toNewsCreate/?_id='+id)
//	$("#title").html(title);
//	$("#createNews").show();
}

/**
 * 推送INEWS提示框
 * @param id

function sendInewsTip(id){
	layer.confirm('确认推送文稿？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		sendINEWS(outIndex,id);
	}, function(){
	});
} */

function tosharedocs(This,id,e){
	$(This).find(".qr-code").show();
	e.stopPropagation();
	var index = layer.load();
	var map = new OnairHashMap();
	map.put("catalogueid", id);
	map.put("accessToken", getAccessToken());
	map.put("timeStamp", getTimeStamp());
	$.ajax({
		url : ctx + pathValue+"presentation/toFieldNews/",
		headers:{"Content-Type":"application/json"},
		data : map.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0 ){
				$(This).find("[name='sharediv']").html('');
				$(This).find("[name='sharediv']").qrcode( {width: 137,  height:137,text:"http://"+window.location.host+"/api/share/getShareDocsInfo/?_id="+data.id+"&userId="+userId+"&type=doc"});
				//toastr.success("共享文稿失败");
			}else{
				toastr.error("共享文稿失败");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("共享文稿失败");
		}
	});
}
/**
 * 加载推送路径
 */
function showSendShare(id){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "shareresource/sendShareNews/?_id="+id+"&pushType=DOCS",
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
 * 推送INEWS提示框
 * @param id
 */
function sendInewsTip(_id){

	var contentLayer=' <div class="tsp_cont">'+
	'<p class="title">选择推送到：<p>'+
	' <div class="ts_popup">  '+              	 
	     '<div class="ts_inner fl ">'+
	         '<div class="inews">'+
	         '</div>'+
//	         '<p>未完成</p>'+
	     '</div>'+
	     '<div class="ts_inner fr">'+
	         '<div class="paipai"></div>'+
//	         '<p>未完成</p>'+
	     '</div>'+
	     '<div class="clear"></div>'+
	 '</div>'+
	'</div><div class="footer-btn"><a href="javascript:sendINEWSorPAIPAI(\''+_id+'\');" class="save-btn"> 确&nbsp;定 </a><a href="javascript:layer.closeAll();" class="cancel-btn"> 取&nbsp;消 </a></div>';
	layer.open({
		type : 1, // page层
		area : [ '400px','300px' ],
		title : "信息",
		shade : 0.5, // 遮罩透明度
		moveType : 1, // 拖拽风格，0是默认，1是传统拖动
		shift : 0, // 0-6的动画形式，-1不开启
		scrollbar : false,
		content : contentLayer
	});
	$(".ts_popup div").click(function(){
		$(this).toggleClass("active")
	})
	$(".select_p i").click(function(){
		$(this).toggleClass("click")
	})

}
function sendINEWSorPAIPAI(_id){
	var share;
	
	sendObject.docsId=_id;
	if($(".inews").hasClass("active")&&!$(".paipai").hasClass("active")){
		sendObject.sendINEWS();
	}else if(!$(".inews").hasClass("active")&&$(".paipai").hasClass("active")){
		sendObject.sendNET();
	}else if($(".inews").hasClass("active")&&$(".paipai").hasClass("active")){
		sendObject.sendINEWS();
		sendObject.sendNET();
	}else {
		toastr.warning("请至少选择一种推送方式");
	}
}
/**
 * 要进行推送的对象
 */
var sendObject={
		docsId:"",
		sendINEWS:function(){
			sendINEWS(this.docsId);
		},
		sendNET:function(){
			toSendNet(this.docsId);
		}
}

/**
 * 推送到INEWS
 * @param id
 */
function sendINEWS(id){
	layer.closeAll('page');
	var index = layer.load();
	var map = new OnairHashMap();
	map.put("id", id);
	map.put("accessToken", getAccessToken());
	map.put("timeStamp", getTimeStamp());
	$.ajax({
		url : ctx + pathValue+"presentation/pushINEWS/",
		headers:{"Content-Type":"application/json"},
		data : map.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0 ){
				toastr.success("推送INEWS成功");
			}else{
				toastr.error("推送INEWS失败");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("推送INEWS失败");
		}
	});
	}
//提交网台
function toSendNet(_id){
	layer.closeAll('page');
	var index = layer.load();
	var map = new OnairHashMap();
	map.put("id", _id);
	map.put("accessToken", getAccessToken());
	map.put("timeStamp", getTimeStamp());
	$.ajax({
		url : ctx + pathValue+"presentation/pushConverge/",
        'headers': {'Content-Type': 'application/json'},
        'dataType':'json', // 返回数据类型
        'type':'POST', // 请求类型
        'data':map.toJson(),
        'success':function(data){
        	$("#sendStatus").html('');
        	layer.close(index);
        	if(data.code==0){
        		toastr.success("推送智云成功");
        		//uCont('news/toNewsMyWaitingSendList/');
    		 }else{
    			 toastr.error("推送智云失败");
    			// uCont('news/toNewsMyWaitingSendList/');
    		 }
        },
		error : function(){
			layer.close(index);
			 toastr.error("推送智云失败");
		}
	});
}

//获取时长 视频音频和图片数量
function getAVPinfo(data){
	var duration=0;
	var avCount=0;
	var pCount=0;
	if(data.videos!=undefined&&data.videos.length>0){
		$.each(data.videos,function(i,item){
			if(item.duration!=undefined){
				duration+=item.duration;
			}
			avCount++;
		})
	}
	if(data.audios!=undefined&&data.audios.length>0){
		$.each(data.audios,function(i,item){
			if(item.duration!=undefined){
				if(duration==0){
					duration+=item.duration;
				}
			}
		
			avCount++;
		})
	}
	if(data.pics!=undefined&&data.pics.length>0){
		$.each(data.pics,function(i,item){
			pCount++;
		})
	}
	return {"duration":duration,"avCount":avCount,"pCount":pCount};
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
     $("input[name='doc_name']").click(function () {
                $("input[name='doc_name']:checked").length == $("input[name='doc_name']").length ? $("#checkAll").prop("checked", true) : $("#checkAll").prop("checked", false);
     });

}