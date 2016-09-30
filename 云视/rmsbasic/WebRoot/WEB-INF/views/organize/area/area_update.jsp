<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>更新地区</title>
<link rel="stylesheet" href="${ctx}/css/organize/area.css" type="text/css">
</head>
<body>
	<table class="new-role">
		<tr>
			<td class="inner-area">名称：</td>
			<td><input type="text" id="txtName" name="txtName" autofocus="autofocus"/></td>
		</tr>
		<tr>
			<td class="inner-area">编码：</td>
			<td><input type="text" id="txtCode" name="txtCode"/>
			</td>
		</tr>
		<tr>
			<td class="inner-area">电视台：</td>
			<td><input type="text" id="txtTVStation" name="txtTVStation"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"><a href="javascript:updateAreaToAppCode();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a></td>
		</tr>
	</table>
	<input type="hidden" value="" id="areaId" name="areaId">
</body>

</html>
