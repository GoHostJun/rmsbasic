$(document).ready(function() {
	$("#pageDiv").onairPage({
		"callback" : showMaterialTemplateList
	});
	showMaterialTemplateList(1,10);
	document.onkeydown=function(event){
	     var e = event || window.event || arguments.callee.caller.arguments[0];
	     if(e && e.keyCode==13){ // enter 键
	    	 showMaterialTemplateList(1,10);
	     }
   	}; 
});

/**
 * 加载过滤模板列表
 */
function showMaterialTemplateList(a,b) {
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
		url : ctx +pathValue+  "directpassingpath/findAllMaterialTemplate/",
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
					tableBodyData.push("<th>模板名称</th>");
					tableBodyData.push("<th>视频码率</th>");
					tableBodyData.push("<th>视频编码格式</th>");
					tableBodyData.push("<th>视频帧率</th>");
					tableBodyData.push("<th>封装格式</th>");
					tableBodyData.push("<th>音频格式</th>");
					tableBodyData.push("<th>分辨率</th>");
					tableBodyData.push("<th>所属商</th>");
					tableBodyData.push("<th>是否启用</th>");
					tableBodyData.push("<th>操作</th>");
					tableBodyData.push("</tr>");
					tableBodyData.push("<tbody>");
		$(data).each(function(i, item) {
					tableBodyData.push("<tr>");
					tableBodyData.push("<td>" + (i + 1) + "</td>");
					tableBodyData.push("<td class='i_td'><i></i><input type='checkbox' name='materialtemplate_id' value="+item._id+"></td>");
					tableBodyData.push("<td>" + toString(item.name) + "</td>");
					tableBodyData.push("<td>" +toString(item.rate) + "</td>");
					tableBodyData.push("<td>" + toString(item.type) + "</td>");
					tableBodyData.push("<td>" + toString(item.vframe )+ "</td>");
					tableBodyData.push("<td>" + toString(item.fmt) + "</td>");
					tableBodyData.push("<td>" + toString(item.aframe) + "</td>");
					tableBodyData.push("<td>" + toString(item.proportion) + "</td>");
					tableBodyData.push("<td>" + toString(item.companyid) + "</td>");
					tableBodyData.push("<td>" + (toString(item.isused)=="0"?"启用":"停用") + "</td>");
					if(toString(item.userName)!="system"){
						tableBodyData.push("<td><a href='javascript:openUpdateMaterialTemplate(\"" + item._id + "\");'>编辑</a><a href='javascript:deleteMaterialTemplate(\""
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
 * 批量删除
 */
function deleteMaterialTemplates() {

	var ids = [];
	 $("[name = materialtemplate_id]:checkbox").each(function () {
    if ($(this).is(":checked")) {
    	ids.push($(this).attr("value"));
    }
	});
	 if(ids.length<1){
		 layer.msg('至少选择一条数据');
		 return ;
	 }
	 layer.confirm("确定要批量删除过滤模板吗？", {
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
 * 删除
 */
function deleteMaterialTemplate(customId) {

	 layer.confirm("确定要删除过滤模板吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var index = layer.load();
			var onairMap = new OnairHashMap();
			onairMap.put("_id",customId);
			 $.ajax({
		        'url':ctx  +pathValue+ 'directpassingpath/deleteMaterialTemplate/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	layer.close(index);
		        	if (0 == data.code) {
						layer.msg('删除过滤模板成功！');
						layer.closeAll('page');
						showMaterialTemplateList();
					} else {
						layer.msg('删除过滤模板失败！');
					}
		        },
		        error : function() {
		        	layer.close(index);
					layer.msg('请求出错！');
				}
		    });
		  });
}
//弹出新增窗口
function openAddMaterialTemplate(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/materialtemplate_add",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['600px'],
				title : "新增过滤模板",
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
 * 新增
 */
function addMaterialTemplate() {
	var onairMap = new OnairHashMap();
	onairMap.put("name", $("#name").val());
	onairMap.put("rate", $("#rate").val());
	onairMap.put("type", $("#type").val());
	onairMap.put("vframe", $("#vframe").val());
	onairMap.put("fmt", $("#fmt").val());
	onairMap.put("aframe", $("#aframe").val());
	onairMap.put("height", $("#height").val());
	onairMap.put("width", $("#width").val());
	onairMap.put("proportion", $("#proportion").val());
	onairMap.put("companyid", $("#companyid").val());
	onairMap.put("desc", $("#desc").val());
	onairMap.put("isused", $("[name=isused]:checked").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "directpassingpath/addMaterialTemplate/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加成功！');
				layer.closeAll('page');
				showMaterialTemplateList();
			}else {
				layer.msg('添加失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
//弹出修改窗口
function openUpdateMaterialTemplate(customId){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=custom/materialtemplate_update",
		cache : false,
		async : false,
		success : function(data) {
			openPop("修改", data);
			showMaterialTemplate(customId);
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
}
/**
 * 展示详情
 * 
 * @param userId
 */
function showMaterialTemplate(id) {
	var onairMap = new OnairHashMap();
	onairMap.put("_id", id);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  +pathValue+ "directpassingpath/findOneMaterialTemplate/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var custom = data.data;
			$("#_id").val(custom._id)
			$("#name").val(custom.name)
			$("#desc").val(custom.desc)
			$("#rate").val(custom.rate)
			$("#type").val(custom.type)
			$("#vframe").val(custom.vframe)
			$("#fmt").val(custom.fmt)
			$("#aframe").val(custom.aframe)
			$("#height").val(custom.height)
			$("#width").val(custom.width)
			$("#proportion").val(custom.proportion)
			$("#companyid").val(custom.companyid)
			$("[name=isused]").each(function(){
				if($(this).val()==custom.isused){
					$(this).attr("checked",true);
				}
			})
			
		}
	});
}
/**
 * 修改
 */
function updateMaterialTemplate() {
	var onairMap = new OnairHashMap();
	onairMap.put("_id", $("#_id").val());
	onairMap.put("name", $("#name").val());
	onairMap.put("rate", $("#rate").val());
	onairMap.put("type", $("#type").val());
	onairMap.put("vframe", $("#vframe").val());
	onairMap.put("fmt", $("#fmt").val());
	onairMap.put("aframe", $("#aframe").val());
	onairMap.put("height", $("#height").val());
	onairMap.put("width", $("#width").val());
	onairMap.put("proportion", $("#proportion").val());
	onairMap.put("companyid", $("#companyid").val());
	onairMap.put("desc", $("#desc").val());
	onairMap.put("isused", $("[name=isused]:checked").val());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue+ "directpassingpath/updateMaterialTemplate/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('修改成功！');
				layer.closeAll('page');
				showMaterialTemplateList();
			} else {
				layer.msg('修改失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}
