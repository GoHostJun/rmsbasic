<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/directpass/directpass.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/pushtask/pushtask_list.js"></script>
<title>云报道</title>
<p class="p_url">
	<i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('system/toPage/?view=pushtask/pushtask_list');">任务列表</a>
</p>
 <div class="record_content">
			<div class="recode_inner">
        <div class="recode_search_div clearfloat">
          <div class="search_what fl">
            <p>任务名称<input type="text" id="title"></p>
            <p>创建时间
            <input type="text" id="starttime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss',skin:'whyGreen',isShowClear:false});" class="Wdate">
            <span>至</span>
       	   <input type="text"  id="endtime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss',skin:'whyGreen',isShowClear:false});" class="Wdate">
            </p>
          </div>
          <div class="recode_search_btn fr">
            <a href="javascript:;" class="search_recode" id="search_direct">查询</a>
            <a href="javascript:;" class="search_reset" id="reset_direct">重置</a>
          </div>
        </div>
        <div class="recode_bottom" id="outter">
          <div class="recode_table_div" id="pushtask_list">
          </div>
           <%@include file="/common/pages/page.jsp" %>
        </div>
        <div id="inner">
        </div>
      </div>
	</div>
