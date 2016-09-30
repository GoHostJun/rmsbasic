<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>

  <head>
    <title>${mapInfo.title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/api/share.css">
    <script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx }/common/js/config.js"></script>
    <script type="text/javascript" src="${ctx }/js/api/share.js"></script>
	<script type="text/javascript" src="${ctx }/js/api/video_screen.js"></script>
  </head>
  <body>
   <img width="0px" height="0px" src="${ctx}/images/api/yn.png">
   <div class="share_wrap">
		<div class="news_cont">
			<!-- 素材集 -->
			<div class="news_div sucai">
				<h3>${mapInfo.title}</h3>
				<p class="clearfloat">
					<span class="time fl"><i></i>${mapInfo.ctime}</span>
					<span class="name fl">${mapInfo.cusename}</span>
					<span class="sucai_name fr">${mapInfo.src}</span>
				</p>
				<div class="img_div">
					<c:choose>
						<c:when test="${empty  mapInfo.thumbnailurl}">
							<img src="${ctx }/common/images/defaultwechat.png">
						</c:when>
						<c:otherwise>
							<img src="${ mapInfo.thumbnailurl }@320w_100q.src">
						</c:otherwise>
					</c:choose>
				</div>
<%--				<div class="about_sucai clearfloat">--%>
<%--					<p class="sucai_num">素材数<br><span>12</span></p>--%>
<%--					<p>参与人<br><span>12</span></p>--%>
<%--				</div>--%>
				<a href="javascript:browserRedirect('${type}','${mapInfo._id}','${userId}')" class="see_btn">点击查看</a>
			</div>
		</div>
		<!-- 蒙版 -->
		<div class="share_tip hide">
			<img src= "${ctx}/images/api/share_tip.png">
<%--			<a href="javascript:;"><img src="${ctx}/images/api/iknow.png"></a>--%>
		</div>
		<div class="share_tip_ios hide">
			<img src= "${ctx}/images/api/share_tip_ios.png">
		</div>
	</div>
  </body>
</html>
