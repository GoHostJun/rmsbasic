<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<%
String ids = request.getParameter("ids");
%>
<script type="text/javascript">
	var ids ="<%=ids %>";
</script>
<script type="text/javascript"src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js" ></script>
<script type="text/javascript" src="${ctx }/flash/fastcode/fastcode.js"></script>
<head>
<title>云报道</title>

</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<input type="hidden" id="ids" value="<%=ids %>" />
	<script>
	
	</script>
	<div id="content">
	<p class="p_url material_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toMaterial/');">素材</a> > 快编</p>

	<div  id="flashdiv" style="text-align:center;background-color:#fff; margin:0 auto;">
		<%--flash显示区域 --%>
		<div id="flashContent"></div>
	</div>
	</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>

</body>
</html>
<script>
	$(function() {
		// something
		init();
		var win_h=$(window).height();
		$("#flashdiv").css({"height":win_h,"width":win_h/0.49});
		var flash_w=$("#flashdiv").width();
		if(flash_w>$(window).width()){
			$("#flashdiv").css({"width":$(window).width(),"height":$(window).width()*0.49});
		}
	});
	
	$(window).load(function(){
	var t = $("#flashdiv").offset().top;
	$(window).scrollTop(t);//滚动到锚点位置
	//window.location.href="#flashdiv";
	})
		 
</script>