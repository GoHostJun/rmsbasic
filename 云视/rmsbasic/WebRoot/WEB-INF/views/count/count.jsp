<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
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
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<a id="docMain" href="#" target="_blank"><span
						class="li_bottom_bg fr"></span>
					</a>
				</div></li>
			<li>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_media">
						<p>视音频</p>
					</div>
				</div>
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<a id="materialMain" href="#" target="_blank"><span
						class="li_bottom_bg fr"></span>
					</a>
				</div></li>
			<li>

				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_picture">
						<p>图片</p>
					</div>
				</div>
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<a id="picMain" href="#" target="_blank"><span
						class="li_bottom_bg fr"></span>
					</a>
				</div></li>
			<li><img src="${ctx }/common/images/unicon.png" id="un_deal" style="display:none;"/>
				<div class="li_cont">
					<span class="li_cont_bg"></span>
					<div class="li_num fr"  id="div_news_news">
						<p>新闻通联</p>
					</div>
				</div>
				<div class="li_bottom">
					<p>VIEW MORE</p>
					<a id="tongLianMain" href="#" target="_blank"><span
						class="li_bottom_bg fr"></span>
					</a>
				</div></li>
		</ul>
	</div>
</div>
<script>
	$(function() {
		$("#ascrail2000").remove();
		initDocsTotle();
		initVideoAudioTotle();
		initPicTotle();
		initNewsTotle();
	});
	
</script>