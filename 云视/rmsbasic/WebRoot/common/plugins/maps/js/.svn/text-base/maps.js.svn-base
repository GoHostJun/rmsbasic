//查询首页地图的代办城市任务【通连人】
function getUnDealCityTasks() {
	var index = layer.load();
	$("div[name='yunnan']").hide();
	var cityCodes = [];
	for(var n=0;n<16;n++){
		var code = $("div[name='yunnan']").eq(n).attr("id");  
		cityCodes.push(code);
 	}
 	var strJson = {"accessToken":getAccessToken(),
 					"timeStamp":getTimeStamp(),
 					"areaCode":cityCodes,
 					"shareuser":userId
 					}
	strJson= JSON.stringify(strJson);
	$.ajax({
		type : 'POST',
		dataType:'json',
		headers: {'Content-Type': 'application/json'},
		url : ctx + pathValue + 'mapinfo/findUnDealCityTasks/',
		data: strJson,
		async : false,
		success : function(data) {
			if(data.code == 0){
				if(data.data != null && data.data.length > 0){
					for(var i = 0; i< data.data.length ; i++){
						if(data.data[i].count > 0){
							$("#"+data.data[i].areacode).show();
							$("#"+data.data[i].areacode).attr("title",data.data[i].count);
						}
					}
				}
			}else{
				toastr.warning("地图任务查询失败!");
			}
			layer.close(index);
		},
		error : function(e){
			toastr.error("系统初始化地图任务错误!");
			layer.close(index);
		}
	});
}

//$(document).ready(function() {
//	windowSizeChange();
//});

//查询首页地图的代办城市任务【通连人】
//function getUnDealCityTasks() {
//	$.ajax({
//		type : 'POST',
//		dataType:'json',
//		headers: {'Content-Type': 'application/json'},
//		url : ctx + pathValue + 'mapinfo/findUnDealCityTasks/',
//		data: {
//		"cuid":"123"
//		},
//		async : false,
//		success : function(data) {
//			if(data.code == 0){
//				if(data.data != null && data.data.length > 0){
//					for(var i = 0; i< data.data.length ; i++){
//						getMapNewsCity(data.data[i].pointX,data.data[i].pointY,i);		
//					}
//				}
//			}else{
//				alert("系统操作失败!");
//			}
//		},
//		error : function(e){
//			alert("系统初始化错误!");
//		}
//	});
//}

////获取地图单个城市任务
//function getMapNewsCity(xx,yy,i){
//	//地图图片的宽度和高度
//	var img_w = $("#map_div_id").width(); 
//	var img_h = $("#map_div_id").height();
//	//当前城市坐标点离map的左部和顶部的比例
//	k_x=accDiv(xx, 721); 
//	k_y=accDiv(yy, 573); 
//	//当前城市坐标点离map的左部和顶部的距离
//	var x =  accMul(img_w, k_x);
//	var y =  accMul(img_h, k_y);
//	//将这个距离设置给坐标(相对定位)
//	var id = "point_"+i; 
//	var img = "<img class = 'point_class' id='"+id+"' src='"+ctx+"/common/images/b.jpg' height='10' width='5' />";
//	$("#div_news_maps").append(img);
//	$("#point_"+i).css({"top":y,"left":x});
//}
//	
////乘法函数  
//function accMul(arg1, arg2) {
//	var m = 0, s1 = arg1.toString(),s2 = arg2.toString();
//	try {
//		m += s1.split(".")[1].length;
//	} catch (e) {}
//	try {
//		m += s2.split(".")[1].length;
//	} catch (e) {}
//	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
//}
//	
////除法函数  
//function accDiv(arg1, arg2) {
//	var t1 = 0, t2 = 0, r1, r2;
//	try {
//		t1 = arg1.toString().split(".")[1].length;
//	} catch (e) {
//	}
//	try {
//		t2 = arg2.toString().split(".")[1].length;
//	} catch (e) {
//	}
//	with (Math) {
//	r1 = Number(arg1.toString().replace(".", ""));
//	r2 = Number(arg2.toString().replace(".", ""));
//		return (r1 / r2) * pow(10, t2 - t1);
//	}
//}

//窗口大小改变事件(针对改变地图大小的情况)
//function windowSizeChange(){
//	window.onresize = function() { 
//		//getMapNewsCity(593,285);
//		getUnDealCityTasks();
//	}; 
//}