$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showRoleList
	});
	showRoleList(1,10);
	document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode==13){ // enter 键
	       showRoleList(1,10);
	    }
   	}; 
});

/**
 * 加载角色列表
 */
function showRoleList(a,b) {
	var keyWord = $("#title").val();
	if (!keyWord) {
		keyWord = "";
	}
	var startTime = $("#time_begin").val();
	if (!startTime) {
		startTime = "";
	}
	var endTime = $("#time_end").val();
	if (!endTime) {
		endTime = "";
	}
	if(!checkEndTime(startTime,endTime)){
		return;
	}
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
//	var pagePar = $("#pageDiv").getOnairPageParameter();
//	pageNow = pagePar.pageNow;
//	pageSize = pagePar.pageSize;
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("keyWord", keyWord);
	onairMap.put("startTime", startTime);
	onairMap.put("endTime", endTime);
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getRolesByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if (0 == data.code) {
				$("#table_body").html(loadTableData(data.data.results));
				initSelectAll();
			} else {
				$("#table_body").html(loadTableData(data.data.results));
			//	$("#table_body").html("暂无数据...");
			}
			$("#pageDiv").resetOnairPageParameter(data.data.totalRecord, data.data.currentPage);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 拼接角色数据
 * 
 * @param data
 * @returns
 */
function loadTableData(data) {
	var tableBodyData = [];
	if (0 < data.length) {
					tableBodyData.push("<table border='0' cellpadding='0' cellspacing='0'>");
					tableBodyData.push("<tr>");
					tableBodyData.push("<th></th>");
					tableBodyData.push("<th class='i_td'><i></i><input type='checkbox' id='all' /></th>");
					tableBodyData.push("<th>名称</th>");
					tableBodyData.push("<th>别名</th>");
					tableBodyData.push("<th>创建人</th>");
					tableBodyData.push("<th>创建时间</th>");
					tableBodyData.push("<th>描述</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
			tableBodyData.push("<tr>");
			tableBodyData.push("<td>" + (i + 1) + "</td>");
			tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='role_name' value="+item._id+"></td>");
			tableBodyData.push("<td>" + item.name + "</td>");
			tableBodyData.push("<td>" + item.alias + "</td>");
			tableBodyData.push("<td>" + item.cusename + "</td>");
			tableBodyData.push("<td>" + item.ctime + "</td>");
			tableBodyData.push("<td>" + item.desc + "</td>");
			tableBodyData.push("<td><a href='javascript:;' style='color:#fefefe;cursor:default; background:#bbb;'>编辑</a><a href='javascript:roleDelete(\""+ item._id + "\");' class='td_del'>删除</a></td>");
			tableBodyData.push("</tr>");
		});
			tableBodyData.push("</tbody>");
			tableBodyData.push("</table>");
		$("#pageDiv").show();
	} else {
		$("#pageDiv").hide();
		tableBodyData.push("<div class='no_data'></div>");
	//	tableBodyData = [ "<tr><td colspan='12'><br/><center>暂无数据，马上<a href='javascript:roleAddOpen();' class='default_a' style='color:blue;'><b>添加角色</b></a>。</center><br/></td></tr>" ];
	}
	return tableBodyData.join("");
}

/**
 * 打开添加角色页面
 */
function roleAddOpen() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/role/role_add",
		cache : false,
		async : false,
		success : function(data) {
			openPop("新增角色", data);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 新增角色
 */
function addRoleToAppCode() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("txtRolename", $("#txtRolename").val());
	onairMap.put("txtRolealias", $("#txtRolealias").val());
	onairMap.put("txtRoleDesc", $("#txtRoleDesc").text($("#txtRoleDesc").val()).html());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/addRoleToAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加角色成功！');
				layer.closeAll('page');
				showRoleList();
			} else {
				layer.msg('添加角色失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}

/**
 * 打开编辑角色页面
 */
function roleUpdateOpen(roleId) {
//	$.ajax({
//		type : 'POST',
//		contentType : 'application/json',
//		url : ctx + pathPage + "system/toPage/?view=organize/role/user_update",
//		cache : false,
//		async : false,
//		success : function(data) {
//			openPop("更新角色", data);
//		},
//		error : function() {
//			$("#table_body").html("更新角色出错了...");
//		}
//	});
}

/**
 * 展示角色详情
 * 
 * @param userId
 */
function showRoleInfo(roleId) {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("roleId", roleId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getUserInfoByUserId/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var user = data.data;
			$("#txtUsername").val(user.name);
			$("#selSex").val(user.sex);
			$("#selStatus").val(user.status);
			$("#selArea").val(user.area.code)
			$("#selRoles").val(function() {
				var role = [];
				$(user.role).each(function(i, item) {
					role.push(item._id);
				});
				return role.join("");
			})
			$("#txtDepartments").val(getDepartmentIds(user.department));
			$("#txtDepartmentsName").val(getDepartmentNames(user.department));
			$("#txtRealUsername").val(user.realname);
			$("#txtEmail").val(user.email);
			$("#txtPhone").val(user.phone);
			$("#userId").val(user._id);
		}
	});
}

/**
 * 删除角色
 * 
 * @param roleId
 */
function roleDelete(roleId) {
	layer.confirm("确定要删除角色吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		layer.close(index);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("_id", roleId);
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : ctx + pathValue + "org/deleteRoleToAppCode/",
			data : onairMap.toJson(),
			cache : false,
			async : false,
			success : function(data) {
				layer.close(index);
				if (0 == data.code) {
					layer.msg('删除用户成功！');
					layer.closeAll('page');
					showRoleList();
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

function checkEndTime(time_begin,time_end){
	if(time_begin != "" && time_end != ""){
		 var start=new Date(time_begin.replace("-", "/").replace("-", "/"));  
   		 var end=new Date(time_end.replace("-", "/").replace("-", "/"));  
   		  if(end<start){  
   		  	 toastr.warning("时间输入错误!");
        	 return false;
    	  } 
	}
	return true;
}  