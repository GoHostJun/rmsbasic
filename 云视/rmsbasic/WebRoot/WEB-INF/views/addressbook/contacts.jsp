<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<title>云报道</title>
<link rel="stylesheet" type="text/css" href="${ctx }/common/css/notIndex.css">
        <link rel="stylesheet" type="text/css" href="${ctx }/css/addressbook/contacts.css"/>
		<script type="text/javascript" src="${ctx }/common/plugins/page/onairpage-v0.9.js"></script>
		<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
        <script type="text/javascript" src="${ctx }/js/addressbook/contacts.js"></script>
		<div class="contacts_cont">
            <div class="search_div fl">
                <input type="text" id="search_input" class="search_input" placeholder="请输入关键字搜索">
                <a href="javascript:;" class="search_btn" onclick="getListData(1,10);">搜索</a>
            </div>
            <div class="a_btn_div fr">
                <a href="javascript:;" class="a_btn new_btn">同步</a>
                <a href="javascript:;" class="a_btn dao_btn">导入</a>
            </div>
            <div class="clear"></div>
            <div id="addresslist_id">
            </div>
        </div>
	<div id="pageDiv"></div> 