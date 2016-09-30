$(function(){
		$("#page").hide();
		$("#page").onairPage({"callback":getDataByUser});
			getDataByUser(1,10);
		$("#search_id").click(function(){
			getDataByUser(1,10);
		})
		document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 getDataByUser(1,10);
	         }
   		}; 
})

function getDataByUser(a,b){
	var page;
	var pageNum;
	page=(a==undefined?$("#page").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#page").getOnairPageParameter().pageSize:b)
	getNoticeForUser(page,pageNum);
}

function getNoticeForUser(page,pageNum){
	var keyWord = $("#search").val();
	if(!keyWord){
		keyWord = "";
	}
		var strJson={"noticeTypeId":noticeTypeId,"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"keyWord":keyWord,"currentPage":page,"pageNum":pageNum}
	strJson= JSON.stringify(strJson);
	$.ajax({
        'url' : ctx + pathValue + 'notice/findNotices/',
        'dataType':'json', //返回数据类型
        'headers': {'Content-Type': 'application/json'},
        'type':'POST', //请求类型
        'data':strJson,
        'success':function(data){
	       	if(data.code==0){
	       		var html = "";
	       		if(data.data.results !=undefined&&data.data.results.length>0){
	       			 $("#page").show();
	       		//	$("#page").resetOnairPageParameter(data.data.totalRecord);
	       			$("#div_system_content").html('');   
	       									html+= "<table  border='0.5' cellspacing='0' cellpadding='0'>"+
										"<thead>"+
											"<tr>"+
												"<th style='width: 5%;'></th>"+
												"<th style='width: 15%;'>公告类型</th>"+
												"<th style='width: 15%;'>标题</th>"+
												"<th style='width:50%;'>内容</th>"+
												"<th style='width: 30%;'>发布人</th>"+
												"<th style='width: 15%;'>发布时间</th>"+
											"</tr>"+
										"</thead>"+
										"<tbody>";
										var n = 1;
							for(var i=0;i<data.data.results.length;i++){  
								//uCont("notice/toNoticeDetail/?id='5673fc8b79836f455b913045'")
								var hrefStr = "javascript:uCont(\"notice/toNoticeDetail/?id='" + data.data.results[i]._id+"'\");";
								html += "<tr>"+    
											"<td>"+n+"</td>"+
											"<td><a href="+hrefStr+">"+cutString(data.data.results[i].noticetype==undefined?'':data.data.results[i].noticetype.noticename)+"</a></td>"+
											"<td title='"+data.data.results[i].noticetitle+"'><a href="+hrefStr+">"+cutString(data.data.results[i].noticetitle,16,'...')+"</a></td>"+
											"<td ><a href="+hrefStr+">"+cutString(data.data.results[i].noticecontent,80,'...')+"</a></td>"+
											"<td>"+toString(data.data.results[i].cusename)+"</td>"+
											"<td>"+toString(data.data.results[i].ctime)+"&nbsp;</td>"+
										"</tr>";
										 if(n > pageNum){
                  	    					n = 1;
                  	   					 }
                  	    				 n++;
							}
							html+= "</tbody>"+"</table> "; 
							$("#div_system_content").removeClass("wu_data");
	       			}else{
	       				html += "<div class='no_data'></div>";
	       				$("#div_system_content").addClass("wu_data");
	       				$("#page").hide();
					}
					$("#div_system_content").html(html);
					$("#page").resetOnairPageParameter(data.data.totalRecord, data.data.currentPage);
			 }else{
			 	toastr.error("获取公告列表失败");
			 }
        }
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
