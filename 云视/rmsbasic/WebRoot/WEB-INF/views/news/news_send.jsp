<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/news/news_create.css">
<script src="${ctx }/js/news/news_send.js"></script>
<script src="${ctx }/js/news/news_list_common.js"></script>

<title>云报道</title>

<p class="p_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('news/toNewsMyWaitingSendList/');">待推送</a> > <a href="javascript:;">推送</a></p>
<div class="tonglian_title" id="docDiv">
</div>
<div class="tonglian_cont">
	<div class="inner">
        <div class="download_cont">
            <div class="download_title">
              <a href="javascript:;" onclick="toSend()">
	           		<div class="down_title_logo fl">
	               		<img src="${ctx }/common/images/title_logo.png">
	               </div>
               </a>
	                <div class="down_title_right fl">
	                	<h5 id="title"></h5>
	                    <p id="sendStatus">
	                    </p>
	                </div>
               
                <div class="clear"></div>
            </div>
            <ul class="tonglian_nav down_nav">
                <li class="active"><a href="javascript:;">下载</a></li>
                <div class="clear"></div>
            </ul>
            <div class="download_div">
                <div class="down_div">
                    <p>含文稿信息</p>
                    <i class="download_icon1"></i>
                    <span>预览码率</span>
                </div>
                <div class="down_div">
                    <p>含文稿信息</p>
                    <i class="download_icon2"></i>
                    <span>播出</span>
                </div>
                <a href="javascript:download4zip();" class="down_btn fr"><i></i>下载全部</a>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>

