<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>机构管理 - 权限管理</title>
<link rel="stylesheet" href="${ctx}/common/plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/organize/uuid.js"></script>
<script type="text/javascript" src="${ctx}/js/organize/department.js"></script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
</HEAD>

<BODY>
	<div class="gg_div js_list">
		<h5>
			<i></i>部门列表
		</h5>
		<div  class="gg_cont">
			<div class="content_wrap">
				<div class="ztreeDepartmentBackground left">
					<div class="btn_div fr">
						<a href="javascript:deptAddOpen();" class="new_btn">新增顶级部门</a> 
					</div>
					<ul id="treeDepartment" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
</BODY>
</HTML>
