<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<%
String mId = request.getParameter("mId");
%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/uploadify3.1/uploadify.css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/validatorEngine/css/validationEngine.jquery.css">
<script type="text/javascript" src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx }/common/plugins/uploadify3.1/jquery.uploadify.min.js"></script> 
<script type="text/javascript"  src="${ctx }/common/plugins/validatorEngine/js/languages/jquery.validationEngine-zh_CN.js" ></script>
<script type="text/javascript" src="${ctx }/common/plugins/validatorEngine/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx }/js/material/material_edit.js"></script>
<head>
<title>云报道</title>
<style type="text/css">
.uploadify {
    margin: 1em 0;
    position: relative;
}
</style>
</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
	<input id="mId" type="hidden" value="<%=mId%>">
	<p class="p_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toMaterial/');">素材</a> > 素材编辑</p>
	<div class="check_cont">
		<div class="inner">
	        <div class="check_div">
	        <form name="form" id="form">
	        	<div class="check_left veCon_left_check_div fl">
	                <div class="wengao_edit_div">
	                    <h3>素材信息</h3>
	                    <div class="veCon_left">
	                        <div class="veConLeft_title veConLeft_title_title">
	                            <h5 class="h_title"><i></i>标题 <span>*</span> </h5>
	                          <input type="text" id="title" value="" class="validate[required,maxSize[50]]">
	                            <p>亲，您还可以输入<span id="leftTitleCount">50</span>个字符，已输入<span id="titleCount"></span>个字符</p>
	                        </div>
	                        <div class="veConLeft_title">
	                            <h5 class="h_time"><i></i>发生时间</h5>
	                            <input type="text" value="" id="happenTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen',isShowClear:false,maxDate:'%y-%M-%d'});" class="Wdate">
	                        </div>
	                        <div class="veConLeft_title">
	                            <h5 class="h_place"><i></i>发生地点</h5>
	                            <input type="text" value="" id="happenPlace" value="" placeholder="定位地点为......">
	                        </div>
	                        <div class="veConLeft_title material-textarea">
	                            <h5 class="h_ms"><i></i>描述</h5>
	                            <textarea id="describe" placeholder="请详细描述新闻内容" class="validate[maxSize[400]]"></textarea>
	                            <p>亲，您还可以输入<span id="leftWordNumber">400</span>个字符，已输入<span id="wordNumber"></span>个字符</p>
	                        </div>
	                        
	                        <div class="edit_btn_div left_edit_btn_div">
	                        	<a onclick="updateMaterial();" style="cursor:pointer;" class="save_btn">保存</a>
	                            <a href="javascript:;" class="empty_btn" id="materialRest">重置</a>
	                            <div class="clear"></div>
	                        </div>
	                        <div class="clear"></div>
	                    </div>
	                    
	                    
	                </div>
	            </div>
	            </form>
	            <div class="check_right veCon_right fr">
	                <div class="veConRight_title">
	                    <h5 class="h_slt"><i></i>缩略图</h5>
	                </div>
	                <div class="slt_div">
	                    <div class="slt_img">
	                        <img id="thumbnail" >
	                    </div>
	<%--                    <a href="javascript:;" class="upload_slt_btn"><i></i>上传缩略图</a>--%>
						<input type="file" name="fileUpload" id="fileUpload" />
	                    <p>支持格式：<span>jpg</span>、<span>png</span>、<span>gif</span>(图片大小不超过<span>1M</span>)</p>
	                </div>
	                <div class="veConRight_title">
	                    <h5 class="h_xt"><i></i>系统截图</h5>
	                </div>
	                <div class="veConRight_img">
	                    <div id="veConRgImg_Sonmenu">            	
	                        <div class="hide show veConRgImgDiv" id="thumbnails" >
	                        	<ul class="veConRgImg_ul fl mr">
	           				    </ul>
	           					<ul class="veConRgImg_ul fr">
	                            </ul>
	                        </div>
	                        
	                    </div>    
	                    
	                    <div class="clear"></div>
	                </div>
	                
	                
	            </div>
	            <div class="clear"></div>
	        </div>
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
