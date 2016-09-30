<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE>
<html>
<head>

<title>任务管理</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/emegency/emegency.js"></script>

</head>

<body>
	<div class="gg_div js_list" style="margin-top: 0px;">
		<h5>
			<i></i>任务列表
		</h5>
		<div  class="gg_cont">
			<div class="gg_top_no">
				<input type="text" id="txtKeyWord" placeholder="请输入名称">
				<a href="javascript:getEmeListData(1,10);" class="chaxun">查询</a> 
			</div>
			<div class="gg_top_no">
				<input name="uri" placeholder="这里输入url地址" >
				<input name="param" placeholder="这里输入参数" >
				<a href="javascript:url();" class="chaxun">任务重试发送</a>  
			</div>
			<div class="gg_top_no">
				<input name="userId" placeholder="这里输入用户id" >
				<input name="file" placeholder="这里输入文件地址" >
				<input name="fileName" placeholder="这里输入文件名" >
				<a href="javascript:retry();" class="chaxun">素材重试发送</a>  
			</div>
			<div class="clear"></div>
			<div id="outter">
				<div id="table_body"></div>
				<div id="pageDiv"></div>
			</div>
		</div>
		<div id="inner">
		</div>
	</div>
</body>

</html>
