<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>更新客户</title>
<link rel="stylesheet" href="${ctx}/css/organize/user.css" type="text/css">
</head>

<body>
<form autocomplete="off">
	<table class="new-user-table">
		<tr>
			<td>用户名：</td>
			<td><input type="text" id="userName" name="userName" autofocus="autofocus" disabled="disabled" /></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="password" id="passWord" name="passWord" autofocus="autofocus"  autocomplete="off" /></td>
		</tr>
		<tr>
			<td>真实姓名：</td>
			<td><input type="text" id="realName" name="realName" /></td>
		</tr>
		<tr>
			<td>客户标识：</td>
			<td><input type="text" id="companyId" name="companyId" disabled="disabled"/></td>
		</tr>
		<tr>
			<td>应用系统：</td>
			<td><input type="text" id="appCode" name="appCode" /></td>
		</tr>
		<tr>
			<td>服务：</td>
			<td><input type="text" id="serviceCode" name="serviceCode" /></td>
		</tr>
		<tr>
			<td>BSS密钥：</td>
			<td><input type="text" id="otherConfig" name="otherConfig" /><span id="notice_otherConfig">*</span></td>
		</tr>
		<tr>
			<td colspan="2"><a href="javascript:updateCustom();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a></td>
		</tr>
	</table>
	</form>
	<input type="hidden" value="" id="customId" name="customId">
</body>
</html>
