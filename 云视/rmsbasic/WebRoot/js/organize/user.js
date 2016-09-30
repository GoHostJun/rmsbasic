$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showUserList
	});
	showUserList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	        showUserList(1,10);
	     }
   	};
   	if(userRole=="管理员"){
		//重置样式
		$(".js_list").css("margin-top","0px");
		$("#startUsers").hide();
		$("#stopUsers").hide();
		$("#syncUsers").hide();
	}
   	$("#syncUsers").click(function(){
   		var index = layer.load();
   		var onairMap = new OnairHashMap();
   		onairMap.put("accessToken", getAccessToken());
   		onairMap.put("timeStamp", getTimeStamp());
   		$.ajax({
   			type : 'POST',
   			contentType : 'application/json',
   			url : ctx + pathValue + "org/syncUsers/",
   			data : onairMap.toJson(),
   			cache : false,
   			async : false,
   			success : function(data) {
   				layer.close(index);
   				if (0 == data.code) {
   					layer.msg('同步用户成功！');
   					showUserList(1,10);
   				} else {
   					layer.msg('同步用户失败！');
   				}
   			},
   			error : function() {
   				layer.msg('同步用户失败！');
   				layer.close(index);
   			}
   		});
   	})
   	
   	$("#startUsers").click(function(){
   		updateStatus(1)
   	})
   	$("#stopUsers").click(function(){
   		updateStatus(0)
   	})
});
/**
 * 批量更新用户状态
 * @param staus
 */
function updateStatus(staus){
		var onairMap = new OnairHashMap();
		var ids=[];
		$("[name=user_name]:checked").each(function(i,item){
			ids.push($(this).val());
		})
		if(ids.length==0){
			layer.msg('至少选择一条数据');
			return ;
		}
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("_id",ids);
		onairMap.put("status",staus);
		var index = layer.load();
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : ctx + pathValue + "org/updateUsersStatus/",
			data : onairMap.toJson(),
			cache : false,
			async : false,
			success : function(data) {
				layer.close(index);
				if (0 == data.code) {
					layer.msg('状态更新成功');
					showUserList(1,10);
				} else {
					layer.msg('状态更新失败！');
				}
			},
			error : function() {
				layer.msg('状态更新失败！');
				layer.close(index);
			}
		});
	
}

/**
 * 加载用户列表
 */
