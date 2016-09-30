var ue ;
$(function(){
	if(uname=="超级管理员"){
		$("#noticety").show();
	}
	
	$("#page").hide();
	$("#ascrail2000").remove();
		//管理员
		$("#page").onairPage({"callback":getDataByManager});
			getDataByManager();
		$("#search_id").click(function(){
			getDataByManager(1,10);
		});
		document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 getDataByManager(1,10);
	         }
   		}; 
		 ue = UE.getEditor('editor');
		 
	 $(".btn_div a.add").click(function(){
		 getNoticeType();
	 	 $("#gg_name").val("");
	 	 $("#notice_title").html("*");
	 	 ue.setContent("");
		 $(".gg_list").hide();
	 	 $(".add_gg_div").show();
	 	 $("#page").hide();
	 });
	  	 $("#gg_name").blur(function(){
  			if(!$.trim($("#gg_name").val())){
  				$("#notice_title").html("标题不能为空");
  			}else{
  				$("#notice_title").html("*");
  			}
		 });
//	 $(".add_btn_div a").click(function(){
//		$(".gg_list").show();
//		$(".add_gg_div").hide();
//		$("#page").show();
//	 });
//	UE.getEditor('editor').addListener('blur',function(){
//		//alert();
//		if(!ue.getContent()){
//  				$("#notice_contents").html("内容不能为空");
//  			}else{
//  				$("#notice_contents").html("*");
//  			}
//	});
});

function getNoticeType(selectedId){
	
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
        'url':ctx +pathValue+  "notice/findNoticeTypeAll/", //服务器的地址
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data': onairMap.toJson(),
        'success':function(data){
       	if(data.code==0){
       		if(data.data.results!=undefined&&data.data.results.length>0){
       			$("#noticeTypeId").html('');
       			$.each(data.data.results,function(i,item){
       				if(selectedId!=undefined){
       					if(selectedId==item._id){
       	       				$("#noticeTypeId").append("<option value="+item._id+" selected>"+item.noticename+"</option>");
       					}else{
       	       				$("#noticeTypeId").append("<option value="+item._id+">"+item.noticename+"</option>");
       					}
       				}else{
   	       				$("#noticeTypeId").append("<option value="+item._id+">"+item.noticename+"</option>");

       				}
       			})
       		}
       	
       		
		 }
        }
	});
}
function getDataByManager(a,b){
	var page;
	var pageNum;
	page=(a==undefined?$("#page").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#page").getOnairPageParameter().pageSize:b)
	getNoticeForManager(page,pageNum);
}

function getNoticeForManager(page,pageNum){
	var title = $("#title").val();
	if (!title) {
		title = "";
	}
	var startTime = $("#time_begin").val();
	if (!startTime) {
		startTime = "";
	}
	var endTime = $("#time_end").val();
	if (!endTime) {
		endTime = "";
	}
	if(!checkEndTime(startTime,endTime)){
		return;
	}
	var strJson={"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"keyWord":title,"currentPage":page,"pageNum":pageNum,"startTime":startTime,"endTime":endTime}
	strJson= JSON.stringify(strJson);
	$.ajax({
        url : ctx + pathValue + 'notice/findNotices/',
        dataType:'json', //返回数据类型
        headers: {'Content-Type': 'application/json'},
        type:'POST', //请求类型
        data : strJson,
        async:false,
        success:function(data){
	       	if(data.code==0){
	       		var html = [];
	       		var currentPage = data.data.currentPage;
	       		if(data.data.results !=undefined&&data.data.results.length>0){
	       			$("#page").show();
	       			$("#system_notice_list").html('');  
	       			html.push("<table border='0' cellpadding='0' cellspacing='0'>"); 
                    html.push("<tr>"); 
                    html.push(" <th></th>"); 
                    html.push("<th class='i_td'><input type='checkbox' id='all'></th>"); 
                    html.push("<th>公告类型</th>");
                    html.push("<th>标题</th>");
                    html.push("<th>内容</th>");
                    html.push("<th>创建人</th>");
                    html.push("<th>创建时间</th>");
                    html.push("<th>操作</th>");
                    html.push("</tr>");
                    var n = 1;
                  	for(var i=0;i<data.data.results.length;i++){
                  	 	html.push("<tr>");
                  	 	html.push("<td>"+n+"</td>");
                  	   	html.push("<td class='i_td'><input type='checkbox' name='notice_name' value="+data.data.results[i]._id+"></td>");
                  	   	html.push("<td>"+toString(data.data.results[i].noticetype==undefined?'':data.data.results[i].noticetype.noticename)+"</td>");
                  	    html.push("<td title='"+data.data.results[i].noticetitle+"'>"+cutString(data.data.results[i].noticetitle,16,'...')+"</td>");
                  	    html.push("<td >"+cutString(data.data.results[i].noticecontent,60,'...')+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].cusename)+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].ctime)+"</td>");
                  	    html.push("<td><a href='javascript:;' onclick='editNotice(\""+data.data.results[i]._id +"\");'>编辑</a><a href='javascript:;' class='td_del' onclick='deleteNotice(\""+data.data.results[i]._id +"\");'>删除</a></td>");
                  	    html.push("</tr>");
                  	    if(n > pageNum){
                  	    	n = 1;
                  	    }
                  	    n++;
                  }
                  html.push("</table>");
                  $("#div_system_content").removeClass("wu_data");
	       		}else{
	       			 html.push("<div class='no_data'></div>");
	       			 $("#div_system_content").addClass("wu_data");
	       			 $("#page").hide();
				}
					$("#system_notice_list").html(html.join(""));
					$("#page").resetOnairPageParameter(data.data.totalRecord,currentPage);	
					initSelectAll();
			 }else{
			 	toastr.error("获取公告列表失败");
			 }
        }
	});
}

