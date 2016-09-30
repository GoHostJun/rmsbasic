/**
 * 展示推送路径详情
 * 
 * @param userId
 */
function showDirectPassingPath(id) {
	var onairMap = new OnairHashMap();
	onairMap.put("_id", id);
	onairMap.put("accessToken", getAccessToken());
	onairMap.put("timeStamp", getTimeStamp());
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : ctx  + pathValue+"directpassingpath/findOneDirectPassingPath/",
		data : onairMap.toJson(),
		cache : false,
		async : false,
		success : function(data) {
			var path = data.data;
			$("#_id").val(path._id)
			$("#tarpathname").val(path.tarpathname)
			$("#tarpathid").val(path.tarpathid)
			$("#tarpathaddr").val(path.tarpathaddr)
			$("#othermsg").val(path.othermsg)
			$("#pushpathaddr").val(path.pushpathaddr)
			$("#pushpathname").val(path.pushpathname)
		}
	});
}