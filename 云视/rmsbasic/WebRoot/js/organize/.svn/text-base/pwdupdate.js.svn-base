$(document).ready(function() {
	$("#password").blur(function(){
  		var password = $("#password").val();
  		if(!password){
			$("#passwordSpan").html("新密码不能为空");
		}else{
			if(password.length < 6){
				$("#passwordSpan").html("新密码不能小于6位");
			}
		}
		
	});
	$("#rPassword").blur(function(){
		var password = $("#password").val();
  		var rPassword = $("#rPassword").val();
  		if(!rPassword){
			$("#passwordSpan").html("确认新密码不能为空");
		}else{
			if(rPassword.length < 6){
				$("#passwordSpan").html("确认新密码不能小于6位");
			}else{
				if(password != rPassword){
					$("#passwordSpan").html("确认新密码错误");
				}else{
					$("#passwordSpan").html("");
				}
			}
		}
  		
	});
});



/**
 * 保存新密码
 */
function saveNewPwd() {
	var password = $("#password").val();
	var rpassword = $("#rPassword").val();
	if(!password){
		$("#passwordSpan").html("新密码不能为空");
		return;
	}
	if(password.length < 6){
		$("#passwordSpan").html("新密码不能小于6位");
		return;
	}
	if(!rpassword){
		$("#passwordSpan").html("确认新密码不能为空");
		return;
	}
	if(rpassword.length < 6){
		$("#passwordSpan").html("确认新密码不能小于6位");
		return;
	}
	if(password != rpassword){
		$("#passwordSpan").html("确认新密码错误");
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("password",password);
	onairMap.put("userId", userId);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathValue + "org/updatePassword/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			if (0 == data.code) {
				toastr.success("修改密码成功!");
				layer.closeAll('page');
			} else {
				toastr.error("修改密码失败!");
			}
		},
		error : function() {
			layer.close(index);
			toastr.error("操作异常!");
		}
	});
}