function showUserList(a,b) {
	var index = layer.load();
	var showDept = $("#showDept_id").val();
	if(showDept == 'show'){
		$("#areaId").show();
		$("#deptId").show();
	}
	var keyWord = $("#txtKeyWord").val();
	if (!keyWord) {
		keyWord = "";
	}
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	//var pagePar = $("#pageDiv").getOnairPageParameter();
	//	pageNow = pagePar.pageNow;
	//	pageSize = pagePar.pageSize;
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("keyWord", keyWord);
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getUsersByAppCode/",
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
				//$("#table_body").html("暂无数据...");
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
 * 拼接用户数据
 * 
 * @param data
 *            返回信息的用户数组
 * @returns
 */
function loadTableData(data) {
	var tableBodyData = [];
	if (0 < data.length) {
					tableBodyData.push("<table border='0' cellpadding='0' cellspacing='0'>");
					tableBodyData.push("<tr>");
					tableBodyData.push("<th></th>");
					tableBodyData.push("<th class='i_td'><i></i><input type='checkbox' id='all' /></th>");
					tableBodyData.push("<th>登录账户</th>");
					tableBodyData.push("<th>名称</th>");
					tableBodyData.push("<th>性别</th>");
					tableBodyData.push("<th>状态</th>");
					tableBodyData.push("<th>所属角色</th>");
					tableBodyData.push("<th>所属机构</th>");
					tableBodyData.push("<th>真实姓名</th>");
					tableBodyData.push("<th>电子邮箱</th>");
					tableBodyData.push("<th>电话</th>");
					tableBodyData.push("<th>创建时间</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='user_name' value="+item._id+" role='"+(item.role == undefined ? "" : getRoleNames(item.role))+"'></td>");
					tableBodyData.push("<td>" + (item.email==undefined?"":item.email )+ "</td>");
					tableBodyData.push("<td>" +( item.name==undefined?"":item.name ) + "</td>");
					tableBodyData.push("<td>" + (item.sex == 0 ? "女" : "男") + "</td>");
					tableBodyData.push("<td>" + (item.status == 1 ? "已启用" : "已停用") + "</td>");
					tableBodyData.push("<td>" + (item.role == undefined ? "" : getRoleNames(item.role)) + "</td>");
					tableBodyData.push("<td>" + (item.department == undefined ? "" : getDepartmentNames(item.department)) + "</td>");
					tableBodyData.push("<td>" + (item.realname==undefined?"":item.realname )+ "</td>");
					tableBodyData.push("<td>" + (item.emailaddress== undefined ?"":item.emailaddress) + "</td>");
					tableBodyData.push("<td>" +  (item.phone==undefined?"":item.phone ) + "</td>");
					tableBodyData.push("<td>" +(item.ctime==undefined?"":item.ctime ) + "</td>");
					tableBodyData.push("<td><a role='"+(item.role == undefined ? "" : getRoleNames(item.role))+"' href='javascript:' onclick='userUpdateOpen(this,\"" + item._id + "\");'>编辑</a><a href='javascript:;'onclick='userDelete(this,\""
							+ item._id + "\");' class='td_del'  role='"+(item.role == undefined ? "" : getRoleNames(item.role))+"'>删除</a></td>");
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

/**
 * 获取角色名称
 * 
 * @param roles
 * @returns
 */
function getRoleNames(roles) {
	var roleNames = [];
	if(roles&&roles.length>0){
		$(roles).each(function(i, item) {
			roleNames.push(processValue(item.name));
			roleNames.push(",");
		});
	}
	roleNames.pop();
	return roleNames.join("");
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
 * 获取部门名称
 * 
 * @param departments
 * @returns
 */
function getDepartmentNames(departments) {
	var departmentNames = [];
	if(departments&&departments.length>0){
		$(departments).each(function(i, item) {
			departmentNames.push(item.name);
			departmentNames.push(",");
		});
	}
	departmentNames.pop();
	return departmentNames.join("");
}

/**
 * 获取部门标识
 * 
 * @param departments
 * @returns
 */
function getDepartmentIds(departments) {
	var departmentIds = [];
	if(departments){
		$(departments).each(function(i, item) {
			departmentIds.push(item._id);
			departmentIds.push(",");
		});
		departmentIds.pop();
		return departmentIds.join("");
	}
	return "";
}


/**
 * 打开添加用户页面
 */
function userAddOpen() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/user/user_add",
		cache : false,
		async : false,
		success : function(data) {
			openPop("新增用户", data);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 新增用户
 */
function addUserToAppCode() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("txtUsername", $("#txtUsername").val());
	onairMap.put("txtEmpNo", $("#txtEmpNo").val());
	onairMap.put("selSex", $("#selSex").val());
	onairMap.put("selAreaCode", $("#selArea").val());
	onairMap.put("selAreaName", $("#selArea option:selected").text());
	onairMap.put("selRoles", $("#selRoles").val());
	onairMap.put("txtDepartments", $("#txtDepartments").val());
	onairMap.put("txtRealUsername", $("#txtRealUsername").val());
	onairMap.put("txtEmail", $("#txtEmail").val());
	onairMap.put("txtPhone", $("#txtPhone").val());
	onairMap.put("emailAddress", $("#emailAddress").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/addUserToAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加用户成功！');
				layer.closeAll('page');
				showUserList();
			} else {
				layer.msg('添加用户失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}

/**
 * 获取角色数据
 * 
 * @returns
 */
function getRolesData() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 100);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getRolesByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			$(data.data.results).each(function(i, item) {
				$("#selRoles").append("<option value='" + item._id + "'>" + item.name + "</option>");
			});
		}
	});
}

/**
 * 打开添加用户页面
 * 
 * @param userId
 */
function userUpdateOpen(This,userId) {
	if(userRole=="管理员"){
		
		if($(This).attr("role")=="管理员"){
			layer.msg('暂无编辑权限');
			return ;
		}
	}
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/user/user_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("更新用户", data);
			getAreasData();
			showUserInfo(userId);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 展示用户详情
 * 
 * @param userId
 */
function showUserInfo(userId) {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userId", userId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getUserInfoByUserId/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if(data.code==0){
				var user = data.data;
				$("#txtUsername").val(prossValue(user.name));
				$("#txtEmpNo").val(prossValue(user.empNo));
				$("#selSex").val(prossValue(user.sex));
				if(user.area&&user.area.code){
					$("#selArea").val(prossValue(user.area.code));
				}
				$("#selRoles").val(function() {
					var role = [];
					if(user.role){
						$(user.role).each(function(i, item) {
							role.push(item._id);
						});
						return role.join("");
					}
					return "";
				});
				$("#txtDepartments").val(getDepartmentIds(user.department));
				$("#txtDepartmentsName").val(getDepartmentNames(user.department));
				$("#txtRealUsername").val(prossValue(user.realname));
				$("#txtEmail").val(prossValue(user.email));
				$("#txtPhone").val(prossValue(user.phone));
				$("#userId").val(prossValue(user._id));
				$("#emailAddress").val(prossValue(user.emailaddress));
			}
		}
	});
}

