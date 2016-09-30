<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/sidebar/sidebar.css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link  rel="stylesheet" type="text/css" href="${ctx }/css/news/newsMyWaitingSend_list.css">
<script type="text/javascript" src="${ctx }/common/plugins/qrcode/jquery.qrcode.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/qrcode/qrcode.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/docs/docs_list.js"></script>
<script type="text/javascript" src="${ctx }/js/sidebar/sidebar.js"></script>
<title>云报道</title>
<div class="search_div">
	<input type="text" id="search" placeholder="请输入关键字搜索"> <a style="cursor:pointer" onclick="getListData(1,10)"><i></i>搜索</a>
	<div class="clear"></div>
</div>
<p class="p_url">
	<i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toDocs/');">文稿</a>
</p>
<div class="list_cont">
	<div class="inner" id="outter">
		<p class="check_p">
			<input type="checkbox" class="choice_all" id="checkAll">全选 
			<a href="javascript:;" id="delete"><i class="del_img"></i>删除</a>
			<div class="clear"></div>
		</p>
		<div id="listDiv"></div>
		<div id="pageDiv"></div>
	</div>
	<div id="inner">
    </div>
</div>
<!-- 右侧悬浮按钮江苏版本 -->
<div class="sidebar_div">
		<ul>
			<li>
				<a href="#" target="_blank" id="addDocs">
					<i class="sidebar_icon1"></i>
					<p>新建<br>文稿</p>
					<span class="line"></span>
				</a>
			</li>
			<li>
				<a href="#"  id="uploadFile">
					<i class="sidebar_icon2"></i>
					<p>上传<br>素材</p>
					<span class="line"></span>
				</a>
			</li>
			
			<li>
				<a href="javascript:;">
					<i class="sidebar_icon3"></i>
					<p class="three_word">客户端</p>
					<span class="line"></span>
					<div class="code_cont clearfloat hide">
						<div class="code_div fl">
							<img  name="clintImg" >
						</div>
						<div class="about_code fr">
							<h4>手机客户端</h4>
							<p><i></i>扫二维码下载</p>
							<p><i></i>让工作变得更加简单、快乐</p>
						</div>
					</div>
				</a>
			</li>
			<li>
				<a href="javascript:;">
					<i class="sidebar_icon4"></i>
					<p class="three_word">服务号</p>
					<div class="code_cont clearfloat hide">
						<div class="code_div fl">
							<img src="${ctx}/images/sidebar/wx.jpg">
						</div>
						<div class="about_code fr">
							<h4>微信服务号</h4>
							<p class="clearfloat"><i></i><span>扫二维码下载</span></p>
							<p class="clearfloat"><i></i><span>随时随地掌握最新动态</span></p>
						</div>
					</div>
				</a>
			</li>
		</ul>
	</div>
	
		<!-- 右侧悬浮按钮其他版本
		<div class="code_hover"  >
			<div class="app_code">
				<i class="app_code_icon"></i>
				<div class="code_cont2 clearfloat">
					<div class="code_div2">
						<div class="code_img fl">
							<img src="${ctx }/images/index/code.jpg">
						</div>
						<div class="about_code2 fr">
							<h3>手机客户端</h3>
							<p>· 扫二维码下载</p>
							<p>· 让工作变得更加简单、快乐</p>
						</div>
					</div>
					<b></b>
				</div>
			</div>
			<div class="app_code">
				<i class="wx_code_icon"></i>
				<div class="code_cont2 clearfloat">
					<div class="code_div2">
						<div class="code_img fl">
							<img src="${ctx }/images/index/code.jpg">
						</div>
						<div class="about_code2 fr">
							<h3>手机客户端</h3>
							<p>· 扫二维码关注</p>
							<p>· 随时随地掌握最新动态</p>
						</div>
					</div>
					<b></b>
				</div>
			</div>

		</div>
		 -->
