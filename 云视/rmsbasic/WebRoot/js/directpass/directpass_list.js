$(function(){
	$("#ascrail2000").remove();
	$("#indexPop").hide();
	$("#indexJX").hide();
	getAllDirectPassingPath();
		//管理员
		$("#page").onairPage({"callback":getdirectpass});
			getdirectpass();
		$("#search_direct").click(function(){
			getdirectpass(1,10);
		});
		
		$("#reset_direct").click(function(){
			$("#starttime").val('');
			$("#endtime").val('');
			$("#filename").val('');
		});
		
		document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 getdirectpass(1,10);
	         }
   		}; 
	
})

/**
 * 获取上传路径
 */
function getAllDirectPassingPath(){
	var onairMap = new OnairHashMap();
	onairMap.put("currentPage", 1);
	onairMap.put("pageNum", 10000);
	onairMap.put("keyWord", $("#pushnameword").val());
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	var index = layer.load();
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx +pathValue+"directpassingpath/findAllDirectPassingPath/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			layer.close(index);
			if (0 == data.code) {
				$("#directpath").html('');
				if(data.data.results&&data.data.results.length>0){
					$("#directpath").append('<option value="">请选择</option>');
					$.each(data.data.results,function(i,item){
						$("#directpath").append('<option value="'+item._id+'">'+item.tarpathname+'</option>');
					})
				}
			} else {
				layer.msg('查询直传路径失败！');
			}
		},
		error : function() {
			layer.close(index);
			layer.msg('请求出错！');
		}
	});
}

function getdirectpass(a,b){
	var page;
	var pageNum;
	page=(a==undefined?$("#page").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#page").getOnairPageParameter().pageSize:b)
	findDirectPass(page,pageNum);
}

function findDirectPass(page,pageNum){
	
	var strJson={"directId":$("#directpath").val(),"startTime":$("#starttime").val(),"endTime":$("#endtime").val(),"keyWord":$("#filename").val(),"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"currentPage":page,"pageNum":pageNum}
	strJson= JSON.stringify(strJson);
	$.ajax({
        url : ctx + pathValue + 'directpassing/findDirectPassAll/',
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
	       			$("#directpass_list").html('');   
	       			html.push("<table border='0' cellpadding='0' cellspacing='0'>"); 
                    html.push("<tr>"); 
                    html.push("<th width='5%'>序号</th>"); 
                    html.push("<th width='20%'>文件名称</th>"); 
                    html.push("<th width='9%'>类型</th>"); 
                    html.push("<th width='10%'>大小</th>"); 
                    html.push("<th width='20%'>创建时间</th>"); 
                    html.push("<th width='9%'>上传状态</th>"); 
                    html.push(" <th width='9%'>目标路径</th>"); 
                    html.push("<th width='9%'>迁移状态</th>"); 
                    html.push("<th width='9%'>是否可用</th>"); 
                    html.push("</tr>");
                    var n = 1;
                  	for(var i=0;i<data.data.results.length;i++){
                  	 	html.push("<tr>");
                  	 	html.push("<td>"+n+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].name)+"</td>");
                  	    html.push("<td>"+getType(toString(data.data.results[i].type))+"</td>");
                  	    html.push("<td>"+formartSize(toString(data.data.results[i].size))+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].ctime)+"</td>");
                  	    html.push("<td>成功</td>");
                  	    html.push("<td >"+toString(data.data.results[i].directpathname)+"</td>");
                  	    html.push("<td>"+getStatus(toString(data.data.results[i].status))+"</td>");
                  	    html.push("<td>"+getNTMStatus(toString(data.data.results[i].otherStatus))+"</td>");
                  	    html.push("</tr>");
                  	    if(n > pageNum){
                  	    	n = 1;
                  	    }
                  	    n++;
                  }
                  html.push("</table>"); 
                  $("#outter").show();
                  $("#inner").hide();
                  $("#directpass_list").html(html.join(""));
                  $("#page").resetOnairPageParameter(data.data.totalRecord,currentPage);	
	       		}else{
	       			$("#inner").html("<div class='no_data'></div>");
	       			$("#inner").show();
	       			$("#outter").hide();
				}
			 }else{
			 	toastr.error("查询直传记录失败");
			 }
        }
	});
}
/**
 * 文件类型
 * @param type
 * @returns {String}
 */
function getType(type){
	if(type==0){
		return "视频";
	}else if(type==1){
		return "音频";
	}else if(type==2){
		return "图片";
	}
}
/**
 * 处理文件大小
 * 
 * @param fileSize
 * @returns
 */
function formartSize(fileSize){
	if(fileSize){
		if(parseFloat(fileSize/1024/1024) > 1){
			fileSize = parseFloat(fileSize/1024/1024);
			return fileSize.toFixed(2) + " MB";
		}else{
			fileSize = parseFloat(fileSize/1024);
			return fileSize.toFixed(2) + " KB";
		}
	}
	return toString(fileSize);
}
/**
 * 迁移状态
 * @param status
 * @returns {String}
 */
function getStatus(status){
	if(status=="0"){
		return "成功";
	}else if(status=="1"){
		return "失败";
	}else {
		return "迁移中";
	}
}
/**
 * ntm状态
 * @param status
 * @returns {String}
 */
function getNTMStatus(status){
	if(status=="0"){
		return "可用";
	}else if(status=="-1"){
		return "不可用";
	}else{
		return "...";
	}
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


