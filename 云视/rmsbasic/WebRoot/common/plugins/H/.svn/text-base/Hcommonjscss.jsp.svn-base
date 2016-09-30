<%@page import="com.cdvcloud.upload.util.SpringUtil"%>
<%@page import="com.cdvcloud.rms.common.UserAuthentication"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<%
	UserAuthentication userAuthentication = (UserAuthentication)SpringUtil.getInstance("userAuthentication");
	boolean status = userAuthentication.getAccessToken(request, "loginId");
%>
<script>
var userId="<%=request.getSession().getAttribute("userId")%>";
var consumerId="<%=request.getSession().getAttribute("consumerId")%>";
var areaCode="<%=request.getSession().getAttribute("areaCode")%>";
var accessToken="<%=request.getSession().getAttribute("accessToken")%>";
var userRole="<%=request.getSession().getAttribute("userRole")%>";
var uname = "${userName}";
</script>
<link href="${ctx }/common/plugins/H/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx }/common/plugins/H/bootstrap/css/bootstrap.min14ed.css" rel="stylesheet">
<link href="${ctx }/common/plugins/H/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="${ctx }/common/plugins/H/css/animate.min.css" rel="stylesheet">
<link href="${ctx }/common/plugins/H/css/index.css" rel="stylesheet">
<link href="${ctx }/common/plugins/H/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<link href="${ctx }/common/plugins/H/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
<script src="${ctx }/common/plugins/H/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx }/common/plugins/H/plugins/layer/layer.min.js"></script>
<script src="${ctx }/common/plugins/H/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx }/common/plugins/H/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx }/common/plugins/H/js/plugins/layer/layer.min.js"></script>
<script src="${ctx }/common/plugins/H/js/hplus.min.js?v=4.1.0"></script>
<script type="text/javascript" src="${ctx }/common/plugins/H/js/contabs.min.js"></script>
<script src="${ctx }/common/plugins/H/js/plugins/pace/pace.min.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/H/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="${ctx }/common/plugins/hashmap/onairmap-v0.9.js"></script>
<script type="text/javascript" src="${ctx }/common/js/global.js"></script>


