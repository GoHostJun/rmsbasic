$("#md5").attr("checked", false);
var checkType = 0;
var casuserId = casUserId;
var appCode = "ytl";
var companyId = consumerId;
var chunkSize = 5 * 1024 * 1024; // 分块大小
var uniqueFileNameMap = new Map();
var filemd5MarkMap = new Map();


//var backEndUrl = "http://127.0.0.1:8080/webUploader/";
var backEndUrl="http://101.200.212.65:8083/ytl_upload/";


/*************wu上传初始****************/
new WebUploader.Uploader.register(
		{
			"before-send-file" : "beforeSendFile",
			"before-send" : "beforeSend",
			"after-send-file" : "afterSendFile"
		},
		{  //上传文件开始触发
			beforeSendFile : function(file) {
				//秒传验证
				var task = new $.Deferred();
				var start = new Date().getTime();
				var md5Mark;
				if (checkType == 1) {
					uploader.md5File(file).progress(
									function(percentage) {
										$("#" + file.id + " .progress .md5_pro .pro_color").width(percentage * 100 + "%");
                                        $("#" + file.id + " .progress .md5_pro .jindu").text("MD5进度：" + Math.round(percentage * 100 ) + "%");
										//console.log(percentage);
									}).then(function(val) {
									    $("#" + file.id + " .operate .del_btn").show();
	                                   // $("#" + file.id + " .operate .pause_btn").show();
										console.log("总耗时: "+ ((new Date().getTime()) - start)/ 1E3);
										md5Mark = val;
										filemd5MarkMap.put(file.id,md5Mark);
										$.ajax(
														{
															type : "POST",
															url : backEndUrl+ "fileUploadCheck",
															data : {
																status : "md5Check",
																md5 : val
															},
															cache : false,
															timeout : 10000, //todo 超时的话，只能认为该文件不曾上传过
															dataType : "json"
														}).then(function(data,textStatus,jqXHR) {
															if (data.ifExist) { //若存在，这返回失败给WebUploader，表明该文件不需要上传
																task.reject();
																uploader.skipFile(file);
																file.path = data.path;
																$("#" + file.id + " .progress .upload_pro .pro_color").width(100 + "%");
																UploadComlate(file,filemd5MarkMap.get(file.id));
															} else {
																task.resolve();
															}
														},function(jqXHR,textStatus,errorThrown) { //任何形式的验证失败，都触发重新上传
															task.resolve();
														});
									});
				} else {
				    $("#" + file.id + " .operate .del_btn").show();
	              //  $("#" + file.id + " .operate .pause_btn").show();
					task.resolve();
				}
				return $.when(task);
			},
			//开始上传分片触发
			beforeSend : function(block) {
				//分片验证是否已传过，用于断点续传
				var task = new $.Deferred();
				var file = block.file;
				var id = file.id;
		uploader.md5File(file,block.start,block.end).then(function(val){
				$.ajax({
					type : "POST",
					url : backEndUrl + "fileUploadCheck",
					data : {
						status : "chunkCheck",
						name : uniqueFileNameMap.get(id),
						chunkIndex : block.chunk,
						size : block.end - block.start,
						userId : casuserId,
						companyId:companyId,
						appCode:appCode,
						blockmd5:val
					},
					cache : false,
					timeout : 10000, //todo 超时的话，只能认为该分片未上传过
					dataType : "json",
				}).then(function(data, textStatus, jqXHR) {
					if (data.ifExist==1) { //若存在，返回失败给WebUploader，表明该分块不需要上传
						task.reject();
					} else {
						task.resolve();
					}
				}, function(jqXHR, textStatus, errorThrown) { //任何形式的验证失败，都触发重新上传
				    task.reject();
					task.resolve();
				});	
				});
				return $.when(task);
			},
			//上传文件完成触发
			afterSendFile : function(file) {
				var chunksTotal = Math.ceil(file.size / chunkSize);
				//		if ((chunksTotal = Math.ceil(file.size
				//				/ chunkSize)) > 1) {
				//合并请求
				var task = new $.Deferred();
				$.ajax({
					type : "POST",
					url : backEndUrl + "fileUploadCheck",
					data : {
						status : "chunksMerge",
						name : uniqueFileNameMap.get(file.id),
						chunks : chunksTotal,
						ext : file.ext,
						md5 : filemd5MarkMap.get(file.id),
						userId : casuserId,
						companyId:companyId,
						appCode:appCode
					},
					cache : false,
					dataType : "json"
				}).then(function(data, textStatus, jqXHR) {
					//todo 检查响应是否正常
					var status = data.status;
					file.path = data.path;
					task.resolve();
					if (status == 1) {
						UploadComlate(file,filemd5MarkMap.get(file.id));
					} else {
						UploadFail(file);
					}
					uniqueFileNameMap.remove(file.id);
					filemd5MarkMap.remove(file.id);
				}, function(jqXHR, textStatus, errorThrown) {
					task.reject();
				});
				return $.when(task);
				//	} else {
				//		UploadComlate(file);
				//	}
			}
		});



