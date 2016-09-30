<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<%
String mId = request.getParameter("mId");
%>
<script type="text/javascript">
var mtype="<%=request.getParameter("mtype")%>";
</script>
<!DOCTYPE html>
<html>
<script type="text/javascript" src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js" ></script>
<script type="text/javascript" src="${ctx }/js/material/material_preview.js"></script>
<head>
<title>云报道</title>

</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<input type="hidden" id="mId" value="<%=mId %>" />
	<div id="content">
	<p class="p_url material_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toMaterial/');">素材</a> > 素材预览</p>

<div class="preview_div">
    <div class="type-name inner">
	    <p id="title"><span id="titletype">视频</span></p>
	</div>	
	<div class="inner">    
    	<div class="preview_left">  	  		
			<div class="bfqloading" id="previewFlashContent"></div>				     	 
        </div>
        <div class="preview_right" id="preId">
            <ul class="tab clearfix" >
                <li  class="active" onclick="tabChange(this)">原参数</li>
                <li onclick="tabChange(this)">高清参数</li>
                <li onclick="tabChange(this)">预览参数</li>
        	</ul>
        	<div class="tab-div" style="display: block;">
        	    <h3>素材详情</h3>
	        	<p class="half_p">时长：<span id="duration"></span></p>
	            <p class="half_p">大小：<span id="fileSize"></span></p>
	            <p class="half_p">封装格式：<span id="fmt"></span></p>
	            <p class="half_p">视频编码：<span id="format"></span></p>
	            <p class="half_p">视频码率：<span id="rate"></span></p>
	            <p class="half_p">分辨率：<span id="pixels"></span></p>
	            <p class="half_p">画面比例：<span id="vedioSize"></span></p>
        	</div>
        	<div class="tab-div">
        	    <h3>素材详情</h3>
	        	<p class="half_p">时长：<span id="superduration"></span></p>
	            <p class="half_p">大小：<span id="superfileSize"></span></p>
	            <p class="half_p">封装格式：<span id="superfmt"></span></p>
	            <p class="half_p">视频编码：<span id="superformat"></span></p>
	            <p class="half_p">视频码率：<span id="superrate"></span></p>
	            <p class="half_p">分辨率：<span id="superpixels"></span></p>
	            <p class="half_p">画面比例：<span id="supervedioSize"></span></p>
        	</div>
        	<div class="tab-div">
        	    <h3>素材详情</h3>
	        	<p class="half_p">时长：<span id="preduration"></span></p>
	            <p class="half_p">大小：<span id="prefileSize"></span></p>
	            <p class="half_p">封装格式：<span id="prefmt"></span></p>
	            <p class="half_p">视频编码：<span id="preformat"></span></p>
	            <p class="half_p">视频码率：<span id="prerate"></span></p>
	            <p class="half_p">分辨率：<span id="prepixels"></span></p>
	            <p class="half_p">画面比例：<span id="prevedioSize"></span></p>
        	</div>
        	<div class="tab-div">
        	    <h3>素材详情</h3>
	        	<p class="half_p">时长：<span id="audioduration"></span></p>
	            <p class="half_p">大小：<span id="audiofileSize"></span></p>
	             <p class="half_p">封装格式：<span id="audiofmt"></span></p>
	             <p class="half_p">音频编码：<span id="audioformat"></span></p>
	            <p class="half_p">音频码率：<span id="audiorate"></span></p>
	            <p class="half_p">音频声道：<span id="audiochannels"></span></p>
        	</div>
        	<div class="tab-div">
        	    <h3>素材详情</h3>
	            <p class="half_p">大小：<span id="picfileSize"></span></p>
	             <p class="half_p">分辨率：<span id="picpixels"></span></p>
	             <p class="half_p">封装格式：<span id="picfmt"></span></p>
        	</div>
            
        </div>
        <div class="clear"></div>
    </div>
</div>
	
	</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
	<!-- 	右侧浮动标签-开始 -->
	<!--div class="pop">
		<div class="up_load">
			<p>
				<a href="javascript:upload_open();">上传素材</a>
			</p>
		</div>
		<div class="new_draft">
			<p>
				<a href="wengao_new.html" target="_blank">新建文稿</a>
			</p>
		</div>
	</div-->
	<!-- 	右侧浮动标签-结束 -->
</body>
</html>
<script>
	$(function() {
		// something
		init();
	});
</script>
