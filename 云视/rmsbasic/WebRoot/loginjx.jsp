<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>登录</title>
        <link rel="stylesheet" type="text/css" href="${ctx }/css/login/loginBasic.css"/>
		<script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
        <script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
        <script src="${ctx }/js/login/login.js" type="text/javascript"></script>
        <script src="${ctx }/js/login/loginBasic.js" type="text/javascript"></script>
        <script src="${ctx }/js/login/jquery.cookie.js" type="text/javascript"></script>
	</head>
	<body style="background:url(${ctx }/common/images/sign_bdbg.png); min-width:1220px;">
		<div class="sign_in stand">
			<div class="sign_in_bg">
				<div class="login_logo"></div>
				<div class="sign_title"><img src="${ctx }/common/images/jx/sign_title.png"/></div>
				<div class="sign_in_bg_text">
				<p>北京新奥特云视科技有限公司©2014cdvcloud.com 版权所有</p>
				<p>ICP证：京ICP备14009498</p>
			    </div>
			</div>
			<!--用户登陆小窗口-->
			<div class="sign_in_small">
				<div class="sign_in_small_small">
					<div class="pc-login-div">
					    <p>用户登录</p>
					    <input class="user_name"  type="text" name="" id="user_name" value="" placeholder="用户名/手机/邮箱" onblur="checkData('user_name');" />
					    <input class="user_password" type="password" name="" id="user_password" value="" placeholder="密码" onblur="checkData('user_password');" />
					    <input class="user_yunK" type="text" name="" id="keyPwd" value="" placeholder="云Key" onblur="checkData('keyPwd');"/>
						<p class="user_check"><input type="checkbox" id="zd"><span class="stand auto"><label for="zd">自动登录</label></span></p>
						<span class="stand user_land" onclick="doLogin();">立即登录</span>
		                <div class="tishi"></div>
		                <div class="line-bottom"></div>
		                <div class="weixin-login">
		                	<span>使用第三方登录</span>
		                	<p onclick="openWeixin()"><span>微信</span></p>
		                </div>
		            </div>
		            <div class="wx-login-div">
					    <div class="qrcode-img">
					    	<img src="${ctx }/common/images/jx/qrcodejx.png"/>
					    </div>
					    <p><i></i>扫一扫下载手机客户端</p>
					</div>
	                
	                <div class="qr-login" onclick="Loginclick()"></div>			
			    </div>
		    </div>
		</div>
	</body>
	
</html>

