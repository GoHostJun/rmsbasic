<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx }/common/plugins/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx }/js/nav/nav.js"></script> 
<div class="nav header" >
	<div class="inner">
		<div class="logo fl">
			<a href="javascript:toHome();"><img id="navImg" > </a>
		</div>
		<div class="user_div fr">
			<div id="user_menu">
				<span id="user_menu_span">${userName}</span>
				<i class="user_menu_i" style="display:inline-block;position:relative;">
					<div class="user_pop" style="position:absolute;">
	        		   <i class="stand user_pop_triangle" style="position:absolute;"></i>
	        		   <div class="user_pop_inner">
	        		        <ul>
	        		    		<li id="personal_m"><b></b>个人信息</li>
	        		    		<li id="update_password" onclick="pwdUpdateOpen();"><b></b>修改密码</li>
	        		    		<li onclick="doLogout();"><b></b>退出登录</li>
	        		    	</ul>
	        		   </div>
	        	    </div>
				</i>
			</div>
			<div class="msg_btn" id="comment_div">
           	</div>
		</div>
		<div class="nav_div fr" >
			<a href="javascript:uCont('material/toDocs/');"><i class="i1"></i>我的素材</a>
			<a href="javascript:uCont('news/toNewsMyCreateList/');"><i class="i2"></i><span id="nav_div_span">新闻通联</span></a>
			<a href="javascript:uCont('shareresource/toShareList/');"><i class="i6"></i>资源共享</a>
			<a href="javascript:uCont('count/toReport/');"><i class="i3"></i>统计分析</a>
			<a href="javascript:uCont('system/toSetting/?toModule=notice');"><i class="i4"></i>系统设置</a>
			<a href="javascript:uCont('user/toAddressBookList/');"><i class="i5"></i>通讯录</a>
		</div>
		<div class="clear"></div>
	</div>
	<div class="nav_line"></div>
	<ul class="down_list">
		<li class="down_list1">
			<ul>
				<a href="javascript:uCont('material/toDocs/');"><li>文稿</li> </a>
				<a href="javascript:uCont('material/toMaterial/');"><li>素材</li> </a>
			</ul>
		</li>
		<li>
			<ul >
				<a href="javascript:uCont('news/toNewsMyCreateList/');"><li><span id="down_list_ul_span">我的通联</span></li> </a>
				<a href="javascript:uCont('news/toNewsCompleteList/');"><li>我的任务</li> </a>
			</ul>
		</li>
	</ul>
</div>