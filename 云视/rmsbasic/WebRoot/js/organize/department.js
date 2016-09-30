var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		removeTitle : "删除",
		renameTitle : "编辑",
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : showRenameBtn
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeEditName : beforeEditName,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onRemove : onRemove,
		onRename : onRename
	}
};

var zNodes = getDepartmentData();
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	return true;
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
	zTree.selectNode(treeNode);
	if (confirm("确认删除【" + treeNode.name + "】吗？")) {
		return removeDepartmentByAppCode(treeNode._id);
	} else {
		return false;
	}
}
function onRemove(e, treeId, treeNode) {
	showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() + " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
	if (newName.length == 0) {
		var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
		alert("节点名称不能为空。");
		setTimeout(function() {
			zTree.editName(treeNode)
		}, 10);
		return false;
	} else if (newName.length > 15) {
		alert("名称不能超过15个字。");
		return false;
	} else {
		if(treeNode.getParentNode()!=undefined){
			editDepartmentByAppCode(treeNode.id,treeNode._id, newName,treeNode.getParentNode().id);
		}else{
			addDepartmentByAppCode(treeNode.id, treeNode.getParentNode().id, newName);
		}
		//uContDiv('system/departmentManage/','div_system_content');
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime() + " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
}
function showRemoveBtn(treeId, treeNode) {
	return !treeNode.isParent && treeNode.name!="云报道;
}
function showRenameBtn(treeId, treeNode) {
	return true;
}
function showLog(str) {
	// if (!log)
	// log = $("#log");
	// log.append("<li class='" + className + "'>" + str + "</li>");
	// if (log.children("li").length > 8) {
	// log.get(0).removeChild(log.children("li")[0]);
	// }
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		var treeNodes;
	btn.bind("click", function() {
		var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
		var nodeId = Math.uuid(32);
		treeNodes = zTree.addNodes(treeNode, {
			id : nodeId,
			pId : treeNode.id,
			name : ""
		});
		if (treeNodes) {
			zTree.editName(treeNodes[0]);
		}
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDepartment");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}

/**
 * 添加到指定应用
 * 
 * @param newId
 * @param parentId
 * @param newName
 */
function addDepartmentByAppCode(newId, parentId, newName) {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", newId);
	onairMap.put("pId", parentId);
	onairMap.put("name", newName);
	var blnAdd = false;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/addDepartmentByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if (0 == data.code) {
				blnAdd = true;
			}
		}
	});
	return blnAdd;
}
/**
 * 修改部门的名称
 * @param _id
 * @param newName
 */
function editDepartmentByAppCode(id,_id,newName,parentId) {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("_id", _id);
	onairMap.put("id", id);
	onairMap.put("pId", parentId);
	onairMap.put("name", newName);
	var blnAdd = false;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/editDepartmentByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if (0 == data.code) {
				
			}
		}
	});
	//return blnAdd;
}
/**
 * 获取信息
 * 
 * @returns 信息
 */
function getDepartmentData() {
	var departmentData;
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/getOrgsByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			departmentData = data.data.results;
		}
	});
	return departmentData;
}

/**
 * 删除信息
 * 
 * @returns true:成功
 */
function removeDepartmentByAppCode(removeId) {
	var blnRemove = false;
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("id", removeId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/removeDepartmentByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if (0 == data.code) {
				blnRemove = true;
			} else {
				blnRemove = false;
			}
		}
	});
	return blnRemove;
}

$(document).ready(function() {
	$.fn.zTree.init($("#treeDepartment"), setting, zNodes);
	var treeObj = $.fn.zTree.getZTreeObj("treeDepartment");
	treeObj.expandAll(true);
});

function deptAddOpen(){
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=organize/department/department_add",
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
function addTopDept(){
	var index = layer.load();
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("name", $("#name").val());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/addDepartmentByAppCode/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				layer.msg('添加顶级成功！');
				layer.closeAll('page');
				uContDiv('system/toPage/?view=organize/department/department_manage','div_system_content');
			} else 	if (2 == data.code)  {
				layer.msg('名称重复');
			}else {
				layer.msg('添加顶级失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});

}
