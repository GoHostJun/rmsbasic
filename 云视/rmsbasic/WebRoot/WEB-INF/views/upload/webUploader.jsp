<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>WEB上传</title>
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/webUploader/css/upload.css">
<link rel="stylesheet"  type="text/css"  href="${ctx }/common/plugins/webUploader/css/webuploader.css" />
<style type="text/css">
.webuploader-pick {
	position: relative;
	display: inline-block;
	cursor: pointer;
    background: #edf3fd; 
	padding: 0px 0px;
	color: #fff;
	width:100%;
}
.webuploader-pick-hover {
 	background: #edf3fd; 
}

.webuploader-pick-disable {
	opacity: 0.6;
	pointer-events:none;
}
</style>


</head>
<body>
	<div   class="upload_cont"   id="upload_cont">
		<h4>
             <span id="jd"></span>
			<i class="close_upload_icon"></i> <i class="min_upload_icon"></i>
		</h4>
		<div class="upload_div"   >
			<div class="upload_btn"  id=upload_btn>
				<div  id="picker"><img src="${ctx}/common/plugins/webUploader/images/upload_img.png" ></div>
				<p>
					可将文件拖拽至此处上传 <span><input type="checkbox"  id="md5"  onchange="check()"/>MD5校验</span>
				</p>
				<div class="upload_pos" style="display: none;" id ="upload_pos">
					<p>可将文件拖拽至此处上传</p>
				</div> 
			</div>
			<div class="progress_cont" id="progress_cont">
			</div>
			<a href="javascript:;" class="empty_btn">清空已上传</a>
		</div>
	</div>
	 <script src="${ctx }/common/plugins/webUploader/js/webuploader.min.js" type="text/javascript" charset="utf-8"></script> 
    <script src="${ctx }/common/plugins/webUploader/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="${ctx }/common/plugins/webUploader/js/md5.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx }/common/plugins/webUploader/js/upload.js"></script>
</body>
</html>