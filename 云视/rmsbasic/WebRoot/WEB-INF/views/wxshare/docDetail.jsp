<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
  <head>
	<meta name="viewport"content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/wxshare/ybd_details.css"/>
	    <script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
		 <script type="text/javascript" src="${ctx }/common/js/config.js"></script>
		<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
		<title id="headtitle">详情</title>
  </head>
  <body>
		<div class="top-fixed container">
			<img src="${ctx }/images/wxshare/logo.png" class="fl logo"/>
			<div class="fr">
				<p><i></i><a href="javascript:browserRedirect()">使用APP查看</a></p>
			</div>
		</div>
		<div class="container">
			<div class="title-cont">
				<h2 id="title"></h2>
				<p id="sourceDate"></p>
			</div>
			<div class="bannar">
<%--				<div class="device hide">--%>
<%--					<div class="swiper-container">--%>
<%--						<div class="swiper-wrapper clearfloat" id="docThumb">--%>
<%--						</div>--%>
<%--					</div>--%>
<%--					<div class="pagination"></div>--%>
<%--				</div>--%>
				<div class="video-show" id="viOrim"></div>
			</div>
			<div class="img-list-div">
				<div class="img-list">
					<ul class="clearfloat" id="docThumb">
					</ul>
				</div>
			</div>
			<div class="text-cont" id="docContent">
			</div>
		</div>
		<div class="bottom-fixed ">
			<div class="btn-group clearfloat">
				<a class="inport-btn" id="copyDoc">
				<i></i>
				导入我的文稿
			</a>
			</div>
			
		</div>
		<!--导入成功弹窗-->
		<div class="popup">
			<div class="popup-inner audit-success clearfloat">
				<div class="fl circle-ic">
					<i></i>
				</div>
				<div class="fl">
					<h2>导入成功！</h2>
					<p>2秒后关闭此页面</p>
				</div>
			</div>
		</div>
		<!--导入失败弹窗-->
		<div class="popup">
			<div class="popup-inner audit-success clearfloat">
				<div class="fl circle-ic">
					<i></i>
				</div>
				<div class="fl">
					<h2>导入失败！</h2>
					<p>2秒后关闭此页面</p>
				</div>
			</div>
		</div>
		<div class="popup share_tip" >
			<img src= "${ctx}/images/api/share_tip.png">
		</div> 
		<div class="popup share_tip_ios" >
			<img src= "${ctx}/images/api/share_tip_ios.png">
		</div
	</body>
	<script>
	var role="<%=request.getAttribute("role")%>";
	var shareId="<%=request.getParameter("shareId")%>";
	var type="<%=request.getParameter("type")%>";
	var consumerId="<%=request.getAttribute("consumerId")%>";
	var userId="<%=request.getAttribute("userId")%>";
	var path= "/"+consumerId+"/ytl/"+userId+"/566fd73c84e353224410c0b6/v1/api/";
	</script>
	<script src="${ctx }/js/wxshare/doc_details.js" type="text/javascript" charset="utf-8"></script>
</html>
