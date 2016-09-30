<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/toastr/toastr.min.css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/css/header/header.css"/>
<script type="text/javascript" src="${ctx }/common/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="${ctx }/js/comment/comment.js"></script>
<script type="text/javascript" src="${ctx }/common/js/headimg.js"></script>
<script type="text/javascript" src="${ctx }/common/js/weChatAndqq.js"></script>

<%--<div class="header">
	<div class="inner">
		<div class="user_div fr">
			<img src="${ctx }/common/images/headimg.png" />  
			<b id="head_img_b" class="heading_info"></b>   
			<div id="user_menu">
				<span id="user_menu_span">${userName}</span>
				<i class="user_menu_i" style="display:inline-block;position:relative;">
					<div class="user_pop" style="position:absolute;">
	        		   <i class="stand user_pop_triangle" style="position:absolute;"></i>
	        		   <div class="user_pop_inner">
	        		        <ul>
	        		    		<li id="personal_m"><b></b>个人信息</li>
	        		    		<li id="update_password" onclick="pwdUpdateOpen();"><b></b>修改密码</li>
	        		    		<li><b></b>系统帮助</li>
	        		    		<li onclick="doLogout();"><b></b>退出登录</li>
	        		    	</ul>
	        		   </div>
	        	    </div>
				</i>
			</div>
			<div class="msg_btn" id="comment_div">
           	</div>
		</div>
	</div>
</div>

		--%><div class="popup user_message_popup" style="display:none">
		   <div class="popup_inner popup_inner_index">
		   	  <p class="top">个人信息<i class="close_btn"></i></p>
		   	  <div class="popup_cont  ">
		   	  	   <div class="head_img fl clearfix">
		   	  	   	   <b id="head_img_b1" class="heading_info"></b>
		   	  	   	   <%--<p><u>修改头像</u></p>   
		   	  	   --%></div>
		   	  	   <div class="user_message fl clearfix">
		   	  	       <div>
		   	  	           <i class="storage"></i><span>已用1.3G</span>
		   	  	           <i class="integral"></i><span id="score"></span>
		   	  	       </div>
		   	  	   	   <ul class="fl">
		   	  	   	   	<li>用户名：<input id="userNameId" type="text" value=""/></li>
		   	  	   	   	<li>手机号：<input id="phoneId" type="text" value=""/></li>
		   	  	   	   	<li>常用邮箱： <input id="emailId" type="text" value=""></li>
		   	  	   	   </ul>
		   	  	   	   <ul class="fl">
		   	  	   	   	<li>工号： <span id="gonghao"></span></li>
		   	  	   	   	<li>所属机构：<span id ="jiGouId"></span> </li>
		   	  	   	   	<li><a class="per-data">完善个人资料</a></li>
		   	  	   	   </ul>
		   	  	   </div>
		   	  	   <div class="clear"></div>
		   	  	   <div class="free-space">
		   	  	   		<%--<p>剩余空间:1.3G</p>--%>
		   	  	   		<div class="space-percent">
		   	  	   			<div></div>
		   	  	   		</div>
		   	  	   </div>
		   	  	   <div class="third-party clearfix">
		   	  			<div class="fl">
		   	  				<div class="weixin-bd">
		   	  					<i></i>
		   	  					<span class="active" id="weChatBind">已绑定</span>
		   	  					<u class="active" id="weChatNotBind">解绑</u>
		   	  				</div>
		   	  				<p ></p>
		   	  			</div>
		   	  			<div class="fr">
		   	  				<%--<div class="qq-bd">
		   	  					<i></i>
		   	  					<span class="" id="qqBind">未绑定</span>
		   	  					<u class="" id="qqNotBind">绑定</u>
		   	  				</div>
		   	  					<p id="qqNickName"></p>
		   	  					
		   	  			--%>
		   	  			<p class="wechat-id" id="weChatNickName"></p>
		   	  			</div>		   	  			
		   	  	   </div>
		   	  	   <p class="state-ment">*绑定后可以使用微信或QQ账号直接登录</p>
		   	  	   <div class="edit_btn_div">
                        <a id="save_id" href="javascript:saveUserInfo();" class="save_btn save_btn_index">保存</a>
                        <a id="reset_id" href="javascript:;" class="empty_btn">取消</a>
                        <div class="clear"></div>
                   </div>
		   	  </div>
		   </div>	
		</div> 
