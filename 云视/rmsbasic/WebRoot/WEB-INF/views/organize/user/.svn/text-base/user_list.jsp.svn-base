<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>

<title>用户管理</title>
<script type="text/javascript" src="${ctx }/js/organize/user.js"></script>
</head>

<body>
	<div class="gg_div js_list">
		<h5>
			<i></i>用户列表
		</h5>
		<div  class="gg_cont">
			<div class="gg_top_left fl">
				<input type="text" id="txtKeyWord" placeholder="请输入名称">
			</div>
			<div class="btn_div fr">
				<a href="javascript:;" id="startUsers">启用</a> 
				<a href="javascript:;" id="stopUsers">停用</a>
				<a href="javascript:;" id="syncUsers">同步</a> 
				<a href="javascript:showUserList(1,10);" class="chaxun">查询</a> <a id="ele6" href="javascript:userAddOpen();" class="new_btn">新增</a> <a href="javascript:deleteUsers();" class="shanchu">批量删除</a> 
				<a href="javascript:resetPasswords();" class="chongshe">重置密码</a>
			</div>
			<div class="clear"></div>
			<div id="table_body"></div><%--
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th></th>
					<th class="i_td"><i></i><input type='checkbox' id='all' /></th>
					<th>名称</th>
					<th>性别</th>
					<th>状态</th>
					<th>所属角色</th>
					<th>所属部门</th>
					<th>真实姓名</th>
					<th>电子邮箱</th>
					<th>电话</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<tbody></tbody>
			</table>
			--%><div id="pageDiv"></div>
		</div>

	</div>
</body>
<script>
function aaa(){
//	alert($(window).height());
//	alert($(document).height());
//	alert(document.documentElement.clientHeight);
	layer.confirm('您是如何看待前端开发？', {
	    btn: ['重要','奇葩'] //按钮
	}, function(){
	    layer.msg('的确很重要', {icon: 1});
	}, function(){
	    layer.msg('也可以这样', {
	        time: 20000, //20s后自动关闭
	        btn: ['明白了', '知道了']
	    });
	});
}
</script>
</html>
