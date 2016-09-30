<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script src="${ctx }/js/logs/logs_list.js" type="text/javascript" charset="utf-8"></script>
               <div class="gg_div gg_list">
                    <h5><i></i>日志列表</h5>
                    <div class="gg_cont">
                        <div class="gg_top_left fl">
                            <input type="text" id="cusename" placeholder="请输入用户名称">
                            <input type="text" id="title" placeholder="请输入描述">
                            <input type="text" id="time_begin" class="input_time" placeholder="请选择开始时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"> &nbsp;至&nbsp;
                            <input type="text" id="time_end" class="input_time" placeholder="请选择终止时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});">
                        </div>
                        <div class="btn_div fr">
                            <a href="javascript:;" class="chaxun" id="search_id">查询</a>
                        	<a href="javascript:;" class="chaxun" onclick="exportLogs()">导出</a>
                        </div>
                        <div class="clear"></div>
                        
                        <div id="system_logs_list">
                        </div>
                        <%@include file="/common/pages/page.jsp"%>
                    </div>
                </div>
               
	