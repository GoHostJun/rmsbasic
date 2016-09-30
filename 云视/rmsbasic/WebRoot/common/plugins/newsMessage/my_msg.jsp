<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/newsMessage/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/newsMessage/css/my_msg.css"/>
		<script src="${ctx }/common/plugins/newsMessage/js/my_msg.js" type="text/javascript"></script>
		<script src="${ctx }/common/plugins/newsMessage/js/common.js" type="text/javascript"></script>
		<title></title>
	</head>
	<body>
		<div class="my-msg mh_div">
			<h3>
				<p>
					<b>我的信息</b><br />
					<span>MY MESSAGE</span>
				</p>
				<a href="${ctx}/index.jsp"  style="cursor:pointer;" target="_blank">More >></a>
		    </h3>
			<div class="msg-list" id="msg-list">
		</div>
		</div>
		
	</body>
</html>