/***********上传配置*********************/
var uploader = WebUploader.create({
	swf : "js/Uploader.swf",//上传的flash
	server : backEndUrl + "fileUpload",//后台上传方法
	pick : "#picker",
	resize : false,
	dnd : "#upload_btn",
	disableWidgets : "log",
	paste : document.body,
	disableGlobalDnd : true,
	multiple : true,
	thumb : {
		width : 100,
		height : 100,
		quality : 100,
		allowMagnify : false,
		crop : true
	},
	compress : false,
	prepareNextFile : true,
	chunked : true,
	chunkSize : chunkSize,
	threads : "5",
	formData : {
		userId : casuserId,
		appCode:appCode,
		companyId:companyId
	},
	fileSizeLimit : 100 * 1024 * 1024 * 1024,
	fileSingleSizeLimit : 100 * 1024 * 1024 * 1024,
	duplicate : true,
	auto : true,
	//自定义上传类型
	accept: {
        title: '',
        extensions: 'ts,rmvb,flv,avi,mkv,mp4,mpg,mov,mpeg,'+
        	'asf,wmv,navi,3gp,rm,f4v,webm,m2v,ifox,jpg,gif,png,'+
        	'bmp,jpeg,cd,wave,wav,aiff,au,mp3,midi,wma,realaudio,vqf,oggvorbis,aac,ape,m4a,mxf',
        mimeTypes: '.ts,.rmvb,.flv,.avi,.mkv,.mp4,.mpg,'+
        	'.mov,.mpeg,.asf,.wmv,.navi,.3gp,'+
        	'.rm,.f4v,.webm,.m2v,.ifox,.jpg,.gif,.png,.bmp,'+
        	'.jpeg,.cd,.wave,.wav,.aiff,.au,.mp3,.midi,.wma,.realaudio,.vqf,.oggvorbis,.aac,.ape,.m4a,mxf'
    }
});

/************文件加入上传队列执行*******************/
uploader.on("fileQueued",function(file) {
	            	uploader.sort(function(a,b){return a.size-b.size;});//多文件同时加入时小文件优先上传
					var uniqueFileName = md5(file.name + file.size+ file.type + casuserId);//文件唯一标识
					uniqueFileNameMap.put(file.id, uniqueFileName);
					var fileSize=WebUploader.Base.formatSize( file.size,2, ['B', 'KB', 'MB','G','TB'] );
					$("#progress_cont").append('<div class="progress_div" id="'+file.id+'"><img /><p>'+fileSize 
											+ '&nbsp;&nbsp;'+file.name+'</p><div class="progress" id="progress'+file.id+'"></div>');
					if(checkType==1){
							$("#progress"+file.id).append('<div class="md5_pro">'
											+ '<span class="pro_color" style="width: 0%;"></span><span class="jindu"></span></div>'
											+ '<div class="upload_pro"><span class="pro_color "  style="width: 0%;"></span><span class="jindu"></span></div>'
											+ '</div>');		
			               //$("#"+file.id).append('<div class="operate"><i  class="reset_btn" style="display:none" ></i><i class="pause_btn" style="display:none"></i><i class="del_btn" style="display:none" ></i></div><div class="clear"></div>	');	
			               $("#"+file.id).append('<div class="operate"><i  class="reset_btn" style="display:none" ></i><i class="del_btn" style="display:none" ></i></div><div class="clear"></div>	');	
					}else{
						   $("#progress"+file.id).append('<div class="upload_pro"><span class="pro_color "  style="width: 0%;"></span><span class="jindu"></span></div>'+ '</div>');	
						   //$("#"+file.id).append('<div class="operate"><i  class="reset_btn" style="display:none" ></i><i class="pause_btn" style="display:none"></i><i class="del_btn"></i></div><div class="clear"></div>	');	
					       $("#"+file.id).append('<div class="operate"><i  class="reset_btn" style="display:none" ></i><i class="del_btn"></i></div><div class="clear"></div>	');	
					}				
			        var $img = $("#" + file.id).find("img");
					uploader.makeThumb(file, function(error, src) {
						if (error) {
							$img.replaceWith("");
						}
						$img.attr("src", src);
					});
});

