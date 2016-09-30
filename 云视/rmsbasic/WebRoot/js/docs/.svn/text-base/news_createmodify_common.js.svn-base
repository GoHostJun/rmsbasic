var firstDepArr=[];
var secondDepArr=[];
var thirdDepArr=[];
//点击添加通联人或者审核人
function addpoeple(This,name,role,id){
	if(existsJoinOrAudit2(id)==1){
		return ;
	}
	if($(".sh-leaguer").hasClass("active")){
		if($("#auditing_man").find("ul").length<1){
			$("#auditing_man").html('');
			
		}
		if(role=="审核人"){
			$("#auditing_man").find("ul").append( '<li class="clearfix"><span class="fl" >'+name+'</span><span class="fr" onclick="removepoeple(this,event)">删除</span><input type="hidden" name="userId" value="'+id+'" /><input type="hidden" name="realname" value="'+name+'" /></li>');
		}else{
			toastr.warning("只能添加审核人");
			return ;
		}
	}else{
		if($("#tonglian_man").find("ul").length<1){
			$("#tonglian_man").html('');
		}
		$("#tonglian_man").find("ul").append( '<li class="clearfix"><span class="fl" >'+name+'</span><span class="fr" onclick="removepoeple(this,event)">删除</span><input type="hidden" name="userId" value="'+id+'" /><input type="hidden" name="realname" value="'+name+'" /></li>');
	}
	if($(This).parent().is("ul")){
		$(This).remove();
	}else{
		$(This).parent().remove();
	}

}
//判断通联人和审核人是否已经有
function existsJoinOrAudit2(id){
	var flag=0
	$.each( $("#tonglian_man [name=userId]"),function(i,item){
		if($(this).val()==id){
			flag=1;
			toastr.warning("该用户通联人已存在");
			return ;
		}
	})
	if($(".sh-leaguer").hasClass("active")){
		if(id==userId){
			flag=1;
			toastr.warning("当前用户不能为审核人");
		}
	}
	
	$.each( $("#auditing_man [name=userId]"),function(i,item){
		if($(this).val()==id){
			flag=1;
			toastr.warning("该用户审核人已存在");
			return ;
		}
	})
	return flag;
}
//点击删除通联人或者审核人
function removepoeple(This,e){
	e.stopPropagation();
	$(This).parent().remove();
}
//加载通联人或者审核人
function loadUserByTypt(){
	var type;
	 if($(".lt-leaguer").hasClass("active")){
		 type="";
	 }else{
		 type="审核人";
	 }
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
		       		$("#userList").html("");
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

function loadUserByDept(partId){
	 var index = layer.load();
	 var type;
	 if($(".lt-leaguer").hasClass("active")){
		 type="";
	 }else{
		 type="审核人";
	 }
	 var jsonStr={"keyWord":$("#searchInput").val(),"name":type,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"partId":partId};
         jsonStr= JSON.stringify(jsonStr);
	 $.ajax({
	        url:ctx+pathValue+"org/getUsersByPartId2/", //服务器的地址
	        dataType:'json', //返回数据类型
	        headers: {'Content-Type': 'application/json'},
	        type:'POST', //请求类型
	        data:jsonStr,
	        success:function(data){
		        layer.close(index);
		       	if(data.code==0){
       				$("#userList").html("");
		       		if(data.data.results!=undefined&&data.data.results.length>0){
		       			loadUser(data.data.results);
		       		}else{
		       			$("#userList").html("<ul><p>暂无成员</p></ul>")
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
//加载用户展示
function loadUser( user){
	$("#userList").append("<ul></ul>");
	$.each(user,function(i,item){
		$("#userList").find("ul").append(
				"<li onclick='addActive(this)' ondblclick='addpoeple(this ,\""+item.realname+"\",\""+getFirstArray(item.role)+"\",\""+item._id+"\")'>" +
				"<span>"+item.realname+"</span>" +
				"<span >"+getFirstArray(item.department)+"</span><input type='hidden' value="+item._id+"  name='_id'/>" +
				"<span onclick='addpoeple(this ,\""+item.realname+"\",\""+getFirstArray(item.role)+"\",\""+item._id+"\")'>添加</span></li>");
	})
	
}
function addActive(This){
	$(This).addClass("active").siblings().removeClass("active");
}
//加载部门
function loadDep2(){
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
                	dataIterator2(data.data.results,tempdata,0);
                	$("[name='firstDep']").html('');
            		$("[name='firstDep']").append("<option value=''>全部</option>");
                	$.each(firstDepArr,function(i,item){
                		$("[name='firstDep']").append("<option _id="+item._id+" value="+item.id+">"+item.name+"</option>")
                	})
                	$("[name='firstDep']").change(function(){
                		$("[name='secondDep']").html('');
                		$("[name='secondDep']").append("<option value=''>请选择</option>");
                		$("[name='thirdDep']").html('');
                		$("[name='thirdDep']").append("<option value=''>请选择</option>");
                		$.each(secondDepArr,function(i,item){
                			if($("[name='firstDep']").val()==item.pId){
                				$("[name='secondDep']").append("<option _id="+item._id+" value="+item.id+">"+item.name+"</option>")
                			}
                    	})
                    	if($("[name='firstDep']").find("option:selected").attr("_id")){
                    		
                    		loadUserByDept($("[name='firstDep']").find("option:selected").attr("_id"))
                    	}else{
                    		loadUserByTypt();
                    	}
                	})
                	$("[name='secondDep']").change(function(){
                		$("[name='thirdDep']").html('');
                		$("[name='thirdDep']").append("<option value=''>请选择</option>");
                		$.each(thirdDepArr,function(i,item){
                			if($("[name='secondDep']").val()==item.pId){
                				$("[name='thirdDep']").append("<option _id="+item._id+" value="+item.id+">"+item.name+"</option>")
                			}
                    	})
                    	if(!$("[name='secondDep']").find("option:selected").attr("_id")){
                    		loadUserByDept($("[name='firstDep']").find("option:selected").attr("_id"))
                    	}else{
                    		loadUserByDept($("[name='secondDep']").find("option:selected").attr("_id"))
                    	}

                	})
            		$("[name='thirdDep']").change(function(){
            			if(!$("[name='thirdDep']").find("option:selected").attr("_id")){
            				loadUserByDept($("[name='secondDep']").find("option:selected").attr("_id"))
            			}else{
            				loadUserByDept($("[name='thirdDep']").find("option:selected").attr("_id"))
            			}
            		})
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
var topDep = new OnairHashMap();
var itCount=0;
function dataIterator2(results,temp){
	itCount++;
	temp=[];
	//alert(results.length)
	$.each(results,function(i,item){
		//第一次
		if(itCount==1){
			if(item._id==item.id){
				topDep.put("_id", item._id);
				topDep.put("id", item.id);
				topDep.put("pId", item.pId);
			}else{
				temp.push(item);
			}
		}else{
			if(itCount==2){
				//第二次
				if(topDep.get("id")==item.pId){
					firstDepArr.push(item);
				}else{
					temp.push(item);
				}
				
			}else{
				if(itCount==3){
					//第三次
					var state=0;
					$.each(firstDepArr,function(j,jtem){
						if(item.pId==jtem.id){
							state=1;
							secondDepArr.push(item);
							return ;
						}
					})
					if(state==0){
						temp.push(item);
					}
					
				}else{
					if(itCount==4){
						//第四次
						var state=0;
						$.each(secondDepArr,function(j,jtem){
							if(item.pId==jtem.id){
								state=1;
								thirdDepArr.push(item);
								return ;
							}
						})
						if(state==0){
							temp.push(item);
						}
						
					}
				}
			}
		}
		
	})
	//alert(temp.length)
	if(temp.length>0){
		dataIterator2(temp,[]);
	}
		
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