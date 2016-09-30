<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script src="${ctx }/js/transcode/transcode.js" type="text/javascript" charset="utf-8"></script>
<title>系统设置--转码模板</title>
<div id="content" class="sys_cont">
	<div class="sys_cont_inner">
		<div class="veCon_zhuanma">
			<div class="veConLeft_title">
				<h5 class="fl">
					<i></i>转码模板设置
				</h5>
				<div class="veConZm_sel fr" id="veConZm_sel">
					<i class=" fl"></i> <span>全选</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="veConZm_cont">
			<div id="template_list_id">
			
			</div>
				<div class="clear"></div>
			</div>
			<div>
				<ul class="veCen_ul">
					<b></b>
					<li><i></i>视频上传后的自动转码将默认采用此处设置好的转码模板;</li>
					<li><i></i>为了保证视频画面质量，系统将只转小于等于视频文件原有分辨率的模板类型，例如：原视频文件分辨率为640×370，那么将最多只转PC流畅、Mobile标清、Mobile流畅三种模板类型。</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>