/***********上传进度获取**************/
uploader.on("uploadProgress", function(file, percentage, uploaded) {
	var num = percentage * 100;
	//$("#" + file.id + " .operate .pause_btn").show();
	$("#" + file.id + " .operate .reset_btn").hide();
	$("#" + file.id + " .progress .upload_pro .pro_color").width(num + "%");
	$("#" + file.id + " .progress .upload_pro .jindu").text("上传进度：" + num.toFixed(2) + "%");
});



/************执行操作（暂缓开通）*****************/
$("#progress_cont").on("click", " .progress_div .operate .play_btn",
		function() {
		    var id = $(this).parent().parent().attr("id");
	    	var file = uploader.getFile(id);
			uploader.upload(file);
			$(this).removeClass("play_btn").addClass("pause_btn");
		});



/************暂停操作（暂缓开通）*****************/
$("#progress_cont ").on("click", " .progress_div .operate .pause_btn",
		function() {
			var id = $(this).parent().parent().attr("id");
			var file = uploader.getFile(id);
		   	uploader.stop(file );
			$(this).removeClass("pause_btn").addClass("play_btn");
		});


/************删除操作***************/
$("#progress_cont ").on("click"," .progress_div .operate .del_btn",function() {
		uniqueFileNameMap.remove($(this).parent().parent().attr("id"));
        filemd5MarkMap.remove($(this).parent().parent().attr("id"));
	    uploader.removeFile($(this).parent().parent().attr("id"),true ); //从上传文件列表中删除
		$(this).parent().parent().remove(); //从上传列表dom中删除
	}); 

/************重试操作****************/
$("#progress_cont ").on("click", " .progress_div .operate .reset_btn",
		function() {
			var id = $(this).parent().parent().attr("id");
			var file = uploader.getFile(id);
			 var uniqueFileName = md5(file.name + file.size
							+ file.type + casuserId);
			uniqueFileNameMap.put(file.id, uniqueFileName);
			uploader.retry(file);
		}); 

/************清空列表****************/
$(".empty_btn").click(function() {
	$(".yc").remove();
});

/************上传成功触发*************/
function UploadComlate(file,md5) {
	$("#" + file.id + " .progress .upload_pro .pro_color").width(
			100 + "%");
	$("#" + file.id + " .progress .upload_pro .jindu").text(
			"上传完成" );
	$("#" + file.id).addClass("yc");
	var compl=0;
	$("#progress_cont").find(".upload_pro").each(function(i,item){
		if($(this).find(".jindu").text()!="上传完成"&& $(this).find(".jindu").text()!="上传失败"){
			compl++;
			return ;
		}
		
	})
	if(compl==0){
//		$(".upload_cont h4 i.min_upload_icon").toggleClass("max_upload_icon");
//		$(".upload_cont").toggleClass("max");
		$("#upload_cont").css("visibility","hidden");
	}
	
	
//	//回调
	 var onairMap = new OnairHashMap();
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("url", file.path);
	 onairMap.put("name", file.name);
	 onairMap.put("md5", md5);
	 onairMap.put("size", file.size);
//		if(getUrlConfig.nameTV=="JS"){
			onairMap.put("directIds", $("#directpathIndex").val());
			 $.ajax({
				 url : ctx +pathValue+"directpassing/saveMaterial/",
				 headers:{"Content-Type":"application/json"},
				 data : onairMap.toJson(),
				 type : "post",
				 cache : false,
				 dataType : "json",
				 success : function(data) {
					 if($("#addUpload").val()==0){
							if(data.code==0){
								loadUploadHtml(data.data.id);
							}
						}else{
							if($("#directpathIndex").val()){
								uCont('directpass/toDirectPassList/');
							}else{
								uCont('material/toMaterial/');
							}
							
						}
				 },
				 error:function(){
					 
					 }
				 })
//		}else{
//			 $.ajax({
//				 url : ctx +pathValue+"media/saveHttpMaterial/",
//				 headers:{"Content-Type":"application/json"},
//				 data : onairMap.toJson(),
//				 type : "post",
//				 cache : false,
//				 dataType : "json",
//				 success : function(data) {
//					
//					 if($("#addUpload").val()==0){
//							if(data.code==0){
//								loadUploadHtml(data.data.id);
//							}
//						}else{
//							uCont('material/toMaterial/');
//						}
//				 },
//				 error:function(){
//					 
//					 }
//				 })
//		}
	//$("#" + file.id + " .operate .play_btn").hide();
	//$("#" + file.id + " .operate .pause_btn").hide();
}


