$(document).ready(function() {
	getListData(1,10);
	$("#pageDiv").onairPage({"callback":getListData});
});
function setAgentManagement(){
	layer.load(2);
	var companyId = $("#companyId").val();
	var appCode = $("#appCode").val();
	var address = $("#address").val();
	$.ajax({
		url : ctx +"/setAgentManagement/",
		type : "post",
		data:{
			companyId:companyId,
			appCode:appCode,
			address:address
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				layer.closeAll('loading');
				swal({title:"保存失败",type:"error"});
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
			$('#myModal').hide();
			layer.closeAll('loading');
			swal({title:"保存成功",type:"success"});
			getListData(1,10);
		},
		error : function() {
			layer.closeAll('loading');
			swal({title:"保存失败",type:"error"});
		}
	});
}
function setAgentManagementById(id){
	layer.load(2);
	$.ajax({
		url : ctx +"/setAgentManagementById/",
		type : "post",
		data:{
			id:id
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				layer.closeAll('loading');
				swal({title:"查询失败",type:"error"});
				return;
			}
				$("#update_companyId").val(data.companyId);
				$("#update_appCode").val(data.appCode);
				$("#update_address").val(data.address);
				$("#updateId").val(data._id);
				layer.closeAll('loading');
		},
		error : function() {
			layer.closeAll('loading');
			swal({title:"查询失败",type:"error"});
		}
	});
}
function deleteAgentManagementById(id){
	if(!confirm("确认删除吗")){
		return;
	}
	layer.load(2);
	$.ajax({
		url : ctx +"/deleteAgentManagementById/",
		type : "post",
		data:{
			id:id
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				layer.closeAll('loading');
				swal({title:"删除失败",type:"error"});
				return;
			}
			layer.closeAll('loading');
			swal({title:"删除成功",type:"success"});
			getListData(1,10);
		},
		error : function() {
			layer.closeAll('loading');
			swal({title:"删除失败",type:"error"});
		}
	});
}

function updateAgentManagement(id){
	layer.load(2);
	var companyId = $("#update_companyId").val();
	var appCode = $("#update_appCode").val();
	var address = $("#update_address").val();
	var id = $("#updateId").val();
	$.ajax({
		url : ctx +"/updateAgentManagement/",
		type : "post",
		data:{
			id:id,
			companyId:companyId,
			appCode:appCode,
			address:address
		},
		cache : false,
		success : function(response) {
			var code = response.code;
			var data = response.data;
			if(code != 0){
				layer.closeAll('loading');
				swal({title:"查询失败",type:"error"});
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
			layer.closeAll('loading');
			swal({title:"修改成功",type:"success"});
			getListData(1,10);
		},
		error : function() {
			layer.closeAll('loading');
			swal({title:"修改失败",type:"error"});
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
		url : ctx +"/getAgentManagementList/",
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
				$("#pageDiv").hide();
				$("#listDiv").html("<div>暂无数据</div>");
				layer.closeAll('loading');
				swal({title:"查询失败",type:"error"});
				return;
			}
//				$("#inner").hide();
//				$("#outter").show();
			loadHtml(response);
			layer.closeAll('loading');
		},
		error : function() {
			$("#pageDiv").hide();
			$("#listDiv").html("<div>暂无数据</div>");
			layer.closeAll('loading');
			swal({title:"查询失败",type:"error"});
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
	html.push("<thead><tr><th>序号</th><th>企业标识</th><th>应用标识</th><th>代理地址</th><th>操作</th></tr></thead><tbody>");
	
	$(results).each(function(i,item){

		html.push("<tr>");
		html.push("<td><span class=\"line\">"+(i+1)+"</span></td>");
		html.push("<td><span class=\"line\">"+item.companyId+"</span></td>");
		html.push("<td>"+item.appCode+"</td>"); 
		html.push("<td><span class=\"line\">"+item.address+"</span></td>");
		html.push("<td>");
		html.push("<button type=\"button\" class=\"btn btn-sm btn-danger\" onclick=\"deleteAgentManagementById('"+item._id+"');\">删除</button>");
		html.push("<button type=\"button\" class=\"btn btn-sm btn-primary\" data-toggle=\"modal\" data-target=\"#myModal_update\" onclick=\"setAgentManagementById('"+item._id+"');\">修改</button>");
		html.push("</td>");
		html.push("</tr>");
	});
	
	html.push("</tbody></table></div></div></div></div>");
	$("#listDiv").html(html.join(""));
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
}