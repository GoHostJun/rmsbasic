$(function(){
	document.onkeydown = function(evt) {
		var evt = window.event ? window.event : evt;
		if (evt.keyCode == 13) {
			$("#submit").click();
		}
	}
});
function validateLock(){
	var type = $("#type").val();
	var pwd = $("#password").val();
	$.ajax({
		type: 'POST',
		url: ctx+'/lock/checklogin/',
		cache: false,
		async : false,
		data:{
			password:pwd
			},
		success: function(response){
			if("true"==response){
				if("template"==type){
					window.location.href=ctx+"/toTemplateList/";
				}
				if("agentManagement"==type){
					window.location.href=ctx+"/toAgentManagementList/";
				}
				if("global"==type){
					window.location.href=ctx+"/toGlobalList/";
				}
				if("thirdpary"==type){
					window.location.href=ctx+"/toThirdparyList/";
				}
			}else{
				$("#gl").html("密码错误！");
				$("#gl").show();
			}
		},
		error:function(a,b,c){
		}
   }); 
}
