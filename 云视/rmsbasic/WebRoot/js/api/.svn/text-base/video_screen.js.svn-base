$(window).load(function() {
	$(".load1").fadeOut();
})
/* ***************横屏竖屏************************ */
function orient() {

	if (window.orientation == 0 || window.orientation == 180) {

		$(".load3").remove();
	} else if (window.orientation == 90 || window.orientation == -90) {// 横屏

		var html_phone = $('<div class="load3"><div class="rotate"><i></i> <div class="phone_div"><img src="'+ctx+'/images/api/phone.png" class="iphone"></div> <div><p class="phone_p">请调整到适合屏幕阅读方向</p></div> </div></div>');
		html_phone.appendTo("body");
		$(".load3").fadeIn();

	}
}
// 用户变化屏幕方向时调用
$(window).bind('orientationchange', function(e) {
	orient();
});