function saveNotice(){
	var ueContent = ue.getContent();
	var ueContentTxt = ue.getContentTxt();
	$("#noticeContent").val(ueContentTxt);
	$("#noticeContentHTML").val(ueContent);
	var title = $("#gg_name").val();
	//var content = $("#gg_content").val();
	//var content = $("#gg_content").text(content).html();
	var content = $("#noticeContent").val();
	var noticeTypeId = $("#noticeTypeId").val();
	var contentHTML = $("#noticeContentHTML").val();
	var noticeId = $("#notice_id").val();
	if (!title) {
		$("#notice_title").html("标题不能为空");
		$("#gg_name").focus();
		return;
	}
	if (!content) {
		toastr.warning("内容不能为空");
		return;
	}
	if (!contentHTML) {
		contentHTML = "";
	}
	var strJson={"noticeId":noticeId,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"title":title,"content":content,"contentHtml":contentHTML,"noticeTypeId":noticeTypeId};
	strJson= JSON.stringify(strJson); 
	var url = ctx + pathValue + 'notice/updateNotice/';
	if(!noticeId){
		url = ctx + pathValue + 'notice/addNotice/';
	}
	$.ajax({
	        'url':url, //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':strJson,
	        'success':function(data){
	       	if(data.code==0){
	       		layer.msg('保存公告成功！');
	       		hideWindows();
		       getNoticeForManager(1,10);
			 }else{
				 toastr.error("操作失败");
			 }
	        }
		});
	
}

function hideWindows(){
	$(".gg_list").show();
	$(".add_gg_div").hide();
	$("#page").show();
}

function deleteNotice(id){
	layer.confirm("确定要删除公告吗？", {
		icon : 3,
		title : '提示'
	}, function(index) {
		layer.close(index);
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("noticeId",id);
		var index = layer.load();
		 $.ajax({
	        'url':ctx + pathValue + 'notice/deleteNotice/', //服务器的地址
	        'dataType':'json', //返回数据类型
	        'headers': {'Content-Type': 'application/json'},
	        'type':'POST', //请求类型
	        'data':onairMap.toJson(),
	        'success':function(data){
	        	if(data.code==0){
	        		layer.close(index);
	        		layer.msg('删除公告成功！');
	        		layer.closeAll('page');
	        		getNoticeForManager(1,10);
	        	}
	        },
	        error : function() {
	        	layer.close(index);
	        	layer.msg('删除公告失败！');
			}
	    })
	})
}

