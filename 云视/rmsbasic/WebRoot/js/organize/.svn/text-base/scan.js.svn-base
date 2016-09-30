$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showScanList
	});
	showScanList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showScanList(1,10);
	     }
   	}; 
});

/**
 * 加载客户列表
 */
function showScanList(a,b) {
	var index = layer.load();
	var page;
	var pageNum;
	page=(a==undefined?$("#pageDiv").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#pageDiv").getOnairPageParameter().pageSize:b)
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("currentPage", page);
	onairMap.put("pageNum", pageNum);
//	onairMap.put("keyWord", $("#txtKeyWord").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "scan/findScanAll/",
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
					tableBodyData.push("<th></th>");
//					tableBodyData.push("<th class='i_td'><i></i><input type='checkbox' id='all' /></th>");
					tableBodyData.push("<th>扫描源目录</th>");
					tableBodyData.push("<th>扫描文件类型</th>");
					tableBodyData.push("<th>扫描人id</th>");
					tableBodyData.push("<th>扫描客户标识</th>");
					tableBodyData.push("<th>是否启用</th>");
					tableBodyData.push("<th>扫描类型</th>");
					tableBodyData.push("<th>扫描服务标识</th>");
					tableBodyData.push("<th>扫描服务应用标识</th>");
					tableBodyData.push("<th>注册地址</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
//					tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='custom_id' value="+item._id+"></td>");
					tableBodyData.push("<td>" + cutTitleString(toString(item.srcdic),5,"...") + "</td>");
					tableBodyData.push("<td>" +cutTitleString(toString(item.dictype),5,"...") + "</td>");
					tableBodyData.push("<td>" + cutTitleString(toString(item.taruserid),5,"...") + "</td>");
					tableBodyData.push("<td>" +cutTitleString( toString(item.companyid ),5,"...")+ "</td>");
					tableBodyData.push("<td>" + cutTitleString(toUsed(item.isused),5,"...") + "</td>");
					tableBodyData.push("<td>" +cutTitleString( toString(item.scantype),5,"...") + "</td>");
					tableBodyData.push("<td>" + cutTitleString(toString(item.tardic),5,"...") + "</td>");
					tableBodyData.push("<td>" + cutTitleString(toString(item.appcode),5,"...") + "</td>");
					tableBodyData.push("<td>" + cutTitleString(toString(item.url),5,"...") + "</td>");
					tableBodyData.push("<td><a href='javascript:openUpdateScan(\"" + item._id + "\");'>编辑</a><a href='javascript:deleteScan(\""
							+ item._id + "\");' class='td_del'>删除</a></td>");
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

function toUsed(isused){
	return toString(isused)==0?"停用":"启用"
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
function deleteScans() {

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
		        'url':ctx  + pathValue+'rms/custom/deleteCustomByIds/', //服务器的地址
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
function deleteScan(scanId) {

	 layer.confirm("确定要删除扫描吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("_id",scanId);
			onairMap.put("accessToken", getAccessToken());
			onairMap.put("timeStamp", getTimeStamp());
			 $.ajax({
		        'url':ctx  + pathValue+'scan/deleteScan/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除扫描成功！');
						layer.closeAll('page');
						showScanList();
					} else {
						layer.msg('删除扫描失败！');
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
function openAddScan (){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/scan_add",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增扫描",
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
function addScan() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("srcdic", $("#srcdic").val());
	onairMap.put("dictype", $("#dictype").val());
	onairMap.put("taruserid", $("#taruserid").val());
	onairMap.put("companyid", $("#companyid").val());
	onairMap.put("isused", $("[name=isused]:checked").val());
	onairMap.put("scantype", $("#scantype").val());
	onairMap.put("tardic", $("#tardic").val());
	onairMap.put("appcode", $("#appcode").val());
	onairMap.put("url", $("#url").val());
	onairMap.put("service", $("#service").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "scan/addScan/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加扫描成功！');
				layer.closeAll('page');
				showScanList();
			} else if(10005==data.code){
				layer.msg('输入参数有误！');
			}else {
				layer.msg('添加扫描失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
//弹出修改客户窗口
function openUpdateScan(scanId){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/scan_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改扫描", data);
			showScanInfo(scanId);
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
function showScanInfo(scanId) {
	var onairMap = new OnairHashMap();
	onairMap.put("_id", scanId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  +pathValue+ "scan/getScan/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var scan = data.data;
			$("#_id").val(scan._id)
			$("#scanid").val(scan.scanid)
			$("#dictype").val(scan.dictype);
			$("#srcdic").val(scan.srcdic);
			$("#taruserid").val(scan.taruserid);
			$("#companyid").val(scan.companyid);
			$("[name=isused][value="+scan.isused+"]").attr("checked","checked");
			$("#scantype").val(scan.scantype);
			$("#tardic").val(scan.tardic);
			$("#appcode").val(scan.appcode);
			$("#url").val(scan.url);
			$("#service").val(scan.service);
		}
	});
}
/**
 * 修改客户列表
 */
function updateScan(customId) {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("_id", $("#_id").val());
	onairMap.put("scanid", $("#scanid").val());
	onairMap.put("dictype", $("#dictype").val());
	onairMap.put("srcdic", $("#srcdic").val());
	onairMap.put("taruserid", $("#taruserid").val());
	onairMap.put("companyid", $("#companyid").val());
	onairMap.put("isused", $("[name=isused]:checked").val());
	onairMap.put("scantype", $("#scantype").val());
	onairMap.put("tardic", $("#tardic").val());
	onairMap.put("appcode", $("#appcode").val());
	onairMap.put("url", $("#url").val());
	onairMap.put("service", $("#service").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "scan/updateScan/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改扫描成功！');
				layer.closeAll('page');
				showScanList();
			}  else if(10005==data.code){
				layer.msg('输入参数有误！');
			}else {
				layer.msg('修改扫描失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