function prossValue(obj){
	if(obj){
		return obj;
	}
	return "";
	
}

/**
 * 更新用户信息
 */
function updateUserToAppCode() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("txtUsername", $("#txtUsername").val());
	onairMap.put("txtEmpNo", $("#txtEmpNo").val());
	onairMap.put("selSex", $("#selSex").val());
	onairMap.put("selAreaCode", $("#selArea").val());
	onairMap.put("selAreaName", $("#selArea option:selected").text());
	onairMap.put("selRoles", $("#selRoles").val());
	onairMap.put("txtDepartments", $("#txtDepartments").val());
	onairMap.put("txtRealUsername", $("#txtRealUsername").val());
	onairMap.put("txtPhone", $("#txtPhone").val());
	onairMap.put("userId", $("#userId").val());
	onairMap.put("selStatus","1");
	onairMap.put("emailAddress", $("#emailAddress").val());

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/updateUserToAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('更新用户成功！');
				layer.closeAll('page');
				showUserList();
			} else {
				layer.msg('更新用户失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}

/**
 * 删除用户
 * 
 * @param userId
 */
function userDelete(This,userId) {
	if(userRole=="管理员"){
		if($(This).attr("role")=="管理员"){
			layer.msg('暂无删除权限');
			return ;
		}
	}
	layer.confirm("确定要删除用户吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		layer.close(index);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("userId", userId);
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : ctx + pathValue + "org/deleteUserToAppCode/",
			data : onairMap.toJson(),
			cache : false,
			async : false,
			success : function(data) {
				layer.close(index);
				if (0 == data.code) {
					layer.msg('删除用户成功！');
					layer.closeAll('page');
					showUserList();
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
 * 批量删除
 */
function deleteUsers(){
	var ids = [];
	var i=0;
	 $("[name = user_name]:checkbox").each(function () {
	     if ($(this).is(":checked")) {
	    	 if(userRole=="管理员"){
				if($(this).attr("role")=="管理员"){
					i++;
					return  ;
				}
	    	}
	     	ids.push($(this).attr("value"));
	     }
 	});
	 if(i>0){
		 layer.msg('暂无删除管理员权限');
		 return  ;
	 }
	 if(ids.length<1){
		 layer.msg('至少选择一个用户');
		 return  ;
	 }
 	layer.confirm("确定要批量删除用户吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		layer.close(index);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("userIds",ids);
		 $.ajax({
	        'url':ctx + pathValue + 'org/deleteUserByIds/', //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':onairMap.toJson(),
	        'success':function(data){
	        	layer.close(index);
	        	if (0 == data.code) {
					layer.msg('批量删除用户成功！');
					layer.closeAll('page');
					showUserList();
				} else {
					layer.msg('批量删除用户失败！');
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
 * 获取地区数据
 * 
 * @returns
 */
function getAreasData() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 100);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getAreasByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			$(data.data.results).each(function(i, item) {
				$("#selArea").append("<option value='" + item.code + "'>" + item.name + "</option>");
			});
		}
	});
}

/**
 * 重置密码
 */
function resetPasswords(){
	var ids = [];
	var i=0;
	 $("[name = user_name]:checkbox").each(function () {
     if ($(this).is(":checked")) {
    	 if(userRole=="管理员"){
				if($(this).attr("role")=="管理员"){
					i++;
					return  ;
				}
	    	}
     	ids.push($(this).attr("value"));
     }
 	});
	 if(i>0){
		 layer.msg('暂无重置管理员权限');
		 return  ;
	 }
	 if(ids.length<1){
		 layer.msg('至少选择一个用户');
		 return  ;
	 }
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userIds",ids);
	 $.ajax({
        'url':ctx + pathValue + 'org/resetPassword/', //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':onairMap.toJson(),
        'success':function(data){
        	layer.msg('重置密码成功！');
        	showUserList();
        },
        error : function() {
		}
    })
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
     $("input[name='user_name']").click(function () {
                $("input[name='user_name']:checked").length == $("input[name='user_name']").length ? $("#all").prop("checked", true) : $("#all").prop("checked", false);
     });

}