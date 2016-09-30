<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>系统设置</title>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/system/system.js"></script>
</head>

<body>
	
	<div class="sys_cont">
		<div class="sys_cont_inner">
			<ul class="nav_ul">
				<li id="li_notice" style="display:none" <c:if test="${toModule == 'notice' }">class="active"</c:if>><a href="javascript:uCont('system/toSetting/?toModule=notice');">公告</a></li>
				<li id="li_transcode" style="display:none" <c:if test="${toModule == 'transcode' }">class="active"</c:if>><a href="javascript:uCont('system/toSetting/?toModule=transcode');">转码模块</a></li>
				<li id="li_logs" <c:if test="${toModule == 'logs' }">class="active"</c:if>><a href="javascript:uCont('system/toSetting/?toModule=logs');">日志</a></li>
				<li id="li_path" style="display:none" <c:if test="${toModule == 'path' }">class="active"</c:if>><a href="javascript:uCont('system/toSetting/?toModule=path');">路径</a></li>
				<li id="li_organize" style="display:none" <c:if test="${toModule == 'organize' }">class="active"</c:if>><a href="javascript:uCont('system/toSetting/?toModule=organize');">权限</a></li>
				<div class="clear"></div>
			</ul>
			<div class="path_nav fr" style="display: none;" id="path_id">
				<a id="push_path" style="display:none" href="javascript:uContDiv('system/toPage/?view=pushpath/pushpath_list','div_system_content');" class="yh_btn">推送路径</a> 
				<a id="directpass_path" style="display:none" href="javascript:uContDiv('system/toPage/?view=directpassingpath/directpassingpath_list','div_system_content');" class="yh_btn">直传路径</a> 
			</div>
			<div class="mini_nav fr" style="display: none;" id="quanxian_id"> 
				<a id="system_user" style="display:none" href="javascript:uContDiv('system/toPage/?view=organize/custom/custom_list','div_system_content');" class="yh_btn">客户管理</a> 
				<a id="system_area" style="display:none" href="javascript:uContDiv('system/toPage/?view=organize/area/area_list','div_system_content');" class="yh_btn">地区管理</a> 
				<a id="system_department" style="display:none" href="javascript:uContDiv('system/toPage/?view=organize/department/department_manage','div_system_content');" class="yh_btn">部门管理</a> 
				<a id="system_user2" style="display:none" href="javascript:uContDiv('system/toPage/?view=organize/user/user_list','div_system_content');" class="yh_btn">用户管理</a> 
				<a id="system_dep" style="display:none" href="javascript:uContDiv('system/toPage/?view=organize/role/role_list','div_system_content');" class="js_btn">角色管理</a>
			</div>
			<div id="div_system_content">
			
			</div>
			<input id="showDept_id" type="hidden" value="${showDept}">
			<input id="toModule_id" type="hidden" value="${toModule}">
			<input id="nomalUser_id" type="hidden" value="${nomalUser}">
			<input id="systemUser_id" type="hidden" value="${systemUser}">
		</div>
	</div>

</body>
</html>
