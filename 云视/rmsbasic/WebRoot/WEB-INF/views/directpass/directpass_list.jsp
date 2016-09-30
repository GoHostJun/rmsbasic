<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/system/system.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/directpass/directpass.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/directpass/directpass_list.js"></script>
<title>云报道</title>
<p class="p_url">
	<i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('directpass/toDirectPassList/');">直传</a>
</p>
 <div class="record_content">
			<div class="recode_inner">
        <div class="recode_search_div clearfloat">
          <div class="search_what fl">
            <p>文件名称<input type="text" id="filename"></p>
            <p>上传时间
            <input type="text" id="starttime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen',isShowClear:false});" class="Wdate">
            <span>至</span>
       	   <input type="text"  id="endtime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen',isShowClear:false});" class="Wdate">
            </p>
            <p>目标路径
	            <select id="directpath">
	              <option>新闻中心NTM</option>
	              <option>新媒体中心存储</option>
	              <option>城市频道资源库</option>
	            </select>
            </p>
          </div>
          <div class="recode_search_btn fr">
            <a href="javascript:;" class="search_recode" id="search_direct">查询</a>
            <a href="javascript:;" class="search_reset" id="reset_direct">重置</a>
          </div>
        </div>
        <div class="recode_bottom" id="outter">
          <div class="recode_table_div" id="directpass_list">
          </div>
           <%@include file="/common/pages/page.jsp" %>
        </div>
        <div id="inner">
        </div>
      </div>
	</div>
