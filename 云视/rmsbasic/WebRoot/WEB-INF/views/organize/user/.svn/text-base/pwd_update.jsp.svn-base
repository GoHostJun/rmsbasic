<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>修改密码</title>
<link rel="stylesheet" href="${ctx}/css/organize/user.css" type="text/css">
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/common/plugins/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${ctx}/js/organize/pwdupdate.js"></script>	
<style>
    .new-user-table tr{
    height:44px; 
    }
    .new-user-table tr.make-sure .save-btn, .new-user-table tr.make-sure .cancel-btn{
    margin-top:9px;
    }
</style>
</head>

<body>
	<table class="new-user-table">
		<tr>
			<td>新密码：</td>
			<td><input type="password" id="password" name="password" autofocus="autofocus" /><span style="color:red;">*</span></td>
		</tr>
		<tr>
			<td>确认新密码：</td>
			<td><input type="password" id="rPassword" name="rPassword" type="password"  autofocus="autofocus" /><span style="color:red;">*</span></td>
		</tr>
		<tr style="height:16px;" >
		<td></td>
		<td style="height:30px;"><span id = "passwordSpan" style="color:red;"></span></td>
		
		</tr>
		<tr class="make-sure">
			<td colspan="2"><a href="javascript:saveNewPwd();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a></td>
		</tr>
	</table>
</body>
</html>