/************上传失败触发*************/
function UploadFail(file) {
	 var onairMap = new OnairHashMap();
	 var div=$("#" + file.id + " .progress .upload_pro ").width();
	 var span=$("#" + file.id + " .progress .upload_pro .pro_color").width();
	 var progress=(parseInt(div)*100/parseInt(span)+"%");
	$("#" + file.id + " .progress .upload_pro .pro_color").width(
			100 + "%");
	$("#" + file.id + " .progress .upload_pro .jindu").text("上传失败");
	$("#" + file.id + " .operate .reset_btn").show();
	//上传失败日志
	 onairMap.put("accessToken", getAccessToken());
	 onairMap.put("timeStamp", getTimeStamp());
	 onairMap.put("action", "文件上传失败");
	 onairMap.put("logdesc", "上传文件名称:"+ file.name+"上传失败!");
	 $.ajax({
		 url : ctx +pathValue+"log/insertLog/",
		 headers:{"Content-Type":"application/json"},
		 data : onairMap.toJson(),
		 type : "post",
		 cache : false,
		 dataType : "json",
		 success : function(data) {},
		 error:function(){}
		 })
}

function check() {
	var checkbox = document.getElementById('md5');
	if (checkbox.checked) {
		checkType = 1;
	} else {
		checkType = 0;
	}
}

function Map() {
	this.container = new Object();
}

Map.prototype.put = function(key, value) {
	this.container[key] = value;
};

Map.prototype.get = function(key) {
	return this.container[key];
};

Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数 
		if (key == 'extend') {
			continue;
		}
		keyset[count] = key;
		count++;
	}
	return keyset;
};

Map.prototype.size = function() {
	var count = 0;
	for ( var key in this.container) {
		// 跳过object的extend函数
		if (key == 'extend') {
			continue;
		}
		count++;
	}
	return count;
};


Map.prototype.remove = function(key) {
	delete this.container[key];
};




//MD5校验
$(".upload_btn p span i").click(function() {
	$(this).toggleClass("active");
});


$(function() {
	$(document).on({
		dragleave : function(e) { //拖离 
			$("#upload_pos").css('display', 'none');
		},
		drop : function(e) { //拖后放 
			$("#upload_pos").css('display', 'none');
		},
		dragenter : function(e) { //拖进 
			$("#upload_pos").css('display', 'inline');
		},
		dragover : function(e) { //拖来拖去 
			$("#upload_pos").css('display', 'inline');
		}
	});
});


/***************弹窗控制***********************/
$(function(){
	//$("#upload_cont").css("visibility","hidden");
	//alert(11);
	//$("#upload_cont").css("visibility","visiable");
	$(".upload_cont h4 i.min_upload_icon").click(function(){
		$(this).toggleClass("max_upload_icon");
		$(".upload_cont").toggleClass("max");
	});
	
	$(".upload_cont h4 i.close_upload_icon").click(function(){
		$("#upload_cont").css("visibility","hidden");
		//$(this).parent().parent().css("visibility","hidden");
	});

	//MD5校验触发按钮
	$(".upload_btn p span i").click(function(){
		$(this).toggleClass("active");
	});
	
	
	
//	var UnloadConfirm = {};
//		UnloadConfirm.MSG_UNLOAD = "有附件正在上传\n\n您确定要离开吗？";
//		UnloadConfirm.set = function(a) {
//			window.onbeforeunload = function(b) {
//				b = b || window.event;
//				b.returnValue = a;
//				return a;
//			};
//		};
//		
//		UnloadConfirm.clear = function() {
//			fckDraft.delDraftById();
//			window.onbeforeunload = function(b) {
//				b = b || window.event;
//				b.returnValue = a;
//				return a;
//			};
//		};
//		UnloadConfirm.set(UnloadConfirm.MSG_UNLOAD); 
});
