<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="${ctx }/css/sidebar/sidebar.css">
<script type="text/javascript" src="${ctx }/js/index/index.js"></script>
<script type="text/javascript" src="${ctx }/js/index/hotevent.js"></script>
<script type="text/javascript" src="${ctx }/js/sidebar/sidebar.js"></script>


<head>
<title>首页 - 云报道</title>

</head>

<body>
 <input type="hidden" id="directpathIndex">    
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content"></div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
	<!-- 	右侧浮动标签-结束 -->
	<div class="sidebar_div" id="indexPop" >
		<ul>
			<li>
				<a href="#" target="_blank" id="toDoc">
					<i class="sidebar_icon1"></i>
					<p>新建<br>文稿</p>
					<span class="line"></span>
				</a>
			</li>
			<li>
				<a href="#"  id="uploadmaterial">
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
							<img name="clintImg" >
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
					<p class="three_word" >服务号</p>
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

		<ul class="sucaiji_btn">
			<li>
				<a href="#" id="tomaterialset">
					<i class="sidebar_icon5"></i>
					<p>新建<br>素材集</p>
				</a>
			</li>

		</ul>
	</div>
	
	<!--  又不需要改了
		<div class="code_hover" id="indexJX" >
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
</body>
</html>
<script>
	$(function() {
		//初始化global组件
		init();
		if(getUrlConfig.nameTV=="JS"){
			uCont("news/toJSIndex/");
		}else{
			
			//uCont("news/toNews/");
			uCont("news/toJXIndex/");
		}
		$("#indexPop").find("li").each(function(i){
			if(i==0||i==1){
				$(this).hide();
			}
			
		});
		getMyNewsCommentList();
		$("#toDoc").attr("href",ctx+pathPage+"material/toAddDocs/");
		
		if(userRole=="管理员"){
			$(".sucaiji_btn").show();
			if(getUrlConfig.nameTV!="JS"){
				$(".sucaiji_btn").append('<li name="jsuploadmaterial">'+
						'<a href="#" >'+
						'<i class="sidebar_icon6"></i>'+
						'<p>素材<br>直传</p>'+
					'</a>'+
				'</li>')
			}
		}
		//弹出新增客户窗口
	$("#tomaterialset").click(function (){
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : ctx + pathPage + "system/toPage/?view=materialset/materialset_add",
				cache : false,
				async : false,
				success : function(data) {
					layer.open({
						type : 1, // page层
						area: ['600px','400px'],
						title : "新增素材集",
						shade : 0.5, // 遮罩透明度
						moveType : 1, // 拖拽风格，0是默认，1是传统拖动
						shift : 0, // 0-6的动画形式，-1不开启
//						maxmin : true,
						scrollbar : false,
						//offset: ['300px', '200px'],
						content : data
					});
				},
				error : function() {
					$("#table_body").html("查询出错了...");
				}
			});
		})
	});
	
	$("#uploadmaterial").click(function(){
		$("#upload_cont").css("visibility","visible");
		$(".min_upload_icon").hasClass("max_upload_icon")&&$(".min_upload_icon").removeClass("max_upload_icon");
		$(".upload_cont").hasClass("max")&&$(".upload_cont").removeClass("max");
	})

</script>
<script type="text/javascript" src="${ctx }/js/index/index_jiangsu.js"></script>
