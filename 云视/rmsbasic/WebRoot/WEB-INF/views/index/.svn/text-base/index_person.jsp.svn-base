<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx }/common/js/jquery.nicescroll.min.js"></script>
<div class="inner">
	<div class="content_top">
		<ul>
			<li>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr" id="div_news_docs">
						<p>新闻文稿</p>
					</div>
				</div>
				<a id="docMain" href="#" onclick="setNavigation(0,1);" target="_blank">
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<span class="li_bottom_bg fr"></span>
				</div></a></li>
			<li>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_media">
						<p>视音频</p>
					</div>
				</div>
				<a id="materialMain" href="#" onclick="setNavigation(0,2);" target="_blank">
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<span class="li_bottom_bg fr"></span>				
				</div></a></li>
			<li>

				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_picture">
						<p>图片</p>
					</div>
				</div>
				<a id="picMain" href="#"  onclick="setNavigation(0,2);" target="_blank">
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<span class="li_bottom_bg fr"></span>
				</div></a></li>
			<li><img src="${ctx }/common/images/unicon.png" id="un_deal" style="display:none;"/>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_news">
						<p>新闻通联</p>
					</div>
				</div>
				<a id="tongLianMain" href="#"  onclick="setNavigation(1,4);"  target="_blank">
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<span class="li_bottom_bg fr"></span>
				</div></a>
				<div class="tl-up" id="mengban"  style="display:none">

					<ul class="tl-up-inner">
						<li class="tl-up-lf"  id="topushid" onclick="waitting_to_push();"  style="display:none">
							<i></i>
							<p>待推送</p>
						</li>
						<li class="tl-up-rg" id="toauditid" onclick="waitting_to_audit();"  style="display:none">
							<i></i>
							<p>待审核</p>
						</li>
				    </ul>

				</div>
				</li>
		</ul>
	</div>
					
	<div class="content_main personal">
		<div class="cont_left fl">
			<div class="public_tile">
				<b></b> 我的信息
				<p class="stand fr">
					<i  onclick="getMyNewsMessages();"></i>
				</p>
			</div>
			<div id="div_news_message" class="cont_left_inner" style="position:relative;">
			</div>
		</div>
		<div class="cont_right fr">
			<div class="new_notice">
				<div class="public_tile">
					<b></b> 最新公告
					<p class="stand fr">
						<i onclick="getNewNotices(1,3);"></i><span><a id="noticeMain" href="#" target="_blank">More&nbsp;></a></span>
					</p>
				</div>
				<ul id="ul_news_notices">
				</ul>
			</div>
			<div class="hot_event">
				<div class="public_tile">
					<b></b> 热点事件
					<p class="stand fr">
						<i onclick="getHotEvent();"></i><span onclick="getMoreHotEvent();">More&nbsp;></span>
					</p>
				</div>
				<ul id="ul_news_hotevents">
				</ul>
			</div>

		</div>
		<div class="clear"></div>
	</div>
</div>
<script>
	$(function() {
		
		$("#div_news_message").niceScroll({cursorwidth:"2px", cursorcolor:"#398F70"});
		initHref();
		initDocsTotle();
		initVideoAudioTotle();
		initPicTotle();
		initNewsTotle();
		judgeToDeal();
		judgeToDealForPush();
		getNewNotices(1,3);
		getMyNewsMessages();
		getHotEvent();
	});
	// 刷新点击旋转
	$(function(){
		var ifRotate=true;
		$('.content_main .public_tile i').click(function(){
		    if(ifRotate){
		        ifRotate=false;
		        $(this).addClass('rotate');
		        setTimeout(function(){
		           $('.content_main .public_tile i').removeClass('rotate');
		            ifRotate=true;
		        },1000);      
		    }    
		});
	})
	
	

</script>
