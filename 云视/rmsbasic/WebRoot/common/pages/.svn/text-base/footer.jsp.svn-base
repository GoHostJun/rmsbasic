<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script>
$(function(){
	if(getUrlConfig.nameTV=="JS"){
		$("#footJS").show();
		$("#footJX").remove();
	}else{
		$("#footJX").show();
		$("#footJS").remove();
	}
})
</script>
	<div id="footer">
		<div id="footJX" >
			<%@include file="/common/pages/footerJX.jsp"%>
		</div>
		<div id="footJS" >
			<%@include file="/common/pages/footerJS.jsp"%>
		</div>
	</div>
<div id="upload_div_wrap">
		<div id="upload_div_title">
			&nbsp;
			<div id="upload_div_title_percent"></div>
			<a href="javascript:" id="close" onclick="upload_close();" title="隐藏">X</a> <a href="javascript:" id="upload_img_min"
				onclick="upload_stats('min');" title="最小化">— </a> <a href="javascript:" id="upload_img_max" onclick="upload_stats('max');" title="最大化">+ </a>
		</div>
		<div id="upload_div_content">
			<div id="upload_div_page"></div>
			<div id="upload_div_btn">
				<button id="upload_btn_clear" onclick="upload_clear();" style="float: right; margin-right: 0px;">清空已上传</button>
			</div>
		</div>
	</div>
	<!-- 	上传插件-结束 -->
	<%@include file="/WEB-INF/views/upload/webUploader.jsp"%>
	