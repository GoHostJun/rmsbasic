<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script>
var _id="<%=request.getParameter("_id")%>";
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/news/news_create.css">
<script src="${ctx }/js/news/news_list_common.js"></script>
<script src="${ctx }/js/news/news_createmodify_common.js"></script>
<script src="${ctx }/js/news/news_create.js"></script>
<title>云报道</title>
<p class="p_url"><i></i><a href="javascript:toHome();" >首页</a> > <a href="javascript:uCont('material/toDocs/');">文稿</a> > <a href="javascript:window.location.reload();">创建通联</a></p>

<div class="tonglian_title" id="docDiv">
</div>
<div class="create_cont">
	<div class="modify_cont">
				<div class="modify_left fl">
					<div class="tonglian_man">
						<p>通联@</p>
						<ul>
							<li><span class="tl_name">姓名</span><span class="tl_job">职务</span><span class="tl_handle">操作</span></li>
						</ul>
					</div>
					<div class="make_sel"><span>添加审核人 </span><i class="ture_btn"></i>是<i class="no_btn active"></i>否</div>
					<div class="auditing_man active">
						<p>审核人</p>
						<ul>
							<li><span class="tl_name">姓名</span><span class="tl_job">职务</span><span class="tl_handle">操作</span></li>
						</ul>
					</div>
				</div>
				<div class="modify_right fr">
					<div class="search_btn fr">
					<input type="" name="" id="searchInput" value="" placeholder="请输入关键字搜索"/><span id="search"><img src="${ctx }/common/images/search2.png"/>搜&nbsp;&nbsp;索</span>
					</div>
					<div class="clear"></div>
					<div class="modify_right_cont">
						<div class="fl modify_right_l" id="org">
<!-- 							<ul> -->
<!-- 								<li class="menu2" onclick="menuToggle(this)"><i></i>本台机构</li> -->
<!-- 								<li>新闻部</li> -->
<!-- 								<li>通联部</li> -->
<!-- 								<li>记者</li> -->
<!-- 							</ul> -->
						</div>
						<div class="fr modify_right_r">
							<ul>
								<li><span class="mrr_name">姓名</span><span class="mrr_job">职务</span><span class="mrr_inst">机构</span><span class="mrr_operate">操作</span></li>
							</ul>	
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
   		<div class="bottom_btn">
	 	<div class="ctrl_s_btn">保存</div>
		<div class="reset_btn">重置</div>
	</div>
</div>

