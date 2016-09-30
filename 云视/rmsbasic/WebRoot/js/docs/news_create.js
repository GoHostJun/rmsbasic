$(function(){
	
	$(".leaguer").click(function(){
		$("#searchInput").val('');
		$(this).addClass('active').siblings().removeClass('active');
		var partId;
			if($("[name='thirdDep']").val()==""){
					if($("[name='secondDep']").val()==""){
						if($("[name='firstDep']").val()==""){
							
						}else{
							
							partId=$("[name='firstDep']").find("option:selected").attr("_id");
						}
				}else{
					partId=$("[name='secondDep']").find("option:selected").attr("_id");
				}
			}else{
				partId=$("[name='thirdDep']").find("option:selected").attr("_id");
		}
		loadUserByDept(partId);
	})
	loadDep2();
	loadUserByTypt();
	$("#searchPeople").click(function(){
		loadUserByDept();
	})
	//回车事件
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 loadUserByDept();
        }
    }; 
	$("#title").html('').html(title);
	//点击提交
$(".save_btn").click(function(){
	var joinjson=new Array();
	var dealjson=new Array();
	$.each( $("#tonglian_man li"),function(i,item){
		//if(i==0) return;
		joinjson.push({"userId":$(this).find("[name=userId]").val(),"realname":$(this).find("[name=realname]").val()})
	})
	$.each( $("#auditing_man li"),function(i,item){
		//if(i==0) return;
		dealjson.push({"userId":$(this).find("[name=userId]").val(),"realname":$(this).find("[name=realname]").val()})
	})
//	if(joinjson.length==0){
//		toastr.warning("至少选择一个通联人");
//		return;
//	}
	
	if($(".ture_btn ").hasClass("active")){
		if($(".auditing_man li").length==1){
			toastr.warning("请添加审核人");
			return ;
		}
	}
	layer.confirm('确定创建通联？', {
	    btn: ['确认','取消'] 
	}, function(outIndex){
		createNews(outIndex,joinjson,dealjson);
	}, function(){
		
	})
	
	
})
	
})
function toggleActive(This){
	$(This).toggleClass("active");
}
function createNews(outIndex,joinjson,dealjson){
	layer.close(outIndex);
	var shareNews=1;
	if($("#shareActive").hasClass("active")){
		shareNews=0;
	}
	var strJson={"shareNews":shareNews,"joinjson":joinjson,"dealjson":dealjson,catalogueId:_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	strJson= JSON.stringify(strJson); 
	var index = layer.load();
	$.ajax({
	        'url':ctx+pathValue+"news/createNews/", //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':strJson,
	        'success':function(data){
	        	layer.close(index);
		       	if(data.code==0){
		       		toastr.success("创建通联成功");
			        uCont('news/toNewsMyCreateList/');
			        layer.closeAll();
			        navChangeClass(1,4);
			        navJXChangeClass(4,5);
				 }else{
					 toastr.error("创建通联失败");
				 }
	        } ,
			'error' : function(){
				layer.close(index);
				toastr.error("创建通联失败");
			}
	});
}