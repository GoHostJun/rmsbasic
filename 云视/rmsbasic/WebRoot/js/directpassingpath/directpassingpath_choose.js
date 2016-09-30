$(function(){
	getAllDirectPassingPath();
	$("#direct_ul li").click(function(){
		$(this).toggleClass("active");
	})
	
})
function toSaveAndOpenUpload(){
	
	var directIds=[];
	$("#direct_ul").find("li").each(function(i,item){
		if($(this).hasClass("active")){
			directIds.push($(this).attr("_id"))
		}
	})
	$("#directpathIndex").val(directIds);
	$("#upload_cont").css("visibility","visible");
	$(".min_upload_icon").hasClass("max_upload_icon")&&$(".min_upload_icon").removeClass("max_upload_icon");
	$(".upload_cont").hasClass("max")&&$(".upload_cont").removeClass("max");
	layer.closeAll();
}
/**
 * 获取上传路径
 */
function getAllDirectPassingPath(){
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 10000);
	onairMap.put("keyWord", $("#pushnameword").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("isIndex", "0");
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +pathValue+"directpassingpath/findAllDirectPassingPath/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#direct_ul li:gt(0)").remove();
				if(data.data.results&&data.data.results.length>0){
					$.each(data.data.results,function(i,item){
						$("#direct_ul").append('<li _id="'+item._id+'"><i></i>'+item.tarpathname+'</li>');
					})
				}
			} else {
				layer.msg('查询直传路径失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}