
$(function(){
	$("#headImg").attr("src",head);
	$("#nickname").text(nickname);
	
	if(type=="weChat"){
		$("#qqOrWechatP").text("已使用微信登录");
	}else if(type=="qq"){
		$("#qqOrWechatP").text("已使用qq登录");
	}
	function win_resize(){
	var win=$(window); 
	
	$(".wrap").css({"width":"100%","height":$(win).height()});
	}
	win_resize();
	/**窗口缩放监听事件**/
	$(window).resize(function(){
		win_resize();
	})
	$(".login-btn").mousedown(function(){
		$(this).addClass("active");
	})
})
function bindLogin(){
	var url ;
	if(type=="weChat"){
		url=ctx +'/api/auth/bindWeChat/'
	}else if(type=="qq"){
		url=ctx +'/api/auth/bindQQ/'
	}
	var txtName = $("#user_name").val();
	var txtPwd = $("#user_password").val();
	var onairMap = new OnairHashMap();
	if (!txtName) {
		$("#tipError").find("p").text("用户名不能为空！");
		$("#tipError").css("visibility","visible");
		$("#user_name").focus();
		return;
	}else if(!txtPwd){
		$("#tipError").find("p").text("密码不能为空！");
		$("#tipError").css("visibility","visible");
		$("#user_password").focus();
		return;
	}
	var onairMap = new OnairHashMap();
	onairMap.put("userName", txtName);
	onairMap.put("password", txtPwd);
	var options={
			url:url,
			data:onairMap.toJson(),
			success:function(data){
				if(0 == data.code){
					toastr.success('绑定成功');
					toHome();
				}
				var msg = "";
				if (data.code == 10004) {
					if(data.data!=null&&data.data!=""){
						toastr.error(data.data.error);
					}else{
						$("#tipError").find("p").text("账号或密码错误！");
						$("#tipError").css("visibility","visible");
					}
				} else if ("unuse" == data) {
					$("#tipError").find("p").text("该用户已被停止使用！");
					$("#tipError").css("visibility","visible");
				}
			},
			error:function(){
				toastr.error('用户更新头像失败');
			}
			
	}
	$ajax(this,options);
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
				$("#tipError").find("p").text("用户名不能为空！");
				$("#tipError").css("visibility","visible");
			}
		}else if(id == "user_password"){
			if(!txtPwd){
				$("#tipError").find("p").text("密码不能为空！");
				$("#tipError").css("visibility","visible");
			}
		}
	}