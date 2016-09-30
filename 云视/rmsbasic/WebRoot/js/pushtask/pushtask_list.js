$(function(){
	$("#ascrail2000").remove();
	$("#indexPop").hide();
	$("#indexJX").hide();
		//管理员
		$("#page").onairPage({"callback":getpathlist});
			getpathlist();
		$("#search_direct").click(function(){
			getpathlist(1,10);
		});
		
		$("#reset_direct").click(function(){
			$("#starttime").val('');
			$("#endtime").val('');
			$("#title").val('');
		});
		
		document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 getpathlist(1,10);
	         }
   		}; 
	
})


function getpathlist(a,b){
	var page;
	var pageNum;
	page=(a==undefined?$("#page").getOnairPageParameter().pageNow:a)
	pageNum=(b==undefined?$("#page").getOnairPageParameter().pageSize:b)
	findDirectPass(page,pageNum);
}

function findDirectPass(page,pageNum){
	var strJson={"startTime":$("#starttime").val(),"endTime":$("#endtime").val(),"keyWord":$("#title").val(),"accessToken":getAccessToken(),"timeStamp":getTimeStamp(),"currentPage":page,"pageNum":pageNum}
	strJson= JSON.stringify(strJson);
	$.ajax({
        url : ctx + pathValue + 'pushTask/pushTaskFindList/',
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
	       			$("#pushtask_list").html('');   
	       			html.push("<table border='0' cellpadding='0' cellspacing='0'>"); 
                    html.push("<tr>"); 
                    html.push("<th width='5%'>序号</th>"); 
                    html.push("<th width='20%'>任务名称</th>"); 
                    html.push("<th width='10%'>创建者</th>"); 
                    html.push("<th width='9%'>类型</th>"); 
                    html.push("<th width='20%'>描述</th>"); 
                    html.push("<th width='9%'>创建时间</th>"); 
                    html.push("<th width='9%'>状态</th>"); 
                    html.push("<th width='9%'>完成时间</th>"); 
                    html.push("</tr>");
                    var n = 1;
                  	for(var i=0;i<data.data.results.length;i++){
                  		if(toString(data.data.results[i].status)==2){
                  			html.push("<tr id='"+data.data.results[i]._id+"' processStatus='"+data.data.results[i]._id+"'>");
                  		}else{
                  			html.push("<tr id='"+data.data.results[i]._id+"' >");
                  		}
                  	 	html.push("<td>"+n+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].title)+"</td>");
                  	    html.push("<td>"+toString(toString(data.data.results[i].cusename))+"</td>");
                  	    html.push("<td>"+toString(toString(data.data.results[i].srcName))+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].uniqueName)+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].ctime)+"</td>");
                  	    html.push("<td>"+getStatus(toString(data.data.results[i].status))+"</td>");
                  	    html.push("<td>"+toString(data.data.results[i].uutime)+"</td>");
                  	    html.push("</tr>");
                  	    if(n > pageNum){
                  	    	n = 1;
                  	    }
                  	    n++;
                  }
                  html.push("</table>"); 
                  $("#outter").show();
                  $("#inner").hide();
                  $("#pushtask_list").html(html.join(""));
                  $("#page").resetOnairPageParameter(data.data.totalRecord,currentPage);	
	       		}else{
	       			$("#inner").html("<div class='no_data'></div>");
	       			$("#inner").show();
	       			$("#outter").hide();
				}
			 }else{
			 	toastr.error("查询任务列表失败");
			 }
        }
	});
}


//获取素材进度
function loadProgress(){
		var pulls = $("[processStatus]");
		var map = new Map();
		for ( var j = 0; j < pulls.size(); j++) {
			var $sp = $(pulls.get(j));
			var trid = $sp.attr("processstatus");
			map.put(j, trid);
		}
		if (map.getValues().toString() != "") {
			var cataIds = map.getValues().toString();
			var onairMap = new OnairHashMap();
			onairMap.put("accessToken", getAccessToken());
			onairMap.put("timeStamp", getTimeStamp());
			onairMap.put("ids", cataIds);
			$.ajax({
				url : ctx + pathValue+"pushTask/queryProgress/",
				headers:{"Content-Type":"application/json"},
				data : onairMap.toJson(),
				type : "post",
				cache : false,
				dataType : "json",
				success : function(response) {
					var code = response.code;
					var data = response.data;
					if(code != 0){
						return;
					}
					if(code == 0 && data.length > 0){
						data.forEach(function(element, index){
							console.log(element.progress)
							$("#"+element.id).find("td:eq(6)").text("处理中  "+element.progress);
							if(element.progress=="100.00%"){
								$("#"+element.id).find("td:eq(6)").text("转码完成");
								loadlist();
							}
							
						})
					}
				}					
			});
		}
}


refreshId = setInterval("loadProgress()", 5000);
function loadlist(){
	var pulls = $("[processStatus]");
	var map = new Map();
	for ( var j = 0; j < pulls.size(); j++) {
		var $sp = $(pulls.get(j));
		var trid = $sp.attr("processstatus");
		map.put(j, trid);
	}
	if (map.getValues().toString() != "") {
		var cataIds = map.getValues().toString();
		var onairMap = new OnairHashMap();
		onairMap.put("accessToken", getAccessToken());
		onairMap.put("timeStamp", getTimeStamp());
		onairMap.put("ids", cataIds);
		$.ajax({
			url : ctx + pathValue+"pushTask/pushTaskFindById/",
			headers:{"Content-Type":"application/json"},
			data : onairMap.toJson(),
			type : "post",
			cache : false,
			dataType : "json",
			success : function(response) {
				var code = response.code;
				var data = response.data;
				if(code != 0){
					return;
				}
				if(code == 0 && data.results.length > 0){
					data.results.forEach(function(element, index){
						if(element.status==0){
							$("#"+element._id).removeAttr("processStatus");
							$("#"+element._id).find("td:eq(6)").text("成功");
							$("#"+element._id).find("td:eq(7)").text(element.uutime);
						}else if(element.status==1){
							console.log("status:"+element.status)
							$("#"+element._id).removeAttr("processStatus");
							$("#"+element._id).find("td:eq(6)").text("失败");
							$("#"+element._id).find("td:eq(7)").text(element.uutime);
						}
						
						
					})
				}
			}					
		});
	}
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
	}else if(status=="2"){
		return "进行中";
	}else if(status=="3"){
		return "转码完成";
	}else {
		return "转码失败";
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

function Map() {
	var myArrays = new Array();
	// 添加键值，如果键重复则替换值
	this.put = function(key, value) {
		var v = this.get(key);
		if (v == null) {
			var len = myArrays.length;
			myArrays[len] = {
				Key : key,
				Value : value
			};
		} else {
			for ( var i = 0; i < myArrays.length; i++) {
				if (myArrays[i].Key == key) {
					myArrays[i].Value = value;
				}
			}
		}
	},
	// 根据键获取值
	this.get = function(key) {
		for ( var i = 0; i < myArrays.length; i++) {
			if (myArrays[i].Key == key) {
				return myArrays[i].Value;
			}
		}
		return null;
	},
	// 以数组形式返回值列表
	this.getValues = function() {
		var newArray = new Array();
		for ( var i = 0; i < myArrays.length; i++) {
			newArray[i] = myArrays[i].Value;
		}
		return newArray;
	}
}
