$(function(){
	$("#title2").html('').html(title);
	$(".leaguer :eq(0)").click(function(){
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
	init();
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
	//点击提交
$(".save_btn").click(function(){
	addJoin();
})

	
})

//添加通联人保存
function addJoin(){
	 var index = layer.load();
	 var joinjson=new Array();
	 var dealjson=new Array();
	 $.each( $("#tonglian_man li"),function(i,item){
		if($(this).attr("stype")==1) {
			return;}
		joinjson.push({"userId":$(this).find("[name=userId]").val(),"realname":$(this).find("[name=realname]").val(),"position":$(this).find("[name=position]").val()})
	 })
	 var jsonStr={"joinjson":joinjson,catalogueId:_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
	        url:ctx+pathValue+"news/addNewsUser/", //服务器的地址
	        dataType:'json', //返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', //请求类型
	        data:jsonStr,
	        success:function(data){
		        layer.close(index);
		       	if(data.code==0){
		       	   toastr.success("添加成功");
		       	  layer.closeAll();
			       window.location.reload();
				 }else{
				   toastr.error("添加失败");
				 }
		    },
			error : function(){
				layer.close(index);
				toastr.error("添加失败");
			}
		});
}

//初始化通联人和审核人
function init(){
	var index = layer.load();
	var strJson={"newsId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url':ctx+pathValue+"news/findNewsById/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
        	layer.close(index);
        	if(data.code==0){
        		if(data.data.shareuser!=undefined&&data.data.shareuser.length>0){
        			//初始化通联人
        			$.each(data.data.shareuser,function(i,item){
        				$("#tonglian_man").find("ul").append( '<li stype="1" class="clearfix"><span class="fl" >'+item.realname+'</span><input type="hidden" name="userId" value="'+item.userId+'" /><input type="hidden" name="realname" value="'+item.realname+'" /></li>');

//        				$(".tonglian_man").find("ul").append( '<li stype="1"><input type="hidden" name="userId" value="'+item.userId+'"/><input type="hidden" name="realname" value="'+item.realname+'" /><input type="hidden" name="position" value="'+item.position+'" /><span class="tl_name">'+item.realname+'</span><span class="tl_job">'+item.position+'</span><span class="tl_handle"><i class="active"></i></span></li>');
        			})
        		}
        		//初始化审核人
        		if(data.data.checkuser!=undefined&&data.data.checkuser.length>0){
        			$.each(data.data.checkuser,function(i,item){
        				$("#auditing_man").find("ul").append( '<li class="clearfix"><span class="fl" >'+item.realname+'</span><input type="hidden" name="userId" value="'+item.userId+'" /><input type="hidden" name="realname" value="'+item.realname+'" /></li>');

//        				$(".auditing_man").find("ul").append( '<li><input type="hidden" name="userId" value="'+item.userId+'" /><input type="hidden" name="realname" value="'+item.realname+'" /><span class="tl_name">'+item.realname+'</span><span class="tl_job">'+item.position+'</span><span class="tl_handle"><i></i></span></li>');
        			})
        		}
        	}else{
        		toastr.error("查询失败");
        	}
         },
		error : function() {
			layer.close(index);
			toastr.error("查询失败");
		}
     })
}