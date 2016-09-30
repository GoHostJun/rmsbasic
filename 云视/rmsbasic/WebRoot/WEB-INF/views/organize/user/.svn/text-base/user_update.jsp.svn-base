<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>更新用户</title>
<link rel="stylesheet" href="${ctx}/common/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/organize/user.css" type="text/css">
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
<script>
	var setting = {
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};

	var zNodes = getDepartmentData();

	function beforeClick(treeId, treeNode) {
		
		var check = (treeNode.level==0);
		/*if (check)
			alert("不能选择...");
		return !check;*/
	}

	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), v = "";
		vId = "";
		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		for ( var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			vId += nodes[i]._id + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		if (vId.length > 0)
			vId = vId.substring(0, vId.length - 1);
		console.info(v);
		console.info(vId);
		var cityObj = $("#txtDepartmentsName");
		var cityObjId = $("#txtDepartments");
		cityObj.val(v);
		cityObjId.val(vId);
	}

	function showMenu() {
		var cityObj = $("#txtDepartmentsName");
		var cityOffset = $("#txtDepartmentsName").offset();
		$("#menuContent").css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "txtDepartmentsName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
			hideMenu();
		}
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
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		getRolesData();
	});
</script>
</head>

<body>
	<table class="new-user-table">
		<tr>
			<td>名称：</td>
			<td><input type="text" id="txtUsername" name="txtUsername" autofocus="autofocus" /></td>
		</tr>
		<tr>
			<td>工号：</td>
			<td><input type="text" id="txtEmpNo" name="txtEmpNo" autofocus="autofocus" /></td>
		</tr>
		<tr>
			<td>性别：</td>
			<td><select id="selSex" name="selSex">
					<option value="1">男</option>
					<option value="0">女</option>
			</select>
			</td>
		</tr>
		<tr>
			<td>地区：</td>
			<td>
				<select id="selArea"  name="selArea">
					<option value="">--请选择--</option>
					<%--<option value="53">云南省</option>
					<option value="5301">昆明市</option>
					<option value="5334">迪庆州</option>
					<option value="5333">怒江州</option>
					<option value="5307">丽江市</option>
					<option value="5306">昭通市</option>
					<option value="5329">大理州</option>
					<option value="5305">保山市</option>
					<option value="5331">德宏州</option>
					<option value="5323">楚雄州</option>
					<option value="5303">曲靖市</option>
					<option value="5309">临沧市</option>
					<option value="5304">玉溪市</option>
					<option value="5308">思茅市</option>
					<option value="5325">红河州</option>
					<option value="5326">文山州</option>
					<option value="5328">西双版纳州</option>
				--%></select>
			</td>
		</tr>
		<tr>
			<td>角色：</td>
			<td>
				<select id="selRoles" name="selRoles">
					<option value="">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top;">部门：</td>
			<td><input type="hidden" id="txtDepartments" name="txtDepartments"> <input type="text" id="txtDepartmentsName"
				name="txtDepartmentsName" readonly="readonly" onfocus="showMenu(); return false;" onclick="showMenu(); return false;" />
				<div id="menuContent" class="menuContent" style="display: none;">
					<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
				</div>
			</td>
		</tr>
		<tr>
			<td>真实姓名：</td>
			<td><input type="text" id="txtRealUsername" name="txtRealUsername" /></td>
		</tr>
		<tr>
			<td>电子邮箱：</td>
			<td><input type="text" id="emailAddress" name="emailAddress" /></td>
		</tr>
		<tr>
			<td>电话：</td>
			<td><input type="text" id="txtPhone" name="txtPhone" /></td>
		</tr>
		<tr>
			<td colspan="2"><a href="javascript:updateUserToAppCode();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();"  class="cancel-btn"> 取 消 </a></td>
		</tr>
	</table>
	<input type="hidden" value="" id="userId" name="userId">
</body>
</html>
