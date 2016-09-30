$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showMaterialList
	});
	showMaterialList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showMaterialList(1,10);
	     }
   	}; 
});
/**
 * 加载素材列表
 */
function showMaterialList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	onairMap.put("keyWord", $("#txtKeyWord").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/retry/findMedias/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data.results));
			} else {
				$("#table_body").html(loadTableData(data.data.results));
			}
			$("#pageDiv").resetOnairPageParameter(data.data.totalRecord, data.data.currentPage);
		},
		error : function() {
			layer.close(index);
			$("#table_body").html("查询出错了...");
		}
	});
}
function loadTableData(data){

	var tableBodyData = [];
	if (0 < data.length) {
					tableBodyData.push("<table border='0' cellpadding='0' cellspacing='0'>");
					tableBodyData.push("<tr>");
					tableBodyData.push("<th>名称</th>");
					tableBodyData.push("<th>大小</th>");
					tableBodyData.push("<th>分辨率</th>");
					tableBodyData.push("<th>格式</th>");
					tableBodyData.push("<th>来源</th>");
					tableBodyData.push("<th>源地址</th>");
					tableBodyData.push("<th>转码后地址</th>");
					tableBodyData.push("<th>创建时间</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + toString(item.name) + "</td>");
					tableBodyData.push("<td>" +formartSize(item.size) + "</td>");
					tableBodyData.push("<td>" + formartWidthHeight(item.width)+" * "+formartWidthHeight(item.height) + "</td>");
					tableBodyData.push("<td>" + toString(item.fmt )+ "</td>");
					tableBodyData.push("<td>" + toString(item.src) + "</td>");
					tableBodyData.push("<td> <textarea >" + toString(item.wanurl) + "</textarea></td>");
					tableBodyData.push("<td> <textarea >" + getDeaults(item.defaults) + "</textarea></td>");
					tableBodyData.push("<td> " + toString(item.uutime) + "</td>");
					tableBodyData.push("</tr>");
				});
				tableBodyData.push("</tbody>");
				tableBodyData.push("</table>");
				$("#pageDiv").show();
	} else {
		$("#pageDiv").hide();
		tableBodyData.push("<div class='no_data'></div>");
	//	tableBodyData = [ "<tr><td colspan='12'><br/><center>暂无数据，马上<a href='javascript:userAddOpen();' class='default_a' style='color:blue;'><b>添加用户</b></a>。</center><br/></td></tr>" ];
	}
	return tableBodyData.join("");

}
function getDeaults(defaults){
	var defaultsUrl='';
	if(defaults && defaults.length>0){
		$.each(defaults,function(i,item){
			defaultsUrl+="格式"+item.fmt+"外网地址"+item.wanurl;
		})
		
	}
	return defaultsUrl;
}
function toString(str){
	if(str){
		return str;
	}else{
		return "";
	}
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
	return toString(fileSize);
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
	return toString(num);
}
//弹出新增客户窗口
function openAddCustom(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/custom/custom_add",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增客户",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
//				maxmin : true,
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