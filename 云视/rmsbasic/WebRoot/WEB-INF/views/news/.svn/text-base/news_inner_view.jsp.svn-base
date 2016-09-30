<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script>
var _id="<%=request.getParameter("_id")%>";
var type="<%=request.getParameter("type")%>";
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/docs/docs_edit.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/news/news_view.css">
<script src="${ctx }/js/news/news_view.js"></script>
<script src="${ctx }/flash/swfobject.js" ></script>
<title>云报道</title>
<style>
.extended-div {position:relative;}
.extended {position:absolute;top:40px;right:10px;}
.made-person {margin-top:14px;font-size:14px;}
</style>
<p class="p_url"><i></i><a href="javascript:toHome();" >首页</a> > <a id="newsList" href="javascript:;">新闻通联</a> > <a href="javascript:window.location.reload();">通联查看</a></p>
<div class="check_cont">
	<div class="inner">
        <div class="btn_div fr" >
        	
        </div>
        <div class="clear"></div>
        <div class="check_div">
        	<div class="check_left fl">
                <div class="wengao_div" id="wengao">
                    <h3>文稿信息
	                    <a href="javascript:;"  onclick="copyNews()"><i class="copy_img"></i>复制</a>
	                    <a href="javascript:;"  id="edit"  onclick="editNews()"><i class="edit_img"></i>编辑</a>
                    </h3>
                     <div class="border_div" style="border-bottom:1px solid #ccc;">
                    <div>
                    	<div class="fl wg-title">
                    		<h4><span id="title"></span></h4>
                    	</div>
                    	<div class="fr made-person">
                    		<span id="createor">创建人：</span>
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
                		<div class="clear"></div>
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
                 <div class="message_div">
                    <div class="public_tile"id="public_tile">
                    <b></b>
                    通联信息
                    </div>
                    <div class="cont_left_inner">
                        <div class="send">
                        <input type="" name="" id="" value="评论…" placeholder="评论…" class='comment'/><b  onclick="talk(this)"></b>
                        </div>
                        <div class="dialogue_cont" id="dialogue">
                        						   
                        				   
                    	</div>
                    </div>
                </div>
            </div>
            <div class="check_right fr">
            	<div id="draggable"></div>
                <div class="sh_div">
                	
                </div>
                <div class="sh_div join">
                	
                </div>
               
                <div class="video_audio_div">
                	<h3>视音频</h3>
                	<div id="avInfo">
                		<ul></ul>
                	</div>
                    <div class="clear"></div>
                </div>
                
                <div class="video_audio_div">
                	<h3>图片<a href="#"></a></h3>
                	<div id="picInfo"></div> 
                    
                    <div class="clear"></div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<div class="popup audit_through_popup">
	<div class="popup_inner">
		<p class="top">审核<i class="close_btn"></i></p>
		<div class="popup_cont">
			<p>是否确认审核通过？</p>
			<div class="audit_opinipn">
				<textarea  placeholder="输入审核意见" ></textarea>
			
			</div>
			<div class="edit_btn_div">
                <a href="javascript:;" class="save_btn"  onclick="agree(this)">是</a>
                <a href="javascript:;" class="empty_btn" >否</a>
                <div class="clear"></div>
            </div>
		</div>
	</div>
</div>
<div class="popup audit_back_popup">
	<div class="popup_inner">
		<p class="top">审核<i class="close_btn"></i></p>
		<div class="popup_cont">
			<p>是否确认审核打回？</p>
			<div class="audit_opinipn">
			<textarea  placeholder="输入审核意见" ></textarea>
			
			</div>
			<div class="edit_btn_div">
                <a href="javascript:;" class="save_btn" onclick="disAgree(this)">是</a>
                <a href="javascript:;" class="empty_btn">否</a>
                <div class="clear"></div>
            </div>
		</div>
	</div>
</div>
<div class="popup audit_cancel_popup">
	<div class="popup_inner">
		<p class="top">通联<i class="close_btn"></i></p>
		<div class="popup_cont">
			<p>是否取消通联？</p>
			<div class="edit_btn_div">
                <a href="javascript:;" class="save_btn" onclick="cancelNews()">是</a>
                <a href="javascript:;" class="empty_btn">否</a>
                <div class="clear"></div>
            </div>
		</div>
	</div>
</div>
<div class="popup audit_add_popup">
	<div class="popup_inner">
		<p class="top">添加通联人<i class="close_btn"></i></p>
		<div class="popup_cont">
			<p>是否确认添加通联人？</p>
			<div class="edit_btn_div">
                <a href="javascript:;" onclick="addJoin()" class="save_btn">是</a>
                <a href="javascript:;" class="empty_btn">否</a>
                <div class="clear"></div>
            </div>
		</div>
	</div>
</div>
<div class="popup audit_sub_audit">
	<div class="popup_inner">
		<p class="top">通联提交审核<i class="close_btn"></i></p>
		<div class="popup_cont">
			<p>是否提交审核？</p>
			<div class="edit_btn_div">
                <a href="javascript:;" onclick="subAudit()" class="save_btn">是</a>
                <a href="javascript:;" class="empty_btn">否</a>
                <div class="clear"></div>
            </div>
		</div>
	</div>
</div>



