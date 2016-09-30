<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/commonjscss.jsp"%>
<%
String sharedocId = request.getParameter("sharedocId");
%>
<!DOCTYPE HTML>
<html>
<link rel="stylesheet" type="text/css" href="${ctx }/css/docs/docs_edit.css">
<script type="text/javascript" src="${ctx }/common/plugins/upload/js/upload.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js" ></script>
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/js/shareresource/sharedocs_edit_share.js"></script>
<script type="text/javascript" src="${ctx }/js/docs/doc_addedit_common.js"></script>
<head>
<title>云报道</title>
<style>
.extended-div {position:relative;}
.extended {position:absolute;top:40px;right:10px;}
.made-person {margin-top:0;font-size:14px;}
</style>
</head>

<body>
	<%@include file="/common/pages/header.jsp"%>
	<%@include file="/common/pages/nav.jsp"%>
	<div id="content">
		<p class="p_url"><i></i><a href="javascript:toHome();">首页</a> > <a href="javascript:uCont('material/toDocs/');">文稿</a> > <a href="javascript:;">文稿预览</a></p>
	<div class="check_cont">
		<input id="sharedocId" type="hidden" value="<%=sharedocId%>">
		<input type="hidden" id="src">
		<div class="inner">
	        <div class="check_div">
	        	<div class="check_left fl">
                <div class="wengao_div">
                    <h3>文稿信息
                    	<span id="createor" class="fr made-person">创建人：</span>
                    </h3>
                    <div class="border_div" style="border-bottom:1px solid #ccc;">
                    <div>
                    	<div class="fl wg-title">
                    		<h4><span id="title"></span></h4>
                    	</div>
                    	
                    	<div class="clear">
                    		
                    	</div>
                    </div>
                    <p><span id="program">栏目：</span></p>
                    <p><span id="tvStation">供片台：</span></p>
                    <p><span id="reporter">记者：</span></p>
                    <p><span id="repProviders">通讯员：</span></p>
                    <div class="extended-div" >
                		<span class="fr extended">扩展属性<i></i></span>
                		<div class="extended-drop wg-preview">
                			<p><span id="customType">分类：</span></p>
		                    <p><span id="playDate">播报时间：</span></p>
		                    <p><span id="cameraMan">摄像：</span></p>
		                    <p><span id="source">所属频道：</span></p>
		                    <p><span id="dubbingMan">播音：</span></p>
		                    <p><span id="titleType">文稿类型：</span></p>
                			<p><span id="titleDesign">题花：</span></p>
		                    <p><span id="presenter">主持人：</span></p>
		                    <p><span id="assistants">协作：</span></p>
		                    <p><span id="keyWords">关键词：</span></p>
		                    <p><span id="subtitleWords">字幕词：</span></p>
		                    <p><span id="specialReporters">特约记者：</span></p>
		                    <p><span id="editor">编辑：</span></p>
		                    <p><span>Video ID：</span> 20160218AB51</p>
                		</div>
                		<div class="clear"></div>
                	</div>
                	</div>
                	<div id="docs" class="doc-word-break">
                		<p style="float:none;margin-top:16px;">文稿</p>
                	</div>
                </div>
                 <div class="edit_btn_div">
					            <a  href="javascript:;" onclick="addDocs();" class="save_btn">保存</a>
					            <div class="clear"></div>
					        </div>
	             </div>
	           <div class="check_right fr">
						<div class="video_div">
<%--							<p>预览区</p>--%>
							<div class="bfqloading" id="previewFlashContent">
							<div id="player">
<%--								<img src="${ctx }/common/images/video.jpg" width="100%">--%>
							</div>				
							</div>
						</div>
  						<div class="upload-local-btn" id="uploadFile">
                           
                        </div>
						<div class="video_audio_div" id="">
							<h3>
								视音频<a href="javascript:;" class="addVideo"><i class="add_img"></i>添加</a>
							</h3>
							<div id="videoAudioDiv" class="clearfix">
								<ul>
								</ul>
							</div>																			
							
						</div>

						<div class="video_audio_div">
							<h3>
								图片<a href="javascript:;" class="addPic"><i class="add_img"></i>添加</a>
							</h3>
							<div id="picDiv" class="clearfix"></div>																											
							<div class="clear"></div>
						</div>
					</div>
	            <div class="clear"></div>
	        </div>
	    </div>
	</div>
</div>
	<%@include file="/common/pages/footer.jsp"%>
	<%@include file="/common/plugins/gotop/gotop.jsp"%>
	<div class="add_alert hide" >
		<input id="mediaType" type="hidden" value="">
		<div class="add_alert_div">
			<h3 class="close_icon_h">
				<i></i>
			</h3>

			<div class="search_div">
				<input type="text" id="search" placeholder="请输入关键字搜索"> <a style="cursor:pointer" onclick="getDataList(1,6)" href="javascript:;"><i></i>搜索</a>
				<div class="clear"></div>
			</div>
			<div class="list_cont">
				<div class="alert_inner" id="outter">
					<div id="listDiv">
					</div>
					<div class="alert_bottom">
					    <div id="pageDiv" ></div> 
					    <div>
						<a href="javascript:;" onclick="processConfirm();" class="alert_true_btn">确定</a>
						<a href="javascript:;" onclick="hideConfirm();" class="alert_cancel_btn">取消</a>
						</div>					 
					</div>
				</div>
				<div id="inner">
    			</div>
			</div>
		</div>
	</div>
</body>
<script>
	$(function() {
		init();
	});
</script>
</html>
