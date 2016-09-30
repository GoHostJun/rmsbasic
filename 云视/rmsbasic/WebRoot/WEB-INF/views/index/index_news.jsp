<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx }/common/plugins/maps/js/maps.js"></script>
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
					<span
						class="li_bottom_bg fr"></span>
				
				</div>	</a></li>
			<li>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_media">
						<p>视音频</p>
					</div>
				</div>
				<a id="materialMain" href="#"  onclick="setNavigation(0,2);" target="_blank">
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<span
						class="li_bottom_bg fr"></span>
					
				</div>
				</a></li>
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
				   </div>
				</a></li>
			<li><img src="${ctx }/common/images/unicon.png" id="un_deal" style="display:none;"/>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_news">
						<p>新闻通联</p>
					</div>
				</div>
				<a id="tongLianMain" href="#"  onclick="setNavigation(1,4);" target="_blank">
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<span class="li_bottom_bg fr"></span>
				</div></a>
				<div class="tl-up" id="mengban"  style="display:none">

					<ul class="tl-up-inner">
						<li class="tl-up-lf"  id="topushid" onclick="waitting_to_push();" style="display:none">
							<i></i>
							<p>待推送</p>
						</li>
						<li class="tl-up-rg" id="toauditid" onclick="waitting_to_audit();" style="display:none">
							<i></i>
							<p>待审核</p>
						</li>
				    </ul>

				</div>
				</li>
		</ul>
	</div>
	<div class="content_main">
		<div class="cont_left fl">
			<div class="public_tile">
				<b></b> 地图
				<p class="stand fr">
					<i onclick="getUnDealCityTasks();"></i><span><a href="javascript:void(0);"  onclick="toPage('news/toNewsMyWaitingSendListIndex/');">More&nbsp;></a></span>
				</p>
			</div>
			<div class="cont_left_inner map_div">
				<!-- 获取地图信息   style="display:none"-->
    			 <img src="${ctx }/common/images/map.png"/>
    			 <div class="i_coord i_coord1" id="5334" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5334');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord2" id="5333" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5333');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord3" id="5305" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5305');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord4" id="5331" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5331');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord5" id="5307" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5307');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord6" id="5329" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5329');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord7" id="5309" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5309');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord8" id="5323" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5323');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord9" id="5308" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5308');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord10" id="5328" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5328');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord11" id="5301" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5301');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord12" id="5304" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5304');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord13" id="5325" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5325');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord14" id="5306" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5306');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord15" id="5303" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5303');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
                 <div class="i_coord i_coord16" id="5326" name="yunnan" style="display:none"  onclick="toPage('news/toNewsMyWaitingSendListIndex/?areacode=5326');">
                 	<img src="${ctx }/common/images/pos1.png" class="pos1">
                     <img src="${ctx }/common/images/pos2.png">
                 </div>
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
		initHref();
		initDocsTotle();
		initVideoAudioTotle();
		initPicTotle();
		initNewsTotle();
		judgeToDeal();
		judgeToDealForPush();
		getNewNotices(1,3);
		getUnDealCityTasks();
	//	setInterval("getUnDealCityTasks()",5000);//1000为1秒钟 
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