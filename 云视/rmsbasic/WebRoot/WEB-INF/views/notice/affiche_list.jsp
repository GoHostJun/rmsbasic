<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
	<title>云报道</title>
	<script src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
	<script type="text/javascript" src="${ctx }/js/notice/notice.js"></script>
	<script>
	var noticeTypeId="<%=request.getParameter("noticeTypeId")%>";
	</script>
		<div class="search_div">
			<input type="text" id="search" placeholder="请输入关键字搜索"> <a href="javascript:;" id="search_id"><i></i>搜索</a>
			<div class="clear"></div>
		</div>
		<p class="p_url">
			<i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:;">公告列表</a>
		</p>
		<div class="list_cont inner">
			<div class="affiche_list" id="div_system_content">
			</div>
			<%@include file="/common/pages/page.jsp"%>
	
		</div>
