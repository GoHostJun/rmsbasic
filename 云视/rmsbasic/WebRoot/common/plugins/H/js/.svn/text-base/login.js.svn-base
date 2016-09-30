$(function(){
	$(".submit_button").click(function() {
		if ($(".username").val() == "") {
			$('.username').focus();
			$("#error").css("display", "block").html("<p>用户名不能为空!</p>");
			return;
		}
		if ($(".password").val() == "") {
				$('.password').focus();
			$("#error").css("display", "block").html("<p>密码不能为空!</p>");
			return;
		}

		$.ajax({
			type: 'POST',
			url: ctx + '/login/checklogin/',
			cache: false,
			data: 'username=' + $(".username").val() + '&password=' + $(".password").val(),
			success: function(response) {
				if (response == "true") {
					window.location.href = ctx + "/login/tologin/";
				} else {
					$("#error").css("display", "block").html("<p>账户名与密码不匹配，请重新输入</p>");
				}

			},
			error: function(a, b, c) {

			}
		});

	}) /**回车事件**/
	document.onkeydown = function(evt) {
		var evt = window.event ? window.event : evt;
		if (evt.keyCode == 13) {
			$(".submit_button").click();
		}
	}
	/**窗口宽高自适应*/
	$(window).resize(function(){
		$(".login_wrap,.wrap_bg").css({"height":$(window).height()});
	})
})