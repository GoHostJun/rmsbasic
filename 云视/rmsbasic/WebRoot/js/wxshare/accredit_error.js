$(function(){
	$("#bindUser").click(function(){
		bindUser();
	})
	$("#closePop").click(function(){
		$("#errorBind").hide();
	})
	var append="&_id="+shareId
		  +"&title="+title+"&imgUrl="+imgUrl+"&subbmit="+subbmit+"&loginid="+loginid
		  +"&nikename="+nikename;
	$("#viewDetail").click(function(){
		$("#successBind").hide();
		if("clues"==type||"myclues"==type){
			window.location.href=ctx+"/api/share/getMyCueView/?shareId="+shareId+"&userId="+userId+"&consumerId="+consumerId+"&type="+type
			+append;
		}else if("doc"==type){
			window.location.href=ctx+"/api/share/getMyDocView/?shareId="+shareId+"&userId="+userId+"&consumerId="+consumerId+"&type="+type+append;
		}else if("zyclues"==type){
			window.location.href=ctx+"/api/share/getZYCueView/?shareId="+shareId+"&userId="+userId+"&consumerId="+consumerId+"&type="+type+append;
		}
	})
	
})
var userId,consumerId;
function getTimeStamp() {
	return new Date().getTime()+"";
}

function bindUser(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", "");
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userName", $("#username").val());
	onairMap.put("password", $("#password").val());
	onairMap.put("phone", $("#phone").val());
	onairMap.put("headimgurl", headimgurl);
	onairMap.put("openid", openid);
	onairMap.put("unionid", unionid);
	onairMap.put("nickname", nickname);
	var url="";
	if("YN"!=getUrlConfig.nameTV && "JS"!=getUrlConfig.nameTV){
		url=ctx + "/api/wechat/bindUserWechatNotLoginNotCas/";
	}else{
		url=ctx + "/api/wechat/bindUserWechatNotLogin/";
	}
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url :url ,
		data : onairMap.toJson(),
		cache : false,
		success : function(data) {
			if (0 == data.code) {
				$("#successBind").show();
				$("#bindNum").text("账号："+data.data.email)
				consumerId=data.data.consumerid;
				userId=data.data._id;
			} else if(80001== data.code){
				$("#errorBind").show();
				$("#error_text").text("您输入的账号已经被绑定，请更换账号");
			}else{
				$("#errorBind").show();
				$("#error_text").text("您输入的账号密码有误，请重新输入");
			}
		},
		error : function() {
			
		}
	});

	
}
