//var wsUri ="wss://"+ctx+"/api/user/isBIndSucess/"; 
//websocket = new WebSocket(wsUri); 
//websocket.onopen = function(evt) { 
//	
//};  
//websocket.onmessage = function(evt) { 
//    console.log(evt.code);
//}; 

function unbindWeixin(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userId", userId);
	$.ajax({
		url : ctx  + "/api/auth/unbindWeChat/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				toastr.success("解绑微信成功!");
				getUserInfo();
			}else{
				toastr.success("解绑微信失败!");
			}
		},
		error : function(e){
			toastr.success("解绑微信失败!");
		}
	});
}
function unbindqq(){
	var onairMap = new OnairHashMap();
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	onairMap.put("userId", userId);
	$.ajax({
		url : ctx  + "/api/auth/unbindqq/",
		headers:{"Content-Type":"application/json"},
		data : onairMap.toJson(),
		type : "post",
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				toastr.success("解绑qq成功!");
				getUserInfo();
			}else{
				toastr.success("解绑qq失败!");
			}
		},
		error : function(e){
			toastr.success("解绑qq失败!");
		}
	});
}
function openWeixin(){
//	$.ajax({
//		url : ctx  +'/api/auth/redistWeChatOpen/',
//		cache : false,
//		success : function(data) {
//		
//			window.open(data);
//		}
//	});
	
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx + pathPage + "system/toPage/?view=index/wechat_qcode",
		cache : false,
		async : false,
		success : function(data) {
			layer.open({
				type : 1, // page层
				area: ['400px','440px'],
				title : " ",
				shade : 0.5, // 遮罩透明度
				moveType : 1, // 拖拽风格，0是默认，1是传统拖动
				shift : 0, // 0-6的动画形式，-1不开启
			//	maxmin : true,
				scrollbar : false,
				//offset: ['300px', '200px'],
				content : data,
				shade:0.01
			});
		},
		error : function() {
			$("#table_body").html("查询出错了...");
		}
	});
	
}
function openqq(){
	$.ajax({
		url : ctx  +'/api/auth/redistQQOpen/',
		cache : false,
		success : function(data) {
		
			window.open(data);
		}
	});
}