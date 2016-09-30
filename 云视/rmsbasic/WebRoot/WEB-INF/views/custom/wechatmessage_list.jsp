<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE>
<html>
<head>

<title>微信消息管理</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/organize/wechatmessage.js"></script>
</head>

<body>
	<div class="gg_div js_list" style="margin-top: 0px;">
		<h5>
			<i></i>微信消息列表
		</h5>
		<div  class="gg_cont">
			<div class="gg_top_left fl">
				<input type="text" id="txtKeyWord" placeholder="请输入用户名">
				<input type="text" id="consumerid" placeholder="请输入客户标识">
				<input type="text" id="status" placeholder="请输入状态0成功、1失败">
				<a href="https://mp.weixin.qq.com/wiki/17/fa4e1434e57290788bde25603fa2fcbd.html" target="_blank">微信全局返回码说明</a>
			</div>
			<div class="btn_div fr">
				<a href="javascript:showWeChatMessageList(1,10);" class="chaxun">查询</a> 
			</div>
			<div class="clear"></div>
			<div id="table_body"></div>
			<div id="pageDiv"></div>
		</div>

	</div>
</body>

</html>
