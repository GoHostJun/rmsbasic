<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/uenotIndex.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/docs/docs_edit.css">
<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/validatorEngine/css/validationEngine.jquery.css">
<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/flash/swfobject.js" ></script>
<script src="${ctx }/plugin/ueditor/ueditorDocs.config.js" charset="utf-8"></script>
<script src="${ctx }/plugin/ueditor/ueditor.all.min.js" charset="utf-8"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script src="${ctx }/plugin/ueditor/lang/zh-cn/zh-cn.js" charset="utf-8"></script>
<script src="${ctx }/plugin/ueditor/ueditor_common.js" charset="utf-8"></script>
<script type="text/javascript"  src="${ctx }/common/plugins/validatorEngine/js/languages/jquery.validationEngine-zh_CN.js" ></script>
<script type="text/javascript" src="${ctx }/common/plugins/validatorEngine/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx }/js/news/news_edit.js"></script>
<script type="text/javascript" src="${ctx }/js/news/news_material_list.js"></script>
<title>云报道</title>
<input type="hidden" id="saveFlag">
<p class="p_url"><i></i><a href="javascript:toHome();" >首页</a> > <a href="javascript:uCont('news/toNewsMyCreateList/');">通联</a> > <a href="javascript:window.location.reload();">通联编辑</a></p>
<div class="check_cont">
	<div class="inner">
        <div class="check_div">
        	<div class="check_left fl">
        		<form name="form" id="form">
	                <div class="wengao_edit_div">
					        <div>
					            <ul class="wengao_edit_nav fl">
					                <li class="active">电视</li>
					                <li>微信</li>
					                <li>微博</li>
					            </ul>
					            <span class="fr made-man stand">创建人：<span id="createor" > </span></span>
					            <div class="clear"></div>
					        </div>
					        <div class="wengao_edit_inner">
					            <div class="input_div title_input_div wg-input-bg">
					                <p class="wen_title bit">标题<span>*</span></p>
					                <input type="text" id="title"  class="validate[required,maxSize[50]]">
					            </div>
					            <div class="wg-input-bg">
					                <div class="input_div">
					                    <p class="bit">栏目</p>
					                    <select name="program" id="program">
					                    </select>
					                </div>
					                <div class="input_div">
					                    <p class="bit">供片台</p>
					                    <select name="tvStation" id="tvStation">
					                    </select>
					                </div>
					                <div class="input_div">
					                    <p>记者</p>
					                    <input type="text" id="reporter" >
					                </div>
					                <div class="input_div">
					                    <p>通讯员</p>
					                    <input type="text" id="repProviders">
					                </div>
					                <div class="extended-div">
					                    <span class="fr extended">扩展属性<i></i></span>
					                    <div class="clear">
					
					                    </div>
					                    <div class="extended-drop">
					                        <div class="input_div">
					                            <p class="bit">分类</p>
					                            <select name="customType" id="customType">
					                            </select>
					                        </div>
					                        <div class="input_div">
					                            <p>播报时间</p>
					                            <input type="text" value="" id="playDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen',isShowClear:false,minDate:'%y-%M-%d'});" class="Wdate">
					                        </div>
					                        <div class="input_div">
					                            <p>摄像</p>
					                            <input type="text" id="cameraMan">
					                        </div>
					                        <div class="input_div">
					                            <p>所属频道</p>
					                            <input type="text" id="source">
					                        </div>
					                        <div class="input_div">
					                            <p>播音</p>
					                            <input type="text" id="dubbingMan">
					                        </div>
					                        <div class="input_div">
					                            <p class="bit">文稿类型</p>
					                            <select name="titleType" id="titleType">
					                            </select>
					                        </div>
					                        <div class="input_div">
					                            <p>题花</p>
					                            <input type="text" id="titleDesign">
					                        </div>
					                        <div class="input_div">
					                            <p>主持人</p>
					                            <input type="text" id="presenter">
					                        </div>
					                        <div class="input_div">
					                            <p>协作</p>
					                            <input type="text" id="assistants">
					                        </div>
					                        <div class="input_div">
					                            <p>关键词</p>
					                            <input type="text" id="keyWords">
					                        </div>
					                        <div class="input_div">
					                            <p>字幕词</p>
					                            <input type="text" id="subtitleWords">
					                        </div>
					                        <div class="input_div">
					                            <p>特约记者</p>
					                            <input type="text" id="specialReporters">
					                        </div>
					                        <div class="input_div">
					                            <p>编辑：</p>
					                             <input type="text" id="editor">
					                        </div>
					                        <div class="input_div last-p">
					                            <p>Video ID：</p>
					                            <span>20160218AB51</span>
					                        </div>
					                    </div>
					                </div>
					
					            </div>
					
					            <div class="big_input_div wg-input-bg">
					               <div style="height: 32px;">	<p class="bit fl">文稿</p></div>
					                <div class="clear"></div>
					                <div class="controls" style="">
									    <div>
											<script id="docsEditor" type="text/plain" style="width:100%;height:250px;"></script>
									    </div>
									    <input type="hidden" id="docsContent" name="noticeContent" datatype="*1-4000" nullmsg="请输入1公告内容！" errormsg="内容长度1不能超过4000个字符！" /> 
									    <input type="hidden" id="docsContentHTML" name="docsContentHTML" />
							        </div>
					            </div>
					            <div class="clear"></div>
					        </div>
					       
					        <div class="edit_btn_div">
					            <a  href="javascript:;" onclick="saveNews();" class="save_btn">保存</a>
					            <div class="clear"></div>
					        </div>
					    </div>
                </form>
            </div>
            <div class="check_right fr">
            	<div class="video_div">
					<div id="draggable">
						<div class="bfqloading" id="previewFlashContent">
							<div id="player">
							</div>				
						</div>
					</div>
				</div>
            	
                <div class="video_audio_div" id="avInfo">
                	<h3>视音频<a href="javascript:;" onclick="showMaterial(0)"><i class="add_img"></i>添加</a></h3>
                 	<ul></ul>
                    <div class="clear"></div>
                </div>
                
                <div class="video_audio_div" >
               		 <div id="picInfo">
	                	<h3>图片<a href="javascript:;" onclick="showMaterial(1)"><i class="add_img"></i>添加</a></h3>
	                   
	                    <div class="clear"></div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<div class="add_alert hide">
	<div class="add_alert_div">
    	<h3 class="close_icon_h"><i></i></h3>
    	<div class="search_div">
            <input type="text" id="search" placeholder="请输入关键字搜索">
            <input type="hidden" id="materialTypeName">
            <a href="javascript:;" onclick="getListData(1,6)"><i></i>搜索</a>
            <div class="clear"></div>
        </div>
    	<div class="list_cont">
			<div class="alert_inner" id="outter">
				<div id="listDiv">
				</div>
				<div class="alert_bottom">
				    <div id="pageDiv" ></div> 
				    <div>
						<a href="javascript:;" onclick="addMaterial();" class="alert_true_btn">确定</a>
						<a href="javascript:;" onclick="hideConfirm();" class="alert_cancel_btn">取消</a>
					</div>					 
				</div>
			</div>
			<div id="inner">
    		</div>
		</div>
    </div>
</div>

