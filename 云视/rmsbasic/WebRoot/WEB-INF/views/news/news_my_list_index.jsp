<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE html>
<html>
<script type="text/javascript"src="${ctx }/common/plugins/upload/js/upload.js"></script>

<head>
<title>云报道</title>

</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content"></div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
	<!-- 	右侧浮动标签-开始 -->
<!-- 	<div class="pop"> -->
<!-- 		<div class="up_load"> -->
<!-- 			<p> -->
<!-- 				<a href="javascript:upload_open();">上传素材</a> -->
<!-- 			</p> -->
<!-- 		</div> -->
<!-- 		<div class="new_draft"> -->
<!-- 			<p> -->
<!-- 				<a href="wengao_new.html" target="_blank">新建文稿</a> -->
<!-- 			</p> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!-- 	右侧浮动标签-结束 -->
</body>
</html>
<script>
var _id="<%=request.getParameter("_id")%>";
var type="<%=request.getParameter("type")%>";
	$(function() {
		// something
		init();
		uCont("news/toNewsMyCreateList/?_id="+_id);
		getMyNewsCommentList();
	});
</script>
