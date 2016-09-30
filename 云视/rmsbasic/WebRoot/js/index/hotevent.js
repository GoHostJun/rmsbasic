function getHotEvent(){
	
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("userName", "cri_test");
	onairMap.put("dateFlag", 1);
	
	
	var url="";
	var writeDatafunc;
	if(getUrlConfig.nameTV=="JS"){
		onairMap.put("count", 5);
		onairMap.put("pageSize", 5);
		//江苏
		url=ctx + pathValue + "hot/findJSHotEvents/"
		writeDatafunc= writeJSData
	}else{
		onairMap.put("count", 10);
		onairMap.put("pageSize", 10);
		url=ctx + pathValue + "hot/findHotEvents/"
		writeDatafunc=writeData
	}
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var code = data.code;
			if(code == 0){
				writeDatafunc(data.data.data)
			}
			
		}
	});
}


//主页上获取更多热点事件
function getMoreHotEvent(){
	if(getUrlConfig.nameTV=="JS"){
		//江苏
		url="http://zyjs.cdvcloud.com/"
	}else if(getUrlConfig.nameTV=="YN"){
		url="http://zy.news.cdvcloud.com/channel/cas?action=/work/myWork"
	}else{
		url= "http://zy.cdvcloud.com/";
		
	}
	window.open(url);
}


function writeJSData(results){
	
	var html = [];
	$(results).each(function(i,item){
		html.push("<li style=\"cursor:pointer;\" title = '"+item.newsTitle+"' onclick='twosessionslink(\""+item.newsId+"\");'>"+item.newsTitle+"</li>");
	});
	$("#ul_news_hotevents").html(html.join(""));
}

function writeData(results){
	
	var html = [];
	var htmlJX = [];
	$(results).each(function(i,item){
		html.push("<li title = '"+item.newsTitle+"'>"+item.newsTitle+"</li>");
	});
	var count=1;
	$(results).each(function(i,item){
		htmlJX.push("<li title = '"+item.newsTitle+"'><span>"+(count++)+"</span>"+cutTitleString(item.newsTitle,34,"...")+"</li>");
	});
	$("#ul_news_hotevents").html(html.join(""));
	$("#ul_news_hotevents_JX").html(htmlJX.join(""));
}

function twosessionslink(newsId){
	window.open("http://zyjs.cdvcloud.com/detail/showDetail_new/?id="+newsId);
}