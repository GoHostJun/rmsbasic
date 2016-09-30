<%@page import="com.cdvcloud.rms.util.UserUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%
String companyName = UserUtil.getUser4CAS(request, "companyName");
%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/nav/navJX.css">
<script type="text/javascript" src="${ctx }/js/nav/navJX.js"></script>
	<div class="jx_index_wrap">
		<div class="jx_header clearfloat">
			<div class="jx_logo">
				<a href="javascript:toHome();"><img src="${ctx }/images/logo/<%=companyName %>.png" onerror="this.src='<%=request.getContextPath()%>/images/index/jx_logo.png'"></a>
			</div>
			<div class="header_right fr">
				<ul class="jx_nav fl">
					<li class="active"><p><a href="javascript:toHome();">首页</a></p></li>
					<li>
						<p>素材<i></i></p>
						<div class="mark hide">
							<div class="mark_cont"></div>
						</div>
						<ul class="little_nav clearfloat hide">
							<li><a href="javascript:uCont('material/toDocs/');">文稿</a></li>
							<li><a href="javascript:uCont('material/toMaterial/');">文稿素材</a></li>
						</ul>
					</li>
					<li>
						<p>通联<i></i></p>
						<div class="mark hide">
							<div class="mark_cont"></div>
						</div>
						<ul class="little_nav clearfloat hide">
							<li><a href="javascript:uCont('news/toNewsMyCreateList/');">我的通联</a></li>
							<li><a href="javascript:uCont('news/toNewsCompleteList/');">我的任务</a></li>
						</ul>
					</li>
					<li>
						<p><a href="javascript:uCont('shareresource/toShareList/');">圈子</a>
<%--						<i></i>--%>
						</p>
						<div class="mark hide">
<%--							<div class="mark_cont"></div>--%>
						</div>
<%--						<ul class="little_nav clearfloat hide">--%>
<%--							<li><a href="javascript:;">新闻外场</a></li>--%>
<%--							<li><a href="javascript:;">新闻通联</a></li>--%>
<%--						</ul>--%>
					</li>
					<li class="more">
						<p>更多<i></i></p>
						<div class="mark hide">
							<div class="mark_cont"></div>
						</div>
						<ul class="little_nav clearfloat hide">
							<li><a href="javascript:uCont('count/toReport/');">统计分析</a></li>
							<li><a href="javascript:uCont('system/toSetting/?toModule=notice');">系统设置</a></li>
							<li><a href="javascript:uCont('system/toPage/?view=pushtask/pushtask_list');">任务列表</a></li>
							<li><a href="javascript:uCont('user/toAddressBookList/');">通讯录</a></li>
						</ul>
					</li>
				</ul>
				<div class="user_div fl">
					<div class="user_name" id="user_name"><i></i><span>${userName}<b class="sanj"></b></span>
						<div class="user_pop">
		        		   <i class="stand user_pop_triangle" style="position:absolute;"></i>
		        		   <div class="user_pop_inner">
		        		        <ul>
		        		    		<li id="personal_m"><b></b>个人信息</li>
		        		    		<li id="update_password" onclick="pwdUpdateOpen();"><b></b>修改密码</li>
		        		    		<li onclick="doLogout();"><b></b>退出登录</li>
		        		    	</ul>
		        		   </div>
		        	    </div>
					</div>
					<div class="messages"  id="comment_div_JX">
					</div>
				</div>
			</div>
		</div>
	</div>
	
