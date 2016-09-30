<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link  rel="stylesheet" type="text/css" href="${ctx }/css/news/newsMyWaitingSend_list.css">
<script src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script src="${ctx }/js/news/news_list_common.js"></script>
<script src="${ctx }/js/news/newsComplete_list.js"></script>
<title>云报道</title>
<div class="search_div">
	<input type="text" id="search" placeholder="请输入关键字搜索">
    <a href="javascript:;" id="searchButton"><i></i>搜索</a>
    <div class="clear"></div>
</div>
<p class="p_url material_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('news/toNewsCompleteList/');">我的任务</a></p>
<p class="material_choice">
	类型：<span onclick="uCont('news/toNewsMyWaitingDealList/');" >待审核</span><span onclick="uCont('news/toNewsMyWaitingSendList/');">待推送</span><span onclick="uCont('news/toNewsCompleteList/');" class="active">已完成</span>
</p>
<div class="list_cont">
	<div class="inner" id="outter">
    	<p class="check_p">
        	<i class="choice_all"></i>全选
            <a href="javascript:;" class="a_hui"><i class="del_img del_img_hui"></i>删除</a>
            <!--<a href="#"><i class="kb_img"></i>快编</a>-->
            <div class="clear"></div>
        </p>
		<div id="inner_list">
        </div>          
        <%@include file="/common/pages/page.jsp" %>
    </div>
    <div id="inner">
    </div>
</div>

