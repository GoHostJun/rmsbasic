<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE html>
<script type="text/javascript">
var mId="<%=request.getParameter("mId")%>";
var mtype="<%=request.getParameter("mtype")%>";
</script>
<html>
<script type="text/javascript" src="${ctx }/js/material/material_down.js"></script>
<head>
<title>云报道</title>
</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
		<p class="p_url">
			<i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toMaterial/');">素材</a> > 素材下载
		</p>

		<div class="tonglian_title">
			<div class="inner">
				<a href="" target="_blank" id="toPreview">
					<div class="title_img">
						<img id="vslt"> <i></i>
						<p id="duration"></p>
					</div> </a>
				<div class="title_right">
					<h4>
						<a href="" target="_blank" id="title"></a>
					</h4>
					<p>
						大小：<span id="fileSize"></span > 码率：<span id="rate"></span> / 分辨率：<span id="pixels"></span>
					</p>
					<p class="time" id="updateTime"></p>
				</div>
				<div class="clear"></div>
				<ul class="tonglian_nav">
					<li class="active"><a href="#">下载</a>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</div>
		<div class="tonglian_cont">
			<div class="inner">
				<div class="download_cont">

					<div class="download_div">
						<div class="down_div">
<%--							<p>含文稿信息</p>--%>
							 <a  href="javascript:;" id="viewMark" download=""><i class="download_icon1"></i> <span>预览码率</span></a>
						</div>
						<div class="down_div">
<%--							<p>含文稿信息</p>--%>
							<a href="javascript:;" id="playMark" class="download_icon1" download="" ><i class="download_icon2"></i> <span>播出</span></a>
						</div>
<%--							<a class="down_btn fr"><i></i>下载全部</a>--%>
						<div class="clear"></div>
					</div>
				</div>
			</div>
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
	});
</script>
