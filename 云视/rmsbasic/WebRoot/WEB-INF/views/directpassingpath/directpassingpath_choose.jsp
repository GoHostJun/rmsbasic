<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<script>
var _id="<%=request.getParameter("_id")%>";
var title="<%=request.getParameter("title")%>";
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/directpassingpath/directpassingpath_choose.css">
<script type="text/javascript" src="${ctx }/js/directpassingpath/directpassingpath_choose.js"></script>
<!DOCTYPE>
<html>
  <head>

  </head>
  <body>
	<div class="">
		<div class="popup-cont">
			<h2>请选择目标路径：<span id="title"></span></h2>
			<div class="inner-div">
				<ul class="clearfix" id="direct_ul">
<%--					<li><i></i>移动云报道素材库(必选项)</li>--%>
				</ul>
			</div>
		</div>
	</div>
	<div class="edit_btn_div clearfix fr">
         <a href="javascript:;" class="save_btn" onclick="toSaveAndOpenUpload()">确定</a>
         <a href="javascript:layer.closeAll();;" class="empty_btn">取消</a>
   	</div>	
  </body>
</html>
