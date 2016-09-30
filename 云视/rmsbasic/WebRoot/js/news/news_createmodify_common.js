//点击添加通联人或者审核人
function addpoeple(This,name,position,id,dept){
	if(existsJoinOrAudit(id)==1){
		return ;
	}
	if($(".tonglian_man").hasClass("active")){
		if(position=="审核人"){
			$(".auditing_man").find("ul").append( '<li><input type="hidden" name="userId" value="'+id+'" /><input type="hidden" name="realname" value="'+name+'" /><input type="hidden" name="position" value="'+position+'" /><span class="tl_name">'+name+'</span><span class="tl_job">'+position+'</span><span class="tl_handle"><i onclick="removepoeple(this,event,\''+name+'\',\''+position+'\',\''+id+'\',\''+dept+'\')"></i></span></li>');
		}else{
			toastr.warning("只能添加审核人");
			return ;
		}
	}else{
		$(".tonglian_man").find("ul").append( '<li><input type="hidden" name="userId" value="'+id+'"/><input type="hidden" name="realname" value="'+name+'" /><input type="hidden" name="position" value="'+position+'" /><span class="tl_name">'+name+'</span><span class="tl_job">'+position+'</span><span class="tl_handle"><i onclick="removepoeple(this,event,\''+name+'\',\''+position+'\',\''+id+'\',\''+dept+'\')"></i></span></li>');
	}
	$(This).parent().parent().remove();

}
//判断通联人和审核人是否已经有
function existsJoinOrAudit(id){
	var flag=0
	$.each( $(".tonglian_man [name=userId]"),function(i,item){
		if($(this).val()==id){
			flag=1;
			toastr.warning("该用户通联人已存在");
			return false;
		}
	})
	$.each( $(".auditing_man [name=userId]"),function(i,item){
		if($(this).val()==userId){
			flag=1;
			toastr.warning("当前用户不能为审核人");
			return false;
		}
		if($(this).val()==id){
			flag=1;
			toastr.warning("该用户审核人已存在");
			return false;
		}
	})
	return flag;
}
//点击删除通联人或者审核人
function removepoeple(This,e,name,position,id,dept){
	if($(This).parent().parent().parent().parent().hasClass("active")){
		return;
	}
	/* 由于不知道当前用户是否在当前组织结构存在*/
    if(existUserList(id,dept)!=1){
    	$(".modify_right_r").find("ul").append('<li><input type="hidden" value="'+id+'" name="_id"/><span class="mrr_name">'+name+'</span><span class="mrr_job">'+position+'</span><span class="mrr_inst">'+dept+'</span><span class="mrr_operate"><i onclick="addpoeple(this,\''+name+'\',\''+position+'\',\''+id+'\',\''+dept+'\')"></i></span></li>');
    } 
	e.stopPropagation();
	$(This).parent().parent().remove();
}
//判断用户列表是否 1代表存在
function existUserList(id,dept){
	var flag=0
	$.each( $(".modify_right_r [name=_id]"),function(i,item){
		if($(this).val()==id){
			flag=1;
		}
		/*
		if($(this).nextAll(".mrr_inst").html()==dept){
		}else{
			flag=1;
			
		}*/
	})
	return flag;

}
//加载通联人或者审核人
function loadUserByTypt(type){
	 var index = layer.load();
	 var jsonStr={"name":type,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
         jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
	        url:ctx+pathValue+"org/getUsersByRole/", //服务器的地址
	        dataType:'json', //返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', //请求类型
	        data:jsonStr,
	        success:function(data){
		        layer.close(index);
		       	if(data.code==0){
		       		if(data.data.results!=undefined&&data.data.results.length>0){
		       			loadUser(data.data.results);
		       		}
				 }else{
					 toastr.error("查询通联人信息出错");
				 }
	        },
			error : function(){
				layer.close(index);
				toastr.error("查询通联人信息出错");
			}
		});
}
//加载部门
function loadDep(){
	var index = layer.load();
	//加载结构
	var allDep={"accessToken":getAccessToken(),"timeStamp":getTimeStamp()}
	allDep= JSON.stringify(allDep);
	$.ajax({
        'url':ctx+pathValue+"org/getOrgsByAppCode/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':allDep,
        'success':function(data){
        	layer.close(index);
        	if(data.code==0){
        		//加载本台机构
        		  //遍历返回值
                var tempdata=new Array();
                if(data.data.results!=undefined && data.data.results.length>0){
                	dataIterator(data.data.results,tempdata);
                }
        		
        	}else{
        		toastr.error("查询组织结构失败");
        	}
         },
		error : function(){
			layer.close(index);
			toastr.error("查询组织结构失败");
		}
     })
}
//递归
 function dataIterator(data,tempdata){
        if(tempdata.length>0){
        	data=tempdata;
        }
        var tempdata=new Array();
        if(data.length<1)return;
        $.each(data,function(i,item){
        	orgData(tempdata,item);
        })
        dataIterator(tempdata,tempdata);
    }
