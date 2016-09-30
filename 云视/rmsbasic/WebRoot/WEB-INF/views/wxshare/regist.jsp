<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport"content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/wxshare/accredit_error.css"/>
		<script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
		 <script type="text/javascript" src="${ctx }/common/js/config.js"></script>
		<title>授权登录失败</title>
	</head>
	<body>
		<div class="top-cue container">
			<div class="clearfloat">
				<div class="circle-ic fl">
					<i></i>
				</div>
				<p class="fl">你的系统账号还没有绑定微信，授权失败!</p>
			</div>
		</div>
		<div class="container accredit-cont">
			<h2>请授权微信绑定您的云报道账号：</h2>
			<ul>
				<li>
					<label for=""><span>*</span>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</label><input type="text" name="" id="username" value="" placeholder="输入您的账号"/>
				</li>
				<li>
					<label for=""><span>*</span>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label><input type="password" name="" id="password" value="" placeholder="输入您的密码"/>
				</li>
				<li>
					<label for=""><span></span>联系电话</label><input type="text" name="" id="phone" value="" placeholder="输入您的联系电话（选填）"/>
				</li>
			</ul>
		</div>
		<div class="bottom-fixed ">
			<div class="btn-group clearfloat">
				<a class="binding-btn" id="bindUser">
					<i></i>
					绑定
				</a>
			</div>
		</div>
		<!--绑定成功弹窗-->
		<div class="popup" id="successBind">
			<div class="popup-inner bind-successe">
				<div class="">
					<div class="p-text">
						<p>您的微信已成功绑定，</p>
						<p id="bindNum"></p>
					</div>
					<div class="btn-group clearfloat">
						<a class="look-btn" id="viewDetail">
						<i></i>
						立即查看
						</a>
					</div>
				</div>
			</div>
		</div>
		<!--绑定失败弹窗-->
		<div class="popup" id="errorBind">
			<div class="popup-inner error-import">
				<div class="">
					<div class="p-text">
						<p id="error_text"></p>
					</div>

					<div class="btn-group clearfloat">
						<a class="back-btn" id="closePop">
						<i></i>
						重新输入
						</a>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
	var shareId="<%=request.getParameter("shareId")%>";
	var type="<%=request.getParameter("type")%>";
	var headimgurl="<%=request.getParameter("headimgurl")%>";
	var openid="<%=request.getParameter("openid")%>";
	var nickname="<%=request.getParameter("nickname")%>";
	var unionid="<%=request.getParameter("unionid")%>";
	var nikename="<%=request.getParameter("nikename")%>";
	var subbmit="<%=request.getParameter("subbmit")%>";
	var loginid="<%=request.getParameter("loginid")%>";
	var title="<%=request.getParameter("title")%>";
	var imgUrl="<%=request.getParameter("imgUrl")%>";
	</script>
    
	<script src="${ctx }/js/wxshare/accredit_error.js" type="text/javascript" charset="utf-8"></script>
</html>
