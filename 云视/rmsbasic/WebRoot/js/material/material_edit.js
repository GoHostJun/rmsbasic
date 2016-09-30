
$(document).ready(function() {
	//initDate();
	$("#form").validationEngine();
	initPicStyle();
	initUpload();
	findMaterialById();
	$("#describe").keyup(function(){
		$("#wordNumber").text($(this).val().length);
		$("#leftWordNumber").text(400-$(this).val().length);
	})
	$("#title").keyup(function(){
		$("#titleCount").text($(this).val().length);
		$("#leftTitleCount").text(50-$(this).val().length);
	})
	
	//重置
	$("#materialRest").click(function(){
		layer.confirm('确认重置素材？', {
		    btn: ['确认','取消'] 
		}, function(outIndex){
			layer.close(outIndex);
			$("[name='form'] :input").each(function(){
				$(this).val('');
				$("#wordNumber").text($(this).val().length);
				$("#titleCount").text($(this).val().length);
				$("#leftWordNumber").text(400-$(this).val().length);
				$("#leftTitleCount").text(50-$(this).val().length);
			})
		}, function(){
		});
	})
});

function findMaterialById() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", $("#mId").val());
	
	$.ajax({
		url : ctx + pathValue+"media/queryById/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0){
				$("#title").val(processValue(data.name));
				$("#happenTime").val(processOthermsg(data.othermsg,"happenTime"));
				$("#happenPlace").val(processOthermsg(data.othermsg,"happenPlace"));
				$("#describe").val(processValue(data.describe));
				$("#wordNumber").text(processValue(data.describe).length);
				$("#titleCount").text(processValue(data.name).length);
				$("#leftWordNumber").text(400-processValue(data.describe).length);
				$("#leftTitleCount").text(50-processValue(data.name).length);
				$("#thumbnail").attr('src',processValue(data.vslt+getMaterialResolution())); 
				//$("#thumbnail").attr('src',"http://192.168.0.65/vmsStorage/companyId/appCode/userId/b70a286b23e74eb9822380e561248dcc.jpg"); 
				if(data.thumbnails!=undefined&&data.thumbnails.length>0){
					$.each(data.thumbnails,function(i,item){
						if(i<3){
							if(item.type==1){
								$("#thumbnails ul:eq(0)").append('<li class="active"><a href="javascript:;"><i style="height:;"></i><img src="'+item.wanurl+getMaterialResolution()+'"></a></li>')
							}else{
								$("#thumbnails ul:eq(0)").append('<li><a href="javascript:;"><i style="height:;"></i><img src="'+item.wanurl+getMaterialResolution()+'"></a></li>')
							}
						}else{
							if(item.type==1){
								$("#thumbnails ul:eq(1)").append('<li class="active"><a href="javascript:;"><i style="height:;"></i><img src="'+item.wanurl+getMaterialResolution()+'"></a></li>')
							}else{
								$("#thumbnails ul:eq(1)").append('<li><a href="javascript:;"><i style="height: 85.9551px;"></i><img src="'+item.wanurl+getMaterialResolution()+'"></a></li>')
							}
						}
						
					})
					var img_w=$(".veConRgImg_ul li img").width();
					$(".veConRgImg_ul li img").css({"height":img_w/1.78});
					$(".veConRgImg_ul li i").css({"height":img_w/1.78});
				}
				
				//系统图片鼠标悬浮和点击效果
				$(".veConRgImg_ul li").mouseenter(function(){
					$(this).addClass("hover");
				})
				$(".veConRgImg_ul li").mouseleave(function(){
					$(this).removeClass("hover");
				})
				$(".veConRgImg_ul li").click(function(){
					$(this).toggleClass("active").removeClass("hover").siblings().removeClass("active");
					$(this).parent("ul").siblings().find("li").removeClass("active");
					$("#thumbnail").attr("src",$(this).find("img").attr("src"));
				})
			
			}else{
				toastr.error("查询素材详情失败");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询素材详情失败");
			
		}
	});
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
 * 所有信息如果不存在显示--
 * 
 * @param obj
 * @returns
 */
function processValue(obj){
	if(obj){
		return obj;
	}
	return "";
}

/**
 * 更新数据
 * 
 * @param pageNow
 * @param pageSize
 */
function updateMaterial() {
	var bool = $('#form').validationEngine('validate');
	if (!bool) {
		return;
	}
	var index = layer.load();
	//var onairMap = new OnairHashMap();
	var onairMap = new Object();
	onairMap.accessToken = "";
	onairMap.timeStamp = "";
	onairMap.id = $("#mId").val();
	onairMap.name = $("#title").val();
	onairMap.thumbnailurl = $("#thumbnail").attr("src").split("@")[0];
	onairMap.describe = $("#describe").val();
	var othermsg = new Object();
	othermsg.happenPlace = $("#happenPlace").val();
	othermsg.happenTime = $("#happenTime").val();
	onairMap.othermsg = othermsg;
	onairMap.vslt=$("thumbnail").attr("src");
	$.ajax({
		url : ctx + pathValue+"media/update/",
		headers:{"Content-Type":"application/json"},
		data : JSON.stringify(onairMap),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code == 0){
				toastr.success("更新成功！");
				window.opener.location.href="javascript:uCont('material/toMaterial/')";
				window.close();
				return;
			}else{
				toastr.error("更新失败，请重试！");
				return;
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("更新失败，请重试！");
			return;
		}
	});
}
function initPicStyle() {
	// 素材编辑
	// 缩略图
	var img_w = $(".veConRgImg_ul li img").width();
	$(".veConRgImg_ul li img").css({
		"height" : img_w / 1.78
	});
	$(".veConRgImg_ul li i").css({
		"height" : img_w / 1.78
	});

	$(window).resize(function() {
		var img_w = $(".veConRgImg_ul li img").width();
		$(".veConRgImg_ul li img").css({
			"height" : img_w / 1.78
		});
		$(".veConRgImg_ul li i").css({
			"height" : img_w / 1.78
		});
	})
	var veConRgImg_Sonmenu = $("#veConRgImg_Sonmenu");
	veConRgImg_Sonmenu.find("div ul li").hover(function() {
		$(this).addClass("hover").click(function() {
			$("#thumbnail").attr('src',$(this).find("img")[0].src); 
			veConRgImg_Sonmenu.find("div ul li").removeClass("active");
			$(this).removeClass("hover").addClass("active");
		});
	}, function() {
		$(this).removeClass("hover");

	})

}

/**
 * 初始化上传插件
 */
function initUpload() {
	$("#fileUpload").uploadify(
			{
				'debug' : false,
				'swf' : ctx + '/common/plugins/uploadify3.1/uploadify.swf',
				'fileTypeExts' : '*.jpg;*.png;*.gif;*.png;',
				'buttonImage' : ctx+ '/common/plugins/uploadify3.1/img/uploadThumb.png',
				'uploader' : ctx +"/upload/thumbnail/?id="+mId,
				'fileObjName' : 'fileUpload',
				'queueID' : 'fileQueue',
				'fileSizeLimit' : 1024,
				'auto' : true,
				'multi' : false,
				'width' : 160,
				'height' : 38,
				'progressData' : 'speed',
				'preventCaching' : true,
				'successTimeout' : 99999,
				'removeTimeout' : '1',
				'removeCompleted' : true,
				'removeTimeout' : 3,
				'onUploadSuccess' : function(file, data, response) {
					if(data){
						$("#thumbnail").attr('src',data); 
					}
				}
			});
}

function initDate() {

	var date = new Date();
	$("#happenTime").val(date.format("yyyy-MM-dd hh:mm:ss"));
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};


