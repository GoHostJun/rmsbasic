<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>角色管理</title>
<script type="text/javascript" src="${ctx }/js/organize/role.js"></script>
</head>

<body>
	<div class="gg_div js_list">
		<h5>
			<i></i>角色列表
		</h5>
		<div class="gg_cont">
			<div class="gg_top_left fl">
				<input type="text" id="title" placeholder="请输入名称"> <input type="text" id="time_begin" class="input_time" placeholder="请选择开始时间"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"> 至
				<input type="text" id="time_end" class="input_time" placeholder="请选择终止时间"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});">
			</div>
			<div class="btn_div fr">
				<a href="javascript:showRoleList(1,10);" class="chaxun">查询</a> <a href="javascript:roleAddOpen();" class="new_btn">新增</a> 
			</div>
			<div class="clear"></div>
			<div id="table_body"></div>
			<%--<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th></th>
					<th class="i_td"><i></i><input type='checkbox' id='all' /></th>
					<th>名称</th>
					<th>别名</th>
					<th>创建人</th>
					<th>创建时间</th>
					<th>描述</th>
					<th>操作</th>
				</tr>
				<tbody id="table_body"></tbody>
			</table>
			--%><div id="pageDiv"></div>
		</div>

	</div>
</body>
</html>
