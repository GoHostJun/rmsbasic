<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
	<head>
	<title>推送路径管理</title>
	<script src="${ctx }/js/pushpath/pushpath_list.js" type="text/javascript" ></script>
	</head>
	<body>
		<div class="gg_div js_list">
			<h5>
				<i></i>推送路径列表
			</h5>
			<div  class="gg_cont">
				<div class="gg_top_left fl">
					<input type="text" id="pushnameword" placeholder="请输入名称">
				</div>
				<div class="btn_div fr">
					<a href="javascript:showPushPathList(1,10);" class="chaxun">查询</a> <a id="ele6" href="javascript:openAddPath();" class="new_btn">新增</a> 
					
				</div>
				<div class="clear"></div>
				<div id="table_body"></div>
				<div id="pageDiv"></div>
			</div>
		</div>
	</body>

</html>