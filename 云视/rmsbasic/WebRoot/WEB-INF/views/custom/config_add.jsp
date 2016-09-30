<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>添加配置项</title>
<link rel="stylesheet" href="${ctx}/css/organize/user.css" type="text/css">
</head>

<body>
	<form id="form" name="form">
		<table class="new-user-table">
			<tr>
				<td>globalKey：</td>
				<td><input type="text" id="globalKey" name="globalKey" autofocus="autofocus" /></td></td>
			</tr>
			<tr>
				<td>globalValue：</td>
				<td><input type="text" id="globalValue" name="globalValue"  /></td></td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:addConfig();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a></td>
			</tr>
		</table>
	</form>
</body>
</html>
