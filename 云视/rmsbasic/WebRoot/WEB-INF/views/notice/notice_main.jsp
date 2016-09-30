<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
	
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
	
	</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
<script>
var noticeTypeId="<%=request.getParameter("noticeTypeId")%>";
	$(function() {
		init();
		uCont("notice/toNoticeList/?noticeTypeId="+noticeTypeId);
	});
</script>