<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script src="${ctx }/plugin/ueditor/notice.ueditor.config.js" charset="utf-8"></script>
<script src="${ctx }/plugin/ueditor/ueditor.all.min.js" charset="utf-8"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script src="${ctx }/plugin/ueditor/lang/zh-cn/zh-cn.js" charset="utf-8"></script>
<script src="${ctx }/js/notice/notice_list.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/notice/notice.css"/>

               <div class="gg_div gg_list">
                    <h5><i></i>公告列表</h5>
                    <div class="gg_cont">
                        <div class="gg_top_left fl">
                            <input type="text" id="title" placeholder="请输入标题">
                            <input type="text" id="time_begin" class="input_time" placeholder="请选择开始时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"> &nbsp;至&nbsp;
                            <input type="text" id="time_end" class="input_time" placeholder="请选择终止时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});">
                        </div>
                        <div class="btn_div fr">
                            <a href="javascript:;" class="chaxun" id="search_id">查询</a>
                            <a href="javascript:;" class="add">添加公告</a>
                            <a style="display: none" id="noticety" href="javascript:uContDiv('system/toPage/?view=noticetype/noticetype_list','div_system_content');" >修改类型</a>
                            <a href="javascript:;" class="shanchu" onclick="deleteNotices();">批量删除</a>
                        </div>
                        <div class="clear"></div>
                        <div id="system_notice_list">
                        </div>
                         <%@include file="/common/pages/page.jsp"%>
                    </div>
                    
                </div>
               
              <!--新建公告-->
              <div class="gg_div add_gg_div hide">
                  <h5><i></i>新建公告</h5>
                  <div class="gg_cont add_gg" style="">
                   	  <input type="hidden" id="notice_id" value="" />
                      <div class="gg_input_div">
                          <p>公告类型：</p><select id="noticeTypeId" name="noticeTypeId"></select>
                          <p>标题：</p><input type="text" id="gg_name" value=""><span id="notice_title">*</span>
                      </div>
                      <%--
                      <div class="gg_input_div">
                          <p>内容：</p><textarea id="gg_content"></textarea>
                      </div>
                      --%>
                <div class="control-group">
					<p class="control-label" style="">内容：</p>
					<div class="controls" style="">
						<div>
							<script id="editor" type="text/plain" style="width:100%;height:250px;"></script>
						</div>
						
						<input type="hidden" id="noticeContent" name="noticeContent" datatype="*1-4000" nullmsg="请输入1公告内容！" errormsg="内容长度1不能超过4000个字符！" /> 
						<input type="hidden" id="noticeContentHTML" name="noticeContentHTML" />
					</div>
				</div>
                      <div class="add_btn_div">
                          <a href="javascript:;" class="tijiao_btn" onclick="saveNotice();">提交</a>
                          <a href="javascript:;" class="quxiao_btn" onclick="hideWindows();">取消</a>
                      </div>
                  </div>
              </div>
