$(function(){
	$(".tabs li").click(function(){
	 	$(".tabs li").eq($(this).index()).addClass("active").siblings().removeClass('active');
	 	$(".tabs-div-cont").hide().eq($(this).index()).show();
 	})
 	getAllPushPath();
	getOftenPushPath();
})

function subSend(){
	
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("newsId", newsId);
	onairMap.put("pushType", pushType);
	var pushSetId;
	
	$(".tabs-div-cont :visible").find("li").each(function(){
		if($(this).hasClass("active")){
			pushSetId=$(this).attr("value");
		}
	})
	if(pushSetId==undefined){
		toastr.error("请选择一个推送路径");
		return ;
	}
	onairMap.put("pushSetId", pushSetId);
	var index = layer.load();
	$.ajax({
		url : ctx + pathValue+"news/pushNews/",
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
//				toastr.error("推送失败");
				uCont('system/toPage/?view=pushtask/pushtask_list')
				return;
			}
			if(code == 0){
				uCont('system/toPage/?view=pushtask/pushtask_list')
//				  toastr.success("推送成功");
				  layer.closeAll();
				  
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("推送失败");
		}
	});
}
function getAllPushPath(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 100);
	$.ajax({
		url : ctx + pathValue+"push/getPushSets/",
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
				toastr.error("查询全部推送路径失败");
				return;
			}
			if(code == 0 && data.results.length > 0){
				$("#allSends").html('');	
				loadAll(data.results);
			}else{
				$("#allSends").html("<p>暂无数据</p>");	
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询全部推送路径失败");
		}
	});
}

function loadAll(results){
	$.each(results,function(i,item){
		$("#allSends").append("<li value="+item._id+" onclick='addActive(this)'>"+item.pushname+"</li>");
	})
}


function getOftenPushPath(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 100);
	$.ajax({
		url : ctx + pathValue+"push/getCustomPushs/",
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
				$("#oftenSends").html('');	
				toastr.error("查询常用推送路径失败");
				return;
			}
			if(code == 0 && data.results.length > 0){
				$("#oftenSends").html('');	
				loadOften(data.results);
			}else{
				$("#oftenSends").html("<p>暂无数据</p>");	
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("查询常用推送路径失败");
		}
	});
}

function loadOften(results){
	$.each(results,function(i,item){
		$("#oftenSends").append("<li value="+item.pushset._id+" onclick='addActive(this)'>"+item.pushset.pushname+"</li>");
	})
}

function addActive(This){
	$(This).addClass("active").siblings().removeClass("active");
}