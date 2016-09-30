<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/newsMessage/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/newsMessage/css/my_task.css"/>
		<script type="text/javascript" src="${ctx }/common/js/jquery.nicescroll.js"></script>
		 <script src="${ctx }/common/plugins/newsMessage/js/my_task.js" type="text/javascript"></script>
		 <script src="${ctx }/common/plugins/newsMessage/js/common.js" type="text/javascript"></script>
		<title></title>
	</head>
	<body>
		<div class="my-task mh_div">
			<h3>
				<p>
					<b>我的任务</b><br />
					<span>MY TASKS</span>
				</p>
				<a href="${ctx}/index.jsp" style="cursor:pointer;" target="_blank">More >></a>
		    </h3>
			<div class="task-cont">
				<ul class="task-cont-ul">
					<a href="#"><li id="wait_to_push_li" onclick="to_push_list();" style="cursor:pointer;">待推送</li></a>
					<a href="#"><li id="wait_to_audit_li" onclick="to_audit_list();" style="cursor:pointer;">待审核</li></a>
					<a href="#"><li onclick="to_publish_list();" style="cursor:pointer;">我发布</li></a>
					<a href="#"><li onclick="to_complete_list();" style="cursor:pointer;">已完成</li></a>
				</ul>
				<div class="my-dynamic" id="my-dynamic">
					
				</div>
			</div>
		</div>
	</body>
</html>
