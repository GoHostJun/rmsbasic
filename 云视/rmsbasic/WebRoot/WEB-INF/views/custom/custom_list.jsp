<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE>
<html>
<head>

<title>客户管理</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/organize/custom.js"></script>
</head>

<body>
	<div class="gg_div js_list" style="margin-top: 0px;">
		<h5>
			<i></i>客户列表
		</h5>
		<div  class="gg_cont">
			<div class="gg_top_left fl">
				<input type="text" id="txtKeyWord" placeholder="请输入名称">
			</div>
			<div class="btn_div fr">
				<a href="javascript:showCustomList(1,10);" class="chaxun">查询</a> <a id="ele6" href="javascript:openAddCustom();" class="new_btn">新增</a> <a href="javascript:deleteCustoms();" class="shanchu">批量删除</a> 
			</div>
			<div class="clear"></div>
			<div id="table_body"></div>
			<div id="pageDiv"></div>
		</div>

	</div>
</body>

</html>
