// JavaScript Document
$(function(){
	$(".sign_in").css({"height":$(window).height()});
	//登陆回车事件
	$('#user_password').keydown(function(e){
		if(e.keyCode==13){
   			 doLogin();
	}
}); 
	clearInputValue();

   if ($.cookie("rmbUser") == "true") {
    $("#zd").attr("checked", true);
    $("#user_name").val($.cookie("username"));
    $("#user_password").val($.cookie("password"));
    }
});

/**
 * 用户登录
 */
function doLogin(){
	var txtName = $("#user_name").val();
	var txtPwd = $("#user_password").val();
	var info = "";
	if (!txtName) {
		$(".tishi").html("<p>用户名不能为空！</p>");
		$("#user_name").focus();
		return;
	}else if(!txtPwd){
		$(".tishi").html("<p>密码不能为空！</p>");
		$("#user_password").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("userName", txtName);
	onairMap.put("password", txtPwd);
	$.ajax({
		url : ctx +"/rms/user/checkUserInfo/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if("success" == data.message){
				Save();
				//成功，则跳转到主页
				window.location=ctx + "/index.jsp";
				
			} else {
				var msg = "";
				if (data.code == 10004) {
					msg = "<p>用户名或密码错误！</p>";
				} else if ("unuse" == data) {
					msg = "<p>该用户已被停止使用！</p>";
				}
				$(".tishi").html(msg);
			}
		}
	});
}

	
/**
 * 光标移出，检查数据非空
 * @param {} id
 */
function checkData(id){
	var txtName = $("#user_name").val();
	var txtPwd = $("#user_password").val();
	var keyPwd = $("#keyPwd").val();
	var msg = "";
	if(id == "user_name"){
		if(!txtName){
			msg = "<p>用户名不能为空！</p>";
		}
	}else if(id == "user_password"){
		if(!txtPwd){
			msg = "<p>密码不能为空！</p>";
		}
	}
	$(".tishi").html(msg);
}
	
/**
 * 清除input里的信息
 */
function clearInputValue(){
	$("#user_name").val("");
	$("#user_password").val("");
	$("#keyPwd").val("");
}

    //记住用户名密码
    function Save() {
        if ($('#zd').is(':checked')) {
            var str_username = $("#user_name").val();
            var str_password = $("#user_password").val();
            $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
            $.cookie("username", str_username, { expires: 7 });
            $.cookie("password", str_password, { expires: 7 });
        }else {
            $.cookie("rmbUser", "false", { expire: -1 });
            $.cookie("username", "", { expires: -1 });
            $.cookie("password", "", { expires: -1 });
        }
    };
    //20160422微信登录

	$(function(){
		$(".qr-login").click(function(){
			if($(this).hasClass("pclogin")){
				$(this).removeClass("pclogin").addClass("wxlogin");				
			}
			else{			
				$(this).addClass("pclogin").removeClass("wxlogin");
			}		
		})
//		$(".weixin-login p").mousedown(function(){
//			$(this).addClass("active");
//		})
//		$(".weixin-login p").mouseup(function(){
//			$(this).removeClass("active");
//		})
		$(".qr-login").click(function(){
			$(".pc-login-div").toggle();
			$(".wx-login-div").toggle();
		})
	})
	function openWeixin(){
	//window.open(ctx  +'/api/auth/login/?code=021e4f34c12d4b4459a169948fe77eaR&state=STATE')
	$.ajax({
		url : ctx  +'/api/auth/redistWeChatOpen/',
		cache : false,
		success : function(data) {
			window.open(data);
		}
	});
}

function openQQ(){
	$.ajax({
		url : ctx  +'/api/auth/redistQQOpen/',
		cache : false,
		success : function(data) {
			window.open(data);
		}
	});
}