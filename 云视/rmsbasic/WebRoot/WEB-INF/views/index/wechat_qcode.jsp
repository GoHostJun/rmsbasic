<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<script>
</script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/index/wechatqcode.css">
<%--<script type="text/javascript" src="${ctx }/js/directpassingpath/directpassingpath_choose.js"></script>--%>
<script>
$(function(){
	var onairMap = new OnairHashMap();
	onairMap.put("userId", userId);
	$.ajax({
		type : 'POST',
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		url : ctx  + "/api/pub/selfEvent/",
		success : function(data) {
			$("#weChat").attr("src",data);
		},
		error : function() {
			
		}
	});
	
	
})
var interVal=setInterval("getUserIn()", 5000);

function getUserIn() {
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userId", userId);
	$.ajax({
		url : ctx + pathValue + "org/getUserInfoByUserId/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				if(!data.data){
					return ;
				}
				//微信绑定状态
				if(data.data.weChatUnionid!=undefined&&data.data.weChatUnionid!=""){
					 toastr.success("绑定成功！");
					clearInterval(interVal);
				}
			}
		},
		error : function(e){}
	});
}
</script>
<!DOCTYPE>
<html>
  <head>

  </head>
  <body>
	<div class="outdiv">
		<div class="popup-cont">
			<img id="weChat" >
		</div>
		<p>微信扫码，完成账户绑定</p>
	</div>
  </body>
</html>
