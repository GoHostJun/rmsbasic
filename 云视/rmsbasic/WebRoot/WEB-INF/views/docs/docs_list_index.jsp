<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<script type="text/javascript" src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<%@include file="/common/pages/header.jsp"%>
<%@include file="/common/pages/nav.jsp"%>
<title>云报道</title>
<div id="content">

</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
<script>
	init();
	uCont('material/toDocs/');
</script>