<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<script>
var _id="<%=request.getParameter("_id")%>";
var title="<%=request.getParameter("title")%>";
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/docs/create_tl_popup.css">
<script type="text/javascript" src="${ctx }/js/docs/news_createmodify_common.js"></script>
<script type="text/javascript" src="${ctx }/js/docs/news_create.js"></script>

<!DOCTYPE>
<html>
  <head>

  </head>
  
  <body>
			<div class="popup-inner tuisong-pop">
				<div class="popup-cont">
					<h2>任务名称：<span id="title"></span></h2>
					<div class="clearfix">
					<div class="fl">
						<div class="search-div">
							<input type="" name="" id="searchInput" value="" placeholder="请输入关键字搜索"/>
							<span id="searchPeople">搜&nbsp;索</span>
							<i></i>
						</div>
						<div class="choice-div">
							<select name="firstDep">
								<option value="">全部</option>
							</select>
							<select name="secondDep">
								<option value="">请选择</option>
							</select>
							<select name="thirdDep">
								<option value="">请选择</option>
							</select>
							<div class="add-list" id="userList">
								<ul>
<%--									<p>暂无审核成员</p>--%>
								</ul>
							</div>
						</div>
						<div class="select-input clearfix">
							<div class="fl"><i class="active" onclick="toggleActive(this)" id="shareActive"></i>所有人可见</div>
							<div class="edit_btn_div clearfix fr">
				                <a href="javascript:;" class="save_btn">确定</a>
				                <a href="javascript:layer.closeAll();;" class="empty_btn">取消</a>
          					 </div>	
						</div>
					</div>
					<div class="fr">
						<div class="leaguer lt-leaguer active">
							<p class="title">通联成员</p>
							<div class="leaguer-cont" id="tonglian_man">
								<ul>
<%--									<p>暂无审核成员</p>--%>
								</ul>
							</div>
						</div>
						<div class="leaguer sh-leaguer">
							<p class="title">审核成员</p>
							<div class="leaguer-cont" id="auditing_man">	
								<ul>
<%--								<p>暂无审核成员</p>--%>
								</ul>
							</div>
						</div>
					</div>
				</div>
				</div>
				
			</div>
  </body>
</html>