//组织结构
function orgData(tempdata,item){
        var childOrg =
        	'<dt style="display: none;" id="'+item._id+'" pid="'+item.pId+'"  onclick="loaddeppeople(\''+item._id+'\')">'+item.name+'</dt>';
        if(item.id==item._id){
        	$("#org").append('<ul id="'+item._id+'" >'+
					'<li class="menu1" onclick="menuToggle(this)"><i></i>'+item.name+'</li><dl class="menu-list"></dl>'+
					'</ul>');
        }else if($("#"+item.pId).length>0){  
            $("#"+item.pId).find("dl").append(childOrg);
        }else{
            tempdata.push(item);
        }
	
}
//点击机构+号的效果
function menuToggle(This){
	$(This).find('i').toggleClass('active');
	$(This).show();
	$(This).siblings().toggle();
	$(This).siblings().find("dt").toggle();
	$(This).parent("ul").siblings().find("li:gt(0)").hide();
	$(This).parent("ul").siblings().find("dt").hide();
	$(This).parent("ul").siblings().find("dl").hide();
	$(This).parent("ul").siblings().find("i").removeClass('active');
}
//加载用户展示
function loadUser( user){
	$(".modify_right_r").find("ul li:gt(0)").remove();
	$.each(user,function(i,item){
		$(".modify_right_r").find("ul").append(
				"<li><input type='hidden' value="+item._id+"  name='_id'/>" +
				"<span class='mrr_name'>"+item.realname+"</span>" +
				"<span class='mrr_job'>"+getFirstArray(item.role)+"</span>" +
				"<span class='mrr_inst'>"+getFirstArray(item.department)+"</span>" +
				"<span class='mrr_operate'" +
				"><i onclick='addpoeple(this ,\""+item.realname+"\",\""+getFirstArray(item.role)+"\",\""+item._id+"\",\""+getFirstArray(item.department)+"\")'>" +
		"</i></span></li>");
	})
}

//点击机构加载人
function loaddeppeople(_id){
	 $(".modify_right_r").find("ul li:gt(0)").remove();
	 var index = layer.load();
	 var jsonStr={"partId":_id,"accessToken":getAccessToken(),"timeStamp":getTimeStamp()};
	 jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
	        url:ctx+pathValue+"org/getUsersByPartId/", //服务器的地址
	        dataType:'json', //返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', //请求类型
	        data:jsonStr,
	        success:function(data){
	        	layer.close(index);
		       	if(data.code==0){
		       		if(data.data.results!=undefined&&data.data.results.length>0){
		       			$.each(data.data.results,function(i,item){
		       				$(".modify_right_r").find("ul").append(
		       						"<li><input type='hidden' value="+item._id+" name='_id'/>" +
		       						"<span class='mrr_name'>"+item.realname+"</span>" +
		       						"<span class='mrr_job'>"+getFirstArray(item.role)+"</span>" +
		       						"<span class='mrr_inst'>"+getFirstArray(item.department)+"</span>" +
		       						"<span class='mrr_operate'" +
		       						"><i onclick='addpoeple(this ,\""+item.realname+"\",\""+getFirstArray(item.role)+"\",\""+item._id+"\",\""+getFirstArray(item.department)+"\")'>" +
		       				"</i></span></li>");
		       			})
		       		}
				 }else{
					toastr.error("查询失败");
				 }
	        },
			error : function(){
				layer.close(index);
				toastr.error("查询失败");
			}
		});
	 
}
//获取角色或者部门第一个对象的name属性值
function getFirstArray(data){
	var firstData="";
	if(data!=undefined&&data.length>0){
		$.each(data,function(i,item){
			if(i==0){
				firstData=item.name;
				return ;
			}
			
		})
	}
	return firstData;
}
