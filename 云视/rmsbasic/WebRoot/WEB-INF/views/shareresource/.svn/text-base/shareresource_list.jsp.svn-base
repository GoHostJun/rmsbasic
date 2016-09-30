<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/shareresource/shareresource.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/css/shareresource/tanchuang.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/mCustomScrollbar/jquery.mCustomScrollbar.min.css"/>
<script type="text/javascript" src="${ctx }/common/plugins/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="${ctx }/common/js/global.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/qrcode/qrcode.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js" ></script>
<script src="${ctx }/js/shareresource/shareresource.js" type="text/javascript" charset="utf-8"></script>
<div class="search_div">
	<input type="text" id="search" placeholder="请输入关键字搜索">
    <a href="javascript:getList();;"><i></i>搜索</a>
    <div class="clear"></div>
</div>
<div class="inner resource-list">
	<div class="tab">
		<ul>
			<li class="active">新闻通联</li>
			<li>新闻外场</li>
		</ul>
	</div>
	<div class="city-list">
		<ul id="citylist">
			<li class="active" >全部</li>
		</ul>
	</div>
	<div class="news-style-div">
		<div class="clearfix news-tl">
			<div class="fl left-list" id="outter">
				<ul class="left-list-ul" id="listDiv">
				</ul>
				<div id="pageDiv"></div>
			</div>
			<div id="inner" style="width: 731px;float:left">
   			 </div>		
			<div class="fr right-cont">
				 <div class="public-title">
				 	新闻每日播出稿件
				 	<a href="" id="dailyNoticeMore" target="_blank">更多</a>
				 </div>
				 <div class="yx-gj">
				 	<ul id="dailyNotice">
				 	</ul>
				 </div>
				 <div class="public-title">
				 	策划与报道重点
				 	<a href="" id="focusNoticeMore" target="_blank" >更多</a>
				 </div>
				 <div class="ch-bd">
				 	<ul id="focusNotice">
				 	</ul>
				 </div>
				 <div class="public-title">
				 通联量排行榜
				 </div>
				 <div class="clearfix tll_paihang" id="newsCount">
				 	<ul class="fl">
				 	</ul>
				 	<ul class="fl">
				 	</ul>
				 </div>
				 <div class="public-title">
				 点击量排行榜
				 </div>
				 <div class="click-paihang">
				 	<ul id="newsClickSort">
				 	</ul>
				 </div>
			</div>
		</div>
		<div class="clearfix news-wc">
			<div id="sharecoutsize"></div>
		</div>
	</div>
</div>
<div class="popup news-popup" id="detail">
	<div class="popup_inner">
		<i class="close_btn" id="closeDetial"></i>
		<h2 id="title"></h2>
		<ul class="news-dtmsg">
			<li id="uutime"></li>
			<li>记者：<span id="reporter"></span></li>
			<li>供片台：<span id="tvStation"></span></li>
			<li>创建人：<span id="createor"></span></li>
		</ul>
		<div class="clearfix">
			<div class="fl text-cont">
			<div id="flashDiv">
			</div>
				<div id="docs" class="news-detail">
				</div>
			</div>
			<div class="fr right-cont">
				 <div class="public-title">
				 素材列表
				 </div>
				 <ul class="sucai-list" id="videoAudioDiv">
				 </ul>
			</div>
		</div>
		<div class="newstoolbar" id="rightopre">
	    	
		</div>	
	</div>
</div>
