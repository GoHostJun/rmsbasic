<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<script>
var _id="<%=request.getParameter("docId")%>";
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link type="text/css" rel="stylesheet" href="${ctx }/css/docs/docs_service.css">
<script type="text/javascript" src="${ctx }/js/news/news_list_common.js"></script>
<script type="text/javascript" src="${ctx }/js/docs/docs_service.js"></script>
<title>云报道</title>
</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
		<p class="p_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toDocs/');">文稿</a> > <a href="javascript:;">服务</a></p>
		<div class="tonglian_title" id="docDiv"></div>
		<div class="tonglian_cont">
			<div class="inner">
		        <div class="service_cont">
		        	<div class="service_div libre_div">
		        		<input type="hidden" name="userId" value="56737f7d6acecb9ae8b9b2a1">
		        		<input type="hidden" name="realname" value="李老师">
		        		<input type="hidden" name="position" value="记者">
		            	<i></i>
		            </div>
		            <div class="service_div dubb_div">
		            	<i></i>
		            </div>
		            <div class="service_div make_div">
		            	<i></i>
		            </div>
		            <div class="clear"></div>
		        </div>
		        <div class="requir_div">
		            <form id="ser_form" name="form">
		            	<div style="position:relative" >
			                <textarea id ="require" placeholder="请在此处输入您的要求......" ></textarea>
<%--			                 <p>亲，您还可以输入180个字符，已输入<span id="requireCount">0</span>个字符</p>--%>
		               </div>
		                <a href="javascript:;" id="sub">提交</a>
		                <div class="clear"></div>
		            </form>
		        </div>
		        
		        <div class="requir_succ_div hide">
		            <h3><i></i>您的服务已经提交成功！</h3>
		            <ul class="requir_ul">
		            	<li id="require2"></li>
		            </ul>
		        </div>
		    </div>
		</div>
	</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
	</body>
<script>
	$(function() {
		init();
	});
</script>
</html>
