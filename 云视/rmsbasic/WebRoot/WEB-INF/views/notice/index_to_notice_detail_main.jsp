<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/common/commonjscss.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
</head>
<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
	
	</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
	</body>
	</html>
	<script>
		$(function() {
			var noticeId = "<%=request.getParameter("id")%>";
			init();
			uCont("notice/indexToNoticeDetail/?id="+noticeId);
		});
	</script>