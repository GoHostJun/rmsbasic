<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<script type="text/javascript" >
var mtype = "<%=request.getParameter("mtype")%>" 
var id = "<%=request.getParameter("mId")%>" 
</script>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<script type="text/javascript" src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js" ></script>
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/docs/docs_refer_list.js"></script>
<head>
<title>云报道</title>
</head>
<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
		<div class="search_div">
			<input type="text" id="search" placeholder="请输入关键字搜索">
		    <a href="javascript:;" id="searchBotton"><i></i>搜索</a>
		    <div class="clear"></div>
		</div>
		<p class="p_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toMaterial/');">素材</a> > <a href="javascript:;">引用文稿</a></p>
		<div class="list_cont">
			<div class="inner" id="outter">
		    	<p class="check_p">
		        	<!-- <i class="choice_all"></i>全选 -->
		            <a href="javascript:;" id="check">确定</a>
		            <div class="clear"></div>
		        </p>
		       <div id="listDiv"></div>
			   <div id="pageDiv"></div>
		    </div>
			<div id="inner">
	   		</div>
		</div>
	</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>

</body>
</html>
<script>
	$(function() {
		init();
	});
</script>
