<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>云报道</title>
<script type="text/javascript" src="${ctx }/js/notice/noticeDetail.js"></script>
</head>

<body>
<p class="p_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:;">公告详情</a></p>

<div class="inner detail_cont" id="notice_detail_id">
</div>
</body>
</html>
<script>
$(function() {
	var noticeId = "<%=request.getParameter("id")%>";
	getNoticeDetail(noticeId);
});
</script>
