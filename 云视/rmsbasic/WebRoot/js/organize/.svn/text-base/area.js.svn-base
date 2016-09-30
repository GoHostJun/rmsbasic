$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showAreaList
	});
	showAreaList(1,10);
	document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode==13){ // enter 键
	       showAreaList(1,10);
	    }
   	}; 
});

/**
 * 加载地区列表
 */
function showAreaList(a,b) {
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
		url : ctx + pathValue + "org/getAreasByAppCode/",
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
 * 拼接地区数据
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
					tableBodyData.push("<th>编码</th>");
					tableBodyData.push("<th>电视台</th>");
					tableBodyData.push("<th>创建时间</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
			tableBodyData.push("<tr>");
			tableBodyData.push("<td>" + (i + 1) + "</td>");
			tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='area_name' value="+item._id+"></td>");
			tableBodyData.push("<td>" + toString(item.name) + "</td>");
			tableBodyData.push("<td>" + toString(item.code) + "</td>");
			tableBodyData.push("<td>" + toString(item.tvStation) + "</td>");
			tableBodyData.push("<td>" + toString(item.ctime) + "</td>");
			tableBodyData.push("<td><a href='javascript:areaUpdateOpen(\""+ item._id + "\");'>编辑</a><a href='javascript:areaDelete(\""+ item._id + "\");' class='td_del'>删除</a></td>");
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
 * 打开添加地区页面
 */
function areaAddOpen() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/area/area_add",
		cache : false,
		async : false,
		success : function(data) {
			openPop("新增地区", data);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}

/**
 * 新增地区
 */
function addAreaToAppCode() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("txtName", $("#txtName").val());
	onairMap.put("txtCode", $("#txtCode").val());
	onairMap.put("txtTVStation", $("#txtTVStation").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/addAreaToAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加地区成功！');
				layer.closeAll('page');
				showAreaList();
			} else {
				layer.msg('添加地区失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}

/**
 * 打开编辑地区页面
 */
function areaUpdateOpen(areaId) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/area/area_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("更新地区", data);
			showAreaInfo(areaId);
		},
		error : function() {
			$("#table_body").html("更新地区出错了...");
		}
	});
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
function areaDelete(areaId) {
	layer.confirm("确定要删除地区吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		layer.close(index);
		var index = layer.load();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("_id", areaId);
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : ctx + pathValue + "org/deleteAreaToAppCode/",
			data : onairMap.toJson(),
			cache : false,
			async : false,
			success : function(data) {
				layer.close(index);
				if (0 == data.code) {
					layer.msg('删除地区成功！');
					layer.closeAll('page');
					showAreaList();
				} else {
					layer.msg('删除地区失败！');
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
 * 区域编辑框内容展示
 * 
 * @param areaId
 */
function showAreaInfo(areaId) {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("areaId", areaId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getAreaInfoByAreaId/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var area = data.data;
			$("#areaId").val(area._id);
			$("#txtName").val(area.name);
			$("#txtCode").val(area.code);
			$("#txtTVStation").val(area.tvStation);
		}
	});
}

/**
 * 更新地区信息
 */
function updateAreaToAppCode() {
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("txtName", $("#txtName").val());
	onairMap.put("txtCode", $("#txtCode").val());
	onairMap.put("txtTVStation",$("#txtTVStation").val());
	onairMap.put("areaId", $("#areaId").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/updateAreaToAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('更新地区成功！');
				layer.closeAll('page');
				showAreaList();
			} else {
				layer.msg('更新地区失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
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
     $("input[name='area_name']").click(function () {
                $("input[name='area_name']:checked").length == $("input[name='area_name']").length ? $("#all").prop("checked", true) : $("#all").prop("checked", false);
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

 function toString(str){
	if(str == null || str == undefined || str == 'null'){
		str = '';
	}
	return str;
}