/**
 * 展示推送路径详情
 * 
 * @param userId
 */
function showpushpathInfo(id) {
	var onairMap = new OnairHashMap();
	onairMap.put("_id", id);
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  + pathValue+"push/getPushSetInfo/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var pushpath = data.data;
			$("#_id").val(pushpath._id)
			$("#pushName").val(pushpath.pushname)
			$("#uniqueName").val(pushpath.uniquename)
			$("#pushUrl").val(pushpath.pushurl)
			$("#queryUrl").val(pushpath.queryurl)
			$("#otherMsg").val(pushpath.othermsg)
		}
	});
}