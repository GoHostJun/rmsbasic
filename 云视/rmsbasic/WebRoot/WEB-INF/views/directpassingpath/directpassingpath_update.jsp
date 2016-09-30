<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="${ctx}/css/organize/user.css" type="text/css">
<script src="${ctx }/js/directpassingpath/directpassingpath_update.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx }/js/directpassingpath/directpassingpath_view.js" type="text/javascript" charset="utf-8"></script>
	<table class="new-user-table">
	<input type="hidden" id="_id" name="_id">
		<tr>
			<td>目标路径名称：</td>
			<td><input type="text" id="tarpathname" name="tarpathname"  /><span id="tarpath_name">*</span></td>
		</tr>
		<tr>
			<td>目标路径ID：</td>
			<td><input type="text" id="tarpathid" name="tarpathid"  /><span id="tarpath_id">*</span></td>
		</tr>
		<tr>
			<td>目标路径地址：</td>
			<td><input type="text" id="tarpathaddr" name="tarpathaddr" /><span id="tarpath_addr">*</span></td>
		</tr>
		<tr>
			<td>推送路径唯一标识：</td>
			<td><input type="text" id="pushpathname" name="pushpathname" /></td>
		</tr>
		<tr>
			<td>推送路径地址：</td>
			<td><input type="text" id="pushpathaddr" name="pushpathaddr" /></td>
		</tr>
		<tr>
			<td>其它信息：</td>
			<td><textarea id=othermsg style="width: 270px;    padding-left: 10px;"></textarea><td>
		</tr>
		<tr>
			<td colspan="2"><a href="javascript:updatedirectpassingpath();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a></td>
		</tr>
	</table>
