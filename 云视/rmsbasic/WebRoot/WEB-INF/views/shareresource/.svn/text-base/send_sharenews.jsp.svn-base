<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/shareresource/shareresource.css"/>
<!DOCTYPE>
<html>
  <head>
<script>
var newsId="<%=request.getParameter("_id")%>";
var pushType="<%=request.getParameter("pushType")%>";

</script>
<script src="${ctx }/js/shareresource/send_sharenews.js" type="text/javascript" charset="utf-8"></script>
  </head>
  <body>
  <div class="popup-inner tuisong-popup">
		<div class="popup_cont">
			<ul class="tabs">
				<li class="active">常用推送路径</li>
			    <li>全部推送路径</li>
			</ul>
			<div class="tabs-div">
				<div class="tabs-div-cont">
					<ul id="oftenSends">
					</ul>
				</div>
				<div class="hide tabs-div-cont">
					<ul id="allSends">
					</ul>
				</div>
				<div class="edit_btn_div">
	                <a href="javascript:;" class="save_btn" onclick="subSend()">确定</a>
	                <a href="javascript:layer.closeAll();" class="empty_btn" >取消</a>
	                <div class="clear"></div>
	            </div>
			</div>
		</div>
	</div>
  </body>
</html>
