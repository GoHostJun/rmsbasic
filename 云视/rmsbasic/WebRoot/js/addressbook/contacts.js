$(document).ready(function() {
	$("#ascrail2000").remove();
	$("#indexPop").remove();
	$("#indexJX").remove();
	$("#pageDiv").hide();
	$("#pageDiv").onairPage({
		"callback" : getListData
	});
	getListData(1,10)
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 getListData(1,10);
        }
    }; 
});

function getListData(pageNow,pageSize){
	var index = layer.load();
	$("#listDiv").html("正在查看请稍后......");
	if(!pageNow || !pageSize){
		var pagePar = $("#pageDiv").getOnairPageParameter();
		pageNow = pagePar.pageNow;
		pageSize = pagePar.pageSize;
	}
	var keyWord = $("#search_input").val();
	if(!keyWord){
		keyWord = "";
	}
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("keyWord", keyWord);
	onairMap.put("currentPage", pageNow);
	onairMap.put("pageNum", pageSize);
	$.ajax({
		url : ctx + pathValue + "org/getUsersByAppCode/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(response) {
			layer.close(index);
			var code = response.code;
			var data = response.data;
			if(code != 0){
				$("#listDiv").html("查询失败，请重试！");
				return;
			}
			if(code == 0 && data.results.length > 0){
				$("#pageDiv").show();
				loadHtml(data);
			}else{
				$("#pageDiv").hide();
				$("#addresslist_id").html("<div class='no_data'></div>");
			}
		},
		error : function() {}
	});
}

/**
 * 渲染页面
 * 
 * @param obj
 */
function loadHtml(obj){
	var totalRecord = obj.totalRecord;
	var currentPage = obj.currentPage;
	var pageNum = obj.pageNum;
	var results = obj.results;
	var html = [];
	html.push("<table border='0' cellpadding='0' cellspacing='0'>");
	html.push("<tr>");
	html.push("<th>序号</th>");          
    html.push("<th style='width:20%;'>姓名</th>");            
    html.push("<th>机构</th>");             
    html.push("<th>工号</th>");            
    html.push("<th>电话</th>");             
    html.push("<th>邮箱</th>");             
  //  html.push("<th>发送消息</th>");            
    html.push("</tr>");  
    var n = 1;
	$(results).each(function(i,item){
		 html.push("<tr>");
         html.push("<td>"+n+"</td>");         
         html.push("<td>"+'<p style="margin-left:40px;"><b id="head_img_b"  class="heading_info" style="margin-top:0;">'+getName(item.name)+'</b><span style="margin-top:12px; float:left;">'+toString(item.name)+'</span></p></td>');  
        
         html.push("<td>"+toString(getDepartmentNames(item.department))+"</td>");          
         html.push("<td>"+toString(item.empNo)+"</td>");           
         html.push("<td>"+toString(item.phone)+"</td>");            
         html.push("<td class='mail_td'>"+toString(item.emailaddress)+"</td>");           
     //    html.push("<td><a href='javascript:;' class='noclick'>发送消息<i></i></a></td>");           
         html.push("</tr>");  
         if(n>9){
        	 n=1;
         }
         n++;
		});
		html.push("</table>");  
	$("#addresslist_id").html(html.join(""));
	$("#pageDiv").resetOnairPageParameter(totalRecord,currentPage);	
	
}
function getName(name){
	
	return name==undefined?"":(name.substring(name.length-2,name.length));
}
/**
 * 获取部门名称
 * 
 * @param departments
 * @returns
 */
function getDepartmentNames(departments) {
	var departmentNames = [];
	$(departments).each(function(i, item) {
		departmentNames.push(item.name);
		departmentNames.push(",");
	});
	departmentNames.pop();
	return departmentNames.join("");
}

 function toString(str){
	if(str == null || str == undefined || str == 'null'){
		str = '';
	}
	return str;
}