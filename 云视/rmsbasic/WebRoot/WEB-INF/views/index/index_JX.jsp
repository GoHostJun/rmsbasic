<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
	<link rel="stylesheet" href="${ctx}/common/plugins/fullPage/jquery.fullPage.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/index/index_JX.css">
	<script type="text/javascript" src="${ctx}/common/plugins/fullPage/jquery.fullPage.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/index/index_JX.js"></script>
	<style>
	</style>
	<div class="jx_index_wrap">
		<!-- 滚屏 -->
		<div id="swiper_div">
			<!-- 第一屏 -->
			<div class="section section1" id='screenfirst'>
				<i class="mouse_show"></i>
				<div class="big_btn_cont clearfloat">
					<div class="big_btn_div new_manuscript">
						<a href="javascript:;" id="uploadmaterialJX">
							<i></i>
							<p>上传素材</p>
						</a>
					</div>
					<div class="big_btn_div upload_material">
						<a href="javascript:;" id="toDocJX" target="_blank" >
							<i></i>
							<p>新建文稿</p>
						</a>
					</div>
					<!-- 没有消息时,跳到我的任务页面 -->
					<div class="big_btn_div task_alert">
						<a href="javascript:;">
							<i></i>
							<p>消息提醒</p>
						</a>
					</div>
					<!-- 有待审核提醒 -->
					<div class="big_btn_div task_alert examine_alert hide">
						<a href="javascript:;"  onclick=" waitting_to_audit()">
							<i></i>
							<p>待审核</p>
							<b></b>
						</a>
					</div>
					<!-- 有待推送提醒 -->
					<div class="big_btn_div task_alert push_alert hide">
						<a href="javascript:;"  onclick="waitting_to_push()">
							<i></i>
							<p>待推送</p>
							<b></b>
						</a>
					</div>
					<!-- 有待审核和待推送提醒 -->
					<div class="big_btn_div task_alert two_task_alert hide">
						<div class="hover_before">
							<i></i>
							<p>消息提醒</p>
							<b></b>
						</div>
						<div class="hover_after clearfloat hide">
							<div class="examine_part">
								<a href="javascript:;"  onclick=" waitting_to_audit()">
									<i></i>
									<p>待审核</p>
									<b></b>
								</a>
							</div>
							<div class="push_part">
								<a href="javascript:;"  onclick="waitting_to_push()">
									<i></i>
									<p>待推送</p>
									<b></b>
								</a>
							</div>
						</div>
					</div>
				</div>
					
			</div>
			<!-- 第一屏结束 -->
			<!-- 第二屏 -->
			<div class="section section2">
				<div class="infor_cont clearfloat">
					<div class="infor_left">
						<h2 class="title"><span>信息推荐<i></i></span></h2>
						<div class="infor_div">
							<h3><i class="work_icon"></i>我的工作
								<a href="javascript:uCont('news/toNewsCompleteList/');">MORE ></a>
							</h3>
							<ul class="my_work_cont">
								<li>
									<a href="javascript:uCont('news/toNewsMyCreateList/');">
										<p class="fl"><i class="work_icon1"></i>未发布</p>
										<p class="fr"><span id="myCreateIndex"></span>条</p>
									</a>
								</li>
								<li>
									<a href="javascript:uCont('news/toNewsMyWaitingDealList/');;">
										<p class="fl"><i class="work_icon2"></i>待审核</p>
										<p class="fr"><span id="waitingDealIndex"></span>条</p>
									</a>
								</li>
								<li>
									<a href="javascript:uCont('news/toNewsMyWaitingSendList/');">
										<p class="fl"><i class="work_icon3"></i>待推送</p>
										<p class="fr"><span id="waitingSendIndex"></span>条</p>
									</a>
								</li>
							</ul>
						</div>
						<div class="infor_div notice_div">
							<h3><i class="notice_icon"></i>最新公告
								<p>
<%--								<b></b><span></span>--%>
								</p>
								<a href="javascript:;" id="noticeMainJX">MORE ></a>
							</h3>
							<div class="notice_list" id="ul_news_noticesJX">
								
							</div>
						</div>
					</div>
					<div class="infor_right">
						<div class="infor_div news_infor_div">
							<h3><i class="news_icon"></i>热点新闻
								<a href="javascript:;" onclick="getMoreHotEvent();">MORE ></a>
							</h3>
							<ul id="ul_news_hotevents_JX">
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- 第二屏结束 -->
			<!-- 第三屏 -->
			<div class="section section3">
				<div class="news_circle">
					<h2 class="title"><span>新闻圈子<i></i></span></h2>
					<div class="news_circle_list clearfloat" id="shareNewsIndex">
					</div>
				</div>
			<%@include file="/common/pages/footerJXIndex.jsp"%>
			</div>
			<!-- 第三屏结束 -->
		</div>
	</div>
	
