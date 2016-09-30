<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>添加素材集</title>
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/uploadify3.1/uploadify.css">
<link rel="stylesheet" href="${ctx}/css/organize/user.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/materialset/materialset.css" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/validatorEngine/css/validationEngine.jquery.css">
<script type="text/javascript"  src="${ctx }/common/plugins/validatorEngine/js/languages/jquery.validationEngine-zh_CN.js" ></script>
<script type="text/javascript" src="${ctx }/common/plugins/validatorEngine/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/uploadify3.1/jquery.uploadify.min.js"></script> 
<script type="text/javascript" src="${ctx }/js/materialset/materialset.js"></script> 
<style type="text/css">
.uploadify {
    margin: 1em 0;
    position: relative;
}
</style>
<head>

<body>
	<div class="popup-top">
		<i></i>
	</div>
	<form id="form" name="form">
		<div class="popup-cont clearfix">
			<div class="fl thumb">
				<img id="thumbnailurl">
				<input type="file" name="fileUpload" id="fileUpload" />
			</div>
			<div class="fr details-thumb">
				<div class="">
					<label for="">标题<span>*</span></label>
				    <input type="" name="materialtitle" id="materialtitle" value=""  class="validate[required]"/>
				</div>
				<div class="">
					<label for="">来源<span>*</span></label>
					<select name="src" class="validate[required]">
						
					</select>
				</div>
				<div class="">
					<label for="">描述</label>
					<textarea name="remark" id="remark" rows="" cols=""></textarea>
				</div>
			</div>
			<div>
			<a href="javascript:addMaterial();" class="save-btn"> 保 存 </a>&nbsp;<a href="javascript:layer.closeAll();" class="cancel-btn"> 取 消 </a>
			</div>
		</div>
	</form>
</body>
</html>
