<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>添加顶级部门</title>
<link rel="stylesheet" href="${ctx}/css/organize/area.css" type="text/css">
</head>

<body>
	<table class="new-role">
		<tr>
			<td class="inner-area">名称：</td>
			<td><input type="text" id="name" name="name" autofocus="autofocus"/></td>
		</tr>
		<tr>
			<td colspan="2"><a href="javascript:addTopDept();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a></td>
		</tr>
	</table>
</body>
</html>
