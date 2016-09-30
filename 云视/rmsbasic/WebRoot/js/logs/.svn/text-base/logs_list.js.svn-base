$(function(){
	$("#ascrail2000").remove();
		//管理员
		$("#page").onairPage({"callback":getSystemLogs});
			getSystemLogs();
		$("#search_id").click(function(){
			getSystemLogs(1,10);
		});
		document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 getSystemLogs(1,10);
	         }
   		}; 
	
})



function getSystemLogs(a,b){
	var page;
	var pageNum;
	page=(a==undefined?$("#page").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#page").getOnairPageParameter().pageSize:b)
	findLogs(page,pageNum);
}

function findLogs(page,pageNum){
	var title = $("#title").val();
	if (!title) {
		title = "";
	}
	var cusename=$("#cusename").val();
	if (!cusename) {
		cusename = "";
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
	var strJson={"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"keyWord":title,"cusename":cusename,"currentPage":page,"pageNum":pageNum,"startTime":startTime,"endTime":endTime}
	strJson= JSON.stringify(strJson);
	$.ajax({
        url : ctx + pathValue + 'log/query/',
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
	       			$("#system_logs_list").html('');   
	       			html.push("<table border='0' cellpadding='0' cellspacing='0'>"); 
                    html.push("<tr>"); 
                    html.push(" <th style='width: 3%;'></th>"); 
                    html.push("<th style='width: 13%;'>标题</th>");
                    html.push("<th style='width: 43%;'>描述</th>");
                    html.push("<th style='width: 16%;'>创建时间 </th>");
                    html.push("<th style='width: 13%;'>创建人</th>");
                    html.push("<th style='width: 12%;'>IP</th>");
                    html.push("</tr>");
                    var n = 1;
                  	for(var i=0;i<data.data.results.length;i++){
                  	 	html.push("<tr>");
                  	 	html.push("<td>"+n+"</td>");
                  	    html.push("<td>"+data.data.results[i].action+"</td>");
                  	    html.push("<td title='"+data.data.results[i].logdesc+"'>"+cutString(data.data.results[i].logdesc,36,'...')+"</td>");
                  	    html.push("<td>"+data.data.results[i].ctime+"</td>");
                  	    html.push("<td>"+data.data.results[i].cusename+"</td>");
                  	    html.push("<td>"+data.data.results[i].ip+"</td>");
                  	    html.push("</tr>");
                  	    if(n > pageNum){
                  	    	n = 1;
                  	    }
                  	    n++;
                  }
                  html.push("</table>"); 
                  $("#page").show();
	       		}else{
	       			html.push("<div class='no_data'></div>");
	       			$("#page").hide();
				}
					$("#system_logs_list").html(html.join(""));
					$("#page").resetOnairPageParameter(data.data.totalRecord,currentPage);	
			 }else{
			 	toastr.error("查询日志列表失败");
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
   	if(str.length <= len){
   		return str;  
   	}
   	for(var i=0;i<str.length;i++){  
           templen++;  
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

/**
 * 导出日志
 */
function exportLogs(page,pageNum){
	var title = $("#title").val();
	if (!title) {
		title = "";
	}
	var cusename=$("#cusename").val();
	if (!cusename) {
		cusename = "";
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
	//设置列宽
	var columnWidths={"action":4000,"logdesc":20000,"ctime":5500,"cusename":2500,"ip":4000}
	var strJson={"keyWord":title,"cusename":cusename,"currentPage":1,"pageNum":20000,"startTime":startTime,"endTime":endTime,"columnWidths":columnWidths}
	strJson = JSON.stringify(strJson);
	this.window.location = ctx + pathValue + "export/exportLogs/?strJson="+strJson;
}

