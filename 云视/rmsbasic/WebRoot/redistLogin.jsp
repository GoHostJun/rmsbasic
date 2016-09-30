<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE html><html>
<head>
<meta property="qc:admins" content="344356244736346347546375" />
<title>绑定微信</title>
<link rel="stylesheet" type="text/css" href="${ctx }/css/redistLogin/redistLogin.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css"/>
<script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
<script src="${ctx }/js/redistLogin/redistLogin.js" type="text/javascript"></script>
<script>
var head="<%=session.getAttribute("headimgurl")%>" ;
var nickname="<%=session.getAttribute("nickname")%>" ;
var type="<%=request.getParameter("type")%>" ;
</script>
</head>
<body>
	<div class="wrap">
		<div class="bind-inner clearfix">
			<div class="fl user-msg">
				<div class="user-img">
				
					<img id="headImg"/>
				</div>
				<h1 id="nickname"></h1>
				<p id="qqOrWechatP"></p>
			</div>
			<div class="fr user-bind">
				<h1>云通联账户绑定</h1>
				<div class="username-div">
					<i></i>
				    <input type="text" value="" id="user_name"  class="username" placeholder="账号" onblur="checkData('user_name');"/>
				</div>
				<div class="password-div">
				    <i></i>
					<input type="password" value="" id="user_password"  class="password" placeholder="密码" onblur="checkData('user_password');"/>
				<div/>
				<div id="tipError" class="notice">
					<p>账号或密码错误！</p>
				</div>
				<input type="button" name="" id="" onclick="bindLogin();" value="绑定" class="login-btn"/>
			</div>
		</div>
	</div>
</body>
</html>