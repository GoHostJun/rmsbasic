<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>权限管理</title>
<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
</head>

<body>
	<p class="p_url">
		<i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:;">系统设置</a> > <a href="javascript:;">权限管理</a>
	</p>
	<div class="inner">
		<div class="btn_div fr">
			<a href="javascript:uContDiv('system/departmentManage/','div_system_content');" class="btn btn_green" id="a_departmentManage">机构管理</a>
			<a href="javascript:;" class="btn btn_green">角色管理</a>
			<a href="javascript:;" class="btn btn_org org1">用户管理</a>
		</div>
		<div class="clear"></div>
		<div id="div_system_content"></div>
	</div>
</body>
</html>
<script>
$(function(){
	uContDiv('system/departmentManage/','div_system_content');
});
</script>