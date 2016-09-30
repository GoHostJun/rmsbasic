<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/plugins/H/Hcommonjscss.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<meta name="viewpoint" content="width=device-width initial-scale=1.0">
<script type="text/javascript" src="${ctx }/js/index/index.js"></script>
<title>云报道管理系统</title>

</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow:hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="nav-close">
			<i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li class="nav-header">
					<div class="dropdown profile-element">
						<span><img alt="image" class="img-circle"
							src="${ctx }/common/plugins/H/img/onair_logo.png" />
						</span> <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
							class="clear"> <span class="block m-t-xs"><strong
									class="font-bold">${userName}</strong>
							</span> <span class="text-muted text-xs block">系统管理员<b
									class="caret"></b>
							</span> </span> </a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
<%--							<li><a class="J_menuItem" href="form_avatar.html">修改头像</a></li>--%>
<%--							<li><a class="J_menuItem" href="profile.html">个人资料</a></li>--%>
<%--							<li><a class="J_menuItem" href="contacts.html">联系我们</a></li>--%>
<%--							<li><a class="J_menuItem" href="mailbox.html">信箱</a></li>--%>
<%--							<li class="divider"></li>--%>
							<li><a href="javascript:;" onclick="doLogout();">安全退出</a></li>
						</ul>
					</div>
					<div class="logo-element">云报道管理系统</div>
				</li>
				<li><a href="#"> <i class="fa fa-home"></i> <span
						class="nav-label">素材管理</span> <span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toMaterial/"
							data-index="0">素材列表</a></li>
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toEmegency/"
							data-index="0">任务列表</a></li>
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toScanlog/"
							data-index="0">扫描日志</a></li>
					</ul>
				</li>
				<li><a href="#"> <i class="fa fa-gears"></i> <span
						class="nav-label">系统管理</span> <span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toCustom/">客户列表</a>
						</li>
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toConfig/">配置列表</a>
						</li>
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toScan/">扫描列表</a>
						</li>
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toMaterialtemplate/">过滤模板</a>
						</li>
						<li><a class="J_menuItem" href="${ctx }/rms/custom/toWechatTemp/">微信消息模板</a>
						</li>
					</ul>
				</li>
				<li><a href="#"> <i class="fa fa-gears"></i> <span
						class="nav-label">告警管理</span> <span class="fa arrow"></span> </a>
					<ul class="nav nav-second-level">
					<li><a class="J_menuItem" href="${ctx }/rms/custom/toWeChatMessage/">微信消息列表</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
						href="#"><i class="fa fa-bars"></i> </a>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="#"> <i class="fa fa-envelope"></i>
							<span class="label label-warning">0</span> </a>
						</li>
					<li class="dropdown"><a class="dropdown-toggle count-info"
						data-toggle="dropdown" href="#"> <i class="fa fa-bell"></i> <span
							class="label label-primary">0</span> </a>
						</li>
					<li class="dropdown hidden-xs"><a class="right-sidebar-toggle"
						aria-expanded="false"> <i class="fa fa-tasks"></i> 主题 </a></li>
				</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
				<div class="page-tabs-content">
				<a href="javascript:;" class="active J_menuTab"
						data-id="index_v1.html">首页</a>
				</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="javascript:;" onclick="doLogout();" class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out" ></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="${ctx}/rms/custom/toWelcom/" frameborder="0"
					data-id="index_v1.html" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					<a href="http://www.cdvcloud.com/" target="_blank">北京新奥特云视科技有限公司©2014cdvcloud.com 版权所有</a>
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<ul class="nav nav-tabs navs-3">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> 主题 </a></li>
				</ul>

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu"
											class="onoffswitch-checkbox" id="collapsemenu"> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span> </label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="fixednavbar"
											class="onoffswitch-checkbox" id="fixednavbar"> <label
											class="onoffswitch-label" for="fixednavbar"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span> </label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>

								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="boxedlayout"
											class="onoffswitch-checkbox" id="boxedlayout"> <label
											class="onoffswitch-label" for="boxedlayout"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span> </label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">
										默认皮肤 </a> </span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">
										蓝色主题 </a> </span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">
										黄色/紫色主题 </a> </span>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
		<!--右侧边栏结束-->
</body>
</html>