function deleteNotices(){
	var ids = [];
	 $("[name = notice_name]:checkbox").each(function () {
     if ($(this).is(":checked")) {
     	ids.push($(this).attr("value"));
     }
 	});
	 if(ids.length<1){
		 layer.msg('至少选择一条记录');
		 return  ;
	 }
	layer.confirm("确定要批量删除公告吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			var onairMap = new OnairHashMap();
			onairMap.put("accessToken", getAccessToken());
			onairMap.put("timeStamp", getTimeStamp());
			onairMap.put("noticeIds",ids);
			var index = layer.load();
			 $.ajax({
		        'url':ctx + pathValue + 'notice/deleteNotices/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	if(data.code==0){
			        		layer.close(index);
			        		layer.msg('批量删除公告成功！');
			        		layer.closeAll('page');
			        		getNoticeForManager(1,10);
		        	}
		        },
		        error : function() {
		        	layer.close(index);
		        	layer.msg('批量删除公告失败！');
				}
		    })
		})
}
function editNotice(id){
	layer.confirm("确定要编辑公告吗？", {
			icon : 3,
			title : '提示'
		}, function(index) {
			layer.close(index);
			 $(".gg_list").hide();
			 $(".add_gg_div").show();
			 $("#page").hide();
			  $("#notice_title").html("*");
			 var onairMap = new OnairHashMap();
			 onairMap.put("accessToken", getAccessToken());
			 onairMap.put("timeStamp", getTimeStamp());
			 onairMap.put("noticeId",id);
			 var index = layer.load();
			 $.ajax({
		        'url':ctx + pathValue + 'notice/findNoticeById/', //服务器的地址
		        'dataType':'json', //返回数据类型
		        'headers': {'Content-Type': 'application/json'},
		        'type':'POST', //请求类型
		        'data':onairMap.toJson(),
		        'success':function(data){
		        	if(data.code==0){
		        		layer.close(index);
		        		layer.closeAll('page');
		        		getNoticeType(data.data.noticetype==undefined?data.data.noticetype:data.data.noticetype._id)
		        		//$("#noticeTypeId").val(data.data.noticetype._id);
		        		$("#notice_id").val(data.data._id);
		        		$("#gg_name").val(data.data.noticetitle);
		        		$("#noticeContent").val(data.data.noticecontent);
		        		$("#noticeContentHTML").val(data.data.noticecontenthtml);
		        		ue.setContent(data.data.noticecontenthtml);
		        	}
		        },
		        error : function() {
		        	layer.close(index);
					layer.closeAll('page');
				}
	    })
	})
}

/**
 * 全选相关的操作
 */
function initSelectAll(){
	 //全选或全不选 
     $("#all").click(function(){  
        if(this.checked){  
            $("#system_notice_list :checkbox").prop('checked',true);   
        }else{    
            $("#system_notice_list :checkbox").prop('checked',false);
        }    
     }); 
     $("input[name='notice_name']").click(function () {
                $("input[name='notice_name']:checked").length == $("input[name='notice_name']").length ? $("#all").prop("checked", true) : $("#all").prop("checked", false);
     });

}

function toString(str){
	if(str == null || str == undefined){
		str = '';
	}
	return str;
}

//截取字符串
function cutString(str,len,suffix){  
  	if(!str) return "";  
  	if(len<= 0) return "";  
    if(!suffix) suffix = "";  
   	var templen=0;  
   	for(var i=0;i<str.length;i++){  
        if(str.charCodeAt(i)>255){  
           templen+=2;  
       	}else{  
           templen++  
        }  
        if(templen == len){  
           return str.substring(0,i+1)+suffix;  
        }else if(templen >len){  
           return str.substring(0,i)+suffix;  
        }  
    }  
    return str;  
}

function checkEndTime(time_begin,time_end){
	if(time_begin != "" && time_end != ""){
		 var start=new Date(time_begin.replace("-", "/").replace("-", "/"));  
   		 var end=new Date(time_end.replace("-", "/").replace("-", "/"));  
   		  if(end<start){  
   		  	 toastr.warning("时间输入错误!");
        	 return false;
    	  } 
	}
	return true;
}  
