<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>

  <head>
    <title id="newsCueId"></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx }/css/api/share.css">
    <script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx }/common/js/config.js"></script>
    <script type="text/javascript" src="${ctx }/js/api/share.js"></script>
	<script type="text/javascript" src="${ctx }/js/api/newsCues.js"></script>
  </head>
  <script>
var title="<%=request.getParameter("title")%>";
var imgUrl="<%=request.getParameter("imgUrl")%>";
var openUrl="<%=request.getAttribute("openUrl")%>";
$(function(){
$("#newsCueId").text(title);
$("h3").html(title);
if(imgUrl==""){
	$("#imgUrlId").attr("src",ctx+"/common/images/defaultwechat.png")
}else{
	$("#imgUrlId").attr("src",imgUrl+"@320w_100q.src");
	
}
})
</script>
  
  <body>
  <img width="0px" height="0px" src="${ctx}/images/api/yn.png">
   <div class="share_wrap">
		<div class="news_cont">
			<div class="news_div wengao">
				<h3 id="newsCueIdH"></h3>
				<div class="img_div">
					<img id="imgUrlId" >
				</div>
				<a  name="shareAI" href="<%=request.getAttribute("openUrl")%>" class="see_btn">点击查看</a>
			</div>
		</div>
	</div>
  </body>
</html>
