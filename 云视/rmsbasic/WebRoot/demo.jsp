<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/layer/skin/layer.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/common/plugins/toastr/toastr.min.css">
	<script type="text/javascript" src="${ctx }/common/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx }/common/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx }/common/js/global.js"></script>
	<script type="text/javascript" src="${ctx }/common/plugins/toastr/toastr.min.js"></script>

  <head>
    <title>My JSP 'demo.jsp' starting page</title>
  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
<script>
	$(function() {
		//初始化global组件
		init();
		toastr.error("内容123456","标题");
		toastr.info("内容123456","标题");
		toastr.success("内容123456","标题");
		toastr.warning("内容123456","标题");
		toastr.success("只提示内容123456");
		layer.confirm('您是如何看待前端开发？', {
		    btn: ['重要','奇葩'] //按钮
		}, function(i){
		alert(i)
		   layer.close(i)
		}, function(){
		   
		});
		//初体验
		//layer.alert('内容')
		//提示层
		//layer.msg('玩命提示中');
		//loading层
		//var index = layer.load(1, {
		//    shade: [0.1,'#fff'] //0.1透明度的白色背景
		//});
	});
</script>