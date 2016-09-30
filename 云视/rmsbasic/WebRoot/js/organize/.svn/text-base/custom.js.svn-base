$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showCustomList
	});
	showCustomList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showCustomList(1,10);
	     }
   	}; 
});

/**
 * 加载客户列表
 */
function showCustomList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	onairMap.put("keyWord", $("#txtKeyWord").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/rms/custom/getAllCustom/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data.results));
				initSelectAll();
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
/**
 * 全选相关的操作
 */
function initSelectAll(){
	 //全选或全不选 
     $("#all").click(function(){  
        if(this.checked){  
            $("#table_body :checkbox").prop('checked',true);   
        }else{    
            $("#table_body :checkbox").prop('checked',false);
        }    
     }); 
     $("input[name='role_name']").click(function () {
                $("input[name='role_name']:checked").length == $("input[name='role_name']").length ? $("#all").prop("checked", true) : $("#all").prop("checked", false);
     });

}
function loadTableData(data){

	var tableBodyData = [];
	if (0 < data.length) {
					tableBodyData.push("<table border='0' cellpadding='0' cellspacing='0'>");
					tableBodyData.push("<tr>");
					tableBodyData.push("<th></th>");
					tableBodyData.push("<th class='i_td'><i></i><input type='checkbox' id='all' /></th>");
					tableBodyData.push("<th>用户名</th>");
					tableBodyData.push("<th>真实姓名</th>");
					tableBodyData.push("<th>客户标识</th>");
					tableBodyData.push("<th>应用系统</th>");
					tableBodyData.push("<th>服务</th>");
					tableBodyData.push("<th>其他配置项</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='custom_id' value="+item._id+"></td>");
					tableBodyData.push("<td>" + toString(item.userName) + "</td>");
					tableBodyData.push("<td>" +toString(item.realName) + "</td>");
					tableBodyData.push("<td>" + toString(item.companyId) + "</td>");
					tableBodyData.push("<td>" + toString(item.appCode )+ "</td>");
					tableBodyData.push("<td>" + toString(item.serviceCode) + "</td>");
					tableBodyData.push("<td>" + toString(item.otherConfig) + "</td>");
					if(toString(item.userName)!="system"){
						tableBodyData.push("<td><a href='javascript:openUpdateCustom(\"" + item._id + "\");'>编辑</a><a href='javascript:deleteCustom(\""
								+ item._id + "\");' class='td_del'>删除</a></td>");
					}else{
						tableBodyData.push("<td></td>")
					}
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

function toString(str){
	if(str!=undefined){
		return str;
	}else{
		return "";
	}
}

/**
 * 批量删除客户
 */
function deleteCustoms() {

	var ids = [];
	 $("[name = custom_id]:checkbox").each(function () {
    if ($(this).is(":checked")) {
    	ids.push($(this).attr("value"));
    }
	});
	 if(ids.length<1){
		 layer.msg('至少选择一条数据');
		 return ;
	 }
	 layer.confirm("确定要批量删除客户吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("customIds",ids);
			 $.ajax({
		        'url':ctx  + '/rms/custom/deleteCustomByIds/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('批量删除用户成功！');
						layer.closeAll('page');
						showCustomList();
					} else {
						layer.msg('删除用户失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}

/**
 * 删除客户
 */
function deleteCustom(customId) {

	 layer.confirm("确定要删除客户吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("customId",customId);
			 $.ajax({
		        'url':ctx  + '/rms/custom/deleteCustomById/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除用户成功！');
						layer.closeAll('page');
						showCustomList();
					} else {
						layer.msg('删除用户失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}
//弹出新增客户窗口
function openAddCustom(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/custom_add",
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
/**
 * 新增客户
 */
function addCustom() {
	if (!$.trim($("#userName").val())) {
		$("#notice_userName").html("用户名不能为空");
		$("#userName").focus();
		return;
	}
	if (!$.trim($("#companyId").val())) {
		$("#notice_companyId").html("客户标识不能为空");
		$("#companyId").focus();
		return;
	}
	if (!$.trim($("#otherConfig").val())) {
		$("#notice_otherConfig").html("BSS密钥不能为空");
		$("#otherConfig").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	
	onairMap.put("realName", $("#realName").val());
	onairMap.put("passWord", $("#passWord").val());
	onairMap.put("userName", $("#userName").val());
	onairMap.put("companyId", $("#companyId").val());
	onairMap.put("appCode", $("#appCode").val());
	onairMap.put("serviceCode", $("#serviceCode").val());
	onairMap.put("otherConfig", $("#otherConfig").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/rms/custom/addCustom/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加客户成功！');
				layer.closeAll('page');
				showCustomList();
			} else if(10007 == data.code){
				layer.msg('用户名或者客户标识重复');
			}else {
				layer.msg('添加客户失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
//弹出修改客户窗口
function openUpdateCustom(customId){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/custom_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改客户", data);
			showCustomInfo(customId);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * 展示客户详情
 * 
 * @param userId
 */
function showCustomInfo(customId) {
	var onairMap = new OnairHashMap();
	onairMap.put("customId", customId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  + "/rms/custom/getCustomById/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var custom = data.data;
			$("#customId").val(custom._id)
			$("#realName").val(custom.realName);
			$("#passWord").val(custom.passWord);
			$("#userName").val(custom.userName);
			$("#companyId").val(custom.companyId);
			$("#appCode").val(custom.appCode);
			$("#serviceCode").val(custom.serviceCode);
			$("#otherConfig").val(custom.otherConfig);
		}
	});
}
/**
 * 修改客户列表
 */
function updateCustom(customId) {
	if (!$.trim($("#userName").val())) {
		$("#notice_userName").html("用户名不能为空");
		$("#userName").focus();
		return;
	}
	if (!$.trim($("#companyId").val())) {
		$("#notice_companyId").html("客户标识不能为空");
		$("#companyId").focus();
		return;
	}
	if (!$.trim($("#otherConfig").val())) {
		$("#notice_otherConfig").html("BSS密钥不能为空");
		$("#otherConfig").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("customId", $("#customId").val());
	onairMap.put("realName", $("#realName").val());
	onairMap.put("passWord", $("#passWord").val());
	onairMap.put("userName", $("#userName").val());
	onairMap.put("companyId", $("#companyId").val());
	onairMap.put("appCode", $("#appCode").val());
	onairMap.put("serviceCode", $("#serviceCode").val());
	onairMap.put("otherConfig", $("#otherConfig").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +  "/rms/custom/updateCustom/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改客户成功！');
				layer.closeAll('page');
				showCustomList();
			} else {
				layer.msg('修改客户失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
