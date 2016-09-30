<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/count/report.css">
<script src="${ctx }/js/echarts/asset/js/esl/esl.js"></script>
<script
src="${ctx }/js/echarts/example/www/js/echarts.js"></script>
<script src="${ctx }/js/count/report.js"></script>
<title>云报道</title>
<p class="p_url"><i></i><a href="javascript:toHome();" >首页</a> > <a href="javascript:uCont('count/toReport/');">统计分析</a></p>
<div class="gg_cont inner">
	<div class="gg_top_left fl">
		<label>日期</label>
<%--		<select id="per" class="gg_select">--%>
<%--			<option value="week">本周</option>--%>
<%--			<option value="month">本月</option>--%>
<%--			<option value="year">本年</option>--%>
<%--		</select>--%>
		<input type="text" id="beginDate" class="input_time" placeholder="请选择开始时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd '});"> &nbsp;至&nbsp;
		<input type="text" id="endDate" class="input_time" placeholder="请选择终止时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd '});">
	    <div class="btn_div fr">
             <a href="javascript:;" class="chaxun" id="search_id">查询</a>
        </div>
	</div>
</div>
  <div class="inner tonglian-report-form">
	<!-- 分类标准：转码成功、转码失败 -->
	<div class="tonglian-report-state fl"  id="list_cont"></div>
	<div class="tonglian-report-state fl"  id="list_cont2"></div>
	<div class="tonglian-report-state fl"  id="list_cont3"></div>
	<div class="clear"></div>
</div>       
