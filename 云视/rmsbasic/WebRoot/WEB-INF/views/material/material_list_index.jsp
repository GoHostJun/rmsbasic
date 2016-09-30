<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<script type="text/javascript" >
var type="<%=request.getParameter("type")%>";
</script>
<script type="text/javascript" src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js"></script>
<%@include file="/common/pages/header.jsp"%>
<%@include file="/common/pages/nav.jsp"%>
<title>云报道</title>
<div id="content">

<div class="pop">
    <div class="up_load">
        <p><a id="uploadFile" href="#">上传素材</a></p>
    </div>
</div>
</div>
<%@include file="/common/pages/footer.jsp"%>
<%@include file="/common/plugins/gotop/gotop.jsp"%>
<script>
	init();
	uCont("material/toMaterial/?type="+type);
</script>