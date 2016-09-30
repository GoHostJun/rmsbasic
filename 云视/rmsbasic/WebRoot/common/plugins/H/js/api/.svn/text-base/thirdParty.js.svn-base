$(document).ready(function() {
	getListData(1,10);
	$("#pageDiv").onairPage({"callback":getListData});
});
function setThirdParty(){
	
	var companyId = $("#companyId").val();
	var serviceCode = $("#serviceCode").val();
	var fun = $("#function").val();
	var validity = $("#validity").val();
	var IPAddress = $("#IPAddress").val();
	var taskQuery = $("#taskQuery").val();
	var taskRetry = $("#taskRetry").val();
	$.ajax({
		url : ctx +"/setThirdParty/",
		type : "post",
		data:{
			companyId:companyId,
			serviceCode:serviceCode,
			function:fun,
			validity:validity,
			IPAddress:IPAddress,
			taskQuery:taskQuery,
			taskRetry:taskRetry
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
			$('#myModal').hide();
			swal({title:"保存成功",type:"success"});
			getListData(1,10);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}
function setThirdPartyById(id){
	
	$.ajax({
		url : ctx +"/setThirdPartyById/",
		type : "post",
		data:{
			id:id
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
				$("#update_companyId").val(data.companyId);
				$("#update_serviceCode").val(data.serviceCode);
				$("#update_function").val(data.function);
				$("#update_validity").val(data.validity);
				$("#update_IPAddress").val(data.IPAddress);
				$("#update_taskQuery").val(data.taskQuery);
				$("#update_taskRetry").val(data.taskRetry);
				$("#updateId").val(data._id);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}
function deleteThirdPartyById(id){
	if(!confirm("确认删除吗")){
		return;
	}
	layer.load(2);
	$.ajax({
		url : ctx +"/deleteThirdPartyById/",
		type : "post",
		data:{
			id:id
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				swal({title:"删除失败",type:"error"});
				return;
			}
			swal({title:"删除成功",type:"success"});
			getListData(1,10);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}

function updateThirdParty(id){
	layer.load(2);
	var companyId = $("#update_companyId").val();
	var serviceCode = $("#update_serviceCode").val();
	var fun = $("#update_function").val();
	var validity = $("#update_validity").val();
	var IPAddress = $("#update_IPAddress").val();
	var taskQuery = $("#update_taskQuery").val();
	var taskRetry = $("#update_taskRetry").val();
	var id = $("#updateId").val();
	$.ajax({
		url : ctx +"/updateThirdParty/",
		type : "post",
		data:{
			id:id,
			companyId:companyId,
			serviceCode:serviceCode,
			function:fun,
			validity:validity,
			IPAddress:IPAddress,
			taskQuery:taskQuery,
			taskRetry:taskRetry
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
			$('#myModal_thirdParty').hide();
			swal({title:"保存成功",type:"success"});
			getListData(1,10);
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}
function getListData(pageNow,pageSize){
	layer.load(2);
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	$.ajax({
		url : ctx +"/getThirdPartyList/",
		type : "post",
		cache : false,
		data:{
			currentPage:pageNow,
			pageNum:pageSize
		},
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
//				toastr.error("查询文稿列表失败");
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
			loadHtml(response);
			layer.closeAll('loading');
		},
		error : function() {
//			toastr.error("查询文稿列表失败");
		}
	});
}

/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var html = [];
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	html.push("<div class=\"col-sm-12\">");
	html.push("<div class=\"ibox float-e-margins\">"); 
	html.push("<div class=\"ibox-content\">");
	html.push("<table class=\"table table-hover\">");
	html.push("<thead><tr><th>序号</th><th>企业表示</th><th>服务标识</th><th>功能</th><th>是否有效</th><th>任务创建</th><th>任务查询</th><th>任务重试</th><th>操作</th></tr></thead><tbody>");
	
	$(results).each(function(i,item){

		html.push("<tr>");
		html.push("<td><span class=\"line\">"+(i+1)+"</span></td>");
		html.push("<td><span class=\"line\">"+item.companyId+"</span></td>");
		html.push("<td><span class=\"line\">"+item.serviceCode+"</span></td>");
		html.push("<td>"+item.function+"</td>"); 
		if("1"==item.validity){
			html.push("<td><span class=\"line\">是</span></td>");
		}else{
			html.push("<td><span class=\"line\">否</span></td>");
		}
		html.push("<td><span class=\"line\">"+item.IPAddress+"</span></td>");
		html.push("<td><span class=\"line\">"+item.taskQuery+"</span></td>");
		html.push("<td><span class=\"line\">"+item.taskRetry+"</span></td>");
		html.push("<td>");
		html.push("<button type=\"button\" class=\"btn btn-sm btn-danger\" onclick=\"deleteThirdPartyById('"+item._id+"');\">删除</button>");
		html.push("<button type=\"button\" class=\"btn btn-sm btn-primary\" data-toggle=\"modal\" data-target=\"#myModal_thirdParty_update\" onclick=\"setThirdPartyById('"+item._id+"');\">修改</button>");
		html.push("</td>");
		html.push("</tr>");
	});
	
	html.push("</tbody></table></div></div></div></div>");
	$("#listDiv").html(html.join(""));
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}